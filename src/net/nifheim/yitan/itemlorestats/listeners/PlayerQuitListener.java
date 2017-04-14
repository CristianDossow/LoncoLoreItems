package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if ((event.getPlayer() instanceof Player)) {
            Player player = event.getPlayer();

            if (!new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                if (!player.isDead()) {
                    player.setMaxHealth(20.0D);
                    if (player.getHealth() > 20.0D) {
                        player.setHealth(20.0D);
                    }
                } else {
                    player.setMaxHealth(20.0D);
                }
            } else if (new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                try {
                    Main.plugin.PlayerDataConfig = new YamlConfiguration();
                    Main.plugin.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                    Main.plugin.PlayerDataConfig.set("extra.logoutHealth", Math.round(player.getHealth()));
                    Main.plugin.PlayerDataConfig.set("extra.maxHealth", Math.round(player.getMaxHealth()));
                    Main.plugin.PlayerDataConfig.set("extra.hunger", player.getFoodLevel());
                    Main.plugin.PlayerDataConfig.set("extra.xp", player.getExp());
                    Main.plugin.PlayerDataConfig.set("extra.level", player.getLevel());
                    Main.plugin.PlayerDataConfig.set("extra.combatLogVisible", Main.plugin.combatLogVisible.get(player.getName()));
                    Main.plugin.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");

                    if (!player.isDead()) {
                        player.setMaxHealth(20.0D);
                        if (player.getHealth() > 20.0D) {
                            player.setHealth(20.0D);
                        }
                    } else {
                        player.setMaxHealth(20.0D);
                    }
                } catch (IOException | InvalidConfigurationException e) {
                    System.out.println("*********** Failed to save player data for " + player.getName() + " when logging out! ***********");
                }
            }
        }
    }
}
