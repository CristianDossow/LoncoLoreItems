package net.nifheim.yitan.lorestats.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void modifyMobHealth(CreatureSpawnEvent event) {
        /*
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getWorld().getName())) {
            LivingEntity entity = event.getEntity();
            Location entityLoc = entity.getLocation();

            if ((Main.plugin.getConfig().getBoolean("ILSLootFromNaturalSpawnsOnly"))
                    && (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))) {
                entity.setMetadata("naturalSpawn", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
            }

            if ((ItemLoreStats.plugin.util_WorldGuard != null) /*&& (ItemLoreStats.plugin.util_WorldGuard.entityInLevelRegion(entity))) {
                    String regionName = ItemLoreStats.plugin.util_WorldGuard.getRegionNameFromLocation(entityLoc);

                    int minLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[0]);
                    int maxLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[1]);
                    int mobLevel = ItemLoreStats.plugin.util_Random.randomRange(minLevelRange, maxLevelRange);

                    double additionalHealth = ItemLoreStats.plugin.gearStats.getHealthItemInHand(ItemLoreStats.plugin.itemInMainHand(entity)) + ItemLoreStats.plugin.gearStats.getHealthItemInHand(ItemLoreStats.plugin.itemInOffHand(entity)) + ItemLoreStats.plugin.gearStats.getHealthGear(entity);
                    double newHealth = entity.getMaxHealth() + mobLevel * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + regionName + ".healthMultiplier") + additionalHealth;

                    entity.setMetadata("level", new FixedMetadataValue(ItemLoreStats.plugin, Integer.valueOf(mobLevel)));
                    entity.setMetadata("regionSpawned", new FixedMetadataValue(ItemLoreStats.plugin, ItemLoreStats.this.util_WorldGuard.getRegionInsideName(entity)));

                    entity.setMaxHealth(Double.valueOf(newHealth).intValue());
                    entity.setHealth(Double.valueOf(newHealth).intValue());/*if ((ItemLoreStats.this.util_WorldGuard != null) /*&& (ItemLoreStats.this.util_WorldGuard.entityInLevelRegion(entity))) {
                    String regionName = ItemLoreStats.this.util_WorldGuard.getRegionNameFromLocation(entityLoc);

                    int minLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[0]);
                    int maxLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[1]);
                    int mobLevel = ItemLoreStats.this.util_Random.randomRange(minLevelRange, maxLevelRange);

                    double additionalHealth = ItemLoreStats.this.gearStats.getHealthItemInHand(ItemLoreStats.this.itemInMainHand(entity)) + ItemLoreStats.this.gearStats.getHealthItemInHand(ItemLoreStats.this.itemInOffHand(entity)) + ItemLoreStats.this.gearStats.getHealthGear(entity);
                    double newHealth = entity.getMaxHealth() + mobLevel * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + regionName + ".healthMultiplier") + additionalHealth;

                    entity.setMetadata("level", new FixedMetadataValue(ItemLoreStats.plugin, Integer.valueOf(mobLevel)));
                    entity.setMetadata("regionSpawned", new FixedMetadataValue(ItemLoreStats.plugin, ItemLoreStats.this.util_WorldGuard.getRegionInsideName(entity)));

                    entity.setMaxHealth(Double.valueOf(newHealth).intValue());
                    entity.setHealth(Double.valueOf(newHealth).intValue());
                } else if (Main.plugin.getConfig().getString("npcModifier." + event.getEntity().getWorld().getName()) != null) {
                String worldName = entity.getWorld().getName();

                Location loc = new Location(entity.getWorld(), Main.plugin.getConfig().getInt("npcModifier." + worldName + ".location.x"), Main.plugin.getConfig().getInt("npcModifier." + worldName + ".location.y"), Main.plugin.getConfig().getInt("npcModifier." + worldName + ".location.z"));

                int mobLevel = (int) Math.ceil(entity.getEyeLocation().distance(loc) / Main.plugin.getConfig().getDouble("npcModifier." + worldName + ".blocksPerLevel"));
                double calcNewHealth = Math.ceil(mobLevel * Main.plugin.getConfig().getDouble("npcModifier." + worldName + ".healthMultiplier"));
                double additionalHealth = Main.plugin.gearStats.getHealthItemInHand(Main.plugin.itemInMainHand(entity)) + Main.plugin.gearStats.getHealthItemInHand(Main.plugin.itemInOffHand(entity)) + Main.plugin.gearStats.getHealthGear(entity);
                double newHealth = calcNewHealth + additionalHealth;

                entity.setMetadata("level", new FixedMetadataValue(Main.plugin, Integer.valueOf(mobLevel)));

                if (newHealth > 2000000.0D) {
                    newHealth = 2000000.0D;
                }

                entity.setMaxHealth(Double.valueOf(newHealth).intValue());
                entity.setHealth(Double.valueOf(newHealth).intValue());
            }
        }*/
    }
}
