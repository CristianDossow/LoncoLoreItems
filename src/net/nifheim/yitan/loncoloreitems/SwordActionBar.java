package net.nifheim.yitan.loncoloreitems;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import java.text.DecimalFormat;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.nifheim.yitan.itemlorestats.Main;

public class SwordActionBar extends BukkitRunnable {
    private final Main instance;
    private final Player player;
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
            if (Objects.equals(startime, instance.damagefix.attackCooldowns.get(player.getUniqueId()))) {
                double weaponspeed = LoreUtils.getWeaponSpeed(player.getInventory().getItemInMainHand());
                if (weaponspeed >= 1) {
                    if (instance.damagefix.IsAttackInCooldown(player.getUniqueId())) {
                        double power = instance.damagefix.getAttackpower(player);
                        ActionBarAPI.sendActionBar(player.getPlayer(), "§e§lPoder: §a§l" + df.format(power * 100) + "%", 10);
                    } else {
                        ActionBarAPI.sendActionBar(player.getPlayer(),"§e§lPoder: §cListo!", 40);
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
