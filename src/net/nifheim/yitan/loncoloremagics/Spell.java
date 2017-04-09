package net.nifheim.yitan.loncoloremagics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;

import de.slikey.effectlib.util.ParticleEffect;

public class Spell {
	public String name;
	public int lvl;
	public int castype; // 1 Proyectile - 2 Self
	public long lifeTime;
	public double speed;
	public Effect hitEffect;
	public double cooldown;
	public double directHeal;
	public double aoeHealAmount;
	public double aoeHealRange;
	public double directDamageAmount;
	public double aoeDamageAmount;
	public double aoeDamageRange;
	public List<String> lore;
	public ParticleEffect particleEffectSphere;
	public double particleEffectSphereradio;
	
	public Spell(String name, int lvl, int castype, double speed, Effect hitEffect) {
		super();
		this.name = name;
		this.lvl = lvl;
		this.castype = castype;
		this.lifeTime=4000;
		this.speed = speed;
		this.hitEffect = hitEffect;
		this.cooldown = 0;
		this.aoeHealAmount=0;
		this.aoeHealRange=0;
		this.directDamageAmount=0;
		this.aoeDamageAmount=0;
		this.aoeDamageRange=0;
		lore=new ArrayList<>();
		particleEffectSphereradio=0;
		particleEffectSphere=null;
	}
	
}
