package net.nifheim.yitan.items;

import java.util.List;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class LoreUtils {

    static String armour = Main.getInstance().getConfig().getString("primaryStats.armour.name");
    static String dodge = Main.getInstance().getConfig().getString("secondaryStats.dodge.name");
    static String block = Main.getInstance().getConfig().getString("secondaryStats.block.name");
    static String critChance = Main.getInstance().getConfig().getString("secondaryStats.critChance.name");
    static String critDamage = Main.getInstance().getConfig().getString("secondaryStats.critDamage.name");
    static String damage = Main.getInstance().getConfig().getString("primaryStats.damage.name");
    static String health = Main.getInstance().getConfig().getString("primaryStats.health.name");
    static String healthRegen = Main.getInstance().getConfig().getString("primaryStats.healthRegen.name");
    static String lifeSteal = Main.getInstance().getConfig().getString("secondaryStats.lifeSteal.name");
    static String reflect = Main.getInstance().getConfig().getString("secondaryStats.reflect.name");
    static String fire = Main.getInstance().getConfig().getString("secondaryStats.fire.name");
    static String ice = Main.getInstance().getConfig().getString("secondaryStats.ice.name");
    static String poison = Main.getInstance().getConfig().getString("secondaryStats.poison.name");
    static String wither = Main.getInstance().getConfig().getString("secondaryStats.wither.name");
    static String harming = Main.getInstance().getConfig().getString("secondaryStats.harming.name");
    static String blind = Main.getInstance().getConfig().getString("secondaryStats.blind.name");
    static String xpmultiplier = Main.getInstance().getConfig().getString("bonusStats.xpMultiplier.name");
    static String movementspeed = Main.getInstance().getConfig().getString("secondaryStats.movementSpeed.name");
    static String weaponspeed = "VAtaque";
    static String durability = Main.getInstance().getConfig().getString("bonusStats.durability.name");
    static String lvl = Main.getInstance().getConfig().getString("bonusStats.xpLevel.name");
    static String destroy = EspecialAtributes.destroyname;
    static public String languageRegex = "[^A-Za-z������������_]";

    static public boolean IsWeapon(ItemStack item) {
        if (Main.getInstance().isTool(item.getType())) {
            if (item.getItemMeta().hasLore()) {
                boolean haveSpeed = false;
                boolean haveDamage = false;
                List<String> lore = item.getItemMeta().getLore();
                for (String loreline : lore) {
                    loreline = ChatColor.stripColor(loreline.toString());
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(weaponspeed.replaceAll(languageRegex, "").toLowerCase())) {
                        haveSpeed = true;
                    }
                    if (loreline.replaceAll(languageRegex, "").matches(damage.replaceAll(languageRegex, "").toLowerCase())) {
                        haveDamage = true;
                    }
                }
                if (haveSpeed && haveDamage) {
                    return true;
                }
            }

        }

        return false;
    }

    static public double getWeaponSpeed(ItemStack item) {
        double thisweaponspeed = 1;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String loreline : lore) {
                loreline = ChatColor.stripColor(loreline.toString());
                loreline = loreline.toLowerCase();
                if (loreline.replaceAll(languageRegex, "").matches(weaponspeed.toLowerCase())) {
                    thisweaponspeed = Double.parseDouble(loreline.replaceAll("[^0-9.+-]", ""));
                }
            }
        }
        return thisweaponspeed;
    }

}
