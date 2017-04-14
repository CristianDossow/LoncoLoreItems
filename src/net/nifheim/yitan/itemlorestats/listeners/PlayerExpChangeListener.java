package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerExpChangeListener implements Listener{

    @EventHandler
    public void expChangeEvent(PlayerExpChangeEvent e) {
        if (e.getPlayer().hasMetadata("NPC")) {
            return;
        }
        Player player = e.getPlayer();
        double bonusExp = 0.0D;
        double xpMultiplier = 0.0D;

        if ((Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType()))
                && (Main.plugin.gearStats.getXPMultiplierItemInHand(Main.plugin.itemInMainHand(player)) > 0.0D)) {
            xpMultiplier += Main.plugin.gearStats.getXPMultiplierItemInHand(Main.plugin.itemInMainHand(player));
        }

        if ((Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType()))
                && (Main.plugin.gearStats.getXPMultiplierItemInHand(Main.plugin.itemInOffHand(player)) > 0.0D)) {
            xpMultiplier += Main.plugin.gearStats.getXPMultiplierItemInHand(Main.plugin.itemInOffHand(player));
        }

        if (Main.plugin.gearStats.getXPMultiplierGear(player) > 0.0D) {
            xpMultiplier += Main.plugin.gearStats.getXPMultiplierGear(player);
        }

        bonusExp = e.getAmount() * xpMultiplier / 100.0D;
        player.giveExp((int) bonusExp);

    }
}
