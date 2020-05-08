package com.luzinsk.fallout.listeners;

import com.luzinsk.fallout.display.Bar;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;


public class PlayerBowListener implements Listener {

    private HashMap<String, Bar> ammoBar = new HashMap<String, Bar>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(PlayerInteractEvent event) {

        if (event.hasItem() && event.getItem().getType().equals(Material.CROSSBOW)) {

            Player player = event.getPlayer();

            Bar ammo = ammoBar.get(event.getPlayer().getUniqueId().toString());


            CrossbowMeta meta = (CrossbowMeta) player.getInventory().getItemInMainHand().getItemMeta();

            if (meta.hasChargedProjectiles() && !ammo.isAmmo()) {
                ammo.refillAmmo();
            }

            if (ammo.isAmmo()) {
                ammo.useAmmo();
                meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                player.getInventory().getItemInMainHand().setItemMeta(meta);
            }

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void useItemListener(EntityShootBowEvent event) {


        Entity entity = event.getEntity();
        Float speed = event.getForce();
        Entity arrow = event.getProjectile();

        if (entity.getType().equals(EntityType.PLAYER)) {
            Player p = (Player) entity;
            Vector vec = p.getLocation().getDirection();
            arrow.setVelocity(new Vector(vec.getX() * speed * 5, vec.getY() * speed * 3.5, vec.getZ() * speed * 5));

        } else return;


    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void playerJoinEvent(PlayerJoinEvent event) {
        Bar bar = new Bar();
        bar.createBar();
        bar.addPlayer(event.getPlayer());
        ammoBar.put(event.getPlayer().getUniqueId().toString(), bar);
    }
}
