package com.luzinsk.fallout;

import com.luzinsk.fallout.commands.AmmoCommand;
import com.luzinsk.fallout.commands.AwpCommand;
import com.luzinsk.fallout.commands.M16Command;
import com.luzinsk.fallout.commands.TestCommand;
import com.luzinsk.fallout.entities.FalloutPlayer;
import com.luzinsk.fallout.factory.FalloutItemFactory;
import com.luzinsk.fallout.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;



public class Fallout extends JavaPlugin {

    public static Fallout instance;

    @Override
    public void onEnable() {

        instance = this;

        //Listener
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        //Commands
        Objects.requireNonNull(getCommand("m16")).setExecutor(new M16Command());
        Objects.requireNonNull(getCommand("awp")).setExecutor(new AwpCommand());
        Objects.requireNonNull(getCommand("ammo")).setExecutor(new AmmoCommand());
        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());

        Bukkit.getScheduler().runTaskTimer(this, new Runnable(){

            @Override
            public void run() {

                for(Player player : Bukkit.getServer().getOnlinePlayers()) {

                    FalloutPlayer fplayer = PlayerListener.falloutPlayers.get(player.getUniqueId().toString());
                    if (fplayer != null) {
                        if (fplayer.getCurrentItem() != null) {
                            ItemStack item = fplayer.getCurrentItem();
                            if (item.getItemMeta().getLore() != null) {
                                if (item.getItemMeta().getLore().equals(FalloutItemFactory.M16().getItemMeta().getLore()) || item.getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore())) {
                                    CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
                                    if (!fplayer.hasAmmo() && meta.hasChargedProjectiles() && fplayer.canReload()) {
                                        int count = 0;
                                        for (ItemStack stack : player.getInventory().getContents()) {
                                            if (stack != null && stack.getType() == Material.ARROW)
                                                count += stack.getAmount();
                                        }
                                        if (count >= fplayer.getMaxAmmo() - 1)
                                            fplayer.reload(fplayer.getMaxAmmo());
                                        else
                                            fplayer.reload(count + 1);
                                        fplayer.createFalloutPlayerScoreboard();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }, 0, 2L);

    }


    @Override
    public void onDisable() {
    }

}
