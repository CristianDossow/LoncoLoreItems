package net.nifheim.yitan.loncoloreitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.nifheim.yitan.itemlorestats.Main;

public class ItemCategory {
	static private String languageRegex = "[^A-Za-z������������_]";
	static public String armortype = "Armadura";
	static public String tooltype = "Herramienta";
	static public String shieldtype = "Escudo";
	static public String allweapontype = "Arma";
	static public String swordtype = "Espada";
	static public String bowtype = "Arco";
	static public String anytype = "General";
	
	static public boolean IsArmor(ItemStack item){
		return Main.plugin.isArmour(item.getType());
	}
	
	static public boolean isTool(ItemStack item){
		return Main.plugin.isTool(item.getType());
	}
	
	static public boolean isShield(ItemStack item){
		if(item.getType() == Material.SHIELD)
			return true;
		return false;
	}
	static public boolean isAnyWeapon(ItemStack item){
		if(Main.plugin.isSword(item))
			return true;
		if(Main.plugin.isAxe(item))
			return true;
		if(item.getType() == Material.BOW)
			return true;
		return false;
	}
	static public boolean isSword(ItemStack item){
		if(Main.plugin.isSword(item))
			return true;
		if(Main.plugin.isAxe(item))
			return true;
		return false;
	}
	static public boolean isBow(ItemStack item){
		if(item.getType() == Material.BOW)
			return true;
		return false;
	}
	static public boolean isAnyType(ItemStack item){
		if(IsArmor(item) || isTool(item)|| isShield(item)||isAnyWeapon(item) )
			return true;
		return false;
	}
	
	static public boolean isValidToEnchantGiver(ItemStack enchantgiver,ItemStack item){
		String type = enchantgiver.getItemMeta().getLore().get(0);
		type = ChatColor.stripColor(type);
		type=type.toLowerCase().replaceAll(languageRegex, "");
		if(type.matches(armortype.toLowerCase().replaceAll(languageRegex, "")))
			return IsArmor(item);
		if(type.matches(tooltype.toLowerCase().replaceAll(languageRegex, "")))
			return isTool(item);
		if(type.matches(shieldtype.toLowerCase().replaceAll(languageRegex, "")))
			return isShield(item);
		if(type.matches(allweapontype.toLowerCase().replaceAll(languageRegex, "")))
			return isAnyWeapon(item);
		if(type.matches(swordtype.toLowerCase().replaceAll(languageRegex, "")))
			return isSword(item);
		if(type.matches(bowtype.toLowerCase().replaceAll(languageRegex, "")))
			return isBow(item);
		if(type.matches(anytype.toLowerCase().replaceAll(languageRegex, "")))
			return isAnyType(item);
		
		return false;
	}
	
}
