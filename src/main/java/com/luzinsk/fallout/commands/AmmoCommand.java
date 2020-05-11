package com.luzinsk.fallout.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.luzinsk.fallout.listeners.PlayerListener.falloutPlayers;

public class AmmoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            sender.sendMessage("Console");
            return true;
        } else {
            player.sendMessage(falloutPlayers.get(player.getUniqueId().toString()).getAmmo() + "/" + falloutPlayers.get(player.getUniqueId().toString()).getMaxAmmo());
        }

        return true;
    }
}
