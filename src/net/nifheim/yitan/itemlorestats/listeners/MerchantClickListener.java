package net.nifheim.yitan.itemlorestats.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

import net.nifheim.yitan.loncoloreitems.LoreItemMaker;

public class MerchantClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    	if(event.getClickedInventory() !=null && event.getClickedInventory().getType().equals(InventoryType.MERCHANT)){
    		event.getSlotType().equals(InventoryType.SlotType.RESULT);
    		ItemStack item = event.getCurrentItem();
    		item=LoreItemMaker.CheckItemLore(item, (Player)event.getWhoClicked());
    	}
    }
}
