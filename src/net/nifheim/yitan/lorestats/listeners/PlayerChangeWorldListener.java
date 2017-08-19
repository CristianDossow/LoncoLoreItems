package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangeWorldListener implements Listener {

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        if ((event.getPlayer() instanceof Player)) {
            final Player playerFinal = event.getPlayer();

            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                Main.getInstance().updateHealth(playerFinal);
                Main.getInstance().updatePlayerSpeed(playerFinal);
                Main.getInstance().setBonuses.updateSetBonus(playerFinal);
                Main.getInstance().getPlayerStats(playerFinal).UpdateAll();
            }, 2L);

            if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(playerFinal.getWorld().getName())) {
            }
        }
    }
}
