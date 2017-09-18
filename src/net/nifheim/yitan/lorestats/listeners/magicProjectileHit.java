package net.nifheim.yitan.lorestats.listeners;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import net.nifheim.yitan.lorestats.utils.Util_WorldGuard;
import net.nifheim.yitan.magic.Spell;
import net.nifheim.yitan.magic.SpellsList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 * no es un evento propiamente tal, pero el projecthitevent no reconoce al
 * llamaSpit por esto se llama este evento desde la clase SpellParticles cuando
 * el proyectil adquiere el estado de "dead"
 *
 * @author YitanTribal
 */
public class magicProjectileHit {

    private static final Function<? super Double, Double> ZERO = Functions.constant(-0.0);
    static Util_WorldGuard util_WorldGuard = new Util_WorldGuard(Main.getInstance());

    public static void onProjectileHitEvent(Projectile projectile) {
        Entity shooter = (Entity) projectile.getShooter();
        if (projectile.hasMetadata("SPELLNAME=")) {
            PlayerStats damagerStats = null;
            if (shooter instanceof Player) {
                damagerStats = Main.getInstance().getPlayerStats((Player) shooter);
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
                            if (damagerStats != null && !entity.equals(damagerStats.player)) {
                                if ((entity instanceof Player)) {
                                    if (util_WorldGuard.playerInPVPRegion((Player) entity)) {
                                        PlayerStats ps = Main.getInstance().getPlayerStats((Player) entity);
                                        ps.UpdateDefence();
                                        double damage = AOEDamageAmount * (1 - (ps.magicPercentArmor * (1 - magicPen)));
                                        if (spell.fireTicks >= 2) {
                                            entity.setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks * 20 / 2));
                                        }
                                        Map<DamageModifier, Double> mapDM = Maps.newHashMap();
                                        mapDM.put(DamageModifier.BASE, damage);
                                        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity) (damagerStats.player), entity, DamageCause.LIGHTNING, mapDM, (Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO))));
                                    }
                                } else if ((entity instanceof LivingEntity) && entity.getType() != EntityType.ARMOR_STAND) {
                                    if (damagerStats.magicArmorPen > 0) {
                                        entity.setMetadata("DamageBefore=", new FixedMetadataValue(Main.getInstance(), AOEDamageAmount));
                                        entity.setMetadata("ArmorPen=", new FixedMetadataValue(Main.getInstance(), damagerStats.magicArmorPen));
                                    }
                                    Map<DamageModifier, Double> mapDM = Maps.newHashMap();
                                    mapDM.put(DamageModifier.BASE, AOEDamageAmount);
                                    Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent((Entity) (damagerStats.player), entity, DamageCause.LIGHTNING, mapDM, (Map<DamageModifier, ? extends Function<? super Double, Double>>) new EnumMap<DamageModifier, Function<? super Double, Double>>(ImmutableMap.of(DamageModifier.BASE, ZERO))));
                                    if (spell.fireTicks >= 2) {
                                        //((LivingEntity) entity).setFireTicks(spell.fireTicks*20/2);
                                        entity.setMetadata("FireTicks=", new FixedMetadataValue(Main.getInstance(), spell.fireTicks * 20 / 2));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
