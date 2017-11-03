package net.nifheim.beelzebu.characters;

import java.util.UUID;
import net.nifheim.beelzebu.utils.AccountData;
import net.nifheim.beelzebu.utils.ItemSerializer;
import net.nifheim.beelzebu.utils.LocationSerializer;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class Character {

    private final Main plugin = Main.getInstance();
    private final Player player;
    private final PlayerStats playerStats;
    private final UUID uuid;
    private final int id;
    private final AccountData data;

    public Character(Player p, int ID) {
        player = p;
        playerStats = new PlayerStats(player);
        uuid = p.getUniqueId();
        id = ID;
        data = plugin.getAccount(p).getData();
        data.loadData();
    }

    public Character(UUID uid, int ID) {
        player = Bukkit.getPlayer(uid);
        playerStats = new PlayerStats(player);
        uuid = uid;
        id = ID;
        data = plugin.getAccount(player).getData();
        data.loadData();
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
        return LocationSerializer.locationFromString(data.getDataFile().getString(getID() + "Lastlocation"));
    }

    public void setLastLocation(Location location) {
        data.getDataFile().set("Lastlocation", LocationSerializer.locationToString(location));
        data.saveData();
    }

    public PlayerStats getStats() {
        return playerStats;
    }

    public void loadStats() {

    }

    public void saveStats() {

    }

    public void loadInventory() {
        try {
            player.getInventory().setHelmet(ItemSerializer.deserialize(data.getDataFile().getString(id + ".Inventory.Armor.Helmet")));
            player.getInventory().setChestplate(ItemSerializer.deserialize(data.getDataFile().getString(id + ".Inventory.Armor.Chestplate")));
            player.getInventory().setLeggings(ItemSerializer.deserialize(data.getDataFile().getString(id + ".Inventory.Armor.Leggings")));
            player.getInventory().setBoots(ItemSerializer.deserialize(data.getDataFile().getString(id + ".Inventory.Armor.Boots")));
            player.getInventory().setItemInOffHand(ItemSerializer.deserialize(data.getDataFile().getString(id + ".Inventory.Armor.Secondary")));
            for (int i = 0; i < 36; i++) {
                player.getInventory().setItem(i, ItemSerializer.deserialize(data.getDataFile().getString(id + ".Inventory.Content." + i)));
            }
        }
        catch (NullPointerException ex) {
        }
    }

    public void saveInventory() {
        try {
            data.getDataFile().set(id + ".Inventory.Armor.Helmet", ItemSerializer.serialize(player.getInventory().getHelmet()));
            data.getDataFile().set(id + ".Inventory.Armor.Chestplate", ItemSerializer.serialize(player.getInventory().getChestplate()));
            data.getDataFile().set(id + ".Inventory.Armor.Leggings", ItemSerializer.serialize(player.getInventory().getLeggings()));
            data.getDataFile().set(id + ".Inventory.Armor.Boots", ItemSerializer.serialize(player.getInventory().getBoots()));
            data.getDataFile().set(id + ".Inventory.Armor.Secondary", ItemSerializer.serialize(player.getInventory().getItemInOffHand()));
            for (int i = 0; i < 36; i++) {
                data.getDataFile().set(id + ".Inventory.Content." + i, ItemSerializer.serialize(player.getInventory().getContents()[i]));
            }
        }
        catch (NullPointerException ex) {
        }
        data.saveData();
    }
}
