package net.nifheim.yitan.itemlorestats.Util;

import net.nifheim.yitan.itemlorestats.Main;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Util_WorldGuard {

    Main main;
    Util_Random util_Random = new Util_Random();

    public Util_WorldGuard(Main instance) {
        this.main = instance;
    }

    /*    public boolean playerInPVPRegion(Player player) {
    //ApplicableRegionSet set = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
    //ApplicableRegionSet set = ItemLoreStats.plugin.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
    try {
    ApplicableRegionSet set = (ItemLoreStats.plugin.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()));
    if (set.allows(com.sk89q.worldguard.protection.flags.DefaultFlag.PVP)) {
    return true;
    }
    } catch (Exception e) {
    
    }
    
    return false;
    }*/

    /*    public boolean playerInInvincibleRegion(Player player) {
    ApplicableRegionSet set = ItemLoreStats.plugin.getWorldGuard().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
    
    if (set.allows(com.sk89q.worldguard.protection.flags.DefaultFlag.INVINCIBILITY)) {
    return true;
    }
    return false;
    }*/
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
