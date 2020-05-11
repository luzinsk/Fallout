package com.luzinsk.fallout.entities;

import com.luzinsk.fallout.Fallout;
import com.luzinsk.fallout.display.PlayerScoreboardDisplay;
import com.luzinsk.fallout.weapons.Weapon;
import com.luzinsk.fallout.weapons.type.None;
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
    private Weapon currentWeap = new None();
    private ItemStack currentItem = null;

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

    public void setCurrentItem(ItemStack item) { currentItem = item; }

    public ItemStack getCurrentItem() { return currentItem; }

    public boolean hasAmmo() {
        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");

        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        int foundValue = container.get(ammo, PersistentDataType.INTEGER);
        if (foundValue > 0)
            return true;

        return false;
    }

    public int getAmmo() {
        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        int foundValue = container.get(ammo, PersistentDataType.INTEGER);
        return foundValue;
    }

    public int getMaxAmmo() {
        NamespacedKey maxAmmo = new NamespacedKey(Fallout.instance, "maxAmmo");
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
        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if(container.has(ammo , PersistentDataType.INTEGER)) {
            itemMeta.getPersistentDataContainer().set(ammo, PersistentDataType.INTEGER, count);
            currentItem.setItemMeta(itemMeta);
            player.getInventory().removeItem(new ItemStack(Material.ARROW, count - 1));
        }
    }

    public void setAmmo(int count) {
        player.sendMessage(getAmmo() + ""); // DEBUGGING

        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(ammo, PersistentDataType.INTEGER)) {
            itemMeta.getPersistentDataContainer().set(ammo, PersistentDataType.INTEGER, count);
            currentItem.setItemMeta(itemMeta);
        }
        player.sendMessage(getAmmo() + ""); // DEBUGGING
    }

    public void decrementAmmo() {
        NamespacedKey ammo = new NamespacedKey(Fallout.instance, "ammo");
        ItemMeta itemMeta = currentItem.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        if (container.has(ammo, PersistentDataType.INTEGER)) {
            int curr = container.get(ammo, PersistentDataType.INTEGER) - 1;
            container.set(ammo, PersistentDataType.INTEGER, curr);
            currentItem.setItemMeta(itemMeta);
        }
    }

}
