package com.luzinsk.fallout.entities;

import com.luzinsk.fallout.display.PlayerScoreboardDisplay;
import com.luzinsk.fallout.weapons.Weapon;
import com.luzinsk.fallout.weapons.type.None;
import org.bukkit.entity.Player;

public class FalloutPlayer {

    private Player player;
    private PlayerScoreboardDisplay board;
    private Weapon currentWeap = new None();

    public FalloutPlayer(Player player) {
        this.player = player;
        board = new PlayerScoreboardDisplay(player);
    }

    public void createFalloutPlayerScoreboard() {
        board.createBoard();
    }

    public void setCurrentWeap(Weapon weap) {
        currentWeap = weap;
    }

    public Weapon getCurrentWeap() {
        return currentWeap;
    }


}
