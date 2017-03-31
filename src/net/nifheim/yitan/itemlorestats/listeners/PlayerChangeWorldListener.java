package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangeWorldListener implements Listener {

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        if ((event.getPlayer() instanceof Player)) {
            final Player playerFinal = event.getPlayer();

            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                Main.plugin.updateHealth(playerFinal);
                Main.plugin.updatePlayerSpeed(playerFinal);
                Main.plugin.setBonuses.updateSetBonus(playerFinal);
            }, 2L);

            if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(playerFinal.getWorld().getName())) {
            }
        }
    }
}
