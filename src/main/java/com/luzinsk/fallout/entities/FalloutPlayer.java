package com.luzinsk.fallout.entities;

import com.luzinsk.fallout.Fallout;
import com.luzinsk.fallout.display.PlayerScoreboardDisplay;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FalloutPlayer {

    private Player player;
    private PlayerScoreboardDisplay board;
    private ItemStack currentItem = null;

    private NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
    private NamespacedKey maxAmmo = new NamespacedKey(Fallout.instance, "maxAmmo");
    private NamespacedKey timer = new NamespacedKey(Fallout.instance, "timer");
    private NamespacedKey maxTimer = new NamespacedKey(Fallout.instance, "maxTimer");

    public FalloutPlayer(Player player) {
        this.player = player;
        board = new PlayerScoreboardDisplay(player);
    }

    public void createFalloutPlayerScoreboard() {
        board.createBoard();
    }


    public void setCurrentItem(ItemStack item) { currentItem = item; }

    public ItemStack getCurrentItem() { return currentItem; }

    public boolean hasAmmo() {
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        int foundValue = container.get(ammo, PersistentDataType.INTEGER);
        return foundValue > 0;

    }

    public int getAmmo() {
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        int foundValue = container.get(ammo, PersistentDataType.INTEGER);
        return foundValue;
    }

    public int getMaxAmmo() {
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        int foundValue = container.get(maxAmmo, PersistentDataType.INTEGER);
        return foundValue;
    }

    public boolean canReload() {
        if (player.getInventory().contains(Material.ARROW))
            return true;
        else
            return false;
    }

    public void reload(int count) {
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(ammo , PersistentDataType.INTEGER)) {
            itemMeta.getPersistentDataContainer().set(ammo, PersistentDataType.INTEGER, count);
            currentItem.setItemMeta(itemMeta);
            player.getInventory().removeItem(new ItemStack(Material.ARROW, count - 1));
        }
    }

    public void setAmmo(int count) {
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(ammo, PersistentDataType.INTEGER)) {
            container.set(ammo, PersistentDataType.INTEGER, count);
        }
        currentItem.setItemMeta(itemMeta);
    }

    public void setTimer(int count) {
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(timer, PersistentDataType.INTEGER)) {
            container.set(timer, PersistentDataType.INTEGER, count);
        }
        currentItem.setItemMeta(itemMeta);
    }

    public int getTimer() {
        ItemMeta itemMeta = currentItem.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(timer, PersistentDataType.INTEGER);
    }

    public int getMaxTimer() {
        ItemMeta itemMeta = currentItem.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(maxTimer, PersistentDataType.INTEGER);
    }


}
