package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

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

                if (!Main.plugin.getConfig().getBoolean("usingMcMMO")) {
                    Main.plugin.durability.syncArmourDurability(player);
                }

                if (Main.plugin.getSlots.isOffHandSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))
                        || ((Main.plugin.getSlots.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (Main.plugin.isArmour(item.getType()))) || ((Main.plugin.getSlots.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (player.getInventory().getHeldItemSlot() == Main.plugin.getSlots.getRawHeldItemSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (Main.plugin.isTool(item.getType())))) {
                    /*if (!Main.plugin.xpLevel.checkXPLevel(player, item)) {
                        event.setCancelled(true);
                        player.updateInventory();

                        return;
                    }*/

                    if (!Main.plugin.soulbound.checkSoulbound(player, item)) {
                        event.setCancelled(true);
                        player.updateInventory();

                        return;
                    }

                    if (!Main.plugin.classes.checkClasses(player, item)) {
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
                || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) && ((Main.plugin.getSlots.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) || (Main.plugin.getSlots.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))))) {
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                player.updateInventory();
                Main.plugin.updateHealth(player);
                Main.plugin.updatePlayerSpeed(player);
                Main.plugin.getPlayerStats(player).UpdateAll();
            }, 1L);
        }
    }
}
