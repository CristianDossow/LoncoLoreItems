package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import org.bukkit.entity.LivingEntity;

public class Vanilla_Sharpness {

    Util_EntityManager util_EntityManager = new Util_EntityManager();

    public boolean hasSharpness(LivingEntity entity) {

        if (entity != null) {
            if (Main.getInstance().isTool(Main.getInstance().itemInMainHand(entity).getType())) {
                if (entity.getEquipment().getItemInMainHand().getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.DAMAGE_ALL)) {
                    return true;
                }
            } else if ((Main.getInstance().isTool(Main.getInstance().itemInOffHand(entity).getType()))
                    && (entity.getEquipment().getItemInOffHand().getEnchantments().containsKey(org.bukkit.enchantments.Enchantment.DAMAGE_ALL))) {
                return true;
            }
        }

        return false;
    }

    public double calculateNewDamage(double damage, int enchantLevelMain, int enchantLevelOff) {
        double value = damage;
        int levelMain = 0;
        int levelOff = 0;

        if (enchantLevelMain != 0) {
            levelMain = enchantLevelMain;
        }

        if (enchantLevelOff != 0) {
            levelOff = enchantLevelMain;
        }

        int enchantLevel = levelMain + levelOff;

        value = damage + value / 100.0D * (Main.getInstance().getConfig().getDouble("enchants.sharpness.levelMultiplier") * enchantLevel);

        return value;
    }
}
