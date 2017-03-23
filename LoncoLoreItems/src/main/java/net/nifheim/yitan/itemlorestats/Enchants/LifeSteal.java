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
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 
 public class LifeSteal
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
   
   public void lifeStealChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, double weaponDamage, boolean isTool) {
     if (this.gearStats.getLifeStealGear(getAttacker) + this.gearStats.getLifeStealItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getLifeStealItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)) <= 0.0D) { return;
     }
     if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".lif", ItemLoreStats.plugin.getConfig().getInt("secondaryStats.lifeSteal.internalCooldown"))) {
       if ((getAttacker instanceof Player)) {
         ItemLoreStats.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".lif", Long.valueOf(System.currentTimeMillis()));
       }
       
       double lifeStealHeal = 0.0D;
       double lifeStealPercent = 0.0D;
       
 
 
 
 
 
 
 
       if (isTool) {
         lifeStealPercent = this.util_Format.format(this.gearStats.getLifeStealGear(getAttacker) + this.gearStats.getLifeStealItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getLifeStealItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)));
       } else {
         lifeStealPercent = this.util_Format.format(this.gearStats.getLifeStealGear(getAttacker));
       }
       
       if (lifeStealPercent > 100.0D) {
         lifeStealPercent = 100.0D;
       }
       
       if (this.util_Random.random(100) <= lifeStealPercent) {
         lifeStealHeal = ItemLoreStats.plugin.getConfig().getDouble("secondaryStats.lifeSteal.healPercentage") * weaponDamage;
         
         if (((getAttacker instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.lifeSteal"))) {
           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.LifeStealSuccess", getAttacker, getDefender, String.valueOf((int)lifeStealHeal), String.valueOf((int)lifeStealHeal)));
         }
         
         if (((getDefender instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.enemyLifeSteal"))) {
           if ((getAttacker instanceof Player)) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int)lifeStealHeal), String.valueOf((int)lifeStealHeal)));
           } else if ((getAttacker instanceof LivingEntity)) {
             if (getAttacker.getCustomName() != null) {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int)lifeStealHeal), String.valueOf((int)lifeStealHeal)));
             } else {
               ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int)lifeStealHeal), String.valueOf((int)lifeStealHeal)));
             }
           }
         }
         
 
         if (this.util_EntityManager.returnEntityCurrentHealth(getAttacker) < this.util_EntityManager.returnEntityMaxHealth(getAttacker))
         {
           if (lifeStealHeal > Math.abs(this.util_EntityManager.returnEntityCurrentHealth(getAttacker) - this.util_EntityManager.returnEntityMaxHealth(getAttacker))) {
             double getRemainingHealth = Math.abs(this.util_EntityManager.returnEntityCurrentHealth(getAttacker) - this.util_EntityManager.returnEntityMaxHealth(getAttacker));
             this.util_EntityManager.setEntityCurrentHealth(getAttacker, this.util_EntityManager.returnEntityCurrentHealth(getAttacker) + getRemainingHealth);
           } else {
             this.util_EntityManager.setEntityCurrentHealth(getAttacker, this.util_EntityManager.returnEntityCurrentHealth(getAttacker) + lifeStealHeal);
           }
         }
         
         if ((getAttacker instanceof Player)) {
           ItemLoreStats.plugin.updateBarAPI((Player)getAttacker);
         }
         
         if ((getDefender instanceof Player)) {
           ItemLoreStats.plugin.updateBarAPI((Player)getDefender);
         }
       }
     }
   }
 }
