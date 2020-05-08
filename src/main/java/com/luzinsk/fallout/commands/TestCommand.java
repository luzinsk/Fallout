package com.luzinsk.fallout.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.Collections;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            sender.sendMessage("Console");
            return true;
        } else {
            ItemStack loadedCrossbow = new ItemStack(Material.CROSSBOW, 1);

            CrossbowMeta meta = (CrossbowMeta) loadedCrossbow.getItemMeta();

            meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
            meta.setDisplayName("M4");
            meta.setLore(Collections.singletonList("Automatic Crossbow"));

            loadedCrossbow.setItemMeta(meta);

            player.getInventory().addItem(loadedCrossbow);
        }

        return true;
    }
}
