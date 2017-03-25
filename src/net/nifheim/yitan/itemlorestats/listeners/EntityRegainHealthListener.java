package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void onRegenHealth(EntityRegainHealthEvent event) {
        if ((event.getEntity() instanceof Player)) {
            if (event.getRegainReason().equals(org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.SATIATED)) {
                Player player = (Player) event.getEntity();
                if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                    if (Main.plugin.getConfig().getDouble("baseHealthRegen") == 0.0D) {
                        return;
                    }
                    double gearRegen = 0.0D;
                    double modifier = 0.0D;

                    if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
                        gearRegen += Main.plugin.gearStats.getHealthRegenItemInHand(Main.plugin.itemInMainHand(player));
                    }

                    if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
                        gearRegen += Main.plugin.gearStats.getHealthRegenItemInHand(Main.plugin.itemInOffHand(player));
                    }

                    gearRegen += Main.plugin.gearStats.getHealthRegenGear(player);

                    double baseRegen = Main.plugin.getConfig().getDouble("baseHealthRegen");
                    double additionalLevelRegen = Main.plugin.getConfig().getDouble("additionalStatsPerLevel.healthRegen");
                    double modifiedHealthRegen = player.getMaxHealth() / 100.0D * (gearRegen + baseRegen + Double.valueOf(player.getLevel()) * additionalLevelRegen + modifier);

                    event.setAmount(modifiedHealthRegen);
                }
            }

        }
    }
}
