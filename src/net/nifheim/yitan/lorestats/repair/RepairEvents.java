package net.nifheim.yitan.lorestats.repair;

import net.nifheim.yitan.lorestats.Main;

import org.bukkit.Material;

public class RepairEvents implements org.bukkit.event.Listener {

    Repair repair = new Repair();

    public Material getRepairBlock() {
        if (Main.getInstance().getConfig().getString("durabilityAddedOnEachRepair.repairBlock") != null) {
            Material repairBlock = Material.getMaterial(Main.getInstance().getConfig().getString("durabilityAddedOnEachRepair.repairBlock"));
            return repairBlock;
        }
        return Material.WORKBENCH;
    }
}
