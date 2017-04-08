package net.nifheim.yitan.loncoloremagics;

import org.bukkit.Effect;

public class Spell {
	String name;
	int lvl;
	int castype; // 1 Proyectile - 2 Self
	double speed;
	Effect hitEffect;
	double cooldown;
	double directHeal;
	double aoeHealAmount;
	double aoeHealRange;
	double directDamageAmount;
	double aoeDamageAmount;
	double aoeDamageRange;
	
	public Spell(String name, int lvl, int castype, double speed, Effect hitEffect) {
		super();
		this.name = name;
		this.lvl = lvl;
		this.castype = castype;
		this.speed = speed;
		this.hitEffect = hitEffect;
		this.cooldown = 0;
		this.aoeHealAmount=0;
		this.aoeHealRange=0;
		this.directDamageAmount=0;
		this.aoeDamageAmount=0;
		this.aoeDamageRange=0;
	}
	
}
