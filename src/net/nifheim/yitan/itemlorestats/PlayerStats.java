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
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.manaCurrent = 0;
        this.lastSpellCast = System.currentTimeMillis();
        this.spellCastWait = System.currentTimeMillis();
        this.lastMessage = System.currentTimeMillis();
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
        this.percentArmor = PlayerStatsFormules.getPercentArmorStat(player, armor);
    }

    public void UpdateMagicPercentArmor() {
        this.magicPercentArmor = PlayerStatsFormules.getPercentArmorStat(player, magicArmor);
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

    public void ManaRegen(Double multiplier) {
    	double manaRegen = this.manaRegen*multiplier;
        if (manaCurrent + manaRegen > manaMax) {
            this.manaCurrent = this.manaMax;
        } else {
            this.manaCurrent = this.manaCurrent + manaRegen;
        }
    }

    public void ShowStats(Player player) {
        DecimalFormat df = new DecimalFormat("#.#");
        double dp = (minDamage + maxDamage) / 2;
        double dps = dp / weaponSpeed;
        player.sendMessage("Damage: " + df.format(minDamage) + " - " + df.format(maxDamage) + " DP(" + df.format(dp) + ")");
        player.sendMessage("Weapon Speed: " + weaponSpeed + " DPS(" + df.format(dps) + ")");
        player.sendMessage("Magic Power: " + df.format(magicPower) + "");
        player.sendMessage("Mana: " + df.format(manaCurrent) + "/" + df.format(manaMax));
        player.sendMessage("Armor: " + armor + " (" + df.format(percentArmor * 100) + "%)");
        player.sendMessage("Magic Armor: " + magicArmor + " (" + df.format(magicPercentArmor * 100) + "%)");
        player.sendMessage("Dodge: " + df.format(dodge * 100) + "%");
        player.sendMessage("Block: " + df.format(block * 100) + "%");
        player.sendMessage("Crit. Chance: " + df.format(critChance * 100) + "%");
        player.sendMessage("Crit. Damage: " + df.format(critDamage * 100) + "%");
        player.sendMessage("Poison: " + df.format(poison * 100) + "%");
        player.sendMessage("Mana Regen: " + df.format(manaRegen));
        player.sendMessage("CD Reduction: " + df.format(spellCooldownReduction * 100) + "%");
        player.sendMessage("Magic Pen.: " + df.format(magicArmorPen * 100) + "%");
    }

    public void ShowStats() {
        ShowStats(player);
    }

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(double baseDamage) {
		this.baseDamage = baseDamage;
	}

	public double getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(double minDamage) {
		this.minDamage = minDamage;
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(double maxDamage) {
		this.maxDamage = maxDamage;
	}

	public double getWeaponSpeed() {
		return weaponSpeed;
	}

	public void setWeaponSpeed(double weaponSpeed) {
		this.weaponSpeed = weaponSpeed;
	}

	public double getArmor() {
		return armor;
	}

	public void setArmor(double armor) {
		this.armor = armor;
	}

	public double getPercentArmor() {
		return percentArmor;
	}

	public void setPercentArmor(double percentArmor) {
		this.percentArmor = percentArmor;
	}

	public double getDodge() {
		return dodge;
	}

	public void setDodge(double dodge) {
		this.dodge = dodge;
	}

	public double getBlock() {
		return block;
	}

	public void setBlock(double block) {
		this.block = block;
	}

	public double getCritChance() {
		return critChance;
	}

	public void setCritChance(double critChance) {
		this.critChance = critChance;
	}

	public double getCritDamage() {
		return critDamage;
	}

	public void setCritDamage(double critDamage) {
		this.critDamage = critDamage;
	}

	public double getHealthCurrent() {
		return healthCurrent;
	}

	public void setHealthCurrent(double healthCurrent) {
		this.healthCurrent = healthCurrent;
	}

	public double getHealthMax() {
		return healthMax;
	}

	public void setHealthMax(double healthMax) {
		this.healthMax = healthMax;
	}

	public double getHealthRegen() {
		return healthRegen;
	}

	public void setHealthRegen(double healthRegen) {
		this.healthRegen = healthRegen;
	}

	public double getLifeSteal() {
		return lifeSteal;
	}

	public void setLifeSteal(double lifeSteal) {
		this.lifeSteal = lifeSteal;
	}

	public double getReflect() {
		return reflect;
	}

	public void setReflect(double reflect) {
		this.reflect = reflect;
	}

	public double getFire() {
		return fire;
	}

	public void setFire(double fire) {
		this.fire = fire;
	}

	public double getIce() {
		return ice;
	}

	public void setIce(double ice) {
		this.ice = ice;
	}

	public double getPoison() {
		return poison;
	}

	public void setPoison(double poison) {
		this.poison = poison;
	}

	public double getWither() {
		return wither;
	}

	public void setWither(double wither) {
		this.wither = wither;
	}

	public double getHarming() {
		return harming;
	}

	public void setHarming(double harming) {
		this.harming = harming;
	}

	public double getBlind() {
		return blind;
	}

	public void setBlind(double blind) {
		this.blind = blind;
	}

	public double getXPMultiplier() {
		return XPMultiplier;
	}

	public void setXPMultiplier(double xPMultiplier) {
		XPMultiplier = xPMultiplier;
	}

	public double getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(double movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public double getManaMax() {
		return manaMax;
	}

	public void setManaMax(double manaMax) {
		this.manaMax = manaMax;
	}

	public double getManaCurrent() {
		return manaCurrent;
	}

	public void setManaCurrent(double manaCurrent) {
		this.manaCurrent = manaCurrent;
	}

	public double getManaRegen() {
		return manaRegen;
	}

	public void setManaRegen(double manaRegen) {
		this.manaRegen = manaRegen;
	}

	public double getSpellCooldownReduction() {
		return spellCooldownReduction;
	}

	public void setSpellCooldownReduction(double spellCooldownReduction) {
		this.spellCooldownReduction = spellCooldownReduction;
	}

	public double getMagicPower() {
		return magicPower;
	}

	public void setMagicPower(double magicPower) {
		this.magicPower = magicPower;
	}

	public double getMagicArmor() {
		return magicArmor;
	}

	public void setMagicArmor(double magicArmor) {
		this.magicArmor = magicArmor;
	}

	public double getMagicPercentArmor() {
		return magicPercentArmor;
	}

	public void setMagicPercentArmor(double magicPercentArmor) {
		this.magicPercentArmor = magicPercentArmor;
	}

	public double getArmorPen() {
		return armorPen;
	}

	public void setArmorPen(double armorPen) {
		this.armorPen = armorPen;
	}

	public double getMagicArmorPen() {
		return magicArmorPen;
	}

	public void setMagicArmorPen(double magicArmorPen) {
		this.magicArmorPen = magicArmorPen;
	}

	public double getStab() {
		return stab;
	}

	public void setStab(double stab) {
		this.stab = stab;
	}

	public double getStabDamage() {
		return stabDamage;
	}

	public void setStabDamage(double stabDamage) {
		this.stabDamage = stabDamage;
	}

	public double getLuck() {
		return Luck;
	}

	public void setLuck(double luck) {
		Luck = luck;
	}

	public long getLastSpellCast() {
		return lastSpellCast;
	}

	public void setLastSpellCast(long lastSpellCast) {
		this.lastSpellCast = lastSpellCast;
	}

	public long getSpellCastWait() {
		return spellCastWait;
	}

	public void setSpellCastWait(long spellCastWait) {
		this.spellCastWait = spellCastWait;
	}

	public long getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(long lastMessage) {
		this.lastMessage = lastMessage;
	}

}
