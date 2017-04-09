package net.nifheim.yitan.loncoloremagics;

import java.io.File;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;

import net.nifheim.yitan.itemlorestats.Main;

public class SpellCast {
	   public static void spellBuilder(Spell spell, Player player)
	   {
	     Projectile projectile = getProjectile(spell, player);
	     projectile.setShooter(player);
	     projectile.setVelocity(player.getLocation().getDirection().multiply(spell.speed));
	     setProjectileProperties(projectile, spell);
	   }
	   
	   public static Projectile getProjectile(Spell spell,Player player) {
	     Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
	     
	     LlamaSpit projectile = (LlamaSpit)player.getWorld().spawn(loc, LlamaSpit.class);
	     projectile.setGravity(false);
	     projectile.setGlowing(true);
	     //projectile.setMetadata("ILS_DragonFireball", new FixedMetadataValue(Main.getPlugin(), player.getName()));
	     return projectile;
	     
	   }
	   
	   public static void setProjectileProperties(Projectile projectile, Spell spell) {
	     double DHA = spell.directHeal;
	     double AHA = spell.aoeHealAmount;
	     double DDA = spell.directDamageAmount;
	     double ADA = spell.aoeDamageAmount;
	     
	     projectile.setMetadata("SPELLNAME=", new FixedMetadataValue(Main.getPlugin(), spell.name));
	     projectile.setMetadata("DHA=", new FixedMetadataValue(Main.getPlugin(), Double.valueOf(DHA)));
	     projectile.setMetadata("AHA=", new FixedMetadataValue(Main.getPlugin(), Double.valueOf(AHA)));
	     projectile.setMetadata("AHR=", new FixedMetadataValue(Main.getPlugin(), Double.valueOf(spell.aoeHealRange)));
	     projectile.setMetadata("DDA=", new FixedMetadataValue(Main.getPlugin(), Double.valueOf(DDA)));
	     projectile.setMetadata("ADA=", new FixedMetadataValue(Main.getPlugin(), Double.valueOf(ADA)));
	     projectile.setMetadata("ADR=", new FixedMetadataValue(Main.getPlugin(), Double.valueOf(spell.aoeDamageRange)));
	     
	     if ((DHA > 0.0D) || (AHA > 0.0D)) {
	       projectile.setMetadata("Heal=", new FixedMetadataValue(Main.getPlugin(), Boolean.valueOf(true)));
	     }
	     
	     if ((DDA > 0.0D) || (ADA > 0.0D)) {
	       projectile.setMetadata("Damage=", new FixedMetadataValue(Main.getPlugin(), Boolean.valueOf(true)));
	     }
	   }
}
