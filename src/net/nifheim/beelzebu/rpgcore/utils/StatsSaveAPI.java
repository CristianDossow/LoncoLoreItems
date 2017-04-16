package net.nifheim.beelzebu.rpgcore.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class StatsSaveAPI {

    private static final FileConfiguration config = Main.getInstance().getConfig();
    private static final Connection c = MySQL.getConnection();
    private static final String prefix = config.getString("MySQL.Prefix");
    private static PlayerStats ps;

    public static void saveAllStats(Player p) throws SQLException {
        String name = p.getUniqueId().toString();

        Statement check = c.createStatement();
        ResultSet res = check.executeQuery("SELECT * FROM " + prefix + "Characters WHERE uuid ='" + name + "';");
        res.next();

        if (res.getString("uuid") != null) {
            ps = new PlayerStats(p);
            double maxhp = ps.healthMax;
            double hp = ps.healthCurrent;
            double maxmana = ps.manaMax;
            double mana = ps.manaCurrent;
            Statement update = c.createStatement();
            update.executeUpdate("UPDATE " + prefix + "Characters SET "
                    + "maxhp = " + maxhp + ","
                    + "hp = " + hp + ","
                    + "maxmana = " + maxmana + ","
                    + "mana = " + mana + "WHERE uuid = '" + name + "';");
        }
    }

    public static void setAllStats(Player p) throws SQLException {
        String name = p.getUniqueId().toString();

        Statement check = c.createStatement();
        ResultSet res = check.executeQuery("SELECT * FROM " + prefix + "Characters WHERE uuid ='" + name + "';");
        res.next();

        if (res.getString("uuid") != null) {
            ps = new PlayerStats(p);
            double maxhp = res.getDouble("maxhp");
            double hp = res.getDouble("hp");
            double maxmana = res.getDouble("maxmana");
            double mana = res.getDouble("mana");
            ps.setHP(hp);
            ps.setMana(mana);
            ps.setMaxHP(maxhp);
            ps.setMaxMana(maxmana);
        }
    }
}
