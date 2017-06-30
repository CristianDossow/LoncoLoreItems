package net.nifheim.yitan.loncoloreitems;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.nifheim.yitan.loncoloremagics.Spell;
import org.bukkit.configuration.file.FileConfiguration;

public class ItemMaker {

    private static final Main plugin = Main.getInstance();
    private static final FileConfiguration messages = plugin.getMessages();

    public static ItemStack EnchantScroll(String enchant, String Type, List<String> description) {

        ItemStack scroll = new ItemStack(Material.PAPER, 1);
        ItemMeta im = scroll.getItemMeta();
        im.setDisplayName(plugin.rep(messages.getString("Items.Enchant Scroll.Name")));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + Type);
        lore.add(ChatColor.GOLD + EspecialAtributes.enchantgiver);
        lore.add(ChatColor.GRAY + enchant);
        lore.add("");
        if (description != null) {
            for (String desc : description) {
                lore.add(ChatColor.DARK_AQUA + desc);
            }
        }
        im.setLore(lore);
        scroll.setItemMeta(im);
        return scroll;
    }

    public static ItemStack RepairerStone(int poder) {

        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName(plugin.rep(messages.getString("Items.Repairer Stone.Name")));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + EspecialAtributes.repairer + " " + poder);
        List<String> mlore = messages.getStringList("Items.Repairer Stone.Lore");
        mlore.stream().forEach((str) -> {
            lore.add(plugin.rep(str));
        });
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) 103);
        return item;
    }

    public static ItemStack itemLevelBoost(int poder) {

        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName(ChatColor.GOLD + "Piedra Reformadora");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + EspecialAtributes.itemLevelBoost + " " + poder);
        lore.add(ChatColor.DARK_AQUA + "Piedra mágica capaz de alterar la");
        lore.add(ChatColor.DARK_AQUA + "composicion elemental de un objeto");
        lore.add(ChatColor.DARK_AQUA + "y aumentar su nivel");
        lore.add(ChatColor.RED + "Los Encantamientos se perderán y el");
        lore.add(ChatColor.RED + "resultado es totalmente impredecible");
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) 205);
        return item;
    }

    public static ItemStack SpellBook(Spell spell) {

        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName(ChatColor.GOLD + plugin.rep(messages.getString("Items.Spell Book.Name") + ": " + spell.colorName + spell.name));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + spell.name);
        spell.lore.stream().forEach((loreline) -> {
            lore.add(ChatColor.AQUA + loreline);
        });
        LoreItemMaker.addEnchantSlots(lore, 30);
        im.setLore(lore);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) 98);
        return item;
    }

    public static ItemStack Weapon(int data) {

        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName("Weapon Data: " + data);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) data);
        return item;
    }

    public static ItemStack Bow(int data) {

        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName("Bow Data: " + data);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) data);
        return item;
    }

    public static ItemStack Tool(int data) {

        ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName("Tool Data: " + data);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) data);
        return item;
    }

    public static ItemStack Shield(int data) {

        ItemStack item = new ItemStack(Material.SHIELD, 1);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.setDisplayName("Shield Data: " + data);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(im);
        item.setDurability((short) data);
        return item;
    }
}
