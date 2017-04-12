package net.nifheim.yitan.itemlorestats;

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
	int armor;
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
	int magicArmor;  
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
	}
	public void UpdateDamage() {
		this.minDamage = PlayerStatsFormules.getDamageGearStat(player)[0];
		this.maxDamage = PlayerStatsFormules.getDamageGearStat(player)[1];
		
	}
	public void ShowStats(Player player) {
		player.sendMessage("Damage: "+minDamage+" - "+maxDamage);
	}

}
