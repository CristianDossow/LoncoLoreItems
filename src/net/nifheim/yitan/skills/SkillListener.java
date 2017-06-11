package net.nifheim.yitan.skills;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import static org.bukkit.entity.EntityType.PLAYER;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SkillListener implements Listener {

    static SkillList sl = new SkillList();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack mainhand = event.getPlayer().getInventory().getItemInMainHand();
            if (event.getPlayer().isSneaking() && mainhand != null) {
                if (mainhand.hasItemMeta() && !mainhand.getType().equals(Material.PAPER)) {
                    if (mainhand.getItemMeta().hasLore()) {
                        sl.RunSkills(event.getPlayer(), mainhand);
                    }
                }
            }
        }
    }

//    @EventHandler
//    public void Damage(EntityDamageByEntityEvent e) {
//        Entity eAttacker = e.getDamager();
//        if (eAttacker instanceof Arrow) {
//            eAttacker = (Entity) ((Arrow) eAttacker).getShooter();
//        }
//        // No sé si alguna habilidad lanza bolas de fuego, así que mejor dejo esto por aquí
//        if (eAttacker instanceof Fireball) {
//            eAttacker = (Entity) ((Fireball) eAttacker).getShooter();
//        }
//        if (eAttacker instanceof SmallFireball) {
//            eAttacker = (Entity) ((SmallFireball) eAttacker).getShooter();
//        }
//        if (eAttacker instanceof Player) {
//            Player p = (Player) eAttacker;
//            ItemStack mainhand = p.getInventory().getItemInMainHand();
//            sl.RunSkills(p, mainhand);
//        }
//    }
}
