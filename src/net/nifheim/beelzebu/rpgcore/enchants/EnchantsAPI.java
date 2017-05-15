package net.nifheim.beelzebu.rpgcore.enchants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.nifheim.beelzebu.rpgcore.enchants.sword.Backstab;
import net.nifheim.beelzebu.rpgcore.utils.RomanNumeral;
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

    private static Main plugin;
    private static Map<String, Enchant> enchants;

    private static final FileConfiguration config = Main.getInstance().getConfig();
    private static final FileConfiguration messages = plugin.getMessages();

    public EnchantsAPI(Main plugin) {
        EnchantsAPI.plugin = plugin;

        enchants = new HashMap<>();

        // Start Sword Enchants
        //addEnchant(new Backstab());

    }

    /**
     * NOT FINISHED NOT FINISHED NOT FINISHED NOT FINISHED NOT FINISHED NOT
     * FINISHED
     *
     * @param p Player to get the enchants from their hand.
     * @return The enchants of the item in the hand of the player.
     */
    public static List<String> getPlayerEnchants(Player p) {
        // NOT FINISHED
        ItemStack hand = p.getEquipment().getItemInMainHand();
        if (hand.getItemMeta().hasLore()) {

        }
        List<String> playerenchants = new ArrayList<>();
        ItemMeta itemMeta = p.getEquipment().getItemInMainHand().getItemMeta();
        List<String> lore = itemMeta.getLore();
        int loresize = lore.size();
        if (lore.contains("LEVEL")) {
            switch (Integer.parseInt(lore.get(0))) {
                case 30:
                    if (!lore.get(loresize - 2).equals(messages.getString("Lores.No Enchant"))) {
                        playerenchants.add(lore.get(loresize - 2));
                    }
                    break;
                case 60:
                    if (!lore.get(loresize - 2).equals(messages.getString("Lores.No Enchant"))) {
                        playerenchants.add(lore.get(loresize - 2));
                    }
                    if (!lore.get(loresize - 3).equals(messages.getString("Lores.No Enchant"))) {
                        playerenchants.add(lore.get(loresize - 3));
                    }
                    break;
                case 90:
                    if (!lore.get(loresize - 2).equals(messages.getString("Lores.No Enchant"))) {
                        playerenchants.add(lore.get(loresize - 2));
                    }
                    if (!lore.get(loresize - 3).equals(messages.getString("Lores.No Enchant"))) {
                        playerenchants.add(lore.get(loresize - 3));
                    }
                    if (!lore.get(loresize - 4).equals(messages.getString("Lores.No Enchant"))) {
                        playerenchants.add(lore.get(loresize - 4));
                    }
                    break;
            }
        }
        return playerenchants;
    }

    private static void addEnchant(Enchant icp) {
        enchants.put(icp.getName().toLowerCase(), icp);
    }

    public static String getEnchantString(Enchant icp, Integer level) {
        return (icp.getType()) + icp.getName() + " " + RomanNumeral.convertToRoman(level);
    }

    public static Enchant getEnchant(String property) {
        return enchants.get(property.toLowerCase());
    }

    public static String getEnchantType(Enchant enchant) {
        return enchant.getType().toString();
    }

    /**
     * Get all registred enchants in the server.
     * @return Enchants
     */
    public static Collection<Enchant> getAllEnchants() {
        return enchants.values();
    }
}
