package com.luzinsk.fallout.display;

import com.luzinsk.fallout.entities.FalloutPlayer;
import com.luzinsk.fallout.factory.FalloutItemFactory;
import com.sun.media.jfxmedia.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.logging.Level;

import static com.luzinsk.fallout.listeners.PlayerListener.falloutPlayers;

public class PlayerScoreboardDisplay {

    private Player player;

    public PlayerScoreboardDisplay(Player player) {
        this.player = player;
    }

    public void createBoard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("FalloutDisp1", "dummy", "Player Information");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        FalloutPlayer fplayer = falloutPlayers.get(player.getUniqueId().toString());

        if (fplayer != null) {

            Score line1 = obj.getScore(ChatColor.DARK_PURPLE + "-=-=-=-=-=-=-=-=-=-");
            line1.setScore(3);

            if (fplayer.getCurrentItem() != null && fplayer.getCurrentItem().getItemMeta().getLore() != null) {
                if (fplayer.getCurrentItem().getItemMeta().getLore().equals(FalloutItemFactory.M16().getItemMeta().getLore()) ||
                        fplayer.getCurrentItem().getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore())) {
                    Score line2 = obj.getScore(ChatColor.GOLD + "Current Weapon: " + ChatColor.AQUA + fplayer.getCurrentItem().getItemMeta().getDisplayName());
                    line2.setScore(2);
                    Score line3 = obj.getScore(ChatColor.GOLD + "Magazine: " + ChatColor.AQUA + fplayer.getAmmo() + "/" + fplayer.getMaxAmmo());
                    line3.setScore(1);
                } else {
                    Score line2 = obj.getScore(ChatColor.GOLD + "Current Weapon:" + ChatColor.RED + "" + ChatColor.BOLD + "N/A");
                    line2.setScore(2);
                    Score line3 = obj.getScore(ChatColor.GOLD + "Magazine: " + ChatColor.AQUA + "N/A");
                    line3.setScore(1);
                }
            } else {
                Score line2 = obj.getScore(ChatColor.GOLD + "Current Weapon:" + ChatColor.RED + "" + ChatColor.BOLD + "N/A");
                line2.setScore(2);
                Score line3 = obj.getScore(ChatColor.GOLD + "Magazine: " + ChatColor.AQUA + "N/A");
                line3.setScore(1);
                Bukkit.getLogger().log(Level.INFO, "No weapon");
            }

            Score line4 = obj.getScore(ChatColor.DARK_PURPLE + "=-=-=-=-=-=-=-=-=-=");
            line4.setScore(0);

            player.setScoreboard(board);
        }
    }

}
