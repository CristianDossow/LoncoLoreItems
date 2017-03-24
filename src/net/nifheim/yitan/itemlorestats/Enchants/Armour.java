 package net.nifheim.yitan.itemlorestats.Enchants;
 
 import net.nifheim.yitan.itemlorestats.Durability.Durability;
 import net.nifheim.yitan.itemlorestats.GearStats;
 import net.nifheim.yitan.itemlorestats.ItemLoreStats;
 import net.nifheim.yitan.itemlorestats.SetBonuses;
 import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
 import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
 import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
 import net.nifheim.yitan.itemlorestats.Util.Util_Format;
 import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
 import net.nifheim.yitan.itemlorestats.Util.Util_Random;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.inventory.ItemStack;
 
 public class Armour
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
   
   public double armourChanceOnHit(LivingEntity getDefender)
   {
     double stat = 0.0D;
     
 
 
 
 
 
 
     if (ItemLoreStats.plugin.isTool(this.getSlots.returnItemInMainHand(getDefender).getType())) {
       stat += this.gearStats.getArmourItemInHand(ItemLoreStats.plugin.itemInMainHand(getDefender));
     }
     
     if (ItemLoreStats.plugin.isTool(this.getSlots.returnItemInOffHand(getDefender).getType())) {
       stat += this.gearStats.getArmourItemInHand(ItemLoreStats.plugin.itemInOffHand(getDefender));
     }
     
     stat += this.gearStats.getArmourGear(getDefender);
     
     return this.util_Format.format(stat);
   }
 }
