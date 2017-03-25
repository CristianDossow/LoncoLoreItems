package net.nifheim.yitan.loncoloreitems;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.nifheim.yitan.itemlorestats.Main;

public class SwordActionBar extends BukkitRunnable {
    private io.puharesource.mc.titlemanager.APIProvider tm;
    private final Main instance;
    private Player player;
    static DecimalFormat df = new DecimalFormat("#.#");
    Long startime;

    public SwordActionBar(Main plugin, Player player) {
        this.instance = plugin;
        this.player = player;
        this.startime = instance.damagefix.attackCooldowns.get(player.getUniqueId());
    }

    @Override
    public void run() {
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.BOW)) {
            if (startime == instance.damagefix.attackCooldowns.get(player.getUniqueId())) {
                double weaponspeed = LoreUtils.getWeaponSpeed(player.getInventory().getItemInMainHand());
                if (weaponspeed >= 1) {
                    if (instance.damagefix.IsAttackInCooldown(player.getUniqueId())) {
                        double power = instance.damagefix.getAttackpower(player);

                        tm.sendActionbar(player, ChatColor.YELLOW + "" + ChatColor.BOLD + "Poder: " + ChatColor.GREEN + "" + ChatColor.BOLD + df.format(power * 100) + "%");
                    } else {
                        tm.sendActionbar(player, ChatColor.YELLOW + "" + ChatColor.BOLD + "Poder: " + ChatColor.RED + "" + ChatColor.BOLD + "Listo!");
                        if (instance.damagefix.attackCooldownsEnd.get(player.getUniqueId()) + 2000 < System.currentTimeMillis()) {
                            this.cancel();
                        }
                    }
                } else {
                    this.cancel();
                }
            } else {
                this.cancel();
            }
        } else {
            this.cancel();
        }
    }
}
