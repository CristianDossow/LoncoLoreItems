package net.nifheim.yitan.itemlorestats.Repair;

import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.Material;

public class RepairEvents implements org.bukkit.event.Listener {

    Repair repair = new Repair();

    public Material getRepairBlock() {
        if (Main.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairBlock") != null) {
            Material repairBlock = Material.getMaterial(Main.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairBlock"));
            return repairBlock;
        }
        return Material.WORKBENCH;
    }
}
