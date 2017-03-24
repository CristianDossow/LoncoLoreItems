 package net.nifheim.yitan.itemlorestats.Commands;
 
 import net.nifheim.yitan.itemlorestats.GenerateFromFile;
 import net.nifheim.yitan.itemlorestats.ItemLoreStats;
 import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
 import java.io.File;
 import java.io.PrintStream;
 import org.bukkit.Bukkit;
 import org.bukkit.Server;
 import org.bukkit.World;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.PlayerInventory;
 
 public class Give_Com
 {
   Util_GetResponse util_GetResponse = new Util_GetResponse();
    net.nifheim.yitan.itemlorestats.Util.Util_Colours util_Colours = new net.nifheim.yitan.itemlorestats.Util.Util_Colours();
   
   GenerateFromFile generateFromFile = new GenerateFromFile();
   
   public void onGiveCommand(org.bukkit.command.CommandSender sender, String[] args)
   {
     if (args[0].equalsIgnoreCase("give")) {
       if ((sender instanceof Player)) {
         Player player = (Player)sender;
         if (player.hasPermission("ils.admin")) {
           if (args.length > 1) {
             if (player.getServer().getPlayer(args[1]) != null) {
               if (args.length > 2)
               {
                 String newItemName = "";
                 String replaceNewItemName = "";
                 
                 for (int i = 0; i < args.length; i++) {
                   if (i >= 3) {
                     newItemName = newItemName + " " + args[i];
                   } else {
                     newItemName = args[i];
                   }
                 }
                 
                 if (newItemName.contains(",")) {
                   replaceNewItemName = newItemName.split(",")[1].trim();
                   newItemName = newItemName.split(",")[0].trim();
                 }
                 
                 if (new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + newItemName + ".yml").exists()) {
                   Player givePlayer = player.getServer().getPlayer(args[1]);
                   
                   if (givePlayer.getInventory().firstEmpty() == -1) {
                     givePlayer.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.InventoryFull", player, player, givePlayer.getName(), givePlayer.getName()));
                     
                     if (!replaceNewItemName.equals("")) {
                       Bukkit.getServer().getWorld(givePlayer.getWorld().getName()).dropItemNaturally(givePlayer.getLocation(), this.generateFromFile.importWeapon(newItemName, replaceNewItemName, player.getName()));
                     } else {
                       Bukkit.getServer().getWorld(givePlayer.getWorld().getName()).dropItemNaturally(givePlayer.getLocation(), this.generateFromFile.importWeapon(newItemName, "noChange", player.getName()));
                     }
                   }
                   else if (!replaceNewItemName.equals("")) {
                     givePlayer.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { this.generateFromFile.importWeapon(newItemName, replaceNewItemName, player.getName()) });
                   } else {
                     givePlayer.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { this.generateFromFile.importWeapon(newItemName, "noChange", player.getName()) });
                   }
                 }
                 else {
                   player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.DoesntExistError", player, player, newItemName, newItemName));
                 }
               } else {
                 player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
               }
             } else {
               player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PlayerNotOnlineError", player, player, args[1], args[1]));
             }
           } else {
             player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.EnterPlayerNameError", null, null, "", ""));
           }
         } else {
           player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
         }
       }
       else if (args.length > 1) {
         if (Bukkit.getServer().getPlayer(args[1]) != null) {
           if (args.length > 2)
           {
             String newItemName = "";
             String replaceNewItemName = "";
             
             for (int i = 0; i < args.length; i++) {
               if (i >= 3) {
                 newItemName = newItemName + " " + args[i];
               } else {
                 newItemName = args[i];
               }
             }
             
             if (newItemName.contains(",")) {
               replaceNewItemName = newItemName.split(",")[1].trim();
             }
             
             if (new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + newItemName + ".yml").exists()) {
               Player givePlayer = Bukkit.getServer().getPlayer(args[1]);
               
               if (givePlayer.getInventory().firstEmpty() == -1) {
                 System.out.println(org.bukkit.ChatColor.stripColor(this.util_GetResponse.getResponse("ErrorMessages.InventoryFull", givePlayer, givePlayer, givePlayer.getName(), givePlayer.getName())));
                 
                 if (!replaceNewItemName.equals("")) {
                   Bukkit.getServer().getWorld(givePlayer.getWorld().getName()).dropItemNaturally(givePlayer.getLocation(), this.generateFromFile.importWeapon(newItemName, replaceNewItemName, givePlayer.getName()));
                 } else {
                   Bukkit.getServer().getWorld(givePlayer.getWorld().getName()).dropItemNaturally(givePlayer.getLocation(), this.generateFromFile.importWeapon(newItemName, "noChange", givePlayer.getName()));
                 }
               }
               else if (newItemName.contains(",")) {
                 System.out.println(givePlayer.getName() + " successfully received " + replaceNewItemName + ".");
                 givePlayer.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { this.generateFromFile.importWeapon(newItemName, replaceNewItemName, givePlayer.getName()) });
               } else {
                 System.out.println(givePlayer.getName() + " successfully received " + newItemName + ".");
                 givePlayer.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { this.generateFromFile.importWeapon(newItemName, "noChange", givePlayer.getName()) });
               }
               
             }
           }
           else
           {
             System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
           }
         }
       }
       else
       {
         System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.EnterPlayerNameError", null, null, "", ""));
       }
     }
   }
 }
