package net.nifheim.yitan.lorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import org.bukkit.attribute.Attribute;
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
    public void onPlayerQuit(PlayerQuitEvent e) {
        // Async tasks
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getAccount(e.getPlayer()).logout();
            plugin.removePlayerStats(e.getPlayer().getUniqueId());
        });

        if ((e.getPlayer() instanceof Player)) {
            Player player = e.getPlayer();
            plugin.damagefix.attackCooldownsEnd.remove(player.getUniqueId());
            plugin.damagefix.attackCooldowns.remove(player.getUniqueId());
            if (!new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                if (!player.isDead()) {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                    if (player.getHealth() > 20.0D) {
                        player.setHealth(20.0D);
                    }
                } else {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                }
            } else if (new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                try {
                    PlayerStats ps = plugin.getPlayerStats(player);
                    plugin.PlayerDataConfig = new YamlConfiguration();
                    plugin.PlayerDataConfig.load(new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                    plugin.PlayerDataConfig.set("extra.logoutHealth", Math.round(player.getHealth()));
                    plugin.PlayerDataConfig.set("extra.maxHealth", Math.round(player.getMaxHealth()));
                    plugin.PlayerDataConfig.set("extra.hunger", player.getFoodLevel());
                    plugin.PlayerDataConfig.set("extra.xp", player.getExp());
                    plugin.PlayerDataConfig.set("extra.level", player.getLevel());
                    plugin.PlayerDataConfig.set("extra.mana", ps.manaCurrent);
                    plugin.PlayerDataConfig.set("extra.combatLogVisible", plugin.combatLogVisible.get(player.getName()));
                    plugin.PlayerDataConfig.save(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");

                    if (!player.isDead()) {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                        if (player.getHealth() > 20.0D) {
                            player.setHealth(20.0D);
                        }
                    } else {
                        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                    }
                }
                catch (IOException | InvalidConfigurationException ex) {
                    System.out.println("*********** Failed to save player data for " + player.getName() + " when logging out! ***********");
                }
            }
            plugin.removePlayerStats(e.getPlayer().getUniqueId());
        }
    }
}
