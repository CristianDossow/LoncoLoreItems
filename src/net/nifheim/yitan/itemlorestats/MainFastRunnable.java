package net.nifheim.yitan.itemlorestats;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.TextComponent;

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
			float progress= (float) (ps.manaCurrent/ps.manaMax);
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
				bs.setProgress((float) (ps.manaCurrent/ps.manaMax));
				bs.addPlayer(player);
				instance.manaBar.put(player, bs);
			}
		}
	}
}
