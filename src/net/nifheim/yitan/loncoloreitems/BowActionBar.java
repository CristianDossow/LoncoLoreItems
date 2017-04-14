package net.nifheim.yitan.loncoloreitems;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.nifheim.yitan.itemlorestats.Main;

public class BowActionBar extends BukkitRunnable {

    private final Main instance;
    private final Player player;
    static DecimalFormat df = new DecimalFormat("#.#");
    Long startime;

    public BowActionBar(Main plugin, Player player) {
        this.instance = plugin;
        this.player = player;
        this.startime = instance.eventlistener.bowCooldowns.get(player.getUniqueId());
    }

    @Override
    public void run() {
        if (player.getInventory().getItemInMainHand().getType().equals(Material.BOW)) {
            if (instance.eventlistener.bowActionControl.get(player.getUniqueId())) {
                Long time = System.currentTimeMillis() - instance.eventlistener.bowCooldowns.get(player.getUniqueId());
                double weaponspeed = EspecialAtributes.getWeaponSpeed(player.getInventory().getItemInMainHand());
                Long fulltime = (long) (weaponspeed * 1000);
                if ((double) time / (double) fulltime < 1) {
                    ActionBarAPI.sendActionBar(player, ChatColor.YELLOW + "" + ChatColor.BOLD + "Poder: " + ChatColor.GREEN + "" + ChatColor.BOLD + df.format((double) time / (double) fulltime * 100) + "%", 10);
                } else {

                    ActionBarAPI.sendActionBar(player, ChatColor.YELLOW + "" + ChatColor.BOLD + "Poder: " + ChatColor.RED + "" + ChatColor.BOLD + "Listo!", 40);
                    if (startime + fulltime + 2000 < System.currentTimeMillis()) {
                        this.cancel();
                    }
                }
            } else {
                this.cancel();
            }
        } else {
            this.cancel();
        }

    }

}
