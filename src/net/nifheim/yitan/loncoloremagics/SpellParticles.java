package net.nifheim.yitan.loncoloremagics;

import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.SphereEffect;
import net.nifheim.yitan.itemlorestats.Main;


public class SpellParticles extends BukkitRunnable {

    private final Long maxTime = (long) 10000;
    private final Main instance;
    private final Projectile projectile;
    private final Spell spell;
    Long startime;
    EffectManager em;

    public SpellParticles(Main plugin, Spell spell, Projectile projectile) {
        this.instance = plugin;
        this.projectile = projectile;
        this.spell = spell;
        this.startime = System.currentTimeMillis();
        
        em= new EffectManager(plugin);
		Effect effect = new SphereEffect(em); 
		effect.setEntity(projectile);
		effect.start();
    }
	
	@Override
	public void run() {
		
		if(startime+maxTime<System.currentTimeMillis()){
			em.dispose();
			this.cancel();
		}
	}

}
