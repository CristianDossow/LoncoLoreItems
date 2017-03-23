 package net.nifheim.yitan.itemlorestats.Enchants;
 
 import net.nifheim.yitan.itemlorestats.ItemLoreStats;
 import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.inventory.EntityEquipment;
 import org.bukkit.inventory.ItemStack;
 
 public class Vanilla_Sharpness
 {
   Util_EntityManager util_EntityManager = new Util_EntityManager();
   
   public boolean hasSharpness(LivingEntity entity) {
	       
	        if(entity != null)
	        {
	/* 14 */     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(entity).getType())) {
	/* 15 */       if (entity.getEquipment().getItemInMainHand().getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.DAMAGE_ALL)) {
	/* 16 */         return true;
	/*    */       }
	/* 18 */     } else if ((ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(entity).getType())) && 
	/* 19 */       (entity.getEquipment().getItemInOffHand().getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.DAMAGE_ALL))) {
	/* 20 */       return true;
	/*    */     }
     }
 
     return false;
   }
   
   public double calculateNewDamage(double damage, int enchantLevelMain, int enchantLevelOff) {
     double value = damage;
     int levelMain = 0;
     int levelOff = 0;
     
     if (enchantLevelMain != 0) {
       levelMain = enchantLevelMain;
     }
     
     if (enchantLevelOff != 0) {
       levelOff = enchantLevelMain;
     }
     
     int enchantLevel = levelMain + levelOff;
     
     value = damage + value / 100.0D * (ItemLoreStats.plugin.getConfig().getDouble("enchants.sharpness.levelMultiplier") * enchantLevel);
     
     return value;
   }
 }
