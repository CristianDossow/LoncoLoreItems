package net.nifheim.beelzebu.rpgcore.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 *
 * @author Beelzebu
 */
public class MySQL {

    private static final Main plugin = Main.getInstance();
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static final String host = Main.getMySQLFile().getString("MySQL.Host");
    private static final int port = Main.getMySQLFile().getInt("MySQL.Port");
    private static final String name = Main.getMySQLFile().getString("MySQL.Database");
    private static final String user = Main.getMySQLFile().getString("MySQL.User");
    private static final String passwd = Main.getMySQLFile().getString("MySQL.Password");
    private static final String prefix = Main.getMySQLFile().getString("MySQL.Prefix");
    private static final int checkdb = Main.getMySQLFile().getInt("MySQL.Connection Interval") * 1200;
    private static Connection c;

    public static Connection getConnection() {
        return c;
    }

    public static void SQLConnection() throws SQLException {
        Connect();

        if (!getConnection().isClosed()) {
            console.sendMessage(plugin.rep("%prefix% Plugin conected sucesful to the MySQL."));
        }

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            console.sendMessage(plugin.rep("%prefix% Checking the database connection ..."));
            if (getConnection() == null) {
                console.sendMessage(plugin.rep("%prefix% The database connection is null, reconnecting ..."));
                Reconnect();
            } else {
                console.sendMessage(plugin.rep("%prefix% The connection to the database is still active."));
            }
        }, 0L, checkdb);
    }

    private static void Connect() throws SQLException {
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
                + "`maxhp` INT,"
                + "`hp` INT,"
                + "`maxmana` INT,"
                + "`mana` INT,"
                + "`stamina` INT,"
                + "`strength` INT,"
                + "`intelligence` INT,"
                + "PRIMARY KEY (`id`));";

        Statement update = c.createStatement();
        update.execute(createPlayers);
        update.execute(createCharacters);
    }

    public static void Reconnect() {
        Disconnect();

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            try {
                Connect();
            } catch (SQLException ex) {
            }
        }, 20L);
    }

    public static void Disconnect() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
        }
    }
}
