package net.nifheim.yitan.loncoloreitems;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import net.nifheim.yitan.itemlorestats.Main;

public class LoreUtils {
	
	static String armour =Main.plugin.getConfig().getString("primaryStats.armour.name");
	static String dodge =Main.plugin.getConfig().getString("secondaryStats.dodge.name");
	static String block = Main.plugin.getConfig().getString("secondaryStats.block.name");
	static String critChance = Main.plugin.getConfig().getString("secondaryStats.critChance.name");
	static String critDamage = Main.plugin.getConfig().getString("secondaryStats.critDamage.name");
	static String damage = Main.plugin.getConfig().getString("primaryStats.damage.name");
	static String health = Main.plugin.getConfig().getString("primaryStats.health.name");
	static String healthRegen = Main.plugin.getConfig().getString("primaryStats.healthRegen.name");
	static String lifeSteal =Main.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
	static String reflect = Main.plugin.getConfig().getString("secondaryStats.reflect.name");
	static String fire =  Main.plugin.getConfig().getString("secondaryStats.fire.name");
	static String ice =  Main.plugin.getConfig().getString("secondaryStats.ice.name");
	static String poison =  Main.plugin.getConfig().getString("secondaryStats.poison.name");
	static String wither =  Main.plugin.getConfig().getString("secondaryStats.wither.name");
	static String harming =  Main.plugin.getConfig().getString("secondaryStats.harming.name");
	static String blind =  Main.plugin.getConfig().getString("secondaryStats.blind.name");
	static String xpmultiplier =  Main.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
	static String movementspeed =  Main.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
	static String weaponspeed = "VAtaque";
	static String durability =  Main.plugin.getConfig().getString("bonusStats.durability.name");
	static String lvl =  Main.plugin.getConfig().getString("bonusStats.xpLevel.name");
	static String destroy = EspecialAtributes.destroyname;
	static public String languageRegex = "[^A-Za-z������������_]";

	static public boolean IsWeapon(ItemStack item){
		if(Main.plugin.isTool(item.getType()))
		{
			if(item.getItemMeta().hasLore()){
				boolean haveSpeed =false;
				boolean haveDamage = false;
				List<String> lore = item.getItemMeta().getLore();
				for(String loreline : lore)
				{
					loreline = ChatColor.stripColor(loreline.toString());
					loreline=loreline.toLowerCase();
					if(loreline.replaceAll(languageRegex, "").matches(weaponspeed.replaceAll(languageRegex, "").toLowerCase())){
						haveSpeed = true;
					}
					if(loreline.replaceAll(languageRegex, "").matches(damage.replaceAll(languageRegex, "").toLowerCase())){
						haveDamage = true;
					}
				}
				if(haveSpeed && haveDamage)
					return true;
			}

		  }

		return false;
	}
	
	static public double getWeaponSpeed(ItemStack item){
		double thisweaponspeed = 1;
		if(item !=null && item.hasItemMeta() && item.getItemMeta().hasLore()){
			List<String> lore = item.getItemMeta().getLore();
			for(String loreline : lore){
				loreline = ChatColor.stripColor(loreline.toString());
				loreline=loreline.toLowerCase();
				if (loreline.replaceAll(languageRegex, "").matches(weaponspeed.toLowerCase())){
					thisweaponspeed= Double.parseDouble(loreline.replaceAll("[^0-9.+-]", ""));
				}
			}
		}
		return thisweaponspeed;
	}
	
}
