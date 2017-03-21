 package com.github.supavitax.itemlorestats.Misc;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import java.io.File;
 import java.io.PrintStream;
 import java.text.DecimalFormat;
 import java.text.NumberFormat;
 import java.util.Locale;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.plugin.PluginDescriptionFile;
 
 public class WriteDefaultFiles implements org.bukkit.event.Listener
 {
   private File PlayerDataFile;
   private FileConfiguration PlayerDataConfig;
   DecimalFormat format = new DecimalFormat("0.00");
   
   private String formatVersion(double version) {
     Locale forceLocale = new Locale("en", "UK");
     String decimalPattern = "0.00";
     
     DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(forceLocale);
     decimalFormat.applyPattern(decimalPattern);
     
     String format = decimalFormat.format(version);
     
     return format;
   }
   
   public void checkExistence()
   {
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems").exists()) {
       new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems").mkdirs();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs").exists()) {
       new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs").mkdirs();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells").exists()) {
       new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells").mkdirs();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData").exists()) {
       new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "PlayerData").mkdirs();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "language-en.yml").exists()) {
       writeLanguageEN();
     }
     new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "language-de.yml").exists();
     
 
     new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "language-pl.yml").exists();
     
 
 
 
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "blaze.yml").exists()) {
       writeBlaze();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "cave_spider.yml").exists()) {
       writeCave_Spider();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "creeper.yml").exists()) {
       writeCreeper();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "ender_dragon.yml").exists()) {
       writeEnder_Dragon();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "enderman.yml").exists()) {
       writeEnderman();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "ghast.yml").exists()) {
       writeGhast();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "magma_cube.yml").exists()) {
       writeMagma_Cube();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "silverfish.yml").exists()) {
       writeSilverfish();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "skeleton.yml").exists()) {
       writeSkeleton();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither_skeleton.yml").exists()) {
       writeWitherSkeleton();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "slime.yml").exists()) {
       writeSlime();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "spider.yml").exists()) {
       writeSpider();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "witch.yml").exists()) {
       writeWitch();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither.yml").exists()) {
       writeWither();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "zombie.yml").exists()) {
       writeZombie();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "baby_zombie.yml").exists()) {
       writeBabyZombie();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "villager_zombie.yml").exists()) {
       writeVillagerZombie();
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "pig_zombie.yml").exists()) {
       writePig_Zombie();
     }
     
 
 
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball.yml").exists()) {
       writeDefaultSpell("Fireball", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 5, 2, 2);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball I.yml").exists()) {
       writeDefaultSpell("Fireball I", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 8, 5, 2);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball II.yml").exists()) {
       writeDefaultSpell("Fireball II", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 11, 8, 3);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Fireball III.yml").exists()) {
       writeDefaultSpell("Fireball III", "SmallFireball", 2, "Mobspawner_Flames", 15, 0, 0, 0, 14, 10, 3);
     }
     
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt.yml").exists()) {
       writeDefaultSpell("Frostbolt", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 5, 2, 2);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt I.yml").exists()) {
       writeDefaultSpell("Frostbolt I", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 8, 5, 2);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt II.yml").exists()) {
       writeDefaultSpell("Frostbolt II", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 11, 8, 3);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Frostbolt III.yml").exists()) {
       writeDefaultSpell("Frostbolt III", "Snowball", 2, "Potion_Break", 15, 0, 0, 0, 14, 10, 3);
     }
     
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal.yml").exists()) {
       writeDefaultSpell("Minor Heal", "Snowball", 3, "Ender_Signal", 4, 4, 0, 0, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal I.yml").exists()) {
       writeDefaultSpell("Minor Heal I", "Snowball", 3, "Ender_Signal", 4, 5, 0, 0, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal II.yml").exists()) {
       writeDefaultSpell("Minor Heal II", "Snowball", 3, "Ender_Signal", 4, 6, 0, 0, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor Heal III.yml").exists()) {
       writeDefaultSpell("Minor Heal III", "Snowball", 3, "Ender_Signal", 4, 7, 0, 0, 0, 0, 0);
     }
     
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal.yml").exists()) {
       writeDefaultSpell("Major Heal", "Snowball", 3, "Ender_Signal", 8, 10, 0, 0, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal I.yml").exists()) {
       writeDefaultSpell("Major Heal I", "Snowball", 3, "Ender_Signal", 8, 11, 0, 0, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal II.yml").exists()) {
       writeDefaultSpell("Major Heal II", "Snowball", 3, "Ender_Signal", 8, 12, 0, 0, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major Heal III.yml").exists()) {
       writeDefaultSpell("Major Heal III", "Snowball", 3, "Ender_Signal", 8, 13, 0, 0, 0, 0, 0);
     }
     
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal.yml").exists()) {
       writeDefaultSpell("Minor AOE Heal", "Snowball", 8, "Ender_Signal", 8, 8, 8, 2, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal I.yml").exists()) {
       writeDefaultSpell("Minor AOE Heal I", "Snowball", 8, "Ender_Signal", 8, 9, 9, 2, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal II.yml").exists()) {
       writeDefaultSpell("Minor AOE Heal II", "Snowball", 8, "Ender_Signal", 8, 10, 10, 3, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Minor AOE Heal III.yml").exists()) {
       writeDefaultSpell("Minor AOE Heal III", "Snowball", 8, "Ender_Signal", 8, 11, 11, 3, 0, 0, 0);
     }
     
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal.yml").exists()) {
       writeDefaultSpell("Major AOE Heal", "Snowball", 8, "Ender_Signal", 16, 14, 14, 3, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal I.yml").exists()) {
       writeDefaultSpell("Major AOE Heal I", "Snowball", 8, "Ender_Signal", 16, 15, 15, 3, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal II.yml").exists()) {
       writeDefaultSpell("Major AOE Heal II", "Snowball", 8, "Ender_Signal", 16, 16, 16, 4, 0, 0, 0);
     }
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + "Major AOE Heal III.yml").exists()) {
       writeDefaultSpell("Major AOE Heal III", "Snowball", 8, "Ender_Signal", 16, 17, 17, 4, 0, 0, 0);
     }
     
 
     if (!new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + "SetBonuses.yml").exists()) {
       writeSetBonuses();
     }
   }
   
   public void writeBlaze()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "blaze.yml");
       
 
       this.PlayerDataConfig.set("5", null);
       this.PlayerDataConfig.set("5.1.itemId", Integer.valueOf(306));
       this.PlayerDataConfig.set("5.1.nameColour", "&9&l");
       this.PlayerDataConfig.set("5.1.prefix", "random");
       this.PlayerDataConfig.set("5.1.suffix", "random");
       this.PlayerDataConfig.set("5.1.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("5.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.health", "&b30-player+40");
       this.PlayerDataConfig.set("5.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("5.1.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.block", "&b1-2");
       this.PlayerDataConfig.set("5.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.movementSpeed", "&e1-3");
       this.PlayerDataConfig.set("5.1.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("5.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("5.2.itemId", Integer.valueOf(307));
       this.PlayerDataConfig.set("5.2.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.2.prefix", "random");
       this.PlayerDataConfig.set("5.2.suffix", "random");
       this.PlayerDataConfig.set("5.2.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("5.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.health", "&b30-player+40");
       this.PlayerDataConfig.set("5.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("5.2.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.block", "&b1-2");
       this.PlayerDataConfig.set("5.2.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.movementSpeed", "&e1-3");
       this.PlayerDataConfig.set("5.2.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("5.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("5.3.itemId", Integer.valueOf(308));
       this.PlayerDataConfig.set("5.3.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.3.prefix", "random");
       this.PlayerDataConfig.set("5.3.suffix", "random");
       this.PlayerDataConfig.set("5.3.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("5.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.health", "&b30-player+40");
       this.PlayerDataConfig.set("5.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("5.3.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.block", "&b1-2");
       this.PlayerDataConfig.set("5.3.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.movementSpeed", "&e1-3");
       this.PlayerDataConfig.set("5.3.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("5.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("5.4.itemId", Integer.valueOf(309));
       this.PlayerDataConfig.set("5.4.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.4.prefix", "random");
       this.PlayerDataConfig.set("5.4.suffix", "random");
       this.PlayerDataConfig.set("5.4.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("5.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.health", "&b30-player+40");
       this.PlayerDataConfig.set("5.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("5.4.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.block", "&b1-2");
       this.PlayerDataConfig.set("5.4.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.movementSpeed", "&e1-3");
       this.PlayerDataConfig.set("5.4.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("5.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("5.5.itemId", Integer.valueOf(276));
       this.PlayerDataConfig.set("5.5.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.5.prefix", "random");
       this.PlayerDataConfig.set("5.5.suffix", "random");
       this.PlayerDataConfig.set("5.5.properties.weaponspeed", "&2Fast");
       this.PlayerDataConfig.set("5.5.properties.damage", "&b40-50-player+60-70");
       this.PlayerDataConfig.set("5.5.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.critChance", "&b3-9");
       this.PlayerDataConfig.set("5.5.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.critDamage", "&b8-23");
       this.PlayerDataConfig.set("5.5.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.lifeSteal", "&23-7");
       this.PlayerDataConfig.set("5.5.properties.blindRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.blind", "&f2-5");
       this.PlayerDataConfig.set("5.5.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.ice", "&b1-4");
       this.PlayerDataConfig.set("5.5.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("5.5.properties.durability", "&7600-725");
       this.PlayerDataConfig.set("5.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.xpLevel", "&b2-player+5");
       
       this.PlayerDataConfig.set("5.6.itemId", Integer.valueOf(279));
       this.PlayerDataConfig.set("5.6.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.6.prefix", "random");
       this.PlayerDataConfig.set("5.6.suffix", "random");
       this.PlayerDataConfig.set("5.6.properties.weaponspeed", "&2Fast");
       this.PlayerDataConfig.set("5.6.properties.damage", "&b40-50-player+60-70");
       this.PlayerDataConfig.set("5.6.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.critChance", "&b3-9");
       this.PlayerDataConfig.set("5.6.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.critDamage", "&b8-23");
       this.PlayerDataConfig.set("5.6.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.lifeSteal", "&23-7");
       this.PlayerDataConfig.set("5.6.properties.blindRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.blind", "&f2-5");
       this.PlayerDataConfig.set("5.6.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.ice", "&b1-4");
       this.PlayerDataConfig.set("5.6.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("5.6.properties.durability", "&7600-725");
       this.PlayerDataConfig.set("5.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.xpLevel", "&b2-player+5");
       
       this.PlayerDataConfig.set("5.7.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("5.7.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.7.prefix", "random");
       this.PlayerDataConfig.set("5.7.suffix", "random");
       this.PlayerDataConfig.set("5.7.properties.weaponspeed", "&2Fast");
       this.PlayerDataConfig.set("5.7.properties.damage", "&b40-50-player+60-70");
       this.PlayerDataConfig.set("5.7.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.critChance", "&b3-9");
       this.PlayerDataConfig.set("5.7.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.critDamage", "&b8-23");
       this.PlayerDataConfig.set("5.7.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.lifeSteal", "&23-7");
       this.PlayerDataConfig.set("5.7.properties.blindRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.blind", "&f2-5");
       this.PlayerDataConfig.set("5.7.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.ice", "&b1-4");
       this.PlayerDataConfig.set("5.7.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("5.7.properties.durability", "&7600-725");
       this.PlayerDataConfig.set("5.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.xpLevel", "&b2-player+5");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default blaze file! ***********");
     }
   }
   
   public void writeCave_Spider()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "cave_spider.yml");
       
       this.PlayerDataConfig.set("25", null);
       this.PlayerDataConfig.set("25.1.itemId", Integer.valueOf(275));
       this.PlayerDataConfig.set("25.1.nameColour", "&c");
       this.PlayerDataConfig.set("25.1.prefix", "random");
       this.PlayerDataConfig.set("25.1.suffix", "random");
       this.PlayerDataConfig.set("25.1.properties.damage", "&b5-20-player+15-45");
       this.PlayerDataConfig.set("25.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.critChance", "&b3-6");
       this.PlayerDataConfig.set("25.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.critDamage", "&b2-4");
       this.PlayerDataConfig.set("25.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.healthRegen", "&b1-2");
       this.PlayerDataConfig.set("25.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.lifeSteal", "&21-3");
       this.PlayerDataConfig.set("25.1.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.poison", "&a3-5");
       this.PlayerDataConfig.set("25.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("25.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("25.1.properties.durability", "&7600-750");
       this.PlayerDataConfig.set("25.1.properties.droppedXp", "2");
       
       this.PlayerDataConfig.set("10", null);
       this.PlayerDataConfig.set("10.1.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("10.1.nameColour", "&c");
       this.PlayerDataConfig.set("10.1.prefix", "random");
       this.PlayerDataConfig.set("10.1.suffix", "random");
       this.PlayerDataConfig.set("10.1.properties.damage", "&b15-40-player+30-65");
       this.PlayerDataConfig.set("10.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.critChance", "&b6-8");
       this.PlayerDataConfig.set("10.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.critDamage", "&b1-5");
       this.PlayerDataConfig.set("10.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.healthRegen", "&b1-3");
       this.PlayerDataConfig.set("10.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("10.1.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.poison", "&a4-6");
       this.PlayerDataConfig.set("10.1.properties.harmingRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.harming", "&d1-2");
       this.PlayerDataConfig.set("10.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("10.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("10.1.properties.durability", "&7300-550");
       this.PlayerDataConfig.set("10.1.properties.droppedXp", "3");
       this.PlayerDataConfig.set("10.1.properties.spells.frostbolt", Boolean.valueOf(true));
       
 
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default cave_spider file! ***********");
     }
   }
   
   public void writeCreeper()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "creeper.yml");
       
 
       this.PlayerDataConfig.set("8", null);
       this.PlayerDataConfig.set("8.1.itemId", Integer.valueOf(306));
       this.PlayerDataConfig.set("8.1.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.1.prefix", "random");
       this.PlayerDataConfig.set("8.1.suffix", "random");
       this.PlayerDataConfig.set("8.1.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("8.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("8.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("8.1.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.block", "&b1-2");
       this.PlayerDataConfig.set("8.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("8.1.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("8.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("8.2.itemId", Integer.valueOf(307));
       this.PlayerDataConfig.set("8.2.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.2.prefix", "random");
       this.PlayerDataConfig.set("8.2.suffix", "random");
       this.PlayerDataConfig.set("8.2.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("8.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("8.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("8.2.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.block", "&b1-2");
       this.PlayerDataConfig.set("8.2.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("8.2.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("8.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("8.3.itemId", Integer.valueOf(308));
       this.PlayerDataConfig.set("8.3.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.3.prefix", "random");
       this.PlayerDataConfig.set("8.3.suffix", "random");
       this.PlayerDataConfig.set("8.3.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("8.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("8.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("8.3.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.block", "&b1-2");
       this.PlayerDataConfig.set("8.3.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("8.3.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("8.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("8.4.itemId", Integer.valueOf(309));
       this.PlayerDataConfig.set("8.4.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.4.prefix", "random");
       this.PlayerDataConfig.set("8.4.suffix", "random");
       this.PlayerDataConfig.set("8.4.properties.armour", "&b8-16");
       this.PlayerDataConfig.set("8.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("8.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("8.4.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.block", "&b1-2");
       this.PlayerDataConfig.set("8.4.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("8.4.properties.durability", "&71800-2400");
       this.PlayerDataConfig.set("8.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("8.5.itemId", Integer.valueOf(258));
       this.PlayerDataConfig.set("8.5.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.5.prefix", "random");
       this.PlayerDataConfig.set("8.5.suffix", "random");
       this.PlayerDataConfig.set("8.5.properties.weaponspeed", "&2Fast");
       this.PlayerDataConfig.set("8.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("8.5.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.critChance", "&b1-7");
       this.PlayerDataConfig.set("8.5.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.critDamage", "&b5-18");
       this.PlayerDataConfig.set("8.5.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.fire", "&42-6");
       this.PlayerDataConfig.set("8.5.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("8.5.properties.durability", "&7500-625");
       this.PlayerDataConfig.set("8.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.5.properties.xpLevel", "&b4-player+5");
       
       this.PlayerDataConfig.set("8.6.itemId", Integer.valueOf(257));
       this.PlayerDataConfig.set("8.6.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.6.prefix", "random");
       this.PlayerDataConfig.set("8.6.suffix", "random");
       this.PlayerDataConfig.set("8.6.properties.weaponspeed", "&2Fast");
       this.PlayerDataConfig.set("8.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("8.6.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.critChance", "&b1-7");
       this.PlayerDataConfig.set("8.6.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.critDamage", "&b5-18");
       this.PlayerDataConfig.set("8.6.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.fire", "&42-6");
       this.PlayerDataConfig.set("8.6.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("8.6.properties.durability", "&7500-625");
       this.PlayerDataConfig.set("8.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.6.properties.xpLevel", "&b4-player+5");
       
       this.PlayerDataConfig.set("8.7.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("8.7.nameColour", "&9&l");
       this.PlayerDataConfig.set("8.7.prefix", "random");
       this.PlayerDataConfig.set("8.7.suffix", "random");
       this.PlayerDataConfig.set("8.7.properties.weaponspeed", "&2Fast");
       this.PlayerDataConfig.set("8.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("8.7.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.critChance", "&b1-7");
       this.PlayerDataConfig.set("8.7.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.critDamage", "&b5-18");
       this.PlayerDataConfig.set("8.7.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.fire", "&42-6");
       this.PlayerDataConfig.set("8.7.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.movementSpeed", "&b1-4");
       this.PlayerDataConfig.set("8.7.properties.durability", "&7500-625");
       this.PlayerDataConfig.set("8.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("8.7.properties.xpLevel", "&b4-player+5");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default creeper file! ***********");
     }
   }
   
   public void writeEnder_Dragon()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "ender_dragon.yml");
       
       this.PlayerDataConfig.set("100", null);
       this.PlayerDataConfig.set("100.1.itemId", Integer.valueOf(276));
       this.PlayerDataConfig.set("100.1.nameColour", "&9&l");
       this.PlayerDataConfig.set("100.1.prefix", "random");
       this.PlayerDataConfig.set("100.1.suffix", "random");
       this.PlayerDataConfig.set("100.1.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("100.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.critChance", "&b8-14");
       this.PlayerDataConfig.set("100.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.critDamage", "&b7-23");
       this.PlayerDataConfig.set("100.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.health", "&b65-player+115");
       this.PlayerDataConfig.set("100.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.healthRegen", "&b3-5");
       this.PlayerDataConfig.set("100.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("100.1.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.wither", "&74-6");
       this.PlayerDataConfig.set("100.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("100.1.properties.durability", "&71900-2450");
       this.PlayerDataConfig.set("100.1.properties.droppedXp", "250");
       this.PlayerDataConfig.set("100.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("100.1.properties.tntRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.spells.tnt", Boolean.valueOf(true));
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default ender_dragon file! ***********");
     }
   }
   
   public void writeEnderman()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "enderman.yml");
       
 
       this.PlayerDataConfig.set("5", null);
       this.PlayerDataConfig.set("5.1.itemId", Integer.valueOf(310));
       this.PlayerDataConfig.set("5.1.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.1.prefix", "random");
       this.PlayerDataConfig.set("5.1.suffix", "random");
       this.PlayerDataConfig.set("5.1.properties.armour", "&b11-21");
       this.PlayerDataConfig.set("5.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.health", "&b50-player+60");
       this.PlayerDataConfig.set("5.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.healthRegen", "&b3-6");
       this.PlayerDataConfig.set("5.1.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.1.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.lifeSteal", "&23-6");
       this.PlayerDataConfig.set("5.1.properties.durability", "&72500-3300");
       this.PlayerDataConfig.set("5.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("5.2.itemId", Integer.valueOf(311));
       this.PlayerDataConfig.set("5.2.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.2.prefix", "random");
       this.PlayerDataConfig.set("5.2.suffix", "random");
       this.PlayerDataConfig.set("5.2.properties.armour", "&b11-21");
       this.PlayerDataConfig.set("5.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.health", "&b50-player+60");
       this.PlayerDataConfig.set("5.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.healthRegen", "&b3-6");
       this.PlayerDataConfig.set("5.2.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.2.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.2.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.lifeSteal", "&23-6");
       this.PlayerDataConfig.set("5.2.properties.durability", "&72500-3300");
       this.PlayerDataConfig.set("5.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("5.3.itemId", Integer.valueOf(312));
       this.PlayerDataConfig.set("5.3.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.3.prefix", "random");
       this.PlayerDataConfig.set("5.3.suffix", "random");
       this.PlayerDataConfig.set("5.3.properties.armour", "&b11-21");
       this.PlayerDataConfig.set("5.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.health", "&b50-player+60");
       this.PlayerDataConfig.set("5.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.healthRegen", "&b3-6");
       this.PlayerDataConfig.set("5.3.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.3.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.3.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.lifeSteal", "&23-6");
       this.PlayerDataConfig.set("5.3.properties.durability", "&72500-3300");
       this.PlayerDataConfig.set("5.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("5.4.itemId", Integer.valueOf(313));
       this.PlayerDataConfig.set("5.4.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.4.prefix", "random");
       this.PlayerDataConfig.set("5.4.suffix", "random");
       this.PlayerDataConfig.set("5.4.properties.armour", "&b11-21");
       this.PlayerDataConfig.set("5.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.health", "&b50-player+60");
       this.PlayerDataConfig.set("5.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.healthRegen", "&b3-6");
       this.PlayerDataConfig.set("5.4.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.4.properties.dodgeRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.block", "&b1-3");
       this.PlayerDataConfig.set("5.4.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.lifeSteal", "&23-6");
       this.PlayerDataConfig.set("5.4.properties.durability", "&72500-3300");
       this.PlayerDataConfig.set("5.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("5.5.itemId", Integer.valueOf(267));
       this.PlayerDataConfig.set("5.5.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.5.prefix", "random");
       this.PlayerDataConfig.set("5.5.suffix", "random");
       this.PlayerDataConfig.set("5.5.properties.weaponspeed", "&eNormal");
       this.PlayerDataConfig.set("5.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("5.5.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.critChance", "&b2-8");
       this.PlayerDataConfig.set("5.5.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.critDamage", "&b7-23");
       this.PlayerDataConfig.set("5.5.properties.blindRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.blind", "&f3-7");
       this.PlayerDataConfig.set("5.5.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.ice", "&b1-4");
       this.PlayerDataConfig.set("5.5.properties.durability", "&7500-625");
       this.PlayerDataConfig.set("5.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.5.properties.xpLevel", "&b3-player+5");
       
       this.PlayerDataConfig.set("5.6.itemId", Integer.valueOf(292));
       this.PlayerDataConfig.set("5.6.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.6.prefix", "random");
       this.PlayerDataConfig.set("5.6.suffix", "random");
       this.PlayerDataConfig.set("5.6.properties.weaponspeed", "&eNormal");
       this.PlayerDataConfig.set("5.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("5.6.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.critChance", "&b2-8");
       this.PlayerDataConfig.set("5.6.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.critDamage", "&b7-23");
       this.PlayerDataConfig.set("5.6.properties.blindRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.blind", "&f3-7");
       this.PlayerDataConfig.set("5.6.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.ice", "&b1-4");
       this.PlayerDataConfig.set("5.6.properties.durability", "&7500-625");
       this.PlayerDataConfig.set("5.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.6.properties.xpLevel", "&b3-player+5");
       
       this.PlayerDataConfig.set("5.7.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("5.7.nameColour", "&d&l");
       this.PlayerDataConfig.set("5.7.prefix", "random");
       this.PlayerDataConfig.set("5.7.suffix", "random");
       this.PlayerDataConfig.set("5.7.properties.weaponspeed", "&eNormal");
       this.PlayerDataConfig.set("5.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("5.7.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.critChance", "&b2-8");
       this.PlayerDataConfig.set("5.7.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.critDamage", "&b7-23");
       this.PlayerDataConfig.set("5.7.properties.blindRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.blind", "&f3-7");
       this.PlayerDataConfig.set("5.7.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.ice", "&b1-4");
       this.PlayerDataConfig.set("5.7.properties.durability", "&7500-625");
       this.PlayerDataConfig.set("5.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("5.7.properties.xpLevel", "&b3-player+5");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default enderman file! ***********");
     }
   }
   
   public void writeGhast()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "ghast.yml");
       
       this.PlayerDataConfig.set("40", null);
       this.PlayerDataConfig.set("40.1.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("40.1.nameColour", "&6&l");
       this.PlayerDataConfig.set("40.1.prefix", "random");
       this.PlayerDataConfig.set("40.1.suffix", "random");
       this.PlayerDataConfig.set("40.1.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("40.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("40.1.properties.critChance", "&b1-5");
       this.PlayerDataConfig.set("40.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("40.1.properties.critDamage", "&b8-14");
       this.PlayerDataConfig.set("40.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("40.1.properties.lifeSteal", "&23-5");
       this.PlayerDataConfig.set("40.1.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("40.1.properties.fire", "&44-8");
       this.PlayerDataConfig.set("40.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("40.1.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("40.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("40.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("40.1.properties.durability", "&7900-1350");
       this.PlayerDataConfig.set("40.1.properties.droppedXp", "2");
       
       this.PlayerDataConfig.set("25", null);
       this.PlayerDataConfig.set("25.1.itemId", Integer.valueOf(309));
       this.PlayerDataConfig.set("25.1.nameColour", "&6&l");
       this.PlayerDataConfig.set("25.1.prefix", "random");
       this.PlayerDataConfig.set("25.1.suffix", "random");
       this.PlayerDataConfig.set("25.1.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("25.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.health", "&b40-player+80");
       this.PlayerDataConfig.set("25.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("25.1.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.fire", "&42-6");
       this.PlayerDataConfig.set("25.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.movementSpeed", "&e2-3");
       this.PlayerDataConfig.set("25.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("25.1.properties.durability", "&7350-650");
       this.PlayerDataConfig.set("25.1.properties.fireballRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("25.1.properties.spells.fireball", Boolean.valueOf(true));
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default ghast file! ***********");
     }
   }
   
   public void writeMagma_Cube()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "magma_cube.yml");
       
       this.PlayerDataConfig.set("75", null);
       this.PlayerDataConfig.set("75.1.itemId", Integer.valueOf(283));
       this.PlayerDataConfig.set("75.1.nameColour", "&6&l");
       this.PlayerDataConfig.set("75.1.prefix", "random");
       this.PlayerDataConfig.set("75.1.suffix", "random");
       this.PlayerDataConfig.set("75.1.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("75.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.critChance", "&b2-4");
       this.PlayerDataConfig.set("75.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.critDamage", "&b8-14");
       this.PlayerDataConfig.set("75.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("75.1.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.fire", "&44-8");
       this.PlayerDataConfig.set("75.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.movementSpeed", "&e1-4");
       this.PlayerDataConfig.set("75.1.properties.durability", "&7300-550");
       this.PlayerDataConfig.set("75.1.properties.droppedXp", "2");
       this.PlayerDataConfig.set("75.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("75.1.properties.xpLevel", "&b3-player+2");
       
       this.PlayerDataConfig.set("45", null);
       this.PlayerDataConfig.set("45.1.itemId", Integer.valueOf(316));
       this.PlayerDataConfig.set("45.1.nameColour", "&6&l");
       this.PlayerDataConfig.set("45.1.prefix", "random");
       this.PlayerDataConfig.set("45.1.suffix", "random");
       this.PlayerDataConfig.set("45.1.properties.armour", "&b4-8");
       this.PlayerDataConfig.set("45.1.properties.health", "&b30-player+60");
       this.PlayerDataConfig.set("45.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.healthRegen", "&b1-3");
       this.PlayerDataConfig.set("45.1.properties.fireRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.fire", "&42-4");
       this.PlayerDataConfig.set("45.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.movementSpeed", "&e2-3");
       this.PlayerDataConfig.set("45.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("45.1.properties.durability", "&7800-1150");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default magma_cube file! ***********");
     }
   }
   
   public void writeSilverfish()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "silverfish.yml");
       
       this.PlayerDataConfig.set("45", null);
       this.PlayerDataConfig.set("45.1.itemId", Integer.valueOf(299));
       this.PlayerDataConfig.set("45.1.nameColour", "&c&l");
       this.PlayerDataConfig.set("45.1.prefix", "random");
       this.PlayerDataConfig.set("45.1.suffix", "random");
       this.PlayerDataConfig.set("45.1.properties.armour", "&b1-3");
       this.PlayerDataConfig.set("45.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.health", "&b30-player+60");
       this.PlayerDataConfig.set("45.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.healthRegen", "&b1-3");
       this.PlayerDataConfig.set("45.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.movementSpeed", "&e2-5");
       this.PlayerDataConfig.set("45.1.properties.durability", "&7500-950");
       this.PlayerDataConfig.set("45.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("45.1.properties.xpLevel", "&b3-player+2");
       
       this.PlayerDataConfig.set("20", null);
       this.PlayerDataConfig.set("20.1.itemId", Integer.valueOf(301));
       this.PlayerDataConfig.set("20.1.nameColour", "&c&l");
       this.PlayerDataConfig.set("20.1.prefix", "random");
       this.PlayerDataConfig.set("20.1.suffix", "random");
       this.PlayerDataConfig.set("20.1.properties.armour", "&b2-4");
       this.PlayerDataConfig.set("20.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("20.1.properties.health", "&b50-player+30");
       this.PlayerDataConfig.set("20.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("20.1.properties.healthRegen", "&b1-1");
       this.PlayerDataConfig.set("20.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("20.1.properties.movementSpeed", "&e4-7");
       this.PlayerDataConfig.set("20.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("20.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("20.1.properties.durability", "&7600-1100");
       this.PlayerDataConfig.set("20.1.properties.droppedXp", "3");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default silverfish file! ***********");
     }
   }
   
   public void writeSkeleton()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "skeleton.yml");
       
 
       this.PlayerDataConfig.set("10", null);
       this.PlayerDataConfig.set("10.1.itemId", Integer.valueOf(314));
       this.PlayerDataConfig.set("10.1.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.1.prefix", "random");
       this.PlayerDataConfig.set("10.1.suffix", "random");
       this.PlayerDataConfig.set("10.1.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.1.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.2.itemId", Integer.valueOf(315));
       this.PlayerDataConfig.set("10.2.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.2.prefix", "random");
       this.PlayerDataConfig.set("10.2.suffix", "random");
       this.PlayerDataConfig.set("10.2.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.2.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.3.itemId", Integer.valueOf(316));
       this.PlayerDataConfig.set("10.3.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.3.prefix", "random");
       this.PlayerDataConfig.set("10.3.suffix", "random");
       this.PlayerDataConfig.set("10.3.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.3.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.4.itemId", Integer.valueOf(317));
       this.PlayerDataConfig.set("10.4.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.4.prefix", "random");
       this.PlayerDataConfig.set("10.4.suffix", "random");
       this.PlayerDataConfig.set("10.4.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.4.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("10.5.itemId", Integer.valueOf(283));
       this.PlayerDataConfig.set("10.5.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.5.prefix", "random");
       this.PlayerDataConfig.set("10.5.suffix", "random");
       this.PlayerDataConfig.set("10.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.5.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.5.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.5.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.6.itemId", Integer.valueOf(286));
       this.PlayerDataConfig.set("10.6.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.6.prefix", "random");
       this.PlayerDataConfig.set("10.6.suffix", "random");
       this.PlayerDataConfig.set("10.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.6.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.6.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.6.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.7.itemId", Integer.valueOf(285));
       this.PlayerDataConfig.set("10.7.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.7.prefix", "random");
       this.PlayerDataConfig.set("10.7.suffix", "random");
       this.PlayerDataConfig.set("10.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.7.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.7.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.7.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("10.8.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.8.prefix", "random");
       this.PlayerDataConfig.set("10.8.suffix", "random");
       this.PlayerDataConfig.set("10.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.8.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.8.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.8.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default skeleton file! ***********");
     }
   }
   
   public void writeWitherSkeleton()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither_skeleton.yml");
       
 
       this.PlayerDataConfig.set("10", null);
       this.PlayerDataConfig.set("10.1.itemId", Integer.valueOf(314));
       this.PlayerDataConfig.set("10.1.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.1.prefix", "random");
       this.PlayerDataConfig.set("10.1.suffix", "random");
       this.PlayerDataConfig.set("10.1.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.1.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.2.itemId", Integer.valueOf(315));
       this.PlayerDataConfig.set("10.2.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.2.prefix", "random");
       this.PlayerDataConfig.set("10.2.suffix", "random");
       this.PlayerDataConfig.set("10.2.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.2.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.3.itemId", Integer.valueOf(316));
       this.PlayerDataConfig.set("10.3.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.3.prefix", "random");
       this.PlayerDataConfig.set("10.3.suffix", "random");
       this.PlayerDataConfig.set("10.3.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.3.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.4.itemId", Integer.valueOf(317));
       this.PlayerDataConfig.set("10.4.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.4.prefix", "random");
       this.PlayerDataConfig.set("10.4.suffix", "random");
       this.PlayerDataConfig.set("10.4.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.4.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("10.5.itemId", Integer.valueOf(283));
       this.PlayerDataConfig.set("10.5.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.5.prefix", "random");
       this.PlayerDataConfig.set("10.5.suffix", "random");
       this.PlayerDataConfig.set("10.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.5.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.5.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.5.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.6.itemId", Integer.valueOf(286));
       this.PlayerDataConfig.set("10.6.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.6.prefix", "random");
       this.PlayerDataConfig.set("10.6.suffix", "random");
       this.PlayerDataConfig.set("10.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.6.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.6.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.6.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.7.itemId", Integer.valueOf(285));
       this.PlayerDataConfig.set("10.7.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.7.prefix", "random");
       this.PlayerDataConfig.set("10.7.suffix", "random");
       this.PlayerDataConfig.set("10.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.7.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.7.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.7.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("10.8.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.8.prefix", "random");
       this.PlayerDataConfig.set("10.8.suffix", "random");
       this.PlayerDataConfig.set("10.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.8.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.lifeSteal", "&22-6");
       this.PlayerDataConfig.set("10.8.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.8.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default wither_skeleton file! ***********");
     }
   }
   
   public void writeSlime()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "slime.yml");
       
       this.PlayerDataConfig.set("30", null);
       this.PlayerDataConfig.set("30.1.itemId", Integer.valueOf(275));
       this.PlayerDataConfig.set("30.1.nameColour", "&a&l");
       this.PlayerDataConfig.set("30.1.prefix", "random");
       this.PlayerDataConfig.set("30.1.suffix", "random");
       this.PlayerDataConfig.set("30.1.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("30.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("30.1.properties.critChance", "&b1-2");
       this.PlayerDataConfig.set("30.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("30.1.properties.critDamage", "&b3-8");
       this.PlayerDataConfig.set("30.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("30.1.properties.lifeSteal", "&21-2");
       this.PlayerDataConfig.set("30.1.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("30.1.properties.poison", "&a3-4");
       this.PlayerDataConfig.set("30.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("30.1.properties.movementSpeed", "&e1-3");
       this.PlayerDataConfig.set("30.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("30.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("30.1.properties.durability", "&7250-550");
       
       this.PlayerDataConfig.set("15", null);
       this.PlayerDataConfig.set("15.1.itemId", Integer.valueOf(272));
       this.PlayerDataConfig.set("15.1.nameColour", "&a&l");
       this.PlayerDataConfig.set("15.1.prefix", "random");
       this.PlayerDataConfig.set("15.1.suffix", "random");
       this.PlayerDataConfig.set("15.1.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.critChance", "&b1-3");
       this.PlayerDataConfig.set("15.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.critDamage", "&b3-6");
       this.PlayerDataConfig.set("15.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.lifeSteal", "&21-3");
       this.PlayerDataConfig.set("15.1.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.poison", "&a1-6");
       this.PlayerDataConfig.set("15.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("15.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("15.1.properties.durability", "&7400-600");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default slime file! ***********");
     }
   }
   
   public void writeSpider()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "spider.yml");
       
 
       this.PlayerDataConfig.set("15", null);
       this.PlayerDataConfig.set("15.1.itemId", Integer.valueOf(298));
       this.PlayerDataConfig.set("15.1.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.1.prefix", "random");
       this.PlayerDataConfig.set("15.1.suffix", "random");
       this.PlayerDataConfig.set("15.1.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.1.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.2.itemId", Integer.valueOf(299));
       this.PlayerDataConfig.set("15.2.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.2.prefix", "random");
       this.PlayerDataConfig.set("15.2.suffix", "random");
       this.PlayerDataConfig.set("15.2.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.2.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.3.itemId", Integer.valueOf(300));
       this.PlayerDataConfig.set("15.3.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.3.prefix", "random");
       this.PlayerDataConfig.set("15.3.suffix", "random");
       this.PlayerDataConfig.set("15.3.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.3.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.3.itemId", Integer.valueOf(301));
       this.PlayerDataConfig.set("15.3.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.3.prefix", "random");
       this.PlayerDataConfig.set("15.3.suffix", "random");
       this.PlayerDataConfig.set("15.3.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.3.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("15.4.itemId", Integer.valueOf(272));
       this.PlayerDataConfig.set("15.4.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.4.prefix", "random");
       this.PlayerDataConfig.set("15.4.suffix", "random");
       this.PlayerDataConfig.set("15.4.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.4.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.4.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.lifeSteal", "&21-5");
       this.PlayerDataConfig.set("15.4.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.ice", "&b2-6");
       this.PlayerDataConfig.set("15.4.properties.durability", "&7300-425");
       this.PlayerDataConfig.set("15.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevel", "&b8-player+5");
       
       this.PlayerDataConfig.set("15.5.itemId", Integer.valueOf(274));
       this.PlayerDataConfig.set("15.5.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.5.prefix", "random");
       this.PlayerDataConfig.set("15.5.suffix", "random");
       this.PlayerDataConfig.set("15.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.5.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.lifeSteal", "&21-5");
       this.PlayerDataConfig.set("15.5.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.ice", "&b2-6");
       this.PlayerDataConfig.set("15.5.properties.durability", "&7300-425");
       this.PlayerDataConfig.set("15.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevel", "&b8-player+5");
       
       this.PlayerDataConfig.set("15.6.itemId", Integer.valueOf(275));
       this.PlayerDataConfig.set("15.6.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.6.prefix", "random");
       this.PlayerDataConfig.set("15.6.suffix", "random");
       this.PlayerDataConfig.set("15.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.6.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.lifeSteal", "&21-5");
       this.PlayerDataConfig.set("15.6.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.ice", "&b2-6");
       this.PlayerDataConfig.set("15.6.properties.durability", "&7300-425");
       this.PlayerDataConfig.set("15.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevel", "&b8-player+5");
       
       this.PlayerDataConfig.set("15.7.itemId", Integer.valueOf(291));
       this.PlayerDataConfig.set("15.7.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.7.prefix", "random");
       this.PlayerDataConfig.set("15.7.suffix", "random");
       this.PlayerDataConfig.set("15.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.7.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.lifeSteal", "&21-5");
       this.PlayerDataConfig.set("15.7.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.ice", "&b2-6");
       this.PlayerDataConfig.set("15.7.properties.durability", "&7300-425");
       this.PlayerDataConfig.set("15.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevel", "&b8-player+5");
       
       this.PlayerDataConfig.set("15.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("15.8.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.8.prefix", "random");
       this.PlayerDataConfig.set("15.8.suffix", "random");
       this.PlayerDataConfig.set("15.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.8.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.lifeSteal", "&21-5");
       this.PlayerDataConfig.set("15.8.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.ice", "&b2-6");
       this.PlayerDataConfig.set("15.8.properties.durability", "&7300-425");
       this.PlayerDataConfig.set("15.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevel", "&b8-player+5");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default spider file! ***********");
     }
   }
   
   public void writeWitch()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "witch.yml");
       
 
       this.PlayerDataConfig.set("10", null);
       this.PlayerDataConfig.set("10.1.itemId", Integer.valueOf(314));
       this.PlayerDataConfig.set("10.1.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.1.prefix", "random");
       this.PlayerDataConfig.set("10.1.suffix", "random");
       this.PlayerDataConfig.set("10.1.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.1.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.block", "&b1-3");
       this.PlayerDataConfig.set("10.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("10.1.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.2.itemId", Integer.valueOf(315));
       this.PlayerDataConfig.set("10.2.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.2.prefix", "random");
       this.PlayerDataConfig.set("10.2.suffix", "random");
       this.PlayerDataConfig.set("10.2.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.2.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.block", "&b1-3");
       this.PlayerDataConfig.set("10.2.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("10.2.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.3.itemId", Integer.valueOf(316));
       this.PlayerDataConfig.set("10.3.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.3.prefix", "random");
       this.PlayerDataConfig.set("10.3.suffix", "random");
       this.PlayerDataConfig.set("10.3.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.3.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.block", "&b1-3");
       this.PlayerDataConfig.set("10.3.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("10.3.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("10.4.itemId", Integer.valueOf(317));
       this.PlayerDataConfig.set("10.4.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.4.prefix", "random");
       this.PlayerDataConfig.set("10.4.suffix", "random");
       this.PlayerDataConfig.set("10.4.properties.armour", "&b6-12");
       this.PlayerDataConfig.set("10.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.health", "&b20-player+30");
       this.PlayerDataConfig.set("10.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.healthRegen", "&b2-5");
       this.PlayerDataConfig.set("10.4.properties.blockRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.block", "&b1-3");
       this.PlayerDataConfig.set("10.4.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("10.4.properties.durability", "&72300-2600");
       this.PlayerDataConfig.set("10.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("10.5.itemId", Integer.valueOf(283));
       this.PlayerDataConfig.set("10.5.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.5.prefix", "random");
       this.PlayerDataConfig.set("10.5.suffix", "random");
       this.PlayerDataConfig.set("10.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.5.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.wither", "&72-6");
       this.PlayerDataConfig.set("10.5.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.5.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.5.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.6.itemId", Integer.valueOf(286));
       this.PlayerDataConfig.set("10.6.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.6.prefix", "random");
       this.PlayerDataConfig.set("10.6.suffix", "random");
       this.PlayerDataConfig.set("10.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.6.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.wither", "&72-6");
       this.PlayerDataConfig.set("10.6.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.6.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.6.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.7.itemId", Integer.valueOf(285));
       this.PlayerDataConfig.set("10.7.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.7.prefix", "random");
       this.PlayerDataConfig.set("10.7.suffix", "random");
       this.PlayerDataConfig.set("10.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.7.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.wither", "&72-6");
       this.PlayerDataConfig.set("10.7.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.7.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.7.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.set("10.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("10.8.nameColour", "&a&l");
       this.PlayerDataConfig.set("10.8.prefix", "random");
       this.PlayerDataConfig.set("10.8.suffix", "random");
       this.PlayerDataConfig.set("10.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("10.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("10.8.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.wither", "&72-6");
       this.PlayerDataConfig.set("10.8.properties.iceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.ice", "&b1-5");
       this.PlayerDataConfig.set("10.8.properties.durability", "&7400-525");
       this.PlayerDataConfig.set("10.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("10.8.properties.xpLevel", "&b6-player+7");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default witch file! ***********");
     }
   }
   
   public void writeWither()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither.yml");
       
       this.PlayerDataConfig.set("100", null);
       this.PlayerDataConfig.set("100.1.itemId", Integer.valueOf(279));
       this.PlayerDataConfig.set("100.1.nameColour", "&9&l");
       this.PlayerDataConfig.set("100.1.prefix", "random");
       this.PlayerDataConfig.set("100.1.suffix", "random");
       this.PlayerDataConfig.set("100.1.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("100.1.properties.critChanceRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.critChance", "&b8-14");
       this.PlayerDataConfig.set("100.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.critDamage", "&b14-23");
       this.PlayerDataConfig.set("100.1.properties.critDamageRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.critDamage", "&b3-9");
       this.PlayerDataConfig.set("100.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.health", "&b35-player+85");
       this.PlayerDataConfig.set("100.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.healthRegen", "&b3-5");
       this.PlayerDataConfig.set("100.1.properties.lifeStealRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.lifeSteal", "&22-5");
       this.PlayerDataConfig.set("100.1.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.poison", "&a3-5");
       this.PlayerDataConfig.set("100.1.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.wither", "&72-6");
       this.PlayerDataConfig.set("100.1.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.wither", "&72-6");
       this.PlayerDataConfig.set("100.1.properties.movementSpeedRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.movementSpeed", "&e2-4");
       this.PlayerDataConfig.set("100.1.properties.durability", "&71500-1950");
       this.PlayerDataConfig.set("100.1.properties.droppedXp", "75");
       this.PlayerDataConfig.set("100.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.xpLevel", "&b3-player+2");
       this.PlayerDataConfig.set("100.1.properties.tntRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("100.1.properties.spells.tnt", Boolean.valueOf(true));
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default wither file! ***********");
     }
   }
   
   public void writeZombie()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "zombie.yml");
       
 
       this.PlayerDataConfig.set("15", null);
       this.PlayerDataConfig.set("15.1.itemId", Integer.valueOf(298));
       this.PlayerDataConfig.set("15.1.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.1.prefix", "random");
       this.PlayerDataConfig.set("15.1.suffix", "random");
       this.PlayerDataConfig.set("15.1.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.1.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.class", "random");
       this.PlayerDataConfig.set("15.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.2.itemId", Integer.valueOf(299));
       this.PlayerDataConfig.set("15.2.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.2.prefix", "random");
       this.PlayerDataConfig.set("15.2.suffix", "random");
       this.PlayerDataConfig.set("15.2.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.2.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.class", "random");
       this.PlayerDataConfig.set("15.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.3.itemId", Integer.valueOf(300));
       this.PlayerDataConfig.set("15.3.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.3.prefix", "random");
       this.PlayerDataConfig.set("15.3.suffix", "random");
       this.PlayerDataConfig.set("15.3.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.3.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.class", "random");
       this.PlayerDataConfig.set("15.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.4.itemId", Integer.valueOf(301));
       this.PlayerDataConfig.set("15.4.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.4.prefix", "random");
       this.PlayerDataConfig.set("15.4.suffix", "random");
       this.PlayerDataConfig.set("15.4.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.4.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.class", "random");
       this.PlayerDataConfig.set("15.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("15.5.itemId", Integer.valueOf(268));
       this.PlayerDataConfig.set("15.5.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.5.prefix", "random");
       this.PlayerDataConfig.set("15.5.suffix", "random");
       this.PlayerDataConfig.set("15.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.5.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.5.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.5.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.6.itemId", Integer.valueOf(271));
       this.PlayerDataConfig.set("15.6.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.6.prefix", "random");
       this.PlayerDataConfig.set("15.6.suffix", "random");
       this.PlayerDataConfig.set("15.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.6.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.6.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.6.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.7.itemId", Integer.valueOf(270));
       this.PlayerDataConfig.set("15.7.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.7.prefix", "random");
       this.PlayerDataConfig.set("15.7.suffix", "random");
       this.PlayerDataConfig.set("15.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.7.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.7.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.7.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("15.8.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.8.prefix", "random");
       this.PlayerDataConfig.set("15.8.suffix", "random");
       this.PlayerDataConfig.set("15.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.8.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.8.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.8.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default zombie file! ***********");
     }
   }
   
   public void writeBabyZombie()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "baby_zombie.yml");
       
 
       this.PlayerDataConfig.set("15", null);
       this.PlayerDataConfig.set("15.1.itemId", Integer.valueOf(298));
       this.PlayerDataConfig.set("15.1.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.1.prefix", "random");
       this.PlayerDataConfig.set("15.1.suffix", "random");
       this.PlayerDataConfig.set("15.1.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.1.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.class", "random");
       this.PlayerDataConfig.set("15.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.2.itemId", Integer.valueOf(299));
       this.PlayerDataConfig.set("15.2.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.2.prefix", "random");
       this.PlayerDataConfig.set("15.2.suffix", "random");
       this.PlayerDataConfig.set("15.2.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.2.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.class", "random");
       this.PlayerDataConfig.set("15.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.3.itemId", Integer.valueOf(300));
       this.PlayerDataConfig.set("15.3.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.3.prefix", "random");
       this.PlayerDataConfig.set("15.3.suffix", "random");
       this.PlayerDataConfig.set("15.3.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.3.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.class", "random");
       this.PlayerDataConfig.set("15.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.4.itemId", Integer.valueOf(301));
       this.PlayerDataConfig.set("15.4.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.4.prefix", "random");
       this.PlayerDataConfig.set("15.4.suffix", "random");
       this.PlayerDataConfig.set("15.4.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.4.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.class", "random");
       this.PlayerDataConfig.set("15.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("15.5.itemId", Integer.valueOf(268));
       this.PlayerDataConfig.set("15.5.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.5.prefix", "random");
       this.PlayerDataConfig.set("15.5.suffix", "random");
       this.PlayerDataConfig.set("15.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.5.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.5.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.5.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.6.itemId", Integer.valueOf(271));
       this.PlayerDataConfig.set("15.6.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.6.prefix", "random");
       this.PlayerDataConfig.set("15.6.suffix", "random");
       this.PlayerDataConfig.set("15.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.6.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.6.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.6.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.7.itemId", Integer.valueOf(270));
       this.PlayerDataConfig.set("15.7.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.7.prefix", "random");
       this.PlayerDataConfig.set("15.7.suffix", "random");
       this.PlayerDataConfig.set("15.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.7.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.7.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.7.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("15.8.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.8.prefix", "random");
       this.PlayerDataConfig.set("15.8.suffix", "random");
       this.PlayerDataConfig.set("15.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.8.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.8.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.8.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default baby_zombie file! ***********");
     }
   }
   
   public void writeVillagerZombie()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "villager_zombie.yml");
       
 
       this.PlayerDataConfig.set("15", null);
       this.PlayerDataConfig.set("15.1.itemId", Integer.valueOf(298));
       this.PlayerDataConfig.set("15.1.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.1.prefix", "random");
       this.PlayerDataConfig.set("15.1.suffix", "random");
       this.PlayerDataConfig.set("15.1.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.1.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.class", "random");
       this.PlayerDataConfig.set("15.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.2.itemId", Integer.valueOf(299));
       this.PlayerDataConfig.set("15.2.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.2.prefix", "random");
       this.PlayerDataConfig.set("15.2.suffix", "random");
       this.PlayerDataConfig.set("15.2.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.2.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.class", "random");
       this.PlayerDataConfig.set("15.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.3.itemId", Integer.valueOf(300));
       this.PlayerDataConfig.set("15.3.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.3.prefix", "random");
       this.PlayerDataConfig.set("15.3.suffix", "random");
       this.PlayerDataConfig.set("15.3.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.3.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.class", "random");
       this.PlayerDataConfig.set("15.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.4.itemId", Integer.valueOf(301));
       this.PlayerDataConfig.set("15.4.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.4.prefix", "random");
       this.PlayerDataConfig.set("15.4.suffix", "random");
       this.PlayerDataConfig.set("15.4.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.4.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.classRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.class", "random");
       this.PlayerDataConfig.set("15.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("15.5.itemId", Integer.valueOf(268));
       this.PlayerDataConfig.set("15.5.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.5.prefix", "random");
       this.PlayerDataConfig.set("15.5.suffix", "random");
       this.PlayerDataConfig.set("15.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.5.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.5.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.5.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.6.itemId", Integer.valueOf(271));
       this.PlayerDataConfig.set("15.6.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.6.prefix", "random");
       this.PlayerDataConfig.set("15.6.suffix", "random");
       this.PlayerDataConfig.set("15.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.6.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.6.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.6.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.7.itemId", Integer.valueOf(270));
       this.PlayerDataConfig.set("15.7.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.7.prefix", "random");
       this.PlayerDataConfig.set("15.7.suffix", "random");
       this.PlayerDataConfig.set("15.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.7.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.7.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.7.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("15.8.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.8.prefix", "random");
       this.PlayerDataConfig.set("15.8.suffix", "random");
       this.PlayerDataConfig.set("15.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.8.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.8.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.8.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default villager_zombie file! ***********");
     }
   }
   
   public void writePig_Zombie()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "pig_zombie.yml");
       
 
       this.PlayerDataConfig.set("15", null);
       this.PlayerDataConfig.set("15.1.itemId", Integer.valueOf(298));
       this.PlayerDataConfig.set("15.1.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.1.prefix", "random");
       this.PlayerDataConfig.set("15.1.suffix", "random");
       this.PlayerDataConfig.set("15.1.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.1.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.1.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.1.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.1.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.1.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.2.itemId", Integer.valueOf(299));
       this.PlayerDataConfig.set("15.2.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.2.prefix", "random");
       this.PlayerDataConfig.set("15.2.suffix", "random");
       this.PlayerDataConfig.set("15.2.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.2.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.2.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.2.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.2.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.2.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.3.itemId", Integer.valueOf(300));
       this.PlayerDataConfig.set("15.3.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.3.prefix", "random");
       this.PlayerDataConfig.set("15.3.suffix", "random");
       this.PlayerDataConfig.set("15.3.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.3.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.3.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.3.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.3.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.3.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.4.itemId", Integer.valueOf(301));
       this.PlayerDataConfig.set("15.4.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.4.prefix", "random");
       this.PlayerDataConfig.set("15.4.suffix", "random");
       this.PlayerDataConfig.set("15.4.properties.armour", "&b3-7");
       this.PlayerDataConfig.set("15.4.properties.healthRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.health", "&b10-player+30");
       this.PlayerDataConfig.set("15.4.properties.healthRegenRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.healthRegen", "&b1-4");
       this.PlayerDataConfig.set("15.4.properties.durability", "&71350-1500");
       this.PlayerDataConfig.set("15.4.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.4.properties.xpLevel", "&b10-player+3");
       
 
       this.PlayerDataConfig.set("15.5.itemId", Integer.valueOf(268));
       this.PlayerDataConfig.set("15.5.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.5.prefix", "random");
       this.PlayerDataConfig.set("15.5.suffix", "random");
       this.PlayerDataConfig.set("15.5.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.5.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.5.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.5.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.5.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.5.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.5.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.6.itemId", Integer.valueOf(271));
       this.PlayerDataConfig.set("15.6.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.6.prefix", "random");
       this.PlayerDataConfig.set("15.6.suffix", "random");
       this.PlayerDataConfig.set("15.6.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.6.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.6.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.6.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.6.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.6.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.6.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.7.itemId", Integer.valueOf(270));
       this.PlayerDataConfig.set("15.7.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.7.prefix", "random");
       this.PlayerDataConfig.set("15.7.suffix", "random");
       this.PlayerDataConfig.set("15.7.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.7.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.7.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.7.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.7.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.7.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.7.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.set("15.8.itemId", Integer.valueOf(261));
       this.PlayerDataConfig.set("15.8.nameColour", "&f&l");
       this.PlayerDataConfig.set("15.8.prefix", "random");
       this.PlayerDataConfig.set("15.8.suffix", "random");
       this.PlayerDataConfig.set("15.8.properties.weaponspeed", "&4Slow");
       this.PlayerDataConfig.set("15.8.properties.damage", "&b1-6-player+1-7");
       this.PlayerDataConfig.set("15.8.properties.poisonRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.poison", "&a1-5");
       this.PlayerDataConfig.set("15.8.properties.witherRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.wither", "&71-5");
       this.PlayerDataConfig.set("15.8.properties.durability", "&7200-325");
       this.PlayerDataConfig.set("15.8.properties.soulboundRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.soulbound", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevelRandomApply", Boolean.valueOf(true));
       this.PlayerDataConfig.set("15.8.properties.xpLevel", "&b10-player+3");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default pig_zombie file! ***********");
     }
   }
   
   public void writeDefaultSpell(String fileName, String projType, int projVelocity, String projHitEff, int cooldown, int dHA, int aHA, int aHR, int dDA, int aDA, int aDR)
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + fileName + ".yml");
       
       this.PlayerDataConfig.set("projectile-type", projType);
       this.PlayerDataConfig.set("projectile-velocity", Integer.valueOf(projVelocity));
       this.PlayerDataConfig.set("projectile-hit-effect", projHitEff);
       this.PlayerDataConfig.set("cooldown", Integer.valueOf(cooldown));
       this.PlayerDataConfig.set("heal.direct-heal-amount", Integer.valueOf(dHA));
       this.PlayerDataConfig.set("heal.aoe-heal-amount", Integer.valueOf(aHA));
       this.PlayerDataConfig.set("heal.aoe-heal-range", Integer.valueOf(aHR));
       this.PlayerDataConfig.set("damage.direct-damage-amount", Integer.valueOf(dDA));
       this.PlayerDataConfig.set("damage.aoe-damage-amount", Integer.valueOf(aDA));
       this.PlayerDataConfig.set("damage.aoe-damage-range", Integer.valueOf(aDR));
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       ItemLoreStats.plugin.getLogger().log(Level.WARNING, "Failed to save the default spell config for " + fileName + ".");
     }
   }
   
   public void writeSetBonuses()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + "SetBonuses.yml");
       
       this.PlayerDataConfig.set("Sets", null);
       
 
 
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default SetBonuses file! ***********");
     }
   }
   
   public void writeLanguageEN()
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataFile = new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "language-en.yml");
       
       //this.PlayerDataConfig.set("FileVersion", Integer.valueOf(Integer.parseInt(String.valueOf(formatVersion(Double.parseDouble(ItemLoreStats.plugin.getDescription().getVersion())).replace(".", "")))));
       
       this.PlayerDataConfig.set("ErrorMessages", null);
       this.PlayerDataConfig.set("ErrorMessages.PermissionDeniedError", "&cYou do not have permission to perform that command.");
       this.PlayerDataConfig.set("ErrorMessages.IngameOnlyError", "You can only run that command in-game!");
       this.PlayerDataConfig.set("ErrorMessages.DoesntExistError", "&4{value1} &cdoesn't exist!");
       this.PlayerDataConfig.set("ErrorMessages.PlayerNotOnlineError", "&4{value1} &cis not online!");
       this.PlayerDataConfig.set("ErrorMessages.NotEnoughSpaceError", "&4{value1} &cdoes not have enough space in their inventory.");
       this.PlayerDataConfig.set("ErrorMessages.IncludeItemNameError", "&cYou need to include an item name!. For example, /ils name &4Hand of God");
       this.PlayerDataConfig.set("ErrorMessages.EnterPlayerNameError", "&cYou need to enter a players name!");
       this.PlayerDataConfig.set("ErrorMessages.ItemAlreadyExistsError", "&cThat item already exists. Please choose a different name for the item.");
       this.PlayerDataConfig.set("ErrorMessages.NullItemInHandError", "&cYou need to be holding an item to use that command.");
       this.PlayerDataConfig.set("ErrorMessages.NoLoreError", "&cThat item doesn't contain any lore.");
       this.PlayerDataConfig.set("ErrorMessages.EnterTypeError", "&cYou need to enter a type &4Armour&c, &4Hand &cor &4All.");
       this.PlayerDataConfig.set("ErrorMessages.ShiftClickDisabled", "&cShift-clicking armour is currently disabled.");
       this.PlayerDataConfig.set("ErrorMessages.InventoryFull", "&4Dropping the item as your inventory is full.");
       this.PlayerDataConfig.set("ErrorMessages.AddedToConfig", "&2{value1} &ahas been added to the config.");
       
       this.PlayerDataConfig.set("CustomItemMessages", null);
       this.PlayerDataConfig.set("CustomItemMessages.HoldCustomItem", "&cYou need to hold the item you want to add to the config.");
       this.PlayerDataConfig.set("CustomItemMessages.CustomEquipmentType", "&cYou need to enter the equipment type of the item. Either &4tool &cor &4armour&c.");
       this.PlayerDataConfig.set("CustomItemMessages.CustomItemType", "&cYou need to enter the type of item. E.g &4Diamond Sword &cor &4Gold Chestplate&c.");
       this.PlayerDataConfig.set("CustomItemMessages.CustomArmourType", "&cYou need to enter the type of Armour as shown; &4helmet&c, &4chest&c, &4legs&c, &4boots&c.");
       
       this.PlayerDataConfig.set("ClassRequirementMessages", null);
       this.PlayerDataConfig.set("ClassRequirementMessages.NotRequiredClass", "&cYou are not the required class to equip that item.");
       this.PlayerDataConfig.set("ClassRequirementMessages.NotRequiredClassForItemInHand", "&cYou are not the required class to hold that item.");
       this.PlayerDataConfig.set("ClassRequirementMessages.InventoryFullOnPickup", "&4Your inventory is full and you're not the required class to hold that item.");
       
       this.PlayerDataConfig.set("LevelRequirementMessages", null);
       this.PlayerDataConfig.set("LevelRequirementMessages.LevelTooLow", "&cYou are not the required level to equip that item.");
       this.PlayerDataConfig.set("LevelRequirementMessages.LevelTooLowForItemInHand", "&cYou are not the required level to hold that item.");
       this.PlayerDataConfig.set("LevelRequirementMessages.InventoryFullOnPickup", "&4Your inventory is full and you're not the required level to hold that item.");
       
       this.PlayerDataConfig.set("SoulboundMessages", null);
       this.PlayerDataConfig.set("SoulboundMessages.BoundToSomeoneElse", "&cThis item is bound to someone else and you're unable to equip it.");
       this.PlayerDataConfig.set("SoulboundMessages.BoundToSomeoneElseForItemInHand", "&cThis item is bound to someone else and you're unable to hold it.");
       this.PlayerDataConfig.set("SoulboundMessages.InventoryFullOnPickup", "&4Your inventory is full and that item is bound to someone else.");
       
       this.PlayerDataConfig.set("DamageMessages", null);
       this.PlayerDataConfig.set("DamageMessages.DamageDone", "      &dYou hit &r{defender} &dfor &6{value2} &ddamage.");
       this.PlayerDataConfig.set("DamageMessages.DamageDoneWithoutVowel", "      &dYou hit a &r{value1} &dfor &6{value2} &ddamage.");
       this.PlayerDataConfig.set("DamageMessages.DamageDoneWithVowel", "      &dYou hit an &r{value1} &dfor &6{value2} &ddamage.");
       this.PlayerDataConfig.set("DamageMessages.DamageTaken", "      &r{attacker} &dhit you for &6{value2} &ddamage.");
       this.PlayerDataConfig.set("DamageMessages.CriticalStrikeSuccess", "&aCritical Strike!");
       this.PlayerDataConfig.set("DamageMessages.EnemyCriticalStrikeSuccess", "&4{attacker} &ccrit you!");
       this.PlayerDataConfig.set("DamageMessages.DodgeSuccess", "&aDodge!");
       this.PlayerDataConfig.set("DamageMessages.EnemyDodgeSuccess", "&4{defender} &cdodged your attack!");
       this.PlayerDataConfig.set("DamageMessages.BlockSuccess", "&aBlocked!");
       this.PlayerDataConfig.set("DamageMessages.EnemyBlockSuccess", "&4{defender} &cblocked your attack!");
       this.PlayerDataConfig.set("DamageMessages.LifeStealSuccess", "&2{value1} &ahealth stolen from &2{defender}&a!");
       this.PlayerDataConfig.set("DamageMessages.EnemyLifeStealSuccess", "&4{attacker} &cstole &4{value1} &cof your health!");
       this.PlayerDataConfig.set("DamageMessages.ReflectSuccess", "&aReflected!");
       this.PlayerDataConfig.set("DamageMessages.EnemyReflectSuccess", "&4{defender} &creflected your attack!");
       this.PlayerDataConfig.set("DamageMessages.FireSuccess", "&2{defender} &aset alight!");
       this.PlayerDataConfig.set("DamageMessages.EnemyFireSuccess", "&4{attacker} &cset you on fire!");
       this.PlayerDataConfig.set("DamageMessages.IceSuccess", "&2{defender} &aslowed!");
       this.PlayerDataConfig.set("DamageMessages.EnemyIceSuccess", "&4{attacker} &cslowed you!");
       this.PlayerDataConfig.set("DamageMessages.PoisonSuccess", "&2{defender} &apoisoned!");
       this.PlayerDataConfig.set("DamageMessages.EnemyPoisonSuccess", "&4{attacker} &cpoisoned you!");
       this.PlayerDataConfig.set("DamageMessages.WitherSuccess", "&2{defender} &awithered!");
       this.PlayerDataConfig.set("DamageMessages.EnemyWitherSuccess", "&4{attacker} &cwithered you!");
       this.PlayerDataConfig.set("DamageMessages.HarmSuccess", "&2{defender} &aharmed!");
       this.PlayerDataConfig.set("DamageMessages.EnemyHarmSuccess", "&4{attacker} &charmed you!");
       this.PlayerDataConfig.set("DamageMessages.BlindSuccess", "&2{defender} &ablinded!");
       this.PlayerDataConfig.set("DamageMessages.EnemyBlindSuccess", "&4{attacker} &cblinded you!");
       
       this.PlayerDataConfig.set("SpellMessages", null);
       this.PlayerDataConfig.set("SpellMessages.CastSpell.ItemInHand", "&eRight-click to cast");
       this.PlayerDataConfig.set("SpellMessages.CastSpell.CooldownRemaining", "&6{value1} &dseconds remaining.");
       this.PlayerDataConfig.set("SpellMessages.CastSpell.Heal", "&2{attacker} &ahealed you for &2{value1} &aHealth.");
       this.PlayerDataConfig.set("SpellMessages.CastSpell.Damaged", "      &dYou hit &r{defender} &dfor &6{value2} &ddamage.");
       
       this.PlayerDataConfig.set("RepairMessages", null);
       this.PlayerDataConfig.set("RepairMessages.RepairSuccessfulMaterial", "&aSuccessfully repaired &2{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.RepairSuccessfulCurrency", "&aSuccessfully repaired &2{value1} &afor &2{value2} Dollars&a.");
       this.PlayerDataConfig.set("RepairMessages.DoesntNeedRepair", "&4{value1} &cdoesn't need repairing.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughMoney", "&cYou need &4{value2} Dollars &cto repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughLeather", "&cNot enough Leather to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughWood", "&cNot enough Wood to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughCobblestone", "&cNot enough Cobblestone to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughIron", "&cNot enough Iron Ingots to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughGold", "&cNot enough Gold Ingots to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughDiamond", "&cNot enough Diamonds to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughSticks", "&cNot enough Sticks to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughFlint", "&cNot enough Flint to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughWood", "&cNot enough Wooden Planks to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughString", "&cNot enough String to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughFishingRod", "&cYou need a Fishing Rod to repair &4{value1}&c.");
       this.PlayerDataConfig.set("RepairMessages.NotEnoughCarrots", "&cYou need a Carrot to repair &4{value1}&c.");
       
       this.PlayerDataConfig.set("UpgradeMessages", null);
       this.PlayerDataConfig.set("UpgradeMessages.UpgradeSuccessful", "&2{value1} &ahas been upgraded.");
       this.PlayerDataConfig.set("UpgradeMessages.UpgradeCapReached", "&2{value1} &ahas reached the cap and can no longer be upgraded.");
       
       this.PlayerDataConfig.set("SellMessages", null);
       this.PlayerDataConfig.set("SellMessages.SellSuccessful", "&aYou sold &2{value1} &afor &2{value2} Dollars&a.");
       this.PlayerDataConfig.set("SellMessages.UnableToSell", "&cYou are unable to sell &2{value1}&c.");
       this.PlayerDataConfig.set("SellMessages.NoItemInHand", "&cYour hand is empty.");
       
       this.PlayerDataConfig.set("ItemType", null);
       this.PlayerDataConfig.set("ItemType.Tool.Sword", "Sword");
       this.PlayerDataConfig.set("ItemType.Tool.Pickaxe", "Pickaxe");
       this.PlayerDataConfig.set("ItemType.Tool.Axe", "Axe");
       this.PlayerDataConfig.set("ItemType.Tool.Spade", "Spade");
       this.PlayerDataConfig.set("ItemType.Tool.Hoe", "Hoe");
       this.PlayerDataConfig.set("ItemType.Tool.Bow", "Bow");
       this.PlayerDataConfig.set("ItemType.Tool.Stick", "Stick");
       this.PlayerDataConfig.set("ItemType.Tool.Blaze_Rod", "Blaze Rod");
       this.PlayerDataConfig.set("ItemType.Tool.String", "String");
       this.PlayerDataConfig.set("ItemType.Armour.Skull_Helmet", "Helmet");
       this.PlayerDataConfig.set("ItemType.Armour.Helmet", "Helmet");
       this.PlayerDataConfig.set("ItemType.Armour.Chestplate", "Chestplate");
       this.PlayerDataConfig.set("ItemType.Armour.Leggings", "Leggings");
       this.PlayerDataConfig.set("ItemType.Armour.Boots", "Boots");
       
       this.PlayerDataConfig.set("Item", null);
       this.PlayerDataConfig.set("Item.Looted", "&bYou looted &6{value1}&b!");
       
       this.PlayerDataConfig.save(this.PlayerDataFile);
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to save default language-EN file! ***********");
     }
   }
 }
