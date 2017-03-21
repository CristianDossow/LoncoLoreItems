 package com.github.supavitax.itemlorestats.Enchants;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
 import com.github.supavitax.itemlorestats.Util.Util_Random;
 import java.util.Map;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.inventory.ItemStack;
 
 public class Vanilla_FeatherFalling
 {
   Util_EntityManager util_EntityManager = new Util_EntityManager();
   Util_Random util_Random = new Util_Random();
   
   public boolean hasFeatherFalling(ItemStack boots) {
     if (boots.getEnchantments().containsKey(Enchantment.PROTECTION_FALL)) {
       return true;
     }
     
     return false;
   }
   
   public double calculateNewFallDamage(int enchantLevel, double fallDamage) {
     double value = fallDamage;
     int percentage = enchantLevel * ItemLoreStats.plugin.getConfig().getInt("enchants.featherFalling.levelMultiplier");
     
     value = fallDamage - fallDamage * percentage / 100.0D;
     
     return value;
   }
 }
