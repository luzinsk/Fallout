package com.luzinsk.fallout;

import com.luzinsk.fallout.commands.TestCommand;
import com.luzinsk.fallout.display.Bar;
import com.luzinsk.fallout.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Objects;



public class Fallout extends JavaPlugin {

    public static Fallout instance;

    @Override
    public void onEnable() {

        instance = this;

        //Listener
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        //Commands
        Objects.requireNonNull(getCommand("crossbow")).setExecutor(new TestCommand());


        Bukkit.getScheduler().runTaskTimer(this, new Runnable(){

            @Override
            public void run() {

                for(Player all : Bukkit.getServer().getOnlinePlayers())
                {
                    Bar ammo = PlayerListener.ammoBar.get(all.getUniqueId().toString());
                    ItemStack item = all.getInventory().getItemInMainHand();
                    CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
                    if (meta.hasChargedProjectiles() && !ammo.isAmmo()) {
                        ammo.refillAmmo();
                    }
                }

            }

        }, 0, 5L);

    }

    @Override
    public void onDisable() {


    }
}
