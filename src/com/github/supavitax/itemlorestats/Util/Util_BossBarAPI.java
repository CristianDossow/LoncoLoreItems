 package com.github.supavitax.itemlorestats.Util;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import java.text.DecimalFormat;
 import java.text.NumberFormat;
 import java.util.Locale;
 import org.bukkit.ChatColor;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.Player;
 import org.inventivetalent.bossbar.BossBarAPI;
 
 public class Util_BossBarAPI
 {
   Util_Colours util_Colours = new Util_Colours();
   ItemLoreStats main;
   
   public Util_BossBarAPI(ItemLoreStats instance)
   {
     this.main = instance;
   }
   
   private String format(double value) {
     Locale forceLocale = new Locale("en", "UK");
     String decimalPattern = "0";
     
     DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(forceLocale);
     decimalFormat.applyPattern(decimalPattern);
     
     String format = decimalFormat.format(value);
     
     return format;
   }
   
   public void setBarValue(Player player, double currentHealth, double maxHealth) {
     if (player.hasMetadata("NPC")) return;
     if (ItemLoreStats.plugin.getConfig().getBoolean("usingBossBarAPI")) {
       float value = Float.parseFloat(String.valueOf(currentHealth)) / Float.parseFloat(String.valueOf(maxHealth));
       int maxHealthDiv = Double.valueOf(maxHealth).intValue() / 10;
       
       String splitterName = ItemLoreStats.plugin.getConfig().getString("bossBar.splitter");
       String splitterColour = ItemLoreStats.plugin.getConfig().getString("bossBar.colour");
       
       String healthName = ItemLoreStats.plugin.getConfig().getString("bossBar.health.name");
       String healthColour = ItemLoreStats.plugin.getConfig().getString("bossBar.health.colour");
       String healthSplitter = ItemLoreStats.plugin.getConfig().getString("bossBar.health.splitter");
       
       String levelName = ItemLoreStats.plugin.getConfig().getString("bossBar.level.name");
       String levelColour = ItemLoreStats.plugin.getConfig().getString("bossBar.level.colour");
       int level = 0;
       
       String expName = ItemLoreStats.plugin.getConfig().getString("bossBar.exp.name");
       String expColour = ItemLoreStats.plugin.getConfig().getString("bossBar.exp.colour");
       String expSplitter = ItemLoreStats.plugin.getConfig().getString("bossBar.exp.splitter");
       int exp = 0;
       
       String bossBarText = "";
       
       if (currentHealth < maxHealthDiv * 2.6D)
       {
         bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + ChatColor.RED + format(Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + format(Math.round(maxHealth));
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
           } else {
             level = player.getLevel();
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level;
         }
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
           } else {
             exp = Math.round(player.getExp() * player.getExpToLevel());
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp + expSplitter + player.getExpToLevel();
         }
         
         BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
         BossBarAPI.setHealth(player, value * 100.0F);
       } else if (currentHealth < maxHealthDiv * 5.1D)
       {
         bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + ChatColor.YELLOW + format(Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + format(Math.round(maxHealth));
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
           } else {
             level = player.getLevel();
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level;
         }
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
           } else {
             exp = Math.round(player.getExp() * player.getExpToLevel());
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp + expSplitter + player.getExpToLevel();
         }
         
         BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
         BossBarAPI.setHealth(player, value * 100.0F);
       } else if (currentHealth < maxHealthDiv * 7.6D)
       {
         bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + ChatColor.GREEN + format(Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + format(Math.round(maxHealth));
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
           } else {
             level = player.getLevel();
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level;
         }
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
           } else {
             exp = Math.round(player.getExp() * player.getExpToLevel());
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp + expSplitter + player.getExpToLevel();
         }
         
         BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
         BossBarAPI.setHealth(player, value * 100.0F);
       }
       else {
         bossBarText = this.util_Colours.replaceTooltipColour(healthColour) + healthName + " " + format(Math.round(currentHealth)) + this.util_Colours.replaceTooltipColour(healthColour) + healthSplitter + format(Math.round(maxHealth));
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.level.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_Heroes.getHeroLevel(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             level = ItemLoreStats.plugin.util_SkillAPI.getSkillAPILevel(player);
           } else {
             level = player.getLevel();
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(levelColour) + levelName + " " + level;
         }
         
         if (ItemLoreStats.plugin.getConfig().getBoolean("bossBar.exp.enabled"))
         {
           if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_Heroes.getHeroExperience(player);
           } else if (ItemLoreStats.plugin.getHeroes() != null) {
             exp = ItemLoreStats.plugin.util_SkillAPI.getSkillAPIExperience(player);
           } else {
             exp = Math.round(player.getExp() * player.getExpToLevel());
           }
           
           bossBarText = bossBarText + this.util_Colours.replaceTooltipColour(splitterColour) + splitterName + this.util_Colours.replaceTooltipColour(expColour) + expName + " " + exp + expSplitter + player.getExpToLevel();
         }
         
         BossBarAPI.setMessage(player, bossBarText, value * 100.0F);
         BossBarAPI.setHealth(player, value * 100.0F);
       }
     }
   }
   
   public void removeBar(Player player) {
     BossBarAPI.removeBar(player);
   }
 }
