package com.luzinsk.fallout.listeners;

import com.luzinsk.fallout.Fallout;
import com.luzinsk.fallout.display.Bar;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.HashMap;


public class PlayerListener implements Listener {

    public static HashMap<String, Bar> ammoBar = new HashMap<String, Bar>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(PlayerInteractEvent event) {

        //=============================================================================================================
        // Basic gun mechanics.
        //=============================================================================================================
        // Determines if a player has shot a crossbow.
        if (event.hasItem() && event.getItem().getType().equals(Material.CROSSBOW)) {

            Player player = event.getPlayer();

            Bar ammo = ammoBar.get(event.getPlayer().getUniqueId().toString());

            ItemStack item = player.getInventory().getItemInMainHand();

            CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();

            //if (meta.getDisplayName().equalsIgnoreCase("Mosin"))


            // Checks if player has ammo.
            if (ammo.isAmmo()) {
                ammo.useAmmo(.10);
                meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                player.getInventory().getItemInMainHand().setItemMeta(meta);
            }

        }
        //=============================================================================================================

        if (event.hasItem() && event.getItem().getType().equals(Material.FIREWORK_STAR)) {


            Entity entity = event.getPlayer().launchProjectile(ThrownPotion.class);
            Vector vec = event.getPlayer().getLocation().getDirection();
            entity.setVelocity(new Vector(vec.getX() * 1.8, vec.getY() * 2, vec.getZ() * 1.8));
        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(EntityShootBowEvent event) {

        //=============================================================================================================
        // Takes the arrow entity and changes it's vector.
        //=============================================================================================================
        Entity entity = event.getEntity();
        Float speed = event.getForce();
        Entity arrow = event.getProjectile();

        if (entity.getType().equals(EntityType.PLAYER)) {
            Player p = (Player) entity;
            Vector vec = p.getLocation().getDirection();
            arrow.setVelocity(new Vector(vec.getX() * speed * 5, vec.getY() * speed * 4.5, vec.getZ() * speed * 5));
        }
        //=============================================================================================================


    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void playerJoinEvent(PlayerJoinEvent event) {

        //=============================================================================================================
        // Assigns an ammo bar to each player on server and adds it into a HashMap.
        //=============================================================================================================
        Bar bar = new Bar();
        bar.createBar();
        bar.addPlayer(event.getPlayer());
        ammoBar.put(event.getPlayer().getUniqueId().toString(), bar);
        //=============================================================================================================
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileLaunch(ThrownPotion event) {
        Vector vec = event.getLocation().getDirection();
        event.setVelocity(new Vector(vec.getX() * 5, vec.getY()  * 4.5, vec.getZ() * 5));
    }
}
