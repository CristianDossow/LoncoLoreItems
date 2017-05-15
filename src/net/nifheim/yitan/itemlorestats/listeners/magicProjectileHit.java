package net.nifheim.yitan.itemlorestats.listeners;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import net.nifheim.yitan.itemlorestats.Util.Util_WorldGuard;
import net.nifheim.yitan.loncoloremagics.Spell;
import net.nifheim.yitan.loncoloremagics.SpellsList;

//no es un evento propiamente tal, pero el projecthitevent no reconoce al llamaSpit
//por esto se llama este evento desde la clase SpellParticles cuando el proyectil
//adquiere el estado de "dead"

public class magicProjectileHit{
	
	private static final Function<? super Double, Double> ZERO = Functions.constant(-0.0);
	static Util_WorldGuard util_WorldGuard = new Util_WorldGuard(Main.plugin);
	
    public static void onProjectileHitEvent(Projectile projectile) {
		Entity shooter = null;
		//Projectile projectile=event.getEntity();
        shooter = (Entity) projectile.getShooter();
        if (projectile.hasMetadata("SPELLNAME=")) {
        	PlayerStats damagerStats=null;
    		if(shooter instanceof Player){
        		damagerStats=Main.plugin.getPlayerStats((Player)shooter);
    		}
            String SpellName = ((MetadataValue) projectile.getMetadata("SPELLNAME=").get(0)).asString();
            Spell spell = SpellsList.getSpell(SpellName);
            if (spell != null) {
                if (projectile.hasMetadata("Damage=")) {
                    //double DirectDamageAmount = ((MetadataValue) projectile.getMetadata("DDA=").get(0)).asDouble();
                    double AOEDamageAmount = ((MetadataValue) projectile.getMetadata("ADA=").get(0)).asDouble();
                    double AOEDamageRange = ((MetadataValue) projectile.getMetadata("ADR=").get(0)).asDouble();
                    double magicPen = ((MetadataValue) projectile.getMetadata("MAGICPEN=").get(0)).asDouble();
                    if (AOEDamageRange > 0.0D) {
                        for (Iterator<Entity> iterator = projectile.getNearbyEntities(AOEDamageRange, AOEDamageRange, AOEDamageRange).iterator(); iterator.hasNext();) {
                            Entity entity = (Entity) iterator.next();
                            if (!entity.equals(damagerStats.player)) {
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
                                else if((entity instanceof LivingEntity)&&entity.getType()!=EntityType.ARMOR_STAND) {
                                	if(damagerStats!=null && damagerStats.magicArmorPen>0){
                            			entity.setMetadata("DamageBefore=", new FixedMetadataValue(Main.getInstance(), AOEDamageAmount));
                            			entity.setMetadata("ArmorPen=", new FixedMetadataValue(Main.getInstance(), damagerStats.magicArmorPen));
                                    }
                            		Map<DamageModifier,Double> mapDM = Maps.newHashMap();
                            		mapDM.put(DamageModifier.BASE, AOEDamageAmount);
                            		Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity)(damagerStats.player),entity,DamageCause.LIGHTNING,mapDM,(Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO))));
                                	if(spell.fireTicks>=2){
                                		//((LivingEntity) entity).setFireTicks(spell.fireTicks*20/2);
                                		entity.setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks*20/2));
                                	}
                                }
                            }
                        }
                    }
                }
                /*
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
                }*/
            }
        }
	}
}
