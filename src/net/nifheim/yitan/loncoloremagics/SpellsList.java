package net.nifheim.yitan.loncoloremagics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Sound;

import de.slikey.effectlib.util.ParticleEffect;

public class SpellsList {
	static String languageRegex= "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

	public static Spell magicDard(){
		Spell spell = new Spell("Dardo Mágico",1,1,1,ParticleEffect.FIREWORKS_SPARK);
		spell.directDamageAmount=1;
		spell.particleEffectSphere.add(ParticleEffect.FIREWORKS_SPARK);
		spell.particleEffectSphereradio=0.3;
		spell.manaCost=25;
		spell.cooldown = 3000;
		spell.lore.add("Hechizo básico");
		spell.colorName = ChatColor.RED;
		spell.soundOnCast=Sound.ENTITY_FIREWORK_LAUNCH;
		spell.soundOnHit=Sound.ENTITY_PLAYER_BREATH;
		return spell;
	}
	
	public static Spell fireBall(){
		Spell spell = new Spell("Bola de Fuego",1,1,1,ParticleEffect.FLAME);
		spell.directDamageAmount=1.2;
		spell.particleEffectSphere.add(ParticleEffect.FLAME);
		spell.particleEffectSphereradio=0.3;
		spell.lore.add("Hechizo de fuego");
		spell.manaCost = 40;
		spell.cooldown = 4000;
		spell.fireTicks=4;
		spell.colorName = ChatColor.RED;
		spell.soundOnCast=Sound.ENTITY_GHAST_SHOOT;
		spell.soundOnHit=Sound.BLOCK_FIRE_AMBIENT;
		return spell;
	}
	public static Spell fireBlast(){
		Spell spell = new Spell("Estallido de fuego",1,1,1,ParticleEffect.FLAME);
		spell.directDamageAmount=1.4;
		spell.aoeDamageAmount=0.5;
		spell.aoeDamageRange=2;
		spell.particlesAmount=2;
		spell.Interval=2;
		spell.particleEffectSphere.add(ParticleEffect.FLAME);
		spell.particleEffectSphereradio=0.4;
		spell.particlesAmountOnHit=40;
		spell.particleRadioOnhit=2;
		spell.particleSpeedOnHit=0.04F;
		spell.onHitType=2;
		spell.lore.add("Hechizo de fuego poderoso que");
		spell.lore.add("daña a los seres vivos cercanos");
		spell.manaCost = 60;
		spell.cooldown = 5000;
		spell.fireTicks=4;
		spell.colorName = ChatColor.RED;
		spell.soundOnCast=Sound.ENTITY_GHAST_SHOOT;
		spell.soundOnHit=Sound.ENTITY_GENERIC_EXPLODE;
		return spell;
	}
	public static Spell thunder(){
		Spell spell = new Spell("Ira Eléctrica",1,1,1,ParticleEffect.VILLAGER_ANGRY);
		spell.directDamageAmount=2;
		spell.particleEffectSphere.add(ParticleEffect.VILLAGER_ANGRY);
		spell.particleEffectSphereradio=0.2;
		spell.particlesAmountOnHit=8;
		spell.particleSpeedOnHit=0.05F;
		spell.particleRadioOnhit=1;
		spell.onHitType=2;
		spell.lore.add("Hechizo de rayos");
		spell.manaCost = 100;
		spell.cooldown = 6000;
		spell.colorName = ChatColor.RED;
		spell.soundOnCast=Sound.ENTITY_WITHER_SHOOT;
		spell.soundOnHit=Sound.ENTITY_ZOMBIE_INFECT;
		return spell;
	}
	public static Spell Cataclism(){
		Spell spell = new Spell("Cataclismo",1,1,1,ParticleEffect.EXPLOSION_HUGE);
		spell.particlesAmount=1;
		spell.directDamageAmount=3;
		spell.aoeDamageAmount=1;
		spell.aoeDamageRange=4;
		spell.particleEffectSphere.add(ParticleEffect.REDSTONE);
		spell.particleEffectSphere.add(ParticleEffect.END_ROD);
		spell.particleEffectSphere.add(ParticleEffect.LAVA);
		spell.particleEffectSphere.add(ParticleEffect.SNOW_SHOVEL);
		spell.particleEffectSphereradio=0.5;
		spell.particleRadioOnhit=0.5;
		spell.particlesAmountOnHit=3;
		spell.particleSpeedOnHit=0.01F;
		spell.onHitType=2;
		spell.lore.add("Cataclismo");
		spell.manaCost = 250;
		spell.cooldown = 6000;
		spell.colorName = ChatColor.DARK_RED;
		spell.soundOnCast=Sound.ENTITY_GHAST_SHOOT;
		spell.soundOnHit=Sound.ENTITY_GENERIC_EXPLODE;
		return spell;
	}
	public static Spell Explosion(){
		Spell spell = new Spell("Explosion",1,1,1,ParticleEffect.EXPLOSION_HUGE);
		spell.particlesAmount=4;
		spell.directDamageAmount=20;
		spell.aoeDamageAmount=5;
		spell.aoeDamageRange=10;
		spell.particleEffectSphere.add(ParticleEffect.FLAME);
		spell.particleWarmupEffect.add(ParticleEffect.FLAME);
		spell.particleEffectSphereradio=0.8;
		spell.particleRadioOnhit=8;
		spell.particlesAmountOnHit=8;
		spell.particleSpeedOnHit=0.01F;
		spell.onHitType=2;
		spell.manaCost = 750;
		spell.postManaCost = 4250;
		spell.warmup = 6000;
		spell.cooldown = 60000;
		spell.colorName = ChatColor.DARK_RED;
		spell.soundOnCast=Sound.ENTITY_GHAST_SHOOT;
		spell.soundOnHit=Sound.ENTITY_GENERIC_EXPLODE;
		spell.particleColor = Color.RED;
		spell.lore.add("Explosion");
		spell.spellChants.add("Más oscuro que el negro");
		spell.spellChants.add("más sombrio que la oscuridad");
		spell.spellChants.add("combínense con mi carmesí interior");
		spell.spellChants.add("la hora de su despertar ha llegado");
		spell.spellChants.add("desciendan hasta estas fronteras");
		spell.spellChants.add("y aparezcan como una distorsión intangible");
		spell.spellChants.add("!!Explosion!!");
		return spell;
	}
	
	public static List<Spell> getSpellList(){
		List<Spell> list = new ArrayList<>();
		list.add(magicDard());
		list.add(fireBall());
		list.add(thunder());
		list.add(Cataclism());
		list.add(fireBlast());
		list.add(Explosion());
		return list;
	}
	public static Spell getSpell(String name){
		Spell finalspell = null;
		for(Spell spell : getSpellList()){
			if(spell.name.replaceAll(languageRegex, "").toLowerCase().equals(name.replaceAll(languageRegex,"").toLowerCase()))
				finalspell=spell;
		}
		return finalspell;
	}
}
