package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
            return;
        }
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getWhoClicked().getWorld().getName())) {

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

                if (event.getCurrentItem() != null) {
                    ItemStack item = event.getCursor().clone();

                    if (event.isShiftClick()) {
                        item = event.getCurrentItem().clone();
                    }
                    if (!Main.plugin.getConfig().getBoolean("usingMcMMO")) {
                        Main.plugin.durability.syncArmourDurability(player);
                    }

                    if ((event.getSlot() == 45) || (event.getRawSlot() == 45)
                            || ((event.getSlotType().equals(InventoryType.SlotType.ARMOR)) && (Main.plugin.isArmour(item.getType())))
                            || ((event.isShiftClick())) || ((event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) && (event.getSlot() == player.getInventory().getHeldItemSlot()) && (Main.plugin.isTool(item.getType())))) {
                        if (!Main.plugin.xpLevel.checkXPLevel(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();

                            return;
                        }

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
    }

    @EventHandler
    public void healthIncreaseOnEquip(InventoryClickEvent event) {
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
                || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) && ((event.getSlotType().equals(InventoryType.SlotType.ARMOR)) || (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) || (event.isShiftClick()))) {
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    player.updateInventory();
                    Main.plugin.updateHealth(player);
                    Main.plugin.updatePlayerSpeed(player);
                }

            }, 1L);
        }
    }
}
