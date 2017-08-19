package net.nifheim.yitan.items;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.lorestats.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreItemMaker {

    private static final Main plugin = Main.getInstance();
    private static final FileConfiguration messages = Main.getInstance().getMessages();
    public static String unknownItem = EspecialAtributes.unknownItem;
    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

    static public ItemStack CheckItemLore(ItemStack item, Player player) {
        if (EspecialAtributes.HasEffect(item, unknownItem)) {
            return ClearAndAddItemLore(item, player);
        } else {
            return AddItemLore(item, player);
        }
    }

    static public ItemStack AddItemLore(ItemStack item, Player player) {
        double lvl = player.getLevel();
        lvl = Math.random() * (lvl - lvl / 2) + lvl / 2;
        return AddItemLore(item, player, (int) lvl);
    }

    static public ItemStack ClearAndAddItemLore(ItemStack item, Player player) {
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(temlore);
        item.setItemMeta(meta);
        return AddItemLore(item, player);
    }

    static public ItemStack ClearAndAddItemLore(ItemStack item, Player player, int lvl) {
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(temlore);
        item.setItemMeta(meta);
        return AddItemLore(item, player, lvl);
    }

    static public ItemStack AddItemLore(ItemStack item, Player player, int lvl) {
        if (item.getType().equals(Material.BOW)) {
            switch (item.getDurability()) {
                case 2:
                    generatebow(item, player, lvl, 6, 4, 275); //balletas
                    break;
                case 1:
                    generatebow(item, player, lvl, 4, 3, 385, 1.1, 1.1, 1.1, 1); //arco astral
                    break;
                default:
                    generatebow(item, player, lvl, 4, 2, 385);
                    break;
            }
        } else if (item.getType().equals(Material.WOOD_SWORD)) {
            generatesword(item, player, lvl, 1, 2, 60);
        } else if (item.getType().equals(Material.STONE_SWORD)) {
            generatesword(item, player, lvl, 1, 2, 140);
        } else if (item.getType().equals(Material.GOLD_SWORD)) {
            generatesword(item, player, lvl, 1, 2, 40);
        } else if (item.getType().equals(Material.IRON_SWORD)) {
            generatesword(item, player, lvl, 1, 2, 250);
        } else if (item.getType().equals(Material.DIAMOND_SWORD)) {
            switch (item.getDurability()) {
                case 7:
                    //Baston magico 1
                    generateMagicWeapon(item, player, lvl, 2, 3, 200, 0.5, 0.5);
                    break;
                case 21:
                    //Espada Dorada
                    generatesword(item, player, lvl, 0.9, 1.5, 250, 1.2, 1, 1, 0.8, 0, 0, true);
                    break;
                case 25:
                    //cimitarra
                    generatesword(item, player, lvl, 1.5, 2.2, 1600, 1, 1, 1.2, 0.7, 0, 0, true);
                    break;
                case 27:
                    //espada caballero
                    generatesword(item, player, lvl, 1, 2.5, 600, 1.1, 1, 1, 0.8, 0, 0, true);
                    break;

                case 38:
                    //guadaña
                    generatesword(item, player, lvl, 3, 4, 1300, 1.3, 0.6, 1.3, 0.35, 0, 0, true);
                    break;
                case 43:
                    //maza de guerra
                    generatesword(item, player, lvl, 3, 5, 400, 0.8, 1, 2, 0.5, 0.5, 0, true);
                    break;
                case 90:
                    //Daga
                    generatesword(item, player, lvl, 0.6, 1, 500, 0.7, 2, 2, 0.8, 0.4, 0.7, true);
                    break;
                case 125:
                    //Baston magico sin nombre
                    generateMagicWeapon(item, player, lvl, 2, 3, 200, 0.5, 0.5);
                    break;
                default:
                    generatesword(item, player, lvl, 1, 2, 1600);
                    break;
            }

        } else if (item.getType().equals(Material.WOOD_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 60, true);
        } else if (item.getType().equals(Material.STONE_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 140, true);
        } else if (item.getType().equals(Material.GOLD_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 40, true);
        } else if (item.getType().equals(Material.IRON_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 250, true);
        } else if (item.getType().equals(Material.DIAMOND_AXE)) {
            generataxe(item, player, lvl, 2, 3, 1600, true);
        } else if (item.getType().toString().contains("HELMET")) {
            generateArmor(item, player, lvl, true);
        } else if (item.getType().toString().contains("CHESTPLATE")) {
            generateArmor(item, player, lvl, true);
        } else if (item.getType().toString().contains("LEGGINGS")) {
            generateArmor(item, player, lvl, true);
        } else if (item.getType().toString().contains("BOOTS")) {
            generateArmor(item, player, lvl, true);
        } else if (item.getType().toString().contains("PICKAXE")) {
            generatetool(item, player, lvl, true);
        } else if (item.getType().toString().contains("HOE")) {
            if (item.getDurability() == 0) {
                generatetool(item, player, lvl, true);
            }
        } else if (item.getType().toString().contains("SPADE")) {
            generatetool(item, player, lvl, true);
        } else if (item.getType().equals(Material.FISHING_ROD)) {
            generateothertools(item, player, lvl, 65, false);
        } else if (item.getType().equals(Material.FLINT_AND_STEEL)) {
            generateothertools(item, player, lvl, 65, false);
        } else if (item.getType().equals(Material.CARROT_STICK)) {
            generateothertools(item, player, lvl, 26, false);
        } else if (item.getType().equals(Material.SHEARS)) {
            generateothertools(item, player, lvl, 238, false);
        } else if (item.getType().equals(Material.SHIELD)) {
            generatescudo(item, player, lvl, 337, true);
        }
        return item;
    }

    public static void generatebow(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl) {
        generatebow(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1, 1);
    }

    public static void generatebow(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus, double variability) {
        double speed = Math.random() * (speedmax - speedmin) + speedmin;
        List<String> temlore = new ArrayList<>();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }

        ItemMeta meta = item.getItemMeta();
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getRandomDamage(lvl, speed, damagebonus, variability));
        temlore.add(LoreCraftingStats.getSpeed(speed));
        temlore.add(LoreCraftingStats.getRandomCriticalChance(lvl, criticalbonus));
        if (Math.random() < 0.25) {
            temlore.add(LoreCraftingStats.getRandomCriticalDamage(lvl, criticaldamagebonus));
        }
        if (Math.random() < 0.20) {
            temlore.add(LoreCraftingStats.getPoison(lvl));
        }
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
    }

    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl) {
        generatesword(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1);
    }

    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus) {
        generatesword(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1, 0.75, true);
    }

    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus, double variability, boolean enchantable) {
        generatesword(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1, 0.75, 0, 0, true);
    }

    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus, double variability, double maxArmorPen, double maxStab, boolean enchantable) {
        double speed = Math.random() * (speedmax - speedmin) + speedmin;
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            lore = item.getItemMeta().getLore();
        }
        lore.add(LoreCraftingStats.getLvL(lvl)); // 1
        lore.add(LoreCraftingStats.getRandomDamage(lvl, speed, damagebonus, variability)); // 2
        lore.add(LoreCraftingStats.getRandomCriticalChance(lvl, criticalbonus)); // 4
        if (Math.random() < 0.25) {
            lore.add(LoreCraftingStats.getRandomCriticalDamage(lvl, criticaldamagebonus)); // 5
        }
        if (maxArmorPen > 0) {
            lore.add(LoreCraftingStats.getArmourPen(maxArmorPen));
        }
        if (maxStab > 0) {
            lore.add(LoreCraftingStats.getBackstab(maxStab));
        }
        if (Math.random() < 0.20) {
            lore.add(LoreCraftingStats.getPoison(lvl)); // 6
        }
        if (enchantable) {
            addEnchantSlots(lore, lvl);
        }
        lore.add(""); // 12
        lore.add(LoreCraftingStats.getSpeed(speed)); // 13
        lore.add(LoreCraftingStats.getDurability(lvl, materiallvl)); // 14
        item.getItemMeta().setLore(lore);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
    }

    public static void generateMagicWeapon(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double variability) {
        generateMagicWeapon(item, player, lvl, speedmax, speedmin, materiallvl, damagebonus, 1, 1, 1, 1, 1, variability, true);
    }

    public static void generateMagicWeapon(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double magicPowerB, double manaB, double manaRegenB, double CDRB, double magicPenB, double variability, boolean enchantable) {
        double speed = Math.random() * (speedmax - speedmin) + speedmin;
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            lore = item.getItemMeta().getLore();
        }
        lore.add(LoreCraftingStats.getLvL(lvl));
        lore.add(LoreCraftingStats.getRandomDamage(lvl, speed, damagebonus, variability));
        lore.add(LoreCraftingStats.getMagicPower(lvl, 0.5, variability));
        lore.add(LoreCraftingStats.getMagicPen(lvl, 1, 0.25));
        //lore.add(LoreCraftingStats.getManaBonus(lvl, 1, 0.5));
        lore.add(LoreCraftingStats.getManaRegenBonus(lvl, 0.5, 0.5));
        lore.add(LoreCraftingStats.getCDR(lvl, 0.5, 0.25));
        if (enchantable) {
            addEnchantSlots(lore, lvl);
        }
        lore.add("");
        lore.add(LoreCraftingStats.getSpeed(speed));
        lore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(lore);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
    }

    public static void addEnchantSlots(List<String> lore, int lvl) {
        if (lvl >= 30) {
            lore.add(""); // 7
            lore.add(plugin.rep(messages.getString("Lores.Enchants.Header"))); // 8
            lore.add(plugin.rep(messages.getString("Lores.Enchants.Empty"))); // 9 Enchant
        }
        if (lvl >= 60) {
            lore.add(plugin.rep(messages.getString("Lores.Enchants.Empty"))); // 10 Enchant
        }
        if (lvl >= 90) {
            lore.add(plugin.rep(messages.getString("Lores.Enchants.Empty"))); // 11 Enchant
        }
    }

    public static void generataxe(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, boolean enchantable) {
        double speed = Math.random() * (speedmax - speedmin) + speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getRandomDamage(lvl, speed));
        temlore.add(LoreCraftingStats.getSpeed(speed));
        temlore.add(LoreCraftingStats.getRandomCriticalChance(lvl));
        if (Math.random() < 0.25) {
            temlore.add(LoreCraftingStats.getRandomCriticalDamage(lvl));
        }
        if (Math.random() < 0.20) {
            temlore.add(LoreCraftingStats.getPoison(lvl));
        }
        if (speed >= 2) {
            temlore.add(LoreCraftingStats.getDestroy());
        }
        if (enchantable) {
            addEnchantSlots(temlore, lvl);
        }
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generateArmor(ItemStack item, Player player, int lvl, boolean enchantable) {

        if (item.hasItemMeta()
                && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().contains("§5")) {
            generateMagicArmor(item, player, lvl, enchantable);
        } else {
            List<String> temlore = new ArrayList<>();
            ItemMeta meta = item.getItemMeta();
            if (item.getItemMeta().hasLore()) {
                temlore = item.getItemMeta().getLore();
            }
            temlore.add(LoreCraftingStats.getLvL(lvl));
            temlore.add(LoreCraftingStats.getArmour(lvl, item, 1));
            temlore.add(LoreCraftingStats.getMagicArmour(lvl, item, 0.5));
            temlore.add(LoreCraftingStats.getArmorDodge(lvl, item));
            temlore.add(LoreCraftingStats.getMSpeed(item));
            if (enchantable) {
                addEnchantSlots(temlore, lvl);
            }
            temlore.add("");
            temlore.add(LoreCraftingStats.getArmorDurability(lvl, item));
            item.getItemMeta().setLore(temlore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.setUnbreakable(true);
            meta.setLore(temlore);
            item.setItemMeta(meta);
        }
    }

    public static void generateMagicArmor(ItemStack item, Player player, int lvl, boolean enchantable) {
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getArmour(lvl, item, 0.5));
        temlore.add(LoreCraftingStats.getMagicArmour(lvl, item, 1));
        temlore.add(LoreCraftingStats.getArmorDodge(lvl, item));
        temlore.add(LoreCraftingStats.getMagicPower(lvl, 0.125, 1));
        //lore.add(LoreCraftingStats.getMagicPen(lvl, 1, 0.25));
        temlore.add(LoreCraftingStats.getManaBonus(lvl, 0.25, 0.5));
        temlore.add(LoreCraftingStats.getManaRegenBonus(lvl, 0.125, 0.5));
        temlore.add(LoreCraftingStats.getCDR(lvl, 0.125, 0.25));
        temlore.add(LoreCraftingStats.getMSpeed(item));
        if (enchantable) {
            addEnchantSlots(temlore, lvl);
        }
        temlore.add("");
        temlore.add(LoreCraftingStats.getArmorDurability(lvl, item));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generatescudo(ItemStack item, Player player, int lvl, int materiallvl, boolean enchantable) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getBlock(lvl));
        temlore.add(LoreCraftingStats.getMSpeed(item));
        if (enchantable) {
            addEnchantSlots(temlore, lvl);
        }
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generatetool(ItemStack item, Player player, int lvl, boolean enchantable) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        if (enchantable) {
            addEnchantSlots(temlore, lvl);
        }
        temlore.add("");
        temlore.add(LoreCraftingStats.getToolDurability(lvl, item));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generateothertools(ItemStack item, Player player, int lvl, int materiallvl, boolean enchantable) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        if (enchantable) {
            addEnchantSlots(temlore, lvl);
        }
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    /*public static void AddVoidbound(ItemStack item) {
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(0, LoreCraftingStats.getVoidbound());
        item.getItemMeta().setLore(temlore);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }*/
 /*
    public static void AddCustomEnchant(ItemStack item, String enchant) {
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(0, ChatColor.GRAY + enchant);
        item.getItemMeta().setLore(temlore);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }
     */
}
