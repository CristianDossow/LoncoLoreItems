package net.nifheim.yitan.loncoloremagics;

import org.bukkit.Location;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import net.nifheim.yitan.loncoloreitems.SwordActionBar;

public class SpellCast {

    public static void spellBuilder(Spell spell, Player player) {
    	PlayerStats ps = Main.plugin.getPlayerStats(player);
    	ps.UpdateAll();
    	if(ps.manaCurrent>=spell.manaCost){
    		if(ps.spellCastWait<System.currentTimeMillis()){
    			player.sendMessage("lanzando " + spell.name);
    			ps.lastMessage=System.currentTimeMillis();
    			ps.lastSpellCast = System.currentTimeMillis();
    			ps.spellCastWait = System.currentTimeMillis() + (long)(spell.cooldown*(1-ps.spellCooldownReduction));
        		ps.manaCurrent = ps.manaCurrent - spell.manaCost;
                Projectile projectile = getProjectile(spell, player);
                projectile.setShooter(player);
                projectile.setVelocity(player.getLocation().getDirection().multiply(spell.speed));
                setProjectileProperties(projectile, spell, ps);
                BukkitTask task = new SpellParticles(Main.getInstance(),spell,projectile).runTaskTimer(Main.getInstance(), 0, 2);
    		}
    		else{
    			if(ps.lastMessage+1000<System.currentTimeMillis()){
        			long wait= (ps.spellCastWait-System.currentTimeMillis())/1000;
            		player.sendMessage("Debes esperar "+wait+" segundos mas para usar otro hechizo");
            		ps.lastMessage=System.currentTimeMillis();
    			}

        	}
    	}
    	else{
    		if(ps.lastMessage+2000<System.currentTimeMillis()){
    		player.sendMessage("Necesitas más maná!");
    		ps.lastMessage=System.currentTimeMillis();
    		}
    	}
    }

    public static Projectile getProjectile(Spell spell, Player player) {
        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());

        LlamaSpit projectile = (LlamaSpit) player.getWorld().spawn(loc, LlamaSpit.class);
        projectile.setGravity(false);
        //projectile.setGlowingTicks(0);
        //projectile.setGlowing(true);
        //projectile.setMetadata("ILS_DragonFireball", new FixedMetadataValue(Main.getInstance(), player.getName()));
        return projectile;

    }

    public static void setProjectileProperties(Projectile projectile, Spell spell, PlayerStats ps) {
        double DHA = spell.directHeal * ps.magicPower;
        double AHA = spell.aoeHealAmount * ps.magicPower;
        double DDA = spell.directDamageAmount * ps.magicPower;
        double ADA = spell.aoeDamageAmount * ps.magicPower;

        projectile.setFireTicks(spell.fireTicks*20);
        projectile.setMetadata("SPELLNAME=", new FixedMetadataValue(Main.getInstance(), spell.name));
        projectile.setMetadata("MAGICPEN=", new FixedMetadataValue(Main.getInstance(), ps.magicArmorPen));
        projectile.setMetadata("DHA=", new FixedMetadataValue(Main.getInstance(), DHA));
        projectile.setMetadata("AHA=", new FixedMetadataValue(Main.getInstance(), AHA));
        projectile.setMetadata("AHR=", new FixedMetadataValue(Main.getInstance(), spell.aoeHealRange));
        projectile.setMetadata("DDA=", new FixedMetadataValue(Main.getInstance(), DDA));
        projectile.setMetadata("ADA=", new FixedMetadataValue(Main.getInstance(), ADA));
        projectile.setMetadata("ADR=", new FixedMetadataValue(Main.getInstance(), spell.aoeDamageRange));

        if ((DHA > 0.0D) || (AHA > 0.0D)) {
            projectile.setMetadata("Heal=", new FixedMetadataValue(Main.getInstance(), true));
        }

        if ((DDA > 0.0D) || (ADA > 0.0D)) {
            projectile.setMetadata("Damage=", new FixedMetadataValue(Main.getInstance(), true));
        }
    }
}
