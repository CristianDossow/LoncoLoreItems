package net.nifheim.yitan.lorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.lorestats.Main;
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

        if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            final Player playerFinal = player;
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                if (Main.getInstance().getConfig().getBoolean("keepXPOnDeath")) {
                    try {
                        Main.getInstance().PlayerDataConfig = new YamlConfiguration();
                        Main.getInstance().PlayerDataConfig.load(new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                        playerFinal.setExp((float) Main.getInstance().PlayerDataConfig.getDouble("extra.xp"));
                        playerFinal.setLevel(Main.getInstance().PlayerDataConfig.getInt("extra.level"));
                        Main.getInstance().combatLogVisible.put(playerFinal.getName(), Main.getInstance().PlayerDataConfig.getBoolean("extra.combatLogVisible"));
                        Main.getInstance().updateHealth(playerFinal);
                        Main.getInstance().updatePlayerSpeed(playerFinal);
                        Main.getInstance().setBonuses.updateSetBonus(playerFinal);
                        playerFinal.setHealth(playerFinal.getMaxHealth());
                        Main.getInstance().getPlayerStats(playerFinal).UpdateAll();
                    } catch (IOException | InvalidConfigurationException e) {
                        System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when respawning! ***********");
                    }
                } else {
                    Main.getInstance().updateHealth(playerFinal);
                    Main.getInstance().updatePlayerSpeed(playerFinal);
                    Main.getInstance().setBonuses.updateSetBonus(playerFinal);
                    Main.getInstance().getPlayerStats(playerFinal).UpdateAll();
                }
            }, 3L);
        }
    }
}
