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

public class Wither {

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

    public void witherChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, boolean isTool) {
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".wit", Main.getInstance().getConfig().getInt("secondaryStats.wither.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.getInstance().internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".wit", System.currentTimeMillis());
            }

            double witherPercent;

            if (isTool) {
                witherPercent = this.util_Format.format(this.gearStats.getWitherGear(getAttacker) + this.gearStats.getWitherItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getWitherItemInHand(Main.getInstance().itemInMainHand(getAttacker)));
            } else {
                witherPercent = this.util_Format.format(this.gearStats.getWitherGear(getAttacker));
            }

            if (witherPercent > 100.0D) {
                witherPercent = 100.0D;
            }

            if (this.util_Random.random(100) <= witherPercent) {
                if (((getAttacker instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.wither"))) {
                    ((Player) getAttacker).sendMessage(this.util_GetResponse.getResponse("DamageMessages.WitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                }

                if (((getDefender instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.enemyWither"))) {
                    if ((getAttacker instanceof Player)) {
                        ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyWitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                    } else if ((getAttacker instanceof LivingEntity)) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyWitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        } else {
                            ((Player) getDefender).sendMessage(this.util_GetResponse.getResponse("DamageMessages.EnemyWitherSuccess", getAttacker, getDefender, String.valueOf(0), String.valueOf(0)));
                        }
                    }
                }

                getDefender.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.WITHER, Main.getInstance().getConfig().getInt("secondaryStats.wither.effectDuration") * 20, Main.getInstance().getConfig().getInt("secondaryStats.wither.effectAmplifier")));
            }
        }
    }
}
