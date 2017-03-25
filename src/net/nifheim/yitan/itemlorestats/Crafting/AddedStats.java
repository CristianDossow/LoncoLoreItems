 package net.nifheim.yitan.itemlorestats.Crafting;
 
 import net.nifheim.yitan.itemlorestats.Main;
 import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
 import net.nifheim.yitan.itemlorestats.Util.Util_Format;
 import java.util.ArrayList;
 import java.util.List;
 import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.entity.EntityDeathEvent;
 import org.bukkit.event.inventory.PrepareItemCraftEvent;
 import org.bukkit.inventory.CraftingInventory;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.Recipe;
 import org.bukkit.inventory.meta.ItemMeta;
 
 public class AddedStats implements org.bukkit.event.Listener
 {

   Util_Colours util_Colours = new Util_Colours();
   Util_Format util_Format = new Util_Format();
   
   private int randomRange(int min, int max) {
     return (int)(min + Math.random() * (max - min));
   }
   
   private double randomRangeDouble(double min, double max) { return min + Math.random() * (max - min); }

   @EventHandler
   public void addStatsToCraftedItem(PrepareItemCraftEvent event)
   {
	   /*
   }
     if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getView().getPlayer().getWorld().getName())) {
       if (event.isRepair()) {
         event.getInventory().setResult(null);
       }
       else if ((!event.getRecipe().getResult().getItemMeta().hasLore()) && (
         (ItemLoreStats.plugin.isTool(event.getRecipe().getResult().getType())) || (ItemLoreStats.plugin.isArmour(event.getRecipe().getResult().getType()))))
       {
         ItemStack craftedItem = event.getRecipe().getResult();
         
         boolean rename = false;
         
         List<String> setItemLore = new ArrayList();
         
 
         if (ItemLoreStats.plugin.isArmour(event.getRecipe().getResult().getType())) {
           if (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedArmour.enableArmourOnCrafted"))
           {
 
             String armourName = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
             
             String type = "";
             String selectedArmour = "";
             
             if (craftedItem.getType().toString().contains("LEATHER")) {
               type = "leather";
               rename = true;
             } else if (craftedItem.getType().toString().contains("CHAINMAIL")) {
               type = "chainmail";
               rename = true;
             } else if (craftedItem.getType().toString().contains("IRON")) {
               type = "iron";
               rename = true;
             } else if (craftedItem.getType().toString().contains("GOLD")) {
               type = "gold";
               rename = true;
             } else if (craftedItem.getType().toString().contains("DIAMOND")) {
               type = "diamond";
               rename = true;
             } else if (craftedItem.getType().toString().contains("SKULL_ITEM")) {
               type = "mobHead";
               rename = true;
             } else {
               type = null;
               rename = false;
             }
             
             if (type != null) {
               setItemLore.add("");
               if ((ItemLoreStats.plugin.isArmour(craftedItem.getType())) && 
                 (ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type) != null)) {
                 if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).contains("-")) {
                   selectedArmour = String.valueOf(randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                   String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", armourName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedArmour))));
                   setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
                 }
                 else {
                   selectedArmour = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).replaceAll("&([0-9a-f])", "").trim());
                   String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", armourName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedArmour))));
                   setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
                 }
                 
               }
             }
           }
         }
         else if ((ItemLoreStats.plugin.isTool(event.getRecipe().getResult().getType())) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDamage.enableDamageOnCrafted")))
         {
           String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
           
           String type = "";
           String selectedToolMin = "";
           String selectedToolMax = "";
           
           if (craftedItem.getType().toString().contains("BOW")) {
             type = "bow";
             rename = true;
           } else if (craftedItem.getType().toString().contains("WOOD")) {
             type = "wood";
             rename = true;
           } else if (craftedItem.getType().toString().contains("STONE")) {
             type = "stone";
             rename = true;
           } else if (craftedItem.getType().toString().contains("IRON")) {
             type = "iron";
             rename = true;
           } else if (craftedItem.getType().toString().contains("GOLD")) {
             type = "gold";
             rename = true;
           } else if (craftedItem.getType().toString().contains("DIAMOND")) {
             type = "diamond";
             rename = true;
           } else {
             type = null;
             rename = false;
           }
           
           if (type != null) {
             setItemLore.add("");
             if ((ItemLoreStats.plugin.isTool(craftedItem.getType())) && 
               (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type) != null)) {
               if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).contains("-")) {
                 selectedToolMin = String.valueOf(randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                 selectedToolMax = String.valueOf(randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(selectedToolMin)));
                 String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", damageName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedToolMin)))).replace("[statValue2]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedToolMax))));
                 setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
               } else {
                 selectedToolMin = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).replaceAll("&([0-9a-f])", "").trim());
                 selectedToolMax = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).replaceAll("&([0-9a-f])", "").trim());
                 
                 String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", damageName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedToolMin)))).replace("-[statValue2]", "");
                 setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
               }
             }
           }
         }
         
 
 
         if ((!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) && 
           (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDurability.enableDurabilityOnCrafted")))
         {
           String durabilityColour = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.colour");
           String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
           String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
           
           String type = "";
           String selectedDurability = "";
           
           if (craftedItem.getType().toString().contains("WOOD")) {
             type = "wood";
             rename = true;
           } else if (craftedItem.getType().toString().contains("LEATHER")) {
             type = "leather";
             rename = true;
           } else if (craftedItem.getType().toString().contains("STONE")) {
             type = "stone";
             rename = true;
           } else if (craftedItem.getType().toString().contains("CHAINMAIL")) {
             type = "chainmail";
             rename = true;
           } else if (craftedItem.getType().toString().contains("IRON")) {
             type = "iron";
             rename = true;
           } else if (craftedItem.getType().toString().contains("GOLD")) {
             type = "gold";
             rename = true;
           } else if (craftedItem.getType().toString().contains("DIAMOND")) {
             type = "diamond";
             rename = true;
           } else if (craftedItem.getType().toString().contains("BOW")) {
             type = "bow";
             rename = true;
           } else if (craftedItem.getType().toString().contains("SHEARS")) {
             type = "shears";
             rename = true;
           } else if (craftedItem.getType().toString().contains("STICK")) {
             type = null;
             rename = false;
           } else if (craftedItem.getType().toString().contains("BLAZE_ROD")) {
             type = null;
             rename = false;
           } else if (craftedItem.getType().toString().contains("STRING")) {
             type = null;
             rename = false;
           } else if (craftedItem.getType().toString().contains("FLINT_AND_STEEL")) {
             type = "flintAndSteel";
             rename = true;
           } else if (craftedItem.getType().toString().contains("FISHING_ROD")) {
             type = "fishingRod";
             rename = true;
           } else if (craftedItem.getType().toString().contains("CARROT_STICK")) {
             type = "carrotStick";
             rename = true;
           } else if (craftedItem.getType().toString().contains("SKULL_ITEM")) {
             type = "mobHead";
             rename = true;
           } else {
             type = null;
             rename = false;
           }
           
           if (type != null) {
             setItemLore.add("");
             if (ItemLoreStats.plugin.isTool(craftedItem.getType())) {
               if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type) != null) {
                 if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).contains("-")) {
                   selectedDurability = String.valueOf(randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                   setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability);
                 } else {
                   selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).replaceAll("&([0-9a-f])", "").trim());
                   setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability);
                 }
               }
             } else if ((ItemLoreStats.plugin.isArmour(craftedItem.getType())) && 
               (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type) != null)) {
               if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).contains("-")) {
                 selectedDurability = String.valueOf(randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                 setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability);
               } else {
                 selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).replaceAll("&([0-9a-f])", "").trim());
                 setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability);
               }
             }
           }
         }
         
 
 
         if (rename) {
           ItemMeta craftedItemMeta = craftedItem.getItemMeta();
           
           craftedItemMeta.setDisplayName(org.bukkit.ChatColor.RESET + event.getRecipe().getResult().getType().toString()
             .replaceAll("SKULL_ITEM", "Decapitated Head")
             .replaceAll("WOOD_", "Wooden ")
             .replaceAll("LEATHER_", "Leather ")
             .replaceAll("STONE_", "Stone ")
             .replaceAll("CHAINMAIL_", "Chainmail ")
             .replaceAll("IRON_", "Iron ")
             .replaceAll("GOLD_", "Golden ")
             .replaceAll("DIAMOND_", "Diamond ")
             .replaceAll("FISHING_ROD", "Fishing Rod")
             .replaceAll("BOW", "Bow")
             .replaceAll("SHEARS", "Shears")
             .replaceAll("FLINT_AND_STEEL", "Flint and Steel")
             .replaceAll("CARROT_STICK", "Carrot on a Stick")
             .replaceAll("SWORD", "Sword")
             .replaceAll("HOE", "Hoe")
             .replaceAll("SPADE", "Spade")
             .replaceAll("PICKAXE", "Pickaxe")
             .replaceAll("AXE", "Axe")
             .replaceAll("HELMET", "Helmet")
             .replaceAll("CHESTPLATE", "Chestplate")
             .replaceAll("LEGGINGS", "Leggings")
             .replaceAll("BOOTS", "Boots"));
           
           craftedItemMeta.setLore(setItemLore);
           craftedItem.setItemMeta(craftedItemMeta);
           
           event.getInventory().setResult(craftedItem);
         }
       }
     }
   */}
   
 
 
   @EventHandler
   public void addStatsToDroppedItem(EntityDeathEvent event)
   {/*
     if ((event.getEntity() instanceof org.bukkit.entity.Player)) return;
     if (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getLocation().getWorld().getName())) {
       for (int i = 0; i < event.getDrops().size(); i++) {
         if ((!((ItemStack)event.getDrops().get(i)).getItemMeta().hasLore()) && 
           ((ItemLoreStats.plugin.isTool(((ItemStack)event.getDrops().get(i)).getType())) || (ItemLoreStats.plugin.isArmour(((ItemStack)event.getDrops().get(i)).getType()))) && 
           (((ItemStack)event.getDrops().get(i)).getItemMeta().getDisplayName() == null) && (
           (!((ItemStack)event.getDrops().get(i)).getItemMeta().hasLore()) || (!((ItemStack)event.getDrops().get(i)).getItemMeta().getLore().toString().contains(ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name") + ":"))))
         {
           ItemStack droppedItem = (ItemStack)event.getDrops().get(i);
           ItemMeta droppedItemMeta = droppedItem.getItemMeta();
           
           boolean rename = false;
           
           List<String> setItemLore = new ArrayList();
           
           if (ItemLoreStats.plugin.isArmour(droppedItem.getType())) {
             if (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedArmour.enableArmourOnDrops")) {
               String armourName = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
               
               String type = "";
               String selectedArmour = "";
               
               if (droppedItem.getType().toString().contains("LEATHER")) {
                 type = "leather";
                 rename = true;
               } else if (droppedItem.getType().toString().contains("CHAINMAIL")) {
                 type = "chainmail";
                 rename = true;
               } else if (droppedItem.getType().toString().contains("IRON")) {
                 type = "iron";
                 rename = true;
               } else if (droppedItem.getType().toString().contains("GOLD")) {
                 type = "gold";
                 rename = true;
               } else if (droppedItem.getType().toString().contains("DIAMOND")) {
                 type = "diamond";
                 rename = true;
               } else if (droppedItem.getType().toString().contains("SKULL_ITEM")) {
                 type = "mobHead";
                 rename = true;
               } else {
                 type = null;
                 rename = false;
               }
               
               if (type != null) {
                 setItemLore.add("");
                 if ((ItemLoreStats.plugin.isArmour(droppedItem.getType())) && 
                   (ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type) != null)) {
                   if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).contains("-")) {
                     selectedArmour = String.valueOf(randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                     String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", armourName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedArmour))));
                     setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
                   }
                   else {
                     selectedArmour = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armour." + type).replaceAll("&([0-9a-f])", "").trim());
                     String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedArmour.armourStatFormat").replace("[statName]", armourName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedArmour))));
                     setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
                   }
                   
                 }
               }
             }
           }
           else if ((ItemLoreStats.plugin.isTool(droppedItem.getType())) && 
             (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDamage.enableDamageOnDrops")))
           {
             String damageName = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
             
             String type = "";
             String selectedToolMin = "";
             String selectedToolMax = "";
             
             if (droppedItem.getType().toString().contains("BOW")) {
               type = "bow";
               rename = true;
             } else if (droppedItem.getType().toString().contains("WOOD")) {
               type = "wood";
               rename = true;
             } else if (droppedItem.getType().toString().contains("STONE")) {
               type = "stone";
               rename = true;
             } else if (droppedItem.getType().toString().contains("IRON")) {
               type = "iron";
               rename = true;
             } else if (droppedItem.getType().toString().contains("GOLD")) {
               type = "gold";
               rename = true;
             } else if (droppedItem.getType().toString().contains("DIAMOND")) {
               type = "diamond";
               rename = true;
             } else {
               type = null;
               rename = false;
             }
             
             if (type != null) {
               setItemLore.add("");
               if ((ItemLoreStats.plugin.isTool(droppedItem.getType())) && 
                 (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type) != null)) {
                 if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).contains("-")) {
                   selectedToolMin = String.valueOf(randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                   selectedToolMax = String.valueOf(randomRangeDouble(Double.parseDouble(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Double.parseDouble(selectedToolMin)));
                   String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", damageName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedToolMin)))).replace("[statValue2]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedToolMax))));
                   setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
                 } else {
                   selectedToolMin = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).replaceAll("&([0-9a-f])", "").trim());
                   selectedToolMax = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.tool." + type).replaceAll("&([0-9a-f])", "").trim());
                   
                   String statFormat = ItemLoreStats.plugin.getConfig().getString("defaultCraftedDamage.damageStatFormat").replace("[statName]", damageName).replace("[statValue]", String.valueOf(this.util_Format.format(Double.parseDouble(selectedToolMin)))).replace("-[statValue2]", "");
                   setItemLore.add(this.util_Colours.replaceTooltipColour(statFormat));
                 }
               }
             }
           }
           
 
 
           if ((!ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) && 
             (ItemLoreStats.plugin.getConfig().getBoolean("defaultCraftedDurability.enableDurabilityOnDrops"))) {
             String durabilityColour = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.colour");
             String durabilityName = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
             String durabilitySplitter = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.splitter");
             
             String type = "";
             String selectedDurability = "";
             
             if (droppedItem.getType().toString().contains("WOOD")) {
               type = "wood";
               rename = true;
             } else if (droppedItem.getType().toString().contains("LEATHER")) {
               type = "leather";
               rename = true;
             } else if (droppedItem.getType().toString().contains("STONE")) {
               type = "stone";
               rename = true;
             } else if (droppedItem.getType().toString().contains("CHAINMAIL")) {
               type = "chainmail";
               rename = true;
             } else if (droppedItem.getType().toString().contains("IRON")) {
               type = "iron";
               rename = true;
             } else if (droppedItem.getType().toString().contains("GOLD")) {
               type = "gold";
               rename = true;
             } else if (droppedItem.getType().toString().contains("DIAMOND")) {
               type = "diamond";
               rename = true;
             } else if (droppedItem.getType().toString().contains("BOW")) {
               type = "bow";
               rename = true;
             } else if (droppedItem.getType().toString().contains("SHEARS")) {
               type = "shears";
               rename = true;
             } else if (droppedItem.getType().toString().contains("STICK")) {
               type = null;
               rename = false;
             } else if (droppedItem.getType().toString().contains("BLAZE_ROD")) {
               type = null;
               rename = false;
             } else if (droppedItem.getType().toString().contains("STRING")) {
               type = null;
               rename = false;
             } else if (droppedItem.getType().toString().contains("FLINT_AND_STEEL")) {
               type = "flintAndSteel";
               rename = true;
             } else if (droppedItem.getType().toString().contains("FISHING_ROD")) {
               type = "fishingRod";
               rename = true;
             } else if (droppedItem.getType().toString().contains("CARROT_STICK")) {
               type = "carrotStick";
               rename = true;
             } else if (droppedItem.getType().toString().contains("SKULL_ITEM")) {
               type = "mobHead";
               rename = true;
             } else {
               type = null;
             }
             
             if (type != null) {
               setItemLore.add("");
               if (ItemLoreStats.plugin.isTool(droppedItem.getType())) {
                 if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type) != null) {
                   if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).contains("-")) {
                     selectedDurability = String.valueOf(randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                     setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability);
                   } else {
                     selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.tools." + type).replaceAll("&([0-9a-f])", "").trim());
                     setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.tools.").append(type).toString()))) + selectedDurability);
                   }
                 }
               } else if ((ItemLoreStats.plugin.isArmour(droppedItem.getType())) && 
                 (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type) != null)) {
                 if (ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).contains("-")) {
                   selectedDurability = String.valueOf(randomRange(Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[1].replaceAll("&([0-9a-f])", "").trim()), Integer.parseInt(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).split("-")[0].replaceAll("&([0-9a-f])", "").trim())));
                   setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability);
                 } else {
                   selectedDurability = String.valueOf(ItemLoreStats.plugin.getConfig().getString("defaultCraftedDurability.armour." + type).replaceAll("&([0-9a-f])", "").trim());
                   setItemLore.add(this.util_Colours.replaceTooltipColour(durabilityColour) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability + this.util_Colours.replaceTooltipColour(durabilityColour) + durabilitySplitter + this.util_Colours.replaceTooltipColour(this.util_Colours.extractTooltipColour(ItemLoreStats.plugin.getConfig().getString(new StringBuilder("defaultCraftedDurability.armour.").append(type).toString()))) + selectedDurability);
                 }
               }
             }
           }
           
 
 
           if (rename) {
             droppedItemMeta.setDisplayName(org.bukkit.ChatColor.RESET + ((ItemStack)event.getDrops().get(i)).getType().toString().replaceAll("SKULL_ITEM", "Decapitated Head")
               .replaceAll("WOOD_", "Wooden ")
               .replaceAll("LEATHER_", "Leather ")
               .replaceAll("STONE_", "Stone ")
               .replaceAll("IRON_", "Iron ")
               .replaceAll("GOLD_", "Golden ")
               .replaceAll("DIAMOND_", "Diamond ")
               .replaceAll("FISHING_ROD", "Fishing Rod")
               .replaceAll("BOW", "Bow")
               .replaceAll("SHEARS", "Shears")
               .replaceAll("FLINT_AND_STEEL", "Flint and Steel")
               .replaceAll("CARROT_STICK", "Carrot on a Stick")
               .replaceAll("SWORD", "Sword")
               .replaceAll("HOE", "Hoe")
               .replaceAll("SPADE", "Spade")
               .replaceAll("PICKAXE", "Pickaxe")
               .replaceAll("AXE", "Axe")
               .replaceAll("HELMET", "Helmet")
               .replaceAll("CHESTPLATE", "Chestplate")
               .replaceAll("LEGGINGS", "Leggings")
               .replaceAll("BOOTS", "Boots"));
           }
           
           droppedItemMeta.setLore(setItemLore);
           droppedItem.setItemMeta(droppedItemMeta);
           
           if (event.getDrops().get(i) != null) event.getDrops().remove(i);
           event.getDrops().add(droppedItem);
         }
       }
     }
      */
   }
}
