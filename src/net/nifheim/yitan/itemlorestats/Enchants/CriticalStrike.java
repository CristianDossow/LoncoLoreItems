package net.nifheim.yitan.itemlorestats.Enchants;

import net.nifheim.yitan.itemlorestats.Durability.Durability;
import net.nifheim.yitan.itemlorestats.GearStats;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.SetBonuses;
import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_Random;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CriticalStrike {

    Durability durability = new Durability();
    GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    InternalCooldown internalCooldown = new InternalCooldown();
    SetBonuses setBonuses = new SetBonuses();
    net.nifheim.yitan.itemlorestats.Util.Util_Colours util_Colours = new net.nifheim.yitan.itemlorestats.Util.Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();

    public int criticalStrikeChanceOnHit(LivingEntity getAttacker, LivingEntity getDefender) {
        if (this.gearStats.getCritChanceGear(getAttacker) + this.gearStats.getCritChanceItemInHand(Main.plugin.itemInMainHand(getAttacker)) + this.gearStats.getCritChanceItemInHand(Main.plugin.itemInOffHand(getAttacker)) <= 0.0D) {
            return 0;
        }
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".cri", Main.plugin.getConfig().getInt("secondaryStats.critChance.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.plugin.internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".cri", Long.valueOf(System.currentTimeMillis()));
            }

            double critPercent = 0.0D;

            if (Main.plugin.isTool(this.getSlots.returnItemInMainHand(getAttacker).getType())) {
                critPercent += this.gearStats.getCritChanceItemInHand(Main.plugin.itemInMainHand(getAttacker));
            }

            if (Main.plugin.isTool(this.getSlots.returnItemInOffHand(getAttacker).getType())) {
                critPercent += this.gearStats.getCritChanceItemInHand(Main.plugin.itemInOffHand(getAttacker));
            }

            critPercent += this.gearStats.getCritChanceGear(getAttacker);

            if (critPercent >= this.util_Random.random(100)) {
                if (((getAttacker instanceof Player))
                        && (Main.plugin.getConfig().getBoolean("combatMessages.outgoing.critStrike"))
                        && ((getAttacker instanceof Player))) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.CriticalStrikeSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (((getDefender instanceof Player))
                        && (Main.plugin.getConfig().getBoolean("combatMessages.incoming.enemyCritStrike"))) {
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
