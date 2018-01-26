package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onDropItemEvent(PlayerDropItemEvent event) {
        Main.getInstance().getPlayerStats(event.getPlayer()).UpdateAll();
        Player player = event.getPlayer();
        if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            final Player playerFinal = player;
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                Main.getInstance().updateHealth(playerFinal);
                Main.getInstance().updatePlayerSpeed(playerFinal);
                Main.getInstance().setBonuses.updateSetBonus(playerFinal);
                Main.getInstance().getPlayerStats(playerFinal).UpdateAll();
            }, 2L);
        }
    }
}
