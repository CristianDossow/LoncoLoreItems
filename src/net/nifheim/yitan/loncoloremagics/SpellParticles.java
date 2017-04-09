package net.nifheim.yitan.loncoloremagics;

import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import net.nifheim.yitan.itemlorestats.Main;

public class SpellParticles extends BukkitRunnable {
	
	private final Long maxTime = (long) 10000;
    private final Main instance;
    private final Projectile projectile;
    private final Spell spell;
    Long startime;

    public SpellParticles(Main plugin,Spell spell, Projectile projectile) {
        this.instance = plugin;
        this.projectile = projectile;
        this.spell = spell;
        this.startime = System.currentTimeMillis();
    }
	
	@Override
	public void run() {
		
		if(startime+maxTime>System.currentTimeMillis()){
			ParticleEffect.
		}
		else
			this.cancel();
		
	}

}
