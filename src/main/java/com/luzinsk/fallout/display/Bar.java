package com.luzinsk.fallout.display;

import com.luzinsk.fallout.Fallout;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {

    private BossBar bar;

    public Bar() {
    }

    public void addPlayer(Player player) {
        bar.addPlayer(player);
    }

    public BossBar getBar() {
        return bar;
    }

    public void createBar() {
        bar = Bukkit.createBossBar("Ammo Remaining", BarColor.BLUE, BarStyle.SOLID);
        bar.setVisible(true);
        bar.setProgress(1.0);
    }

    public boolean isAmmo() {
        if (bar.getProgress() != 0) {
            return true;
        } else
            return false;
    }

    public void refillAmmo() {
        bar.setProgress(1.0);
    }

    public void useAmmo() {
        double progress = bar.getProgress();

        if (progress < .10)
            bar.setProgress(0.0);
        else {
            progress = progress - .10;
            bar.setProgress(progress);
        }
    }

}
