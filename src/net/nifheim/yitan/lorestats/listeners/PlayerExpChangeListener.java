package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerExpChangeListener implements Listener {

    @EventHandler
    public void expChangeEvent(PlayerExpChangeEvent e) {
        if (e.getPlayer().hasMetadata("NPC")) {
            return;
        }
        Player player = e.getPlayer();
        double bonusExp = 0.0D;
        double xpMultiplier = 0.0D;

        if ((Main.getInstance().isTool(Main.getInstance().itemInMainHand(player).getType()))
                && (Main.getInstance().gearStats.getXPMultiplierItemInHand(Main.getInstance().itemInMainHand(player)) > 0.0D)) {
            xpMultiplier += Main.getInstance().gearStats.getXPMultiplierItemInHand(Main.getInstance().itemInMainHand(player));
        }

        if ((Main.getInstance().isTool(Main.getInstance().itemInOffHand(player).getType()))
                && (Main.getInstance().gearStats.getXPMultiplierItemInHand(Main.getInstance().itemInOffHand(player)) > 0.0D)) {
            xpMultiplier += Main.getInstance().gearStats.getXPMultiplierItemInHand(Main.getInstance().itemInOffHand(player));
        }

        if (Main.getInstance().gearStats.getXPMultiplierGear(player) > 0.0D) {
            xpMultiplier += Main.getInstance().gearStats.getXPMultiplierGear(player);
        }

        bonusExp = e.getAmount() * xpMultiplier / 100.0D;
        player.giveExp((int) bonusExp);

    }
}
