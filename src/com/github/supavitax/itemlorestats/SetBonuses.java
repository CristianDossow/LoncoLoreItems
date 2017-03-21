 package com.github.supavitax.itemlorestats;
 
 import java.io.File;
 import java.util.HashMap;
 import java.util.Set;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.Player;
 
 public class SetBonuses
 {
   private FileConfiguration PlayerDataConfig;
   GearStats gearStats = new GearStats();
   com.github.supavitax.itemlorestats.Util.Util_Colours util_Colours = new com.github.supavitax.itemlorestats.Util.Util_Colours();
   
   public void updateSetBonus(Player player) {
     try {
       this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
       this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + "SetBonuses.yml"));
       
       String gearArmour = "";
       String gearDodge = "";
       String gearBlock = "";
       String gearCritChance = "";
       String gearCritDamage = "";
       String gearDamage = "";
       String gearHealth = "";
       String gearHealthRegen = "";
       String gearLifeSteal = "";
       String gearReflect = "";
       String gearFire = "";
       String gearIce = "";
       String gearPoison = "";
       String gearWither = "";
       String gearHarming = "";
       String gearBlind = "";
       String gearXPMultiplier = "";
       String gearSpeed = "";
       
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".armour", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".dodge", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".block", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".damage", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critChance", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critDamage", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".health", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".healthRegen", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".lifeSteal", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".reflect", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".fire", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".ice", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".poison", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".wither", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".harming", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".blind", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".xpMultiplier", Double.valueOf(0.0D));
       ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".speed", Double.valueOf(0.0D));
       
       int a = this.PlayerDataConfig.getKeys(false).size() - 1;
       do { if (a < 0) break;
	              int x = 0;
         String key = this.PlayerDataConfig.getKeys(false).toString().split(",")[a].replaceAll("\\[", "").replaceAll("\\]", "").trim();
         
 
         if (key != null)
         {
 
           for (int b = this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).size() - 1; (b >= 0) && (b <= this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).size() - 1); b--)
           {
             if (this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).toString().split(",")[b].replaceAll("\\[", "").replaceAll("\\]", "").trim() != null) {
               String keyFromParentKey = this.PlayerDataConfig.getConfigurationSection(key).getKeys(false).toString().split(",")[b].replaceAll("\\[", "").replaceAll("\\]", "").trim();
               
               gearArmour = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".armour");
               gearDodge = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".dodge");
               gearBlock = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".block");
               gearCritChance = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".critChance");
               gearCritDamage = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".critDamage");
               gearDamage = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".damage");
               gearHealth = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".health");
               gearHealthRegen = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".healthRegen");
               gearLifeSteal = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".lifeSteal");
               gearReflect = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".reflect");
               gearFire = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".fire");
               gearIce = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".ice");
               gearPoison = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".poison");
               gearWither = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".wither");
               gearHarming = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".harming");
               gearBlind = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".blind");
               gearXPMultiplier = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".xpMultiplier");
               gearSpeed = this.PlayerDataConfig.getString(key + "." + keyFromParentKey + ".movementSpeed");
               
               if (x >= Integer.parseInt(keyFromParentKey)) {
                 if (gearArmour != null) {
                   player.sendMessage("armour hashmap set to " + Integer.parseInt(gearArmour));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".armour", Double.valueOf(Double.parseDouble(gearArmour)));
                 } else {
                   player.sendMessage("armour hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".armour", Double.valueOf(0.0D));
                 }
                 
                 if (gearDodge != null) {
                   player.sendMessage("dodge hashmap set to " + Integer.parseInt(gearDodge));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".dodge", Double.valueOf(Double.parseDouble(gearDodge)));
                 } else {
                   player.sendMessage("dodge hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".dodge", Double.valueOf(0.0D));
                 }
                 
                 if (gearBlock != null) {
                   player.sendMessage("block hashmap set to " + Integer.parseInt(gearBlock));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".block", Double.valueOf(Double.parseDouble(gearBlock)));
                 } else {
                   player.sendMessage("block hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".block", Double.valueOf(0.0D));
                 }
                 
                 if (gearDamage != null) {
                   player.sendMessage("damage hashmap set to " + Integer.parseInt(gearDamage));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".damage", Double.valueOf(Double.parseDouble(gearDamage)));
                 } else {
                   player.sendMessage("damage hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".damage", Double.valueOf(0.0D));
                 }
                 
                 if (gearCritChance != null) {
                   player.sendMessage("critchance hashmap set to " + Integer.parseInt(gearCritChance));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critChance", Double.valueOf(Double.parseDouble(gearCritChance)));
                 } else {
                   player.sendMessage("critchance hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critChance", Double.valueOf(0.0D));
                 }
                 
                 if (gearCritDamage != null) {
                   player.sendMessage("critdamage hashmap set to " + Integer.parseInt(gearCritDamage));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critDamage", Double.valueOf(Double.parseDouble(gearCritDamage)));
                 } else {
                   player.sendMessage("critdamage hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".critDamage", Double.valueOf(0.0D));
                 }
                 
                 if (gearHealth != null) {
                   player.sendMessage("health hashmap set to " + Integer.parseInt(gearHealth));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".health", Double.valueOf(Double.parseDouble(gearHealth)));
                 }
                 else {
                   player.sendMessage("health hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".health", Double.valueOf(0.0D));
                 }
                 
                 if (gearHealthRegen != null) {
                   player.sendMessage("health regen hashmap set to " + Integer.parseInt(gearHealthRegen));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".healthRegen", Double.valueOf(Double.parseDouble(gearHealthRegen)));
                 }
                 else {
                   player.sendMessage("health regen hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".healthRegen", Double.valueOf(0.0D));
                 }
                 
                 if (gearLifeSteal != null) {
                   player.sendMessage("lifesteal hashmap set to " + Integer.parseInt(gearLifeSteal));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".lifeSteal", Double.valueOf(Double.parseDouble(gearLifeSteal)));
                 } else {
                   player.sendMessage("lifesteal hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".lifeSteal", Double.valueOf(0.0D));
                 }
                 
                 if (gearReflect != null) {
                   player.sendMessage("reflect hashmap set to " + Integer.parseInt(gearReflect));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".reflect", Double.valueOf(Double.parseDouble(gearReflect)));
                 } else {
                   player.sendMessage("reflect hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".reflect", Double.valueOf(0.0D));
                 }
                 
                 if (gearFire != null) {
                   player.sendMessage("fire hashmap set to " + Integer.parseInt(gearFire));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".fire", Double.valueOf(Double.parseDouble(gearFire)));
                 } else {
                   player.sendMessage("fire hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".fire", Double.valueOf(0.0D));
                 }
                 
                 if (gearIce != null) {
                   player.sendMessage("ice hashmap set to " + Integer.parseInt(gearIce));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".ice", Double.valueOf(Double.parseDouble(gearIce)));
                 } else {
                   player.sendMessage("ice hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".ice", Double.valueOf(0.0D));
                 }
                 
                 if (gearPoison != null) {
                   player.sendMessage("poison hashmap set to " + Integer.parseInt(gearPoison));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".poison", Double.valueOf(Double.parseDouble(gearPoison)));
                 } else {
                   player.sendMessage("poison hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".poison", Double.valueOf(0.0D));
                 }
                 
                 if (gearWither != null) {
                   player.sendMessage("wither hashmap set to " + Integer.parseInt(gearWither));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".wither", Double.valueOf(Double.parseDouble(gearWither)));
                 } else {
                   player.sendMessage("wither hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".wither", Double.valueOf(0.0D));
                 }
                 
                 if (gearHarming != null) {
                   player.sendMessage("harming hashmap set to " + Integer.parseInt(gearHarming));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".harming", Double.valueOf(Double.parseDouble(gearHarming)));
                 } else {
                   player.sendMessage("harming hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".harming", Double.valueOf(0.0D));
                 }
                 
                 if (gearBlind != null) {
                   player.sendMessage("blind hashmap set to " + Integer.parseInt(gearBlind));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".blind", Double.valueOf(Double.parseDouble(gearBlind)));
                 } else {
                   player.sendMessage("blind hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".blind", Double.valueOf(0.0D));
                 }
                 
                 if (gearXPMultiplier != null) {
                   player.sendMessage("xpMultiplier hashmap set to " + Integer.parseInt(gearXPMultiplier));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".xpMultiplier", Double.valueOf(Double.parseDouble(gearXPMultiplier)));
                 } else {
                   player.sendMessage("xpMultiplier hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".xpMultiplier", Double.valueOf(0.0D));
                 }
                 
                 if (gearSpeed != null) {
                   player.sendMessage("speed hashmap set to " + Integer.parseInt(gearSpeed));
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".speed", Double.valueOf(Double.parseDouble(gearSpeed)));
                 } else {
                   player.sendMessage("speed hashmap set to 0");
                   ItemLoreStats.plugin.setBonusesModifiers.put(player.getName() + ".speed", Double.valueOf(0.0D));
                 }
               }
             }
           }
         }
         a--; if (a < 0) break; } while (a <= this.PlayerDataConfig.getKeys(false).size() - 1);
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
     }
     catch (Exception e)
     {
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
       e.printStackTrace();
       System.out.println("*********** Failed to load set bonus for " + player.getName() + "! ***********");
     }
   }
   
   public double checkHashMapArmour(Player player) {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".armour") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".armour")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".armour")).doubleValue();
   }
   
   public double checkHashMapDodge(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".dodge") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".dodge")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".dodge")).doubleValue();
   }
   
   public double checkHashMapBlock(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".block") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".block")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".block")).doubleValue();
   }
   
   public double checkHashMapDamage(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".damage") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".damage")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".damage")).doubleValue();
   }
   
   public double checkHashMapCritChance(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critChance") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critChance")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critChance")).doubleValue();
   }
   
   public double checkHashMapCritDamage(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critDamage") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critDamage")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".critDamage")).doubleValue();
   }
   
   public double checkHashMapHealth(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".health") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".health")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".health")).doubleValue();
   }
   
   public double checkHashMapHealthRegen(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".healthRegen") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".healthRegen")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".healthRegen")).doubleValue();
   }
   
   public double checkHashMapLifeSteal(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".lifeSteal") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".lifeSteal")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".lifeSteal")).doubleValue();
   }
   
   public double checkHashMapReflect(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".reflect") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".reflect")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".reflect")).doubleValue();
   }
   
   public double checkHashMapFire(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".fire") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".fire")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".fire")).doubleValue();
   }
   
   public double checkHashMapIce(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".ice") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".ice")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".ice")).doubleValue();
   }
   
   public double checkHashMapPoison(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".poison") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".poison")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".poison")).doubleValue();
   }
   
   public double checkHashMapWither(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".wither") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".wither")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".wither")).doubleValue();
   }
   
   public double checkHashMapHarming(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".harming") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".harming")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".harming")).doubleValue();
   }
   
   public double checkHashMapBlind(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".blind") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".blind")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".blind")).doubleValue();
   }
   
   public double checkHashMapXPMultiplier(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".xpMultiplier") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".xpMultiplier")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".xpMultiplier")).doubleValue();
   }
   
   public double checkHashMapSpeed(Player player)
   {
     if (ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".speed") != null) {
       return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".speed")).doubleValue();
     }
     ItemLoreStats.plugin.setBonuses.updateSetBonus(player);
     return ((Double)ItemLoreStats.plugin.setBonusesModifiers.get(player.getName() + ".speed")).doubleValue();
   }
 }

