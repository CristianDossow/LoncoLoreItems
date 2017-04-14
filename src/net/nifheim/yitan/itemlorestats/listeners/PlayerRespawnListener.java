package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            final Player playerFinal = player;
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                if (Main.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                    try {
                        Main.plugin.PlayerDataConfig = new YamlConfiguration();
                        Main.plugin.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));
                        
                        playerFinal.setExp((float) Main.plugin.PlayerDataConfig.getDouble("extra.xp"));
                        playerFinal.setLevel(Main.plugin.PlayerDataConfig.getInt("extra.level"));
                        Main.plugin.combatLogVisible.put(playerFinal.getName(), Main.plugin.PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                        Main.plugin.updateHealth(playerFinal);
                        Main.plugin.updatePlayerSpeed(playerFinal);
                        Main.plugin.setBonuses.updateSetBonus(playerFinal);
                        playerFinal.setHealth(playerFinal.getMaxHealth());
                    } catch (IOException | InvalidConfigurationException e) {
                        System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when respawning! ***********");
                    }
                } else {
                    Main.plugin.updateHealth(playerFinal);
                    Main.plugin.updatePlayerSpeed(playerFinal);
                    Main.plugin.setBonuses.updateSetBonus(playerFinal);
                }
            }, 3L);
        }
    }
}
