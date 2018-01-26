package net.nifheim.yitan.lorestats;

import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class XpLevel {

    Util_Colours util_Colours = new Util_Colours();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    GearStats gearStats = new GearStats();

    public boolean checkXPLevel(Player player, ItemStack item) {
        if ((!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()))
                && (this.gearStats.getXPLevelRequirement(player, item) <= player.getLevel())) {
            return true;
        }

        player.sendMessage(this.util_GetResponse.getResponse("LevelRequirementMessages.LevelTooLow", null, null, "", ""));
        return false;
    }
}
