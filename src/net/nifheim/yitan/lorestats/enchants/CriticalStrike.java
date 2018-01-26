package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.GearStats;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.SetBonuses;
import net.nifheim.yitan.lorestats.durability.Durability;
import net.nifheim.yitan.lorestats.utils.InvSlot.GetSlots;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import net.nifheim.yitan.lorestats.utils.Util_Format;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CriticalStrike {

    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    net.nifheim.yitan.lorestats.utils.Util_Colours util_Colours = new net.nifheim.yitan.lorestats.utils.Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();

    public int criticalStrikeChanceOnHit(LivingEntity getAttacker, LivingEntity getDefender) {
        if (this.gearStats.getCritChanceGear(getAttacker) + this.gearStats.getCritChanceItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getCritChanceItemInHand(Main.getInstance().itemInOffHand(getAttacker)) <= 0.0D) {
            return 0;
        }
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".cri", Main.getInstance().getConfig().getInt("secondaryStats.critChance.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.getInstance().internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".cri", Long.valueOf(System.currentTimeMillis()));
            }

            double critPercent = 0.0D;

            if (Main.getInstance().isTool(this.getSlots.returnItemInMainHand(getAttacker).getType())) {
                critPercent += this.gearStats.getCritChanceItemInHand(Main.getInstance().itemInMainHand(getAttacker));
            }

            if (Main.getInstance().isTool(this.getSlots.returnItemInOffHand(getAttacker).getType())) {
                critPercent += this.gearStats.getCritChanceItemInHand(Main.getInstance().itemInOffHand(getAttacker));
            }

            critPercent += this.gearStats.getCritChanceGear(getAttacker);

            if (critPercent >= this.util_Random.random(100)) {
                if (((getAttacker instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.critStrike"))
                        && ((getAttacker instanceof Player))) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.CriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (((getDefender instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.enemyCritStrike"))) {
                    if ((getAttacker instanceof Player)) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyCriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if ((getAttacker instanceof LivingEntity)) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyCriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyCriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                return 2;
            }

            return 1;
        }

        return 1;
    }
}
