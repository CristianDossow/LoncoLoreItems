package net.nifheim.yitan.itemlorestats.ItemGeneration;

import net.nifheim.yitan.itemlorestats.Main;

public class Rarity {

    public String get(double dropChance) {
        String rarity = "&f";

        for (int i = 0; i < Main.plugin.getConfig().getConfigurationSection("rarity").getKeys(false).size(); i++) {
            Double rarePercent = Double.valueOf(Double.parseDouble(Main.plugin.getConfig().getConfigurationSection("rarity").getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()));

            if (dropChance <= rarePercent.doubleValue()) {
                rarity = Main.plugin.getConfig().getString("rarity." + rarePercent.intValue() + ".colour");
            }
        }

        return rarity;
    }
}
