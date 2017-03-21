 package com.github.supavitax.itemlorestats.Util;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import java.io.File;
 import java.io.PrintStream;
 import org.bukkit.ChatColor;
 import org.bukkit.Server;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.Player;
 import org.bukkit.metadata.MetadataValue;
 import org.bukkit.plugin.PluginDescriptionFile;
 
 public class Util_GetResponse
 {
   private FileConfiguration PlayerDataConfig;
   Util_Colours util_Colours = new Util_Colours();
   Util_EntityManager util_EntityManager = new Util_EntityManager();
   
   public String getMobLevel(Entity getAttacker) {
     if (getAttacker.hasMetadata("level")) {
       return ((MetadataValue)getAttacker.getMetadata("level").get(0)).asString();
     }
     
     return null;
   }
   
   public String getResponse(String getKeyFromLanguageFile, Entity getAttacker, Entity getDefender, String value1, String value2) {
     try {
       this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
       this.PlayerDataConfig.load(new File(ItemLoreStats.plugin.getDataFolder() + File.separator + ItemLoreStats.plugin.getConfig().getString("languageFile") + ".yml"));
       String dMobLevel;
       if (this.PlayerDataConfig.getInt("FileVersion") >= ItemLoreStats.plugin.getConfig().getInt("fileVersion"))
       {
         if ((getAttacker != null) && (getDefender != null) && (value1 != "") && (value2 != "")) {
           String aMobLevel = getMobLevel(getAttacker);
           dMobLevel = null;
           
           if (getDefender.getType() != org.bukkit.entity.EntityType.PLAYER) {
             dMobLevel = getMobLevel(getDefender);
           } else {
             dMobLevel = null;
           }
           
           String attacker = this.util_EntityManager.returnEntityName(getAttacker);
           String defender = this.util_EntityManager.returnEntityName(getDefender);
           String theValue1 = value1.toString();
           String theValue2 = value2.toString();
           
           String message = this.PlayerDataConfig.getString(getKeyFromLanguageFile);
           
           if (message.contains("{attacker}")) {
             if (aMobLevel != null) {
               attacker = ChatColor.GRAY + "[" + ChatColor.YELLOW + aMobLevel + ChatColor.GRAY + "] " + ChatColor.RESET + attacker;
             }
             
             message = message.replaceAll("\\{attacker\\}", attacker);
           }
           
           if (message.contains("{defender}")) {
             if (dMobLevel != null) {
               defender = ChatColor.GRAY + "[" + ChatColor.YELLOW + dMobLevel + ChatColor.GRAY + "] " + ChatColor.RESET + defender;
             }
             
             message = message.replaceAll("\\{defender\\}", defender);
           }
           
           if (message.contains("{value1}")) {
             if (theValue1.toLowerCase().equals(attacker.toLowerCase())) {
               if (aMobLevel != null) {
                 message = message.replaceAll("\\{value1\\}", ChatColor.GRAY + "[" + ChatColor.YELLOW + aMobLevel + ChatColor.GRAY + "] " + ChatColor.RESET + theValue1);
               } else {
                 message = message.replaceAll("\\{value1\\}", ChatColor.RESET + theValue1);
               }
             } else if (theValue1.toLowerCase().equals(defender.toLowerCase())) {
               if (dMobLevel != null) {
                 message = message.replaceAll("\\{value1\\}", ChatColor.GRAY + "[" + ChatColor.YELLOW + dMobLevel + ChatColor.GRAY + "] " + ChatColor.RESET + theValue1);
               } else {
                 message = message.replaceAll("\\{value1\\}", ChatColor.RESET + theValue1);
               }
             } else {
               message = message.replaceAll("\\{value1\\}", theValue1);
             }
           }
           
           if (message.contains("{value2}")) {
             if (theValue1.toLowerCase().equals(attacker.toLowerCase())) {
               if (aMobLevel != null) {
                 message = message.replaceAll("\\{value2\\}", ChatColor.GRAY + "[" + ChatColor.YELLOW + aMobLevel + ChatColor.GRAY + "] " + ChatColor.RESET + theValue1);
               } else {
                 message = message.replaceAll("\\{value2\\}", ChatColor.RESET + theValue1);
               }
             } else if (theValue2.toLowerCase().equals(defender.toLowerCase())) {
               if (dMobLevel != null) {
                 message = message.replaceAll("\\{value2\\}", ChatColor.GRAY + "[" + ChatColor.YELLOW + dMobLevel + ChatColor.GRAY + "] " + ChatColor.RESET + theValue1);
               } else {
                 message = message.replaceAll("\\{value2\\}", ChatColor.RESET + theValue2);
               }
             } else {
               message = message.replaceAll("\\{value2\\}", theValue2);
             }
           }
           
           return this.util_Colours.replaceTooltipColour(message);
         }
         
 
         return this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(getKeyFromLanguageFile));
       }
       
       for (Player player : org.bukkit.Bukkit.getServer().getOnlinePlayers()) {
         if (player.isOp()) {
           player.sendMessage(ChatColor.GREEN + "[ItemLoreStats]" + ChatColor.RED + " An error has occured when trying to generate a response message from Item Lore Stats. This is usually caused by the language file being out of date. Try deleting the language-en.yml file and restart the server. The language file version is " + this.PlayerDataConfig.getInt("FileVersion") + " and should be at least " + Integer.parseInt(ItemLoreStats.plugin.getDescription().getVersion().replace(".", "")) + ".");
         }
       }
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to load message from language file. Please notify an admin or OP! ***********");
     }
     
     return "*********** Failed to load message from language file. Please notify an admin or OP! ***********";
   }
 }
