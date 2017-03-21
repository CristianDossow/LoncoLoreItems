 package com.github.supavitax.itemlorestats.ItemGeneration;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.Util.Util_Colours;
 import com.github.supavitax.itemlorestats.Util.Util_Random;
 import java.util.List;
 import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 
 public class RandomLore
 {
   Util_Random random = new Util_Random();
   Util_Colours util_Colours = new Util_Colours();
   
   public String get(Material getMaterial) {
     String material = getMaterial.toString();
     
 
     if ((material.contains("_SWORD")) || (material.contains("_AXE")) || (material.contains("_HOE")) || (material.contains("_SPADE")) || (material.contains("_PICKAXE"))) {
       List<String> getRandomLore = ItemLoreStats.getPlugin().getConfig().getStringList("randomLore.tools." + material.split("_")[0].trim().toLowerCase());
       String selectRandomLore = (String)getRandomLore.get(this.random.random(getRandomLore.size()) - 1);
       String materialType = material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase();
       
       return this.util_Colours.replaceTooltipColour(selectRandomLore.replace("{item}", materialType.substring(0, 1).toUpperCase() + materialType.substring(1).toLowerCase()));
     }
     if ((material.contains("_HELMET")) || (material.contains("_CHESTPLATE")) || (material.contains("_LEGGINGS")) || (material.contains("_BOOTS"))) {
       List<String> getRandomLore = ItemLoreStats.getPlugin().getConfig().getStringList("randomLore.armour." + material.split("_")[0].trim().toLowerCase());
       String selectRandomLore = (String)getRandomLore.get(this.random.random(getRandomLore.size()) - 1);
       String materialType = material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase();
       
       return this.util_Colours.replaceTooltipColour(selectRandomLore.replace("{item}", materialType.substring(0, 1).toUpperCase() + materialType.substring(1).toLowerCase()));
     }
     if ((material.contains("BOW")) || (material.contains("STICK")) || (material.contains("STRING")) || (material.contains("BLAZE_ROD")) || (material.contains("SHEARS")) || (material.contains("BUCKET"))) {
       List<String> getRandomLore = ItemLoreStats.getPlugin().getConfig().getStringList("randomLore.tools." + material.trim().toLowerCase());
       String selectRandomLore = (String)getRandomLore.get(this.random.random(getRandomLore.size()) - 1);
       String materialType = material.substring(material.lastIndexOf("_") + 1).trim().toLowerCase();
       
       return this.util_Colours.replaceTooltipColour(selectRandomLore.replace("{item}", materialType.substring(0, 1).toUpperCase() + materialType.substring(1).toLowerCase()));
     }
     
     return "";
   }
 }
