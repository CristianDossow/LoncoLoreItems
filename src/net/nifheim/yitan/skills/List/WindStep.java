package net.nifheim.yitan.skills.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.nifheim.yitan.StatsModifier.StatModifier;
import net.nifheim.yitan.StatsModifier.StatModifierType;
import net.nifheim.yitan.StatsModifier.StatType;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import net.nifheim.yitan.skills.Skill;

public class WindStep extends Skill {
	
	public WindStep() {
		super();
		this.name="Paso Del Viento";
		this.cd=30000;
	}
	@Override
	public void Execute(PlayerStats ps){
		this.ps = ps;
		this.player=ps.player;
		StatModifier sm1 = new StatModifier(0,StatType.MOVEMENT_SPEED
        		,StatModifierType.ACCUMULATIVE,0.5,5000l,"Paso Del Viento");
        ps.addBuff(sm1);
        EffectManager em= new EffectManager(Main.plugin);
        CircleEffect effect = new de.slikey.effectlib.effect.CircleEffect(em);
        effect.particle = ParticleEffect.SPELL_MOB;
        effect.radius=1;
        effect.color=Color.GREEN;
        effect.duration=5000;
        effect.enableRotation = true;
        effect.setEntity(player);
        effect.start();
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 0);
        player.sendMessage(ChatColor.AQUA+"-Te sientes más ligero");
	}
}
