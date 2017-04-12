package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private Main plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player playerFinal = event.getPlayer();
        
        PlayerStats ps = new PlayerStats(playerFinal);
        ps.UpdateAll();
        plugin.playersStats.put(playerFinal.getUniqueId(), ps);
        
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            if (!new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                try {
                    plugin.PlayerDataConfig = new YamlConfiguration();

                    plugin.updateHealth(playerFinal);

                    plugin.PlayerDataConfig.set("extra.logoutHealth", 20.0D);
                    plugin.PlayerDataConfig.set("extra.maxHealth", 20.0D);
                    plugin.PlayerDataConfig.set("extra.hunger", 20);
                    plugin.PlayerDataConfig.set("extra.xp", 0.0F);
                    plugin.PlayerDataConfig.set("extra.level", 0);
                    plugin.PlayerDataConfig.save(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                } catch (Exception e) {
                    System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when logging in! ***********");
                }
            } else if (new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                try {
                    plugin.PlayerDataConfig = new YamlConfiguration();
                    plugin.PlayerDataConfig.load(new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                    playerFinal.setMaxHealth(plugin.PlayerDataConfig.getDouble("extra.maxHealth"));
                    playerFinal.setHealth(plugin.PlayerDataConfig.getDouble("extra.logoutHealth"));
                    playerFinal.setFoodLevel(plugin.PlayerDataConfig.getInt("extra.hunger"));

                    if (plugin.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                        plugin.combatLogVisible.put(playerFinal.getName(), true);
                    } else {
                        plugin.combatLogVisible.put(playerFinal.getName(), plugin.PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                    }

                    if ((plugin.getConfig().getBoolean("keepXPOnDeath"))) {
                        //playerFinal.setExp((float)ItemLoreStats.plugin.PlayerDataConfig.getDouble("extra.xp"));
                        //playerFinal.setLevel(ItemLoreStats.plugin.PlayerDataConfig.getInt("extra.level"));
                    }

                    plugin.updateHealth(playerFinal);
                } catch (IOException | InvalidConfigurationException e) {
                    System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when logging in! ***********");
                }
            }

            plugin.updateHealth(playerFinal);
            plugin.updatePlayerSpeed(playerFinal);
        }, 5L);
        //plugin.activateEnchant.onJoin(playerFinal);
    }
}
