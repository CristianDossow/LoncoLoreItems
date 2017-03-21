 package com.github.supavitax.itemlorestats.Util;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.ItemUpgrading.ItemUpgrade;
 import com.sucy.skill.SkillAPI;
 import com.sucy.skill.api.event.PlayerLevelUpEvent;
 import com.sucy.skill.api.player.PlayerClass;
 import com.sucy.skill.api.player.PlayerData;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.PlayerInventory;
 
 public class Util_SkillAPI implements org.bukkit.event.Listener
 {
   ItemLoreStats main;
   com.github.supavitax.itemlorestats.GearStats gearStats = new com.github.supavitax.itemlorestats.GearStats();
   ItemUpgrade itemUpgrade = new ItemUpgrade();
   
   public Util_SkillAPI(ItemLoreStats instance) {
     this.main = instance;
   }
   
   public int getSkillAPIBaseHealth(Player player)
   {
     int baseMax = 20;
     
     ItemLoreStats.plugin.getSkillAPI(); if (SkillAPI.getPlayerData(player).getMainClass() != null) {
       ItemLoreStats.plugin.getSkillAPI();baseMax = (int)SkillAPI.getPlayerData(player).getMainClass().getData().getBaseHealth();
     }
     
     return baseMax;
   }
   
   public int getSkillAPIHealthPerLevel(Player player)
   {
     int healthPerLevel = 0;
     
     ItemLoreStats.plugin.getSkillAPI(); if (SkillAPI.getPlayerData(player).getMainClass() != null) {
       ItemLoreStats.plugin.getSkillAPI();healthPerLevel = (int)SkillAPI.getPlayerData(player).getMainClass().getData().getHealthScale();
     }
     
     return healthPerLevel;
   }
   
   public int getSkillAPILevel(Player player)
   {
     int level = 0;
     
     ItemLoreStats.plugin.getSkillAPI(); if (SkillAPI.getPlayerData(player).getMainClass() != null) {
       ItemLoreStats.plugin.getSkillAPI();level = SkillAPI.getPlayerData(player).getMainClass().getLevel();
     }
     
     return level;
   }
   
   public int getSkillAPIExperience(Player player)
   {
     int level = 0;
     
     ItemLoreStats.plugin.getSkillAPI(); if (SkillAPI.getPlayerData(player).getMainClass() != null) {
       ItemLoreStats.plugin.getSkillAPI();level = (int)SkillAPI.getPlayerData(player).getMainClass().getExp();
     }
     
     return level;
   }
   
 
   public String getSkillAPIClass(Player player)
   {
     ItemLoreStats.plugin.getSkillAPI(); if (SkillAPI.getPlayerData(player).getMainClass() != null) {
       ItemLoreStats.plugin.getSkillAPI();return SkillAPI.getPlayerData(player).getMainClass().getData().getName();
     }
     
     return null;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   @org.bukkit.event.EventHandler
   public void onSkillAPIPlayerLevel(PlayerLevelUpEvent event)
   {
     Player player = event.getPlayerData().getPlayer();
     
     if (event.getLevel() <= ItemLoreStats.plugin.getConfig().getInt("levelCap")) {
       if (event.getLevel() > ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player))
       {
         if (player.getInventory().getItemInMainHand() != null) {
           this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInMainHand(), "Main");
         }
         
         if (player.getInventory().getItemInOffHand() != null) {
           this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInOffHand(), "Off");
         }
         
         this.itemUpgrade.increaseItemStatOnHelmet(player);
         this.itemUpgrade.increaseItemStatOnChestplate(player);
         this.itemUpgrade.increaseItemStatOnLeggings(player);
         this.itemUpgrade.increaseItemStatOnBoots(player);
         
         ItemLoreStats.plugin.updateHealth(player);
         ItemLoreStats.plugin.updatePlayerSpeed(player);
       }
     } else {
       player.setLevel(ItemLoreStats.plugin.getConfig().getInt("levelCap"));
       player.setExp(0.0F);
     }
   }
 }
