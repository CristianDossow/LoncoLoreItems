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
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.potion.PotionEffect;
 
 public class Blind
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
   
   public void blindChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
     if (this.gearStats.getBlindGear(getAttacker) + this.gearStats.getBlindItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getBlindItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)) <= 0.0D) { return;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".bli", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.blind.internalCooldown"))) {
       if ((getAttacker instanceof Player)) {
         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".bli", Long.valueOf(System.currentTimeMillis()));
       }
       
       double blindPercent = 0.0D;
       
 
 
 
 
 
 
 
       if (isTool) {
         blindPercent = this.util_Format.format(this.gearStats.getBlindGear(getAttacker) + this.gearStats.getBlindItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getBlindItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)));
       } else {
         blindPercent = this.util_Format.format(this.gearStats.getBlindGear(getAttacker));
       }
       
       if (blindPercent > 100.0D) {
         blindPercent = 100.0D;
       }
       
       if (this.util_Random.random(100) <= blindPercent) {
         if (((getAttacker instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.blind"))) {
           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.BlindSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
         }
         
         if (((getDefender instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyBlind"))) {
           if ((getAttacker instanceof Player)) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlindSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
           } else if ((getAttacker instanceof LivingEntity)) {
             if (getAttacker.getCustomName() != null) {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlindSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             } else {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlindSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             }
           }
         }
         
 
         getDefender.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.blind.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.blind.effectAmplifier")));
       }
     }
   }
 }