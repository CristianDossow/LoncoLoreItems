package net.nifheim.yitan.itemlorestats.ItemGeneration;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.Util.Util_Random;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;

public class Prefix {

    Util_Random random = new Util_Random();

    public String get(FileConfiguration configFile, String entity, String dropChance) {
        if (configFile.getString(dropChance + ".prefix").equalsIgnoreCase("Random")) {
            List<String> getListPrefix = Main.plugin.getConfig().getStringList("prefix.random");

            String selectPrefix = (String) getListPrefix.get(this.random.random(getListPrefix.size()) - 1);

            return selectPrefix;
        }
        if (!configFile.getString(dropChance + ".prefix").equalsIgnoreCase("Stat")) {

            return configFile.getString(dropChance + ".prefix");
        }

        Main.plugin.getLogger().log(Level.SEVERE, "Unable to load prefix for " + configFile.getName());
        return "Error";
    }
}
