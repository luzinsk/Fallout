package com.luzinsk.fallout.commands;

import com.luzinsk.fallout.entities.FalloutPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.luzinsk.fallout.listeners.PlayerListener.falloutPlayers;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;

        if (sender == null) {
            sender.sendMessage("Console");
            return true;
        } else {
            FalloutPlayer fplayer = falloutPlayers.get(player.getUniqueId().toString());

            if (fplayer.hasAmmo())
                fplayer.setAmmo(fplayer.getAmmo() - 1);
            fplayer.createFalloutPlayerScoreboard();
        }

    return true;
    }
}