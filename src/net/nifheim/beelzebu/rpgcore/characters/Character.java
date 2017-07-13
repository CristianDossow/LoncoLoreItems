package net.nifheim.beelzebu.rpgcore.characters;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nifheim.beelzebu.rpgcore.utils.InventorySerializer;
import net.nifheim.beelzebu.rpgcore.utils.LocationSerializer;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Beelzebu
 */
public class Character {

    private final Main plugin = Main.getInstance();
    private final Player player;
    private final UUID uuid;
    private int id = 0;
    private final FileConfiguration data;

    public Character(Player p, int ID) {
        player = p;
        uuid = p.getUniqueId();
        id = ID;
        plugin.getDataManager(player).loadData();
        data = plugin.getDataManager(player).getData();
    }

    public Character(UUID uid, int ID) {
        player = Bukkit.getPlayer(uid);
        uuid = uid;
        id = ID;
        plugin.getDataManager(player).loadData();
        data = plugin.getDataManager(player).getData();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getID() {
        return id;
    }

    public int getLevel() {
        return player.getLevel();
    }

    public Location getLastLocation() {
        return LocationSerializer.locationFromString(data.getString(getID() + "Lastlocation"));
    }

    public void setLastLocation(Location location) {
        data.set("Lastlocation", LocationSerializer.locationToString(location));
        plugin.getDataManager(player).saveData();
    }

    public void loadStats() {

    }

    public void saveStats() {

    }

    public void loadInventory() {
        try {
            player.getInventory().setHelmet(InventorySerializer.deserialize(data.get(id + "Inventory.Armor.Helmet")));
            player.getInventory().setChestplate(InventorySerializer.deserialize(data.get(id + "Inventory.Armor.Chestplate")));
            player.getInventory().setLeggings(InventorySerializer.deserialize(data.get(id + "Inventory.Armor.Leggings")));
            player.getInventory().setBoots(InventorySerializer.deserialize(data.get(id + "Inventory.Armor.Boots")));
            player.getInventory().setItemInOffHand(InventorySerializer.deserialize(data.get(id + "Inventory.Armor.Secondary")));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveInventory() {
        data.set(id + "Inventory.Armor.Helmet", InventorySerializer.serialize(player.getInventory().getHelmet()));
        data.set(id + "Inventory.Armor.Chestplate", InventorySerializer.serialize(player.getInventory().getChestplate()));
        data.set(id + "Inventory.Armor.Leggings", InventorySerializer.serialize(player.getInventory().getLeggings()));
        data.set(id + "Inventory.Armor.Boots", InventorySerializer.serialize(player.getInventory().getBoots()));
        data.set(id + "Inventory.Armor.Secondary", InventorySerializer.serialize(player.getInventory().getItemInOffHand()));
        for (int i = 0; i < 36; i++) {
            for (ItemStack is : player.getInventory().getContents()) {
                data.set(id + "Inventory.Content" + i, InventorySerializer.serialize(is));
            }
        }
    }
}
