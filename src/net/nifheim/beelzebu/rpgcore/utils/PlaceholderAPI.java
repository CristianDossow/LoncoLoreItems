package net.nifheim.beelzebu.rpgcore.utils;

import java.text.DecimalFormat;
import net.nifheim.yitan.itemlorestats.Main;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends EZPlaceholderHook {

    private final Main plugin;

    public PlaceholderAPI(Main plugin) {
        super(plugin, "rpgcore");
        this.plugin = plugin;
    }

    DecimalFormat df = new DecimalFormat("#.#");
    @Override
    public String onPlaceholderRequest(Player p, String str) {
        if (p == null) {
            return "Player is needed!";
        }
        if (str.equals("mana")) {
            return String.valueOf(df(plugin.getPlayerStats(p).manaCurrent));
        }
        if (str.equals("mana_max")) {
            return String.valueOf(df(plugin.getPlayerStats(p).manaMax));
        } else {
            return "Invalid Placeholder";
        }
    }
}
