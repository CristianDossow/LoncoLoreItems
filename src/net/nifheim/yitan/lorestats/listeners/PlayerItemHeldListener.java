package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
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
                Main.getInstance().getPlayerStats(event.getPlayer()).UpdateAll();
            }
        }.runTask(Main.getInstance());

        if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
            final Player playerFinal = event.getPlayer();
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                ItemStack checkItemHeld = playerFinal.getInventory().getItem(event.getNewSlot());

                if ((checkItemHeld != null)
                        && (checkItemHeld.getType() != Material.AIR)
                        && (checkItemHeld.getItemMeta() != null)
                        && (checkItemHeld.getItemMeta().getLore() != null)
                        && (Main.getInstance().isTool(checkItemHeld.getType()))) {
                    if ((Main.getInstance().gearStats.phic_SoulboundNameItemInHand(checkItemHeld) != null)
                            && (!Main.getInstance().gearStats.phic_SoulboundNameItemInHand(checkItemHeld).equals(playerFinal.getName()))) {
                        Main.getInstance().swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                        playerFinal.sendMessage(Main.getInstance().util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, String.valueOf(Main.getInstance().gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(Main.getInstance().gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld))));

                        playerFinal.sendMessage(Main.getInstance().util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, Main.getInstance().gearStats.phic_SoulboundNameItemInHand(checkItemHeld), Main.getInstance().gearStats.phic_SoulboundNameItemInHand(checkItemHeld)));

                        return;
                    }

                    if ((Main.getInstance().gearStats.phic_ClassItemInHand(checkItemHeld) != null)
                            && (!playerFinal.hasPermission("ils.use." + Main.getInstance().gearStats.phic_ClassItemInHand(checkItemHeld)))) {
                        Main.getInstance().swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                        playerFinal.sendMessage(Main.getInstance().util_GetResponse.getResponse("ClassRequirementMessages.NotRequiredClassForItemInHand", playerFinal, playerFinal, String.valueOf(Main.getInstance().gearStats.phic_ClassItemInHand(checkItemHeld)), String.valueOf(Main.getInstance().gearStats.phic_ClassItemInHand(checkItemHeld))));

                        return;
                    }
                    /*
                    if ((Main.getInstance().gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld) != 0)
                            && (Main.getInstance().gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld) > playerFinal.getLevel())) {
                        Main.getInstance().swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                        playerFinal.sendMessage(Main.getInstance().util_GetResponse.getResponse("LevelRequirementMessages.LevelTooLowForItemInHand", playerFinal, playerFinal, String.valueOf(Main.getInstance().gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(Main.getInstance().gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld))));
                        
                        return;
                    }*/
                }

                Main.getInstance().updateHealth(playerFinal);
                Main.getInstance().updatePlayerSpeed(playerFinal);
                Main.getInstance().setBonuses.updateSetBonus(playerFinal);
                Main.getInstance().getPlayerStats(playerFinal).UpdateAll();
            }, 2L);
        }
    }
}
