package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.durability.Durability;
import net.nifheim.yitan.lorestats.GearStats;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.SetBonuses;
import net.nifheim.yitan.lorestats.utils.InvSlot.GetSlots;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import net.nifheim.yitan.lorestats.utils.Util_Format;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import org.bukkit.entity.LivingEntity;

public class Reflect {

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

    public double reflectChanceOnHit(LivingEntity getAttacker, boolean isTool) {
        if (this.gearStats.getReflectGear(getAttacker) + this.gearStats.getReflectItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getReflectItemInHand(Main.getInstance().itemInOffHand(getAttacker)) <= 0.0D) {
            return 0.0D;
        }
        if (!this.internalCooldown.hasCooldown(this.util_EntityManager.returnEntityName(getAttacker) + ".ref", Main.getInstance().getConfig().getInt("secondaryStats.reflect.internalCooldown"))) {
            if ((getAttacker instanceof org.bukkit.entity.Player)) {
                Main.getInstance().internalCooldowns.put(this.util_EntityManager.returnEntityName(getAttacker) + ".ref", Long.valueOf(System.currentTimeMillis()));
            }

            double reflect = 0.0D;

            if (isTool) {
                reflect = this.util_Format.format(this.gearStats.getReflectGear(getAttacker) + this.gearStats.getReflectItemInHand(Main.getInstance().itemInMainHand(getAttacker)) + this.gearStats.getReflectItemInHand(Main.getInstance().itemInMainHand(getAttacker)));
            } else {
                reflect = this.util_Format.format(this.gearStats.getReflectGear(getAttacker));
            }

            return this.util_Format.format(reflect);
        }
        return 0.0D;
    }
}
