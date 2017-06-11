package net.nifheim.beelzebu.rpgcore.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class StatsSaveAPI {

    private static final Connection c = MySQL.getConnection();
    private static final FileConfiguration config = Main.getInstance().getConfig();
    private static final String prefix = Main.getInstance().getMySQL().prefix;
    private static PlayerStats ps;

    public static void saveAllStats(Player p) throws SQLException {
        String name = p.getUniqueId().toString();
        ps = new PlayerStats(p);
        double maxhp = ps.healthMax;
        double hp = ps.healthCurrent;
        double maxmana = ps.manaMax;
        double mana = ps.manaCurrent;

        Statement check = c.createStatement();
        ResultSet res = check.executeQuery("SELECT * FROM " + prefix + "Characters WHERE uuid ='" + name + "';");
        res.next();

        if (res.next()) {
            Statement update = c.createStatement();
            update.executeUpdate("UPDATE " + prefix + "Characters SET "
                    + "maxhp = " + maxhp + ","
                    + "hp = " + hp + ","
                    + "maxmana = " + maxmana + ","
                    + "mana = " + mana + "WHERE uuid = '" + name + "';");
        } else {
            Statement update = c.createStatement();
            update.executeQuery("INSERT INTO " + prefix + "Characters VALUES(NULL, '" + name + "', '" + p.getName() + "', " + maxhp + ", " + hp + ", " + maxmana + ", " + mana + ");");
        }
    }

    public static void setAllStats(Player p) throws SQLException {
        String name = p.getUniqueId().toString();

        Statement check = c.createStatement();
        ResultSet res = check.executeQuery("SELECT * FROM " + prefix + "Characters WHERE uuid ='" + name + "';");
        //res.next();

        if (res.next()) {
            ps = new PlayerStats(p);
            double maxhp = res.getDouble("maxhp");
            double hp = res.getDouble("hp");
            double maxmana = res.getDouble("maxmana");
            double mana = res.getDouble("mana");
            ps.setHealthCurrent(hp);
            ps.setManaCurrent(mana);
            ps.setHealthMax(maxhp);
            ps.setManaMax(maxmana);
        }
    }
}
