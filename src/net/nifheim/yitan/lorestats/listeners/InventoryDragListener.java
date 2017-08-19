package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryDragListener implements Listener {

    public InventoryDragListener() {
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
            return;
        }
        if ((event.getInventory().getType().equals(InventoryType.CRAFTING))
                || (event.getInventory().getType().equals(InventoryType.PLAYER))
                || (event.getInventory().getType().equals(InventoryType.FURNACE))
                || (event.getInventory().getType().equals(InventoryType.DROPPER))
                || (event.getInventory().getType().equals(InventoryType.HOPPER))
                || (event.getInventory().getType().equals(InventoryType.DISPENSER))
                || (event.getInventory().getType().equals(InventoryType.CHEST))
                || (event.getInventory().getType().equals(InventoryType.ENCHANTING))
                || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) {
            Player player = (Player) event.getWhoClicked();

            if (event.getOldCursor() != null) {
                ItemStack item = event.getOldCursor().clone();

                if (!Main.getInstance().getConfig().getBoolean("usingMcMMO")) {
                    Main.getInstance().durability.syncArmourDurability(player);
                }

                if (Main.getInstance().getSlots.isOffHandSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))
                        || ((Main.getInstance().getSlots.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (Main.getInstance().isArmour(item.getType()))) || ((Main.getInstance().getSlots.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (player.getInventory().getHeldItemSlot() == Main.getInstance().getSlots.getRawHeldItemSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (Main.getInstance().isTool(item.getType())))) {
                    /*if (!Main.getInstance().xpLevel.checkXPLevel(player, item)) {
                        event.setCancelled(true);
                        player.updateInventory();

                        return;
                    }*/

                    if (!Main.getInstance().soulbound.checkSoulbound(player, item)) {
                        event.setCancelled(true);
                        player.updateInventory();

                        return;
                    }

                    if (!Main.getInstance().classes.checkClasses(player, item)) {
                        event.setCancelled(true);
                        player.updateInventory();

                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void healthIncreaseOnDragEquip(org.bukkit.event.inventory.InventoryDragEvent event) {
        if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
            return;
        }
        final Player player = (Player) event.getWhoClicked();

        if (((event.getInventory().getType().equals(InventoryType.CRAFTING))
                || (event.getInventory().getType().equals(InventoryType.PLAYER))
                || (event.getInventory().getType().equals(InventoryType.FURNACE))
                || (event.getInventory().getType().equals(InventoryType.DROPPER))
                || (event.getInventory().getType().equals(InventoryType.HOPPER))
                || (event.getInventory().getType().equals(InventoryType.DISPENSER))
                || (event.getInventory().getType().equals(InventoryType.CHEST))
                || (event.getInventory().getType().equals(InventoryType.ENCHANTING))
                || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) && ((Main.getInstance().getSlots.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) || (Main.getInstance().getSlots.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))))) {
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                player.updateInventory();
                Main.getInstance().updateHealth(player);
                Main.getInstance().updatePlayerSpeed(player);
                Main.getInstance().getPlayerStats(player).UpdateAll();
            }, 1L);
        }
    }
}
