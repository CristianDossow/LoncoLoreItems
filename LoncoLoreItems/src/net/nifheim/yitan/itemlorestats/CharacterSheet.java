 package net.nifheim.yitan.itemlorestats;
import net.nifheim.yitan.loncolorestats.EspecialAtributes;
 
 import net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Sharpness;
 import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
 import net.nifheim.yitan.itemlorestats.Util.Util_Format;
 import net.nifheim.yitan.itemlorestats.Util.Util_Material;

import java.util.List;

import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 
 public class CharacterSheet
 {
   GearStats gearStats = new GearStats();
   SetBonuses setBonuses = new SetBonuses();
   
   Util_Colours util_Colours = new Util_Colours();
   Util_Material util_Material = new Util_Material();
   Util_Format util_Format = new Util_Format();
    net.nifheim.yitan.itemlorestats.Util.Util_EntityManager util_EntityManager = new net.nifheim.yitan.itemlorestats.Util.Util_EntityManager();
   
   Vanilla_Sharpness vanilla_Sharpness = new Vanilla_Sharpness();
    net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Power vanilla_Power = new net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Power();
   
   String armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
   String dodge = ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.dodge.name");
   String block = ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.block.name");
   String critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name");
   String critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name");
   String damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
   String health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
   String healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.colour") + ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name");
   String lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
   String reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name");
   String fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name");
   String ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name");
   String poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name");
   String wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name");
   String harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name");
   String blind = ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.blind.name");
   String xpmultiplier = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.colour") + ItemLoreStats.plugin.getConfig().getString("bonusStats.xpMultiplier.name");
   String movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.colour") + ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
   String level = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
   String onlydamage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");

			static String languageRegex = "[^A-Za-z������������_]";
			
			public double[] addDamageFromItem(ItemStack gear, double multiplier){
			double damages[] = {0,0};
			List<String> itemLore = gear.getItemMeta().getLore();
			for (String line : itemLore) {
			    String lore = ChatColor.stripColor(line.toString());
			    lore = lore.toLowerCase();
			    if (lore.replaceAll(languageRegex, "").matches(onlydamage.toLowerCase())) {
			      if (lore.contains("-")) {
			    	  damages[0] += Double.parseDouble(lore.split("-")[0].replaceAll("[^0-9.+-]", ""))*multiplier;
			    	  damages[1] += Double.parseDouble(lore.split("-")[1].replaceAll("[^0-9.+-]", ""))*multiplier;
			      } else {
			    	  damages[0] += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""))*multiplier;
			    	  damages[1] += Double.parseDouble(lore.replaceAll("[^0-9.+-]", ""))*multiplier;
			      }
			    }
			  }
			
			return damages;
			}
 
   public void returnStats(Player player, double passHealth)
   {
     player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "Stats:");
              player.sendMessage(getArmourStrengthValue(player));
     player.sendMessage(getArmourValue(player));
     player.sendMessage(getDodgeValue(player));
     player.sendMessage(getBlockValue(player));

     
     double minDamage = 0.0D;
     double maxDamage = 0.0D;
     
     double valueMinMain = 0.0D;
     double valueMaxMain = 0.0D;
     double valueMinOff = 0.0D;
     double valueMaxOff = 0.0D;
     
     minDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[0]);
     maxDamage = Double.parseDouble(this.gearStats.getDamageGear(player).split("-")[1]);
     
     if (ItemLoreStats.plugin.isTool(player.getInventory().getItemInMainHand().getType())) {
       if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
         minDamage += this.util_Material.materialToDamage(player.getInventory().getItemInMainHand().getType());
         maxDamage += this.util_Material.materialToDamage(player.getInventory().getItemInMainHand().getType());
       }
       
       valueMinMain = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player)).split("-")[0]);
       valueMaxMain = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player)).split("-")[1]);
       
       minDamage += valueMinMain;
       maxDamage += valueMaxMain;
     }
     
     if (ItemLoreStats.plugin.isTool(player.getInventory().getItemInOffHand().getType())) {
       if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
         minDamage += this.util_Material.materialToDamage(player.getInventory().getItemInOffHand().getType());
         maxDamage += this.util_Material.materialToDamage(player.getInventory().getItemInOffHand().getType());
       }
       
				if(true){
					   //Player player = (Player) entity;
					   ItemStack offhand= player.getInventory().getItemInOffHand();
					   ItemStack mainhand= player.getInventory().getItemInMainHand();
					   ItemStack gear = offhand;
					  
					   if(mainhand.getType().equals(Material.BOW) && gear.equals(offhand)){
						   valueMinOff=0;
						   valueMaxOff=0;
					   }
					   if(!mainhand.getType().equals(Material.BOW) && gear.equals(offhand))
						   if(!offhand.getType().equals(Material.BOW)){
							   double weaponspeed1 = EspecialAtributes.getWeaponSpeed(offhand);
							   double weaponspeed2 = EspecialAtributes.getWeaponSpeed(mainhand);
							   double damagefactor = (weaponspeed2/weaponspeed1)*0.5;
							   //Bukkit.broadcastMessage(""+damagefactor);
							   double damages[] = addDamageFromItem(gear,damagefactor);
							   valueMinOff = damages[0];
							   valueMaxOff = damages[1];
					   }
				}
       //valueMinOff = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player)).split("-")[0]);
       //valueMaxOff = Double.parseDouble(this.gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player)).split("-")[1]);
       
           

       minDamage += valueMinOff;
       maxDamage += valueMaxOff;
     }
     
     if (this.vanilla_Sharpness.hasSharpness(player)) {
       int mainLevel = ItemLoreStats.plugin.itemInMainHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL);
       int offLevel = ItemLoreStats.plugin.itemInOffHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL);
       
       minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, mainLevel, offLevel);
       maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, mainLevel, offLevel);
     } else if (this.vanilla_Power.hasPower(player)) {
       int mainLevel = ItemLoreStats.plugin.itemInMainHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
       int offLevel = ItemLoreStats.plugin.itemInOffHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
       
       minDamage = this.vanilla_Sharpness.calculateNewDamage(minDamage, mainLevel, offLevel);
       maxDamage = this.vanilla_Sharpness.calculateNewDamage(maxDamage, mainLevel, offLevel);
     }
     
     player.sendMessage("    " + this.util_Colours.replaceTooltipColour(this.damage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(minDamage) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.damage)) + " - " + ChatColor.LIGHT_PURPLE + this.util_Format.format(maxDamage));
     
     player.sendMessage(getCritChanceValue(player));
     player.sendMessage(getCritDamageValue(player));
     player.sendMessage("    " + this.util_Colours.replaceTooltipColour(this.health) + ": " + ChatColor.LIGHT_PURPLE + passHealth);

     player.sendMessage(getHealthRegenValue(player));
     player.sendMessage(getLifeStealValue(player));
     player.sendMessage(getReflectValue(player));
     player.sendMessage(getFireValue(player));
     player.sendMessage(getIceValue(player));
     player.sendMessage(getPoisonValue(player));
     player.sendMessage(getWitherValue(player));
     player.sendMessage(getHarmingValue(player));
     player.sendMessage(getBlindValue(player));
     player.sendMessage(getXPMultiplierValue(player));
     player.sendMessage(getMovementSpeedValue(player));
   }
   
   public String getArmourValue(Player player)
   {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getArmourItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getArmourItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getArmourGear(player);
     
     String message = "    " + ChatColor.DARK_AQUA + "Defensa: " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.armour)) + "%";
     
     return message;
   }

   public String getArmourStrengthValue(Player player)
   {
     double stat = 0.0D;
     
     
     stat += this.gearStats.getStrengthGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.armour) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.armour));
     
     return message;
   }

   
   public String getDodgeValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getDodgeItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getDodgeItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getDodgeGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.dodge) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.dodge)) + "%";
     
     return message;
   }
   
   public String getBlockValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getBlockGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.block) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.block)) + "%";
     
     return message;
   }
   public double getBlockValuedouble(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getBlockItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getBlockGear(player);
     
     //String message = "    " + this.util_Colours.replaceTooltipColour(this.block) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.block)) + "%";
     
     return stat;
   }


   public String getCritChanceValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getCritChanceItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getCritChanceItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getCritChanceGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.critChance) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.critChance)) + "%";
     
     return message;
   }
   
   public String getCritDamageValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getCritDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getCritDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getCritDamageGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.critDamage) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.critDamage)) + "%";
     
     return message;
   }
   
   public String getHealthRegenValue(Player player) {
     double stat = Double.valueOf(player.getLevel()).doubleValue() * ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.healthRegen");
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getHealthRegenItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getHealthRegenItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getHealthRegenGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.healthRegen) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.healthRegen)) + "%";
     
     return message;
   }
   
   public String getLifeStealValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getLifeStealItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getLifeStealItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getLifeStealGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.lifeSteal) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.lifeSteal)) + "%";
     
     return message;
   }
   
   public String getReflectValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getReflectItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getReflectItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getReflectGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.reflect) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.reflect)) + "%";
     
     return message;
   }
   
   public String getFireValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getFireItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getFireItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getFireGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.fire) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.fire)) + "%";
     
     return message;
   }
   
   public String getIceValue(Player player) {
     double stat = 0.0D;
     String message = "    " + this.util_Colours.replaceTooltipColour(this.ice) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.ice)) + "%";
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getIceItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getIceItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getIceGear(player);
     
     return message;
   }
   
   public String getPoisonValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getPoisonItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getPoisonItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getPoisonGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.poison) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.poison)) + "%";
     
 
     return message;
   }
   
   public String getWitherValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getWitherItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getWitherItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getWitherGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.wither) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.wither)) + "%";
     
     return message;
   }
   
   public String getHarmingValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getHarmingItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getHarmingItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getHarmingGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.harming) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.harming)) + "%";
     
 
     return message;
   }
   
   public String getBlindValue(Player player) {
     double stat = 0.0D;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getBlindItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getBlindItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getBlindGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.blind) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.blind)) + "%";
     
     return message;
   }
   
   public String getXPMultiplierValue(Player player) {
     double stat = 0.0D;
     
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getXPMultiplierItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getXPMultiplierItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getXPMultiplierGear(player);
              String message = "    " + this.util_Colours.replaceTooltipColour(this.xpmultiplier) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.xpmultiplier)) + "%";
     
     return message;
   }
   
   public String getMovementSpeedValue(Player player) {
     double stat = Double.valueOf(player.getLevel()).doubleValue() * ItemLoreStats.plugin.getConfig().getDouble("additionalStatsPerLevel.speed")*100;
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInMainHand(player).getType())) {
       stat += this.gearStats.getMovementSpeedItemInHand(ItemLoreStats.plugin.itemInMainHand(player));
     }
     
     if (ItemLoreStats.plugin.isTool(ItemLoreStats.plugin.itemInOffHand(player).getType())) {
       stat += this.gearStats.getMovementSpeedItemInHand(ItemLoreStats.plugin.itemInOffHand(player));
     }
     
     stat += this.gearStats.getMovementSpeedGear(player);
     
     String message = "    " + this.util_Colours.replaceTooltipColour(this.movementspeed) + ": " + ChatColor.LIGHT_PURPLE + this.util_Format.format(stat) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(this.movementspeed)) + "%";
     
     return message;
   }
 }

