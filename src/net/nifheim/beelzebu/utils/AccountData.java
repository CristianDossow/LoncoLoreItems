package net.nifheim.beelzebu.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nifheim.beelzebu.characters.AccountStatus;
import net.nifheim.beelzebu.characters.branch.BranchType;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class AccountData {

    private final Main plugin;
    private final Player player;
    private final File dataFolder;
    private final File dataFile;
    private FileConfiguration data;

    public AccountData(Player p) {
        plugin = Main.getInstance();
        player = p;
        dataFolder = new File(plugin.getDataFolder(), "userdata/");
        dataFile = new File(plugin.getDataFolder(), "userdata/" + player.getName() + ".yml");
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getDataFile() {
        if (!dataFile.exists()) {
            try {
                dataFolder.mkdirs();
                dataFile.createNewFile();
                data = YamlConfiguration.loadConfiguration(dataFile);
            }
            catch (IOException ex) {
                Logger.getLogger(AccountData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public void loadData() {
        getDataFile();
    }

    public boolean isLogged() {
        return getDataFile().getBoolean("Logged");
    }

    public AccountStatus getStatus() {
        return AccountStatus.valueOf(getDataFile().getString("Status"));
    }

    public void setStatus(AccountStatus status, int id) {
        getDataFile().set("Status", AccountStatus.LOGGEDIN.toString());
        getDataFile().set("Active", id);
        saveData();
    }

    public int getActiveCharacter() {
        if (isLogged()) {
            return getDataFile().getInt("Selected");
        }
        return 0;
    }

    public int getLastActiveCharacter() {
        return getDataFile().getInt("LastActive");
    }

    public BranchType getBranch() {
        return BranchType.valueOf(getDataFile().getString("Branch"));
    }

    public void saveData() {
        try {
            data.save(dataFile);
        }
        catch (IOException ex) {
            Logger.getLogger(AccountData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getFile() {
        return dataFile;
    }
}
