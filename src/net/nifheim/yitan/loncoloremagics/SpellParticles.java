package net.nifheim.yitan.loncoloremagics;

import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.effect.DragonEffect;
import de.slikey.effectlib.effect.SphereEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.listeners.magicProjectileHit;

public class SpellParticles extends BukkitRunnable {

    private final Long maxTime;
    private final Main instance;
    Projectile projectile;
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
        em = new EffectManager(plugin);
        if (spell.particleEffectSphere != null) {
            for (ParticleEffect peffect : spell.particleEffectSphere) {
                SphereEffect effect = new SphereEffect(em);
                effect.particle = peffect;
                //effect.particleCount = 2;
                effect.particles = spell.particlesAmount;
                effect.radius = spell.particleEffectSphereradio;
                effect.setEntity(projectile);
                effect.delay = 0;
                effect.speed = spell.particleSpeed;
                effect.period = spell.Interval;
                effect.start();
            }
        }
    }

    @Override
    public void run() {

        if (startime + maxTime < System.currentTimeMillis()) {
            em.dispose();
            this.cancel();
            projectile.remove();
        }
        if (projectile.isDead()) {
            em2 = new EffectManager(instance);
            ParticleEffect peffect = null;
            peffect = spell.hitEffect;
            if (spell.soundOnCast != null) {
                projectile.getLocation().getWorld().playSound(projectile.getLocation(), spell.soundOnHit, 1, 1);
            }
            if (spell.onHitType == 1) {
                DragonEffect effect = new DragonEffect(em2);
                effect.particle = peffect;
                effect.particles = spell.particlesAmountOnHit;
                //effect.radius=spell.particleEffectSphereradio;
                effect.iterations = 1;
                effect.setLocation(projectile.getLocation());
                effect.delay = 0;
                effect.speed = spell.particleSpeedOnHit;
                effect.start();
            }
            if (spell.onHitType == 2) {
                SphereEffect effect = new SphereEffect(em2);
                effect.particle = peffect;
                effect.particles = spell.particlesAmountOnHit;
                effect.radius = spell.particleRadioOnhit;
                effect.iterations = 1;
                effect.setLocation(projectile.getLocation());
                effect.delay = 0;
                effect.speed = spell.particleSpeedOnHit;
                effect.start();
            }
            if (spell.onHitType == 3) {
                ConeEffect effect = new ConeEffect(em2);
                effect.particle = peffect;
                effect.particles = spell.particlesAmountOnHit;
                effect.radiusGrow = (float) spell.particleRadioOnhit;
                effect.iterations = 1;
                effect.setLocation(projectile.getLocation());
                effect.delay = 0;
                effect.speed = spell.particleSpeedOnHit;
                effect.start();
            }
            em.dispose();
            this.cancel();
            magicProjectileHit.onProjectileHitEvent(projectile);
            projectile.remove();
        }
    }

}
