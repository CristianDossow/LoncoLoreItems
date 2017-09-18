package net.nifheim.yitan.lorestats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.nifheim.yitan.items.ItemCategory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerStatsFormules {

    public static String armour = Main.getInstance().getConfig().getString("primaryStats.armour.name");
    public static String dodge = Main.getInstance().getConfig().getString("secondaryStats.dodge.name");
    public static String block = Main.getInstance().getConfig().getString("secondaryStats.block.name");
    public static String critChance = Main.getInstance().getConfig().getString("secondaryStats.critChance.name");
    public static String critDamage = Main.getInstance().getConfig().getString("secondaryStats.critDamage.name");
    public static String damage = Main.getInstance().getConfig().getString("primaryStats.damage.name");
    public static String health = Main.getInstance().getConfig().getString("primaryStats.health.name");
    public static String healthRegen = Main.getInstance().getConfig().getString("primaryStats.healthRegen.name");
    public static String lifeSteal = Main.getInstance().getConfig().getString("secondaryStats.lifeSteal.name");
    public static String reflect = Main.getInstance().getConfig().getString("secondaryStats.reflect.name");
    public static String fire = Main.getInstance().getConfig().getString("secondaryStats.fire.name");
    public static String ice = Main.getInstance().getConfig().getString("secondaryStats.ice.name");
    public static String poison = Main.getInstance().getConfig().getString("secondaryStats.poison.name");
    public static String wither = Main.getInstance().getConfig().getString("secondaryStats.wither.name");
    public static String harming = Main.getInstance().getConfig().getString("secondaryStats.harming.name");
    public static String blind = Main.getInstance().getConfig().getString("secondaryStats.blind.name");
    public static String xpmultiplier = Main.getInstance().getConfig().getString("bonusStats.xpMultiplier.name");
    public static String movementspeed = Main.getInstance().getConfig().getString("secondaryStats.movementSpeed.name");
    public static String level = Main.getInstance().getConfig().getString("bonusStats.xpLevel.name");
    public static String onlydamage = Main.getInstance().getConfig().getString("primaryStats.damage.name");
    public static String magicArmor = "Armadura Mágica";
    public static String weaponspeed = "V Ataque";
    public static String magicPower = "Poder Mágico";
    public static String magicPen = "Penetración magia";
    public static String manaMax = "Maná";
    public static String manaRegen = "Regeneración de maná";
    public static String cdReduction = "Reducción de enfriamiento";
    public static String armorPen = "Penetración Armadura";
    public static String backstab = "Apuñalar";

    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
    final static double ArmorGrowthrate = -0.26;
    final static double Armorxlvl = 4;
    final static double Armorxbase = 100;

    static public double getArmorPenStat(Player player) {
        return (getGearStat(player, armorPen) / 100);
    }

    static public double getBackstabStat(Player player) {
        return (getGearStat(player, backstab) / 100);
    }

    static public double[] getDamageGearStat(Player player) {
        return getDoubleGearStat(player, damage);
    }

    static public double getWeaponSpeedStat(Player player) {
        double speed = getStat(weaponspeed, player.getEquipment().getItemInMainHand(), 1);
        if (speed == 0) {
            speed = 1;
        }
        if (speed < 0.5) {
            speed = 0.5;
        }
        return speed;
    }

    static public double getWeaponSpeedStat(ItemStack gear) {
        double speed = getStat(weaponspeed, gear, 1);
        if (speed == 0) {
            speed = 1;
        }
        if (speed < 0.5) {
            speed = 0.5;
        }
        return speed;
    }

    static public double getArmorStat(Player player) {
        return getGearStat(player, armour);
    }

    static public double getMovementSpeedStat(Player player) {
        double speed = getGearStat(player, movementspeed, false);
        speed = speed / 100;
        if (speed < -1) {
            speed = -1;
        }
        return speed;
    }

    static public double getMagicArmorStat(Player player) {
        return getGearStat(player, magicArmor);
    }

    static public double getMagicArmorPenStat(Player player) {
        return getGearStat(player, magicPen) / 100;
    }

    static public double getPercentArmorStat(Player player, double armor) {
        double basearmor = armor;
        if (basearmor == 0) {
            basearmor = 1;
        }
        double defence = 1 - (Math.pow(basearmor, ArmorGrowthrate));
        defence = defence * Armorpenaltyxlevel(basearmor, player.getLevel());
        if (defence > 0.9) {
            defence = 0.9;
        }
        //defence = defence * 100;
        return defence;
    }

    static public double getDodgeStat(Player player) {
        double stat = getGearStat(player, dodge) / 100;
        if (stat > 0.8) {
            return 0.8;
        } else {
            return stat;
        }
    }

    static public double getBlockStat(Player player) {
        double stat = getGearStat(player, block) / 100;
        if (stat > 0.5) {
            return 0.5;
        } else {
            return stat;
        }
    }

    static public double getCritChanceStat(Player player) {
        double stat = getGearStat(player, critChance) / 100;
        if (stat > 1) {
            return 1;
        } else {
            return stat;
        }
    }

    static public double getCritDamageStat(Player player) {
        return (getGearStat(player, critDamage) / 100) + 0.5;
    }

    static public double getPosionStat(Player player) {
        return getGearStat(player, poison) / 100;
    }

    static public double getManaMaxStat(Player player) {
        return getGearStat(player, manaMax) + 100;
    }

    static public double getManaRegenStat(Player player) {
        return getGearStat(player, manaRegen) + 1;
    }

    static public double getMagicPowerStat(Player player) {
        return getGearStat(player, magicPower);
    }

    static public double getCdReductionStat(Player player) {
        return getGearStat(player, cdReduction) / 100;
    }

    static public double[] getDoubleGearStat(Player player, String stat) {
        double MinValue = 0;
        double MaxValue = 0;
        if (player != null) {
            if (player.getEquipment() != null) {

                List<ItemStack> arrayOfItemStack = new ArrayList<>();
                arrayOfItemStack.addAll(Arrays.asList(player.getEquipment().getArmorContents()));
                if (!player.getEquipment().getItemInMainHand().getType().equals(Material.BOW)) {
                    if (!stat.equals(damage) && ItemCategory.isSwordOrShield(player.getEquipment().getItemInOffHand())) {
                        //arrayOfItemStack.add(player.getEquipment().getItemInOffHand());
                        if (ItemCategory.isShield(player.getEquipment().getItemInOffHand())) {
                            MinValue = MinValue + getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 1)[0];
                            MaxValue = MaxValue + getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 1)[1];
                        } else {
                            MinValue = MinValue + getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 0.5)[0];
                            MaxValue = MaxValue + getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 0.5)[1];
                        }

                    } else {
                        double tempMinValue = +getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 0.5)[0];
                        double tempMaxValue = +getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 0.5)[0];
                        double mainSpeed = getWeaponSpeedStat(player);
                        double offSpeed = getWeaponSpeedStat(player.getEquipment().getItemInOffHand());
                        double speedFactor = mainSpeed / offSpeed;
                        MinValue = MinValue + tempMinValue * speedFactor;
                        MaxValue = MaxValue + tempMaxValue * speedFactor;
                    }
                }
                if (ItemCategory.isSwordOrShield(player.getEquipment().getItemInMainHand()) || ItemCategory.isBow(player.getEquipment().getItemInMainHand())) {
                    arrayOfItemStack.add(player.getEquipment().getItemInMainHand());
                }
                for (ItemStack gear : arrayOfItemStack) {
                    if ((gear != null) && (gear.hasItemMeta()) && (gear.getItemMeta().hasLore())) {
                        double multiplier = 1;
                        int itemlvl = getItemLvl(gear);
                        if (itemlvl > player.getLevel()) {
                            multiplier = 1 - (0.05 * (itemlvl - player.getLevel()));
                            if (multiplier < 0) {
                                multiplier = 0;
                            }
                        }
                        MinValue = MinValue + getDoubleStat(stat, gear, multiplier)[0];
                        MaxValue = MaxValue + getDoubleStat(stat, gear, multiplier)[1];
                    }
                }
            } else {
                double mainSpeed = 1;
                double offSpeed = 1;
                mainSpeed = getWeaponSpeedStat(player.getEquipment().getItemInMainHand());
                offSpeed = getWeaponSpeedStat(player.getEquipment().getItemInOffHand());
                double difSpeed = (mainSpeed / offSpeed) * 0.5;
                MinValue = MinValue + (getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 1)[0]) * difSpeed;
                MaxValue = MaxValue + (getDoubleStat(stat, player.getEquipment().getItemInOffHand(), 1)[1]) * difSpeed;

            }
        }
        double[] values = {MinValue, MaxValue};
        return values;
    }

    static public double[] getDoubleStat(String stat, ItemStack gear, double multiplier) {
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
                                MinValue += (Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", "")) * multiplier);
                                MaxValue += (Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", "")) * multiplier);
                            } else {
                                MinValue += (Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier);
                                MaxValue += (Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier);
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
        return getGearStat(player, stat, true);
    }

    static public double getGearStat(Player player, String stat, boolean useMultiplier) {
        double value = 0;
        if (player != null) {
            if (player.getEquipment() != null) {

                List<ItemStack> arrayOfItemStack = new ArrayList<>();
                arrayOfItemStack.addAll(Arrays.asList(player.getEquipment().getArmorContents()));
                if (!player.getEquipment().getItemInMainHand().getType().equals(Material.BOW)) {
                    if (ItemCategory.isMelle(player.getEquipment().getItemInOffHand())) {
                        value = value + getStat(stat, player.getEquipment().getItemInOffHand(), 0.5);
                    } else if (ItemCategory.isShield(player.getEquipment().getItemInOffHand())) {
                        value = value + getStat(stat, player.getEquipment().getItemInOffHand(), 1);
                    }
                }
                if (ItemCategory.isSwordOrShield(player.getEquipment().getItemInMainHand()) || ItemCategory.isBow(player.getEquipment().getItemInMainHand())) {
                    arrayOfItemStack.add(player.getEquipment().getItemInMainHand());
                }
                for (ItemStack gear : arrayOfItemStack) {
                    if ((gear != null) && (gear.hasItemMeta()) && (gear.getItemMeta().hasLore())) {
                        double multiplier = 1;
                        if (useMultiplier) {
                            int itemlvl = getItemLvl(gear);
                            if (itemlvl > player.getLevel()) {
                                multiplier = 1 - (0.05 * (itemlvl - player.getLevel()));
                                if (multiplier < 0) {
                                    multiplier = 0;
                                }
                            }
                        }
                        value = value + getStat(stat, gear, multiplier);
                    }
                }
            }
        }
        return value;
    }

    static public double getStat(String stat, ItemStack gear, double multiplier) {
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
                            try {
                                value += (Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier);
                            } catch (NumberFormatException e) {

                            }

                        }
                    }
                }
            }
        }
        return value;
    }

    static public int getItemLvl(ItemStack gear) {
        String stat = level.replaceAll(languageRegex, "");
        int value = 0;
        if (gear != null) {
            if (gear.getItemMeta() != null) {
                if (gear.getItemMeta().getLore() != null) {
                    List<String> itemLore = gear.getItemMeta().getLore();
                    for (String line : itemLore) {
                        String lore = ChatColor.stripColor(line.toString());
                        lore = lore.toLowerCase();
                        if (lore.replaceAll(languageRegex, "").matches(stat.toLowerCase())) {
                            try {
                                value += (Integer.parseInt(lore.replaceAll("[^0-9.+-]", "")));
                            } catch (NumberFormatException e) {

                            }

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
