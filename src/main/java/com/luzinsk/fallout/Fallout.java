package com.luzinsk.fallout;

import com.luzinsk.fallout.commands.*;
import com.luzinsk.fallout.entities.FalloutPlayer;
import com.luzinsk.fallout.factory.FalloutItemFactory;
import com.luzinsk.fallout.listeners.PlayerListener;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import javax.xml.stream.events.Namespace;
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
        Objects.requireNonNull(getCommand("kit")).setExecutor(new KitCommand());
        Objects.requireNonNull(getCommand("awp")).setExecutor(new AwpCommand());
        Objects.requireNonNull(getCommand("m97")).setExecutor(new M97Command());
        Objects.requireNonNull(getCommand("topaz")).setExecutor(new TopazCommand());
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
                                if (item.getItemMeta().getLore().equals(FalloutItemFactory.M4().getItemMeta().getLore()) || item.getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore()) || item.getItemMeta().getLore().equals(FalloutItemFactory.M97().getItemMeta().getLore()) || item.getItemMeta().getLore().equals(FalloutItemFactory.Topaz().getItemMeta().getLore())) {
                                    CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();

                                    if (fplayer.hasAmmo() && !meta.hasChargedProjectiles() && item.getItemMeta().getLore().equals(FalloutItemFactory.M4().getItemMeta().getLore())) {
                                        fplayer.setAmmo(fplayer.getAmmo() - 1);
                                        Location location = player.getLocation();
                                        player.playSound(location, Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 2);
                                        player.playSound(location, Sound.BLOCK_STONE_BREAK, 2, 2);
                                        player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1, 2);
                                        player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1, 2);
                                        player.playSound(location, Sound.ENTITY_EGG_THROW, 1, 2);

                                        if (fplayer.getAmmo() != 0) {
                                            meta = (CrossbowMeta) item.getItemMeta();
                                            meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                                            player.getInventory().getItemInMainHand().setItemMeta(meta);
                                        }
                                        fplayer.createFalloutPlayerScoreboard();
                                    } else if (fplayer.hasAmmo() && !meta.hasChargedProjectiles() && item.getItemMeta().getLore().equals(FalloutItemFactory.Topaz().getItemMeta().getLore())) {
                                        fplayer.setAmmo(fplayer.getAmmo() - 1);
                                        Location location = player.getLocation();
                                        location.add(0, 1.5, 0);
                                        if (fplayer.getAmmo() != 0) {
                                            meta = (CrossbowMeta) item.getItemMeta();
                                            meta.addChargedProjectile(new ItemStack(Material.ARROW, 1));
                                            player.getInventory().getItemInMainHand().setItemMeta(meta);
                                        }
                                        player.playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2, 2);

                                        for (int i = 0; i < 8; i++) {
                                            player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, 2);
                                            player.playSound(location, Sound.ENTITY_BLAZE_HURT, 2, 2);
                                            player.playSound(location, Sound.BLOCK_STONE_BREAK, 2, 2);
                                            player.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 2, 2);
                                            player.playSound(location, Sound.ENTITY_GHAST_SHOOT, 2, 2);
                                            Arrow arrow = player.getWorld().spawnArrow(location, location.getDirection(), 2, 1);
                                            arrow.setShooter(player);
                                            Vector vec = player.getLocation().getDirection();
                                            arrow.setVelocity( new Vector (vec.getX(), vec.getY(),vec.getZ()).add(new Vector (PlayerListener.rand.nextDouble() *.05 - .025, PlayerListener.rand.nextDouble() *.05 - .025, PlayerListener.rand.nextDouble() *.05 - .025)));
                                            arrow.setVelocity(arrow.getVelocity().multiply(6.5));
                                            player.playSound(location, Sound.ENTITY_ARROW_SHOOT, 2, 2);
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

                                        if (item.getItemMeta().getLore().equals(FalloutItemFactory.AWP().getItemMeta().getLore()) || item.getItemMeta().getLore().equals(FalloutItemFactory.M97().getItemMeta().getLore())) {
                                            if (fplayer.getTimer() > 0) {
                                                fplayer.setTimer(fplayer.getTimer() - 2);
                                            }


                                        }
                                }
                            }
                        }
                    }
                }
            }

        }, 0, 2L);

        Bukkit.getLogger().log(Level.INFO, "Fallout finished starting.");
    }


    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "Fallout stopping.");
    }

}
