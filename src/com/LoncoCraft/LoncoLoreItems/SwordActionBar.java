package com.LoncoCraft.LoncoLoreItems;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.github.supavitax.itemlorestats.ItemLoreStats;

public class SwordActionBar extends BukkitRunnable{
    private final ItemLoreStats instance;
    private Player player;
    static DecimalFormat df = new DecimalFormat("#.#");
    Long startime;

    public SwordActionBar(ItemLoreStats plugin,Player player) {
        this.instance = plugin;
        this.player=player;
        this.startime = instance.damagefix.attackCooldowns.get(player.getUniqueId());
    }

	@Override
	public void run() {
		if(!player.getInventory().getItemInMainHand().getType().equals(Material.BOW)){
			if(startime==instance.damagefix.attackCooldowns.get(player.getUniqueId())){
				double weaponspeed = LoreUtils.getWeaponSpeed(player.getInventory().getItemInMainHand());
				if(weaponspeed >= 1){
					if(instance.damagefix.IsAttackInCooldown(player.getUniqueId())){
						double power = instance.damagefix.getAttackpower(player);
					    
					    ActionBarAPI.sendActionBar(player,ChatColor.YELLOW+""+ChatColor.BOLD+"Poder: "+ChatColor.GREEN+""+ChatColor.BOLD+df.format(power*100)+"%", 10);
					}else{
						ActionBarAPI.sendActionBar(player,ChatColor.YELLOW+""+ChatColor.BOLD+"Poder: "+ChatColor.RED+""+ChatColor.BOLD+"Listo!", 40);
						if(instance.damagefix.attackCooldownsEnd.get(player.getUniqueId())+2000 <System.currentTimeMillis())
							this.cancel();
					}
				}
				else{
					this.cancel();
				}
			}
			else{
				this.cancel();
			}
		}
		else{
			this.cancel();
		}
	}
}
