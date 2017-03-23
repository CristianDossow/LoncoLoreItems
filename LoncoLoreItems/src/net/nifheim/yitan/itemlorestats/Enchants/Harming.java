/*    */ package net.nifheim.yitan.itemlorestats.Enchants;
/*    */ 
/*    */ import net.nifheim.yitan.itemlorestats.Durability.Durability;
/*    */ import net.nifheim.yitan.itemlorestats.GearStats;
/*    */ import net.nifheim.yitan.itemlorestats.ItemLoreStats;
/*    */ import net.nifheim.yitan.itemlorestats.SetBonuses;
/*    */ import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
/*    */ import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
/*    */ import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
/*    */ import net.nifheim.yitan.itemlorestats.Util.Util_Format;
/*    */ import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
/*    */ import net.nifheim.yitan.itemlorestats.Util.Util_Random;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.PigZombie;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.entity.Wither;
/*    */ import org.bukkit.entity.Zombie;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class Harming
/*    */ {
/* 25 */   Durability durability = new Durability();
/* 26 */   GearStats gearStats = new GearStats();
/* 27 */   GetSlots getSlots = new GetSlots();
/* 28 */   InternalCooldown internalCooldown = new InternalCooldown();
/* 29 */   SetBonuses setBonuses = new SetBonuses();
/* 30 */   Util_Colours util_Colours = new Util_Colours();
/* 31 */   Util_EntityManager util_EntityManager = new Util_EntityManager();
/* 32 */   Util_Format util_Format = new Util_Format();
/* 33 */   Util_GetResponse util_GetResponse = new Util_GetResponse();
/* 34 */   Util_Random util_Random = new Util_Random();
/*    */   
/*    */   public void harmingChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
/* 37 */     if (this.gearStats.getHarmingGear(getAttacker) + this.gearStats.getHarmingItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getHarmingItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)) <= 0.0D) { return;
/*    */     }
/* 39 */     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".har", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.internalCooldown"))) {
/* 40 */       if ((getAttacker instanceof Player)) {
/* 41 */         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".har", Long.valueOf(System.currentTimeMillis()));
/*    */       }
/*    */       
/* 44 */       double harmingPercent = 0.0D;
/*    */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 53 */       if (isTool) {
/* 54 */         harmingPercent = this.util_Format.format(this.gearStats.getHarmingGear(getAttacker) + this.gearStats.getHarmingItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getHarmingItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)));
/*    */       } else {
/* 56 */         harmingPercent = this.util_Format.format(this.gearStats.getHarmingGear(getAttacker));
/*    */       }
/*    */       
/* 59 */       if (harmingPercent > 100.0D) {
/* 60 */         harmingPercent = 100.0D;
/*    */       }
/*    */       
/* 63 */       if (this.util_Random.random(100) <= harmingPercent) {
/* 64 */         if (((getAttacker instanceof Player)) && 
/* 65 */           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.harm"))) {
/* 66 */           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.HarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
/*    */         }
/*    */         
/* 69 */         if (((getDefender instanceof Player)) && 
/* 70 */           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyHarm"))) {
/* 71 */           if ((getAttacker instanceof Player)) {
/* 72 */             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
/* 73 */           } else if ((getAttacker instanceof LivingEntity)) {
/* 74 */             if (getAttacker.getCustomName() != null) {
/* 75 */               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
/*    */             } else {
/* 77 */               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
/*    */             }
/*    */           }
/*    */         }
/*    */         
/*    */ 
/* 83 */         if (((getDefender instanceof Wither)) || ((getDefender instanceof Zombie)) || ((getDefender instanceof org.bukkit.entity.Skeleton)) || ((getDefender instanceof PigZombie))) {
/* 84 */           getDefender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectAmplifier")));
/*    */         } else {
/* 86 */           getDefender.addPotionEffect(new PotionEffect(PotionEffectType.HARM, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectDuration") * 20, ItemLoreStats.plugin.getConfig().getInt("secondaryStats.harming.effectAmplifier")));
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\ItemLoreStats (2).jar!\com\github\supavitax\itemlorestats\Enchants\Harming.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */