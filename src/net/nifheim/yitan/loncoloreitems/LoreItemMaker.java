package net.nifheim.yitan.loncoloreitems;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.itemlorestats.Main;

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
    public String unknownItem = EspecialAtributes.unknownItem;
    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

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
                    generatebow(item, player, lvl, 4, 3, 385, 1.1, 1.1, 1.1); //arco astral
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
                case 27:
                    //espada caballero
                    generatesword(item, player, lvl, 1, 2.5, 600, 1.1, 1, 1);
                    break;
                case 43:
                    //maza de guerra
                    generatesword(item, player, lvl, 3, 5, 400);
                    break;
                case 38:
                    //guadaña
                    generatesword(item, player, lvl, 3, 4, 1300, 1, 1.2, 1);
                    break;
                case 25:
                    //cimitarra
                    generatesword(item, player, lvl, 1.5, 2.2, 1600, 1, 1, 1.2);
                    break;
                default:
                    generatesword(item, player, lvl, 1, 2, 1600);
                    break;
            }

        } else if (item.getType().equals(Material.WOOD_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 60);
        } else if (item.getType().equals(Material.STONE_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 140);
        } else if (item.getType().equals(Material.GOLD_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 40);
        } else if (item.getType().equals(Material.IRON_AXE)) {
            generataxe(item, player, lvl, 1.5, 3, 250);
        } else if (item.getType().equals(Material.DIAMOND_AXE)) {
            generataxe(item, player, lvl, 2, 3, 1600);
        } else if (item.getType().toString().contains("HELMET")) {
            generatarmor(item, player, lvl);
        } else if (item.getType().toString().contains("CHESTPLATE")) {
            generatarmor(item, player, lvl);
        } else if (item.getType().toString().contains("LEGGINGS")) {
            generatarmor(item, player, lvl);
        } else if (item.getType().toString().contains("BOOTS")) {
            generatarmor(item, player, lvl);
        } else if (item.getType().toString().contains("PICKAXE")) {
            generatetool(item, player, lvl);
        } else if (item.getType().toString().contains("HOE")) {
            if (item.getDurability() == 0) {
                generatetool(item, player, lvl);
            }
        } else if (item.getType().toString().contains("SPADE")) {
            generatetool(item, player, lvl);
        } else if (item.getType().equals(Material.FISHING_ROD)) {
            generateothertools(item, player, lvl, 65);
        } else if (item.getType().equals(Material.FLINT_AND_STEEL)) {
            generateothertools(item, player, lvl, 65);
        } else if (item.getType().equals(Material.CARROT_STICK)) {
            generateothertools(item, player, lvl, 26);
        } else if (item.getType().equals(Material.SHEARS)) {
            generateothertools(item, player, lvl, 238);
        } else if (item.getType().equals(Material.SHIELD)) {
            generatescudo(item, player, lvl, 337);
        }

        return item;
    }

    @Deprecated
    public static void generatebow(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl) {
        generatebow(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1);
    }

    public static void generatebow(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus) {
        double speed = Math.random() * (speedmax - speedmin) + speedmin;

        List<String> temlore = new ArrayList<>();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        ItemMeta meta = item.getItemMeta();
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getRandomDamage(lvl, speed, damagebonus));
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

    @Deprecated
    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl) {
        generatesword(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1);
    }

    @Deprecated
    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus) {
        generatesword(item, player, lvl, speedmax, speedmin, materiallvl, 1, 1, 1, true);
    }

    public static void generatesword(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl, double damagebonus, double criticalbonus, double criticaldamagebonus, boolean enchantable) {
        double speed = Math.random() * (speedmax - speedmin) + speedmin;
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            lore = item.getItemMeta().getLore();
        }
        lore.add(LoreCraftingStats.getLvL(lvl)); // 1
        lore.add(LoreCraftingStats.getRandomDamage(lvl, speed, damagebonus)); // 2
        lore.add(LoreCraftingStats.getRandomCriticalChance(lvl, criticalbonus)); // 4
        if (Math.random() < 0.25) {
            lore.add(LoreCraftingStats.getRandomCriticalDamage(lvl, criticaldamagebonus)); // 5
        }
        if (Math.random() < 0.20) {
            lore.add(LoreCraftingStats.getPoison(lvl)); // 6
        }
        if (enchantable) {
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

    public static void generataxe(ItemStack item, Player player, int lvl, double speedmax, double speedmin, int materiallvl) {
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
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generatarmor(ItemStack item, Player player, int lvl) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getArmour(lvl, item));
        temlore.add(LoreCraftingStats.getArmorDodge(lvl, item));
        temlore.add(LoreCraftingStats.getMSpeed(item));
        temlore.add("");
        temlore.add(LoreCraftingStats.getArmorDurability(lvl, item));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generatescudo(ItemStack item, Player player, int lvl, int materiallvl) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add(LoreCraftingStats.getBlock(lvl));
        temlore.add(LoreCraftingStats.getMSpeed(item));
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generatetool(ItemStack item, Player player, int lvl) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add("");
        temlore.add(LoreCraftingStats.getToolDurability(lvl, item));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void generateothertools(ItemStack item, Player player, int lvl, int materiallvl) {
        //double speed = Math.random()*(speedmax-speedmin)+speedmin;
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(LoreCraftingStats.getLvL(lvl));
        temlore.add("");
        temlore.add(LoreCraftingStats.getDurability(lvl, materiallvl));
        item.getItemMeta().setLore(temlore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

    public static void AddVoidbound(ItemStack item) {
        List<String> temlore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta().hasLore()) {
            temlore = item.getItemMeta().getLore();
        }
        temlore.add(0, LoreCraftingStats.getVoidbound());
        item.getItemMeta().setLore(temlore);
        meta.setLore(temlore);
        item.setItemMeta(meta);
    }

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
}
