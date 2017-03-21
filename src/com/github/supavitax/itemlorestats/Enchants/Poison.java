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
import org.bukkit.entity.EntityType;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.potion.PotionEffect;
 
 public class Poison
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
   
   public void poisonChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
     if (this.gearStats.getPoisonGear(getAttacker) + this.gearStats.getPoisonItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getPoisonItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)) <= 0.0D) { return;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".poi", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.poison.internalCooldown"))) {
       if ((getAttacker instanceof Player)) {
         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".poi", Long.valueOf(System.currentTimeMillis()));
       }
       
       double poisonPercent = 0.0D;
       
 
 
 
 
 
 
 
       if (isTool) {
         poisonPercent = this.util_Format.format(this.gearStats.getPoisonGear(getAttacker) + this.gearStats.getPoisonItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getPoisonItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)));
       } else {
         poisonPercent = this.util_Format.format(this.gearStats.getPoisonGear(getAttacker));
       }
       
       if (poisonPercent > 100.0D) {
         poisonPercent = 100.0D;
       }
       
       if (this.util_Random.random(100) <= poisonPercent) {
         if (((getAttacker instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.poison"))) {
           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.PoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
         }
         
         if (((getDefender instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyPoison"))) {
           if ((getAttacker instanceof Player)) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyPoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
           } else if ((getAttacker instanceof LivingEntity)) {
             if (getAttacker.getCustomName() != null) {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyPoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             } else {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyPoisonSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             }
           }
         }
         
 		if(getDefender instanceof Player ){
	            int lvl =((Player)getDefender).getLevel();
	            int damage = (int)((double)lvl/5);
	            if(damage < 1)
	            	damage = 1;
	            getDefender.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.POISON, 5 * 20, damage));
                }
                else{
                	int damage = (int)(getDefender.getMaxHealth()/10);
                	if(damage < 1)
                		damage = 1;
                	if(damage > 5)
                		damage = 5;
                	
                	//if((getDefender.getType().equals(EntityType.SKELETON)  || getDefender.getType().equals(EntityType.ZOMBIE) ) && getDefender.isCustomNameVisible())
                	getDefender.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.WITHER, 5 * 20, damage));
                 }
  
         
       }
     }
   }
 }
