 package net.nifheim.yitan.itemlorestats;
 
 import net.nifheim.yitan.itemlorestats.ItemGeneration.MaterialType;
 import net.nifheim.yitan.itemlorestats.ItemGeneration.Prefix;
 import net.nifheim.yitan.itemlorestats.ItemGeneration.RandomLore;
 import net.nifheim.yitan.itemlorestats.ItemGeneration.Rarity;
 import net.nifheim.yitan.itemlorestats.ItemGeneration.SellValue;
 import net.nifheim.yitan.itemlorestats.ItemGeneration.StatRanges;
 import net.nifheim.yitan.itemlorestats.ItemGeneration.Suffix;
 import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
 import net.nifheim.yitan.itemlorestats.Util.Util_Format;
 import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
 import net.nifheim.yitan.itemlorestats.Util.Util_Material;
 import net.nifheim.yitan.itemlorestats.Util.Util_Random;
 import java.io.File;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Random;
 import java.util.Set;
 import org.bukkit.ChatColor;
 import org.bukkit.Material;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.entity.EntityType;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.entity.Skeleton;
 import org.bukkit.entity.Skeleton.SkeletonType;
 import org.bukkit.entity.Zombie;
 import org.bukkit.event.entity.EntityDeathEvent;
 import org.bukkit.inventory.EntityEquipment;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.bukkit.metadata.MetadataValue;
 
 public class EntityDrops implements org.bukkit.event.Listener
 {
   Util_Colours util_Colours = new Util_Colours();
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   Util_Material util_Material = new Util_Material();
   Util_Random util_Random = new Util_Random();
   Util_Format util_Format = new Util_Format();
   
   Prefix prefix = new Prefix();
   Suffix suffix = new Suffix();
   SellValue sellValueCalc = new SellValue();
   RandomLore randomLore = new RandomLore();
   Rarity rarity = new Rarity();
   MaterialType materialType = new MaterialType();
   StatRanges statRanges = new StatRanges();
   private FileConfiguration PlayerDataConfig;
   
   private int random(int length)
   {
     return new Random().nextInt(length) + 1;
   }
   
   private int randomKeySelection(int length) {
     return new Random().nextInt(length);
   }
   
   private int randomRangeInt(int min, int max) {
     return (int)(min + Math.random() * (max - min));
   }
   
   public boolean dropChance(int setDropChance) {
     if (random(100) <= setDropChance) {
       return true;
     }
     return false;
   }
   
   public String randomClass() {
     List<String> getListClasses = Main.plugin.getConfig().getStringList("bonusStats.class.list");
     String selectClass = (String)getListClasses.get(random(getListClasses.size()) - 1);
     return selectClass;
   }
   
   public double statMultiplier(String statType, String stat)
   {
     if (Main.plugin.getConfig().getDouble(statType + "." + stat + ".statMultiplierOnDrop") > 0.0D) {
       double multiplier = Main.plugin.getConfig().getDouble(statType + "." + stat + ".statMultiplierOnDrop");
       
       return multiplier;
     }
     
     return 0.0D;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public List<String> setLore(Player player, int level, int mobLevel, String entity, String dropChance, Material material)
   {
     String armour = Main.plugin.getConfig().getString("primaryStats.armour.colour") + Main.plugin.getConfig().getString("primaryStats.armour.name");
     String armourNoColour = armour.replaceAll("&([0-9a-f])", "");
     String dodge = Main.plugin.getConfig().getString("secondaryStats.dodge.colour") + Main.plugin.getConfig().getString("secondaryStats.dodge.name");
     String dodgeNoColour = dodge.replaceAll("&([0-9a-f])", "");
     String block = Main.plugin.getConfig().getString("secondaryStats.block.colour") + Main.plugin.getConfig().getString("secondaryStats.block.name");
     String blockNoColour = block.replaceAll("&([0-9a-f])", "");
     String critChance = Main.plugin.getConfig().getString("secondaryStats.critChance.colour") + Main.plugin.getConfig().getString("secondaryStats.critChance.name");
     String critChanceNoColour = critChance.replaceAll("&([0-9a-f])", "");
     String critDamage = Main.plugin.getConfig().getString("secondaryStats.critDamage.colour") + Main.plugin.getConfig().getString("secondaryStats.critDamage.name");
     String critDamageNoColour = critDamage.replaceAll("&([0-9a-f])", "");
     String damage = Main.plugin.getConfig().getString("primaryStats.damage.colour") + Main.plugin.getConfig().getString("primaryStats.damage.name");
     String damageNoColour = damage.replaceAll("&([0-9a-f])", "");
     String health = Main.plugin.getConfig().getString("primaryStats.health.colour") + Main.plugin.getConfig().getString("primaryStats.health.name");
     String healthNoColour = health.replaceAll("&([0-9a-f])", "");
     String healthRegen = Main.plugin.getConfig().getString("primaryStats.healthRegen.colour") + Main.plugin.getConfig().getString("primaryStats.healthRegen.name");
     String healthRegenNoColour = healthRegen.replaceAll("&([0-9a-f])", "");
     String lifeSteal = Main.plugin.getConfig().getString("secondaryStats.lifeSteal.colour") + Main.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
     String lifeStealNoColour = lifeSteal.replaceAll("&([0-9a-f])", "");
     String reflect = Main.plugin.getConfig().getString("secondaryStats.reflect.colour") + Main.plugin.getConfig().getString("secondaryStats.reflect.name");
     String reflectNoColour = reflect.replaceAll("&([0-9a-f])", "");
     String fire = Main.plugin.getConfig().getString("secondaryStats.fire.colour") + Main.plugin.getConfig().getString("secondaryStats.fire.name");
     String fireNoColour = fire.replaceAll("&([0-9a-f])", "");
     String ice = Main.plugin.getConfig().getString("secondaryStats.ice.colour") + Main.plugin.getConfig().getString("secondaryStats.ice.name");
     String iceNoColour = ice.replaceAll("&([0-9a-f])", "");
     String poison = Main.plugin.getConfig().getString("secondaryStats.poison.colour") + Main.plugin.getConfig().getString("secondaryStats.poison.name");
     String poisonNoColour = poison.replaceAll("&([0-9a-f])", "");
     String wither = Main.plugin.getConfig().getString("secondaryStats.wither.colour") + Main.plugin.getConfig().getString("secondaryStats.wither.name");
     String witherNoColour = wither.replaceAll("&([0-9a-f])", "");
     String harming = Main.plugin.getConfig().getString("secondaryStats.harming.colour") + Main.plugin.getConfig().getString("secondaryStats.harming.name");
     String harmingNoColour = harming.replaceAll("&([0-9a-f])", "");
     String blind = Main.plugin.getConfig().getString("secondaryStats.blind.colour") + Main.plugin.getConfig().getString("secondaryStats.blind.name");
     String blindNoColour = blind.replaceAll("&([0-9a-f])", "");
     String movementSpeed = Main.plugin.getConfig().getString("secondaryStats.movementSpeed.colour") + Main.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
     String movementSpeedNoColour = movementSpeed.replaceAll("&([0-9a-f])", "");
     String xpMultiplier = Main.plugin.getConfig().getString("bonusStats.xpMultiplier.colour") + Main.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
     String xpMultiplierNoColour = xpMultiplier.replaceAll("&([0-9a-f])", "");
     String weaponSpeed = Main.plugin.getConfig().getString("bonusStats.weaponSpeed.colour") + Main.plugin.getConfig().getString("bonusStats.weaponSpeed.name");
     String weaponSpeedNoColour = weaponSpeed.replaceAll("&([0-9a-f])", "");
     String xpLevel = Main.plugin.getConfig().getString("bonusStats.xpLevel.colour") + Main.plugin.getConfig().getString("bonusStats.xpLevel.name");
     String xpLevelNoColour = xpLevel.replaceAll("&([0-9a-f])", "");
     String className = Main.plugin.getConfig().getString("bonusStats.class.colour") + Main.plugin.getConfig().getString("bonusStats.class.name");
     String classNameNoColour = className.replaceAll("&([0-9a-f])", "");
     String soulbound = Main.plugin.getConfig().getString("bonusStats.soulbound.colour") + Main.plugin.getConfig().getString("bonusStats.soulbound.name");
     String durability = Main.plugin.getConfig().getString("bonusStats.durability.colour") + Main.plugin.getConfig().getString("bonusStats.durability.name");
     String durabilityNoColour = durability.replaceAll("&([0-9a-f])", "");
     String tnt = Main.plugin.getConfig().getString("spells.tnt.colour") + Main.plugin.getConfig().getString("spells.tnt.name");
     String tntNoColour = tnt.replaceAll("&([0-9a-f])", "");
     String fireball = Main.plugin.getConfig().getString("spells.fireball.colour") + Main.plugin.getConfig().getString("spells.fireball.name");
     String fireballNoColour = fireball.replaceAll("&([0-9a-f])", "");
     String lightning = Main.plugin.getConfig().getString("spells.lightning.colour") + Main.plugin.getConfig().getString("spells.lightning.name");
     String lightningNoColour = lightning.replaceAll("&([0-9a-f])", "");
     String frostbolt = Main.plugin.getConfig().getString("spells.frostbolt.colour") + Main.plugin.getConfig().getString("spells.frostbolt.name");
     String frostboltNoColour = frostbolt.replaceAll("&([0-9a-f])", "");
     String minorHeal = Main.plugin.getConfig().getString("spells.minorHeal.colour") + Main.plugin.getConfig().getString("spells.minorHeal.name");
     String minorHealNoColour = minorHeal.replaceAll("&([0-9a-f])", "");
     String majorHeal = Main.plugin.getConfig().getString("spells.majorHeal.colour") + Main.plugin.getConfig().getString("spells.majorHeal.name");
     String majorHealNoColour = majorHeal.replaceAll("&([0-9a-f])", "");
     
     int randomApplyChance = Main.plugin.getConfig().getInt("randomApplyChance");
     
     double armourValue = 0.0D;
     double dodgeValue = 0.0D;
     double blockValue = 0.0D;
     double critChanceValue = 0.0D;
     double critDamageValue = 0.0D;
     double healthRegenValue = 0.0D;
     double lifeStealValue = 0.0D;
     double reflectValue = 0.0D;
     double fireValue = 0.0D;
     double iceValue = 0.0D;
     double poisonValue = 0.0D;
     double witherValue = 0.0D;
     double harmingValue = 0.0D;
     double blindValue = 0.0D;
     double xpMultiplierValue = 0.0D;
     double movementSpeedValue = 0.0D;
     
     List<String> setLoreList = new ArrayList(java.util.Arrays.asList(new String[] { "" }));
     List<Double> highestValue = new ArrayList();
     
 
 
 
     double sellValue = 0.0D;
     int playerLevel = level;
     
     if (level < 1) {
       playerLevel = 1;
     }
     
 
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entity.toString().toLowerCase() + ".yml"));
       
       if ((this.PlayerDataConfig.contains(dropChance + ".properties.weaponspeed")) && 
         (!this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").equals("0"))) {
         if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").equalsIgnoreCase("random")) {
           if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
             if (random(100) <= randomApplyChance) {
               int r = random(100);
               if (r <= 20) {
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_RED + "Very Slow");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
               } else if ((r > 20) && (r < 40)) {
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.RED + "Slow");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
               } else if ((r > 40) && (r < 60)) {
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.YELLOW + "Normal");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
               } else if ((r > 60) && (r < 80)) {
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.GREEN + "Fast");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
               } else if (r >= 80) {
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_GREEN + "Very Fast");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
               }
             }
           } else {
             int r = random(100);
             if (r <= 20) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_RED + "Very Slow");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
             } else if ((r > 20) && (r < 40)) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.RED + "Slow");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
             } else if ((r > 40) && (r < 60)) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.YELLOW + "Normal");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
             } else if ((r > 60) && (r < 80)) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.GREEN + "Fast");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
             } else if (r >= 80) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + ChatColor.DARK_GREEN + "Very Fast");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
             }
           }
         } else if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").equalsIgnoreCase("verySlow")) {
           if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
             if (random(100) <= randomApplyChance) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
             }
           } else {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.verySlow");
           }
         } else if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").equalsIgnoreCase("slow")) {
           if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
             if (random(100) <= randomApplyChance) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
             }
           } else {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.slow");
           }
         } else if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").contains("fast")) {
           if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
             if (random(100) <= randomApplyChance) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
             }
           } else {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.fast");
           }
         } else if (this.PlayerDataConfig.getString(dropChance + ".properties.weaponspeed").contains("veryFast")) {
           if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
             if (random(100) <= randomApplyChance) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
             }
           } else {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.veryFast");
           }
         }
         else if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.weaponspeedRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(weaponSpeed)) + weaponSpeedNoColour + ": " + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.weaponspeed").toString())));
           sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.weaponSpeed.sellValuePerStat.normal");
         }
       }
       
 
 
       if (this.PlayerDataConfig.contains(dropChance + ".properties.armour")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.armourRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + armourNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.armour").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "armour", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.armour.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + armourNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.armour").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "armour", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(armour)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.armour.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.dodge")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.dodgeRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + dodgeNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.dodge").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "dodge", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.dodge.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + dodgeNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.dodge").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "dodge", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(dodge)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.dodge.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.block")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.blockRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + blockNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.block").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "block", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.block.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + blockNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.block").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "block", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(block)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.block.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.damage")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.damageRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + damageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.damage").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "damage", dropChance).replace(",", "") + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)));
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.damage.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)) + damageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.damage").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "damage", dropChance).replace(",", "") + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(damage)));
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.damage.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.critChance")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.critChanceRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + critChanceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.critChance").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critChance", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.critChance.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + critChanceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.critChance").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critChance", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critChance)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.critChance.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.critDamage")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.critDamageRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + critDamageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.critDamage").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critDamage", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.critDamage.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + critDamageNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.critDamage").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "critDamage", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(critDamage)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.critDamage.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
 
       if (this.PlayerDataConfig.contains(dropChance + ".properties.health")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.healthRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)) + healthNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.health").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "health", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)));
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.health.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)) + healthNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.health").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "health", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(health)));
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.health.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.healthRegen")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.healthRegenRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + healthRegenNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.healthRegen").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "healthRegen", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.healthRegen.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + healthRegenNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.healthRegen").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "healthRegen", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(healthRegen)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.healthRegen.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.lifeSteal")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.lifeStealRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + lifeStealNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.lifeSteal").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "lifeSteal", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.lifeSteal.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + lifeStealNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.lifeSteal").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "lifeSteal", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(lifeSteal)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.lifeSteal.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.reflect")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.reflectRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + reflectNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.reflect").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "reflect", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.reflect.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + reflectNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.reflect").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "reflect", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(reflect)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.reflect.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.fire")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.fireRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + fireNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.fire").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "fire", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.fire.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + fireNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.fire").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "fire", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(fire)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.fire.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.ice")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.iceRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + iceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.ice").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "ice", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.ice.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + iceNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.ice").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "ice", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ice)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.ice.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.poison")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.poisonRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + poisonNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.poison").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "poison", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.poison.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + poisonNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.poison").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "poison", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(poison)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.poison.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.wither")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.witherRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + witherNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.wither").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "wither", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.wither.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + witherNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.wither").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "wither", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(wither)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.wither.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.harming")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.harmingRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + harmingNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.harming").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "harming", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.harming.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + harmingNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.harming").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "harming", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(harming)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.harming.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.blind")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.blindRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + blindNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.blind").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "blind", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.blind.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + blindNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.blind").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "blind", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(blind)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.blind.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.xpMultiplier")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.xpMultiplierRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + xpMultiplierNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.xpMultiplier").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "xpMultiplier", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.xpMultiplier.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + xpMultiplierNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.xpMultiplier").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "xpMultiplier", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpMultiplier)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.xpMultiplier.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.movementSpeed")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.movementSpeedRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + movementSpeedNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.movementSpeed").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "movementSpeed", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + "%");
             sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.movementSpeed.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + movementSpeedNoColour + ": +" + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.movementSpeed").toString()))) + this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, mobLevel, "movementSpeed", dropChance) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(movementSpeed)) + "%");
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.movementSpeed.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
       if (!Main.plugin.getConfig().getBoolean("usingMcMMO")) {
         if (setLoreList.get(setLoreList.size() - 1) != "") {
           setLoreList.add("");
         }
         
         String selectedDurability = "";
         String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");
         
         if ((this.PlayerDataConfig.contains(dropChance + ".properties.durability")) && 
           (!this.PlayerDataConfig.getString(dropChance + ".properties.durability").equals("0"))) {
           if (this.PlayerDataConfig.getString(dropChance + ".properties.durability").equalsIgnoreCase("player")) {
             if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.durabilityRandomApply")) {
               if (random(100) <= randomApplyChance) {
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + Double.valueOf(playerLevel).doubleValue() + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + playerLevel);
                 setLoreList.add("");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
               }
             } else {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + Double.valueOf(playerLevel).doubleValue() + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + playerLevel);
               setLoreList.add("");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
             }
           } else if (this.PlayerDataConfig.getString(dropChance + ".properties.durability").contains("-player+")) {
             if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.durabilityRandomApply")) {
               if (random(100) <= randomApplyChance) {
                 selectedDurability = String.valueOf(randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                 setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability);
                 setLoreList.add("");
                 sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
               }
             } else {
               selectedDurability = String.valueOf(randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability);
               setLoreList.add("");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
             }
           }
           else if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.durabilityRandomApply")) {
             if (random(100) <= randomApplyChance) {
               selectedDurability = String.valueOf(randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability);
               setLoreList.add("");
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
             }
           } else {
             selectedDurability = String.valueOf(randomRangeInt(Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(this.PlayerDataConfig.getString(dropChance + ".properties.durability").split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilityNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(durability)) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.durability").toString()))) + selectedDurability);
             setLoreList.add("");
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.durability.sellValuePerStat") * 1.0D;
           }
         }
       }
       
 
 
       if (Main.plugin.getConfig().getBoolean("addRandomLore")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.addRandomLoreRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add("");
             setLoreList.add(this.randomLore.get(material));
             setLoreList.add("");
           }
         } else {
           setLoreList.add("");
           setLoreList.add(this.randomLore.get(material));
           setLoreList.add("");
         }
       }
       
       if (this.PlayerDataConfig.contains(dropChance + ".properties.xpLevel")) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.xpLevelRandomApply")) {
           if (random(100) <= randomApplyChance) {
             if (mobLevel > 0) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)) + xpLevelNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.xpLevel").toString()))) + Math.round(Double.parseDouble(this.statRanges.getRandomRange(this.PlayerDataConfig, mobLevel, mobLevel, "xpLevel", dropChance))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)));
               sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.xpLevel.sellValuePerStat") * Double.valueOf(mobLevel).doubleValue();
             } else {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)) + xpLevelNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.xpLevel").toString()))) + Math.round(Double.parseDouble(this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, playerLevel, "xpLevel", dropChance))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)));
               sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.xpLevel.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
             }
           }
         }
         else if (mobLevel > 0) {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)) + xpLevelNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.xpLevel").toString()))) + Math.round(Double.parseDouble(this.statRanges.getRandomRange(this.PlayerDataConfig, mobLevel, mobLevel, "xpLevel", dropChance))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)));
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.xpLevel.sellValuePerStat") * Double.valueOf(mobLevel).doubleValue();
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)) + xpLevelNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.xpLevel").toString()))) + Math.round(Double.parseDouble(this.statRanges.getRandomRange(this.PlayerDataConfig, playerLevel, playerLevel, "xpLevel", dropChance))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(xpLevel)));
           sellValue += Main.getPlugin().getConfig().getDouble("secondaryStats.xpLevel.sellValuePerStat") * Double.valueOf(playerLevel).doubleValue();
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".properties.class")) && 
         (!this.PlayerDataConfig.getString(dropChance + ".properties.class").equals("0"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.classRandomApply")) {
           if (random(100) <= randomApplyChance) {
             if (this.PlayerDataConfig.getString(dropChance + ".properties.class").equalsIgnoreCase("random")) {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString()))) + this.util_Colours.replaceTooltipColour(this.util_Colours.replaceTooltipColour(randomClass())));
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
             } else {
               setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString()))) + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString())));
               sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
             }
           }
           else if (this.PlayerDataConfig.getString(dropChance + ".properties.class").equalsIgnoreCase("random")) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString()))) + this.util_Colours.replaceTooltipColour(this.util_Colours.replaceTooltipColour(randomClass())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
           } else {
             setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString()))) + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString())));
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
           }
           
         }
         else if (this.PlayerDataConfig.getString(dropChance + ".properties.class").equalsIgnoreCase("random")) {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString()))) + this.util_Colours.replaceTooltipColour(this.util_Colours.replaceTooltipColour(randomClass())));
           sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(className)) + classNameNoColour + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString()))) + this.util_Colours.replaceTooltipColour(this.PlayerDataConfig.getString(new StringBuilder(String.valueOf(dropChance)).append(".properties.class").toString())));
           sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.class.sellValuePerStat") * 1.0D;
         }
       }
       
 
 
       if ((this.PlayerDataConfig.contains(dropChance + ".properties.soulbound")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".properties.soulbound"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.soulboundRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_Colours.replaceTooltipColour(soulbound) + " " + player.getName());
             sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.soulbound.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_Colours.replaceTooltipColour(soulbound) + " " + player.getName());
           sellValue += Main.getPlugin().getConfig().getDouble("bonusStats.soulbound.sellValuePerStat") * 1.0D;
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".spells.tnt")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".spells.tnt"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.tntRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.tnt.colour")) + tntNoColour);
             sellValue += Main.getPlugin().getConfig().getDouble("spells.tnt.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.tnt.colour")) + tntNoColour);
           sellValue += Main.getPlugin().getConfig().getDouble("spells.tnt.sellValuePerStat") * 1.0D;
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".spells.fireball")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".spells.fireball"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.fireballRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.fireball.colour")) + fireballNoColour);
             sellValue += Main.getPlugin().getConfig().getDouble("spells.fireball.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.fireball.colour")) + fireballNoColour);
           sellValue += Main.getPlugin().getConfig().getDouble("spells.fireball.sellValuePerStat") * 1.0D;
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".spells.lightning")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".spells.lightning"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.lightningRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.lightning.colour")) + lightningNoColour);
             sellValue += Main.getPlugin().getConfig().getDouble("spells.lightning.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.lightning.colour")) + lightningNoColour);
           sellValue += Main.getPlugin().getConfig().getDouble("spells.lightning.sellValuePerStat") * 1.0D;
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".spells.frostbolt")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".spells.frostbolt"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.frostboltRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.frostbolt.colour")) + frostboltNoColour);
             sellValue += Main.getPlugin().getConfig().getDouble("spells.frostbolt.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.frostbolt.colour")) + frostboltNoColour);
           sellValue += Main.getPlugin().getConfig().getDouble("spells.frostbolt.sellValuePerStat") * 1.0D;
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".spells.minorHeal")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".spells.minorHeal"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.minorHealRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.minorHeal.colour")) + minorHealNoColour);
             sellValue += Main.getPlugin().getConfig().getDouble("spells.minorHeal.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.minorHeal.colour")) + minorHealNoColour);
           sellValue += Main.getPlugin().getConfig().getDouble("spells.minorHeal.sellValuePerStat") * 1.0D;
         }
       }
       
 
       if ((this.PlayerDataConfig.contains(dropChance + ".spells.majorHeal")) && 
         (this.PlayerDataConfig.getBoolean(dropChance + ".spells.majorHeal"))) {
         if (this.PlayerDataConfig.getBoolean(dropChance + ".properties.majorHealRandomApply")) {
           if (random(100) <= randomApplyChance) {
             setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.majorHeal.colour")) + majorHealNoColour);
             sellValue += Main.getPlugin().getConfig().getDouble("spells.majorHeal.sellValuePerStat") * 1.0D;
           }
         } else {
           setLoreList.add(this.util_GetResponse.getResponse("SpellMessages.CastSpell.ItemInHand", null, null, "", "") + " " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("spells.majorHeal.colour")) + majorHealNoColour);
           sellValue += Main.getPlugin().getConfig().getDouble("spells.majorHeal.sellValuePerStat") * 1.0D;
         }
       }
       
 
 
 
 
 
       if (Main.getPlugin().getConfig().getBoolean("addSellValueToDrops")) {
         if (setLoreList.get(setLoreList.size() - 1) != "") {
           setLoreList.add("");
         }
         
         sellValue += this.sellValueCalc.get(material);
         setLoreList.add(this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("bonusStats.sellValue.colour")) + Main.getPlugin().getConfig().getString("bonusStats.sellValue.name") + ": " + this.util_Colours.replaceTooltipColour(Main.getPlugin().getConfig().getString("bonusStats.sellValue.currency.colour")) + Double.valueOf(sellValue).intValue() + " " + Main.getPlugin().getConfig().getString("bonusStats.sellValue.currency.name"));
       }
       
       highestValue.add(Double.valueOf(armourValue));
       highestValue.add(Double.valueOf(dodgeValue));
       highestValue.add(Double.valueOf(blockValue));
       highestValue.add(Double.valueOf(critChanceValue));
       highestValue.add(Double.valueOf(critDamageValue));
       highestValue.add(Double.valueOf(healthRegenValue));
       highestValue.add(Double.valueOf(lifeStealValue));
       highestValue.add(Double.valueOf(reflectValue));
       highestValue.add(Double.valueOf(fireValue));
       highestValue.add(Double.valueOf(iceValue));
       highestValue.add(Double.valueOf(poisonValue));
       highestValue.add(Double.valueOf(witherValue));
       highestValue.add(Double.valueOf(harmingValue));
       highestValue.add(Double.valueOf(blindValue));
       highestValue.add(Double.valueOf(xpMultiplierValue));
       highestValue.add(Double.valueOf(movementSpeedValue));
       highestValue.add(Double.valueOf(1.0D));
       
       double maxValue = ((Double)java.util.Collections.max(highestValue)).doubleValue();
       
       while (highestValue.contains(Double.valueOf(maxValue))) {
         int i = highestValue.indexOf(Double.valueOf(maxValue));
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 0) {
           setLoreList.add("primaryStats.armour");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 1) {
           setLoreList.add("secondaryStats.dodge");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 2) {
           setLoreList.add("secondaryStats.block");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 3) {
           setLoreList.add("secondaryStats.critChance");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 4) {
           setLoreList.add("secondaryStats.critDamage");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 5) {
           setLoreList.add("primaryStats.healthRegen");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 6) {
           setLoreList.add("secondaryStats.lifeSteal");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 7) {
           setLoreList.add("secondaryStats.reflect");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 8) {
           setLoreList.add("secondaryStats.fire");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 9) {
           setLoreList.add("secondaryStats.ice");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 10) {
           setLoreList.add("secondaryStats.poison");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 11) {
           setLoreList.add("secondaryStats.wither");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 12) {
           setLoreList.add("secondaryStats.harming");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 13) {
           setLoreList.add("secondaryStats.blind");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 14) {
           setLoreList.add("bonusStats.xpMultiplier");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 15) {
           setLoreList.add("secondaryStats.movementSpeed");
           break; }
         if (highestValue.indexOf(Double.valueOf(maxValue)) == 16) {
           setLoreList.add(".");
           break;
         }
         
         highestValue.set(i, Double.valueOf(-1.0D));
       }
       
       return setLoreList;
     }
     catch (Exception e) {
       e.printStackTrace();
       System.out.println("*********** Failed to load lore from " + entity + " file! ***********");
     }
     
     return setLoreList;
   }
   
   public ItemStack gearGenerator(Player player, int playerLevel, int mobLevel, String entityType, String dropChance)
   {
     try
     {
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entityType + ".yml"));
       String filePath = Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entityType + ".yml";
       
       if (this.PlayerDataConfig.getString(dropChance + ".savedItem") == null)
       {
         Material generateMaterial = this.util_Material.idToMaterial(this.PlayerDataConfig.getString(dropChance + ".itemId").toUpperCase().replace(" ", "_"));
         
         ItemStack createItem = new ItemStack(generateMaterial, 1);
         ItemMeta createdItemMeta = createItem.getItemMeta();
         
         List<String> generateLore = setLore(player, playerLevel, mobLevel, entityType, dropChance, generateMaterial);
         
         String setPrefix = this.prefix.get(this.PlayerDataConfig, entityType, dropChance);
         String setSuffix = this.suffix.get(this.PlayerDataConfig, entityType, dropChance);
         
         if (this.PlayerDataConfig.getString(dropChance + ".prefix").equalsIgnoreCase("Stat")) {
           if (!((String)generateLore.get(generateLore.size() - 1)).toString().equals(".")) {
             int selectStatPrefixFromStat = randomKeySelection(Main.plugin.getConfig().getStringList(((String)generateLore.get(generateLore.size() - 1)).toString() + ".prefix.list").size());
             setPrefix = this.util_Colours.replaceTooltipColour(Main.plugin.getConfig().getString(new StringBuilder(String.valueOf(((String)generateLore.get(generateLore.size() - 1)).toString())).append(".prefix.colour").toString())) + Main.plugin.getConfig().getStringList(new StringBuilder(String.valueOf(((String)generateLore.get(generateLore.size() - 1)).toString())).append(".prefix.list").toString()).toString().split(",")[selectStatPrefixFromStat].replaceAll("\\[", "").replaceAll("\\]", "").trim();
           } else {
             setPrefix = ".";
           }
         }
         
         if (this.PlayerDataConfig.getString(dropChance + ".suffix").equalsIgnoreCase("Stat")) {
           if (!((String)generateLore.get(generateLore.size() - 1)).toString().equals(".")) {
             int selectStatSuffixFromStat = randomKeySelection(Main.plugin.getConfig().getStringList(((String)generateLore.get(generateLore.size() - 1)).toString() + ".suffix.list").size());
             setSuffix = this.util_Colours.replaceTooltipColour(Main.plugin.getConfig().getString(new StringBuilder(String.valueOf(((String)generateLore.get(generateLore.size() - 1)).toString())).append(".suffix.colour").toString())) + Main.plugin.getConfig().getStringList(new StringBuilder(String.valueOf(((String)generateLore.get(generateLore.size() - 1)).toString())).append(".suffix.list").toString()).toString().split(",")[selectStatSuffixFromStat].replaceAll("\\[", "").replaceAll("\\]", "").trim();
           } else {
             setSuffix = ".";
           }
         }
         
         generateLore.remove(generateLore.size() - 1);
         
         if (setPrefix == ".") {
           if (setSuffix == ".") {
             createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(new StringBuilder(String.valueOf(this.rarity.get(Double.parseDouble(dropChance)))).append(this.materialType.setType(filePath, createItem)).toString()));
           } else {
             createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(new StringBuilder(String.valueOf(this.rarity.get(Double.parseDouble(dropChance)))).append(this.materialType.setType(filePath, createItem)).append(" ").append(setSuffix).toString()));
           }
         } else if (setSuffix == ".") {
           createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(new StringBuilder(String.valueOf(this.rarity.get(Double.parseDouble(dropChance)))).append(setPrefix).append(" ").append(this.materialType.setType(filePath, createItem)).toString()));
         } else {
           createdItemMeta.setDisplayName("ILS_" + this.util_Colours.replaceTooltipColour(new StringBuilder(String.valueOf(this.rarity.get(Double.parseDouble(dropChance)))).append(setPrefix).append(" ").append(this.materialType.setType(filePath, createItem)).append(" ").append(setSuffix).toString()));
         }
         
 
 
 
 
 
         createdItemMeta.setLore(generateLore);
         createItem.setItemMeta(createdItemMeta);
         
         return createItem;
       }
       String fileToLoad = null;
       
       fileToLoad = this.PlayerDataConfig.getString(dropChance + ".savedItem");
       
       this.PlayerDataConfig = new YamlConfiguration();
       this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + fileToLoad + ".yml"));
       
       return this.PlayerDataConfig.getItemStack("Item");
 
     }
     catch (Exception e)
     {
       e.printStackTrace();
       System.out.println("*********** Failed to create ItemStack for " + entityType + "! ***********");
     }
     
     return new ItemStack(Material.POTATO);
   }
   
   public void dropEquippedArmour(LivingEntity mob)
   {
     LivingEntity entity = mob;
     
     if (((entity instanceof LivingEntity)) && (!(entity instanceof Player))) {
       entity.getEquipment().setHelmetDropChance((float)(Main.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.helmet") / 100.0D));
       entity.getEquipment().setChestplateDropChance((float)(Main.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.chestplate") / 100.0D));
       entity.getEquipment().setLeggingsDropChance((float)(Main.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.leggings") / 100.0D));
       entity.getEquipment().setBootsDropChance((float)(Main.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.boots") / 100.0D));
       entity.getEquipment().setItemInMainHandDropChance((float)(Main.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.itemInMainHand") / 100.0D));
       entity.getEquipment().setItemInOffHandDropChance((float)(Main.plugin.getConfig().getDouble("npcDropEquippedGear.dropChance.itemInOffHand") / 100.0D));
     }
   }
   
   @org.bukkit.event.EventHandler
   public void dropGear(EntityDeathEvent mob)
   {
     if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(mob.getEntity().getWorld().getName())) {
       dropEquippedArmour(mob.getEntity());
       
       if (!Main.plugin.getConfig().getBoolean("dropCustomILSLoot")) { return;
       }
       if ((mob.getEntity().getKiller() instanceof Player)) {
         if (mob.getEntity().hasMetadata("naturalSpawn")) {
           if (new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml").exists()) {
             try
             {
               this.PlayerDataConfig = new YamlConfiguration();
               
               if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                 Zombie zombie = (Zombie)mob.getEntity();
                 
                 if (zombie.isBaby()) {
                   this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "baby_zombie.yml"));
                 } else if (zombie.isVillager()) {
                   this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "villager_zombie.yml"));
                 } else {
                   this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                 }
               } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                 Skeleton skeleton = (Skeleton)mob.getEntity();
                 
                 if (skeleton.getSkeletonType().equals(Skeleton.SkeletonType.WITHER)) {
                   this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither_skeleton.yml"));
                 } else {
                   this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
                 }
               } else {
                 this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
               }
               
               int dropChance = random(100);
               int playerLevel = mob.getEntity().getKiller().getLevel();
               int mobLevel = 0;
               
               if (mob.getEntity().hasMetadata("level")) {
                 mobLevel = ((MetadataValue)mob.getEntity().getMetadata("level").get(0)).asInt();
               }
               
               int i = this.PlayerDataConfig.getKeys(false).size() - 1;
               do { if (dropChance <= Integer.parseInt(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())) {
                   int selectRandomDrop = randomKeySelection(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).size());
                   
                   if (this.PlayerDataConfig.getString(String.valueOf(new StringBuilder(String.valueOf(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())).append(".").append(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()).toString()) + ".properties.droppedXp") != null) {
                     mob.setDroppedExp(Integer.parseInt(this.PlayerDataConfig.getString(String.valueOf(new StringBuilder(String.valueOf(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())).append(".").append(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()).toString()) + ".properties.droppedXp")));
                   }
                   
                   if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                     Zombie zombie = (Zombie)mob.getEntity();
                     
                     if (zombie.isBaby()) {
                       mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, "baby_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                     } else if (zombie.isVillager()) {
                       mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, "villager_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                     } else {
                       mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                     }
                   } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                     Skeleton skeleton = (Skeleton)mob.getEntity();
                     
                     if (skeleton.getSkeletonType().equals(Skeleton.SkeletonType.WITHER)) {
                       mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, "wither_skeleton", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                     } else {
                       mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                     }
                   } else {
                     mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   }
                   
 
 
 
                   if (mob.getEntity().hasMetadata("level")) {
                     mob.getEntity().removeMetadata("level", Main.plugin);
                   }
                   
                   if (!mob.getEntity().hasMetadata("regionSpawned")) break;
                   mob.getEntity().removeMetadata("regionSpawned", Main.plugin);
                   
 
                   break;
                 }
                 i--; if (i < 0) break; } while (i <= this.PlayerDataConfig.getKeys(false).size() - 1);
 

             }
             catch (Exception e)
             {
 
               e.printStackTrace();
               System.out.println("*********** Failed to drop gear from " + mob.getEntityType().toString().toLowerCase() + "! ***********");
             }
           }
         }
         else if (mob.getEntity().getCustomName() != null) {
           if (new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + this.util_Colours.extractAndReplaceTooltipColour(mob.getEntity().getCustomName()) + ".yml").exists()) {
             int playerLevel = mob.getEntity().getKiller().getLevel();
             String entityName = this.util_Colours.extractAndReplaceTooltipColour(mob.getEntity().getCustomName());
             int mobLevel = 0;
             
             if (mob.getEntity().hasMetadata("level")) {
               mobLevel = ((MetadataValue)mob.getEntity().getMetadata("level").get(0)).asInt();
             }
             try
             {
               this.PlayerDataConfig = new YamlConfiguration();
               this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + entityName + ".yml"));
               
               int dropChance = random(100);
               
               int i = this.PlayerDataConfig.getKeys(false).size() - 1;
               do { if (dropChance <= Integer.parseInt(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())) {
                   int selectRandomDrop = randomKeySelection(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).size());
                   mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, entityName, this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   
                   if (this.PlayerDataConfig.getString(String.valueOf(new StringBuilder(String.valueOf(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())).append(".").append(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()).toString()) + ".properties.droppedXp") != null) {
                     mob.setDroppedExp(Integer.parseInt(this.PlayerDataConfig.getString(String.valueOf(new StringBuilder(String.valueOf(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())).append(".").append(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()).toString()) + ".properties.droppedXp")));
                   }
                   
 
                   if (mob.getEntity().hasMetadata("level")) {
                     mob.getEntity().removeMetadata("level", Main.plugin);
                   }
                   
                   if (!mob.getEntity().hasMetadata("regionSpawned")) break;
                   mob.getEntity().removeMetadata("regionSpawned", Main.plugin);
                   
 
                   break;
                 }
                 i--; if (i < 0) break; } while (i <= this.PlayerDataConfig.getKeys(false).size() - 1);
 
             }
             catch (Exception e)
             {
 
               e.printStackTrace();
               System.out.println("*********** Failed to drop gear from " + entityName + "! ***********");
             }
           }
         } else if (new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml").exists()) {
           int playerLevel = mob.getEntity().getKiller().getLevel();
           try
           {
             this.PlayerDataConfig = new YamlConfiguration();
             
             if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
               Zombie zombie = (Zombie)mob.getEntity();
               
               if (zombie.isBaby()) {
                 this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "baby_zombie.yml"));
               } else if (zombie.isVillager()) {
                 this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "villager_zombie.yml"));
               } else {
                 this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
               }
             } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
               Skeleton skeleton = (Skeleton)mob.getEntity();
               
               if (skeleton.getSkeletonType().equals(Skeleton.SkeletonType.WITHER)) {
                 this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + "wither_skeleton.yml"));
               } else {
                 this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
               }
             } else {
               this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedMobs" + File.separator + mob.getEntityType().toString().toLowerCase() + ".yml"));
             }
             
             int dropChance = random(100);
             int mobLevel = 0;
             
             if (mob.getEntity().hasMetadata("level")) {
               mobLevel = ((MetadataValue)mob.getEntity().getMetadata("level").get(0)).asInt();
             }
             
             int i = this.PlayerDataConfig.getKeys(false).size() - 1;
             do { if (dropChance <= Integer.parseInt(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())) {
                 int selectRandomDrop = randomKeySelection(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).size());
                 
                 if (this.PlayerDataConfig.getString(String.valueOf(new StringBuilder(String.valueOf(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())).append(".").append(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()).toString()) + ".properties.droppedXp") != null) {
                   mob.setDroppedExp(Integer.parseInt(this.PlayerDataConfig.getString(String.valueOf(new StringBuilder(String.valueOf(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim())).append(".").append(this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()).toString()) + ".properties.droppedXp")));
                 }
                 
                 if (mob.getEntityType().equals(EntityType.ZOMBIE)) {
                   Zombie zombie = (Zombie)mob.getEntity();
                   
                   if (zombie.isBaby()) {
                     mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, "baby_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   } else if (zombie.isVillager()) {
                     mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, "villager_zombie", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   } else {
                     mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   }
                 } else if (mob.getEntityType().equals(EntityType.SKELETON)) {
                   Skeleton skeleton = (Skeleton)mob.getEntity();
                   
                   if (skeleton.getSkeletonType().equals(Skeleton.SkeletonType.WITHER)) {
                     mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, "wither_skeleton", this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   } else {
                     mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                   }
                 } else {
                   mob.getDrops().add(gearGenerator(mob.getEntity().getKiller(), playerLevel, mobLevel, mob.getEntityType().toString().toLowerCase(), this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim() + "." + this.PlayerDataConfig.getConfigurationSection(this.PlayerDataConfig.getKeys(false).toString().split(",")[i].replaceAll("\\[", "").replaceAll("\\]", "").trim()).getKeys(false).toString().split(",")[selectRandomDrop].replaceAll("\\[", "").replaceAll("\\]", "").trim()));
                 }
                 
 
                 if (mob.getEntity().hasMetadata("level")) {
                   mob.getEntity().removeMetadata("level", Main.plugin);
                 }
                 
                 if (!mob.getEntity().hasMetadata("regionSpawned")) break;
                 mob.getEntity().removeMetadata("regionSpawned", Main.plugin);
                 
 
                 break;
               }
               i--; if (i < 0) break; } while (i <= this.PlayerDataConfig.getKeys(false).size() - 1);
 
           }
           catch (Exception e)
           {
             e.printStackTrace();
             System.out.println("*********** Failed to drop gear from " + mob.getEntityType().toString().toLowerCase() + "! ***********");
           }
         }
       }
     }
   }
 }

 