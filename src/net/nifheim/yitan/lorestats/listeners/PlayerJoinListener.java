package net.nifheim.yitan.lorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Main plugin;

    public PlayerJoinListener(Main main) {
        plugin = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        final Player playerFinal = e.getPlayer();

        // Async tasks
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            // ----------------- NEW DATA
            plugin.getAccount(p);
            plugin.getAccount(p).getData().loadData();
            // ----------------- OLD DATA
            if (!new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                try {

                    Main.getInstance().PlayerDataConfig = new YamlConfiguration();

                    Main.getInstance().updateHealth(playerFinal);

                    Main.getInstance().PlayerDataConfig.set("extra.logoutHealth", 20.0D);
                    Main.getInstance().PlayerDataConfig.set("extra.maxHealth", 20.0D);
                    Main.getInstance().PlayerDataConfig.set("extra.hunger", 20);
                    Main.getInstance().PlayerDataConfig.set("extra.xp", 0.0F);
                    Main.getInstance().PlayerDataConfig.set("extra.level", 0);
                    Main.getInstance().PlayerDataConfig.save(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                } catch (IOException ex) {
                    System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when logging in! ***********");
                }
            } else if (new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                try {
                    Main.getInstance().PlayerDataConfig = new YamlConfiguration();
                    Main.getInstance().PlayerDataConfig.load(new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));
                    plugin.getPlayerStats(playerFinal).manaCurrent = Main.getInstance().PlayerDataConfig.getDouble("extra.mana");
                    playerFinal.setMaxHealth(Main.getInstance().PlayerDataConfig.getDouble("extra.maxHealth"));
                    playerFinal.setHealth(Main.getInstance().PlayerDataConfig.getDouble("extra.logoutHealth"));
                    playerFinal.setFoodLevel(Main.getInstance().PlayerDataConfig.getInt("extra.hunger"));

                    if (Main.getInstance().PlayerDataConfig.get("extra.combatLogVisible") == null) {
                        Main.getInstance().combatLogVisible.put(playerFinal.getName(), true);
                    } else {
                        Main.getInstance().combatLogVisible.put(playerFinal.getName(), Main.getInstance().PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                    }

                    Main.getInstance().updateHealth(playerFinal);
                } catch (IOException | InvalidConfigurationException ex) {
                    System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when logging in! ***********");
                }
            }

            Main.getInstance().updateHealth(playerFinal);
            Main.getInstance().updatePlayerSpeed(playerFinal);
        });

        Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
            plugin.getPlayerStats(playerFinal);
            plugin.getPlayerStats(playerFinal).UpdateAll();
        }, 5L);
    }
}
