package net.nifheim.beelzebu.characters;

import net.nifheim.beelzebu.utils.AccountData;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class Account {

    private final Main plugin = Main.getInstance();
    private final Player player;
    private final AccountData data;
    private Character character;

    public Account(Player p) {
        player = p;
        data = new AccountData(player);
    }

    public AccountData getData() {
        return data;
    }

    public AccountStatus getStatus() {
        return data.getStatus();
    }

    public Character loadCharacter(int ID) {
        if (ID == -1) {
            return null;
        } else if (ID > getCharacterLimit() - 1) {
            return null;
        } else {
            data.setStatus(AccountStatus.LOGGEDIN, ID);
            character = new Character(player, ID);
            character.loadInventory();
            character.loadStats();
            return character;
        }
    }

    public int getCharacterLimit() {
        return data.getDataFile().getInt("MaxAccounts", 3);
    }

    public int getActiveCharacterID() {
        if (data.getStatus().equals(AccountStatus.LOGGEDOUT)) {
            return data.getActiveCharacter();
        }
        return getLastActiveCharacter();
    }

    public int getLastActiveCharacter() {
        return data.getLastActiveCharacter();
    }

    public void logout() {
        getLoadedCharacter().saveStats();
        getLoadedCharacter().saveInventory();
        getLoadedCharacter().setLastLocation(player.getLocation());
        data.setStatus(AccountStatus.LOGGEDOUT, getActiveCharacterID());
    }

    public Character getLoadedCharacter() {
        return character;
    }
}
