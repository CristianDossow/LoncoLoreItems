package net.nifheim.yitan.itemlorestats;

import java.util.ArrayList;
import java.util.Arrays;
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
	public static String weaponspeed ="vataque";
	
	static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
	final static double ArmorGrowthrate = -0.26;
	final static double Armorxlvl = 4;
    final static double Armorxbase = 100;
	
	static public double[] getDamageGearStat(Player player) {
		return getDoubleGearStat(player, damage);
	}
	static public double getWeaponSpeedStat(Player player) {
		double speed= getStat(weaponspeed,player.getEquipment().getItemInMainHand() );
		if(speed<1)
			speed=1;
		return speed;
	}
	static public double getWeaponSpeedStat(ItemStack gear) {
		double speed= getStat(weaponspeed,gear);
		if(speed<1)
			speed=1;
		return speed;
	}
	static public double getArmorStat(Player player) {
		return getGearStat(player,armour);
	}
	static public  double getPercentArmorStat(Player player,double armor) {
        double basearmor = armor;
        if (basearmor == 0) {
        	basearmor = 1;
        }
        double defence = 1 - (Math.pow(basearmor, ArmorGrowthrate));
        defence = defence * Armorpenaltyxlevel(basearmor, player.getLevel());
        if (defence > 0.9) {
            defence = 0.9;
        }
        defence = defence * 100;
        return defence;
	}
	
	static public double[] getDoubleGearStat(Player player, String stat) {
        double MinValue = 1;
        double MaxValue = 1;
        if (player != null) {
            if (player.getEquipment() != null) {

                List<ItemStack> arrayOfItemStack = new ArrayList<>();
                arrayOfItemStack.addAll(Arrays.asList(player.getEquipment().getArmorContents()));
                arrayOfItemStack.add(player.getEquipment().getItemInMainHand());
                if(!stat.equals(damage)){
                    arrayOfItemStack.add(player.getEquipment().getItemInOffHand());
                }
                else{
                	double mainSpeed = 1;
                	double offSpeed = 1;
                	mainSpeed = getWeaponSpeedStat(player.getEquipment().getItemInMainHand());
                	offSpeed = getWeaponSpeedStat(player.getEquipment().getItemInOffHand());
                	double difSpeed = (mainSpeed/offSpeed)*0.5;
                	MinValue = MinValue + (getDoubleStat(stat,player.getEquipment().getItemInOffHand())[0])*difSpeed;
                    MaxValue = MaxValue + (getDoubleStat(stat,player.getEquipment().getItemInOffHand())[1])*difSpeed;
                    
                }
                
                for (ItemStack gear : arrayOfItemStack) {
                    if ((gear != null) && (gear.hasItemMeta()) && (gear.getItemMeta().hasLore())) {
                        MinValue = MinValue + getDoubleStat(stat,gear)[0];
                        MaxValue = MaxValue + getDoubleStat(stat,gear)[1];
                    }
                }
            }
        }
        double[] values = {MinValue, MaxValue};
        return values;
    }
	static public double[] getDoubleStat(String stat,ItemStack gear) {
    	stat = stat.replaceAll(languageRegex, "");
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
	
	static public double getGearStat(Player player, String stat) {
        double value = 0;
        if (player != null) {
            if (player.getEquipment() != null) {

                List<ItemStack> arrayOfItemStack = new ArrayList<>();
                arrayOfItemStack.addAll(Arrays.asList(player.getEquipment().getArmorContents()));
                arrayOfItemStack.add(player.getEquipment().getItemInMainHand());
                arrayOfItemStack.add(player.getEquipment().getItemInOffHand());
                for (ItemStack gear : arrayOfItemStack) {
                    if ((gear != null) && (gear.hasItemMeta()) && (gear.getItemMeta().hasLore())) {
                    	value = value + getStat(stat,gear);
                    }
                }
            }
        }
        return value;
    }
	static public double getStat(String stat,ItemStack gear) {
    	stat = stat.replaceAll(languageRegex, "");
        double value = 0;
        if (gear != null) {
            if (gear.getItemMeta() != null) {
                if (gear.getItemMeta().getLore() != null) {
                    List<String> itemLore = gear.getItemMeta().getLore();
                    for (String line : itemLore) {
                        String lore = ChatColor.stripColor(line.toString());
                        lore = lore.toLowerCase();
                        if (lore.replaceAll(languageRegex, "").matches(stat.toLowerCase())) {
                        	value += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                        }
                    }
                }
            }
        }
        return value;
    }
    public static double Armorpenaltyxlevel(double basestrength, int lvl) {
        double maxstrength = Armorxbase + (Armorxlvl * lvl);
        double penalty = basestrength / maxstrength;
        if (penalty > 1) {
            penalty = 1;
        }
        return penalty;
    }

}
