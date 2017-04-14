package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerItemHeldListener implements Listener {

    @EventHandler
    public void onPlayerHeldItemChange(final PlayerItemHeldEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
            	Main.plugin.getPlayerStats(event.getPlayer()).UpdateAll();}
        }.runTaskLater(Main.plugin, 1);

        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
            final Player playerFinal = event.getPlayer();
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                ItemStack checkItemHeld = playerFinal.getInventory().getItem(event.getNewSlot());
                
                if ((checkItemHeld != null)
                        && (checkItemHeld.getType() != Material.AIR)
                        && (checkItemHeld.getItemMeta() != null)
                        && (checkItemHeld.getItemMeta().getLore() != null)
                        && (Main.plugin.isTool(checkItemHeld.getType()))) {
                    if ((Main.plugin.gearStats.phic_SoulboundNameItemInHand(checkItemHeld) != null)
                            && (!Main.plugin.gearStats.phic_SoulboundNameItemInHand(checkItemHeld).equals(playerFinal.getName()))) {
                        Main.plugin.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                        playerFinal.sendMessage(Main.plugin.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, String.valueOf(Main.plugin.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(Main.plugin.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld))));
                        
                        playerFinal.sendMessage(Main.plugin.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, Main.plugin.gearStats.phic_SoulboundNameItemInHand(checkItemHeld), Main.plugin.gearStats.phic_SoulboundNameItemInHand(checkItemHeld)));
                        
                        return;
                    }
                    
                    if ((Main.plugin.gearStats.phic_ClassItemInHand(checkItemHeld) != null)
                            && (!playerFinal.hasPermission("ils.use." + Main.plugin.gearStats.phic_ClassItemInHand(checkItemHeld)))) {
                        Main.plugin.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                        playerFinal.sendMessage(Main.plugin.util_GetResponse.getResponse("ClassRequirementMessages.NotRequiredClassForItemInHand", playerFinal, playerFinal, String.valueOf(Main.plugin.gearStats.phic_ClassItemInHand(checkItemHeld)), String.valueOf(Main.plugin.gearStats.phic_ClassItemInHand(checkItemHeld))));
                        
                        return;
                    }
                    
                    if ((Main.plugin.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld) != 0)
                            && (Main.plugin.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld) > playerFinal.getLevel())) {
                        Main.plugin.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                        playerFinal.sendMessage(Main.plugin.util_GetResponse.getResponse("LevelRequirementMessages.LevelTooLowForItemInHand", playerFinal, playerFinal, String.valueOf(Main.plugin.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(Main.plugin.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld))));
                        
                        return;
                    }
                }
                
                Main.plugin.updateHealth(playerFinal);
                Main.plugin.updatePlayerSpeed(playerFinal);
                
                Main.plugin.setBonuses.updateSetBonus(playerFinal);
            }, 2L);
        }
    }
}
