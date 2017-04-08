package net.nifheim.yitan.itemlorestats.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExpEvent;

public class BlockExpEventListener implements Listener {
	
    @EventHandler
    public void onBlockExpEvent(BlockExpEvent event) {
    	event.setExpToDrop(0);
    }
}
