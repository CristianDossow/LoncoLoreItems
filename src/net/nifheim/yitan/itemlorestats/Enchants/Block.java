 package net.nifheim.yitan.itemlorestats.Enchants;
 
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.nifheim.yitan.itemlorestats.GearStats;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import net.nifheim.yitan.itemlorestats.SetBonuses;
import net.nifheim.yitan.itemlorestats.Durability.Durability;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_Random;
import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
 
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
   
   public boolean checkBlock(EntityDamageByEntityEvent e ,Entity getAttacker ,Entity getDefender, PlayerStats defenderStats) {
	   if (blockChanceOnHit(getDefender, defenderStats)) {
		   if (((getAttacker instanceof Player))
	               && (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyBlockedAttack"))) {
	           ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlockSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
	       }
	       if (((getDefender instanceof Player))
	               && (Main.plugin.getConfig().getBoolean("combatMessages.incoming.blockAttack"))) {
	           ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.BlockSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
	           ((Player) getDefender).addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SLOW, 30, 1));
	       }
	       
	       e.setDamage(0);
	       e.setCancelled(true);
	       return true;
	   }
	   return false;

   }
   
   public boolean blockChanceOnHit(Entity getDefender, PlayerStats defenderStats) {
	   double blockPercent = 0;
	   if(getDefender instanceof Player && defenderStats !=null){
		   blockPercent = defenderStats.block;
	   }
       
       if (blockPercent > 1) {
         blockPercent = 1;
       }
       
       if (blockPercent >= Math.random()) {
         return true;
       }
       return false;
   }
 }
