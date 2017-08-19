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

public class Armour {

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

    public double armourChanceOnHit(LivingEntity getDefender) {
        double stat = 0.0D;

        if (Main.getInstance().isTool(this.getSlots.returnItemInMainHand(getDefender).getType())) {
            stat += this.gearStats.getArmourItemInHand(Main.getInstance().itemInMainHand(getDefender));
        }

        if (Main.getInstance().isTool(this.getSlots.returnItemInOffHand(getDefender).getType())) {
            stat += this.gearStats.getArmourItemInHand(Main.getInstance().itemInOffHand(getDefender));
        }

        stat += this.gearStats.getArmourGear(getDefender);

        return this.util_Format.format(stat);
    }
}
