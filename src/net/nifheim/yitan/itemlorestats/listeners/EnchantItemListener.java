package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantItemListener implements Listener {

    @EventHandler
    public void enchantTableUse(EnchantItemEvent event) {
        if (Main.plugin.getConfig().getBoolean("keepXPOnDeath")) {
            final Player playerFinal = event.getEnchanter();
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                try {
                    Main.plugin.PlayerDataConfig = new YamlConfiguration();
                    Main.plugin.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                    Main.plugin.PlayerDataConfig.set("extra.xp", playerFinal.getExp());
                    Main.plugin.PlayerDataConfig.set("extra.level", playerFinal.getLevel());
                    Main.plugin.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");

                } catch (IOException | InvalidConfigurationException e) {
                    System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when using the enchanting table! ***********");
                }
            }, 4L);
        }
    }
}
