package net.nifheim.beelzebu.rpgcore.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Beelzebu
 */
public class MySQL {

    private static final FileConfiguration config = Main.getInstance().getConfig();
    final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static final String host = config.getString("MySQL.Host");
    private static final int port = config.getInt("MySQL.Port");
    private static final String name = config.getString("MySQL.Database");
    private static final String user = config.getString("MySQL.User");
    private static final String passwd = config.getString("MySQL.Password");
    private static final String prefix = config.getString("MySQL.Prefix");
    private static Connection c;

    public static Connection getConnection() {
        return c;
    }

    public static void Connect() throws SQLException {
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
                + "(`id` INT NOT NULL,"
                + "`name` VARCHAR (50) NOT NULL,"
                + "`minhp` INT,"
                + "`maxhp` INT,"
                + "`hp` INT,"
                + "`minmana` INT,"
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
