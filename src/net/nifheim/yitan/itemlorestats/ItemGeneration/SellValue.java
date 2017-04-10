package net.nifheim.yitan.itemlorestats.ItemGeneration;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class SellValue {

    private final FileConfiguration config = Main.getInstance().getConfig();
    public double get(Material getMaterial) {
        String material = getMaterial.toString();

        if ((material.contains("_SWORD")) || (material.contains("_AXE")) || (material.contains("_HOE")) || (material.contains("_SPADE")) || (material.contains("_PICKAXE"))) {
            return config.getDouble("materialSellValue.tools." + material.split("_")[0].trim().toLowerCase() + "." + material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase());
        }
        if ((material.contains("_HELMET")) || (material.contains("_CHESTPLATE")) || (material.contains("_LEGGINGS")) || (material.contains("_BOOTS"))) {
            return config.getDouble("materialSellValue.armour." + material.split("_")[0].trim().toLowerCase() + "." + material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase());
        }
        if ((material.contains("BOW")) || (material.contains("STICK")) || (material.contains("STRING")) || (material.contains("BLAZE_ROD")) || (material.contains("SHEARS")) || (material.contains("BUCKET"))) {
            return config.getDouble("materialSellValue.tools." + material.replaceAll("_", "").toLowerCase());
        }

        return 0.0D;
    }
}
