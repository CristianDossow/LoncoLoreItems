package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            final Player playerFinal = player;
            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                Main.plugin.updateHealth(playerFinal);
                Main.plugin.updatePlayerSpeed(playerFinal);
                Main.plugin.setBonuses.updateSetBonus(playerFinal);
            }, 2L);
        }
    }
}
