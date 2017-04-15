package net.nifheim.yitan.itemlorestats;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerStats {
	public Player player;
	public UUID uuid;
	public String name;
	//int lvl;  - se ocupará directo de la clase player
	public double baseDamage; //base 1, podria ser modificable mediante nivel o clase
	public double minDamage;
	public double maxDamage;
	public double weaponSpeed;
	public double armor;
	public double percentArmor;
	public double dodge; //maximo 80%, activable 1 vez cada 2 seg, no daña durabilidad armadura
	public double block; //maximo 50%, activable 1 vez cada 2 seg + lentiud del receptor, daña solo durabilidad del escudo
	public double critChance; //base según arma
	public double critDamage; //el base es 50% + bonos del set
	public double healthCurrent;
	public double healthMax;
	public double healthRegen; //no estudiado
	public double lifeSteal;  //sin ocupar
	public double reflect; //no estudiado y sin ocupar
	public double fire; //sin ocupar
	public double ice; //no estudiado y sin ocupar
	public double poison; //cuando es un mob inmune a veneno, se reemplaza por wither
	public double wither; //sin ocupar
	public double harming; //no estudiado y sin ocupar
	public double blind; //no estudiado y sin ocupar
	public double XPMultiplier; //??
	public double movementSpeed;
	
	// stats por programar --------------------
	
	public double manaMax;
	public double manaCurrent;
	public double manaRegen;
	public double spellCooldownReduction; //maximo 50%, reduccion tiempo entre lanzamientos
	public double magicPower; //la aletoriedad del daño dependerá del echizo
	public double magicArmor;  
	public double magicPercentArmor; 
	public double armorPen; // penetración de armadura
	public double magicArmorPen; // penetración de armadura mágica
	public double stab; // probabilidad de apuñalar por detras, stat para dagas
	public double stabDamage; //multiplicador del stab
	public double Luck; //quien sabe :v
	public long lastSpellCast;
	public long spellCastWait;
	public long lastMessage;
	
	
	public PlayerStats(Player player) {
		super();
		this.player=player;
		this.uuid = player.getUniqueId();
		this.name = player.getName();
		this.manaCurrent=0;
		this.lastSpellCast=System.currentTimeMillis();
		this.spellCastWait=System.currentTimeMillis();
		this.lastMessage=System.currentTimeMillis();
	}
	public void UpdateAll() {
		UpdateDamage();
		UpdateWeaponSpeed();
		UpdateArmor();
		UpdateMagicArmor();
		UpdatePercentArmor();
		UpdateMagicPercentArmor();
		UpdateDodge();
		UpdateBlock();
		UpdateCritChance();
		UpdateCritDamage();
		UpdatePoison();
		UpdateManaMax();
		UpdateManaRegen();
		UpdateMagicPower();
		UpdateCdReduction();
		UpdateMagicArmorPen();
	}
	public void UpdateDefence() {
		UpdateArmor();
		UpdateMagicArmor();
		UpdatePercentArmor();
		UpdateMagicPercentArmor();
		UpdateDodge();
		UpdateBlock();
	}
	
	public void UpdateDamage() {
		this.minDamage = PlayerStatsFormules.getDamageGearStat(player)[0];
		this.maxDamage = PlayerStatsFormules.getDamageGearStat(player)[1];
	}
	public void UpdateMagicArmorPen() {
		this.magicArmorPen = PlayerStatsFormules.getMagicArmorPenStat(player);
	}
	public void UpdateWeaponSpeed() {
		this.weaponSpeed = PlayerStatsFormules.getWeaponSpeedStat(player);
	}
	public void UpdateArmor() {
		this.armor = PlayerStatsFormules.getArmorStat(player);
	}
	public void UpdateMagicArmor() {
		this.magicArmor = PlayerStatsFormules.getMagicArmorStat(player);
	}
	public void UpdatePercentArmor() {
		this.percentArmor = PlayerStatsFormules.getPercentArmorStat(player,armor);
	}
	public void UpdateMagicPercentArmor() {
		this.magicPercentArmor = PlayerStatsFormules.getPercentArmorStat(player,magicArmor);
	}
	public void UpdateDodge() {
		this.dodge = PlayerStatsFormules.getDodgeStat(player);
	}
	public void UpdateBlock() {
		this.block = PlayerStatsFormules.getBlockStat(player);
	}
	public void UpdateCritChance() {
		this.critChance = PlayerStatsFormules.getCritChanceStat(player);
	}
	public void UpdateCritDamage() {
		this.critDamage = PlayerStatsFormules.getCritDamageStat(player);
	}
	public void UpdatePoison() {
		this.poison = PlayerStatsFormules.getPosionStat(player);
	}
	public void UpdateManaMax() {
		this.manaMax = PlayerStatsFormules.getManaMaxStat(player);
	}
	public void UpdateManaRegen() {
		this.manaRegen = PlayerStatsFormules.getManaRegenStat(player);
	}
	public void UpdateMagicPower() {
		this.magicPower = PlayerStatsFormules.getMagicPowerStat(player);
	}
	public void UpdateCdReduction() {
		this.spellCooldownReduction = PlayerStatsFormules.getCdReductionStat(player);
	}
	public void ManaRegen() {
		if(manaCurrent+manaRegen>manaMax)
			this.manaCurrent =this.manaMax;
		else
			this.manaCurrent =this.manaCurrent + this.manaRegen;
	}
	public void ShowStats(Player player) {
		DecimalFormat df = new DecimalFormat("#.#");
		double dp = (minDamage+maxDamage)/2;
		double dps = dp/weaponSpeed;
		player.sendMessage("Damage: "+df.format(minDamage)+" - "+df.format(maxDamage)+" DP("+df.format(dp)+")");
		player.sendMessage("Weapon Speed: "+weaponSpeed+" DPS("+df.format(dps)+")");
		player.sendMessage("Magic Power: "+df.format(magicPower)+"");
		player.sendMessage("Mana: "+df.format(manaCurrent)+"/"+df.format(manaMax));
		player.sendMessage("Armor: "+armor+" ("+df.format(percentArmor*100)+"%)");
		player.sendMessage("Magic Armor: "+magicArmor+" ("+df.format(magicPercentArmor*100)+"%)");
		player.sendMessage("Dodge: "+df.format(dodge*100)+"%");
		player.sendMessage("Block: "+df.format(block*100)+"%");
		player.sendMessage("Crit. Chance: "+df.format(critChance*100)+"%");
		player.sendMessage("Crit. Damage: "+df.format(critDamage*100)+"%");
		player.sendMessage("Poison: "+df.format(poison*100)+"%");
		player.sendMessage("Mana Regen: "+df.format(manaRegen));
		player.sendMessage("CD Reduction: "+df.format(spellCooldownReduction*100)+"%");
		player.sendMessage("Magic Pen.: "+df.format(magicArmorPen*100)+"%");
	}
	public void ShowStats() {
		ShowStats(player);
	}

}
