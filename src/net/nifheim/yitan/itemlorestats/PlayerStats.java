package net.nifheim.yitan.itemlorestats;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.nifheim.yitan.itemlorestats.Util.Util_Format;

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
	double magicDamage; //la aletoriedad del daño dependerá del echizo
	double magicArmor;  
	double magicPercentArmor; 
	double armorPen; // penetración de armadura
	double magicArmorPen; // penetración de armadura mágica
	double stab; // probabilidad de apuñalar por detras, stat para dagas
	double stabDamage; //multiplicador del stab
	
	
	public PlayerStats(Player player) {
		super();
		this.player=player;
		this.uuid = player.getUniqueId();
		this.name = player.getName();
	}
	public void UpdateAll() {
		UpdateDamage();
		UpdateWeaponSpeed();
		UpdateArmor();
		UpdatePercentArmor();
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
	
	public void ShowStats(Player player) {
		DecimalFormat df = new DecimalFormat("#.#");
		player.sendMessage("Damage: "+df.format(minDamage)+" - "+df.format(maxDamage));
		player.sendMessage("Weapon Speed: "+weaponSpeed);
		Util_Format util_Format = new Util_Format();
		double percentArmor = util_Format.format(this.percentArmor);
		player.sendMessage("Armor: "+armor+" ("+percentArmor+"%)");
	}
	public void ShowStats() {
		ShowStats(player);
	}

}
