package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GamemodeChangeListener implements Listener {
    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        final Player player = event.getPlayer();
        Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
            Main.plugin.updateHealth(player);
        }, 2L);
    }
}
