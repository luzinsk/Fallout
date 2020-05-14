package com.luzinsk.fallout.listeners;

import com.luzinsk.fallout.Fallout;
import com.luzinsk.fallout.entities.FalloutPlayer;
import com.luzinsk.fallout.factory.FalloutItemFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.*;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;


public class PlayerListener implements Listener {

    public static HashMap<String, FalloutPlayer> falloutPlayers = new HashMap<String, FalloutPlayer>();
    public static Random rand = new Random();

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(PlayerInteractEvent event) {

        if (event.hasItem() && event.getItem().getType().equals(Material.CROSSBOW)) {
            Player player = event.getPlayer();
            FalloutPlayer fplayer = falloutPlayers.get(player.getUniqueId().toString());
            ItemStack item = fplayer.getCurrentItem();
            CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
            fplayer.setCurrentItem(player.getInventory().getItemInMainHand());
            NamespacedKey timer = new NamespacedKey(Fallout.instance, "timer");

            if (event.getAction().equals(Action.LEFT_CLICK_AIR) && fplayer.hasAmmo() ||
            event.getAction().equals(Action.LEFT_CLICK_BLOCK) && fplayer.hasAmmo()) {
                meta.setChargedProjectiles(null);
                player.getInventory().getItemInMainHand().setItemMeta(meta);
                player.getInventory().addItem(new ItemStack(Material.ARROW, fplayer.getAmmo()));
                fplayer.setAmmo(0);
                fplayer.createFalloutPlayerScoreboard();
            } else if (fplayer.hasAmmo() && item.getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore()) && fplayer.getTimer() == 0) {
                fplayer.setAmmo(fplayer.getAmmo() - 1);
                fplayer.setTimer(fplayer.getMaxTimer());
                fplayer.createFalloutPlayerScoreboard();
                meta = (CrossbowMeta) item.getItemMeta();
                meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                player.getInventory().getItemInMainHand().setItemMeta(meta);
                Location location = player.getLocation();
                player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 2);
                player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, 2);
                player.playSound(location, Sound.BLOCK_STONE_BREAK, 2, 2);
                player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
                player.playSound(location, Sound.ENTITY_GHAST_SHOOT, 2, 2);

            } else if (fplayer.hasAmmo() && item.getItemMeta().getLore().equals(FalloutItemFactory.M97().getItemMeta().getLore()) && fplayer.getTimer() == 0) {
                fplayer.setAmmo(fplayer.getAmmo() - 1);
                fplayer.setTimer(fplayer.getMaxTimer());
                meta = (CrossbowMeta) item.getItemMeta();
                fplayer.createFalloutPlayerScoreboard();
                Location location = player.getLocation();
                location.add(0, 1.5, 0);
                meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                player.getInventory().getItemInMainHand().setItemMeta(meta);
                player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 2);
                for (int i = 0; i < 8; i++) {
                    Arrow arrow = player.getWorld().spawnArrow(location, location.getDirection(), 5, 2);
                    arrow.setShooter(player);
                    Vector vec = player.getLocation().getDirection();
                    arrow.setVelocity(new Vector(vec.getX(), vec.getY(), vec.getZ()).add(new Vector(rand.nextDouble() * .1 - .05, rand.nextDouble() * .1 - .05, rand.nextDouble() * .1 - .05)));
                    arrow.setVelocity(arrow.getVelocity().multiply(5.5));
                    player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, 2);
                    player.playSound(location, Sound.ENTITY_BLAZE_HURT, 2, 2);
                    player.playSound(location, Sound.BLOCK_STONE_BREAK, 2, 2);
                    player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
                    player.playSound(location, Sound.ENTITY_GHAST_SHOOT, 2, 2);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void shootArrowListener(EntityShootBowEvent event) {

        //=============================================================================================================
        // Takes the arrow entity and changes it's velocity.
        //=============================================================================================================

        Entity entity = event.getEntity();
        Float speed = event.getForce();
        Entity arrow = event.getProjectile();

        if (entity.getType().equals(EntityType.PLAYER)) {
            Player player = (Player) entity;
            Vector vec = player.getLocation().getDirection();
            if (event.getBow().getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore())) {
                arrow.setVelocity(new Vector(vec.getX() * speed * 50, vec.getY() * speed * 50, vec.getZ() * speed * 50));
                arrow.setGravity(false);
            } else
                arrow.setVelocity(new Vector(vec.getX() * speed * 6.5, vec.getY() * speed * 6, vec.getZ() * speed * 6.5));

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

                falloutPlayers.get(event.getPlayer().getUniqueId().toString()).setCurrentItem(stack);
            }
            falloutPlayers.get(event.getPlayer().getUniqueId().toString()).createFalloutPlayerScoreboard();
        }

    }
}
