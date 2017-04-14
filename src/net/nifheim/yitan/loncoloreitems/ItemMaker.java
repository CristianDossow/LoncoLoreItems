package net.nifheim.yitan.loncoloreitems;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMaker {
	
	public static ItemStack EnchantScroll(String enchant, String Type){
		
		ItemStack scroll = new ItemStack(Material.PAPER, 1);
		ItemMeta im = scroll.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Pergamino Antiguo Encantado");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + Type);
		lore.add(ChatColor.GOLD + EspecialAtributes.enchantgiver);
		lore.add(ChatColor.GRAY + enchant);
		im.setLore(lore);
		scroll.setItemMeta(im);
		return scroll;
	}
	
	public static ItemStack RepairerStone(int poder){
		
		ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1);
		ItemMeta im = item.getItemMeta();
		im.setUnbreakable(true);
		im.setDisplayName(ChatColor.GOLD + "Piedra de Reparaci�n");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD +EspecialAtributes.repairer+" "+poder);
		lore.add(ChatColor.GRAY + "Piedra m�gica que puede reparar los");
		lore.add(ChatColor.GRAY + "da�os causados por el desgaste de");
		lore.add(ChatColor.GRAY + "cualquier objeto");
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		im.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(im);
		item.setDurability((short) 103);
		return item;
	}
	
}
