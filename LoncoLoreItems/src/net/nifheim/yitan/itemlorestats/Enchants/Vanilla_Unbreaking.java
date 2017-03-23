 package net.nifheim.yitan.itemlorestats.Enchants;
 
 import net.nifheim.yitan.itemlorestats.ItemLoreStats;
 import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
 import net.nifheim.yitan.itemlorestats.Util.Util_Random;
 import java.util.Map;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.inventory.EntityEquipment;
 import org.bukkit.inventory.ItemStack;
 
 public class Vanilla_Unbreaking
 {
   Util_EntityManager util_EntityManager = new Util_EntityManager();
   Util_Random util_Random = new Util_Random();
   
   public boolean hasUnbreaking(LivingEntity entity) {
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(entity).getType())) {
       if (entity.getEquipment().getItemInMainHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
         return true;
       }
     } else if ((ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(entity).getType())) && 
       (entity.getEquipment().getItemInOffHand().getEnchantments().containsKey(Enchantment.DURABILITY))) {
       return true;
     }
     
 
     return false;
   }
   
   public boolean armourHasUnbreaking(ItemStack item) {
     if (item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
       return true;
     }
     return false;
   }
   
   public boolean hasUnbreakingMain(ItemStack item) {
     if ((ItemLoreStats.plugin.isTool(item.getType())) && 
       (item.getEnchantments().containsKey(Enchantment.DURABILITY))) {
       return true;
     }
     
     return false;
   }
   
   public int calculateNewDurabilityLoss(int enchantLevel, String durabilityLost) {
     int r = this.util_Random.random(1000);
     
     if (r <= ItemLoreStats.plugin.getConfig().getInt("enchants.unbreaking.levelMultiplier") * enchantLevel * 10) {
       return ItemLoreStats.plugin.getConfig().getInt("environmentalDamage." + durabilityLost + ".durabilityLost");
     }
     
     return 0;
   }
 }
