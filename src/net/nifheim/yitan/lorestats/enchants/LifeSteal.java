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

public class LifeSteal {

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

    public void lifeStealChanceOnHit(LivingEntity getDefender, LivingEntity getAttacker, double weaponDamage, boolean isTool) {
        if (gearStats.getLifeStealGear(getAttacker) + gearStats.getLifeStealItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + gearStats.getLifeStealItemInHand(Main.getInstance().itemInOffHand(getAttacker)) <= 0.0D) {
            return;
        }
        if (!internalCooldown.hasCooldown(util_EntityManager.returnEntityName(getAttacker) + ".lif", Main.getInstance().getConfig().getInt("secondaryStats.lifeSteal.internalCooldown"))) {
            if ((getAttacker instanceof Player)) {
                Main.getInstance().internalCooldowns.put(util_EntityManager.returnEntityName(getAttacker) + ".lif", System.currentTimeMillis());
            }

            double lifeStealHeal;
            double lifeStealPercent;

            if (isTool) {
                lifeStealPercent = util_Format.format(gearStats.getLifeStealGear(getAttacker) + gearStats.getLifeStealItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + gearStats.getLifeStealItemInHand(Main.getInstance().itemInMainHand(getAttacker)));
            } else {
                lifeStealPercent = util_Format.format(gearStats.getLifeStealGear(getAttacker));
            }

            if (lifeStealPercent > 100.0D) {
                lifeStealPercent = 100.0D;
            }

            if (util_Random.random(100) <= lifeStealPercent) {
                lifeStealHeal = Main.getInstance().getConfig().getDouble("secondaryStats.lifeSteal.healPercentage") * weaponDamage;

                if (((getAttacker instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.outgoing.lifeSteal"))) {
                    ((Player) getAttacker).sendMessage(util_GetResponse.getResponse("DamageMessages.LifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                }

                if (((getDefender instanceof Player))
                        && (Main.getInstance().getConfig().getBoolean("combatMessages.incoming.enemyLifeSteal"))) {
                    if ((getAttacker instanceof Player)) {
                        ((Player) getDefender).sendMessage(util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                    } else if ((getAttacker instanceof LivingEntity)) {
                        if (getAttacker.getCustomName() != null) {
                            ((Player) getDefender).sendMessage(util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                        } else {
                            ((Player) getDefender).sendMessage(util_GetResponse.getResponse("DamageMessages.EnemyLifeStealSuccess", getAttacker, getDefender, String.valueOf((int) lifeStealHeal), String.valueOf((int) lifeStealHeal)));
                        }
                    }
                }

                if (util_EntityManager.returnEntityCurrentHealth(getAttacker) < util_EntityManager.returnEntityMaxHealth(getAttacker)) {
                    if (lifeStealHeal > Math.abs(util_EntityManager.returnEntityCurrentHealth(getAttacker) - util_EntityManager.returnEntityMaxHealth(getAttacker))) {
                        double getRemainingHealth = Math.abs(util_EntityManager.returnEntityCurrentHealth(getAttacker) - util_EntityManager.returnEntityMaxHealth(getAttacker));
                        util_EntityManager.setEntityCurrentHealth(getAttacker, util_EntityManager.returnEntityCurrentHealth(getAttacker) + getRemainingHealth);
                    } else {
                        util_EntityManager.setEntityCurrentHealth(getAttacker, util_EntityManager.returnEntityCurrentHealth(getAttacker) + lifeStealHeal);
                    }
                }
            }
        }
    }
}
