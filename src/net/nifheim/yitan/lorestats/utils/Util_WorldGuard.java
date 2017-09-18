package net.nifheim.yitan.lorestats.utils;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.Player;

public class Util_WorldGuard {

    Main main;
    Util_Random util_Random = new Util_Random();

    public Util_WorldGuard(Main instance) {
        this.main = instance;
    }

    public boolean playersInPvPRegion(Player player1, Player player2) {
        if (playerInPVPRegion(player1) && playerInPVPRegion(player2)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playerInPVPRegion(Player player) {
        ApplicableRegionSet set = (Main.getInstance().getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()));
        LocalPlayer localPlayer = Main.getInstance().getWorldGuard().wrapPlayer(player);
        if (set.queryState(localPlayer, DefaultFlag.PVP) != StateFlag.State.DENY) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playerInInvincibleRegion(Player player) {
        ApplicableRegionSet set = Main.getInstance().getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());

        if (set.allows(com.sk89q.worldguard.protection.flags.DefaultFlag.INVINCIBILITY)) {
            return true;
        }
        return false;
    }
    /*    public boolean entityInLevelRegion(LivingEntity entity) {
    for (ProtectedRegion reg : ItemLoreStats.plugin.getWorldGuard().getRegionManager(entity.getWorld()).getApplicableRegions(entity.getLocation())) {
    if ((reg.getId().toString().toLowerCase().contains("ils_")) || (reg.getId().toString().toLowerCase().contains("itemlorestats_"))) {
    return true;
    }
    }
    return false;
    }
    
    public String getRegionInsideName(LivingEntity entity) {
    for (ProtectedRegion reg : ItemLoreStats.plugin.getWorldGuard().getRegionManager(entity.getWorld()).getApplicableRegions(entity.getLocation())) {
    if ((reg.getId().toString().toLowerCase().contains("ils_")) || (reg.getId().toString().toLowerCase().contains("itemlorestats_"))) {
    return reg.getId().toString();
    }
    }
    
    return null;
    }
    
    public String getRegionNameFromLocation(Location loc) {
    for (ProtectedRegion reg : ItemLoreStats.plugin.getWorldGuard().getRegionManager(loc.getWorld()).getApplicableRegions(loc)) {
    if ((reg.getId().toString().toLowerCase().contains("ils_")) || (reg.getId().toString().toLowerCase().contains("itemlorestats_"))) {
    return reg.getId().toString();
    }
    }
    
    return null;
    }*/
}
