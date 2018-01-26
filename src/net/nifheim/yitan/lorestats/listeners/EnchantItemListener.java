package net.nifheim.yitan.lorestats.listeners;

import java.io.File;
import java.io.IOException;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantItemListener implements Listener {

    @EventHandler
    public void enchantTableUse(EnchantItemEvent event) {
        if (Main.getInstance().getConfig().getBoolean("keepXPOnDeath")) {
            final Player playerFinal = event.getEnchanter();
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                try {
                    Main.getInstance().PlayerDataConfig = new YamlConfiguration();
                    Main.getInstance().PlayerDataConfig.load(new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                    Main.getInstance().PlayerDataConfig.set("extra.xp", playerFinal.getExp());
                    Main.getInstance().PlayerDataConfig.set("extra.level", playerFinal.getLevel());
                    Main.getInstance().PlayerDataConfig.save(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");

                } catch (IOException | InvalidConfigurationException e) {
                    System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when using the enchanting table! ***********");
                }
            }, 4L);
        }
    }
}
