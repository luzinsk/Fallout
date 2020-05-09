package com.luzinsk.fallout.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class M16Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            sender.sendMessage("Console");
            return true;
        } else {
            ItemStack M16 = new ItemStack(Material.CROSSBOW);
            ItemMeta meta = M16.getItemMeta();
            meta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Semi Automatic Rifle"));

            meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "M-16");

            M16.setItemMeta(meta);
            player.getInventory().addItem(M16);
        }

        return true;
    }
}
