package net.nifheim.yitan.itemlorestats;

import net.nifheim.yitan.loncoloreitems.EspecialAtributes;

import net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Sharpness;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_Material;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CharacterSheet {

    GearStats gearStats = new GearStats();
    SetBonuses setBonuses = new SetBonuses();

    Util_Colours util_Colours = new Util_Colours();
    Util_Material util_Material = new Util_Material();
    Util_Format util_Format = new Util_Format();
    net.nifheim.yitan.itemlorestats.Util.Util_EntityManager util_EntityManager = new net.nifheim.yitan.itemlorestats.Util.Util_EntityManager();

    Vanilla_Sharpness vanilla_Sharpness = new Vanilla_Sharpness();
    net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Power vanilla_Power = new net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Power();

    String armour = Main.plugin.getConfig().getString("primaryStats.armour.colour") + Main.plugin.getConfig().getString("primaryStats.armour.name");
    String dodge = Main.plugin.getConfig().getString("secondaryStats.dodge.colour") + Main.plugin.getConfig().getString("secondaryStats.dodge.name");
    String block = Main.plugin.getConfig().getString("secondaryStats.block.colour") + Main.plugin.getConfig().getString("secondaryStats.block.name");
    String critChance = Main.plugin.getConfig().getString("secondaryStats.critChance.colour") + Main.plugin.getConfig().getString("secondaryStats.critChance.name");
    String critDamage = Main.plugin.getConfig().getString("secondaryStats.critDamage.colour") + Main.plugin.getConfig().getString("secondaryStats.critDamage.name");
    String damage = Main.plugin.getConfig().getString("primaryStats.damage.colour") + Main.plugin.getConfig().getString("primaryStats.damage.name");
    String health = Main.plugin.getConfig().getString("primaryStats.health.colour") + Main.plugin.getConfig().getString("primaryStats.health.name");
    String healthRegen = Main.plugin.getConfig().getString("primaryStats.healthRegen.colour") + Main.plugin.getConfig().getString("primaryStats.healthRegen.name");
    String lifeSteal = Main.plugin.getConfig().getString("secondaryStats.lifeSteal.colour") + Main.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
    String reflect = Main.plugin.getConfig().getString("secondaryStats.reflect.colour") + Main.plugin.getConfig().getString("secondaryStats.reflect.name");
    String fire = Main.plugin.getConfig().getString("secondaryStats.fire.colour") + Main.plugin.getConfig().getString("secondaryStats.fire.name");
    String ice = Main.plugin.getConfig().getString("secondaryStats.ice.colour") + Main.plugin.getConfig().getString("secondaryStats.ice.name");
    String poison = Main.plugin.getConfig().getString("secondaryStats.poison.colour") + Main.plugin.getConfig().getString("secondaryStats.poison.name");
    String wither = Main.plugin.getConfig().getString("secondaryStats.wither.colour") + Main.plugin.getConfig().getString("secondaryStats.wither.name");
    String harming = Main.plugin.getConfig().getString("secondaryStats.harming.colour") + Main.plugin.getConfig().getString("secondaryStats.harming.name");
    String blind = Main.plugin.getConfig().getString("secondaryStats.blind.colour") + Main.plugin.getConfig().getString("secondaryStats.blind.name");
    String xpmultiplier = Main.plugin.getConfig().getString("bonusStats.xpMultiplier.colour") + Main.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
    String movementspeed = Main.plugin.getConfig().getString("secondaryStats.movementSpeed.colour") + Main.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
    String level = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");
    String onlydamage = Main.plugin.getConfig().getString("primaryStats.damage.name");

    static String languageRegex = "[^A-Za-z������������_]";

    public double[] addDamageFromItem(ItemStack gear, double multiplier) {
        double damages[] = {0, 0};
        List<String> itemLore = gear.getItemMeta().getLore();
        for (String line : itemLore) {
            String lore = ChatColor.stripColor(line.toString());
            lore = lore.toLowerCase();
            if (lore.replaceAll(languageRegex, "").matches(onlydamage.toLowerCase())) {
                if (lore.contains("-")) {
                    damages[0] += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", "")) * multiplier;
                    damages[1] += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", "")) * multiplier;
                } else {
                    damages[0] += Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier;
                    damages[1] += Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier;
                }
            }
        }

        return damages;
    }

    public void returnStats(Player player, double passHealth) {
        player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "Stats:");
        player.sendMessage(getArmourStrengthValue(player));
        player.sendMessage(getArmourValue(player));
        player.sendMessage(getDodgeValue(player));
        player.sendMessage(getBlockValue(player));

        double minDamage = 0.0D;
        double maxDamage = 0.0D;

        double valueMinMain = 0.0D;
        double valueMaxMain = 0.0D;
        double valueMinOff = 0.0D;
        double valueMaxOff = 0.0D;

        minDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]);
        maxDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]);

        if (Main.plugin.isTool(player.getInventory().getItemInMainHand().getType())) {
            if (Main.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
                minDamage += this.util_Material.materialToDamage(player.getInventory().getItemInMainHand().getType());
                maxDamage += this.util_Material.materialToDamage(player.getInventory().getItemInMainHand().getType());
            }

            valueMinMain = Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInMainHand(player)).split("-")[0]);
            valueMaxMain = Double.parseDouble(this.gearStats.getDamageItemInHand(Main.plugin.itemInMainHand(player)).split("-")[1]);

            minDamage += valueMinMain;
            maxDamage += valueMaxMain;
        }

        if (Main.plugin.isTool(player.getInventory().getItemInOffHand().getType())) {
            if (Main.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
                minDamage += this.util_Material.materialToDamage(player.getInventory().getItemInOffHand().getType());
                maxDamage += this.util_Material.materialToDamage(player.getInventory().getItemInOffHand().getType());
            }

            if (true) {
                //Player player = (Player) entity;
                ItemStack offhand = player.getInventory().getItemInOffHand();
                ItemStack mainhand = player.getInventory().getItemInMainHand();
                ItemStack gear = offhand;

                if (mainhand.getType().equals(Material.BOW) && gear.equals(offhand)) {
                    valueMinOff = 0;
                    valueMaxOff = 0;
                }
                if (!mainhand.getType().equals(Material.BOW) && gear.equals(offhand)) {
                    if (!offhand.getType().equals(Material.BOW)) {
                        double weaponspeed1 = EspecialAtributes.getWeaponSpeed(offhand);
                        double weaponspeed2 = EspecialAtributes.getWeaponSpeed(mainhand);
                        double damagefactor = (weaponspeed2 / weaponspeed1) * 0.5;
                        //Bukkit.broadcastMessage(""+damagefactor);
                        double damages[] = addDamageFromItem(gear, damagefactor);
                        valueMinOff = damages[0];
                        valueMaxOff = damages[1];
                    }
                }
            }
            //valueMinOff = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player)).split("-")[0]);
            //valueMaxOff = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player)).split("-")[1]);

            minDamage += valueMinOff;
            maxDamage += valueMaxOff;
        }

        if (this.vanilla_Sharpness.hasSharpness(player)) {
            int mainLevel = Main.plugin.itemInMainHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            int offLevel = Main.plugin.itemInOffHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL);

            minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, mainLevel, offLevel);
            maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, mainLevel, offLevel);
        } else if (this.vanilla_Power.hasPower(player)) {
            int mainLevel = Main.plugin.itemInMainHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
            int offLevel = Main.plugin.itemInOffHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE);

            minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, mainLevel, offLevel);
            maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, mainLevel, offLevel);
        }

        player.sendMessage("    " + this.util_Colours.replaceTooltipColour(this.damage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(minDamage) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.damage)) + " - " + ChatColor.LIGHT_PURPLE + this.util_Format.format(maxDamage));

        player.sendMessage(getCritChanceValue(player));
        player.sendMessage(getCritDamageValue(player));
        player.sendMessage("    " + this.util_Colours.replaceTooltipColour(this.health) + ": " + ChatColor.LIGHT_PURPLE + passHealth);

        player.sendMessage(getHealthRegenValue(player));
        player.sendMessage(getLifeStealValue(player));
        player.sendMessage(getReflectValue(player));
        player.sendMessage(getFireValue(player));
        player.sendMessage(getIceValue(player));
        player.sendMessage(getPoisonValue(player));
        player.sendMessage(getWitherValue(player));
        player.sendMessage(getHarmingValue(player));
        player.sendMessage(getBlindValue(player));
        player.sendMessage(getXPMultiplierValue(player));
        player.sendMessage(getMovementSpeedValue(player));
    }

    public String getArmourValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getArmourItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getArmourItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getArmourGear(player);

        String message = "    " + ChatColor.DARK_AQUA + "Defensa: " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.armour)) + "%";

        return message;
    }

    public String getArmourStrengthValue(Player player) {
        double stat = 0.0D;

        stat += this.gearStats.getStrengthGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.armour) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.armour));

        return message;
    }

    public String getDodgeValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getDodgeItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getDodgeItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getDodgeGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.dodge) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.dodge)) + "%";

        return message;
    }

    public String getBlockValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getBlockItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getBlockItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getBlockGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.block) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.block)) + "%";

        return message;
    }

    public double getBlockValuedouble(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getBlockItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getBlockItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getBlockGear(player);

        //String message = "    " + this.util_Colours.replaceTooltipColour(this.block) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.block)) + "%";
        return stat;
    }

    public String getCritChanceValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getCritChanceItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getCritChanceItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getCritChanceGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.critChance) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.critChance)) + "%";

        return message;
    }

    public String getCritDamageValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getCritDamageItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getCritDamageItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getCritDamageGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.critDamage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.critDamage)) + "%";

        return message;
    }

    public String getHealthRegenValue(Player player) {
        double stat = Double.valueOf(player.getLevel()).doubleValue() * Main.plugin.getConfig().getDouble("additionalStatsPerLevel.healthRegen");

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getHealthRegenItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getHealthRegenItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getHealthRegenGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.healthRegen) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.healthRegen)) + "%";

        return message;
    }

    public String getLifeStealValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getLifeStealItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getLifeStealItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getLifeStealGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.lifeSteal) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.lifeSteal)) + "%";

        return message;
    }

    public String getReflectValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getReflectItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getReflectItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getReflectGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.reflect) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.reflect)) + "%";

        return message;
    }

    public String getFireValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getFireItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getFireItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getFireGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.fire) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.fire)) + "%";

        return message;
    }

    public String getIceValue(Player player) {
        double stat = 0.0D;
        String message = "    " + this.util_Colours.replaceTooltipColour(this.ice) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.ice)) + "%";

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getIceItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getIceItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getIceGear(player);

        return message;
    }

    public String getPoisonValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getPoisonItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getPoisonItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getPoisonGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.poison) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.poison)) + "%";

        return message;
    }

    public String getWitherValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getWitherItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getWitherItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getWitherGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.wither) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.wither)) + "%";

        return message;
    }

    public String getHarmingValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getHarmingItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getHarmingItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getHarmingGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.harming) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.harming)) + "%";

        return message;
    }

    public String getBlindValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getBlindItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getBlindItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getBlindGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.blind) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.blind)) + "%";

        return message;
    }

    public String getXPMultiplierValue(Player player) {
        double stat = 0.0D;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getXPMultiplierItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getXPMultiplierItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getXPMultiplierGear(player);
        String message = "    " + this.util_Colours.replaceTooltipColour(this.xpmultiplier) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.xpmultiplier)) + "%";

        return message;
    }

    public String getMovementSpeedValue(Player player) {
        double stat = Double.valueOf(player.getLevel()).doubleValue() * Main.plugin.getConfig().getDouble("additionalStatsPerLevel.speed") * 100;

        if (Main.plugin.isTool(Main.plugin.itemInMainHand(player).getType())) {
            stat += this.gearStats.getMovementSpeedItemInHand(Main.plugin.itemInMainHand(player));
        }

        if (Main.plugin.isTool(Main.plugin.itemInOffHand(player).getType())) {
            stat += this.gearStats.getMovementSpeedItemInHand(Main.plugin.itemInOffHand(player));
        }

        stat += this.gearStats.getMovementSpeedGear(player);

        String message = "    " + this.util_Colours.replaceTooltipColour(this.movementspeed) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.movementspeed)) + "%";

        return message;
    }
}
