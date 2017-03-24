package net.nifheim.yitan.itemlorestats;

import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
import net.nifheim.yitan.loncoloreitems.EspecialAtributes;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_RPGInventory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GearStats implements org.bukkit.event.Listener {

    Util_Colours util_Colours = new Util_Colours();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_RPGInventory util_RPGInventory = new Util_RPGInventory();

    GetSlots getSlots = new GetSlots();

    String armour = null;
    String dodge = null;
    String block = null;
    String critChance = null;
    String critDamage = null;
    String damage = null;
    String health = null;
    String healthRegen = null;
    String lifeSteal = null;
    String lifeStealHeal = null;
    String reflect = null;
    String ice = null;
    String fire = null;
    String poison = null;
    String wither = null;
    String harming = null;
    String blind = null;
    String movementspeed = null;
    String weight = null;
    String weaponspeed = null;
    String xpmultiplier = null;
    String pvpdamage = null;
    String pvedamage = null;
    String setbonus = null;
    String xplevel = null;
    String className = null;
    String soulbound = null;
    String durability = null;
    String sellValueName = null;
    String currencyName = null;
    String clickToCast = null;
    String tnt = null;
    String fireball = null;
    String lightning = null;
    String frostbolt = null;
    String minorHeal = null;
    String majorHeal = null;
    String minorAOEHeal = null;
    String majorAOEHeal = null;

    String heroes_MaxMana = null;
    String skillAPI_MaxMana = null;

    String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

    final double ArmorGrowthrate = -0.26;
    final double Armorxlvl = 4;
    final double Armorxbase = 100;
    static DecimalFormat df = new DecimalFormat("#.#");

    public String getTotalMovementSpeed(Player player) {
        double stat = Double.valueOf(player.getLevel()).doubleValue() * ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.speed") * 100;

        if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
            stat += getMovementSpeedItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
        }

        if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
            stat += getMovementSpeedItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
        }

        stat += getMovementSpeedGear(player);

        return df.format(stat);
    }

    public double Armorpenaltyxlevel(double basestrength, int lvl) {
        double maxstrength = Armorxbase + (Armorxlvl * lvl);
        double penalty = basestrength / maxstrength;
        if (penalty > 1) {
            penalty = 1;
        }
        return penalty;
    }

    public double ArmorStrength(double basestrength, int lvl) {
        int strength = (int) basestrength;
        if (strength == 0) {
            strength = 1;
        }

        double armor = 1 - (Math.pow(strength, ArmorGrowthrate));
        armor = armor * Armorpenaltyxlevel(strength, lvl);
        if (armor > 0.9) {
            armor = 0.9;
        }
        armor = armor * 100;

        return armor;
    }

    public double getArmourGear(LivingEntity entity) {
        this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name").replaceAll(" ", "");

        double armourValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if (gear != null) {
                if (gear.getItemMeta() != null) {
                    if (gear.getItemMeta().getLore() != null) {
                        List<String> itemLore = gear.getItemMeta().getLore();

                        for (String line : itemLore) {
                            String lore = ChatColor.stripColor(line.toString());

                            if (lore.replaceAll(this.languageRegex, "").matches(this.armour)) {

                                armourValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                            }
                        }
                    }
                }
            }
        }

        int lvl = 0;

        if (entity instanceof Player) {
            lvl = ((Player) entity).getLevel();

        }
        armourValue = ArmorStrength(armourValue, lvl);
        return this.util_Format.format(armourValue);
    }

    public double getStrengthGear(LivingEntity entity) {
        this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name").replaceAll(" ", "");

        double armourValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.armour)) {

                        armourValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }
        return this.util_Format.format(armourValue);
    }

    public double getDodgeGear(LivingEntity entity) {
        this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name").replaceAll(" ", "");

        double dodgeValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.dodge)) {
                        dodgeValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(dodgeValue);
    }

    public double getBlockGear(LivingEntity entity) {
        this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name").replaceAll(" ", "");

        double blockValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.block)) {
                        blockValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(blockValue);
    }

    public String getPlayerDamage(Player player) {
        double maxdamage = 0;
        double mindamage = 0;

        mindamage = mindamage + getDamageFromOffHand(player)[0];
        maxdamage = maxdamage + getDamageFromOffHand(player)[1];

        mindamage = mindamage + Double.parseDouble(getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player)).split("-")[0]);
        maxdamage = maxdamage + Double.parseDouble(getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player)).split("-")[1]);

        mindamage = mindamage + Double.parseDouble(getDamageGear(player).split("-")[0]);
        maxdamage = maxdamage + Double.parseDouble(getDamageGear(player).split("-")[1]);

        return df.format(mindamage) + "-" + df.format(maxdamage);
    }

    public String getDamageGear(LivingEntity entity) {
        double damageMinValue = 0.0D;
        double damageMaxValue = 0.0D;
        this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
        if (entity != null) {
            if (entity.getEquipment() != null) {

                ItemStack[] arrayOfItemStack;
                int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                for (int i = 0; i < j; i++) {
                    ItemStack gear = arrayOfItemStack[i];
                    if ((gear != null)
                            && (gear.hasItemMeta())
                            && (gear.getItemMeta().hasLore())) {

                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            ItemStack offhand = player.getInventory().getItemInOffHand();
                            ItemStack mainhand = player.getInventory().getItemInMainHand();

                            if (mainhand.getType().equals(Material.BOW) && gear.equals(offhand)) {
                                addDamageFromItem(damageMinValue, damageMaxValue, gear, 0);
                            }
                            if (!mainhand.getType().equals(Material.BOW) && gear.equals(offhand)) {
                                if (!offhand.getType().equals(Material.BOW)) {
                                    double weaponspeed1 = EspecialAtributes.getWeaponSpeed(offhand);
                                    double weaponspeed2 = EspecialAtributes.getWeaponSpeed(mainhand);
                                    double damagefactor = (weaponspeed2 / weaponspeed1) * 0.5;
                                    addDamageFromItem(damageMinValue, damageMaxValue, gear, damagefactor);
                                }
                            }

                            //Player player = (Player) entity;
                            //damageMinValue = getDamageFromOffHand(player)[0];
                            //damageMinValue = getDamageFromOffHand(player)[1];
                        } else {
                            addDamageFromItem(damageMinValue, damageMaxValue, gear, 1);
                        }
                    }
                }
            }
        }
        if (damageMinValue < 1) {
            damageMinValue = 1;
        }
        if (damageMaxValue < 1) {
            damageMaxValue = 1;
        }
        return damageMinValue + "-" + damageMaxValue;
    }

    public double[] getDamageFromOffHand(Player player) {
        //Player player = (Player) getAttacker;

        double valueMinOff = 0;
        double valueMaxOff = 0;
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
                double damages[] = getDamageFromItem(gear, damagefactor);
                valueMinOff = damages[0];
                valueMaxOff = damages[1];
            }
        }
        double[] values = {valueMinOff, valueMaxOff};
        return values;
    }

    public void addDamageFromItem(double damageMinValue, double damageMaxValue, ItemStack gear, double multiplier) {
        List<String> itemLore = gear.getItemMeta().getLore();
        for (String line : itemLore) {
            String lore = ChatColor.stripColor(line.toString());
            lore = lore.toLowerCase();
            if (lore.replaceAll(this.languageRegex, "").matches(damage.toLowerCase())) {
                if (lore.contains("-")) {
                    damageMinValue += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", "")) * multiplier;
                    damageMaxValue += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", "")) * multiplier;
                } else {
                    damageMinValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier;
                    damageMaxValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier;
                }
            }
        }
    }

    public double[] getDamageFromItem(ItemStack gear, double multiplier) {
        this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");
        double damageMinValue = 0;
        double damageMaxValue = 0;
        if (gear != null) {
            if (gear.getItemMeta() != null) {
                if (gear.getItemMeta().getLore() != null) {
                    List<String> itemLore = gear.getItemMeta().getLore();

                    for (String line : itemLore) {
                        String lore = ChatColor.stripColor(line.toString());
                        lore = lore.toLowerCase();
                        if (lore.replaceAll(languageRegex, "").matches(damage.toLowerCase())) {

                            if (lore.contains("-")) {
                                damageMinValue += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", "")) * multiplier;
                                damageMaxValue += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", "")) * multiplier;
                            } else {
                                damageMinValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier;
                                damageMaxValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", "")) * multiplier;
                            }
                        }
                    }
                }
            }
        }

        double[] values = {damageMinValue, damageMaxValue};
        return values;
    }

    public double getCritChanceGear(LivingEntity entity) {
        this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name").replaceAll(" ", "");

        double critChanceValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.critChance)) {
                        critChanceValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(critChanceValue);
    }

    public double getCritDamageGear(LivingEntity entity) {
        this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name").replaceAll(" ", "");

        double critDamageValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.critDamage)) {
                        critDamageValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(critDamageValue);
    }

    public double getHealthGear(LivingEntity entity) {
        this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name").replaceAll(" ", "");

        double healthValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.health)) {
                        healthValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(healthValue);
    }

    public double getHealthRegenGear(LivingEntity entity) {
        this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name").replaceAll(" ", "");

        double healthRegenValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.healthRegen)) {
                        healthRegenValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(healthRegenValue);
    }

    public double getLifeStealGear(LivingEntity entity) {
        this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name").replaceAll(" ", "");

        double lifeStealValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {

                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.lifeSteal)) {
                                    lifeStealValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(lifeStealValue);
    }

    public double getLifeStealHealGear(LivingEntity entity) {
        this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name").replaceAll(" ", "");

        double lifeStealHealValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.lifeStealHeal)) {
                                    lifeStealHealValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(lifeStealHealValue);
    }

    public double getReflectGear(LivingEntity entity) {
        this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name").replaceAll(" ", "");

        double reflectValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.reflect)) {
                                    reflectValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(reflectValue);
    }

    public double getFireGear(LivingEntity entity) {
        this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name").replaceAll(" ", "");

        double fireValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.fire)) {
                                    fireValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(fireValue);
    }

    public double getIceGear(LivingEntity entity) {
        this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name").replaceAll(" ", "");

        double iceValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.ice)) {
                                    iceValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(iceValue);
    }

    public double getPoisonGear(LivingEntity entity) {
        this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name").replaceAll(" ", "");

        double poisonValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.poison)) {
                                    poisonValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(poisonValue);
    }

    public double getWitherGear(LivingEntity entity) {
        this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name").replaceAll(" ", "");

        double witherValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.wither)) {
                                    witherValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(witherValue);
    }

    public double getHarmingGear(LivingEntity entity) {
        this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name").replaceAll(" ", "");

        double harmingValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.harming)) {
                                    harmingValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.util_Format.format(harmingValue);
    }

    public double getBlindGear(LivingEntity entity) {
        this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name").replaceAll(" ", "");

        double blindValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getArmorContents() != null) {
                    int j = (arrayOfItemStack = entity.getEquipment().getArmorContents()).length;
                    for (int i = 0; i < j; i++) {
                        ItemStack gear = arrayOfItemStack[i];
                        if ((gear != null)
                                && (gear.hasItemMeta())
                                && (gear.getItemMeta().hasLore())) {
                            List<String> itemLore = gear.getItemMeta().getLore();

                            for (String line : itemLore) {
                                String lore = ChatColor.stripColor(line.toString());

                                if (lore.replaceAll(this.languageRegex, "").matches(this.blind)) {
                                    blindValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                                }
                            }
                        }
                    }

                }
            }
        }

        return this.util_Format.format(blindValue);
    }

    public double getMovementSpeedGear(Player player) {
        this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name").replaceAll(" ", "");

        double movementspeedValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = player.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if (gear != null) {
                if (gear.getItemMeta() != null) {
                    if (gear.getItemMeta().getLore() != null) {
                        List<String> itemLore = gear.getItemMeta().getLore();

                        for (String line : itemLore) {
                            String lore = ChatColor.stripColor(line.toString());

                            if (lore.replaceAll(this.languageRegex, "").matches(this.movementspeed)) {
                                movementspeedValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                            }
                        }
                    }
                }
            }

        }

        return this.util_Format.format(movementspeedValue);
    }

    public double getXPMultiplierGear(Player player) {
        this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name").replaceAll(" ", "");

        double xpmultiplierValue = 0.0D;
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = player.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(this.xpmultiplier)) {
                        xpmultiplierValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(xpmultiplierValue);
    }

    public double get_MaxManaGear(Player player) {
        String mana = null;

        double maxManaValue = 0.0D;
        ItemStack[] arrayOfItemStack;

        int j = (arrayOfItemStack = player.getEquipment().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack gear = arrayOfItemStack[i];
            if ((gear != null)
                    && (gear.hasItemMeta())
                    && (gear.getItemMeta().hasLore())) {
                List<String> itemLore = gear.getItemMeta().getLore();

                for (String line : itemLore) {
                    String lore = ChatColor.stripColor(line.toString());

                    if (lore.replaceAll(this.languageRegex, "").matches(mana)) {
                        maxManaValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return this.util_Format.format(maxManaValue);
    }

    public double getArmourItemInHand(ItemStack item) {
        this.armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.armour)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getDodgeItemInHand(ItemStack item) {
        this.dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.dodge)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getBlockItemInHand(ItemStack item) {
        this.block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.block)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public String getDamageItemInHand(ItemStack item) {
        this.damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name").replaceAll(" ", "");

        double damageMinValue = 0.0D;
        double damageMaxValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.damage)) {
                    if (lore.contains("-")) {
                        damageMinValue += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""));
                        damageMaxValue += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""));
                    } else {
                        damageMinValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                        damageMaxValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                    }
                }
            }
        }

        return damageMinValue + "-" + damageMaxValue;
    }

    public double getCritChanceItemInHand(ItemStack item) {
        this.critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.critChance)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getCritDamageItemInHand(ItemStack item) {
        this.critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.critDamage)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getHealthItemInHand(ItemStack item) {
        this.health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.health)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getHealthRegenItemInHand(ItemStack item) {
        this.healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.healthRegen)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getLifeStealItemInHand(ItemStack item) {
        this.lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.lifeSteal)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getLifeStealHealItemInHand(ItemStack item) {
        this.lifeStealHeal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeStealHeal.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.lifeStealHeal)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getReflectItemInHand(ItemStack item) {
        this.reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.reflect)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getIceItemInHand(ItemStack item) {
        this.ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.ice)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getFireItemInHand(ItemStack item) {
        this.fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.fire)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getPoisonItemInHand(ItemStack item) {
        this.poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.poison)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getWitherItemInHand(ItemStack item) {
        this.wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.wither)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getHarmingItemInHand(ItemStack item) {
        this.harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.harming)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getBlindItemInHand(ItemStack item) {
        this.blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.blind)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getMovementSpeedItemInHand(ItemStack item) {
        this.movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.movementspeed)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getXPMultiplierItemInHand(ItemStack item) {
        this.xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.xpmultiplier)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getPvPDamageModifierItemInHand(ItemStack item) {
        this.pvpdamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pvpDamage.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.pvpdamage)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public double getPvEDamageModifierItemInHand(ItemStack item) {
        this.pvedamage = ItemLoreStats.plugin.getConfig().getString("bonusStats.pveDamage.name").replaceAll(" ", "");

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.pvedamage)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public int getSellValueItemInHand(ItemStack item) {
        this.sellValueName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.name").replaceAll(" ", "");
        this.currencyName = ItemLoreStats.plugin.getConfig().getString("bonusStats.sellValue.currency.name").replaceAll(" ", "");

        int itemInHandValue = 0;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.sellValueName + this.currencyName)) {
                    itemInHandValue += Integer.parseInt(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return itemInHandValue;
    }

    public double get_MaxManaItemInHandX(ItemStack item) {
        String mana = null;

        double itemInHandValue = 0.0D;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(mana)) {
                    itemInHandValue += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""));
                }
            }
        }

        return this.util_Format.format(itemInHandValue);
    }

    public int getXPLevelRequirement(Player player, ItemStack item) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");

        int itemXPLevel = 0;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.xplevel)) {
                    itemXPLevel = Integer.parseInt(lore.split("\\+")[0].replaceAll("[^0-9.+-]", ""));

                    return itemXPLevel;
                }
            }
        }

        return itemXPLevel;
    }

    public int getXPLevelRequirementItemOnPickup(ItemStack itemOnPickup) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");

        int itemInHandValue = 0;

        ItemStack gear = itemOnPickup;

        if ((gear != null)
                && (gear.hasItemMeta())
                && (gear.getItemMeta().hasLore())) {
            List<String> itemLore = gear.getItemMeta().getLore();

            for (String line : itemLore) {
                String lore = ChatColor.stripColor(line.toString());

                if (lore.replaceAll(this.languageRegex, "").matches(this.xplevel)) {
                    itemInHandValue = Integer.parseInt(lore.split("\\+")[0].replaceAll("[^0-9.+-]", ""));

                    return itemInHandValue;
                }
            }
        }

        return itemInHandValue;
    }

    public String getSoulboundName(Player player, ItemStack item) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");

        String storeLoreValues = "";

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                    storeLoreValues = value;
                    return storeLoreValues;
                }
            }
        }

        return storeLoreValues;
    }

    public String getSoulboundNameItemOnPickup(ItemStack itemOnPickup) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");

        String storeLoreValues = "";

        ItemStack item = itemOnPickup;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();
            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                    storeLoreValues = value;
                    return storeLoreValues;
                }
            }
        }

        return storeLoreValues;
    }

    public ArrayList<String> getClass(ItemStack item) {
        this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name").replaceAll(" ", "");

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.className + ": ")) {
                    String value = ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
                    ArrayList<String> storeLoreValues = new ArrayList(java.util.Arrays.asList(value.split(ItemLoreStats.plugin.getConfig().getString("bonusStats.class.separator"))));
                    return storeLoreValues;
                }
            }
        }

        return null;
    }

    public String phic_SetBonusItemInHand(ItemStack itemstack) {
        this.setbonus = ItemLoreStats.plugin.getConfig().getString("bonusStats.setBonus.name").replaceAll(" ", "");
        String weaponKey = "";

        ItemStack itemInHand = itemstack;

        if ((itemInHand != null)
                && (itemInHand.hasItemMeta())
                && (ItemLoreStats.plugin.isTool(itemInHand.getType()))
                && (itemInHand.getItemMeta().hasLore())) {
            List<String> itemInHandLore = itemInHand.getItemMeta().getLore();
            for (String line : itemInHandLore) {
                if (line.contains(this.setbonus)) {
                    String value = this.util_Colours.extractAndReplaceTooltipColour(line.substring(0, 6));
                    weaponKey = value;
                    return weaponKey;
                }
            }
        }

        return weaponKey;
    }

    public String phic_SoulboundNameItemInHand(ItemStack itemstack) {
        this.soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");

        String storeLoreValues = "";

        ItemStack item = itemstack;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.soulbound)) {
                    String value = ChatColor.stripColor(line).substring(this.soulbound.length()).trim();
                    storeLoreValues = value;
                    return storeLoreValues;
                }
            }
        }

        return null;
    }

    public String phic_ClassItemInHand(ItemStack itemstack) {
        this.className = ItemLoreStats.plugin.getConfig().getString("bonusStats.class.name").replaceAll(" ", "");

        String storeLoreValues = "";

        ItemStack item = itemstack;

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(this.className + ": ")) {
                    String value = ChatColor.stripColor(line).substring((this.className + ": ").length()).trim();
                    storeLoreValues = value;
                    return storeLoreValues;
                }
            }
        }

        return null;
    }

    public int phic_XPLevelRequirementItemInHand(ItemStack itemstack) {
        this.xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name").replaceAll(" ", "");

        int storeLoreValues = 0;

        ItemStack itemInHand = itemstack;

        if ((itemInHand != null)
                && (itemInHand.hasItemMeta())
                && (itemInHand.getItemMeta().hasLore())) {
            List<String> itemInHandLore = itemInHand.getItemMeta().getLore();
            for (String line : itemInHandLore) {
                if (ChatColor.stripColor(line).startsWith(this.xplevel + ": ")) {
                    String xpLevelValue = ChatColor.stripColor(line).substring((this.xplevel + ": ").length()).trim();
                    try {
                        if (xpLevelValue.contains("[+")) {
                            storeLoreValues = Integer.parseInt(xpLevelValue.split(" ")[0]);
                        }
                        return Integer.parseInt(xpLevelValue);
                    } catch (NumberFormatException localNumberFormatException) {
                    }
                }
            }
        }

        return 0;
    }

    public String getSpellName(ItemStack item) {
        String castString = ChatColor.stripColor(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "").replaceAll("&([0-9a-f])", ""));

        if ((item != null)
                && (item.hasItemMeta())
                && (item.getItemMeta().hasLore())) {
            List<String> itemLore = item.getItemMeta().getLore();

            for (String line : itemLore) {
                if (ChatColor.stripColor(line).startsWith(castString)) {
                    String value = ChatColor.stripColor(line).substring(castString.length()).trim();

                    return value;
                }
            }
        }

        return null;
    }

    public void containsSetBonus() {
    }
}
