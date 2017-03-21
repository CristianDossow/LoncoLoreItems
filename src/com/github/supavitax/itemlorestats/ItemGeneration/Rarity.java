 package com.github.supavitax.itemlorestats.ItemGeneration;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 
 public class Rarity
 {
   public String get(double dropChance) {
     String rarity = "&f";
     
     for (int i = 0; i < ItemLoreStats.plugin.getConfig().getConfigurationSection("rarity").getKeys(false).size(); i++) {
       Double rarePercent = Double.valueOf(Double.parseDouble(ItemLoreStats.plugin.getConfig().getConfigurationSection("rarity").getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
       
       if (dropChance <= rarePercent.doubleValue()) {
         rarity = ItemLoreStats.plugin.getConfig().getString("rarity." + rarePercent.intValue() + ".colour");
       }
     }
     
     return rarity;
   }
 }
