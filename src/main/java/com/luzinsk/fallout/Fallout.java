package com.luzinsk.fallout;

import com.luzinsk.fallout.commands.TestCommand;
import com.luzinsk.fallout.listeners.PlayerBowListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Fallout extends JavaPlugin {

    @Override
    public void onEnable() {

        //Listener
        getServer().getPluginManager().registerEvents(new PlayerBowListener(), this);

        //Commands
        Objects.requireNonNull(getCommand("crossbow")).setExecutor(new TestCommand());

    }

    @Override
    public void onDisable() {


    }
}
