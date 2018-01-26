package net.nifheim.yitan.lorestats;

import java.util.ArrayList;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Classes {

    Util_Colours util_Colours = new Util_Colours();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    GearStats gearStats = new GearStats();

    public boolean checkClasses(Player player, ItemStack item) {
        if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            if (this.gearStats.getClass(item) != null) {
                ArrayList<String> classes = this.gearStats.getClass(item);
                int counter = 0;

                for (int cl = 0; cl < this.gearStats.getClass(item).size(); cl++) {
                    if (player.hasPermission("ils.use." + (String) classes.get(cl))) {
                        counter++;

                        return true;
                    }
                }
            } else if (this.gearStats.getClass(item) == null) {
                return true;
            }
        }

        player.sendMessage(this.util_GetResponse.getResponse("ClassRequirementMessages.NotRequiredClass", null, null, "", ""));
        return false;
    }
}
