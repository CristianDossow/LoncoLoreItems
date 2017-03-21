 package com.github.supavitax.itemlorestats.Commands;
 
 import com.github.supavitax.itemlorestats.GenerateFromFile;
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.Util.Util_Colours;
 import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 import org.bukkit.plugin.PluginDescriptionFile;
 
 public class Version_Com
 {
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   Util_Colours util_Colours = new Util_Colours();
   
   GenerateFromFile generateFromFile = new GenerateFromFile();
   
   public void onVersionCommand(org.bukkit.command.CommandSender sender, String[] args)
   {
     if (args[0].equalsIgnoreCase("version")) {
       if ((sender instanceof Player)) {
         Player player = (Player)sender;
         player.sendMessage(ChatColor.GOLD + "[ItemLoreStats] " + ChatColor.GREEN + " Currently running version " + ItemLoreStats.plugin.getDescription().getVersion() + ", Created by Supavitax.");
       } else {
         System.out.println("[ItemLoreStats] Currently running version " + ItemLoreStats.plugin.getDescription().getVersion());
       }
     }
   }
 }
