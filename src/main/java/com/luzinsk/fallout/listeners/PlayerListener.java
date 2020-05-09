package com.luzinsk.fallout.listeners;

import com.luzinsk.fallout.entities.FalloutPlayer;
import com.luzinsk.fallout.factory.FalloutItemFactory;
import com.luzinsk.fallout.weapons.type.AWP;
import com.luzinsk.fallout.weapons.type.M16;
import com.luzinsk.fallout.weapons.type.None;
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

        if(event.hasItem() && event.getItem().getType().equals(Material.CROSSBOW)) {
            Player player = event.getPlayer();

            FalloutPlayer fplayer = falloutPlayers.get(player.getUniqueId().toString());

            ItemStack item = player.getInventory().getItemInMainHand();
            CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();

            if (event.getAction().equals(Action.LEFT_CLICK_AIR) && fplayer.getCurrentWeap().hasAmmo() ||
            event.getAction().equals(Action.LEFT_CLICK_BLOCK) && fplayer.getCurrentWeap().hasAmmo()) {
                meta.setChargedProjectiles(null);
                player.getInventory().getItemInMainHand().setItemMeta(meta);
                player.getInventory().addItem(new ItemStack(Material.ARROW, fplayer.getCurrentWeap().getMagazine()));
                fplayer.getCurrentWeap().setMagazine(0);
                falloutPlayers.get(event.getPlayer().getUniqueId().toString()).createFalloutPlayerScoreboard();
            } else if (fplayer.getCurrentWeap().hasAmmo() && !event.getAction().equals(Action.LEFT_CLICK_AIR) && !event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                fplayer.getCurrentWeap().decrementMagazine();
                falloutPlayers.get(event.getPlayer().getUniqueId().toString()).createFalloutPlayerScoreboard();
                if (!meta.hasChargedProjectiles()) {
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
        Inventory inv = event.getPlayer().getInventory();

        int slotId = event.getNewSlot();
        if (slotId >= 0 && slotId < inv.getSize()) {
            ItemStack stack = inv.getItem(slotId);

            if (stack != null) {
                if (stack.getItemMeta().getLore().equals(FalloutItemFactory.M16())) {
                    falloutPlayers.get(event.getPlayer().getUniqueId().toString()).setCurrentWeap(new M16());
                } else if (stack.getItemMeta().getLore().equals(FalloutItemFactory.AWP())) {
                    falloutPlayers.get(event.getPlayer().getUniqueId().toString()).setCurrentWeap(new AWP());
                } else {
                    falloutPlayers.get(event.getPlayer().getUniqueId().toString()).setCurrentWeap(new None());
                }

            }
            falloutPlayers.get(event.getPlayer().getUniqueId().toString()).createFalloutPlayerScoreboard();
        }

    }
}
