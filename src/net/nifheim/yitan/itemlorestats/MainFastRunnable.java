package net.nifheim.yitan.itemlorestats;

import java.util.HashMap;

import org.bukkit.Bukkit;
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
			ps.ManaRegen();
			if(instance.manaBar.containsKey(player)){
				instance.manaBar.get(player).setProgress((float) (ps.manaCurrent/ps.manaMax));
				instance.manaBar.get(player).setTitle("Maná: "+(int)ps.manaCurrent+" / "+(int)ps.manaMax);
				
			}
			else{
				BossBar bs = Bukkit.createBossBar("Maná: "+(int)ps.manaCurrent+" / "+(int)ps.manaMax, BarColor.BLUE, BarStyle.SEGMENTED_20);
				bs.setProgress((float) (ps.manaCurrent/ps.manaMax));
				bs.addPlayer(player);
				instance.manaBar.put(player, bs);
			}
		}
	}
}
