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
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 
 public class Fire
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
   
   public void fireChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
     if (this.gearStats.getFireGear(getAttacker) + this.gearStats.getFireItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getFireItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)) <= 0.0D) { return;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".fir", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.fire.internalCooldown"))) {
       if ((getAttacker instanceof Player)) {
         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".fir", Long.valueOf(System.currentTimeMillis()));
       }
       
       double firePercent = 0.0D;
       
 
 
 
 
 
 
 
       if (isTool) {
         firePercent = this.util_Format.format(this.gearStats.getFireGear(getAttacker) + this.gearStats.getFireItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getFireItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)));
       } else {
         firePercent = this.util_Format.format(this.gearStats.getFireGear(getAttacker));
       }
       
       if (firePercent > 100.0D) {
         firePercent = 100.0D;
       }
       
       if (this.util_Random.random(100) <= firePercent) {
         if (((getAttacker instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.fire"))) {
           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.FireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
         }
         
         if (((getDefender instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyFire"))) {
           if ((getAttacker instanceof Player)) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyFireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
           } else if ((getAttacker instanceof LivingEntity)) {
             if (getAttacker.getCustomName() != null) {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyFireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             } else {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyFireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             }
           }
         }
         
 
         getDefender.setFireTicks(ItemLoreStats.plugin.getConfig().getInt("secondaryStats.fire.effectDuration") * 20);
       }
     }
   }
 }
