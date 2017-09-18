package net.nifheim.yitan.magic;

import de.slikey.effectlib.util.ParticleEffect;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;

public class Spell {

    public String name;
    public ChatColor colorName;
    public Color particleColor;
    public int lvl;
    public int castype; // 1 Proyectile - 2 Self
    public long lifeTime;
    public double speed;
    public ParticleEffect hitEffect;
    public long cooldown;
    public long warmup;
    public double directHeal;
    public double aoeHealAmount;
    public double aoeHealRange;
    public double directDamageAmount;
    public double aoeDamageAmount;
    public double aoeDamageRange;
    public double manaCost;
    public double postManaCost;
    public List<String> lore;
    public List<String> spellChants;
    public List<ParticleEffect> particleEffectSphere;
    public List<ParticleEffect> particleWarmupEffect;
    public double particleEffectSphereradio;
    public int particlesAmount;
    public int Interval;
    public float particleSpeed;
    public int fireTicks;

    public float particleSpeedOnHit;
    public double particleRadioOnhit;
    public int particlesAmountOnHit;

    public int onHitType;

    Sound soundOnCast;
    Sound soundOnHit;

    public Spell(String name, int lvl, int castype, double speed, ParticleEffect hitEffect) {
        super();
        this.colorName = ChatColor.GOLD;
        this.particleColor = Color.WHITE;
        this.name = name;
        this.lvl = lvl;
        this.castype = castype;
        this.lifeTime = 4000;
        this.speed = speed;
        this.hitEffect = hitEffect;
        this.cooldown = 0;
        this.warmup = 0;
        this.aoeHealAmount = 0;
        this.aoeHealRange = 0;
        this.directDamageAmount = 0;
        this.aoeDamageAmount = 0;
        this.aoeDamageRange = 0;
        this.manaCost = 0;
        this.postManaCost = 0;
        this.particlesAmount = 2;
        this.Interval = 2;
        this.particleSpeed = 0.025F;

        this.particleSpeedOnHit = 0.035F;
        this.particleRadioOnhit = 0.5;
        this.particlesAmountOnHit = 6;

        this.onHitType = 1;

        this.soundOnCast = null;
        this.soundOnHit = null;

        lore = new ArrayList<>();
        spellChants = new ArrayList<>();
        particleEffectSphereradio = 0;
        particleEffectSphere = new ArrayList<>();
        particleWarmupEffect = new ArrayList<>();
        fireTicks = 0;
    }

}
