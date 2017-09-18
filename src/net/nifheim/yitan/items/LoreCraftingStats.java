package net.nifheim.yitan.items;

import java.text.DecimalFormat;
import java.util.Locale;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStatsFormules;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class LoreCraftingStats {

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
    static String durability = Main.getInstance().getConfig().getString("bonusStats.durability.name");
    static String lvlname = Main.getInstance().getConfig().getString("bonusStats.xpLevel.name");
    static String destroy = EspecialAtributes.destroyname;
    static String voidbound = EspecialAtributes.voidbound;

    public static String weaponspeed = PlayerStatsFormules.weaponspeed;
    public static String magicPower = PlayerStatsFormules.magicPower;
    public static String magicArmor = PlayerStatsFormules.magicArmor;
    public static String magicPen = PlayerStatsFormules.magicPen;
    public static String manaMax = PlayerStatsFormules.manaMax;
    public static String manaRegen = PlayerStatsFormules.manaRegen;
    public static String cdReduction = PlayerStatsFormules.cdReduction;
    public static String armorPen = PlayerStatsFormules.armorPen;
    public static String backstab = PlayerStatsFormules.backstab;
    //static String durabilitycolor = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.warningColours.above75%");

    static DecimalFormat df = new DecimalFormat("#.#");

    //static final double ArmorGrowthrate = -0.26;
    static final double Armorxlvl = 4;
    static final double Armorxbase = 100;

    public LoreCraftingStats() {
        super();
        Locale.setDefault(Locale.ROOT);
        df.getDecimalFormatSymbols().setDecimalSeparator('.');

    }

    static public String getLvL(int lvl) {
        return (ChatColor.DARK_GREEN + lvlname + ": " + ChatColor.GREEN + lvl);
    }

    static public String getBackstab(double Max) {
        //double statschance = Max;
        double statsChanceMin = Max / 2;
        double statsChanceMax = Max;
        double stats = statsChanceMin + Math.random() * (statsChanceMax - statsChanceMin);
        stats = stats * 100;
        return ChatColor.DARK_AQUA + backstab + ": " + ChatColor.AQUA + df.format(stats) + "%";
    }

    static public String getArmourPen(double Max) {
        //double statschance = Max;
        double statsChanceMin = Max / 2;
        double statsChanceMax = Max;
        double stats = statsChanceMin + Math.random() * (statsChanceMax - statsChanceMin);
        stats = stats * 100;
        return ChatColor.DARK_AQUA + armorPen + ": " + ChatColor.AQUA + df.format(stats) + "%";
    }

    static public String getArmour(double lvl, ItemStack item, double multiplier) {

        double maxstrength = Armorxbase + (Armorxlvl * lvl);

        if (item.getType().toString().contains("_HELMET")) {
            maxstrength = maxstrength * 0.2;
        }
        if (item.getType().toString().contains("_CHESTPLATE")) {
            maxstrength = maxstrength * 0.34;
        }
        if (item.getType().toString().contains("_LEGGINGS")) {
            maxstrength = maxstrength * 0.29;
        }
        if (item.getType().toString().contains("_BOOTS")) {
            maxstrength = maxstrength * 0.17;
        }
        if (item.getType().toString().contains("DIAMOND_")) {
            maxstrength = maxstrength * 1;
        }
        if (item.getType().toString().contains("IRON_")) {
            maxstrength = maxstrength * 0.9;
        }
        if (item.getType().toString().contains("CHAINMAIL_")) {
            maxstrength = maxstrength * 0.8;
        }
        if (item.getType().toString().contains("GOLD_")) {
            maxstrength = maxstrength * 0.6;
        }
        if (item.getType().toString().contains("LEATHER_")) {
            maxstrength = maxstrength * 0.5;
        }

        double minstrength = maxstrength * 0.75;
        double armourtotal = Math.random() * (maxstrength - minstrength) + minstrength;
        armourtotal = armourtotal * multiplier;
        return ChatColor.DARK_AQUA + armour + ": " + ChatColor.AQUA + (int) armourtotal;
    }

    static public String getMagicArmour(double lvl, ItemStack item, double multiplier) {

        double maxstrength = Armorxbase + (Armorxlvl * lvl);

        if (item.getType().toString().contains("_HELMET")) {
            maxstrength = maxstrength * 0.2;
        }
        if (item.getType().toString().contains("_CHESTPLATE")) {
            maxstrength = maxstrength * 0.34;
        }
        if (item.getType().toString().contains("_LEGGINGS")) {
            maxstrength = maxstrength * 0.29;
        }
        if (item.getType().toString().contains("_BOOTS")) {
            maxstrength = maxstrength * 0.17;
        }
        if (item.getType().toString().contains("DIAMOND_")) {
            maxstrength = maxstrength * 0.5;
        }
        if (item.getType().toString().contains("IRON_")) {
            maxstrength = maxstrength * 0.6;
        }
        if (item.getType().toString().contains("CHAINMAIL_")) {
            maxstrength = maxstrength * 0.7;
        }
        if (item.getType().toString().contains("GOLD_")) {
            maxstrength = maxstrength * 0.9;
        }
        if (item.getType().toString().contains("LEATHER_")) {
            maxstrength = maxstrength * 1;
        }

        double minstrength = maxstrength * 0.75;
        double armourtotal = Math.random() * (maxstrength - minstrength) + minstrength;
        armourtotal = armourtotal * multiplier;
        return ChatColor.LIGHT_PURPLE + magicArmor + ": " + ChatColor.DARK_PURPLE + (int) armourtotal;
    }

    static public String getMagicPower(double lvl, double bonus, double variability) {
        double base = 10;
        double xlvl = 0.70; //top 80
        double max = base + (xlvl * lvl);
        double min = max * variability;
        double total = Math.random() * (max - min) + min;
        total = total * bonus;
        return ChatColor.LIGHT_PURPLE + magicPower + ": " + ChatColor.DARK_PURPLE + (int) total;
    }

    static public String getManaRegenBonus(double lvl, double bonus, double variability) {
        double base = 0;
        double xlvl = 0.25; //25 top
        double max = base + (xlvl * lvl);
        double min = max * variability;
        double total = Math.random() * (max - min) + min;
        total = total * bonus;
        return ChatColor.LIGHT_PURPLE + manaRegen + ": " + ChatColor.DARK_PURPLE + df.format(total);
    }

    static public String getManaBonus(double lvl, double bonus, double variability) {
        double base = 0;
        double xlvl = 9; //top 900
        double max = base + (xlvl * lvl);
        double min = max * variability;
        double total = Math.random() * (max - min) + min;
        total = total * bonus;
        return ChatColor.LIGHT_PURPLE + manaMax + ": " + ChatColor.DARK_PURPLE + df.format(total);
    }

    static public String getCDR(double lvl, double bonus, double variability) {
        double base = 0;
        double xlvl = 0.5; //top 50%
        double max = base + (xlvl * lvl);
        double min = max * variability;
        double total = Math.random() * (max - min) + min;
        total = total * bonus;
        return ChatColor.LIGHT_PURPLE + cdReduction + ": " + ChatColor.DARK_PURPLE + df.format(total) + "%";
    }

    static public String getMagicPen(double lvl, double bonus, double variability) {
        double base = 0;
        double xlvl = 0.5; //top 50%
        double max = base + (xlvl * lvl);
        double min = max * variability;
        double total = Math.random() * (max - min) + min;
        total = total * bonus;
        return ChatColor.LIGHT_PURPLE + magicPen + ": " + ChatColor.DARK_PURPLE + df.format(total) + "%";
    }

    static public String getRandomDamage(int lvl, double speed) {
        return getRandomDamage(lvl, speed, 1, 1);
    }

    static public String getRandomDamage(int lvl, double speed, double bonus, double variability) {
        double mindamage = 0;
        double maxdamage = 0;

        //double dmgxlvl=0.40;
        double dmgxlvl = speed / 5;
        //double rmarginxlvl = dmgxlvl / 2;

        double basedmg = (speed * 2) + 1;
        double dmg = basedmg + (dmgxlvl * lvl);

        double maxDmg = dmg + (dmgxlvl * lvl);
        double minDmg = maxDmg * variability;

        double damage1 = Math.random() * (maxDmg - minDmg) + minDmg;
        double damage2 = Math.random() * (maxDmg - minDmg) + minDmg;

        if (damage1 == damage2) {
            mindamage = damage1 - 1;
            maxdamage = damage2;
        }
        if (damage1 < damage2) {
            mindamage = damage1;
            maxdamage = damage2;
        }
        if (damage1 > damage2) {
            mindamage = damage2;
            maxdamage = damage1;
        }
        maxdamage = bonus * maxdamage;
        mindamage = bonus * mindamage;
        return ChatColor.DARK_AQUA + damage + ": +" + ChatColor.AQUA + df.format(mindamage) + "-" + df.format(maxdamage);
    }

    static public String getRandomCriticalChance(int lvl) {
        return getRandomCriticalChance(lvl, 1);
    }

    static public String getRandomCriticalChance(int lvl, double bonus) {

        double criticalbase = 5;
        double criticalxlvl = 0.2;
        double criticalmax = criticalbase + (criticalxlvl * (double) lvl);

        double critical = Math.random() * (criticalmax - 1) + 1;
        critical = critical * bonus;

        return ChatColor.DARK_AQUA + critChance + ": " + ChatColor.AQUA + df.format(critical) + "%";
    }

    static public String getRandomCriticalDamage(int lvl) {
        return getRandomCriticalDamage(lvl, 1);
    }

    static public String getRandomCriticalDamage(int lvl, double bonus) {

        double criticalbase = 5;
        double criticalxlvl = 0.5;
        double criticalmax = criticalbase + (criticalxlvl * (double) lvl);

        double critical = Math.random() * (criticalmax - 1) + 1;
        critical = critical * bonus;
        return ChatColor.DARK_AQUA + critDamage + ": +" + ChatColor.AQUA + df.format(critical) + "%";
    }

    static public String getSpeed(double speed) {
        return ChatColor.DARK_AQUA + weaponspeed + ": " + ChatColor.AQUA + df.format(speed);
    }

    static public String getPoison(double lvl) {
        double poisonchance = 3;
        double poisonchancexlvl = 0.2;
        double poisonchancemax = poisonchance + (poisonchancexlvl * (double) lvl);
        double poisonstats = Math.random() * (poisonchancemax - 1) + 1;
        return ChatColor.DARK_PURPLE + poison + ": " + ChatColor.LIGHT_PURPLE + df.format(poisonstats) + "%";
    }

    static public String getDurability(int lvl, int materiallvl) {
        double durabilitybase = materiallvl;
        double durabilityxlvl = durabilitybase * ((double) lvl / 100.0);
        double durabilitymax = durabilitybase + durabilityxlvl;
        double durabilityxmin = durabilitybase;
        double durabilityfinal = Math.random() * (durabilitymax - durabilityxmin) + durabilityxmin;
        return ChatColor.DARK_GRAY + durability + ": " + ChatColor.DARK_GRAY + df.format((int) durabilityfinal) + "/" + df.format((int) durabilityfinal);
    }

    static public String getMSpeed(ItemStack item) {
        double speed = 0;
        if (item.getType().toString().contains("LEATHER")) {
            speed = -0.5;
        }
        if (item.getType().toString().contains("GOLD")) {
            speed = -2;
        }
        if (item.getType().toString().contains("CHAINMAIL")) {
            speed = -3.5;
        }
        if (item.getType().toString().contains("IRON")) {
            speed = -4;
        }
        if (item.getType().toString().contains("DIAMOND")) {
            speed = -5;
        }
        if (item.getType().toString().contains("SHIELD")) {
            speed = -5;
        }

        return ChatColor.RED + movementspeed + ": " + ChatColor.DARK_RED + df.format(speed) + "%";
    }

    static public String getArmorDurability(int lvl, ItemStack item) {
        int materiallvl = 5;
        if (item.getType().toString().contains("LEATHER")) {
            materiallvl = 200;
        }
        if (item.getType().toString().contains("GOLD")) {
            materiallvl = 250;
        }
        if (item.getType().toString().contains("CHAINMAIL")) {
            materiallvl = 150;
        }
        if (item.getType().toString().contains("IRON")) {
            materiallvl = 330;
        }
        if (item.getType().toString().contains("DIAMOND")) {
            materiallvl = 470;
        }

        double durabilitybase = materiallvl;
        double durabilityxlvl = durabilitybase * ((double) lvl / 100.0);
        double durabilitymax = durabilitybase + durabilityxlvl;
        double durabilityxmin = durabilitybase;
        double durabilityfinal = Math.random() * (durabilitymax - durabilityxmin) + durabilityxmin;

        return ChatColor.DARK_GRAY + durability + ": " + ChatColor.DARK_GRAY + df.format((int) durabilityfinal) + "/" + df.format((int) durabilityfinal);
    }

    static public String getToolDurability(int lvl, ItemStack item) {
        int materiallvl = 20;
        if (item.getType().toString().contains("WOOD")) {
            materiallvl = 60;
        }
        if (item.getType().toString().contains("STONE")) {
            materiallvl = 140;
        }
        if (item.getType().toString().contains("GOLD")) {
            materiallvl = 40;
        }
        if (item.getType().toString().contains("IRON")) {
            materiallvl = 250;
        }
        if (item.getType().toString().contains("DIAMOND")) {
            materiallvl = 1600;
        }

        double durabilitybase = materiallvl;
        double durabilityxlvl = durabilitybase * ((double) lvl / 100.0);
        double durabilitymax = durabilitybase + durabilityxlvl;
        double durabilityxmin = durabilitybase;
        double durabilityfinal = Math.random() * (durabilitymax - durabilityxmin) + durabilityxmin;

        return ChatColor.DARK_GRAY + durability + ": " + ChatColor.DARK_GRAY + df.format((int) durabilityfinal) + "/" + df.format((int) durabilityfinal);
    }

    static public String getBlock(double lvl) {
        double base = 10;
        double xlvl = 0.4;
        double max = base + (xlvl * (double) lvl);
        double min = max * 0.5;
        double total = Math.random() * (max - min) + min;
        return ChatColor.DARK_AQUA + block + ": " + ChatColor.AQUA + df.format(total) + "%";
    }

    static public String getArmorDodge(int lvl, ItemStack item) {
        double dodgemax = 10; //maximo en % luego dividido en 4

        if (item.getType().toString().contains("LEATHER")) {
            dodgemax = 70;
        }
        if (item.getType().toString().contains("GOLD")) {
            dodgemax = 50;
        }
        if (item.getType().toString().contains("CHAINMAIL")) {
            dodgemax = 80;
        }
        if (item.getType().toString().contains("IRON")) {
            dodgemax = 35;
        }
        if (item.getType().toString().contains("DIAMOND")) {
            dodgemax = 20;
        }
        dodgemax = dodgemax / 4;
        dodgemax = dodgemax * ((double) lvl / 100);
        double dodgemin = dodgemax * 0.5;
        double dodgefinal = Math.random() * (dodgemax - dodgemin) + dodgemin;

        return ChatColor.DARK_AQUA + dodge + ": " + ChatColor.AQUA + df.format(dodgefinal) + "%";

        //return "";
    }

    static public String getDestroy() {
        return ChatColor.DARK_RED + destroy;
    }

    static public String getVoidbound() {
        return ChatColor.GRAY + voidbound;
    }

}
