 package com.github.supavitax.itemlorestats.Commands;
 
 import com.github.supavitax.itemlorestats.ItemLoreStats;
 import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
 import java.util.ArrayList;
 import org.bukkit.ChatColor;
 import org.bukkit.Material;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.PlayerInventory;
 import org.bukkit.inventory.meta.ItemMeta;
 
 public class CreateLore_Com
 {
   Util_GetResponse util_GetResponse = new Util_GetResponse();
   
   public void onCreateLoreCommand(org.bukkit.command.CommandSender sender, String[] args) {
     if ((sender instanceof Player)) {
       Player player = (Player)sender;
       if ((player.isOp()) || (player.hasPermission("ils.admin")))
       {
         String armour = ItemLoreStats.plugin.getConfig().getString("primaryStats.armour.name");
         String critChance = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critChance.name");
         String critDamage = ItemLoreStats.plugin.getConfig().getString("secondaryStats.critDamage.name");
         String damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
         String health = ItemLoreStats.plugin.getConfig().getString("primaryStats.health.name");
         String healthRegen = ItemLoreStats.plugin.getConfig().getString("primaryStats.healthRegen.name");
         String lifeSteal = ItemLoreStats.plugin.getConfig().getString("secondaryStats.lifeSteal.name");
         String reflect = ItemLoreStats.plugin.getConfig().getString("secondaryStats.reflect.name");
         String fire = ItemLoreStats.plugin.getConfig().getString("secondaryStats.fire.name");
         String ice = ItemLoreStats.plugin.getConfig().getString("secondaryStats.ice.name");
         String poison = ItemLoreStats.plugin.getConfig().getString("secondaryStats.poison.name");
         String wither = ItemLoreStats.plugin.getConfig().getString("secondaryStats.wither.name");
         String harming = ItemLoreStats.plugin.getConfig().getString("secondaryStats.harming.name");
         String movementspeed = ItemLoreStats.plugin.getConfig().getString("secondaryStats.movementSpeed.name");
         String weaponspeed = ItemLoreStats.plugin.getConfig().getString("bonusStats.weaponSpeed.name");
         String xplevel = ItemLoreStats.plugin.getConfig().getString("bonusStats.xpLevel.name");
         String soulbound = ItemLoreStats.plugin.getConfig().getString("bonusStats.soulbound.name");
         String durability = ItemLoreStats.plugin.getConfig().getString("bonusStats.durability.name");
         
         ItemStack debugItem = new ItemStack(Material.LEATHER_HELMET, 1);
         
         ItemMeta debugItemMeta = debugItem.getItemMeta();
         
         ArrayList<String> debugItemList = new ArrayList();

         debugItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Fire-Chanter Wrap");
         debugItemList.add("");
         debugItemList.add(ChatColor.AQUA + armour + ": " + ChatColor.DARK_GREEN + "2.0" + ChatColor.GREEN + "%");
         debugItemList.add(ChatColor.AQUA + health + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "45.1");
         debugItemList.add(ChatColor.AQUA + healthRegen + ": " + ChatColor.DARK_GREEN + "3.1" + ChatColor.GREEN + "%");
         debugItemList.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "9.0" + ChatColor.GREEN + "%");
         debugItemList.add(ChatColor.DARK_RED + wither + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
         debugItemList.add("");
         debugItemList.add(ChatColor.GOLD + durability + ": " + "1250/1250");
         debugItemList.add("");
         debugItemList.add(ChatColor.DARK_AQUA + soulbound + " " + player.getName());
         debugItemMeta.setLore(debugItemList);
         debugItem.setItemMeta(debugItemMeta);
         player.getInventory().addItem(new ItemStack[] { new ItemStack(debugItem) });
         
         ItemStack debugItem1 = new ItemStack(Material.IRON_CHESTPLATE, 1);
         ItemMeta debugItemMeta1 = debugItem1.getItemMeta();
         ArrayList<String> debugItemList1 = new ArrayList();
         debugItemMeta1.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Revenant Chestplate of Health");
         debugItemList1.add("");
         debugItemList1.add(ChatColor.AQUA + armour + ": " + ChatColor.DARK_GREEN + "5.0" + ChatColor.GREEN + "%");
         debugItemList1.add(ChatColor.AQUA + health + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "109.4");
         debugItemList1.add(ChatColor.AQUA + healthRegen + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
         debugItemList1.add(ChatColor.LIGHT_PURPLE + poison + ": " + ChatColor.DARK_GREEN + "2.0" + ChatColor.GREEN + "%");
         debugItemList1.add(ChatColor.GREEN + lifeSteal + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
         debugItemList1.add("");
         debugItemList1.add(ChatColor.GOLD + durability + ": " + "1750/1750");
         debugItemList1.add("");
         debugItemList1.add(ChatColor.DARK_AQUA + xplevel + ": 2");
         debugItemMeta1.setLore(debugItemList1);
         debugItem1.setItemMeta(debugItemMeta1);
         player.getInventory().addItem(new ItemStack[] { new ItemStack(debugItem1) });
         
         ItemStack debugItem2 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
         ItemMeta debugItemMeta2 = debugItem2.getItemMeta();
         ArrayList<String> debugItemList2 = new ArrayList();
         debugItemMeta2.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "DragonScale Leg Wraps");
         debugItemList2.add("");
         debugItemList2.add(ChatColor.AQUA + armour + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
         debugItemList2.add(ChatColor.AQUA + health + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "59.7");
         debugItemList2.add(ChatColor.AQUA + healthRegen + ": " + ChatColor.DARK_GREEN + "2.0" + ChatColor.GREEN + "%");
         debugItemList2.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "5.0" + ChatColor.GREEN + "%");
         debugItemList2.add(ChatColor.GREEN + lifeSteal + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
         debugItemList2.add("");
         debugItemList2.add(ChatColor.GOLD + durability + ": " + "1500/1500");
         debugItemList2.add("");
         debugItemList2.add(ChatColor.DARK_AQUA + xplevel + ": 3");
         debugItemMeta2.setLore(debugItemList2);
         debugItem2.setItemMeta(debugItemMeta2);
         player.getInventory().addItem(new ItemStack[] { new ItemStack(debugItem2) });
         
         ItemStack debugItem3 = new ItemStack(Material.DIAMOND_BOOTS, 1);
         ItemMeta debugItemMeta3 = debugItem3.getItemMeta();
         ArrayList<String> debugItemList3 = new ArrayList();
         debugItemMeta3.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Boots of the Black Glacier");
         debugItemList3.add("");
         debugItemList3.add(ChatColor.AQUA + armour + ": " + ChatColor.DARK_GREEN + "9.0" + ChatColor.GREEN + "%");
         debugItemList3.add(ChatColor.AQUA + health + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "97.8");
         debugItemList3.add(ChatColor.AQUA + healthRegen + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
         debugItemList3.add(ChatColor.BLUE + ice + ": " + ChatColor.DARK_GREEN + "5.0" + ChatColor.GREEN + "%");
         debugItemList3.add(ChatColor.DARK_RED + wither + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
         debugItemList3.add(ChatColor.YELLOW + movementspeed + ": " + ChatColor.DARK_GREEN + "8.0" + ChatColor.GREEN + "%");
         debugItemList3.add("");
         debugItemList3.add(ChatColor.GOLD + durability + ": " + "1500/1500");
         debugItemList3.add("");
         debugItemList3.add(ChatColor.DARK_AQUA + xplevel + ": 4");
         debugItemMeta3.setLore(debugItemList3);
         debugItem3.setItemMeta(debugItemMeta3);
         player.getInventory().addItem(new ItemStack[] { new ItemStack(debugItem3) });
         
         ItemStack debugItem4 = new ItemStack(Material.DIAMOND_SWORD, 1);
         ItemMeta debugItemMeta4 = debugItem4.getItemMeta();
         ArrayList<String> debugItemList4 = new ArrayList();
         debugItemMeta4.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Slaying Thrustblade");
         debugItemList4.add("");
         debugItemList4.add(ChatColor.AQUA + weaponspeed + ": " + ChatColor.RED + "Slow");
         debugItemList4.add(ChatColor.AQUA + damage + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "68.1" + ChatColor.DARK_GREEN + "-" + ChatColor.DARK_GREEN + "93.7");
         debugItemList4.add(ChatColor.AQUA + critChance + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
         debugItemList4.add(ChatColor.AQUA + critDamage + ": " + ChatColor.DARK_GREEN + "12.0" + ChatColor.GREEN + "%");
         debugItemList4.add(ChatColor.YELLOW + reflect + ": " + ChatColor.DARK_GREEN + "6.0" + ChatColor.GREEN + "%");
         debugItemList4.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "7.0" + ChatColor.GREEN + "%");
         debugItemList4.add(ChatColor.DARK_PURPLE + harming + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
         debugItemList4.add("");
         debugItemList4.add(ChatColor.GOLD + durability + ": " + "350/350");
         debugItemList4.add("");
         debugItemList4.add(ChatColor.DARK_AQUA + "Level: 1");
         debugItemList4.add(ChatColor.DARK_AQUA + soulbound + " " + player.getName());
         debugItemMeta4.setLore(debugItemList4);
         debugItem4.setItemMeta(debugItemMeta4);
         player.getInventory().addItem(new ItemStack[] { new ItemStack(debugItem4) });
         
         ItemStack debugItem5 = new ItemStack(Material.BOW, 1);
         ItemMeta debugItemMeta5 = debugItem5.getItemMeta();
         ArrayList<String> debugItemList5 = new ArrayList();
         debugItemMeta5.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Slaying Thrustbow");
         debugItemList5.add("");
         debugItemList5.add(ChatColor.AQUA + weaponspeed + ": " + ChatColor.RED + "Slow");
         debugItemList5.add(ChatColor.AQUA + damage + ": " + ChatColor.GREEN + "+" + ChatColor.DARK_GREEN + "68" + ChatColor.DARK_GREEN + "-" + ChatColor.DARK_GREEN + "93");
         debugItemList5.add(ChatColor.AQUA + critChance + ": " + ChatColor.DARK_GREEN + "4.0" + ChatColor.GREEN + "%");
         debugItemList5.add(ChatColor.AQUA + critDamage + ": " + ChatColor.DARK_GREEN + "12.0" + ChatColor.GREEN + "%");
         debugItemList5.add(ChatColor.YELLOW + reflect + ": " + ChatColor.DARK_GREEN + "6.0" + ChatColor.GREEN + "%");
         debugItemList5.add(ChatColor.RED + fire + ": " + ChatColor.DARK_GREEN + "7.0" + ChatColor.GREEN + "%");
         debugItemList5.add(ChatColor.DARK_PURPLE + harming + ": " + ChatColor.DARK_GREEN + "3.0" + ChatColor.GREEN + "%");
         debugItemList5.add("");
         debugItemList5.add(ChatColor.GOLD + durability + ": " + "350/350");
         debugItemList5.add("");
         debugItemList5.add(ChatColor.DARK_AQUA + "Level: 1");
         debugItemList5.add(ChatColor.DARK_AQUA + soulbound + " " + player.getName());
         debugItemMeta5.setLore(debugItemList5);
         debugItem5.setItemMeta(debugItemMeta5);
         player.getInventory().addItem(new ItemStack[] { new ItemStack(debugItem5) });
         
         player.sendMessage(ChatColor.RED + "[DEBUGGER] " + ChatColor.WHITE + "items created!");
       } else {
         player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
       }
     } else {
       System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
     }
   }
 }
