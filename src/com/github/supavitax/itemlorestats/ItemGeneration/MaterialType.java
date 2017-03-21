 package com.github.supavitax.itemlorestats.ItemGeneration;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import java.io.File;
 import java.util.List;
 import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.inventory.ItemStack;
 
 public class MaterialType
 {
   private FileConfiguration PlayerDataConfig;
   
   public String setType(String configFile, ItemStack getMaterial)
   {
     try
     {
       this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
       this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + ItemLoreStats.plugin.getConfig().getString("languageFile") + ".yml"));
       
       String material = getMaterial.getType().toString();
       String type = "";
       
       if ((material.contains("_SWORD")) || (material.contains("_AXE")) || (material.contains("_HOE")) || (material.contains("_SPADE")) || (material.contains("_PICKAXE"))) {
         type = this.PlayerDataConfig.getString("ItemType.Tool." + material.substring(material.lastIndexOf("_") + 1, material.lastIndexOf("_") + 2).trim().toUpperCase() + material.substring(material.lastIndexOf("_") + 2).trim().toLowerCase());
       }
       else if ((material.contains("_HELMET")) || (material.contains("_CHESTPLATE")) || (material.contains("_LEGGINGS")) || (material.contains("_BOOTS"))) {
         type = this.PlayerDataConfig.getString("ItemType.Armour." + material.substring(material.lastIndexOf("_") + 1, material.lastIndexOf("_") + 2).trim().toUpperCase() + material.substring(material.lastIndexOf("_") + 2).trim().toLowerCase());
       }
       else if ((material.contains("BOW")) || (material.contains("STICK")) || (material.contains("STRING")) || (material.contains("BLAZE_ROD")) || (material.contains("SHEARS")) || (material.contains("BUCKET"))) {
         type = this.PlayerDataConfig.getString("ItemType.Tool." + material.substring(material.lastIndexOf("_") + 1, material.lastIndexOf("_") + 2).trim().toUpperCase() + material.substring(material.lastIndexOf("_") + 2).trim().toLowerCase());
 
       }
       else if (ItemLoreStats.plugin.isTool(getMaterial.getType())) {
         for (int i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.tools").size(); i++) {
           if (ItemLoreStats.plugin.getConfig().getList("materials.tools").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
             String customMaterial = ItemLoreStats.plugin.getConfig().getList("materials.tools").get(i).toString().split(":")[1];
             
             if ((customMaterial.contains("_SWORD")) || (customMaterial.contains("_AXE")) || (customMaterial.contains("_HOE")) || (customMaterial.contains("_SPADE")) || (customMaterial.contains("_PICKAXE"))) {
               type = this.PlayerDataConfig.getString("ItemType.Tool." + customMaterial.substring(customMaterial.lastIndexOf("_") + 1, customMaterial.lastIndexOf("_") + 2).trim().toUpperCase() + customMaterial.substring(customMaterial.lastIndexOf("_") + 2).trim().toLowerCase());
             }
             else if ((material.contains("BOW")) || (material.contains("STICK")) || (material.contains("STRING")) || (material.contains("BLAZE_ROD")) || (material.contains("SHEARS")) || (material.contains("BUCKET"))) {
               type = this.PlayerDataConfig.getString("ItemType.Tool." + material.substring(material.lastIndexOf("_") + 1, material.lastIndexOf("_") + 2).trim().toUpperCase() + material.substring(material.lastIndexOf("_") + 2).trim().toLowerCase());
             }
             else {
               type = "Weapon";
             }
             
           }
         }
       } else if (ItemLoreStats.plugin.isHelmet(getMaterial.getType())) {
         for (int i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.helmet").size(); i++) {
           if (ItemLoreStats.plugin.getConfig().getList("materials.helmet").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
             type = this.PlayerDataConfig.getString("ItemType.Armour.Helmet");
           }
           
         }
       } else if (ItemLoreStats.plugin.isChestplate(getMaterial.getType())) {
         for (int i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.chest").size(); i++) {
           if (ItemLoreStats.plugin.getConfig().getList("materials.chest").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
             type = this.PlayerDataConfig.getString("ItemType.Armour.Chestplate");
           }
           
         }
       } else if (ItemLoreStats.plugin.isLeggings(getMaterial.getType())) {
         for (int i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.legs").size(); i++) {
           if (ItemLoreStats.plugin.getConfig().getList("materials.legs").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
             type = this.PlayerDataConfig.getString("ItemType.Armour.Leggings");
           }
           
         }
       } else if (ItemLoreStats.plugin.isBoots(getMaterial.getType())) {
         for (int i = 0; i < ItemLoreStats.plugin.getConfig().getList("materials.boots").size(); i++) {
           if (ItemLoreStats.plugin.getConfig().getList("materials.boots").get(i).toString().split(":")[0].equals(getMaterial.getType().toString())) {
             type = this.PlayerDataConfig.getString("ItemType.Armour.Boots");
           }
         }
       }
       
 
 
       this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
       this.PlayerDataConfig.load(new File(configFile));
       
       return type.replaceAll("_", " ");
     } catch (Exception e) {
       e.printStackTrace();
       ItemLoreStats.plugin.getLogger().log(java.util.logging.Level.SEVERE, "Unable to set item type for " + getMaterial.getType().toString());
     }
     
     return "";
   }
 }
