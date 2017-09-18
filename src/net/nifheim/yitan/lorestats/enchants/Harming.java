package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.GearStats;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.SetBonuses;
import net.nifheim.yitan.lorestats.durability.Durability;
import net.nifheim.yitan.lorestats.utils.InvSlot.GetSlots;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import net.nifheim.yitan.lorestats.utils.Util_Format;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Harming {

    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();

    public void harmingChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getHarmingGear(getAttacker) + this.gearStats.getHarmingItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getHarmingItemInHand(Main.getInstance().itemInOffHand(getAttacker)) <= 0.0D) {
            return;
        }
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".har", Main.getInstance().getConfig().getInt("secondaryStats.harming.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.getInstance().internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".har", Long.valueOf(System.currentTimeMillis()));
            }

            double harmingPercent = 0.0D;

            if (isTool) {
                harmingPercent = this.util_Format.format(this.gearStats.getHarmingGear(getAttacker) + this.gearStats.getHarmingItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getHarmingItemInHand(Main.getInstance().itemInMainHand(getAttacker)));
            } else {
                harmingPercent = this.util_Format.format(this.gearStats.getHarmingGear(getAttacker));
            }

            if (harmingPercent > 100.0D) {
                harmingPercent = 100.0D;
            }

            if (this.util_Random.random(100) <= harmingPercent) {
                if (((getAttacker instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.harm"))) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.HarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (((getDefender instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.enemyHarm"))) {
                    if ((getAttacker instanceof Player)) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if ((getAttacker instanceof LivingEntity)) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyHarmSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                if (((getDefender instanceof Wither)) || ((getDefender instanceof Zombie)) || ((getDefender instanceof org.bukkit.entity.Skeleton)) || ((getDefender instanceof PigZombie))) {
                    getDefender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Main.getInstance().getConfig().getInt("secondaryStats.harming.effectDuration") * 20, Main.getInstance().getConfig().getInt("secondaryStats.harming.effectAmplifier")));
                } else {
                    getDefender.addPotionEffect(new PotionEffect(PotionEffectType.HARM, Main.getInstance().getConfig().getInt("secondaryStats.harming.effectDuration") * 20, Main.getInstance().getConfig().getInt("secondaryStats.harming.effectAmplifier")));
                }
            }
        }
    }
}
