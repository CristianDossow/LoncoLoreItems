package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Vanilla_FeatherFalling {

    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Random util_Random = new Util_Random();

    public boolean hasFeatherFalling(ItemStack boots) {
        if (boots.getEnchantments().containsKey(Enchantment.PROTECTION_FALL)) {
            return true;
        }

        return false;
    }

    public double calculateNewFallDamage(int enchantLevel, double fallDamage) {
        double value = fallDamage;
        int percentage = enchantLevel * Main.getInstance().getConfig().getInt("enchants.featherFalling.levelMultiplier");

        value = fallDamage - fallDamage * percentage / 100.0D;

        return value;
    }
}
