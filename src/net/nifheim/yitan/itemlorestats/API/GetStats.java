 package net.nifheim.yitan.itemlorestats.API;
 
 import net.nifheim.yitan.itemlorestats.GearStats;
 import net.nifheim.yitan.itemlorestats.Main;
 import net.nifheim.yitan.itemlorestats.Util.Util_Format;
 import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 
 public class GetStats
 {
    net.nifheim.yitan.itemlorestats.SetBonuses setBonuses = new net.nifheim.yitan.itemlorestats.SetBonuses();
   GearStats gearStats = new GearStats();
   Util_Format util_Format = new Util_Format();
   
   public ItemStack itemInMainHand(LivingEntity entity) {
     ItemStack item = entity.getEquipment().getItemInMainHand();
     
     return item;
   }
   
   public ItemStack itemInOffHand(LivingEntity entity) {
     ItemStack item = entity.getEquipment().getItemInOffHand();
     
     return item;
   }
   
   private double itemInMainHandBaseDamage(ItemStack item)
   {
     double noTool = 1.0D;
     Material itemMaterial = item.getType();
     
     if (Main.plugin.isTool(itemMaterial)) {
       if (itemMaterial == Material.WOOD_SWORD)
         return 4.0D;
       if (itemMaterial == Material.WOOD_AXE)
         return 7.0D;
       if (itemMaterial == Material.WOOD_PICKAXE)
         return 2.0D;
       if (itemMaterial == Material.WOOD_SPADE)
         return 2.5D;
       if (itemMaterial == Material.WOOD_HOE)
         return 1.0D;
       if (itemMaterial == Material.STONE_SWORD)
         return 5.0D;
       if (itemMaterial == Material.STONE_AXE)
         return 9.0D;
       if (itemMaterial == Material.STONE_PICKAXE)
         return 3.0D;
       if (itemMaterial == Material.STONE_SPADE)
         return 3.5D;
       if (itemMaterial == Material.STONE_HOE)
         return 1.0D;
       if (itemMaterial == Material.GOLD_SWORD)
         return 4.0D;
       if (itemMaterial == Material.GOLD_AXE)
         return 7.0D;
       if (itemMaterial == Material.GOLD_PICKAXE)
         return 2.0D;
       if (itemMaterial == Material.GOLD_SPADE)
         return 2.5D;
       if (itemMaterial == Material.GOLD_HOE)
         return 1.0D;
       if (itemMaterial == Material.IRON_SWORD)
         return 6.0D;
       if (itemMaterial == Material.IRON_AXE)
         return 9.0D;
       if (itemMaterial == Material.IRON_PICKAXE)
         return 4.0D;
       if (itemMaterial == Material.IRON_SPADE)
         return 4.5D;
       if (itemMaterial == Material.IRON_HOE)
         return 1.0D;
       if (itemMaterial == Material.DIAMOND_SWORD)
         return 7.0D;
       if (itemMaterial == Material.DIAMOND_AXE)
         return 9.0D;
       if (itemMaterial == Material.DIAMOND_PICKAXE)
         return 5.0D;
       if (itemMaterial == Material.DIAMOND_SPADE)
         return 5.5D;
       if (itemMaterial == Material.DIAMOND_HOE) {
         return 1.0D;
       }
     }
     
     return noTool;
   }
   
   private double itemInOffHandBaseDamage(ItemStack item)
   {
     double noTool = 0.0D;
     Material itemMaterial = item.getType();
     
     if (Main.plugin.isTool(itemMaterial)) {
       if (itemMaterial == Material.WOOD_SWORD)
         return 4.0D;
       if (itemMaterial == Material.WOOD_AXE)
         return 7.0D;
       if (itemMaterial == Material.WOOD_PICKAXE)
         return 2.0D;
       if (itemMaterial == Material.WOOD_SPADE)
         return 2.5D;
       if (itemMaterial == Material.WOOD_HOE)
         return 1.0D;
       if (itemMaterial == Material.STONE_SWORD)
         return 5.0D;
       if (itemMaterial == Material.STONE_AXE)
         return 9.0D;
       if (itemMaterial == Material.STONE_PICKAXE)
         return 3.0D;
       if (itemMaterial == Material.STONE_SPADE)
         return 3.5D;
       if (itemMaterial == Material.STONE_HOE)
         return 1.0D;
       if (itemMaterial == Material.GOLD_SWORD)
         return 4.0D;
       if (itemMaterial == Material.GOLD_AXE)
         return 7.0D;
       if (itemMaterial == Material.GOLD_PICKAXE)
         return 2.0D;
       if (itemMaterial == Material.GOLD_SPADE)
         return 2.5D;
       if (itemMaterial == Material.GOLD_HOE)
         return 1.0D;
       if (itemMaterial == Material.IRON_SWORD)
         return 6.0D;
       if (itemMaterial == Material.IRON_AXE)
         return 9.0D;
       if (itemMaterial == Material.IRON_PICKAXE)
         return 4.0D;
       if (itemMaterial == Material.IRON_SPADE)
         return 4.5D;
       if (itemMaterial == Material.IRON_HOE)
         return 1.0D;
       if (itemMaterial == Material.DIAMOND_SWORD)
         return 7.0D;
       if (itemMaterial == Material.DIAMOND_AXE)
         return 9.0D;
       if (itemMaterial == Material.DIAMOND_PICKAXE)
         return 5.0D;
       if (itemMaterial == Material.DIAMOND_SPADE)
         return 5.5D;
       if (itemMaterial == Material.DIAMOND_HOE) {
         return 1.0D;
       }
     }
     
     return noTool;
   }
   
   public double getBaseHealth() {
     return Main.plugin.getConfig().getDouble("baseHealth");
   }
   
   public double getBaseHealthRegen() {
     return Main.plugin.getConfig().getDouble("baseHealthRegen");
   }
   
   public double getHealthPerLevel() {
     return Main.plugin.getConfig().getDouble("healthPerLevel");
   }
   
   public double getBaseMovementSpeed() {
     return Main.plugin.getConfig().getDouble("baseMovementSpeed");
   }
   
   public double getBaseCritDamage() {
     return Main.plugin.getConfig().getDouble("baseCritDamage");
   }
   
   public String getDamageStatValue(Player player)
   {
     double minValue = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]);
     double maxValue = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]);
     
     String value = minValue + " - " + maxValue;
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       minValue += Double.parseDouble(this.gearStats.getDamageItemInHand(itemInMainHand(player)).split("-")[0]);
       maxValue += Double.parseDouble(this.gearStats.getDamageItemInHand(itemInMainHand(player)).split("-")[1]);
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       minValue += Double.parseDouble(this.gearStats.getDamageItemInHand(itemInOffHand(player)).split("-")[0]);
       maxValue += Double.parseDouble(this.gearStats.getDamageItemInHand(itemInOffHand(player)).split("-")[1]);
     }
     
     return value;
   }
   
   public double getArmourStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getArmourGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getArmourItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getArmourItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getDodgeStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getDodgeGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getDodgeItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getDodgeItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getBlockStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getBlockGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getBlockItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getBlockItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getCritChanceStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getCritChanceGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getCritChanceItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getCritChanceItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getCritDamageStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getCritDamageGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getCritDamageItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getCritDamageItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getHealthStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getHealthGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getHealthItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getHealthItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getHealthRegenStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getHealthRegenGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getHealthRegenItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getHealthRegenItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getLifeStealStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getLifeStealGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getLifeStealItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getLifeStealItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getFireStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getFireGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getFireItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getFireItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getIceStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getIceGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getIceItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getIceItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getReflectStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getReflectGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getReflectItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getReflectItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getPoisonStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getPoisonGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getPoisonItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getPoisonItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getWitherStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getWitherGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getWitherItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getWitherItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getHarmingStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getHarmingGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getHarmingItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getHarmingItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getBlindStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getBlindGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getBlindItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getBlindItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getMovementSpeedStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getMovementSpeedGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getMovementSpeedItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getMovementSpeedItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getXPMultiplierStatValue(Player player)
   {
     double value = this.util_Format.format(this.gearStats.getXPMultiplierGear(player));
     
     if (Main.plugin.isTool(itemInMainHand(player).getType())) {
       value += this.gearStats.getXPMultiplierItemInHand(itemInMainHand(player));
     }
     
     if (Main.plugin.isTool(itemInOffHand(player).getType())) {
       value += this.gearStats.getXPMultiplierItemInHand(itemInOffHand(player));
     }
     
     return value;
   }
   
   public double getItemInHandValue(Player player) {
     double value = 0.0D;
     
     value = this.util_Format.format(this.gearStats.getSellValueItemInHand(itemInMainHand(player)));
     
     return value;
   }
 }
