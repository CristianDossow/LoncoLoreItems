package net.nifheim.beelzebu.rpgcore.api;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Beelzebu
 */
public class API {
    /**
     * NOT FINISHED
     * NOT FINISHED
     * NOT FINISHED
     * NOT FINISHED
     * NOT FINISHED
     * NOT FINISHED
     * @param p Player to get the enchants from their hand.
     * @return The enchants of the item in the hand of the player.
     */
    public static List<String> getEnchants(Player p) {
        // NOT FINISHED
        ItemStack hand = p.getEquipment().getItemInMainHand();
        if (hand.getItemMeta().hasLore()) {
            
        }
        List<String> enchants = new ArrayList<>();
        ItemMeta itemMeta = p.getEquipment().getItemInMainHand().getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore.get(4) != Main.getPlugin().getMessages().getString("Lores.No Enchant")) {
            enchants.add(lore.get(4));
        }
        lore.get(4);
        return enchants;
    }
}
