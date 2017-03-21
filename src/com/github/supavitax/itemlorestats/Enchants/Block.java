 package com.github.supavitax.itemlorestats.Enchants;
 
 import com.github.supavitax.itemlorestats.Durability.Durability;
import com.github.supavitax.itemlorestats.CharacterSheet;
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

 import org.bukkit.Bukkit;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 
 public class Block
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
   
   public boolean blockChanceOnHit(LivingEntity getDefender, boolean isTool) {
     if (this.gearStats.getBlockGear(getDefender) + this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInMainHand(getDefender)) + this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInOffHand(getDefender)) <= 0.0D) { return false;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getDefender) + ".blo", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.block.internalCooldown"))) {
       if ((getDefender instanceof org.bukkit.entity.Player)) {
         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getDefender) + ".blo", Long.valueOf(System.currentTimeMillis()));
       }
       
       double blockPercent = 0.0D;

       if (isTool) {
         blockPercent = this.util_Format.format(this.gearStats.getBlockGear(getDefender) + this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInMainHand(getDefender)) + this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInMainHand(getDefender)));
			   
                } else {
                 CharacterSheet stats = new CharacterSheet();
         blockPercent = stats.getBlockValuedouble((Player) getDefender);
       }

       if (blockPercent > 100.0D) {
         blockPercent = 100.0D;
       }
       
       if (blockPercent >= this.util_Random.random(100)) {
         return true;
      }
       return false;
     }
     
     return false;
   }
 }
