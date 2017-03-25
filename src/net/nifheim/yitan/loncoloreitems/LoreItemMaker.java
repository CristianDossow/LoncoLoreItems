package net.nifheim.yitan.loncoloreitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.nifheim.yitan.loncoloreitems.LoreCraftingStats;;

public class LoreItemMaker {
	
	public String unknownItem = EspecialAtributes.unknownItem;
	public String languageRegex = "[^A-Za-z������������_]";
	
	static public ItemStack AddItemLore(ItemStack item ,Player player){
		double lvl = player.getLevel();
		lvl = Math.random()*(lvl-lvl/2)+lvl/2;
		return AddItemLore(item,player, (int)lvl);
	}
	static public ItemStack ClearAndAddItemLore(ItemStack item ,Player player){
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		meta.setLore(temlore);
		item.setItemMeta(meta);
		return AddItemLore(item,player);
	}
	static public ItemStack ClearAndAddItemLore(ItemStack item ,Player player, int lvl){
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		meta.setLore(temlore);
		item.setItemMeta(meta);
		return AddItemLore(item,player,lvl);
	}
	
	static public ItemStack AddItemLore(ItemStack item ,Player player, int lvl){
		if(item.getType().equals(Material.BOW)){
			if(item.getDurability()==2){
				generatebow(item,player,lvl,6,4,275); //balletas
			}
			else if(item.getDurability()==1){
				generatebow(item,player,lvl,4,3,385,1.1,1.1,1.1); //arco astral
			}
			else{
				generatebow(item,player,lvl,4,2,385);
			}
		}else if(item.getType().equals(Material.WOOD_SWORD)){
			generatesword(item,player,lvl,1,2,60);
		}else if(item.getType().equals(Material.STONE_SWORD)){
			generatesword(item,player,lvl,1,2,140);
		}else if(item.getType().equals(Material.GOLD_SWORD)){
			generatesword(item,player,lvl,1,2,40);
		}else if(item.getType().equals(Material.IRON_SWORD)){
			generatesword(item,player,lvl,1,2,250);
		}else if(item.getType().equals(Material.DIAMOND_SWORD)){
			if(item.getDurability()==27){ //espada caballero
				generatesword(item,player,lvl,1,2.5,600,1.1,1,1); 
			}
			else if(item.getDurability()==43){ //maza de guerra
				generatesword(item,player,lvl,3,5,400);  
			}
			else if(item.getDurability()==38){ //guada�a
				generatesword(item,player,lvl,3,4,1300,1,1.2,1);  
			}
			else if(item.getDurability()==25){ //cimitarra
				generatesword(item,player,lvl,1.5,2.2,1600,1,1,1.2);  
			}
			else{
				generatesword(item,player,lvl,1,2,1600);
			}
			
		}else if(item.getType().equals(Material.WOOD_AXE)){
			generataxe(item,player,lvl,1.5,3,60);
		}else if(item.getType().equals(Material.STONE_AXE)){
			generataxe(item,player,lvl,1.5,3,140);
		}else if(item.getType().equals(Material.GOLD_AXE)){
			generataxe(item,player,lvl,1.5,3,40);
		}else if(item.getType().equals(Material.IRON_AXE)){
			generataxe(item,player,lvl,1.5,3,250);
		}else if(item.getType().equals(Material.DIAMOND_AXE)){
			generataxe(item,player,lvl,2,3,1600);
		}else if(item.getType().toString().contains("HELMET")){
			generatarmor(item,player,lvl);
		}else if(item.getType().toString().contains("CHESTPLATE")){
			generatarmor(item,player,lvl);
		}else if(item.getType().toString().contains("LEGGINGS")){
			generatarmor(item,player,lvl);
		}else if(item.getType().toString().contains("BOOTS")){
			generatarmor(item,player,lvl);
		}else if(item.getType().toString().contains("PICKAXE")){
			generatetool(item,player,lvl);
		}else if(item.getType().toString().contains("HOE")){
			if(item.getDurability()==0){
			generatetool(item,player,lvl);
			}
		}else if(item.getType().toString().contains("SPADE")){ 
		    generatetool(item,player,lvl);
		}else if(item.getType().equals(Material.FISHING_ROD)){
			generateothertools(item,player,lvl,65);
		}else if(item.getType().equals(Material.FLINT_AND_STEEL)){
			generateothertools(item,player,lvl,65);
		}else if(item.getType().equals(Material.CARROT_STICK)){
			generateothertools(item,player,lvl,26);
		}else if(item.getType().equals(Material.SHEARS)){
			generateothertools(item,player,lvl,238);
		}else if(item.getType().equals(Material.SHIELD)){
			generatescudo(item,player,lvl,337);
		}
		
		return item;
	}
	
	public static void generatebow(ItemStack item ,Player player, int lvl , double speedmax, double speedmin,int materiallvl){
		generatebow(item,player,lvl,speedmax,speedmin,materiallvl,1,1,1);
	}
	
	public static void generatebow(ItemStack item ,Player player, int lvl , double speedmax, double speedmin,int materiallvl, double damagebonus,double criticalbonus,double criticaldamagebonus){
		double speed = Math.random()*(speedmax-speedmin)+speedmin;
		
		List<String> temlore = new ArrayList<String>();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
		ItemMeta meta = item.getItemMeta();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add(LoreCraftingStats.getRandomDamage(lvl,speed,damagebonus));
    	temlore.add(LoreCraftingStats.getSpeed(speed));
    	temlore.add(LoreCraftingStats.getRandomCriticalChance(lvl,criticalbonus));
    	if(Math.random()<0.25)
    	temlore.add(LoreCraftingStats.getRandomCriticalDamage(lvl,criticaldamagebonus));
    	if(Math.random()<0.20)
    	temlore.add(LoreCraftingStats.getPoison(lvl));
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getDurability(lvl,materiallvl));
    	item.getItemMeta().setLore(temlore);
    	meta.setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	item.setItemMeta(meta); 
	}
	public static void generatesword(ItemStack item ,Player player, int lvl , double speedmax, double speedmin,int materiallvl){
		generatesword(item,player,lvl,speedmax,speedmin,materiallvl,1,1,1);
	}
	public static void generatesword(ItemStack item ,Player player, int lvl , double speedmax, double speedmin,int materiallvl, double damagebonus,double criticalbonus,double criticaldamagebonus){
		double speed = Math.random()*(speedmax-speedmin)+speedmin;
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add(LoreCraftingStats.getRandomDamage(lvl,speed,damagebonus));
    	temlore.add(LoreCraftingStats.getSpeed(speed));
    	temlore.add(LoreCraftingStats.getRandomCriticalChance(lvl,criticalbonus));
    	if(Math.random()<0.25)
    	temlore.add(LoreCraftingStats.getRandomCriticalDamage(lvl,criticaldamagebonus));
    	if(Math.random()<0.20)
    	temlore.add(LoreCraftingStats.getPoison(lvl));
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getDurability(lvl,materiallvl));
    	item.getItemMeta().setLore(temlore);
    	meta.setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	item.setItemMeta(meta); 
	}

	public static void generataxe(ItemStack item ,Player player ,int lvl , double speedmax, double speedmin,int materiallvl){
		double speed = Math.random()*(speedmax-speedmin)+speedmin;
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add(LoreCraftingStats.getRandomDamage(lvl,speed));
    	temlore.add(LoreCraftingStats.getSpeed(speed));
    	temlore.add(LoreCraftingStats.getRandomCriticalChance(lvl));
    	if(Math.random()<0.25)
    	temlore.add(LoreCraftingStats.getRandomCriticalDamage(lvl));
    	if(Math.random()<0.20)
    	temlore.add(LoreCraftingStats.getPoison(lvl));
    	if(speed >= 2)
    		temlore.add(LoreCraftingStats.getDestroy());
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getDurability(lvl,materiallvl));
    	item.getItemMeta().setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
	public static void generatarmor(ItemStack item ,Player player, int lvl ){
		//double speed = Math.random()*(speedmax-speedmin)+speedmin;
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add(LoreCraftingStats.getArmour(lvl,item));
    	temlore.add(LoreCraftingStats.getArmorDodge(lvl, item));
    	temlore.add(LoreCraftingStats.getMSpeed(item));
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getArmorDurability(lvl,item));
    	item.getItemMeta().setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
	public static void generatescudo(ItemStack item ,Player player,int lvl , int materiallvl){
		//double speed = Math.random()*(speedmax-speedmin)+speedmin;
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add(LoreCraftingStats.getBlock(lvl));
    	temlore.add(LoreCraftingStats.getMSpeed(item));
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getDurability(lvl,materiallvl));
    	item.getItemMeta().setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
	public static void generatetool(ItemStack item ,Player player,int lvl ){
		//double speed = Math.random()*(speedmax-speedmin)+speedmin;
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getToolDurability(lvl,item));
    	item.getItemMeta().setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
	public static void generateothertools(ItemStack item ,Player player,int lvl ,int materiallvl){
		//double speed = Math.random()*(speedmax-speedmin)+speedmin;
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(LoreCraftingStats.getLvL(lvl));
    	temlore.add("");
    	temlore.add(LoreCraftingStats.getDurability(lvl,materiallvl));
    	item.getItemMeta().setLore(temlore);
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    	meta.spigot().setUnbreakable(true);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
	
	public static void AddVoidbound(ItemStack item){
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(0,LoreCraftingStats.getVoidbound());
    	item.getItemMeta().setLore(temlore);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
	
	public static void AddCustomEnchant(ItemStack item, String enchant){
		List<String> temlore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if(item.getItemMeta().hasLore())
			temlore = item.getItemMeta().getLore();
    	temlore.add(0,ChatColor.GRAY+enchant);
    	item.getItemMeta().setLore(temlore);
    	meta.setLore(temlore);
    	item.setItemMeta(meta); 
	}
}
