package net.nifheim.yitan.lorestats.listeners;

import net.nifheim.yitan.lorestats.Main;
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
                if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                    if (Main.getInstance().getConfig().getDouble("baseHealthRegen") == 0.0D) {
                        return;
                    }
                    double gearRegen = 0.0D;
                    double modifier = 0.0D;

                    if (Main.getInstance().isTool(Main.getInstance().itemInMainHand(player).getType())) {
                        gearRegen += Main.getInstance().gearStats.getHealthRegenItemInHand(Main.getInstance().itemInMainHand(player));
                    }

                    if (Main.getInstance().isTool(Main.getInstance().itemInOffHand(player).getType())) {
                        gearRegen += Main.getInstance().gearStats.getHealthRegenItemInHand(Main.getInstance().itemInOffHand(player));
                    }

                    gearRegen += Main.getInstance().gearStats.getHealthRegenGear(player);

                    double baseRegen = Main.getInstance().getConfig().getDouble("baseHealthRegen");
                    double additionalLevelRegen = Main.getInstance().getConfig().getDouble("additionalStatsPerLevel.healthRegen");
                    double modifiedHealthRegen = player.getMaxHealth() / 100.0D * (gearRegen + baseRegen + Double.valueOf(player.getLevel()) * additionalLevelRegen + modifier);

                    event.setAmount(modifiedHealthRegen);
                }
            }

        }
    }
}
