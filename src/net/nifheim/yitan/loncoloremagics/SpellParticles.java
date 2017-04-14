package net.nifheim.yitan.loncoloremagics;

import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.DragonEffect;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.nifheim.yitan.itemlorestats.Main;


public class SpellParticles extends BukkitRunnable {

    private final Long maxTime;
    private final Main instance;
    private final Projectile projectile;
    private final Spell spell;
    Long startime;
    EffectManager em;
    EffectManager em2;

    public SpellParticles(Main plugin, Spell spell, Projectile projectile) {
        this.instance = plugin;
        this.projectile = projectile;
        this.spell = spell;
        this.startime = System.currentTimeMillis();
        this.maxTime = spell.lifeTime;
        em= new EffectManager(plugin);
        if(spell.particleEffectSphere!=null){
            ParticleEffect peffect=null;
            peffect = spell.particleEffectSphere;
            SphereEffect effect = new SphereEffect(em); 
            effect.particle=peffect;
            //effect.particleCount = 2;
            effect.particles=2;
            effect.radius=spell.particleEffectSphereradio;
    		effect.setEntity(projectile);
    		effect.delay=0;
    		effect.speed=0.025F;
    		effect.period = 2;
    		effect.start();
    		projectile.setGlowing(true);
        }
    }
	
	@Override
	public void run() {
		
		if(startime+maxTime<System.currentTimeMillis()){
			em.dispose();
			this.cancel();
			projectile.remove();
		}
		if(projectile.isDead()){
			em2= new EffectManager(instance);
            ParticleEffect peffect=null;
            peffect = spell.hitEffect;
            DragonEffect effect = new DragonEffect(em2); 
            effect.particle=peffect;
            effect.particles=6;
            //effect.radius=spell.particleEffectSphereradio;
            effect.iterations=1;
            effect.setLocation(projectile.getLocation());
    		effect.delay=0;
    		effect.speed=0.035F;
    		effect.start();
			em.dispose();
			this.cancel();
			projectile.remove();
		}
	}

}
