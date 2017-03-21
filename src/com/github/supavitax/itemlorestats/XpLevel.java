 package com.github.supavitax.itemlorestats;
 
 import com.github.supavitax.itemlorestats.Util.Util_Colours;
 import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 
 public class XpLevel
 {
   Util_Colours util_Colours = new Util_Colours();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   GearStats gearStats = new GearStats();
   
   public boolean checkXPLevel(Player player, ItemStack item)
   {
     if ((!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) && 
       (this.gearStats.getXPLevelRequirement(player, item) <= player.getLevel())) {
       return true;
     }
     
 
     player.sendMessage(this.util_GetResponse.getResponse("LevelRequirementMessages.LevelTooLow", null, null, "", ""));
     return false;
   }
 }
