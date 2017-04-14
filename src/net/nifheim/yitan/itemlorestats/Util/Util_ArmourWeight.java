package net.nifheim.yitan.itemlorestats.Util;

import net.nifheim.yitan.itemlorestats.GearStats;
import net.nifheim.yitan.itemlorestats.Main;
import com.zettelnet.armorweight.WeightManager;
import org.bukkit.entity.Player;

public class Util_ArmourWeight {

    GearStats gearStats = new GearStats();
    Main main;

    public Util_ArmourWeight(Main instance) {
        this.main = instance;
    }

    public float getPlayerSpeed(Player player) {
        float speed = 0.0F;
        double weight = 0.0D;

        WeightManager weightManager = Main.plugin.getArmourWeight().getWeightManager();
        weight = weightManager.calculateWeight(player);

        speed = weightManager.getPlayerSpeed(weight, 0.2D);

        return speed;
    }
}
