package net.nifheim.beelzebu.utils;

import java.text.DecimalFormat;
import net.nifheim.yitan.lorestats.Main;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends EZPlaceholderHook {

    private final Main plugin;
    private final DecimalFormat df;

    public PlaceholderAPI(Main plugin) {
        super(plugin, "rpgcore");
        this.df = new DecimalFormat("#.#");
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player p, String str) {
        if (p == null) {
            return "Player is needed!";
        }
        if (str.equals("mana")) {
            return String.valueOf(df.format(plugin.getPlayerStats(p).getManaCurrent()));
        }
        if (str.equals("mana_max")) {
            return String.valueOf(df.format(plugin.getPlayerStats(p).getManaMax()));
        }
        if (str.equals("defense")) {
            return String.valueOf(df.format(plugin.getPlayerStats(p).getPercentArmor() * 100) + "%");

        }
        if (str.equals("damage")) {

            String damageMin = df.format(plugin.getPlayerStats(p).getMinDamage());
            String damageMax = df.format(plugin.getPlayerStats(p).getMaxDamage());
            return String.valueOf(damageMin + "-" + damageMax);
        }
        if (str.equals("magicpower")) {
            return String.valueOf(df.format(plugin.getPlayerStats(p).getMagicPower()));
        }
        if (str.equals("dodge")) {
            return String.valueOf(df.format(plugin.getPlayerStats(p).getDodge() * 100) + "%");
        }
        if (str.equals("movementspeed")) {
            return String.valueOf(df.format(plugin.getPlayerStats(p).getMovementSpeed() * 100) + "%");
        } else {
            return "Invalid Placeholder";
        }
    }
}
