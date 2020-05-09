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

public class AwpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            sender.sendMessage("Console");
            return true;
        } else {
            ItemStack AWP = new ItemStack(Material.CROSSBOW);
            ItemMeta meta = AWP.getItemMeta();
            meta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "Sniper Rifle"));

            meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "AWP");

            AWP.setItemMeta(meta);
            player.getInventory().addItem(AWP);
        }

        return true;
    }
}