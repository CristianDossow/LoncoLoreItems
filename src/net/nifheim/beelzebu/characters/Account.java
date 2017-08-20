package net.nifheim.beelzebu.characters;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class Account {

    private final Main plugin = Main.getInstance();
    private final Player player;
    private final FileConfiguration data;

    public Account(Player p) {
        player = p;
        data = plugin.getDataManager(player).getData();
    }

    public AccountStatus getStatus(Player player) {
        return AccountStatus.valueOf(data.getString("Status"));
    }

    public Character loadCharacter(int ID) {
        if (ID == -1) {
            return null;
        } else if (ID > getCharacterLimit(player) - 1) {
            return null;
        } else {
            data.set("Status", AccountStatus.LOGGEDIN);
            data.set("Active", ID);
            return new Character(player, ID);
        }
    }

    public int getCharacterLimit(Player player) {
        return data.getInt("MaxCharacters");
    }

    public int getActiveCharacter() {
        if (AccountStatus.valueOf(data.getString("Status")).equals(AccountStatus.LOGGEDOUT)) {
            return data.getInt("Selected");
        }
        return getLastActiveCharacter();
    }

    public int getLastActiveCharacter() {
        return data.getInt("LastActive");
    }

    public void logout() {
        getLoadedCharacter().saveStats();
        getLoadedCharacter().setLastLocation(player.getLocation());
    }

    public Character getLoadedCharacter() {
        return loadCharacter(getActiveCharacter());
    }
}
