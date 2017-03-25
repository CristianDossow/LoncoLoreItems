package net.nifheim.yitan.loncoloreitems;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.nifheim.yitan.itemlorestats.Main;

public class LoreCraftingStats {

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
	static String weaponspeed = "V Ataque";
	static String durability =  Main.plugin.getConfig().getString("bonusStats.durability.name");
	static String lvlname =  Main.plugin.getConfig().getString("bonusStats.xpLevel.name");
	static String destroy = EspecialAtributes.destroyname;
	static String voidbound = EspecialAtributes.voidbound;
	//static String durabilitycolor = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.warningColours.above75%");

	static DecimalFormat df = new DecimalFormat("#.#");
	
	static final double ArmorGrowthrate = -0.26;
	static final double Armorxlvl = 4;
	static final double Armorxbase = 100;
	

	public LoreCraftingStats() {
		super();
		Locale.setDefault(Locale.ROOT);
		df.getDecimalFormatSymbols().setDecimalSeparator('.');
		
		
	}
	
	
	static public String getLvL(int lvl){
		return (ChatColor.DARK_GREEN+lvlname+": "+ChatColor.GREEN+lvl);
	}
	
	static public String getArmour(double lvl, ItemStack item){
		
		double maxstrength = Armorxbase + (Armorxlvl * lvl);

		if(item.getType().toString().contains("_HELMET"))
			maxstrength=maxstrength*0.2;
		if(item.getType().toString().contains("_CHESTPLATE"))
			maxstrength=maxstrength*0.34;
		if(item.getType().toString().contains("_LEGGINGS"))
			maxstrength=maxstrength*0.29;
		if(item.getType().toString().contains("_BOOTS"))
			maxstrength=maxstrength*0.17;
		if(item.getType().toString().contains("IRON_"))
			maxstrength=maxstrength*0.9;
		if(item.getType().toString().contains("CHAINMAIL_"))
			maxstrength=maxstrength*0.8;
		if(item.getType().toString().contains("GOLD_"))
			maxstrength=maxstrength*0.7;
		if(item.getType().toString().contains("LEATHER_"))
			maxstrength=maxstrength*0.6;
		
		double minstrength = maxstrength*0.6;
		double armourtotal = Math.random()*(maxstrength-minstrength)+minstrength;
		return ChatColor.DARK_AQUA+armour+": "+ChatColor.AQUA+(int)armourtotal;
	}
	
	static public String getRandomDamage(int lvl,double speed ){
	return getRandomDamage(lvl,speed,1);
	}

	static public String getRandomDamage(int lvl,double speed ,double bonus){
		Random r = new Random();
		double mindamage=0;
		double maxdamage=0;
		
		//double dmgxlvl=0.40;
		double dmgxlvl = speed/5;
		double rmarginxlvl=dmgxlvl/2;
		
		double basedmg=speed*2;
		double basermddmg=speed/2;
		
		double dmg=basedmg+(dmgxlvl*lvl);
		double rmargin=basermddmg+(rmarginxlvl*lvl);
		
		double damage1 = dmg + ( rmargin * 2 * r.nextDouble() - rmargin );
		double damage2 = dmg + ( rmargin * 2 * r.nextDouble() - rmargin );
		
		if(damage1 == damage2){
			mindamage=damage1-1;
			maxdamage=damage2;
		}
		if(damage1 < damage2){
			mindamage=damage1;
			maxdamage=damage2;
		}
		if(damage1 > damage2){
			mindamage=damage2;
			maxdamage=damage1;
		}
		maxdamage= bonus*maxdamage;
		mindamage= bonus*mindamage;
		return ChatColor.DARK_AQUA+damage+": +"+ChatColor.AQUA+df.format(mindamage)+"-"+df.format(maxdamage);
	}
	static public String getRandomCriticalChance(int lvl){
		return getRandomCriticalChance(lvl,1);
	}
	
	static public String getRandomCriticalChance(int lvl, double bonus){

		double criticalbase=5;
		double criticalxlvl = 0.2;
		double criticalmax=criticalbase+(criticalxlvl*(double)lvl);
		
		double critical = Math.random()*(criticalmax-1)+1;
		critical = critical * bonus;
		
		return ChatColor.DARK_AQUA+critChance+": "+ChatColor.AQUA+df.format(critical)+"%";
	}
	
	static public String getRandomCriticalDamage(int lvl){
		return getRandomCriticalDamage(lvl,1);
	}
	static public String getRandomCriticalDamage(int lvl, double bonus){

		double criticalbase=5;
		double criticalxlvl = 0.5;
		double criticalmax=criticalbase+(criticalxlvl*(double)lvl);
		
		double critical = Math.random()*(criticalmax-1)+1;
		critical = critical * bonus;
		return ChatColor.DARK_AQUA+critDamage+": +"+ChatColor.AQUA+df.format(critical)+"%";
	}
	
	static public String getSpeed(double speed){
		return ChatColor.DARK_AQUA+weaponspeed+": "+ChatColor.AQUA+df.format(speed);
	}
	static public String getPoison(double lvl){
		double poisonchance=3;
		double poisonchancexlvl = 0.2;
		double poisonchancemax=poisonchance+(poisonchancexlvl*(double)lvl);
		double poisonstats = Math.random()*(poisonchancemax-1)+1;
		return ChatColor.DARK_PURPLE+poison+": "+ChatColor.LIGHT_PURPLE+df.format(poisonstats)+"%";
	}
	static public String getDurability(int lvl, int materiallvl){
		double durabilitybase=materiallvl;
		double durabilityxlvl = durabilitybase*((double)lvl/100.0);
		double durabilitymax=durabilitybase+durabilityxlvl;
		double durabilityxmin = durabilitybase;
		double durabilityfinal = Math.random()*(durabilitymax-durabilityxmin)+durabilityxmin;
		return ChatColor.DARK_GRAY+durability+": "+ChatColor.DARK_GRAY+df.format((int)durabilityfinal)+"/"+df.format((int)durabilityfinal);
	}
	
	static public String getMSpeed(ItemStack item){
		int speed = 0;
		if(item.getType().toString().contains("LEATHER"))
			speed = 0;
		if(item.getType().toString().contains("GOLD"))
			speed = -1;
		if(item.getType().toString().contains("CHAINMAIL"))
			speed = -2;
		if(item.getType().toString().contains("IRON"))
			speed = -3;
		if(item.getType().toString().contains("DIAMOND"))
			speed = -4;
		if(item.getType().toString().contains("SHIELD"))
			speed = -5;

		return ChatColor.DARK_AQUA+movementspeed+": "+ChatColor.AQUA+df.format(speed)+"%";
	}
	
	static public String getArmorDurability(int lvl, ItemStack item){
		int materiallvl = 5;
		if(item.getType().toString().contains("LEATHER"))
			materiallvl = 150;
		if(item.getType().toString().contains("GOLD"))
			materiallvl = 200;
		if(item.getType().toString().contains("CHAINMAIL"))
			materiallvl = 290;
		if(item.getType().toString().contains("IRON"))
			materiallvl = 330;
		if(item.getType().toString().contains("DIAMOND"))
			materiallvl = 470;
		
		double durabilitybase=materiallvl;
		double durabilityxlvl = durabilitybase*((double)lvl/100.0);
		double durabilitymax=durabilitybase+durabilityxlvl;
		double durabilityxmin = durabilitybase;
		double durabilityfinal = Math.random()*(durabilitymax-durabilityxmin)+durabilityxmin;
		
		return ChatColor.DARK_GRAY+durability+": "+ChatColor.DARK_GRAY+df.format((int)durabilityfinal)+"/"+df.format((int)durabilityfinal);
	}

	static public String getToolDurability(int lvl, ItemStack item){
		int materiallvl = 20;
		if(item.getType().toString().contains("WOOD"))
			materiallvl = 60;
		if(item.getType().toString().contains("STONE")) 
			materiallvl = 140;
		if(item.getType().toString().contains("GOLD"))
			materiallvl = 40;
		if(item.getType().toString().contains("IRON"))
			materiallvl = 250;
		if(item.getType().toString().contains("DIAMOND"))
			materiallvl = 1600;
		
		double durabilitybase=materiallvl;
		double durabilityxlvl = durabilitybase*((double)lvl/100.0);
		double durabilitymax=durabilitybase+durabilityxlvl;
		double durabilityxmin = durabilitybase;
		double durabilityfinal = Math.random()*(durabilitymax-durabilityxmin)+durabilityxmin;
		
		return ChatColor.DARK_GRAY+durability+": "+ChatColor.DARK_GRAY+df.format((int)durabilityfinal)+"/"+df.format((int)durabilityfinal);
	}
	static public String getBlock(double lvl){
		double base=10;
		double xlvl = 0.4;
		double max=base+(xlvl*(double)lvl);
		double min = base + (max - base)/2;
		double total = Math.random()*(max-base)+base;
		return ChatColor.DARK_AQUA+block+": "+ChatColor.AQUA+df.format(total)+"%";
	}
	static public String getArmorDodge(int lvl, ItemStack item){
		double dodgebase = 1;
		

		if(item.getType().toString().contains("LEATHER"))
			dodgebase = 8;
		if(item.getType().toString().contains("GOLD"))
			dodgebase = 6;
		if(item.getType().toString().contains("CHAINMAIL"))
			dodgebase = 5;
		if(item.getType().toString().contains("IRON"))
			dodgebase = 4;
		if(item.getType().toString().contains("DIAMOND"))
			dodgebase = 3;
		
		
		double dodgexlvl = dodgebase*0.02;
		double dodgemax=dodgebase+(dodgexlvl*(double)lvl);
		double dodgemin = dodgebase;
		dodgemin = dodgemin + (dodgemax - dodgemin)/2;
		double dodgefinal = Math.random()*(dodgemax-dodgemin)+dodgemin;
		
		return ChatColor.DARK_AQUA+dodge+": "+ChatColor.AQUA+df.format(dodgefinal)+"%";
		
		//return "";
	}
	static public String getDestroy(){
		return ChatColor.DARK_RED+destroy;
	}
	static public String getVoidbound(){
		return ChatColor.GRAY+voidbound;
	}
	
}
