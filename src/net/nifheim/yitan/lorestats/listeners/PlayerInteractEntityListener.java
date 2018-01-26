package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 *
 * @author Beelzebu
 */
public class PlayerInteractEntityListener implements Listener {

    private Main plugin;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        //plugin.activateEnchant.onInteractEntity(e);
    }

}
