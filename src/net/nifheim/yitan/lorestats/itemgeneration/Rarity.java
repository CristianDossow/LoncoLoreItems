package net.nifheim.yitan.lorestats.itemgeneration;

import net.nifheim.yitan.lorestats.Main;

public class Rarity {

    public String get(double dropChance) {
        String rarity = "&f";

        for (int i = 0; i < Main.getInstance().getConfig().getConfigurationSection("rarity").getKeys(false).size(); i++) {
            Double rarePercent = Double.parseDouble(Main.getInstance().getConfig().getConfigurationSection("rarity").getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim());

            if (dropChance <= rarePercent) {
                rarity = Main.getInstance().getConfig().getString("rarity." + rarePercent.intValue() + ".colour");
            }
        }

        return rarity;
    }
}
