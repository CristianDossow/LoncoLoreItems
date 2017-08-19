package net.nifheim.beelzebu.rpgcore.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nifheim.beelzebu.rpgcore.characters.branch.BranchType;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class DataManager {

    private final Main plugin;
    private final Player player;
    private final File dataFolder;
    private final File dataFile;
    private FileConfiguration data;

    public DataManager(Player p) {
        plugin = Main.getInstance();
        player = p;
        dataFolder = new File(plugin.getDataFolder(), "userdata/");
        dataFile = new File(plugin.getDataFolder(), "userdata/" + player.getName() + ".yml");
    }

    public FileConfiguration getData() {
        if (!dataFile.exists()) {
            try {
                dataFolder.mkdirs();
                dataFile.createNewFile();
                data = YamlConfiguration.loadConfiguration(dataFile);
            } catch (IOException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }

    public void loadData() {
        getData();
    }

    public boolean isLogged(Player player) {
        return getData().getBoolean("Logged");
    }

    public int getActiveCharacter(Player player) {
        if (isLogged(player)) {
            return getData().getInt("Selected");
        }
        return 0;
    }

    public BranchType getBranch(Player player) {
        return BranchType.valueOf(getActiveCharacter(player) + getData().getString("Branch"));
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public File getFile() {
        return dataFile;
    }
}
