package net.nifheim.yitan.itemlorestats;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerStats {
	Player player;
	UUID uuid;
	String name;
	//int lvl;  - se ocupará directo de la clase player
	double baseDamage; //base 1, podria ser modificable mediante nivel o clase
	double minDamage;
	double maxDamage;
	double weaponSpeed;
	double armor;
	double percentArmor;
	double dodge; //maximo 80%, activable 1 vez cada 2 seg, no daña durabilidad armadura
	double block; //maximo 50%, activable 1 vez cada 2 seg + lentiud del receptor, daña solo durabilidad del escudo
	double critChance; //base según arma
	double critDamage; //el base es 50% + bonos del set
	double healthCurrent;
	double healthMax;
	double healthRegen; //no estudiado
	double lifeSteal;  //sin ocupar
	double reflect; //no estudiado y sin ocupar
	double fire; //sin ocupar
	double ice; //no estudiado y sin ocupar
	double poison; //cuando es un mob inmune a veneno, se reemplaza por wither
	double wither; //sin ocupar
	double harming; //no estudiado y sin ocupar
	double blind; //no estudiado y sin ocupar
	double XPMultiplier; //??
	double movementSpeed;
	
	// stats por programar --------------------
	
	double manaMax;
	double manaCurrent;
	double manaRegen;
	double spellCooldownReduction; //maximo 50%, reduccion tiempo entre lanzamientos
	double magicPower; //la aletoriedad del daño dependerá del echizo
	double magicArmor;  
	double magicPercentArmor; 
	double armorPen; // penetración de armadura
	double magicArmorPen; // penetración de armadura mágica
	double stab; // probabilidad de apuñalar por detras, stat para dagas
	double stabDamage; //multiplicador del stab
	double Luck; //quien sabe :v
	
	
	public PlayerStats(Player player) {
		super();
		this.player=player;
		this.uuid = player.getUniqueId();
		this.name = player.getName();
		this.manaCurrent=0;
	}
	public void UpdateAll() {
		UpdateDamage();
		UpdateWeaponSpeed();
		UpdateArmor();
		UpdatePercentArmor();
		UpdateDodge();
		UpdateBlock();
		UpdateCritChance();
		UpdateCritDamage();
		UpdatePoison();
		UpdateManaMax();
		UpdateManaRegen();
		UpdateMagicPower();
		UpdateCdReduction();
	}
	public void UpdateDamage() {
		this.minDamage = PlayerStatsFormules.getDamageGearStat(player)[0];
		this.maxDamage = PlayerStatsFormules.getDamageGearStat(player)[1];
	}
	public void UpdateWeaponSpeed() {
		this.weaponSpeed = PlayerStatsFormules.getWeaponSpeedStat(player);
	}
	public void UpdateArmor() {
		this.armor = PlayerStatsFormules.getArmorStat(player);
	}
	public void UpdatePercentArmor() {
		this.percentArmor = PlayerStatsFormules.getPercentArmorStat(player,armor);
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
		if(manaMax>manaCurrent)
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
		player.sendMessage("Dodge: "+df.format(dodge*100)+"%");
		player.sendMessage("Block: "+df.format(block*100)+"%");
		player.sendMessage("Crit. Chance: "+df.format(critChance*100)+"%");
		player.sendMessage("Crit. Damage: "+df.format(critDamage*100)+"%");
		player.sendMessage("Poison: "+df.format(poison*100)+"%");
		player.sendMessage("Mana Regen: "+df.format(manaRegen));
		player.sendMessage("CD Reduction: "+df.format(spellCooldownReduction*100)+"%");
	}
	public void ShowStats() {
		ShowStats(player);
	}

}
