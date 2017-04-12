package net.nifheim.beelzebu.rpgcore.enchants.sword;

import net.nifheim.beelzebu.rpgcore.enchants.Enchant;
import net.nifheim.beelzebu.rpgcore.enchants.EnchantType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Backstab enchant, this enchant adds extra damage to the backstab attacks.
 * @author Beelzebu
 */
public class Backstab extends Enchant {

    public Backstab() {
        super(EnchantType.SWORD, "Puñalada", "Causa un bonus de daño cuando atacas a tu enemigo por la espalda. El calculo del daño es: ((daño / 3) * nivel de skill)", 8);
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
    }
}
