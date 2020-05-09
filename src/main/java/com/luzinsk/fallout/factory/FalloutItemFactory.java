package com.luzinsk.fallout.factory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class FalloutItemFactory {

    public static List<String> M16(){

        ItemStack M16 = new ItemStack(Material.CROSSBOW);
        ItemMeta meta = M16.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Semi Automatic Rifle"));

        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "M-16");

        M16.setItemMeta(meta);

        return M16.getItemMeta().getLore();
    }

    public static List<String> AWP(){

        ItemStack AWP = new ItemStack(Material.CROSSBOW);
        ItemMeta meta = AWP.getItemMeta();
        meta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Sniper Rifle"));

        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "AWP");

        AWP.setItemMeta(meta);

        return AWP.getItemMeta().getLore();
    }
}
