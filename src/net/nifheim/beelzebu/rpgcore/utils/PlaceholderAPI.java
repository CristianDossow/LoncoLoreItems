package net.nifheim.beelzebu.rpgcore.utils;

import net.nifheim.yitan.itemlorestats.Main;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class PlaceholderAPI extends EZPlaceholderHook {
    private Main plugin;
    public PlaceholderAPI(Main plugin) {
        super(plugin, "rpgcore");
        plugin = this.plugin;
    }

    @Override
    public String onPlaceholderRequest(Player p, String str) {
        if (p == null) {
            return "Player is needed!";
        }
        if (str.equals("mana")) {
            
        }
        if (str.equals("")) {
            
        }
        return "Invalid Placeholder";
    }
}
