 package net.nifheim.yitan.itemlorestats.Repair;
 
 import net.nifheim.yitan.itemlorestats.Main;

import org.bukkit.Bukkit;
 import org.bukkit.Material;
 import org.bukkit.block.Block;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.Player;
 import org.bukkit.event.block.Action;
 import org.bukkit.event.player.PlayerInteractEvent;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 
 public class RepairEvents implements org.bukkit.event.Listener
 {
   Repair repair = new Repair();
   
   public Material getRepairBlock() {
     if (Main.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairBlock") != null) {
       Material repairBlock = Material.getMaterial(Main.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairBlock"));
       return repairBlock;
     }
     return Material.WORKBENCH;
   }
/*
   @org.bukkit.event.EventHandler
   public void repairItemOnLeftClick(PlayerInteractEvent event) {
	       
     if ((ItemLoreStats.plugin.getConfig().getBoolean("enableItemRepairing")) && (
       ((event.getAction() == Action.LEFT_CLICK_BLOCK) && (ItemLoreStats.plugin.isTool(event.getPlayer().getInventory().getItemInMainHand().getType()))) || ((event.getAction() == Action.LEFT_CLICK_BLOCK) && (ItemLoreStats.plugin.isArmour(event.getPlayer().getInventory().getItemInMainHand().getType())) && 
        
       (!ItemLoreStats.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName()))))) {
       if ((ItemLoreStats.plugin.getConfig().getBoolean("usingMcMMO")) || (event.getClickedBlock() == null)) { return;
       }
       Player player = event.getPlayer();
       if(event.getClickedBlock().getType().equals(getRepairBlock()))
       if ((ItemLoreStats.plugin.isTool(player.getInventory().getItemInMainHand().getType())) || (ItemLoreStats.plugin.isArmour(player.getInventory().getItemInMainHand().getType()))) {
         this.repair.payAndRepair(player, player.getInventory().getItemInMainHand().getType());
       }
     }
           
   }
   */
 }
