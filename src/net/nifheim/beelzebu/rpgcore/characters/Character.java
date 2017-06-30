package net.nifheim.beelzebu.rpgcore.characters;

import java.util.UUID;
import net.nifheim.beelzebu.rpgcore.utils.LocationSerializer;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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

    public void saveStats() {

    }
}
