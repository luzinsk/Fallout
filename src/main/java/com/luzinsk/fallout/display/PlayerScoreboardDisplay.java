package com.luzinsk.fallout.display;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static com.luzinsk.fallout.listeners.PlayerListener.falloutPlayers;

public class PlayerScoreboardDisplay {

    private Player player;

    public PlayerScoreboardDisplay(Player player) {
        this.player = player;
    }

    public void createBoard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("FalloutDisp1","dummy","Player Information");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score line1 = obj.getScore(ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-=-");
        line1.setScore(3);

        Score line2 = obj.getScore(ChatColor.GOLD + "Current Weapon: " + ChatColor.AQUA + falloutPlayers.get(player.getUniqueId().toString()).getCurrentItem().getItemMeta().getDisplayName());
        line2.setScore(2);

        Score line3 = obj.getScore(ChatColor.GOLD + "Magazine: " + ChatColor.AQUA + falloutPlayers.get(player.getUniqueId().toString()).getAmmo() + "/" + falloutPlayers.get(player.getUniqueId().toString()).getMaxAmmo());
        line3.setScore(1);

        Score line4 = obj.getScore(ChatColor.DARK_PURPLE + "=-=-=-=-=-=-=-=-=-=");
        line4.setScore(0);

        player.setScoreboard(board);
    }




}
