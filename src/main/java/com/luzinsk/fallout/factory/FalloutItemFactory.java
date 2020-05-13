package com.luzinsk.fallout.factory;

import com.luzinsk.fallout.Fallout;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class FalloutItemFactory {

    public static ItemStack M4() {

        ItemStack M4 = new ItemStack(Material.CROSSBOW);
        ItemMeta meta = M4.getItemMeta();
        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
        NamespacedKey maxAmmo = new NamespacedKey(Fallout.instance, "maxAmmo");
        meta.getPersistentDataContainer().set(ammo, PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(maxAmmo, PersistentDataType.INTEGER, 30);
        meta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Semi Automatic Rifle"));
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "M-16");
        meta.setUnbreakable(true);

        M4.setItemMeta(meta);
        return M4;
    }

    public static ItemStack AWP() {

        ItemStack AWP = new ItemStack(Material.CROSSBOW);
        ItemMeta meta = AWP.getItemMeta();
        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
        NamespacedKey maxAmmo = new NamespacedKey(Fallout.instance, "maxAmmo");
        meta.getPersistentDataContainer().set(ammo, PersistentDataType.INTEGER, 0);
        meta.getPersistentDataContainer().set(maxAmmo, PersistentDataType.INTEGER, 5);
        meta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Sniper Rifle"));
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "AWP");
        meta.setUnbreakable(true);

        AWP.setItemMeta(meta);
        return AWP;
    }
}
