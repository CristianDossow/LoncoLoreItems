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
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Ice {

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

    public void iceChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getIceGear(getAttacker) + this.gearStats.getIceItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getIceItemInHand(Main.getInstance().itemInOffHand(getAttacker)) <= 0.0D) {
            return;
        }
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".ice", Main.getInstance().getConfig().getInt("secondaryStats.ice.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.getInstance().internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".ice", Long.valueOf(System.currentTimeMillis()));
            }

            double icePercent = 0.0D;

            if (isTool) {
                icePercent = this.util_Format.format(this.gearStats.getIceGear(getAttacker) + this.gearStats.getIceItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getIceItemInHand(Main.getInstance().itemInMainHand(getAttacker)));
            } else {
                icePercent = this.util_Format.format(this.gearStats.getIceGear(getAttacker));
            }

            if (icePercent > 100.0D) {
                icePercent = 100.0D;
            }

            if (this.util_Random.random(100) <= icePercent) {
                if (((getAttacker instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.ice"))) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.IceSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (((getDefender instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.enemyIce"))) {
                    if ((getAttacker instanceof Player)) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyIceSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if ((getAttacker instanceof LivingEntity)) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyIceSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyIceSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                getDefender.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.SLOW, Main.getInstance().getConfig().getInt("secondaryStats.ice.effectDuration") * 20, Main.getInstance().getConfig().getInt("secondaryStats.ice.effectAmplifier")));
            }
        }
    }
}
