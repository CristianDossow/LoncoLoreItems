package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
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
        plugin.activateEnchant.onInteractEntity(e);
    }

}
