package com.luzinsk.fallout.commands;

import com.luzinsk.fallout.factory.FalloutItemFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class M16Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            sender.sendMessage("Console");
            return true;
        } else {
            player.getInventory().addItem(FalloutItemFactory.M16());
        }

        return true;
    }
}
