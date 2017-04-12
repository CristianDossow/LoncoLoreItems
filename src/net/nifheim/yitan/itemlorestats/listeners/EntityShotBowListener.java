package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Beelzebu
 */
public class EntityShotBowListener implements Listener {
    private Main plugin;

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        if ((e.getEntity() instanceof Player)) {
            //Mark which bow shot the arrow
            Arrow arrow = (Arrow) e.getProjectile();
            Player shooter = (Player) e.getEntity();

            arrow.setMetadata("shooter", new FixedMetadataValue(plugin, shooter));
            arrow.setMetadata("bow", new FixedMetadataValue(plugin, shooter.getItemInHand()));
        }
    }

}
