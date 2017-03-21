 package com.github.supavitax.itemlorestats.Durability;
 
 import com.github.supavitax.itemlorestats.Enchants.Vanilla_Unbreaking;
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.Util.Util_Colours;
 import java.util.List;

import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Effect;
 import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.Entity;
 import org.bukkit.entity.LivingEntity;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.EntityEquipment;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 import org.bukkit.inventory.meta.ItemMeta;
 
 public class Durability
 {
   DurabilityEvents durabilityEvents = new DurabilityEvents();
   Util_Colours util_Colours = new Util_Colours();
   Vanilla_Unbreaking vanilla_Unbreaking = new Vanilla_Unbreaking();
   
   public String above75;
   public String above50;
   public String above25;
   public String above0;
   public String durabilityName;
   public String durabilitySplitter;
   
   private void setDurabilityStrings()
   {
     if (this.durabilityName == null) {
       this.above75 = this.util_Colours.replaceTooltipColour(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.warningColours.above75%"));
       this.above50 = this.util_Colours.replaceTooltipColour(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.warningColours.above50%"));
       this.above25 = this.util_Colours.replaceTooltipColour(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.warningColours.above25%"));
       this.above0 = this.util_Colours.replaceTooltipColour(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.warningColours.above0%"));
       this.durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
       this.durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
     }
   }
   
   public String calculateNewDurability(int newValue, String maxValue, String durabilityPrefixColour, String durabilityAmountColour, ItemStack item, Player player) {
     int maximumValue = Integer.valueOf(Integer.parseInt(maxValue)).intValue();
     String durabilityRebuilder = "";
     
     setDurabilityStrings();
     
 
     if (newValue < maximumValue / 4) {
       if ((newValue == maximumValue / 4) && (ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enableAbove0%DurabilityWarning"))) {
         player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + this.above0 + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
       }
       
       durabilityRebuilder = this.util_Colours.replaceTooltipColour(durabilityPrefixColour) + this.durabilityName + ": " + this.above0 + newValue + durabilityPrefixColour + this.durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxValue;
     }
     else if (newValue < maximumValue / 2) {
       if ((newValue == maximumValue / 2) && (ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enableAbove25%DurabilityWarning"))) {
         player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + this.above25 + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
       }
       
       durabilityRebuilder = this.util_Colours.replaceTooltipColour(durabilityPrefixColour) + this.durabilityName + ": " + this.above25 + newValue + durabilityPrefixColour + this.durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxValue;
     }
     else if (newValue < maximumValue / 4 * 3) {
       if ((newValue == maximumValue / 4 * 3) && (ItemLoreStats.plugin.getConfig().getBoolean("displayDurabilityWarnings.enableAbove50%DurabilityWarning"))) {
         player.sendMessage(item.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + this.above50 + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
       }
       
       durabilityRebuilder = this.util_Colours.replaceTooltipColour(durabilityPrefixColour) + this.durabilityName + ": " + this.above50 + newValue + durabilityPrefixColour + this.durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxValue;
     }
     else {
       durabilityRebuilder = this.util_Colours.replaceTooltipColour(durabilityPrefixColour) + this.durabilityName + ": " + this.above75 + newValue + durabilityPrefixColour + this.durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxValue;
     }
     
     return durabilityRebuilder;
   }
   
            public void durabilityCalcForItemInHand(Player player, int amount, String durabilityLost, ItemStack itemInHand, String handType)
            {

     if ( 
       (itemInHand != null) && (ItemLoreStats.plugin.isTool(itemInHand.getType())) && 
       (itemInHand.getItemMeta().hasLore())) {
       setDurabilityStrings();


       
       List<String> getItemLore = itemInHand.getItemMeta().getLore();
       
       for (String getDurability : getItemLore) {
         if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
           String durabilityRebuilder = "";
           String durabilityAmountColour = "";
           String prefixColourOnly = "";


           
           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability));
           
           int index = getItemLore.indexOf(getDurability);
           String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
           int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
           
           if (calculateNewValue + 1 < 2) {
             player.playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
             player.getInventory().remove(itemInHand);
             return;
           }
           
           double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
           //itemInHand.setDurability((short)(int)Math.abs(value * itemInHand.getType().getMaxDurability() - itemInHand.getType().getMaxDurability()));
           
           if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
             if (getDurability.length() > 4) {
               if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
               } else {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
               }
             } else {
               prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
             }
           } else {
             prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
           }
           
           if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
             if (getDurability.split(this.durabilitySplitter)[1].trim().length() > 4) {
               if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                 durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(2, 4);
               } else {
                 durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
               }
             } else {
               durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
             }
           } else {
             durabilityAmountColour = prefixColourOnly;
           }
           
           if (this.vanilla_Unbreaking.hasUnbreaking(player)) {
             calculateNewValue += this.vanilla_Unbreaking.calculateNewDurabilityLoss(itemInHand.getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
           }
           
 
 
           durabilityRebuilder = calculateNewDurability(calculateNewValue, maximumValue, this.util_Colours.replaceTooltipColour(prefixColourOnly), this.util_Colours.replaceTooltipColour(durabilityAmountColour), itemInHand, player);
           
           getItemLore.set(index, durabilityRebuilder);
         }
       }
       
       ItemStack setItemInHand = new ItemStack(itemInHand);
       ItemMeta setItemInHandMeta = setItemInHand.getItemMeta();
       
       setItemInHandMeta.setLore(getItemLore);
       setItemInHand.setItemMeta(setItemInHandMeta);
       
 
 
       if (handType.equals("Main")) {
         player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
       } else if (handType.equals("Off")) {
         player.getInventory().setItemInOffHand(new ItemStack(setItemInHand));
       }
     }
   }
   
 
 
 
 
 
 
   public void durabilityCalcForArmour(Entity getDefender, int amount, String durabilityLost)
   {
     if ((getDefender instanceof Player)) {
       setDurabilityStrings();
       
       if ((((Player)getDefender).getInventory().getHelmet() != null) && (ItemLoreStats.plugin.isHelmet(((Player)getDefender).getInventory().getHelmet().getType())) && 
         (((Player)getDefender).getInventory().getHelmet().getItemMeta().hasLore()))
       {
         List<String> getItemLore = ((Player)getDefender).getInventory().getHelmet().getItemMeta().getLore();
         
         for (String getDurability : getItemLore) {
           if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
             String durabilityRebuilder = "";
             String durabilityAmountColour = "";
             String prefixColourOnly = "";
             
             int index = getItemLore.indexOf(getDurability);
             String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
             int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
             
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //((Player)getDefender).getInventory().getHelmet().setDurability((short)(int)Math.abs(value * ((Player)getDefender).getInventory().getHelmet().getType().getMaxDurability() - ((Player)getDefender).getInventory().getHelmet().getType().getMaxDurability()));
             
             if (calculateNewValue + 1 > 1) {
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
                 if (getDurability.length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                   } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                   }
                 } else {
                   prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                 }
               } else {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
               }
               
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                 if (getDurability.split(this.durabilitySplitter)[1].trim().length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(2, 4);
                   } else {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                   }
                 } else {
                   durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                 }
               } else {
                 durabilityAmountColour = prefixColourOnly;
               }
               
               if (this.vanilla_Unbreaking.armourHasUnbreaking(((Player)getDefender).getInventory().getHelmet())) {
                 calculateNewValue += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player)getDefender).getInventory().getHelmet().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
               }
               
 
 
               durabilityRebuilder = calculateNewDurability(calculateNewValue, maximumValue, this.util_Colours.replaceTooltipColour(prefixColourOnly), this.util_Colours.replaceTooltipColour(durabilityAmountColour), ((Player)getDefender).getInventory().getHelmet(), (Player)getDefender);
               
               getItemLore.set(index, durabilityRebuilder);
               
               ItemStack getItem = new ItemStack(((Player)getDefender).getInventory().getHelmet());
               ItemMeta getItemMeta = getItem.getItemMeta();
               
               getItemMeta.setLore(getItemLore);
               getItem.setItemMeta(getItemMeta);
               
               ((Player)getDefender).getInventory().remove(((Player)getDefender).getInventory().getHelmet());
               ((Player)getDefender).getInventory().setHelmet(new ItemStack(getItem));
             } else {
               ((Player)getDefender).playEffect(((Player)getDefender).getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
               ((Player)getDefender).getInventory().setHelmet(new ItemStack(Material.AIR));
             }
           }
         }
       }
       
 
       if ((((Player)getDefender).getInventory().getChestplate() != null) && (ItemLoreStats.plugin.isChestplate(((Player)getDefender).getInventory().getChestplate().getType())) && 
         (((Player)getDefender).getInventory().getChestplate().getItemMeta().hasLore()))
       {
         List<String> getItemLore = ((Player)getDefender).getInventory().getChestplate().getItemMeta().getLore();
         
         for (String getDurability : getItemLore) {
           if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
             String durabilityRebuilder = "";
             String durabilityAmountColour = "";
             String prefixColourOnly = "";
             
             int index = getItemLore.indexOf(getDurability);
             String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
             int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
             
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //((Player)getDefender).getInventory().getChestplate().setDurability((short)(int)Math.abs(value * ((Player)getDefender).getInventory().getChestplate().getType().getMaxDurability() - ((Player)getDefender).getInventory().getChestplate().getType().getMaxDurability()));
             
             if (calculateNewValue + 1 > 1) {
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
                 if (getDurability.length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                   } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                   }
                 } else {
                   prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                 }
               } else {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
               }
               
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                 if (getDurability.split(this.durabilitySplitter)[1].trim().length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(2, 4);
                   } else {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                   }
                 } else {
                   durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                 }
               } else {
                 durabilityAmountColour = prefixColourOnly;
               }
               
               if (this.vanilla_Unbreaking.armourHasUnbreaking(((Player)getDefender).getInventory().getChestplate())) {
                 calculateNewValue += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player)getDefender).getInventory().getChestplate().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
               }
               
 
 
               durabilityRebuilder = calculateNewDurability(calculateNewValue, maximumValue, this.util_Colours.replaceTooltipColour(prefixColourOnly), this.util_Colours.replaceTooltipColour(durabilityAmountColour), ((Player)getDefender).getInventory().getChestplate(), (Player)getDefender);
               
               getItemLore.set(index, durabilityRebuilder);
               
               ItemStack getItem = new ItemStack(((Player)getDefender).getInventory().getChestplate());
               ItemMeta getItemMeta = getItem.getItemMeta();
               
               getItemMeta.setLore(getItemLore);
               getItem.setItemMeta(getItemMeta);
               
               ((Player)getDefender).getInventory().remove(((Player)getDefender).getInventory().getChestplate());
               ((Player)getDefender).getInventory().setChestplate(new ItemStack(getItem));
             } else {
               ((Player)getDefender).playEffect(((Player)getDefender).getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
               ((Player)getDefender).getInventory().setChestplate(new ItemStack(Material.AIR));
             }
           }
         }
       }
       
 
       if ((((Player)getDefender).getInventory().getLeggings() != null) && (ItemLoreStats.plugin.isLeggings(((Player)getDefender).getInventory().getLeggings().getType())) && 
         (((Player)getDefender).getInventory().getLeggings().getItemMeta().hasLore()))
       {
         List<String> getItemLore = ((Player)getDefender).getInventory().getLeggings().getItemMeta().getLore();
         
         for (String getDurability : getItemLore) {
           if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
             String durabilityRebuilder = "";
             String durabilityAmountColour = "";
             String prefixColourOnly = "";
             
             int index = getItemLore.indexOf(getDurability);
             String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
             int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
             
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //((Player)getDefender).getInventory().getLeggings().setDurability((short)(int)Math.abs(value * ((Player)getDefender).getInventory().getLeggings().getType().getMaxDurability() - ((Player)getDefender).getInventory().getLeggings().getType().getMaxDurability()));
             
             if (calculateNewValue + 1 > 1) {
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
                 if (getDurability.length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                   } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                   }
                 } else {
                   prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                 }
               } else {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
               }
               
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                 if (getDurability.split(this.durabilitySplitter)[1].trim().length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(2, 4);
                   } else {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                   }
                 } else {
                   durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                 }
               } else {
                 durabilityAmountColour = prefixColourOnly;
               }
               
               if (this.vanilla_Unbreaking.armourHasUnbreaking(((Player)getDefender).getInventory().getLeggings())) {
                 calculateNewValue += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player)getDefender).getInventory().getLeggings().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
               }
               
 
 
               durabilityRebuilder = calculateNewDurability(calculateNewValue, maximumValue, this.util_Colours.replaceTooltipColour(prefixColourOnly), this.util_Colours.replaceTooltipColour(durabilityAmountColour), ((Player)getDefender).getInventory().getLeggings(), (Player)getDefender);
               
               getItemLore.set(index, durabilityRebuilder);
               
               ItemStack getItem = new ItemStack(((Player)getDefender).getInventory().getLeggings());
               ItemMeta getItemMeta = getItem.getItemMeta();
               
               getItemMeta.setLore(getItemLore);
               getItem.setItemMeta(getItemMeta);
               
               ((Player)getDefender).getInventory().remove(((Player)getDefender).getInventory().getLeggings());
               ((Player)getDefender).getInventory().setLeggings(new ItemStack(getItem));
             } else {
               ((Player)getDefender).playEffect(((Player)getDefender).getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
               ((Player)getDefender).getInventory().setLeggings(new ItemStack(Material.AIR));
             }
           }
         }
       }
       
 
       if ((((Player)getDefender).getInventory().getBoots() != null) && (ItemLoreStats.plugin.isBoots(((Player)getDefender).getInventory().getBoots().getType())) && 
         (((Player)getDefender).getInventory().getBoots().getItemMeta().hasLore()))
       {
         List<String> getItemLore = ((Player)getDefender).getInventory().getBoots().getItemMeta().getLore();
         
         for (String getDurability : getItemLore) {
           if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
             String durabilityRebuilder = "";
             String durabilityAmountColour = "";
             String prefixColourOnly = "";
             
             int index = getItemLore.indexOf(getDurability);
             String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
             int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - amount;
             
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //((Player)getDefender).getInventory().getBoots().setDurability((short)(int)Math.abs(value * ((Player)getDefender).getInventory().getBoots().getType().getMaxDurability() - ((Player)getDefender).getInventory().getBoots().getType().getMaxDurability()));
             
             if (calculateNewValue + 1 > 1) {
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
                 if (getDurability.length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
                   } else {
                     prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                   }
                 } else {
                   prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
                 }
               } else {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
               }
               
               if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                 if (getDurability.split(this.durabilitySplitter)[1].trim().length() > 4) {
                   if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(2, 4);
                   } else {
                     durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                   }
                 } else {
                   durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
                 }
               } else {
                 durabilityAmountColour = prefixColourOnly;
               }
               
               if (this.vanilla_Unbreaking.armourHasUnbreaking(((Player)getDefender).getInventory().getBoots())) {
                 calculateNewValue += this.vanilla_Unbreaking.calculateNewDurabilityLoss(((Player)getDefender).getInventory().getBoots().getEnchantmentLevel(Enchantment.DURABILITY), durabilityLost);
               }
               
 
 
               durabilityRebuilder = calculateNewDurability(calculateNewValue, maximumValue, this.util_Colours.replaceTooltipColour(prefixColourOnly), this.util_Colours.replaceTooltipColour(durabilityAmountColour), ((Player)getDefender).getInventory().getBoots(), (Player)getDefender);
               
               getItemLore.set(index, durabilityRebuilder);
               
               ItemStack getItem = new ItemStack(((Player)getDefender).getInventory().getBoots());
               ItemMeta getItemMeta = getItem.getItemMeta();
               
               getItemMeta.setLore(getItemLore);
               getItem.setItemMeta(getItemMeta);
               
               ((Player)getDefender).getInventory().remove(((Player)getDefender).getInventory().getBoots());
               ((Player)getDefender).getInventory().setBoots(new ItemStack(getItem));
             } else {
               ((Player)getDefender).playEffect(((Player)getDefender).getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
               ((Player)getDefender).getInventory().setBoots(new ItemStack(Material.AIR));
             }
           }
         }
       }
     }
     else if ((getDefender instanceof LivingEntity))
     {
       if ((((LivingEntity)getDefender).getEquipment().getHelmet() != null) && (ItemLoreStats.plugin.isHelmet(((LivingEntity)getDefender).getEquipment().getHelmet().getType()))) {
         ((LivingEntity)getDefender).getEquipment().getHelmet().setDurability((short)0);
         if (((LivingEntity)getDefender).getEquipment().getHelmet().getItemMeta().hasLore())
         {
           List<String> getItemLore = ((LivingEntity)getDefender).getEquipment().getHelmet().getItemMeta().getLore();
           
           for (String getDurability : getItemLore) {
             if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
               int index = getItemLore.indexOf(getDurability);
               String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
               String currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("]", "").trim();
               
               if (Integer.parseInt(currentValue) > 1) {
                 String durabilityRebuilder = this.durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + this.durabilitySplitter + maximumValue;
                 getItemLore.set(index, durabilityRebuilder);
                 
                 ItemStack getHelmetItem = new ItemStack(((LivingEntity)getDefender).getEquipment().getHelmet());
                 ItemMeta getHelmetItemMeta = getHelmetItem.getItemMeta();
                 
                 getHelmetItemMeta.setLore(getItemLore);
                 getHelmetItem.setItemMeta(getHelmetItemMeta);
                 
                 ((LivingEntity)getDefender).getEquipment().setHelmet(new ItemStack(Material.AIR));
                 ((LivingEntity)getDefender).getEquipment().setHelmet(new ItemStack(getHelmetItem));
               } else {
                 ((LivingEntity)getDefender).getEquipment().setHelmet(new ItemStack(Material.AIR));
               }
             }
           }
         }
       }
       
       if ((((LivingEntity)getDefender).getEquipment().getChestplate() != null) && (ItemLoreStats.plugin.isChestplate(((LivingEntity)getDefender).getEquipment().getChestplate().getType()))) {
         ((LivingEntity)getDefender).getEquipment().getChestplate().setDurability((short)0);
         if (((LivingEntity)getDefender).getEquipment().getChestplate().getItemMeta().hasLore())
         {
           List<String> getItemLore = ((LivingEntity)getDefender).getEquipment().getChestplate().getItemMeta().getLore();
           
           for (String getDurability : getItemLore) {
             if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
               int index = getItemLore.indexOf(getDurability);
               String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
               String currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("]", "").trim();
               
               if (Integer.parseInt(currentValue) > 1) {
                 String durabilityRebuilder = this.durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + this.durabilitySplitter + maximumValue;
                 getItemLore.set(index, durabilityRebuilder);
                 
                 ItemStack getChestItem = new ItemStack(((LivingEntity)getDefender).getEquipment().getChestplate());
                 ItemMeta getChestItemMeta = getChestItem.getItemMeta();
                 
                 getChestItemMeta.setLore(getItemLore);
                 getChestItem.setItemMeta(getChestItemMeta);
                 
                 ((LivingEntity)getDefender).getEquipment().setChestplate(new ItemStack(Material.AIR));
                 ((LivingEntity)getDefender).getEquipment().setChestplate(new ItemStack(getChestItem));
               } else {
                 ((LivingEntity)getDefender).getEquipment().setChestplate(new ItemStack(Material.AIR));
               }
             }
           }
         }
       }
       
       if ((((LivingEntity)getDefender).getEquipment().getLeggings() != null) && (ItemLoreStats.plugin.isLeggings(((LivingEntity)getDefender).getEquipment().getLeggings().getType()))) {
         ((LivingEntity)getDefender).getEquipment().getLeggings().setDurability((short)0);
         if (((LivingEntity)getDefender).getEquipment().getLeggings().getItemMeta().hasLore())
         {
           List<String> getItemLore = ((LivingEntity)getDefender).getEquipment().getLeggings().getItemMeta().getLore();
           
           for (String getDurability : getItemLore) {
             if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
               int index = getItemLore.indexOf(getDurability);
               String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
               String currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("]", "").trim();
               
               if (Integer.parseInt(currentValue) > 1) {
                 String durabilityRebuilder = this.durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + this.durabilitySplitter + maximumValue;
                 getItemLore.set(index, durabilityRebuilder);
                 
                 ItemStack getLegsItem = new ItemStack(((LivingEntity)getDefender).getEquipment().getLeggings());
                 ItemMeta getLegsItemMeta = getLegsItem.getItemMeta();
                 
                 getLegsItemMeta.setLore(getItemLore);
                 getLegsItem.setItemMeta(getLegsItemMeta);
                 
                 ((LivingEntity)getDefender).getEquipment().setLeggings(new ItemStack(Material.AIR));
                 ((LivingEntity)getDefender).getEquipment().setLeggings(new ItemStack(getLegsItem));
               } else {
                 ((LivingEntity)getDefender).getEquipment().setLeggings(new ItemStack(Material.AIR));
               }
             }
           }
         }
       }
       
       if ((((LivingEntity)getDefender).getEquipment().getBoots() != null) && (ItemLoreStats.plugin.isBoots(((LivingEntity)getDefender).getEquipment().getBoots().getType()))) {
         ((LivingEntity)getDefender).getEquipment().getBoots().setDurability((short)0);
         if (((LivingEntity)getDefender).getEquipment().getBoots().getItemMeta().hasLore())
         {
           List<String> getItemLore = ((LivingEntity)getDefender).getEquipment().getBoots().getItemMeta().getLore();
           
           for (String getDurability : getItemLore) {
             if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
               int index = getItemLore.indexOf(getDurability);
               String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
               String currentValue = ChatColor.stripColor(getDurability).trim().replace("[", "").replace(":", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("]", "").trim();
               
               if (Integer.parseInt(currentValue) > 1) {
                 String durabilityRebuilder = this.durabilityName + ": " + (Integer.parseInt(currentValue) - 1) + this.durabilitySplitter + maximumValue;
                 getItemLore.set(index, durabilityRebuilder);
                 
                 ItemStack getBootsItem = new ItemStack(((LivingEntity)getDefender).getEquipment().getBoots());
                 ItemMeta getBootsItemMeta = getBootsItem.getItemMeta();
                 
                 getBootsItemMeta.setLore(getItemLore);
                 getBootsItem.setItemMeta(getBootsItemMeta);
                 
                 ((LivingEntity)getDefender).getEquipment().setBoots(new ItemStack(Material.AIR));
                 ((LivingEntity)getDefender).getEquipment().setBoots(new ItemStack(getBootsItem));
               } else {
                 ((LivingEntity)getDefender).getEquipment().setBoots(new ItemStack(Material.AIR));
               }
             }
           }
         }
       }
     }
   }
   
   public void syncArmourDurability(Player player) {
     setDurabilityStrings();
     
     if ((player.getInventory().getHelmet() != null) && 
       (player.getInventory().getHelmet().getItemMeta().hasLore())) {
       List<String> getItemLore = player.getInventory().getHelmet().getItemMeta().getLore();
       
       for (String getDurability : getItemLore) {
         if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
           String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
           int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
           
           if (calculateNewValue > 1) {
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             player.getInventory().getHelmet().setDurability((short)(int)Math.abs(value * player.getInventory().getHelmet().getType().getMaxDurability() - player.getInventory().getHelmet().getType().getMaxDurability()));
           }
         }
       }
     }
     
 
 
 
 
 
 
 
 
     if ((player.getInventory().getChestplate() != null) && 
       (player.getInventory().getChestplate().getItemMeta().hasLore())) {
       List<String> getItemLore = player.getInventory().getChestplate().getItemMeta().getLore();
       
       for (String getDurability : getItemLore) {
         if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
           String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
           int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
           
           if (calculateNewValue > 1) {
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //player.getInventory().getChestplate().setDurability((short)(int)Math.abs(value * player.getInventory().getChestplate().getType().getMaxDurability() - player.getInventory().getChestplate().getType().getMaxDurability()));
           }
         }
       }
     }
     
 
 
 
 
 
 
 
 
     if ((player.getInventory().getLeggings() != null) && 
       (player.getInventory().getLeggings().getItemMeta().hasLore())) {
       List<String> getItemLore = player.getInventory().getLeggings().getItemMeta().getLore();
       
       for (String getDurability : getItemLore) {
         if ((getDurability != null) && (ChatColor.stripColor(getDurability).startsWith(this.durabilityName))) {
           String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
           int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
           
           if (calculateNewValue > 1) {
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //player.getInventory().getLeggings().setDurability((short)(int)Math.abs(value * player.getInventory().getLeggings().getType().getMaxDurability() - player.getInventory().getLeggings().getType().getMaxDurability()));
           }
         }
       }
     }
     
 
 
 
 
 
 
 
 
     if ((player.getInventory().getBoots() != null) && 
       (player.getInventory().getBoots().getItemMeta().hasLore())) {
       List<String> getItemLore = player.getInventory().getBoots().getItemMeta().getLore();
       
       for (String getDurability : getItemLore) {
         if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
           String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
           int calculateNewValue = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
           
           if (calculateNewValue > 1) {
             double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
             //player.getInventory().getBoots().setDurability((short)(int)Math.abs(value * player.getInventory().getBoots().getType().getMaxDurability() - player.getInventory().getBoots().getType().getMaxDurability()));
           }
         }
       }
     }
     
 
 
 
 
 
 
 
 
     final Player playerFinal = player;
     ItemLoreStats.plugin.getServer().getScheduler().scheduleSyncDelayedTask(ItemLoreStats.plugin, new Runnable()
     {
       public void run() {
         playerFinal.updateInventory();
       }
     }, 1L);
   }
 



public ItemStack repairbyamount(Player player, int amount, ItemStack item)
{

     if ( 
       (item != null) && (ItemLoreStats.plugin.isTool(item.getType())) && 
       (item.getItemMeta().hasLore())) {
       setDurabilityStrings();


       
       List<String> getItemLore = item.getItemMeta().getLore();
       
       for (String getDurability : getItemLore) {
         if (ChatColor.stripColor(getDurability).startsWith(this.durabilityName)) {
           String durabilityRebuilder = "";
           String durabilityAmountColour = "";
           String prefixColourOnly = "";


           
           prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability));
           
           int index = getItemLore.indexOf(getDurability);
           String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(this.durabilityName.length()).split(this.durabilitySplitter)[1].replace("]", "").trim();
           int actualvalue= Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(this.durabilityName.length()).split(this.durabilitySplitter)[0].replace("§", "").replace("]", "").trim());
           int calculateNewValue = actualvalue + amount;
           int maxvalue = (int) Double.valueOf(maximumValue).doubleValue();
           
           if (calculateNewValue > maxvalue) {
        	   calculateNewValue = maxvalue;
           }
           if (maxvalue <= actualvalue) {
        	   return null;
           }
           
           
           double value = Double.valueOf(calculateNewValue).doubleValue() / Double.valueOf(maximumValue).doubleValue();
           //itemInHand.setDurability((short)(int)Math.abs(value * itemInHand.getType().getMaxDurability() - itemInHand.getType().getMaxDurability()));
           
           if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
             if (getDurability.length() > 4) {
               if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)).contains("&")) {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(2, 4)));
               } else {
                 prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
               }
             } else {
               prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)));
             }
           } else {
             prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
           }
           
           if ((getDurability.split(this.durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
             if (getDurability.split(this.durabilitySplitter)[1].trim().length() > 4) {
               if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                 durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(2, 4);
               } else {
                 durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
               }
             } else {
               durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(this.durabilitySplitter)[1].trim()).substring(0, 2);
             }
           } else {
             durabilityAmountColour = prefixColourOnly;
           }
 
           durabilityRebuilder = calculateNewDurability(calculateNewValue, maximumValue, this.util_Colours.replaceTooltipColour(prefixColourOnly), this.util_Colours.replaceTooltipColour(durabilityAmountColour), item, player);
           
           getItemLore.set(index, durabilityRebuilder);
         }
       }
       
       ItemStack setItemInHand = new ItemStack(item);
       ItemMeta setItemMeta = setItemInHand.getItemMeta();
       
       setItemMeta.setLore(getItemLore);
       item.setItemMeta(setItemMeta);
       
       return item;
     }
     return null;
   }
 }
