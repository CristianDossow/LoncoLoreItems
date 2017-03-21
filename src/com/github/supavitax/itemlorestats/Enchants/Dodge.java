 package com.github.supavitax.itemlorestats.Enchants;
 
 import com.github.supavitax.itemlorestats.Durability.Durability;
 import com.github.supavitax.itemlorestats.GearStats;
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.SetBonuses;
 import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
 import com.github.supavitax.itemlorestats.Util.Util_Colours;
 import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
 import com.github.supavitax.itemlorestats.Util.Util_Format;
 import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
 import com.github.supavitax.itemlorestats.Util.Util_Random;
 import java.util.HashMap;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.inventory.ItemStack;
 
 public class Dodge
 {
   Durability durability = new Durability();
   GearStats gearStats = new GearStats();
   GetSlots getSlots = new GetSlots();
   InternalCooldown internalCooldown = new InternalCooldown();
   SetBonuses setBonuses = new SetBonuses();
   Util_Colours util_Colours = new Util_Colours();
   Util_EntityManager util_EntityManager = new Util_EntityManager();
   Util_Format util_Format = new Util_Format();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   Util_Random util_Random = new Util_Random();
   
   public boolean dodgeChanceOnHit(LivingEntity getDefender, boolean isTool) {
     if (this.gearStats.getDodgeGear(getDefender) + this.gearStats.getDodgeItemInHand(ItemLoreStats.plugin.itemInMainHand(getDefender)) + this.gearStats.getDodgeItemInHand(ItemLoreStats.plugin.itemInOffHand(getDefender)) <= 0.0D) { return false;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getDefender) + ".dod", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.dodge.internalCooldown"))) {
       if ((getDefender instanceof org.bukkit.entity.Player)) {
         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getDefender) + ".dod", Long.valueOf(System.currentTimeMillis()));
       }
       
       double dodgePercent = 0.0D;
       
 
 
 
 
 
 
 
       if (ItemLoreStats.plugin.isTool(this.getSlots.returnItemInMainHand(getDefender).getType())) {
         dodgePercent += this.gearStats.getDodgeItemInHand(ItemLoreStats.plugin.itemInMainHand(getDefender));
       }
       
       if (ItemLoreStats.plugin.isTool(this.getSlots.returnItemInOffHand(getDefender).getType())) {
         dodgePercent += this.gearStats.getDodgeItemInHand(ItemLoreStats.plugin.itemInOffHand(getDefender));
       }
       
       dodgePercent += this.gearStats.getDodgeGear(getDefender);
       
       if (dodgePercent > 100.0D) {
         dodgePercent = 100.0D;
       }
       
       if (dodgePercent >= this.util_Random.random(100)) {
         return true;
       }
       return false;
     }
     
     return false;
   }
 }
