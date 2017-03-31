package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void saveExpOnDeath(PlayerDeathEvent event) {
        if (event.getEntity().hasMetadata("NPC")) {
            return;
        }
        if (Main.plugin.getConfig().getBoolean("keepXPOnDeath")) {
            Player player = event.getEntity();
            try {
                Main.plugin.PlayerDataConfig = new YamlConfiguration();
                Main.plugin.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                event.setDroppedExp(0);

                int lvl = player.getLevel();
                if (lvl == 0) {
                    player.setExp(0);
                } else {
                    player.setExp(0);
                    player.setLevel(lvl - 1);
                }

                Main.plugin.PlayerDataConfig.set("extra.xp", player.getExp());
                Main.plugin.PlayerDataConfig.set("extra.level", player.getLevel());
                Main.plugin.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
            } catch (IOException | InvalidConfigurationException e) {
                System.out.println("*********** Failed to save player data for " + player.getName() + " when dying! ***********");
            }
        }
    }
}
