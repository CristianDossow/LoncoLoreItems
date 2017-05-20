package net.nifheim.yitan.itemlorestats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.TextComponent;
import net.nifheim.yitan.StatsModifier.StatModifier;

public class MainFastRunnable extends BukkitRunnable { 
	
    private final Main instance;
    //HashMap<Player, BossBar> manaBar = new HashMap();

    public MainFastRunnable(Main plugin) {
        this.instance = plugin;
    }
	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()){
			PlayerStats ps = instance.getPlayerStats(player);
			ps.ManaRegen(0.25);
			float progress= (float) (ps.manaCurrent/ps.manaMax>=0 ? ps.manaCurrent/ps.manaMax : 0);
			String barText = (ChatColor.BLUE+""+ChatColor.BOLD+"Maná: "+ChatColor.BLUE.AQUA+(int)ps.manaCurrent+" / "+(int)ps.manaMax 
					+ChatColor.DARK_RED+""+ChatColor.BOLD+" Vida: "+ChatColor.RED+(int)ps.player.getHealth()+" / "+(int)ps.player.getMaxHealth()
					+ChatColor.GOLD+""+ChatColor.BOLD+" Nivel: "+ChatColor.YELLOW+ps.player.getLevel());
			if(instance.manaBar.containsKey(player)){
				
				instance.manaBar.get(player).setProgress(progress);
				instance.manaBar.get(player).setTitle(barText);
				if(0.20>progress)
					instance.manaBar.get(player).setColor(BarColor.RED);
				else if(0.50>progress)
					instance.manaBar.get(player).setColor(BarColor.YELLOW);
				else
					instance.manaBar.get(player).setColor(BarColor.BLUE);
			}
			else{
				BossBar bs = Bukkit.createBossBar(barText, BarColor.BLUE, BarStyle.SEGMENTED_10);
				progress = (float) (ps.manaCurrent/ps.manaMax>=0 ? ps.manaCurrent/ps.manaMax : 0);
				bs.setProgress(progress);
				bs.addPlayer(player);
				instance.manaBar.put(player, bs);
			}
			List<StatModifier> tr = new ArrayList<>();
			for(StatModifier sm : ps.Buffs){
				if(sm.getEndTime()<System.currentTimeMillis()){
					tr.add(sm);
					for(StatModifier boe : sm.getBuffOnEnd()){
						ps.addBuff(boe);
					}
					for(StatModifier boe : sm.getDeBuffOnEnd()){
						ps.addDeBuff(boe);
					}
				}
			}
			if(ps.Buffs.removeAll(tr))
				ps.UpdateAll();
			tr = new ArrayList<>();
			for(StatModifier sm : ps.DeBuffs){
				if(sm.getEndTime()<System.currentTimeMillis()){
					tr.add(sm);
					for(StatModifier boe : sm.getBuffOnEnd()){
						ps.addBuff(boe);
					}
					for(StatModifier boe : sm.getDeBuffOnEnd()){
						ps.addDeBuff(boe);
					}
				}
			}
			if(ps.DeBuffs.removeAll(tr))
				ps.UpdateAll();
			instance.setPlayerStats(ps);
		}
	}
}
