package net.nifheim.beelzebu.rpgcore.enchants;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Beelzebu
 */
public class EnchantsAPI {

    private static final FileConfiguration config = Main.getInstance().getConfig();
    private static final FileConfiguration messages = Main.getMessages();

    /**
     * NOT FINISHED NOT FINISHED NOT FINISHED NOT FINISHED NOT FINISHED NOT
     * FINISHED
     *
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
        int loresize = lore.size();
        if (lore.contains("LEVEL")) {
            switch (Integer.parseInt(lore.get(0))) {
                case 30:
                    if (!lore.get(loresize - 2).equals(messages.getString("Lores.No Enchant"))) {
                        enchants.add(lore.get(loresize - 2));
                    }
                    break;
                case 60:
                    if (!lore.get(loresize - 2).equals(messages.getString("Lores.No Enchant"))) {
                        enchants.add(lore.get(loresize - 2));
                    }
                    if (!lore.get(loresize - 3).equals(messages.getString("Lores.No Enchant"))) {
                        enchants.add(lore.get(loresize - 3));
                    }
                    break;
                case 90:
                    if (!lore.get(loresize - 2).equals(messages.getString("Lores.No Enchant"))) {
                        enchants.add(lore.get(loresize - 2));
                    }
                    if (!lore.get(loresize - 3).equals(messages.getString("Lores.No Enchant"))) {
                        enchants.add(lore.get(loresize - 3));
                    }
                    if (!lore.get(loresize - 4).equals(messages.getString("Lores.No Enchant"))) {
                        enchants.add(lore.get(loresize - 4));
                    }
                    break;
            }
        }
        lore.get(4);
        return enchants;
    }
}
