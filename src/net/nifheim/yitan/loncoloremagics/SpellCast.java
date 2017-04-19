package net.nifheim.yitan.loncoloremagics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.inventivetalent.glow.GlowAPI;

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
                if(spell.soundOnCast!=null){
                	player.getLocation().getWorld().playSound(projectile.getLocation(), spell.soundOnCast, 1, 1);
                }
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
        //GlowAPI.setGlowing(projectile, GlowAPI.Color.DARK_RED, Bukkit.getOnlinePlayers());
        //Main.scoreboard.getTeam("RedCT").addEntry(projectile.getUniqueId().toString());
        //projectile.setCustomName("asdasd1");
        //Main.scoreboard.getTeam("RedCT").addEntry(projectile.getCustomName());

        
        projectile.setGlowing(true);
        return projectile;
    }

    public static void setProjectileProperties(Projectile projectile, Spell spell, PlayerStats ps) {
    	double max = ps.magicPower;
        double min = max * 0.8;
        double power = Math.random() * (max - min) + min;
        double DHA = spell.directHeal * power;
        double AHA = spell.aoeHealAmount * power;
        double DDA = spell.directDamageAmount * power;
        double ADA = spell.aoeDamageAmount * power;

        //projectile.setFireTicks(spell.fireTicks*20);
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
