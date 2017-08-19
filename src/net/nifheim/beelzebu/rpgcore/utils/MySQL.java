package net.nifheim.beelzebu.rpgcore.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.nifheim.yitan.lorestats.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MySQL {

    private final FileConfiguration config = Main.getInstance().getConfig();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private final String host = config.getString("MySQL.Host");
    private final int port = config.getInt("MySQL.Port");
    private final String name = config.getString("MySQL.Database");
    private final String user = config.getString("MySQL.User");
    private final String passwd = config.getString("MySQL.Password");
    public final String prefix = config.getString("MySQL.Prefix");
    private final int checkdb = config.getInt("MySQL.Connection Interval") * 1200;
    private static Connection c;

    public static Connection getConnection() {
        return c;
    }

    public void SQLConnection() {
        try {
            Connect();

            if (!getConnection().isClosed()) {
                console.sendMessage(("&8[&cLoncoLoreItems&8]&7 Plugin conected sucesful to the MySQL.").replaceAll("&", "§"));
            }
        } catch (SQLException e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.WARNING, "Something was wrong with the connection, the error code is: {0}", e.getErrorCode());
            Bukkit.getScheduler().cancelTasks(Main.getInstance());
            console.sendMessage(("&8[&cLoncoLoreItems&8]&7 Can't connect to the database, disabling plugin...").replaceAll("&", "§"));
            Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
        }

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            console.sendMessage(("&8[&cLoncoLoreItems&8]&7 Checking the database connection ...").replaceAll("&", "§"));
            if (getConnection() == null) {
                console.sendMessage(("&8[&cLoncoLoreItems&8]&7 The database connection is null, reconnecting ...").replaceAll("&", "§"));
                Reconnect();
            } else {
                console.sendMessage(("&8[&cLoncoLoreItems&8]&7 The connection to the database is still active.").replaceAll("&", "§"));
            }
        }, 0L, checkdb);
    }

    private void Connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }
        c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?autoReconnect=true", user, passwd);
        String createPlayers
                = "CREATE TABLE IF NOT EXISTS `" + prefix + "Players`"
                + "(`uuid` VARCHAR(50) NOT NULL,"
                + "`nick` VARCHAR(50) NOT NULL,"
                + "`characters` VARCHAR(777),"
                + "PRIMARY KEY (`uuid`));";
        String createCharacters
                = "CREATE TABLE IF NOT EXISTS `" + prefix + "Characters`"
                + "(`id` INT NOT NULL AUTO_INCREMENT,"
                + "`uuid` VARCHAR(50) NOT NULL,"
                + "`name` VARCHAR (50) NOT NULL,"
                + "`maxhp` DOUBLE,"
                + "`hp` DOUBLE,"
                + "`maxmana` DOUBLE,"
                + "`mana` DOUBLE,"
                + "`stamina` DOUBLE,"
                + "`strength` DOUBLE,"
                + "`intelligence` DOUBLE,"
                + "PRIMARY KEY (`id`));";

        Statement update = c.createStatement();
        update.execute(createPlayers);
        update.execute(createCharacters);
    }

    public void Reconnect() {
        Disconnect();

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            try {
                Connect();
            } catch (SQLException ex) {
            }
        }, 20L);
    }

    private void Disconnect() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
        }
    }
}
