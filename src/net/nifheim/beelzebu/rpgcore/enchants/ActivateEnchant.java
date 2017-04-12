package net.nifheim.beelzebu.rpgcore.enchants;

import java.util.List;
import net.nifheim.beelzebu.rpgcore.utils.RomanNumeral;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Beelzebu
 */
public class ActivateEnchant {

    private Main plugin;

    public void activate(Player player, ItemStack is, Event event, EnchantActions action) {
        if (is == null || !is.hasItemMeta() || !is.getItemMeta().hasLore()) {
            return;
        }

        List<String> lore = is.getItemMeta().getLore();

        for (String sEnchant : lore) {
            String sPropertyString = sEnchant;

            int level = 1;

            String sLevel = sPropertyString.substring(sPropertyString.lastIndexOf(" ") + 1);

            try {
                level = RomanNumeral.valueOf(sLevel);
                sPropertyString = sPropertyString.substring(0, sPropertyString.lastIndexOf(" "));
            } catch (Exception ex) {
            }

            Enchant enchant = EnchantsAPI.getEnchant(sPropertyString.toLowerCase());

            if (enchant != null) {

                if (null != action) {
                    switch (action) {
                        case INTERACT: {
                            enchant.onInteract((PlayerInteractEvent) event, level);
                        }
                        break;
                        case ARROW_HIT_ENTITY:
                            enchant.onArrowHitEntity((EntityDamageByEntityEvent) event, player, level);

                            break;
                        case DAMAGE_OTHER_ENTITY:
                            enchant.onDamageOther((EntityDamageByEntityEvent) event, player, level);

                            break;
                        case INTERACT_ENTITY:
                            enchant.onInteractEntity((PlayerInteractEntityEvent) event, level);
                            break;
                        //case BLOCK_BREAK:
                        //enchant.onBlockBreak((BlockBreakEvent) event, level);
                        //break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void onArrowHitEntity(Player shooter, ItemStack bow, EntityDamageByEntityEvent e) {
        activate(shooter, bow, e, EnchantActions.ARROW_HIT_ENTITY);
    }

    public void onDamagedOtherEntity(Player attacker, EntityDamageByEntityEvent e) {
        activate(attacker, attacker.getItemInHand(), e, EnchantActions.DAMAGE_OTHER_ENTITY);
    }

    public void onInteract(Player player, ItemStack itemInHand, PlayerInteractEvent e) {
        activate(player, itemInHand, e, EnchantActions.INTERACT);
    }

    public void onBlockBreak(BlockBreakEvent e) {
        activate(e.getPlayer(), e.getPlayer().getItemInHand(), e, EnchantActions.BLOCK_BREAK);
    }

    public void onInteractEntity(PlayerInteractEntityEvent e) {
        activate(e.getPlayer(), e.getPlayer().getItemInHand(), e, EnchantActions.INTERACT_ENTITY);
    }

    public void onJoin(Player p) {
        ItemStack[] armor = p.getInventory().getArmorContents();

        if (armor.length > 0) {
            for (ItemStack a : armor) {
                activate(p, a, null, EnchantActions.EQUIP);
            }
        }
    }

    //public void onEquip(PlayerArmorEquipEvent e) {
    //activate(e.getPlayer(), e.getItemStack(), e, EnchantActions.EQUIP);
    //}
    //public void onUnequip(PlayerArmorUnequipEvent e) {
    //activate(e.getPlayer(), e.getItemStack(), e, EnchantActions.UNEQUIP);
    //}
}
