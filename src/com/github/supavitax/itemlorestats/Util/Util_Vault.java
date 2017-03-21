 package com.github.supavitax.itemlorestats.Util;
 
 import com.github.supavitax.itemlorestats.GearStats;
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.Repair.Repair;
 import net.milkbowl.vault.economy.Economy;
 import net.milkbowl.vault.economy.EconomyResponse;
 import org.bukkit.Material;
 import org.bukkit.Server;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 
 public class Util_Vault
 {
   GearStats gearStats = new GearStats();
   Repair repair = new Repair();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   public static Economy econ = null;
   ItemLoreStats main;
   
   public Util_Vault(ItemLoreStats instance)
   {
     this.main = instance;
   }
   
   public String getItemInHandName(ItemStack itemStack) {
     if (itemStack.getItemMeta().getDisplayName() != null) {
       return itemStack.getItemMeta().getDisplayName();
     }
     return itemStack.getType().toString().substring(0, 1) + itemStack.getType().toString().substring(1).toLowerCase().replace("_", " ");
   }
   
   public boolean setupEconomy()
   {
     if (ItemLoreStats.plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
       return false;
     }
     
     org.bukkit.plugin.RegisteredServiceProvider<Economy> rsp = ItemLoreStats.plugin.getServer().getServicesManager().getRegistration(Economy.class);
     
     if (rsp == null) {
       return false;
     }
     
     econ = (Economy)rsp.getProvider();
     
     return econ != null;
   }
   
   public void removeMoneyForRepair(Player player, String type, String material)
   {
     setupEconomy();
     
     double getBalance = econ.getBalance(player.getName());
     int currentBalanceToBeDeducted = ItemLoreStats.plugin.getConfig().getInt("durabilityAddedOnEachRepair.repairCurrencyCost." + type + "." + material);
     EconomyResponse r = econ.withdrawPlayer(player.getName(), currentBalanceToBeDeducted);
     
     if (Double.valueOf(getBalance).intValue() >= currentBalanceToBeDeducted) {
       if (r.transactionSuccess()) {
         this.repair.repair(player, type, material);
         player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulCurrency", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), String.valueOf(currentBalanceToBeDeducted)));
       }
     } else {
       player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughMoney", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), String.valueOf(currentBalanceToBeDeducted - Double.valueOf(getBalance).intValue())));
     }
   }
   
 
   public void removeMoneyForSale(Player player, int stackSize)
   {
     setupEconomy();
     
     if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
       int currentBalanceToBeAdded = this.gearStats.getSellValueItemInHand(ItemLoreStats.plugin.itemInMainHand(player)) * stackSize;
       
       if (currentBalanceToBeAdded > 0) {
         EconomyResponse r = econ.depositPlayer(player.getName(), currentBalanceToBeAdded);
         
         if (r.transactionSuccess()) {
           player.sendMessage(this.util_GetResponse.getResponse("SellMessages.SellSuccessful", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), String.valueOf(currentBalanceToBeAdded)));
           player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
         }
       } else {
         player.sendMessage(this.util_GetResponse.getResponse("SellMessages.UnableToSell", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
       }
     } else {
       player.sendMessage(this.util_GetResponse.getResponse("SellMessages.NoItemInHand", player, player, "", ""));
     }
   }
 }

