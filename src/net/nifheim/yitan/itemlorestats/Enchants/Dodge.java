 package net.nifheim.yitan.itemlorestats.Enchants;
 
 import net.nifheim.yitan.itemlorestats.Durability.Durability;
 import net.nifheim.yitan.itemlorestats.GearStats;
 import net.nifheim.yitan.itemlorestats.Main;
 import net.nifheim.yitan.itemlorestats.SetBonuses;
 import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
 import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
 import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
 import net.nifheim.yitan.itemlorestats.Util.Util_Format;
 import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
 import net.nifheim.yitan.itemlorestats.Util.Util_Random;
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
     if (this.gearStats.getDodgeGear(getDefender) + this.gearStats.getDodgeItemInHand(Main.plugin.itemInMainHand(getDefender)) + this.gearStats.getDodgeItemInHand(Main.plugin.itemInOffHand(getDefender)) <= 0.0D) { return false;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getDefender) + ".dod", Main.plugin.getConfig().getInt("secondaryStats.dodge.internalCooldown"))) {
       if ((getDefender instanceof org.bukkit.entity.Player)) {
         Main.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getDefender) + ".dod", Long.valueOf(System.currentTimeMillis()));
       }
       
       double dodgePercent = 0.0D;
       
 
 
 
 
 
 
 
       if (Main.plugin.isTool(this.getSlots.returnItemInMainHand(getDefender).getType())) {
         dodgePercent += this.gearStats.getDodgeItemInHand(Main.plugin.itemInMainHand(getDefender));
       }
       
       if (Main.plugin.isTool(this.getSlots.returnItemInOffHand(getDefender).getType())) {
         dodgePercent += this.gearStats.getDodgeItemInHand(Main.plugin.itemInOffHand(getDefender));
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
