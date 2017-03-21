 package com.github.supavitax.itemlorestats.Damage;
 import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
 import org.bukkit.Effect;
 import org.bukkit.Material;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.EntityType;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.entity.Projectile;
 import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
 import org.bukkit.event.entity.EntityDamageByEntityEvent;
 import org.bukkit.event.entity.EntityDeathEvent;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.metadata.MetadataValue;
 import org.bukkit.plugin.Plugin;

import com.LoncoCraft.LoncoLoreItems.DamageFix;
import com.LoncoCraft.LoncoLoreItems.EspecialAtributes;
 
 import com.github.supavitax.itemlorestats.Classes;
 import com.github.supavitax.itemlorestats.GearStats;
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.SetBonuses;
 import com.github.supavitax.itemlorestats.Soulbound;
 import com.github.supavitax.itemlorestats.XpLevel;
 import com.github.supavitax.itemlorestats.Durability.Durability;
 import com.github.supavitax.itemlorestats.Enchants.Armour;
 import com.github.supavitax.itemlorestats.Enchants.Blind;
 import com.github.supavitax.itemlorestats.Enchants.Block;
 import com.github.supavitax.itemlorestats.Enchants.CriticalStrike;
 import com.github.supavitax.itemlorestats.Enchants.Dodge;
 import com.github.supavitax.itemlorestats.Enchants.Fire;
 import com.github.supavitax.itemlorestats.Enchants.Harming;
 import com.github.supavitax.itemlorestats.Enchants.Ice;
 import com.github.supavitax.itemlorestats.Enchants.InternalCooldown;
 import com.github.supavitax.itemlorestats.Enchants.LifeSteal;
 import com.github.supavitax.itemlorestats.Enchants.Poison;
 import com.github.supavitax.itemlorestats.Enchants.Reflect;
 import com.github.supavitax.itemlorestats.Enchants.Vanilla_Power;
 import com.github.supavitax.itemlorestats.Enchants.Vanilla_Sharpness;
 import com.github.supavitax.itemlorestats.Enchants.Wither;
 import com.github.supavitax.itemlorestats.Spells.SpellCreator;
 import com.github.supavitax.itemlorestats.Util.Util_Citizens;
 import com.github.supavitax.itemlorestats.Util.Util_EntityManager;
 import com.github.supavitax.itemlorestats.Util.Util_Format;
 import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
 import com.github.supavitax.itemlorestats.Util.Util_Material;
 import com.github.supavitax.itemlorestats.Util.Util_Random;
 import com.github.supavitax.itemlorestats.Util.Util_WorldGuard;
 import com.github.supavitax.itemlorestats.Util.InvSlot.GetSlots;
 
 public class DamageSystem implements org.bukkit.event.Listener
 {
   public ItemLoreStats instance;
   Classes classes = new Classes();
   Durability durability = new Durability();
   GearStats gearStats = new GearStats();
   GetSlots getSlots = new GetSlots();
   InternalCooldown internalCooldown = new InternalCooldown();
   SetBonuses setBonuses = new SetBonuses();
   XpLevel xpLevel = new XpLevel();
   Soulbound soulbound = new Soulbound();
   SpellCreator spellCreator = new SpellCreator();
            
   
   com.github.supavitax.itemlorestats.Util.Util_Colours util_Colours = new com.github.supavitax.itemlorestats.Util.Util_Colours();
   Util_EntityManager util_EntityManager = new Util_EntityManager();
   Util_Format util_Format = new Util_Format();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   Util_Material util_Material = new Util_Material();
   Util_Random util_Random = new Util_Random();
   Util_Citizens util_Citizens = new Util_Citizens(ItemLoreStats.plugin);
   Util_WorldGuard util_WorldGuard = new Util_WorldGuard(ItemLoreStats.plugin);
   
   Armour armour = new Armour();
   Blind blind = new Blind();
   Block block = new Block();
   CriticalStrike criticalStrike = new CriticalStrike();
   Dodge dodge = new Dodge();
   Fire fire = new Fire();
   Harming harming = new Harming();
   Ice ice = new Ice();
   LifeSteal lifeSteal = new LifeSteal();
   Poison poison = new Poison();
   Reflect reflect = new Reflect();
   Wither wither = new Wither();
   
   Vanilla_Sharpness vanilla_Sharpness = new Vanilla_Sharpness();
   Vanilla_Power vanilla_Power = new Vanilla_Power();
   
   Vanilla_Base_Armour vanilla_Base_Armour = new Vanilla_Base_Armour();

   //String onlydamage;

            //static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
   
   public DamageSystem(ItemLoreStats i) {
     this.instance = i;
              //onlydamage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
   }
   
   public void setMetadata(Entity entity, String key, Object value, Plugin plugin) {
     entity.setMetadata(key, new org.bukkit.metadata.FixedMetadataValue(plugin, value));
   }
   
   //@EventHandler(ignoreCancelled=true)
             @EventHandler(priority = EventPriority.LOW)
   public void onEntityDamage(EntityDamageByEntityEvent event) {
     if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getDamager().getWorld().getName())) {
       if (!(event.getEntity() instanceof LivingEntity)) { return;
       }
       if ((ItemLoreStats.plugin.util_WorldGuard != null) && 
         ((event.getEntity() instanceof Player)) && (this.util_WorldGuard.playerInInvincibleRegion((Player)event.getEntity()))) {
         event.setCancelled(true);
         return;
       }
       
 
       Entity getAttacker = null;
       boolean isTool = false;
       if (((event.getDamager() instanceof Player)) || ((event.getDamager() instanceof Projectile))) {
         if ((event.getDamager() instanceof Projectile))
         {
           Entity shooter = null;
           
           Projectile projectile = (Projectile)event.getDamager();
           
           if (!(projectile.getShooter() instanceof Entity))
           {
             return;
           }
           
           shooter = (Entity)projectile.getShooter();
           
           if ((projectile.hasMetadata("ILS_Snowball")) || 
             (projectile.hasMetadata("ILS_SmallFireball")) || 
             (projectile.hasMetadata("ILS_Fireball")) || 
             (projectile.hasMetadata("ILS_Arrow")) || 
             (projectile.hasMetadata("ILS_Egg")) || 
             (projectile.hasMetadata("ILS_TnT")))
           {
 
 
             if (projectile.hasMetadata("Damage=")) {
               double DirectDamageAmount = ((MetadataValue)projectile.getMetadata("DDA=").get(0)).asDouble();
               double AOEDamageAmount = ((MetadataValue)projectile.getMetadata("ADA=").get(0)).asDouble();
               double AOEDamageRange = ((MetadataValue)projectile.getMetadata("ADR=").get(0)).asDouble();
               Effect projectileHitEffect = this.spellCreator.getProjectileHitEffect(this.gearStats.getSpellName(this.util_EntityManager.returnItemStackInMainHand(shooter)));
               
 
               event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), projectileHitEffect, 3);
               
               if (((event.getEntity() instanceof Player)) && 
                 (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken"))) {
                 ((Player)event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Damage", shooter, event.getEntity(), String.valueOf((int)DirectDamageAmount), String.valueOf((int)DirectDamageAmount)));
               }
               
 
               if ((event.getEntity() instanceof LivingEntity)) {
                 ((LivingEntity)event.getEntity()).damage(DirectDamageAmount);
               }
               
 
               if (AOEDamageRange > 0.0D) {
                 for (Iterator<Entity> iterator = event.getEntity().getNearbyEntities(AOEDamageRange, 256.0D, AOEDamageRange).iterator(); iterator.hasNext();) {
                   Entity entity = (Entity)iterator.next();
                   
                   if (entity.equals(event.getDamager()))
                   {
                     event.getEntity().getLocation().getWorld().playEffect(entity.getLocation(), projectileHitEffect, 3);
                     
                     if (((entity instanceof Player)) && 
                       (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken"))) {
                       ((Player)entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Damage", shooter, entity, String.valueOf((int)AOEDamageAmount), String.valueOf((int)AOEDamageAmount)));
                     }
                     
 
                     if ((entity instanceof LivingEntity)) {
                       ((LivingEntity)entity).damage(AOEDamageAmount);
                     }
                   }
                 }
               }
             } else {
               event.setCancelled(true);
             }
             
             if (projectile.hasMetadata("Heal=")) {
               double DirectHealAmount = ((MetadataValue)projectile.getMetadata("DHA=").get(0)).asDouble();
               double AOEHealAmount = ((MetadataValue)projectile.getMetadata("AHA=").get(0)).asDouble();
               double AOEHealRange = ((MetadataValue)projectile.getMetadata("AHR=").get(0)).asDouble();
               
               Effect projectileHitEffect = this.spellCreator.getProjectileHitEffect(this.gearStats.getSpellName(this.util_EntityManager.returnItemStackInMainHand(shooter)));
               
 
               event.getEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), projectileHitEffect, 3);
               
               if ((event.getEntity() instanceof Player)) {
                 ((Player)event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", shooter, event.getEntity(), String.valueOf((int)DirectHealAmount), String.valueOf((int)DirectHealAmount)));
               }
               
               if (this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount > this.util_EntityManager.returnEntityMaxHealth(event.getEntity())) {
                 this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), this.util_EntityManager.returnEntityMaxHealth(event.getEntity()));
               } else {
                 this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount);
               }
               
 
               if (AOEHealRange > 0.0D) {
                 for (Iterator<Entity> iterator = event.getEntity().getNearbyEntities(AOEHealRange, 256.0D, AOEHealRange).iterator(); iterator.hasNext();) {
                   Entity entity = (Entity)iterator.next();
                   
                   event.getEntity().getLocation().getWorld().playEffect(entity.getLocation(), projectileHitEffect, 3);
                   
                   if ((entity instanceof Player)) {
                     ((Player)entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", shooter, entity, String.valueOf((int)AOEHealAmount), String.valueOf((int)AOEHealAmount)));
                   }
                   
                   if (this.util_EntityManager.returnEntityCurrentHealth(entity) + AOEHealAmount > this.util_EntityManager.returnEntityMaxHealth(entity)) {
                     this.util_EntityManager.setEntityCurrentHealth(entity, this.util_EntityManager.returnEntityMaxHealth(entity));
                   } else {
                     this.util_EntityManager.setEntityCurrentHealth(entity, this.util_EntityManager.returnEntityCurrentHealth(entity) + AOEHealAmount);
                   }
                 }
               }
             }
             
             return;
           }
           
 
           if (!(shooter instanceof Player)) {
             if (((event.getEntity() instanceof Player)) && ((event.getEntity() instanceof LivingEntity))) {
               getAttacker = (LivingEntity)shooter;
             }
             
           }
           else {
             getAttacker = (Player)shooter;
           }
         }
         else if ((event.getDamager() instanceof Player))
         {
           if (((event.getEntity() instanceof Player)) && 
             (ItemLoreStats.plugin.getWorldGuard() != null) && (
             (ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion((Player)event.getEntity())) || (ItemLoreStats.plugin.util_WorldGuard.playerInInvincibleRegion((Player)event.getEntity())))) { return;
           }
           
 
           if (((Player)event.getDamager()).getEquipment().getItemInMainHand().getType().equals(Material.BOW)) {
             if ((event.getEntity() instanceof Player)) {
               event.setDamage(1.0D);
               damageDealtMessage(event.getDamager(), event.getEntity(), event.getDamage());
               return;
             }
             event.setDamage(1.0D);
             damageDealtMessage(event.getDamager(), event.getEntity(), event.getDamage());
             return;
           }
           
           getAttacker = (Player)event.getDamager();
         }
         
 
       }
       else if ((event.getDamager() instanceof LivingEntity)) {
         getAttacker = (LivingEntity)event.getDamager();
       } else { if (event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.LIGHTNING)) {
           return;
         }
         event.setCancelled(true);
         return;
       }
       
 
       if ((event.getEntity() instanceof LivingEntity)) {
         if ((event.getDamager() instanceof LivingEntity)) {
           LivingEntity getDefender = (LivingEntity)event.getEntity();
           if (getDefender.getNoDamageTicks() > 10) {
             event.setCancelled(true);
           }
         }
       }
       else {
         event.setCancelled(true);
         return;
       }
       
       LivingEntity getDefender = (LivingEntity)event.getEntity();
       
       if (((getAttacker instanceof Player)) && 
         ((getDefender instanceof Player))) {
         if ((ItemLoreStats.plugin.getWorldGuard() != null) && (
           (ItemLoreStats.plugin.util_WorldGuard.playerInPVPRegion((Player)getDefender)) || (ItemLoreStats.plugin.util_WorldGuard.playerInInvincibleRegion((Player)getDefender)))) { return;
         }
         
         if (((Player)getAttacker).getName().equals(((Player)getDefender).getName())) {
           event.setCancelled(true);
           return;
         }
       }
       
 
       if ((getAttacker instanceof Player))
       {
         if (!this.xpLevel.checkXPLevel((Player)getAttacker, ((Player)getAttacker).getInventory().getItemInMainHand())) {
           event.setDamage(1.0D);
           damageDealtMessage(getAttacker, getDefender, 1.0D);
           
           return;
         }
         
         if (!this.soulbound.checkSoulbound((Player)getAttacker, ((Player)getAttacker).getInventory().getItemInMainHand())) {
           event.setDamage(1.0D);
           damageDealtMessage(getAttacker, getDefender, 1.0D);
           
           return;
         }
         
         if (!this.classes.checkClasses((Player)getAttacker, ((Player)getAttacker).getInventory().getItemInMainHand())) {
           event.setDamage(1.0D);
           damageDealtMessage(getAttacker, getDefender, 1.0D);
           
           return;
         }
       }
       
       if (ItemLoreStats.plugin.isTool(this.util_EntityManager.returnItemStackInMainHand(getAttacker).getType())) {
         isTool = true;
       }
       
       if ((getDefender.hasMetadata("NPC")) && 
         (!this.util_Citizens.checkVulnerability(getDefender))) {
         event.setCancelled(true);
         return;
       }
       
 
       if (this.dodge.dodgeChanceOnHit(getDefender, isTool)) {
         if (((getAttacker instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyDodgedAttack"))) {
           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyDodgeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
         }
         
 
         if (((getDefender instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.dodgeAttack"))) {
           ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DodgeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
         }
         
 		  event.setDamage(0);
         event.setCancelled(true);
         return;
       }
       
       //if (this.block.blockChanceOnHit(getDefender, isTool)) {
                if (this.block.blockChanceOnHit(getDefender, false)) {
         if (((getAttacker instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyBlockedAttack"))) {
           ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyBlockSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
         }
         
 
         if (((getDefender instanceof Player)) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.blockAttack"))) {
           ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.BlockSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
           ((Player)getDefender).addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SLOW, 30, 1));
         }
         
         event.setDamage(0);
         event.setCancelled(true);
         return;
       }
       
       double eventDamage = event.getDamage();
       
       if (!(getAttacker instanceof Player)) {
				 if(getAttacker!=null)
         if ((getAttacker.hasMetadata("level")) && (getAttacker.hasMetadata("regionSpawned"))) {
           int mobLevel = ((MetadataValue)getAttacker.getMetadata("level").get(0)).asInt();
           String mobRegion = ((MetadataValue)getAttacker.getMetadata("regionSpawned").get(0)).asString();
           double newDamage = Math.round(attackerDamage((LivingEntity)getAttacker, getDefender, getDefender.getType(), event.getDamage(), this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInMainHand(getAttacker).getType()), 0.0D, false, isTool) + mobLevel * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + mobRegion + ".damageMultiplier"));
           
           eventDamage = newDamage;
         } else if (ItemLoreStats.plugin.getConfig().getString("npcModifier." + getDefender.getWorld().getName()) != null) {
           int mobLevel = 1;
           
           if (getAttacker.hasMetadata("level")) {
             mobLevel = ((MetadataValue)getAttacker.getMetadata("level").get(0)).asInt();
           }
           
           double newDamage = Math.round(attackerDamage((LivingEntity)getAttacker, getDefender, getDefender.getType(), event.getDamage(), this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInMainHand(getAttacker).getType()), 0.0D, false, isTool) + mobLevel * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + event.getDamager().getWorld().getName() + ".damageMultiplier"));
           
           eventDamage = newDamage;
         }
       }
       
       double getDefenderArmour = this.armour.armourChanceOnHit(getDefender);
       double getAttackerDamage = 0.0D;
       
       if (this.util_EntityManager.returnItemStackInMainHand(getAttacker).getType() == Material.BOW) {
         getAttackerDamage = attackerDamage((LivingEntity)getAttacker, getDefender, getDefender.getType(), eventDamage, eventDamage, getDefenderArmour, true, isTool);
       } else {
         getAttackerDamage = attackerDamage((LivingEntity)getAttacker, getDefender, getDefender.getType(), eventDamage, this.util_Material.materialToDamage(this.util_EntityManager.returnItemStackInMainHand(getAttacker).getType()), getDefenderArmour, true, isTool);
       }
                if (!(getAttacker instanceof Player)) {
                	getAttackerDamage = event.getDamage();
                }

                if(getAttackerDamage < 1)
                	getAttackerDamage = 1;
       
       double reducedDamage = getAttackerDamage / 100.0D * getDefenderArmour;
       
       if (((getDefender instanceof Player)) ) {
         this.durability.durabilityCalcForArmour((Player)getDefender, 1, "damage");
         
         if (((Player)getDefender).isBlocking()) {
           this.durability.durabilityCalcForItemInHand((Player)getDefender, 1, "damage", this.getSlots.returnItemInMainHand(getDefender), "Main");
           this.durability.durabilityCalcForItemInHand((Player)getDefender, 1, "damage", this.getSlots.returnItemInOffHand(getDefender), "Off");
         }
       }
       
       if ((getAttacker instanceof Player)) {
         this.durability.durabilityCalcForItemInHand((Player)getAttacker, 1, "damage", this.getSlots.returnItemInMainHand(getAttacker), "Main");
         this.durability.durabilityCalcForItemInHand((Player)getAttacker, 1, "damage", this.getSlots.returnItemInOffHand(getAttacker), "Off");
       }
       
 
       double reflectVal = this.reflect.reflectChanceOnHit(getDefender, isTool);
       
       if ((reflectVal > 0.0D) && 
         (this.util_Random.random(100) <= reflectVal)) {
         if ((getAttacker instanceof Player)) {
           double damage = getAttackerDamage - reducedDamage;
           
           ((Player)getAttacker).damage(damage);
           if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyReflectedAttack")) {
             if ((getDefender instanceof Player)) {
               ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             } else {
               ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
             }
           }
           if (((getDefender instanceof Player)) && 
             (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack"))) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
           }
           
           event.setDamage(0);
           event.setCancelled(true);
           return;
         }
         if ((getAttacker instanceof LivingEntity)) {
	
           double damage = getAttackerDamage - reducedDamage;
           
           ((LivingEntity)getAttacker).damage(damage);
           if (((getDefender instanceof Player)) && 
             (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack"))) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
           }
           
           event.setDamage(0);
           event.setCancelled(true);
           return;
         }
       }
       if (getDefender instanceof Player){
					Player player = (Player)getDefender;
					if(!instance.damagefix.IsAttackInCooldown(player.getUniqueId())){
						this.lifeSteal.lifeStealChanceOnHit(getDefender, (LivingEntity)getAttacker, getAttackerDamage - reducedDamage, isTool);
						this.fire.fireChanceOnHit(getDefender, (LivingEntity)getAttacker, isTool);
						this.ice.iceChanceOnHit(getDefender, (LivingEntity)getAttacker, isTool);
						this.poison.poisonChanceOnHit(getDefender, (LivingEntity)getAttacker, isTool);
						this.wither.witherChanceOnHit(getDefender, (LivingEntity)getAttacker, isTool);
						this.harming.harmingChanceOnHit(getDefender, (LivingEntity)getAttacker, isTool);
						this.blind.blindChanceOnHit(getDefender, (LivingEntity)getAttacker, isTool);
					}
	
                }
 
       getAttackerDamage -= reducedDamage;
                
                getAttackerDamage =  instance.damagefix.FixDamage(event.getDamager(),getAttackerDamage);
                
                if(getAttacker instanceof Player && getDefender instanceof Player)
	                if(EspecialAtributes.HasDestroy((Player) getAttacker)){
	                	if(!instance.damagefix.IsAttackInCooldown(((Player) getAttacker).getUniqueId()))
	                		this.durability.durabilityCalcForArmour((Player)getDefender, 2, "damage");
	                }
                
                
                if(getAttackerDamage< 0.6)
                {
                	getAttackerDamage=0;
                	event.setDamage(getAttackerDamage);
                	event.setCancelled(true);
                }

       event.setDamage(getAttackerDamage);
             
       
       if ((getDefender instanceof Player)) {
         ItemLoreStats.plugin.updateBarAPI((Player)getDefender);
       }
     }
   }
   
   @EventHandler
   public void onEntityDeath(EntityDeathEvent event) {
     if ((!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getWorld().getName())) && 
       ((event.getEntity().getKiller() instanceof Player)) && 
       (!(event.getEntity() instanceof Player))) {
       LivingEntity entity = event.getEntity();
       
       if (entity.hasMetadata("regionSpawned")) {
         String regionName = ((MetadataValue)entity.getMetadata("regionSpawned").get(0)).asString();
         int level = ((MetadataValue)entity.getMetadata("level").get(0)).asInt();
         double newExp = Math.round(event.getDroppedExp() + level * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + regionName + ".expMultiplier"));
         
         event.setDroppedExp((int)newExp);
       } else if ((ItemLoreStats.plugin.getConfig().getString("npcModifier." + entity.getWorld().getName()) != null) && 
         (entity.hasMetadata("naturalSpawn"))) {
         double distance = this.util_EntityManager.returnEntityMaxHealth(entity) / ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + entity.getWorld().getName() + ".healthMultiplier");
         double newExp = Math.round(event.getDroppedExp() + distance * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + entity.getWorld().getName() + ".expMultiplier"));
         
         event.setDroppedExp((int)newExp);
       }
     }
   }
   
 
 
   public void damageDealtMessage(Entity getAttacker, Entity getDefender, double damageDealt)
   {
     if ((getAttacker instanceof Player)) {
       if ((ItemLoreStats.plugin.combatLogVisible.get(((Player)getAttacker).getName()) == null) || (((Boolean)ItemLoreStats.plugin.combatLogVisible.get(((Player)getAttacker).getName())).booleanValue())) {
         if ((getDefender instanceof Player)) {
           if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
             ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, ((Player)getAttacker).getName(), String.valueOf(Math.round(damageDealt))));
           }
           if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
             ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDone", getAttacker, getDefender, ((Player)getDefender).getName(), String.valueOf(Math.round(damageDealt))));
           }
         }
         else if ((getDefender instanceof LivingEntity)) {
           if (((LivingEntity)getDefender).getCustomName() != null) {
             if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
               ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDone", getAttacker, getDefender, ((LivingEntity)getDefender).getCustomName(), String.valueOf(Math.round(damageDealt))));
             }
           }
           else if ((getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("a")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("e")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("i")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("o")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("u"))) {
             if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
               ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDoneWithoutVowel", getAttacker, getDefender, getDefender.getType().toString().substring(0, 1) + getDefender.getType().toString().substring(1, getDefender.getType().toString().length()).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
             }
           }
           else if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
             ((Player)getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDoneWithoutVowel", getAttacker, getDefender, getDefender.getType().toString().substring(0, 1) + getDefender.getType().toString().substring(1, getDefender.getType().toString().length()).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
           }
           
         }
         
       }
       
     }
     else if (((getDefender instanceof Player)) && 
       ((ItemLoreStats.plugin.combatLogVisible.get(((Player)getDefender).getName()) == null) || (((Boolean)ItemLoreStats.plugin.combatLogVisible.get(((Player)getDefender).getName())).booleanValue())) && 
       ((getAttacker instanceof LivingEntity))) {
       if (((LivingEntity)getAttacker).getCustomName() != null) {
         if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
           ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, ((LivingEntity)getAttacker).getCustomName(), String.valueOf(Math.round(damageDealt))));
         }
       }
       else if (ItemLoreStats.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
         ((Player)getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, getAttacker.getType().toString().substring(0, 1) + getAttacker.getType().toString().substring(1, getAttacker.getType().toString().length()).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
       }
     }
   }
   
 
 
 
   public double attackerDamage(LivingEntity getAttacker, LivingEntity getDefender, EntityType entityType, double eventDamage, double vanillaDamage, double defenderArmour, boolean sendMessage, boolean isTool)
   {
     double valueMin = 0.0D;
     double valueMax = 0.0D;
     double valueRand = 0.0D;
     double modifier = 0.0D;
     double damage = 0.0D;
     
     double valueMinMain = 0.0D;
     double valueMaxMain = 0.0D;
     double valueMinOff = 0.0D;
     double valueMaxOff = 0.0D;

              if(getAttacker != null && getDefender != null)
              {
     
     ItemStack itemInMainHandAttacker = this.getSlots.returnItemInMainHand(getAttacker);
     ItemStack itemInOffHandAttacker = this.getSlots.returnItemInMainHand(getAttacker);
     
     if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage"))
     {
       if ((itemInMainHandAttacker.hasItemMeta()) && 
         (itemInMainHandAttacker.getItemMeta().hasLore())) {
         valueMinMain = eventDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)).split("-")[0]);
         valueMaxMain = eventDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)).split("-")[1]);
       }
       
 
       if ((ItemLoreStats.plugin.getConfig().getBoolean("includeOffHandDamage")) && 
         (itemInOffHandAttacker.hasItemMeta()) && 
         (itemInOffHandAttacker.getItemMeta().hasLore())) {
         valueMinOff = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)).split("-")[0]);
         valueMaxOff = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker)).split("-")[1]);
       }
       
     }
     else
     {
       if ((itemInMainHandAttacker.hasItemMeta()) && 
         (itemInMainHandAttacker.getItemMeta().hasLore())) {
         valueMinMain = eventDamage - vanillaDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)).split("-")[0]);
         valueMaxMain = eventDamage - vanillaDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)).split("-")[1]);
       }
 
       if ((ItemLoreStats.plugin.getConfig().getBoolean("includeOffHandDamage")) && 
         (itemInOffHandAttacker.hasItemMeta()) && 
         (itemInOffHandAttacker.getItemMeta().hasLore())) {
	
	           if(getAttacker instanceof Player){
		             valueMinOff = instance.gearStats.getDamageFromOffHand((Player) getAttacker)[0];
		             valueMaxOff = instance.gearStats.getDamageFromOffHand((Player) getAttacker)[1];
		             //Bukkit.broadcastMessage("minoff "+ valueMin);
		             //Bukkit.broadcastMessage("maxoff "+ valueMax);
	           }
       }
     }
     
 
 
     valueMin = Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[0]) + (valueMinMain + valueMinOff);
     valueMax = Double.parseDouble(this.gearStats.getDamageGear(getAttacker).split("-")[1]) + (valueMaxMain + valueMaxOff);
     if ((valueMin > 0.0D) && (valueMax > 0.0D)) {
       damage = Double.parseDouble(this.util_Random.formattedRandomRange(valueMin, valueMax));
       
       if (this.vanilla_Sharpness.hasSharpness(getAttacker)) {
         damage = this.vanilla_Sharpness.calculateNewDamage(damage, ItemLoreStats.plugin.itemInMainHand(getAttacker).getEnchantmentLevel(Enchantment.DAMAGE_ALL), ItemLoreStats.plugin.itemInOffHand(getAttacker).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
       } else if (this.vanilla_Power.hasPower(getAttacker)) {
         damage = this.vanilla_Power.calculateNewDamage(damage, ItemLoreStats.plugin.itemInMainHand(getAttacker).getEnchantmentLevel(Enchantment.ARROW_DAMAGE), ItemLoreStats.plugin.itemInOffHand(getAttacker).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
       }
       
       if (this.criticalStrike.criticalStrikeChanceOnHit(getAttacker, getDefender) > 1) {
         double critDamageHands = this.gearStats.getCritDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker)) + this.gearStats.getCritDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker));
         
         valueRand = damage + damage * (ItemLoreStats.plugin.getConfig().getDouble("baseCritDamage") + (this.gearStats.getCritDamageGear(getAttacker) + critDamageHands)) / 100.0D;
       } else {
         valueRand = damage;
       }
     }
     else if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
       valueRand = eventDamage + modifier;
     } else {
       valueRand = eventDamage - vanillaDamage + modifier;
     }
     
 
     double pvPDmgModifier = 0.0D;
     double mainHandPvPDmgModifier = this.gearStats.getPvPDamageModifierItemInHand(ItemLoreStats.plugin.itemInMainHand(getAttacker));
     double offHandPvPDmgModifier = this.gearStats.getPvPDamageModifierItemInHand(ItemLoreStats.plugin.itemInOffHand(getAttacker));
     
     if (mainHandPvPDmgModifier != 0.0D) {
       pvPDmgModifier += mainHandPvPDmgModifier;
     }
     
     if (offHandPvPDmgModifier != 0.0D) {
       pvPDmgModifier += offHandPvPDmgModifier;
     }
     
     valueRand += pvPDmgModifier;
     
     //int valueRandInt = (int)Math.round(valueRand);
     double dam = valueRand;
     
     if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeArmour")) {
       double modifiedDamage = 0.0D;
       modifiedDamage = valueRand / Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D);
       
       dam = (int)Math.round(valueRand - valueRand / 100.0D * defenderArmour);
       
       if (sendMessage) {
         if (dam < 0) {
           damageDealtMessage(getAttacker, getDefender, 0.0D);
         } else {
           damageDealtMessage(getAttacker, getDefender, dam);
         }
       }
       //Bukkit.broadcastMessage("modifiedDamage "+modifiedDamage);

       
       return this.util_Format.format(modifiedDamage);
                
     }
     
     if (sendMessage) {
       if (dam < 0) {
         damageDealtMessage(getAttacker, getDefender, 0.0D);
       } else {
         damageDealtMessage(getAttacker, getDefender, dam);
       }
     }

     //Bukkit.broadcastMessage("valueRand "+valueRand);
              }
     return this.util_Format.format(valueRand);
   
           }

           /*
			public double[] addDamageFromItem(ItemStack gear, double multiplier){
			double damages[] = {0,0};
			List<String> itemLore = gear.getItemMeta().getLore();
			for (String line : itemLore) {
			    String lore = ChatColor.stripColor(line.toString());
			    lore = lore.toLowerCase();
			    if (lore.replaceAll(languageRegex, "").matches(onlydamage.toLowerCase())) {
			      if (lore.contains("-")) {
			    	  damages[0] += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""))*multiplier;
			    	  damages[1] += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""))*multiplier;
			      } else {
			    	  damages[0] += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""))*multiplier;
			    	  damages[1] += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""))*multiplier;
			      }
			    }
             }
			return damages;
       }
      */
	     
     }
           
