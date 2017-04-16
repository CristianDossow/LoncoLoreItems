package net.nifheim.beelzebu.rpgcore.utils;

import net.nifheim.yitan.itemlorestats.Main;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;


public class PlaceholderAPI extends EZPlaceholderHook {
    private final Main plugin;
    public PlaceholderAPI(Main plugin) {
        super(plugin, "rpgcore");
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player p, String str) {
        if (p == null) {
            return "Player is needed!";
        }
        if (str.equals("mana")) {
            return String.valueOf(plugin.getPlayerStats(p).manaCurrent);
        }
        else {
            return "Invalid Placeholder";
        }
    }
}