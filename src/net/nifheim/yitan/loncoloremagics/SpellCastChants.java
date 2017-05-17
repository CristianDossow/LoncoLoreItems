package net.nifheim.yitan.loncoloremagics;

import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.util.ParticleEffect;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;

public class SpellCastChants extends BukkitRunnable {

    private Long lastChant;
    private final Long endtime;
    private final Main instance;
    private final Spell spell;
    EffectManager em;
    Long chantTime;
    List<String> chants;
    int chantsDone;
    Player player;
    PlayerStats ps;
    HelixEffect effect1;
    CircleEffect effect2;

    public SpellCastChants(Main plugin, Player player, PlayerStats ps, Spell spell) {
    	this.ps = ps;
    	this.player = player;
    	this.chantsDone=0;
        this.instance = plugin;
        this.spell = spell;
        this.lastChant = System.currentTimeMillis();
        this.endtime = System.currentTimeMillis()+spell.warmup;
        chants = spell.spellChants;
        if(chants.size()>0){
        	chantTime =spell.warmup/chants.size();
        }else{
        	chantTime = spell.warmup;
        }
        em= new EffectManager(plugin);
        if(spell.particleEffectSphere!=null){
        	for(ParticleEffect peffect : spell.particleWarmupEffect){
        	effect1 = new de.slikey.effectlib.effect.HelixEffect(em); 
        	effect2 = new de.slikey.effectlib.effect.CircleEffect(em); 
            effect1.particle=peffect;
            effect2.particle=peffect;
            //effect1.color=spell.particleColor;
            //effect2.color=spell.particleColor;
            effect2.enableRotation = true;
            //effect1.particleCount = 2;
            //effect.particleCount = 2;
            //effect.particles=20;
            effect1.radius=3;
            effect2.radius=2;
            //effect.radius=3;
    		//effect.delay=0;
    		//effect1.speed=1;
    		//effect2.speed=1;
    		//effect.period = spell.Interval;
    		//effect1.setLocation(player.getLocation());
            effect1.setEntity(player);
            effect1.setLocation(player.getLocation());
    		effect2.setEntity(player);
    		effect1.start();
    		effect2.start();
        	}
        }
    }
	
	@Override
	public void run(){
		if(endtime>System.currentTimeMillis()){
			if(effect1!=null)
				effect1.setLocation(player.getLocation());
			if(lastChant+chantTime<System.currentTimeMillis()&&chantsDone<chants.size()-1){
				String line = chants.get(chantsDone);
				//line = line.replace("%name%", player.getCustomName());
				player.sendMessage(player.getDisplayName()+": "+line);
				for (Iterator<Entity> iterator = player.getNearbyEntities(60, 60, 60).iterator(); iterator.hasNext();) {
                    Entity entity = (Entity) iterator.next();
                    if (entity instanceof Player) {
                    	entity.sendMessage(player.getDisplayName()+": "+line);
                    }
				}
				//Bukkit.getServer().dispatchCommand(player, "local "+line);
				//player.sendMessage("local "+line);
				//player.performCommand("l "+line);
				lastChant = System.currentTimeMillis();
				chantsDone++;
			}
		}else{
			if(chantsDone==chants.size()-1){
				String line = chants.get(chantsDone);
				//line = line.replace("%name%", player.getCustomName());
				player.sendMessage(player.getDisplayName()+": "+line);
				for (Iterator<Entity> iterator = player.getNearbyEntities(60, 60, 60).iterator(); iterator.hasNext();) {
                    Entity entity = (Entity) iterator.next();
                    if (entity instanceof Player) {
                    	entity.sendMessage(player.getDisplayName()+": "+line);
                    }
				}
				//Bukkit.getServer().dispatchCommand(player, "local "+line);
				//player.sendMessage("local "+line);
				//player.performCommand("l "+line);
			}
			em.dispose();
			this.cancel();
			SpellCast.Cast(player, ps, spell);
		}
	}
}
