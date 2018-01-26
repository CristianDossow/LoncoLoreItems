package net.nifheim.yitan.lorestats.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

/**
 *
 * @author Beelzebu
 */
public class EntityShotBowListener implements Listener {
    //private final Main plugin = Main.getInstance();

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        //if ((e.getEntity() instanceof Player)) {
        //Mark which bow shot the arrow
        //Arrow arrow = (Arrow) e.getProjectile();
        //Player shooter = (Player) e.getEntity();

        //arrow.setMetadata("shooter", new FixedMetadataValue(plugin, shooter.getUniqueId()));
        //arrow.setMetadata("bow", new FixedMetadataValue(plugin, shooter.getItemInHand()));
        //}
    }

}
