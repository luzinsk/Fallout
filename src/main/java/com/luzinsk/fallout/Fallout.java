package com.luzinsk.fallout;

import com.luzinsk.fallout.commands.AmmoCommand;
import com.luzinsk.fallout.commands.AwpCommand;
import com.luzinsk.fallout.commands.M4Command;
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
import java.util.logging.Level;


public class Fallout extends JavaPlugin {

    public static Fallout instance;

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "Fallout starting.");

        instance = this;

        //Listener
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        //Commands
        Objects.requireNonNull(getCommand("m4")).setExecutor(new M4Command());
        Objects.requireNonNull(getCommand("awp")).setExecutor(new AwpCommand());
        Objects.requireNonNull(getCommand("ammo")).setExecutor(new AmmoCommand());
        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());

        //Sets up falloutPlayers if server is reloaded
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            FalloutPlayer fplayer = new FalloutPlayer(player);
            PlayerListener.falloutPlayers.put(player.getUniqueId().toString(), fplayer);
            fplayer.createFalloutPlayerScoreboard();
        }

        //Checks if a crossbow is ready to be reloaded
        Bukkit.getScheduler().runTaskTimer(this, new Runnable(){

            @Override
            public void run() {


                for(Player player : Bukkit.getServer().getOnlinePlayers()) {

                    FalloutPlayer fplayer = PlayerListener.falloutPlayers.get(player.getUniqueId().toString());
                    if (fplayer != null) {
                        if (fplayer.getCurrentItem() != null) {
                            ItemStack item = fplayer.getCurrentItem();

                            if (item.getItemMeta().getLore() != null) {
                                if (item.getItemMeta().getLore().equals(FalloutItemFactory.M4().getItemMeta().getLore()) || item.getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore())) {
                                    CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();

                                    if (fplayer.hasAmmo() && !meta.hasChargedProjectiles() && item.getItemMeta().getLore().equals(FalloutItemFactory.M4().getItemMeta().getLore())) {
                                        fplayer.setAmmo(fplayer.getAmmo() - 1);
                                        if (fplayer.getAmmo() != 0) {
                                            meta = (CrossbowMeta) item.getItemMeta();
                                            meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                                            player.getInventory().getItemInMainHand().setItemMeta(meta);
                                        }
                                        fplayer.createFalloutPlayerScoreboard();
                                    } else if (!fplayer.hasAmmo() && meta.hasChargedProjectiles() && fplayer.canReload()) {
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

                                    /*if (!fplayer.hasAmmo() && meta.hasChargedProjectiles() && fplayer.canReload()) {
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
                                    } */
                                }
                            }
                        }
                    }
                }
            }

        }, 0, 1L);

        Bukkit.getLogger().log(Level.INFO, "Fallout finished starting.");
    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "Fallout stopping.");
    }

}
