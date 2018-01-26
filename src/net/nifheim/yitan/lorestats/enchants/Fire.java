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

public class Fire {

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

    public void fireChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getFireGear(getAttacker) + this.gearStats.getFireItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getFireItemInHand(Main.getInstance().itemInOffHand(getAttacker)) <= 0.0D) {
            return;
        }
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".fir", Main.getInstance().getConfig().getInt("secondaryStats.fire.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.getInstance().internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".fir", Long.valueOf(System.currentTimeMillis()));
            }

            double firePercent = 0.0D;

            if (isTool) {
                firePercent = this.util_Format.format(this.gearStats.getFireGear(getAttacker) + this.gearStats.getFireItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getFireItemInHand(Main.getInstance().itemInMainHand(getAttacker)));
            } else {
                firePercent = this.util_Format.format(this.gearStats.getFireGear(getAttacker));
            }

            if (firePercent > 100.0D) {
                firePercent = 100.0D;
            }

            if (this.util_Random.random(100) <= firePercent) {
                if (((getAttacker instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.fire"))) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.FireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (((getDefender instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.enemyFire"))) {
                    if ((getAttacker instanceof Player)) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyFireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if ((getAttacker instanceof LivingEntity)) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyFireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyFireSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                getDefender.setFireTicks(Main.getInstance().getConfig().getInt("secondaryStats.fire.effectDuration") * 20);
            }
        }
    }
}
