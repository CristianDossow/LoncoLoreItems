package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player playerFinal = event.getPlayer();
        Main.plugin.getServer().getScheduler().runTaskLaterAsynchronously(Main.plugin, () -> {
            if (!new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                try {
                    Main.plugin.PlayerDataConfig = new YamlConfiguration();

                    Main.plugin.updateHealth(playerFinal);

                    Main.plugin.PlayerDataConfig.set("extra.logoutHealth", 20.0D);
                    Main.plugin.PlayerDataConfig.set("extra.maxHealth", 20.0D);
                    Main.plugin.PlayerDataConfig.set("extra.hunger", 20);
                    Main.plugin.PlayerDataConfig.set("extra.xp", 0.0F);
                    Main.plugin.PlayerDataConfig.set("extra.level", 0);
                    Main.plugin.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                } catch (Exception e) {
                    System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when logging in! ***********");
                }
            } else if (new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                try {
                    Main.plugin.PlayerDataConfig = new YamlConfiguration();
                    Main.plugin.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                    playerFinal.setMaxHealth(Main.plugin.PlayerDataConfig.getDouble("extra.maxHealth"));
                    playerFinal.setHealth(Main.plugin.PlayerDataConfig.getDouble("extra.logoutHealth"));
                    playerFinal.setFoodLevel(Main.plugin.PlayerDataConfig.getInt("extra.hunger"));

                    if (Main.plugin.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                        Main.plugin.combatLogVisible.put(playerFinal.getName(), true);
                    } else {
                        Main.plugin.combatLogVisible.put(playerFinal.getName(), Main.plugin.PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                    }

                    if ((Main.plugin.getConfig().getBoolean("keepXPOnDeath"))) {
                        //playerFinal.setExp((float)ItemLoreStats.plugin.PlayerDataConfig.getDouble("extra.xp"));
                        //playerFinal.setLevel(ItemLoreStats.plugin.PlayerDataConfig.getInt("extra.level"));
                    }

                    Main.plugin.updateHealth(playerFinal);
                } catch (IOException | InvalidConfigurationException e) {
                    System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when logging in! ***********");
                }
            }

            Main.plugin.updateHealth(playerFinal);
            Main.plugin.updatePlayerSpeed(playerFinal);
        }, 5L);
    }
}
