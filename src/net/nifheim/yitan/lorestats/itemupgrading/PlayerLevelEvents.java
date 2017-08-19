package net.nifheim.yitan.lorestats.itemupgrading;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.PlayerInventory;

public class PlayerLevelEvents implements org.bukkit.event.Listener {

    ItemUpgrade itemUpgrade = new ItemUpgrade();

    @org.bukkit.event.EventHandler
    public void onPlayerLevel(PlayerLevelChangeEvent event) {
        if ((event.getPlayer() instanceof Player)) {
            Player player = event.getPlayer();

            if (event.getNewLevel() <= Main.getInstance().getConfig().getInt("levelCap")) {
                if ((Main.getInstance().getConfig().getBoolean("upgradeStatsOnLevelChange.enabled"))
                        && (event.getNewLevel() > event.getOldLevel())) {
                    if (player.getInventory().getItemInMainHand() != null) {
                        //this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInMainHand(), "Main");
                    }

                    if (player.getInventory().getItemInOffHand() != null) {
                        //this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInOffHand(), "Off");
                    }

                    //this.itemUpgrade.increaseItemStatOnHelmet(player);
                    //this.itemUpgrade.increaseItemStatOnChestplate(player);
                    //this.itemUpgrade.increaseItemStatOnLeggings(player);
                    //this.itemUpgrade.increaseItemStatOnBoots(player);
                    Main.getInstance().updateHealth(player);
                    Main.getInstance().updatePlayerSpeed(player);
                }
            } else {
                player.setLevel(Main.getInstance().getConfig().getInt("levelCap"));
                player.setExp(0.0F);
            }
        }
    }
}
