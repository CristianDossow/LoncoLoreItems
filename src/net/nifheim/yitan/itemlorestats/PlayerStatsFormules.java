package net.nifheim.yitan.itemlorestats;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.nifheim.yitan.loncoloreitems.EspecialAtributes;

public class PlayerStatsFormules {
	
	static String armour = Main.plugin.getConfig().getString("primaryStats.armour.colour") + Main.plugin.getConfig().getString("primaryStats.armour.name");
	static String dodge = Main.plugin.getConfig().getString("secondaryStats.dodge.colour") + Main.plugin.getConfig().getString("secondaryStats.dodge.name");
	static String block = Main.plugin.getConfig().getString("secondaryStats.block.colour") + Main.plugin.getConfig().getString("secondaryStats.block.name");
	static String critChance = Main.plugin.getConfig().getString("secondaryStats.critChance.colour") + Main.plugin.getConfig().getString("secondaryStats.critChance.name");
	static String critDamage = Main.plugin.getConfig().getString("secondaryStats.critDamage.colour") + Main.plugin.getConfig().getString("secondaryStats.critDamage.name");
	static String damage = Main.plugin.getConfig().getString("primaryStats.damage.colour") + Main.plugin.getConfig().getString("primaryStats.damage.name");
	static String health = Main.plugin.getConfig().getString("primaryStats.health.colour") + Main.plugin.getConfig().getString("primaryStats.health.name");
	static String healthRegen = Main.plugin.getConfig().getString("primaryStats.healthRegen.colour") + Main.plugin.getConfig().getString("primaryStats.healthRegen.name");
	static String lifeSteal = Main.plugin.getConfig().getString("secondaryStats.lifeSteal.colour") + Main.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
	static String reflect = Main.plugin.getConfig().getString("secondaryStats.reflect.colour") + Main.plugin.getConfig().getString("secondaryStats.reflect.name");
	static String fire = Main.plugin.getConfig().getString("secondaryStats.fire.colour") + Main.plugin.getConfig().getString("secondaryStats.fire.name");
	static String ice = Main.plugin.getConfig().getString("secondaryStats.ice.colour") + Main.plugin.getConfig().getString("secondaryStats.ice.name");
	static String poison = Main.plugin.getConfig().getString("secondaryStats.poison.colour") + Main.plugin.getConfig().getString("secondaryStats.poison.name");
	static String wither = Main.plugin.getConfig().getString("secondaryStats.wither.colour") + Main.plugin.getConfig().getString("secondaryStats.wither.name");
	static String harming = Main.plugin.getConfig().getString("secondaryStats.harming.colour") + Main.plugin.getConfig().getString("secondaryStats.harming.name");
	static String blind = Main.plugin.getConfig().getString("secondaryStats.blind.colour") + Main.plugin.getConfig().getString("secondaryStats.blind.name");
	static String xpmultiplier = Main.plugin.getConfig().getString("bonusStats.xpMultiplier.colour") + Main.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
	static String movementspeed = Main.plugin.getConfig().getString("secondaryStats.movementSpeed.colour") + Main.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
	static String level = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");
	static String onlydamage = Main.plugin.getConfig().getString("primaryStats.damage.name");
	
	static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
	
	static public  double[] getDamageGearStat(Player player) {
		return getDoubleGearStat(player, damage);
	}
	
	static public double[] getDoubleGearStat(Player player, String stat) {
        double MinValue = 0.0D;
        double MaxValue = 0.0D;
        //this.damage = Main.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
        if (player != null) {
            if (player.getEquipment() != null) {

                ItemStack[] arrayOfItemStack;
                arrayOfItemStack = player.getEquipment().getArmorContents();
                int j = arrayOfItemStack.length;
                for (int i = 0; i < j; i++) {
                    ItemStack gear = arrayOfItemStack[i];
                    if ((gear != null) && (gear.hasItemMeta()) && (gear.getItemMeta().hasLore())) {
                    	
                    }
                }
            }
        }
        double[] values = {MinValue, MaxValue};
        return values;
    }
	static public double[] getDoubleStat(String stat,ItemStack gear) {
        //this.damage = Main.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
    	stat = stat.replaceAll(" ", "");
        double MinValue = 0;
        double MaxValue = 0;
        if (gear != null) {
            if (gear.getItemMeta() != null) {
                if (gear.getItemMeta().getLore() != null) {
                    List<String> itemLore = gear.getItemMeta().getLore();
                    for (String line : itemLore) {
                        String lore = ChatColor.stripColor(line.toString());
                        lore = lore.toLowerCase();
                        if (lore.replaceAll(languageRegex, "").matches(stat.toLowerCase())) {
                            if (lore.contains("-")) {
                                MinValue += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""));
                                MaxValue += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""));
                            } else {
                                MinValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                MaxValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                            }
                        }
                    }
                }
            }
        }

        double[] values = {MinValue, MaxValue};
        return values;
    }

}
