package net.nifheim.yitan.items;

import net.md_5.bungee.api.ChatColor;
import net.nifheim.yitan.lorestats.PlayerStats;

import java.text.DecimalFormat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Backstab enchant, this enchant adds extra damage to the backstab attacks.
 *
 * @author Beelzebu
 */
public class Backstab {

    static DecimalFormat df = new DecimalFormat("#.#");

    /*
    public Backstab() {
        super(EnchantTypes.SWORD, "Puñalada", "Causa un bonus de daño cuando atacas a tu enemigo por la espalda. El calculo del daño es: ((daño / 3) * nivel de skill)", 8);
    }

    @Override
    public boolean onDamageOther(final EntityDamageByEntityEvent e, Player p, int level) {
        if (e.getEntity().getLocation().getDirection().dot(e.getDamager().getLocation().getDirection()) > 0.0D) {
            e.setDamage(e.getDamage() + (0.4375 * level));

            p.sendMessage("§cUsaste Puñalada!");

            if (e.getEntity() instanceof Player) {
                ((Player) e.getEntity()).sendMessage("§cFuiste apuñalado!");
            }

            return true;
        }
        return false;
    }*/
    public static double checkStab(PlayerStats ps, Entity e, double damage, double bonus) {
        if (e.getLocation().getDirection().dot(ps.player.getLocation().getDirection()) > 0.0D) {
            if (ps.stab > Math.random()) {
                damage = damage * (1.5 + bonus);
                ps.player.sendMessage(ChatColor.RED + "Has Puñalado! (daño total " + df.format(damage) + ")");
                if (e instanceof Player) {
                    ((Player) e).sendMessage(ChatColor.DARK_RED + "Fuiste apuñalado! (daño total " + df.format(damage) + ")");
                }
            }
        }
        return damage;
    }
}
