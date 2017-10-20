package net.nifheim.yitan.lorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Main plugin;

    public PlayerQuitListener(Main main) {
        plugin = main;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Async tasks
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.playersStats.remove(event.getPlayer().getUniqueId());
        });

        if ((event.getPlayer() instanceof Player)) {
            Player player = event.getPlayer();
            Main.getInstance().damagefix.attackCooldownsEnd.remove(player.getUniqueId());
            Main.getInstance().damagefix.attackCooldowns.remove(player.getUniqueId());
            if (!new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                if (!player.isDead()) {
                    player.setMaxHealth(20.0D);
                    if (player.getHealth() > 20.0D) {
                        player.setHealth(20.0D);
                    }
                } else {
                    player.setMaxHealth(20.0D);
                }
            } else if (new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                try {
                    PlayerStats ps = Main.getInstance().getPlayerStats(player);
                    Main.getInstance().PlayerDataConfig = new YamlConfiguration();
                    Main.getInstance().PlayerDataConfig.load(new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                    Main.getInstance().PlayerDataConfig.set("extra.logoutHealth", Math.round(player.getHealth()));
                    Main.getInstance().PlayerDataConfig.set("extra.maxHealth", Math.round(player.getMaxHealth()));
                    Main.getInstance().PlayerDataConfig.set("extra.hunger", player.getFoodLevel());
                    Main.getInstance().PlayerDataConfig.set("extra.xp", player.getExp());
                    Main.getInstance().PlayerDataConfig.set("extra.level", player.getLevel());
                    Main.getInstance().PlayerDataConfig.set("extra.mana", ps.manaCurrent);
                    Main.getInstance().PlayerDataConfig.set("extra.combatLogVisible", Main.getInstance().combatLogVisible.get(player.getName()));
                    Main.getInstance().PlayerDataConfig.save(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");

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
            Main.getInstance().playersStats.remove(player.getUniqueId());
        }
    }
}
