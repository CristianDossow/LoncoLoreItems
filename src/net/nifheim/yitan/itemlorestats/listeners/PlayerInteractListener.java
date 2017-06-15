package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
        //plugin.activateEnchant.onInteract(player, itemInHand, e);

        if (Action.RIGHT_CLICK_BLOCK.equals(e.getAction())
                && e.getPlayer().getEquipment().getItemInOffHand().getData().getItemType().equals(Material.DIAMOND_HOE)
                && e.getPlayer().getEquipment().getItemInOffHand().getItemMeta().isUnbreakable()
                //&& e.getPlayer().getEquipment().getItemInOffHand().getDurability() != 0
                && (e.getClickedBlock().getType().equals(Material.DIRT)
                || e.getClickedBlock().getType().equals(Material.GRASS)
                || e.getClickedBlock().getType().equals(Material.GRASS_PATH))) {
            e.setCancelled(true);
        }
    }
}
