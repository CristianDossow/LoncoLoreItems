package net.nifheim.yitan.itemlorestats.Damage;

import java.text.DecimalFormat;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import io.lumine.xikage.mythicmobs.MythicMobs;
import net.md_5.bungee.api.ChatColor;
import net.nifheim.yitan.itemlorestats.Classes;
import net.nifheim.yitan.itemlorestats.GearStats;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import net.nifheim.yitan.itemlorestats.SetBonuses;
import net.nifheim.yitan.itemlorestats.Soulbound;
import net.nifheim.yitan.itemlorestats.XpLevel;
import net.nifheim.yitan.itemlorestats.Durability.Durability;
import net.nifheim.yitan.itemlorestats.Enchants.Armour;
import net.nifheim.yitan.itemlorestats.Enchants.Blind;
import net.nifheim.yitan.itemlorestats.Enchants.Block;
import net.nifheim.yitan.itemlorestats.Enchants.CriticalStrike;
import net.nifheim.yitan.itemlorestats.Enchants.Dodge;
import net.nifheim.yitan.itemlorestats.Enchants.Fire;
import net.nifheim.yitan.itemlorestats.Enchants.Harming;
import net.nifheim.yitan.itemlorestats.Enchants.Ice;
import net.nifheim.yitan.itemlorestats.Enchants.InternalCooldown;
import net.nifheim.yitan.itemlorestats.Enchants.LifeSteal;
import net.nifheim.yitan.itemlorestats.Enchants.Poison;
import net.nifheim.yitan.itemlorestats.Enchants.Reflect;
import net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Power;
import net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Sharpness;
import net.nifheim.yitan.itemlorestats.Enchants.Wither;
import net.nifheim.yitan.itemlorestats.Util.Util_Citizens;
import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_Material;
import net.nifheim.yitan.itemlorestats.Util.Util_Random;
import net.nifheim.yitan.itemlorestats.Util.Util_WorldGuard;
import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
import net.nifheim.yitan.loncoloreitems.Backstab;
import net.nifheim.yitan.loncoloreitems.EspecialAtributes;
import net.nifheim.yitan.loncoloreitems.ItemCategory;
import net.nifheim.yitan.loncoloreitems.LoreItemMaker;
import net.nifheim.yitan.loncoloremagics.Spell;
import net.nifheim.yitan.loncoloremagics.SpellCast;
import net.nifheim.yitan.loncoloremagics.SpellsList;

public class DamageSystem implements org.bukkit.event.Listener {

    public Main instance;
    Classes classes = new Classes();
    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    XpLevel xpLevel = new XpLevel();
    Soulbound soulbound = new Soulbound();
    SpellCast spellCreator = new SpellCast();

    net.nifheim.yitan.itemlorestats.Util.Util_Colours util_Colours = new net.nifheim.yitan.itemlorestats.Util.Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Material util_Material = new Util_Material();
    Util_Random util_Random = new Util_Random();
    Util_Citizens util_Citizens = new Util_Citizens(Main.plugin);
    Util_WorldGuard util_WorldGuard = new Util_WorldGuard(Main.plugin);

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
    
    private static final Function<? super Double, Double> ZERO = Functions.constant(-0.0);
    static DecimalFormat df = new DecimalFormat("#.#");
    //private static final Function<? super Double, Double> ZERO = Functions.constant(-1.1);

    //String onlydamage;
    //static String languageRegex = "[^A-Za-z������������_]";
    public DamageSystem(Main i) {
        this.instance = i;
        //onlydamage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
    }

    public void setMetadata(Entity entity, String key, Object value, Plugin plugin) {
        entity.setMetadata(key, new org.bukkit.metadata.FixedMetadataValue(plugin, value));
    }
    /*
    @EventHandler(priority = EventPriority.LOWEST)
    public void MagicDeath(EntityDeathEvent event) {
		for(Player p :Bukkit.getOnlinePlayers()){
			if(p.hasPermission("ils.admin")){
				p.sendMessage(event.getEntity().getLastDamageCause().getCause().toString());
			}
		}
    	if(event.getEntity().getLastDamageCause().getCause().equals(DamageCause.CUSTOM)){
    		for(Player p :Bukkit.getOnlinePlayers()){
    			if(p.hasPermission("ils.admin")){
    				p.sendMessage("ThunderDead");
    			}
    		}
    		if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
    			Entity damager = ((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
        		Map<DamageModifier,Double> mapDM = Maps.newHashMap();
        		mapDM.put(DamageModifier.BASE, event.getEntity().getLastDamageCause().getDamage()); 
        		EntityDamageByEntityEvent ev = new EntityDamageByEntityEvent(damager,event.getEntity(),DamageCause.ENTITY_ATTACK,mapDM,(Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO)));
        		event.getEntity().setLastDamageCause(ev);
        		
        		for(Player p :Bukkit.getOnlinePlayers()){
        			if(p.hasPermission("ils.admin")){
        				p.sendMessage("magic damage dead");
        			}
        		}
    		}

    	}
    }*/
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage2(EntityDamageByEntityEvent event) {
    	if(!event.isCancelled()&&event.getDamage()>0){
    		
    		Entity getAttacker = event.getDamager();
            Entity getDefender = event.getEntity();
    		
            if (((getDefender instanceof Player))) {
                this.durability.durabilityCalcForArmour((Player) getDefender, 1, "damage");
                if (((Player) getDefender).isBlocking()) {
                    this.durability.durabilityCalcForItemInHand((Player) getDefender, 1, "damage", this.getSlots.returnItemInMainHand(getDefender), "Main");
                    this.durability.durabilityCalcForItemInHand((Player) getDefender, 1, "damage", this.getSlots.returnItemInOffHand(getDefender), "Off");
                }
            }
            if ((getAttacker instanceof Player)) {
                this.durability.durabilityCalcForItemInHand((Player) getAttacker, 1, "damage", this.getSlots.returnItemInMainHand(getAttacker), "Main");
                this.durability.durabilityCalcForItemInHand((Player) getAttacker, 1, "damage", this.getSlots.returnItemInOffHand(getAttacker), "Off");
            }
            
            if (getAttacker instanceof Player && getDefender instanceof Player) {
                if (EspecialAtributes.HasDestroy((Player) getAttacker)) {
                    if (!instance.damagefix.IsAttackInCooldown(((Player) getAttacker).getUniqueId())) {
                        this.durability.durabilityCalcForArmour((Player) getDefender, 2, "damage");
                    }
                }
            }

            if(event.getEntity().hasMetadata("FireTicks=")){
            	double fireTicks = ((MetadataValue) event.getEntity().getMetadata("FireTicks=").get(0)).asDouble();
            	event.getEntity().setFireTicks((int)fireTicks);
            	event.getEntity().removeMetadata("FireTicks=",Main.getInstance());
            }
    		if(event.getEntity().hasMetadata("DamageBefore=")&&event.getEntity().hasMetadata("ArmorPen=")){
    			double damageBeforeReduction = ((MetadataValue) event.getEntity().getMetadata("DamageBefore=").get(0)).asDouble();
    			double armorPen = ((MetadataValue) event.getEntity().getMetadata("ArmorPen=").get(0)).asDouble();
        		if(armorPen>1)
        			armorPen=1;
    			if(damageBeforeReduction>event.getDamage()){
        			double newDamage =event.getDamage() + ((damageBeforeReduction - event.getDamage())*armorPen);
        			event.setDamage(newDamage);
        		}
        		event.getEntity().removeMetadata("DamageBefore=", Main.getInstance());
        		event.getEntity().removeMetadata("ArmorPen=", Main.getInstance());
    		}
    	}
    	if(!event.isCancelled() &&event.getCause().equals(DamageCause.LIGHTNING)){
    		if(event.getEntity() instanceof LivingEntity && event.getEntity().getType()!=EntityType.ARMOR_STAND){
    			LivingEntity entity = (LivingEntity)event.getEntity();
    			if(entity.getNoDamageTicks()<=0){
    				entity.setNoDamageTicks(5);
    	    		event.getEntity().setLastDamageCause(event);
    	    		if(entity.getHealth()<event.getDamage()){
    	    			entity.setHealth(0);
    	    		}
    	    		else{
    	    			entity.setHealth(entity.getHealth()-event.getDamage());
    	    		}
    	    		
    	    		//((LivingEntity)event.getEntity()).damage(event.getDamage());
    	    		//((LivingEntity)event.getEntity()).damage(event.getDamage(), event.getDamager());
    	    		return;
    	    	}
    			return;
    		}
    	}
    }
    
    @EventHandler(priority = EventPriority.LOWEST )
    public void onEntityDamage(EntityDamageByEntityEvent event) {
    	if(event.getCause().equals(DamageCause.LIGHTNING)||event.getEntity().getType().equals(EntityType.ARMOR_STAND)){
    		return;
    	}
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getDamager().getWorld().getName())) {
            Entity getAttacker = event.getDamager();
            Entity getDefender = event.getEntity();
        	if (!(event.getEntity() instanceof LivingEntity)) {
                return;
            }
            if ((Main.plugin.util_WorldGuard != null) && ((event.getEntity() instanceof Player)) && (this.util_WorldGuard.playerInInvincibleRegion((Player) event.getEntity()))) {
                event.setCancelled(true);
                return;
            }
            
            if ((getDefender.hasMetadata("NPC"))
                    && (!this.util_Citizens.checkVulnerability(getDefender))) {
                event.setCancelled(true);
                return;
            }
            PlayerStats damagerStats=null;
            PlayerStats defenderStats=null;
        	if(event.getDamager() instanceof Player){
        		Player p =(Player)event.getDamager();
        		ItemStack weapon = p.getInventory().getItemInMainHand();
        		if(ItemCategory.isAnyWeapon(weapon) ){
        			if(!weapon.hasItemMeta() || !weapon.getItemMeta().hasLore()){
        				p.getInventory().setItemInMainHand(LoreItemMaker.CheckItemLore(weapon, p)); 
        			}
        		}
        		damagerStats=Main.plugin.getPlayerStats((Player)event.getDamager());
        		damagerStats.UpdateAttack();
        	}
        	if(event.getEntity() instanceof Player){
        		defenderStats=Main.plugin.getPlayerStats((Player)event.getEntity());
        		defenderStats.UpdateDefence();
        	}
        	if(event.getDamager() instanceof Projectile)
        		if(((Projectile)event.getDamager()).getShooter() instanceof Player){
            		damagerStats=Main.plugin.getPlayerStats((Player)((Projectile)event.getDamager()).getShooter());
            		damagerStats.UpdateAll();
        		}
            //-------------------- Mágico---------------------------------
            if ((event.getDamager() instanceof Projectile)) {
            	Projectile projectile = (Projectile) event.getDamager();
                Entity shooter = null;
                if (!(projectile.getShooter() instanceof Entity)) {
                    return;
                }
                shooter = (Entity) projectile.getShooter();

                if (projectile.hasMetadata("SPELLNAME=")) {
                	projectile.setLastDamageCause(event);
                    String SpellName = ((MetadataValue) projectile.getMetadata("SPELLNAME=").get(0)).asString();
                    Spell spell = SpellsList.getSpell(SpellName);
                    if (spell != null) {
                        if (projectile.hasMetadata("Damage=")) {
                            double DirectDamageAmount = ((MetadataValue) projectile.getMetadata("DDA=").get(0)).asDouble();
                            double AOEDamageAmount = ((MetadataValue) projectile.getMetadata("ADA=").get(0)).asDouble();
                            projectile.removeMetadata("SPELLNAME=", Main.getInstance());
                            double AOEDamageRange = ((MetadataValue) projectile.getMetadata("ADR=").get(0)).asDouble();
                            double magicPen = ((MetadataValue) projectile.getMetadata("MAGICPEN=").get(0)).asDouble();
                            if ((getDefender instanceof Player)){
                                double damage = DirectDamageAmount*(1-(defenderStats.magicPercentArmor* (1-magicPen)) );
                                this.durability.durabilityCalcForArmour((Player) getDefender, 1, "damage");
                                event.setDamage(damage);
                                event.getEntity().setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks*20));
                                //if(util_WorldGuard.playerInPVPRegion((Player)event.getEntity() )){
                                	//((Player)event.getEntity()).setFireTicks(spell.fireTicks*20);
                                	
                                //}
                            }
                            else{
                            	if ((event.getEntity() instanceof LivingEntity) && event.getEntity().getType()!=EntityType.ARMOR_STAND) {
                            		if(spell.fireTicks>=2){
                                		//((LivingEntity) event.getEntity()).setFireTicks(spell.fireTicks*20);
                                		event.getEntity().setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks*20));
                                	}
                            		event.setDamage(0);
                                    if(damagerStats!=null && damagerStats.magicArmorPen>0){
                                    	event.getEntity().setMetadata("DamageBefore=", new FixedMetadataValue(Main.getInstance(), DirectDamageAmount));
                                    	event.getEntity().setMetadata("ArmorPen=", new FixedMetadataValue(Main.getInstance(), damagerStats.magicArmorPen));
                                    }
                            		Map<DamageModifier,Double> mapDM = Maps.newHashMap();
                            		mapDM.put(DamageModifier.BASE, DirectDamageAmount); 
                            		//(Entity)(damagerStats.player)
                            		Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity)(damagerStats.player),event.getEntity(),DamageCause.LIGHTNING,mapDM,(Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO))));
                            	}
                            }
                            
                            if (AOEDamageRange > 0.0D) {
                                for (Iterator<Entity> iterator = event.getEntity().getNearbyEntities(AOEDamageRange, AOEDamageRange, AOEDamageRange).iterator(); iterator.hasNext();) {
                                    Entity entity = (Entity) iterator.next();
                                    if (!entity.equals(damagerStats.player) && !entity.equals(event.getEntity())) {
                                        if ((entity instanceof Player)) {
                                        	if(util_WorldGuard.playerInPVPRegion((Player)entity)){
                                            	PlayerStats ps = Main.plugin.getPlayerStats((Player)entity);
                                            	ps.UpdateDefence();
                                            	double damage = AOEDamageAmount*(1-(ps.magicPercentArmor* (1-magicPen)) );
                                            	if(spell.fireTicks>=2){
                                            		entity.setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks*20/2));
                                            	}
                                            	Map<DamageModifier,Double> mapDM = Maps.newHashMap();
                                        		mapDM.put(DamageModifier.BASE, damage);
                                        		Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity)(damagerStats.player),entity,DamageCause.LIGHTNING,mapDM,(Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO))));
                                        	}
                                        }
                                        else if((entity instanceof LivingEntity)&& entity.getType()!=EntityType.ARMOR_STAND) {
                                    		if(damagerStats!=null && damagerStats.magicArmorPen>0){
                                    			entity.setMetadata("DamageBefore=", new FixedMetadataValue(Main.getInstance(), AOEDamageAmount));
                                    			entity.setMetadata("ArmorPen=", new FixedMetadataValue(Main.getInstance(), damagerStats.magicArmorPen));
                                            }
                                        	if(spell.fireTicks>=2){
                                        		//((LivingEntity) entity).setFireTicks(spell.fireTicks*20/2);
                                        		entity.setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks*20/2));
                                        	}
                                    		Map<DamageModifier,Double> mapDM = Maps.newHashMap();
                                    		mapDM.put(DamageModifier.BASE, AOEDamageAmount);
                                    		Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity)(damagerStats.player),entity,DamageCause.LIGHTNING,mapDM,(Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO))));
                                        }
                                    }
                                }
                            }
                            return;
                        } else {
                            event.setCancelled(true);
                        }
                        if (projectile.hasMetadata("Heal=")) {
                            double DirectHealAmount = ((MetadataValue) projectile.getMetadata("DHA=").get(0)).asDouble();
                            double AOEHealAmount = ((MetadataValue) projectile.getMetadata("AHA=").get(0)).asDouble();
                            double AOEHealRange = ((MetadataValue) projectile.getMetadata("AHR=").get(0)).asDouble();
                            if ((event.getEntity() instanceof Player)) {
                                ((Player) event.getEntity()).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", shooter, event.getEntity(), String.valueOf((int) DirectHealAmount), String.valueOf((int) DirectHealAmount)));
                            }
                            if (this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount > this.util_EntityManager.returnEntityMaxHealth(event.getEntity())) {
                                this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), this.util_EntityManager.returnEntityMaxHealth(event.getEntity()));
                            } else {
                                this.util_EntityManager.setEntityCurrentHealth(event.getEntity(), this.util_EntityManager.returnEntityCurrentHealth(event.getEntity()) + DirectHealAmount);
                            }
                            if (AOEHealRange > 0.0D) {
                                for (Iterator<Entity> iterator = event.getEntity().getNearbyEntities(AOEHealRange, 256.0D, AOEHealRange).iterator(); iterator.hasNext();) {
                                    Entity entity = (Entity) iterator.next();
                                    if ((entity instanceof Player)) {
                                        ((Player) entity).sendMessage(this.util_GetResponse.getResponse("SpellMessages.CastSpell.Heal", shooter, entity, String.valueOf((int) AOEHealAmount), String.valueOf((int) AOEHealAmount)));
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
                    return;
                } 
            }
                //-------------------- Fisico---------------------------------
                if (((getAttacker instanceof Player))
                        && ((getDefender instanceof Player))) {
                    /*if ((Main.plugin.getWorldGuard() != null) && ((util_WorldGuard.playerInPVPRegion((Player) getDefender)) || (util_WorldGuard.playerInInvincibleRegion((Player) getDefender)))) {
                        return;
                    }*/
                    if (((Player) getAttacker).getName().equals(((Player) getDefender).getName())) {
                        event.setCancelled(true);
                        return;
                    }
                }
                if (this.dodge.checkDodge(event,getAttacker,getDefender,defenderStats)) {
                    return;
                }
                if (this.block.checkBlock(event,getAttacker,getDefender,defenderStats)) {
                    return;
                }
                double getDefenderArmour = defenderStats !=null ? defenderStats.percentArmor:0;
                double getAttackerDamage = 0.0D;
                getAttackerDamage = event.getDamage();
                if (getAttacker instanceof Player) {
                    if (this.util_EntityManager.returnItemStackInMainHand(getAttacker).getType() == Material.BOW) {
                        //getAttackerDamage = attackerDamage((LivingEntity) getAttacker, getDefender, getDefender.getType(), eventDamage, eventDamage, getDefenderArmour, true, isTool);
                    	getAttackerDamage = damagerStats.minDamage + Math.random() * (damagerStats.maxDamage-damagerStats.minDamage);
                    	getAttackerDamage = getAttackerDamage /5;
                    }else{
                    	getAttackerDamage = damagerStats.minDamage + Math.random() * (damagerStats.maxDamage-damagerStats.minDamage);
                    	
                    }
                }else if (getAttacker instanceof Projectile) {
                	if(((Projectile)getAttacker).hasMetadata("Damage=")){
                		getAttackerDamage = ((MetadataValue) ((Projectile)getAttacker).getMetadata("Damage=").get(0)).asDouble();
                	}
                }
               
                

                //double reflectVal = this.reflect.reflectChanceOnHit(getDefender, isTool);
                /*
                if ((reflectVal > 0.0D)
                        && (this.util_Random.random(100) <= reflectVal)) {
                    if ((getAttacker instanceof Player)) {
                        double damage = getAttackerDamage - reducedDamage;

                        ((Player) getAttacker).damage(damage);
                        if (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.enemyReflectedAttack")) {
                            if ((getDefender instanceof Player)) {
                                ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                            } else {
                                ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                            }
                        }
                        if (((getDefender instanceof Player))
                                && (Main.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack"))) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }

                        event.setDamage(0);
                        event.setCancelled(true);
                        return;
                    }
                    if ((getAttacker instanceof LivingEntity)) {

                        double damage = getAttackerDamage - reducedDamage;

                        ((LivingEntity) getAttacker).damage(damage);
                        if (((getDefender instanceof Player))
                                && (Main.plugin.getConfig().getBoolean("combatMessages.incoming.reflectAttack"))) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.ReflectSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }

                        event.setDamage(0);
                        event.setCancelled(true);
                        return;
                    }
                }*/
                Boolean isAttackInCooldown=true;
                double armorPen=0;
                if (getAttacker instanceof Player) {
                	isAttackInCooldown=instance.damagefix.IsAttackInCooldown(((Player) getAttacker).getUniqueId());
                	armorPen=damagerStats.armorPen;
                }
                if (getDefender instanceof Player) {
                	getDefenderArmour = getDefenderArmour * (1-armorPen);
                }
                getAttackerDamage = instance.damagefix.FixDamage(event.getDamager(), getAttackerDamage);
                if (getAttackerDamage < 0.9) {
                    getAttackerDamage = 0;
                    event.setDamage(getAttackerDamage);
                    event.setCancelled(true);
                    return;
                }
                double reducedDamage = getAttackerDamage * getDefenderArmour;
                getAttackerDamage = getAttackerDamage - reducedDamage;
                
                if (getAttacker instanceof Player && getDefender instanceof LivingEntity) {
                    Player player = (Player) getAttacker;
                    if (!isAttackInCooldown&&getAttackerDamage>0) {
                        this.lifeSteal.lifeStealChanceOnHit((LivingEntity) getDefender, (LivingEntity) getAttacker, getAttackerDamage - reducedDamage, true);
                        this.fire.fireChanceOnHit((LivingEntity) getDefender, (LivingEntity) getAttacker, true);
                        this.ice.iceChanceOnHit((LivingEntity)getDefender, (LivingEntity) getAttacker, true);
                        this.poison.poisonChanceOnHit((LivingEntity)getDefender, (LivingEntity) getAttacker, true);
                        this.wither.witherChanceOnHit((LivingEntity)getDefender, (LivingEntity) getAttacker, true);
                        this.harming.harmingChanceOnHit((LivingEntity)getDefender, (LivingEntity) getAttacker, true);
                        this.blind.blindChanceOnHit((LivingEntity)getDefender, (LivingEntity) getAttacker, true);
                        getAttackerDamage = checkCritical(damagerStats, getDefender, getAttackerDamage);
                        getAttackerDamage = Backstab.checkStab(damagerStats, getDefender, getAttackerDamage, damagerStats.stabDamage);
                    }

                }
                if (getAttacker instanceof Player && getDefender instanceof Player) {
                    if (EspecialAtributes.HasDestroy((Player) getAttacker)) {
                        if (!instance.damagefix.IsAttackInCooldown(((Player) getAttacker).getUniqueId())) {
                            this.durability.durabilityCalcForArmour((Player) getDefender, 2, "damage");
                        }
                    }
                }
                if(damagerStats!=null && damagerStats.armorPen>0&& !event.getEntity().getType().equals(EntityType.PLAYER)){
                	event.getEntity().setMetadata("DamageBefore=", new FixedMetadataValue(Main.getInstance(), getAttackerDamage));
                	event.getEntity().setMetadata("ArmorPen=", new FixedMetadataValue(Main.getInstance(), damagerStats.armorPen));
                }
                
                event.setDamage(getAttackerDamage);
        }
    }
    
    public static double checkCritical(PlayerStats ps,Entity e, double damage) {
    	if(ps.critChance>Math.random()){
        	damage = damage * (1+ps.critDamage);
        	ps.player.sendMessage(ChatColor.RED+"Golpe Critico! (daño total "+df.format(damage)+")");
            if (e instanceof Player) {
                ((Player) e).sendMessage(ChatColor.DARK_RED+"Recibido Golpe Critico! (daño total "+df.format(damage)+")");
            }
    	}
        return damage;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if ((!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getWorld().getName()))
                && ((event.getEntity().getKiller() instanceof Player))
                && (!(event.getEntity() instanceof Player))) {
            LivingEntity entity = event.getEntity();

            if (entity.hasMetadata("regionSpawned")) {
                String regionName = ((MetadataValue) entity.getMetadata("regionSpawned").get(0)).asString();
                int level = ((MetadataValue) entity.getMetadata("level").get(0)).asInt();
                double newExp = Math.round(event.getDroppedExp() + level * Main.plugin.getConfig().getDouble("npcModifier." + regionName + ".expMultiplier"));

                event.setDroppedExp((int) newExp);
            } else if ((Main.plugin.getConfig().getString("npcModifier." + entity.getWorld().getName()) != null)
                    && (entity.hasMetadata("naturalSpawn"))) {
                double distance = this.util_EntityManager.returnEntityMaxHealth(entity) / Main.plugin.getConfig().getDouble("npcModifier." + entity.getWorld().getName() + ".healthMultiplier");
                double newExp = Math.round(event.getDroppedExp() + distance * Main.plugin.getConfig().getDouble("npcModifier." + entity.getWorld().getName() + ".expMultiplier"));

                event.setDroppedExp((int) newExp);
            }
        }
    }

    public void damageDealtMessage(Entity getAttacker, Entity getDefender, double damageDealt) {
        if ((getAttacker instanceof Player)) {
            if ((Main.plugin.combatLogVisible.get(((Player) getAttacker).getName()) == null) || (((Boolean) Main.plugin.combatLogVisible.get(((Player) getAttacker).getName())).booleanValue())) {
                if ((getDefender instanceof Player)) {
                    if (Main.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, ((Player) getAttacker).getName(), String.valueOf(Math.round(damageDealt))));
                    }
                    if (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                        ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDone", getAttacker, getDefender, ((Player) getDefender).getName(), String.valueOf(Math.round(damageDealt))));
                    }
                } else if ((getDefender instanceof LivingEntity)) {
                    if (((LivingEntity) getDefender).getCustomName() != null) {
                        if (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDone", getAttacker, getDefender, ((LivingEntity) getDefender).getCustomName(), String.valueOf(Math.round(damageDealt))));
                        }
                    } else if ((getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("a")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("e")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("i")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("o")) || (getDefender.getType().toString().substring(0, 1).equalsIgnoreCase("u"))) {
                        if (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                            ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDoneWithoutVowel", getAttacker, getDefender, getDefender.getType().toString().substring(0, 1) + getDefender.getType().toString().substring(1, getDefender.getType().toString().length()).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
                        }
                    } else if (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.damageDone")) {
                        ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageDoneWithoutVowel", getAttacker, getDefender, getDefender.getType().toString().substring(0, 1) + getDefender.getType().toString().substring(1, getDefender.getType().toString().length()).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
                    }

                }

            }

        } else if (((getDefender instanceof Player))
                && ((Main.plugin.combatLogVisible.get(((Player) getDefender).getName()) == null) || (((Boolean) Main.plugin.combatLogVisible.get(((Player) getDefender).getName())).booleanValue()))
                && ((getAttacker instanceof LivingEntity))) {
            if (((LivingEntity) getAttacker).getCustomName() != null) {
                if (Main.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                    ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, ((LivingEntity) getAttacker).getCustomName(), String.valueOf(Math.round(damageDealt))));
                }
            } else if (Main.plugin.getConfig().getBoolean("combatMessages.incoming.damageTaken")) {
                ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.DamageTaken", getAttacker, getDefender, getAttacker.getType().toString().substring(0, 1) + getAttacker.getType().toString().substring(1, getAttacker.getType().toString().length()).toLowerCase().replace("_", " "), String.valueOf(Math.round(damageDealt))));
            }
        }
    }

    public double attackerDamage(LivingEntity getAttacker, LivingEntity getDefender, EntityType entityType, double eventDamage, double vanillaDamage, double defenderArmour, boolean sendMessage, boolean isTool) {
        double valueMin = 0.0D;
        double valueMax = 0.0D;
        double valueRand = 0.0D;
        double modifier = 0.0D;
        double damage = 0.0D;

        double valueMinMain = 0.0D;
        double valueMaxMain = 0.0D;
        double valueMinOff = 0.0D;
        double valueMaxOff = 0.0D;

        if (getAttacker != null && getDefender != null) {

            ItemStack itemInMainHandAttacker = this.getSlots.returnItemInMainHand(getAttacker);
            ItemStack itemInOffHandAttacker = this.getSlots.returnItemInMainHand(getAttacker);

            if (Main.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
                if ((itemInMainHandAttacker.hasItemMeta())
                        && (itemInMainHandAttacker.getItemMeta().hasLore())) {
                    valueMinMain = eventDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInMainHand(getAttacker)).split("-")[0]);
                    valueMaxMain = eventDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInMainHand(getAttacker)).split("-")[1]);
                }

                if ((Main.plugin.getConfig().getBoolean("includeOffHandDamage"))
                        && (itemInOffHandAttacker.hasItemMeta())
                        && (itemInOffHandAttacker.getItemMeta().hasLore())) {
                    valueMinOff = Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInOffHand(getAttacker)).split("-")[0]);
                    valueMaxOff = Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInOffHand(getAttacker)).split("-")[1]);
                }

            } else {
                if ((itemInMainHandAttacker.hasItemMeta())
                        && (itemInMainHandAttacker.getItemMeta().hasLore())) {
                    valueMinMain = eventDamage - vanillaDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInMainHand(getAttacker)).split("-")[0]);
                    valueMaxMain = eventDamage - vanillaDamage + Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInMainHand(getAttacker)).split("-")[1]);
                }

                if ((Main.plugin.getConfig().getBoolean("includeOffHandDamage"))
                        && (itemInOffHandAttacker.hasItemMeta())
                        && (itemInOffHandAttacker.getItemMeta().hasLore())) {

                    if (getAttacker instanceof Player) {
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
                    damage = this.vanilla_Sharpness.calculateNewDamage(damage, Main.plugin.itemInMainHand(getAttacker).getEnchantmentLevel(Enchantment.DAMAGE_ALL), Main.plugin.itemInOffHand(getAttacker).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                } else if (this.vanilla_Power.hasPower(getAttacker)) {
                    damage = this.vanilla_Power.calculateNewDamage(damage, Main.plugin.itemInMainHand(getAttacker).getEnchantmentLevel(Enchantment.ARROW_DAMAGE), Main.plugin.itemInOffHand(getAttacker).getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                }

                if (this.criticalStrike.criticalStrikeChanceOnHit(getAttacker, getDefender) > 1) {
                    double critDamageHands = this.gearStats.getCritDamageItemInHand(Main.plugin.itemInMainHand(getAttacker)) + this.gearStats.getCritDamageItemInHand(Main.plugin.itemInOffHand(getAttacker));

                    valueRand = damage + damage * (Main.plugin.getConfig().getDouble("baseCritDamage") + (this.gearStats.getCritDamageGear(getAttacker) + critDamageHands)) / 100.0D;
                } else {
                    valueRand = damage;
                }
            } else if (Main.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
                valueRand = eventDamage + modifier;
            } else {
                valueRand = eventDamage - vanillaDamage + modifier;
            }

            double pvPDmgModifier = 0.0D;
            double mainHandPvPDmgModifier = this.gearStats.getPvPDamageModifierItemInHand(Main.plugin.itemInMainHand(getAttacker));
            double offHandPvPDmgModifier = this.gearStats.getPvPDamageModifierItemInHand(Main.plugin.itemInOffHand(getAttacker));

            if (mainHandPvPDmgModifier != 0.0D) {
                pvPDmgModifier += mainHandPvPDmgModifier;
            }

            if (offHandPvPDmgModifier != 0.0D) {
                pvPDmgModifier += offHandPvPDmgModifier;
            }

            valueRand += pvPDmgModifier;

            //int valueRandInt = (int)Math.round(valueRand);
            double dam = valueRand;

            if (Main.plugin.getConfig().getBoolean("vanilla.includeArmour")) {
                double modifiedDamage = 0.0D;
                modifiedDamage = valueRand / Math.abs(this.vanilla_Base_Armour.getDamageReductionFromArmour(getDefender) - 1.0D);

                dam = (int) Math.round(valueRand - valueRand / 100.0D * defenderArmour);

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
    // Start Enchant Listener
    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();
            MetadataValue itemCraftMetaData = getItemCraftMetaData(arrow, "bow");

            if (itemCraftMetaData != null) {
                ItemStack isBow = (ItemStack) itemCraftMetaData.value();

                if (isBow != null) {
                    Player pShooter = (Player) getItemCraftMetaData(arrow, "shooter").value();

                    //plugin.activateEnchant.onArrowHitEntity(pShooter, isBow, e);
                }
            }
        } else if (e.getDamager() instanceof Player) {
            Player attacker = (Player) e.getDamager();

            if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR) {
                //plugin.activateEnchant.onDamagedOtherEntity(attacker, e);
            }
        }
    }

    public MetadataValue getItemCraftMetaData(Metadatable holder, String key) {
        List<MetadataValue> metadata = holder.getMetadata(key);

        for (MetadataValue mdv : metadata) {
            if (mdv.getOwningPlugin().equals(instance)) {
                return mdv;
            }
        }

        return null;
    }
    // End Enchant Listener
}
