package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
	/*
	@EventHandler
    public void ItemToStack2(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();
		for(Player player2 : Bukkit.getOnlinePlayers()){
			if(player2.hasPermission("ils.admin"))
				player2.sendMessage(event.getAction().toString());
		}
        if(event.getAction().equals(InventoryAction.CLONE_STACK)){
    		for(Player player2 : Bukkit.getOnlinePlayers()){
    			if(player2.hasPermission("ils.admin"))
    				player2.sendMessage("2");
    		}
        	if(event.getCurrentItem()!=null&&event.getCurrentItem().getType().equals(Material.DIAMOND_HOE)&&event.getCurrentItem().getDurability()!=0){
        		player.setItemOnCursor(event.getCurrentItem());
        		player.getItemOnCursor().setAmount(64);
        		player.updateInventory();
        		event.setCancelled(true);
        		for(Player player2 : Bukkit.getOnlinePlayers()){
        			if(player2.hasPermission("ils.admin"))
        				player2.sendMessage("3");
        		}
            }
        }
	}*/
	@EventHandler
    public void ItemToStack(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getCursor()!=null&&event.getCurrentItem()!=null&&event.getCurrentItem().getType().equals(Material.DIAMOND_HOE)&&event.getCurrentItem().getDurability()!=0 &&( event.getClick().equals(ClickType.LEFT))){
			if(event.getCursor()!=null && event.getCurrentItem()!=null){
				if(event.getCursor().isSimilar(event.getCurrentItem())){
					
	        		if(event.getCurrentItem().getAmount()+event.getCursor().getAmount()<=64){
	        			int sum =event.getCurrentItem().getAmount()+event.getCursor().getAmount();

	        			//event.getCurrentItem().setAmount(sum);
	        			player.setItemOnCursor(event.getCurrentItem());
	        			player.getItemOnCursor().setAmount(sum);
	        			event.setCurrentItem(null);
	        			event.setCancelled(true);
	        			player.updateInventory();
	        		}
				}
			}
		}
	}
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
                    Main.plugin.getPlayerStats(player).UpdateAll();
                }

            }, 1L);
        }
    }
}
