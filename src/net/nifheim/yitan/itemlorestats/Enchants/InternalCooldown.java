 package net.nifheim.yitan.itemlorestats.Enchants;
 
 import net.nifheim.yitan.itemlorestats.Main;
 
 public class InternalCooldown {
   public boolean hasCooldown(String playerName, int getSeconds) {
     if (getSeconds == 0) return false;
     if (Main.plugin.internalCooldowns.get(playerName) != null) {
       if (System.currentTimeMillis() > ((Long)Main.plugin.internalCooldowns.get(playerName)).longValue() + getSeconds * 1000) {
         return false;
       }
       return true;
     }
     
     return false;
   }
 }
