package com.luzinsk.fallout.listeners;

import com.luzinsk.fallout.entities.FalloutPlayer;
import com.luzinsk.fallout.factory.FalloutItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;


public class PlayerListener implements Listener {

    public static HashMap<String, FalloutPlayer> falloutPlayers = new HashMap<String, FalloutPlayer>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(PlayerInteractEvent event) {

        if (event.hasItem() && event.getItem().getType().equals(Material.CROSSBOW)) {
            Player player = event.getPlayer();
            FalloutPlayer fplayer = falloutPlayers.get(player.getUniqueId().toString());
            ItemStack item = fplayer.getCurrentItem();
            CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
            fplayer.setCurrentItem(player.getInventory().getItemInMainHand());

            if (event.getAction().equals(Action.LEFT_CLICK_AIR) && fplayer.hasAmmo() ||
            event.getAction().equals(Action.LEFT_CLICK_BLOCK) && fplayer.hasAmmo()) {
                meta.setChargedProjectiles(null);
                player.getInventory().getItemInMainHand().setItemMeta(meta);
                player.getInventory().addItem(new ItemStack(Material.ARROW, fplayer.getAmmo()));
                fplayer.setAmmo(0);
                fplayer.createFalloutPlayerScoreboard();
                // FIX HERE \/
            } else if (fplayer.hasAmmo()) {
                fplayer.setCurrentItem(player.getInventory().getItemInMainHand());
                if (fplayer.hasAmmo())
                    fplayer.setAmmo(fplayer.getAmmo() - 1);
                fplayer.createFalloutPlayerScoreboard();
                if (!meta.hasChargedProjectiles()) {
                    meta = (CrossbowMeta) item.getItemMeta();
                    meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                    player.getInventory().getItemInMainHand().setItemMeta(meta);
                }

            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(EntityShootBowEvent event) {

        //=============================================================================================================
        // Takes the arrow entity and changes it's velocity.
        //=============================================================================================================

        Entity entity = event.getEntity();
        Float speed = event.getForce();
        Entity arrow = event.getProjectile();

        if (entity.getType().equals(EntityType.PLAYER)) {
            Player p = (Player) entity;
            Vector vec = p.getLocation().getDirection();
            if (event.getBow().getItemMeta().getLore().equals(FalloutItemFactory.AWP()))
                arrow.setVelocity(new Vector(vec.getX() * speed * 10, vec.getY() * speed * 10, vec.getZ() * speed * 10));
            else
                arrow.setVelocity(new Vector(vec.getX() * speed * 4.5, vec.getY() * speed * 3, vec.getZ() * speed * 4.5));
        }
        //=============================================================================================================
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void playerJoinEvent(PlayerJoinEvent event) {

        FalloutPlayer fplayer = new FalloutPlayer(event.getPlayer());
        falloutPlayers.put(event.getPlayer().getUniqueId().toString(), fplayer);
        fplayer.createFalloutPlayerScoreboard();

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileLaunch(ThrownPotion event) {
        Vector vec = event.getLocation().getDirection();
        event.setVelocity(new Vector(vec.getX() * 5, vec.getY()  * 4.5, vec.getZ() * 5));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onArrowHit(ProjectileHitEvent event) {
        event.getEntity().remove();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onItemSwitch(PlayerItemHeldEvent event) {

        if (falloutPlayers.get(event.getPlayer().getUniqueId().toString()) != null) {
            Inventory inv = event.getPlayer().getInventory();

            int slotId = event.getNewSlot();
            if (slotId >= 0 && slotId < inv.getSize()) {
                ItemStack stack = inv.getItem(slotId);

                if (stack != null)
                    falloutPlayers.get(event.getPlayer().getUniqueId().toString()).setCurrentItem(stack);
            }
            falloutPlayers.get(event.getPlayer().getUniqueId().toString()).createFalloutPlayerScoreboard();
        }

    }
}
