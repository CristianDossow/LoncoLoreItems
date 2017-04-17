package net.nifheim.beelzebu.rpgcore.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.nifheim.yitan.itemlorestats.Main;
import static net.nifheim.yitan.itemlorestats.Main.console;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Beelzebu
 */
public class MySQL {

    private final Main plugin;
    private final FileConfiguration mysql;
    private final File mysqlFile;
    private final String host;
    private final int port;
    private final String name;
    private final String user;
    private final String passwd;
    private final String prefix;
    private final int checkdb;
    private static Connection c;

    public MySQL(Main plugin) {
        this.plugin = plugin;
        mysqlFile = new File(plugin.getDataFolder(), "MySQL.yml");
        mysql = YamlConfiguration.loadConfiguration(mysqlFile);
        host = mysql.getString("MySQL.Host");
        port = mysql.getInt("MySQL.Port");
        name = mysql.getString("MySQL.Database");
        user = mysql.getString("MySQL.User");
        passwd = mysql.getString("MySQL.Password");
        prefix = mysql.getString("MySQL.Prefix");
        checkdb = mysql.getInt("MySQL.Connection Interval") * 1200;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }
        try {
            c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?autoReconnect=true", user, passwd);
        } catch (SQLException e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.WARNING, "Something was wrong with the connection, the error code is: " + e.getErrorCode(), e.getCause());
            console.sendMessage(plugin.rep("%prefix% Can't connect to the database, disabling plugin..."));
            Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    public Connection getConnection() {
        return c;
    }

    public void SQLConnection() {
        try {
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

        } catch (SQLException e) {
            Logger.getLogger(MySQL.class.getName()).log(Level.WARNING, "Something was wrong creating the tables, the error code is: " + e.getErrorCode(), e.getCause());
            console.sendMessage(plugin.rep("%prefix% Error with the database, disabling plugin..."));
            Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
        try {
            if (!getConnection().isClosed()) {
                console.sendMessage(plugin.rep("%prefix% Plugin conected sucesful to the MySQL."));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Disconnect() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
        }
    }
}
