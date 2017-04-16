package net.nifheim.yitan.itemlorestats.listeners;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nifheim.beelzebu.rpgcore.utils.MySQL;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import org.bukkit.Bukkit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private static final Main plugin = Main.getInstance();
    private final FileConfiguration config = Main.getInstance().getConfig();
    private final FileConfiguration sqlfile = plugin.getMySQLFile();
    private final String prefix = sqlfile.getString("MySQL.Prefix");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player playerFinal = event.getPlayer();

        Main.plugin.getServer().getScheduler().runTaskLaterAsynchronously(Main.plugin, () -> {
            PlayerStats ps = new PlayerStats(playerFinal);
            ps.UpdateAll();
            Main.plugin.playersStats.put(playerFinal.getUniqueId(), ps);
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
                    ps.manaCurrent = Main.plugin.PlayerDataConfig.getDouble("extra.mana");
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
        //plugin.activateEnchant.onJoin(playerFinal);

        // SQL Stats
//        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
//            try {
//                Player p = event.getPlayer();
//                String name;
//                if (config.getBoolean("Online Mode")) {
//                    name = p.getUniqueId().toString();
//                } else {
//                    name = p.getName();
//                }
//
//                Connection c = MySQL.getConnection();
//                Statement check = c.createStatement();
//                ResultSet res = check.executeQuery("SELECT uuid FROM " + prefix + "Data WHERE uuid = '" + name + "';");
//                if (!res.next()) {
//                    Statement update = c.createStatement();
//                    update.executeUpdate("INSERT INTO " + prefix + "Data VALUES ('" + name + "', '" + p.getName() + "', 0, " + System.currentTimeMillis() + ");");
//                } else {
//                    Statement update = c.createStatement();
//                    update.executeUpdate("UPDATE " + prefix + "Data SET nick = " + p.getName() + " WHERE uuid = '" + name + "';");
//                }
//
//            } catch (SQLException ex) {
//                if (ex.getSQLState().equals("closed")) {
//                    Logger.getLogger(PlayerJoinListener.class.getName()).log(Level.WARNING, "Seems that the database connection is closed.");
//                } else {
//                    Logger.getLogger(PlayerJoinListener.class.getName()).log(Level.WARNING, "Something was wrong executing this query, the error code is: " + ex.getErrorCode(), ex.getCause());
//                }
//            }
//        }, 5L);
    }
}
