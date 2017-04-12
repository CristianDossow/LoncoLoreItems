package net.nifheim.yitan.itemlorestats.listeners;

import java.util.List;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

/**
 *
 * @author Beelzebu
 */
public class EntityDamageByEntityListener implements Listener {

    private Main plugin;

    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();
            MetadataValue itemCraftMetaData = getItemCraftMetaData(arrow, "bow");

            if (itemCraftMetaData != null) {
                ItemStack isBow = (ItemStack) itemCraftMetaData.value();

                if (isBow != null) {
                    Player pShooter = (Player) getItemCraftMetaData(arrow, "shooter").value();

                    plugin.activateEnchant.onArrowHitEntity(pShooter, isBow, e);
                }
            }
        } else if (e.getDamager() instanceof Player) {
            Player attacker = (Player) e.getDamager();

            if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() != Material.AIR) {
                    plugin.activateEnchant.onDamagedOtherEntity(attacker, e);
            }
        }
    }

    public MetadataValue getItemCraftMetaData(Metadatable holder, String key) {
        List<MetadataValue> metadata = holder.getMetadata(key);

        for (MetadataValue mdv : metadata) {
            if (mdv.getOwningPlugin().equals(plugin)) {
                return mdv;
            }
        }

        return null;
    }
}
