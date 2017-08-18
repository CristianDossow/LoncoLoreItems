package net.nifheim.beelzebu.rpgcore.characters;

import java.util.UUID;
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
        player.getInventory().setHelmet((data.getItemStack(id + "Inventory.Armor.Helmet")));
        player.getInventory().setChestplate((data.getItemStack(id + "Inventory.Armor.Chestplate")));
        player.getInventory().setLeggings((data.getItemStack(id + "Inventory.Armor.Leggings")));
        player.getInventory().setBoots((data.getItemStack(id + "Inventory.Armor.Boots")));
        player.getInventory().setItemInOffHand((data.getItemStack(id + "Inventory.Armor.Secondary")));
    }

    public void saveInventory() {
        data.set(id + "Inventory.Armor.Helmet", (player.getInventory().getHelmet()));
        data.set(id + "Inventory.Armor.Chestplate", (player.getInventory().getChestplate()));
        data.set(id + "Inventory.Armor.Leggings", (player.getInventory().getLeggings()));
        data.set(id + "Inventory.Armor.Boots", (player.getInventory().getBoots()));
        data.set(id + "Inventory.Armor.Secondary", (player.getInventory().getItemInOffHand()));
        for (int i = 0; i < 36; i++) {
            for (ItemStack is : player.getInventory().getContents()) {
                data.set(id + "Inventory.Content" + i, (is));
            }
        }
    }
}
