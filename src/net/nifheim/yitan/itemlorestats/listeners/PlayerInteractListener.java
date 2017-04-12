package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Beelzebu
 */
public class PlayerInteractListener implements Listener {

    private Main plugin;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();
        Player player = e.getPlayer();
        plugin.activateEnchant.onInteract(player, itemInHand, e);
    }
}
