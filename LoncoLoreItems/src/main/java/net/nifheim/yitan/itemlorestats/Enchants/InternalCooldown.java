 package net.nifheim.yitan.itemlorestats.Enchants;
 
 import net.nifheim.yitan.itemlorestats.ItemLoreStats;
 
 public class InternalCooldown {
   public boolean hasCooldown(String playerName, int getSeconds) {
     if (getSeconds == 0) return false;
     if (ItemLoreStats.plugin.internalCooldowns.get(playerName) != null) {
       if (System.currentTimeMillis() > ((Long)ItemLoreStats.plugin.internalCooldowns.get(playerName)).longValue() + getSeconds * 1000) {
         return false;
       }
       return true;
     }
     
     return false;
   }
 }
