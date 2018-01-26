package net.nifheim.yitan.lorestats.itemgeneration;

import java.util.List;
import java.util.logging.Level;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import org.bukkit.configuration.file.FileConfiguration;

public class Prefix {

    Util_Random random = new Util_Random();

    public String get(FileConfiguration configFile, String entity, String dropChance) {
        if (configFile.getString(dropChance + ".prefix").equalsIgnoreCase("Random")) {
            List<String> getListPrefix = Main.getInstance().getConfig().getStringList("prefix.random");

            String selectPrefix = (String) getListPrefix.get(this.random.random(getListPrefix.size()) - 1);

            return selectPrefix;
        }
        if (!configFile.getString(dropChance + ".prefix").equalsIgnoreCase("Stat")) {

            return configFile.getString(dropChance + ".prefix");
        }

        Main.getInstance().getLogger().log(Level.SEVERE, "Unable to load prefix for {0}", configFile.getName());
        return "Error";
    }
}
