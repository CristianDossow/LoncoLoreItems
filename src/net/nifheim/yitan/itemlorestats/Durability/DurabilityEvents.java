package net.nifheim.yitan.itemlorestats.Durability;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.nifheim.yitan.itemlorestats.Main;

import net.nifheim.yitan.itemlorestats.Enchants.Vanilla_Unbreaking;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;

public class DurabilityEvents implements org.bukkit.event.Listener {

    net.nifheim.yitan.itemlorestats.SetBonuses setBonuses = new net.nifheim.yitan.itemlorestats.SetBonuses();
    Util_Colours util_Colours = new Util_Colours();
    Vanilla_Unbreaking vanilla_Unbreaking = new Vanilla_Unbreaking();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void disableItemBreakItemInHand(PlayerItemBreakEvent event) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {

            if (event.getPlayer().isDead()) {
                return;
            }
            String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");
            String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");

            final ItemStack item = event.getBrokenItem().clone();
            try {
                if ((item != null)
                        && (item.getItemMeta().hasLore())) {
                    if (Main.plugin.isTool(item.getType())) {
                        List<String> getItemLore = item.getItemMeta().getLore();

                        for (String getDurability : getItemLore) {
                            if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                                String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                                int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;
                                if (currentValueMinusOne > 1) {
                                    double value = Double.valueOf(currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();

                                    //item.setDurability((short)(int)Math.abs(value * item.getType().getMaxDurability() - item.getType().getMaxDurability()));
                                } else {
                                    //item.setDurability((short)1);
                                }
                            }
                        }
                    } else if (Main.plugin.isArmour(item.getType())) {
                        if (Main.plugin.isHelmet(item.getType())) {
                            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                public void run() {
                                    //item.setDurability((short)-1);
                                }
                            }, 1L);
                        } else if (Main.plugin.isChestplate(item.getType())) {
                            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                public void run() {
                                    //item.setDurability((short)-1);
                                }
                            }, 1L);
                        } else if (Main.plugin.isLeggings(item.getType())) {
                            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                public void run() {
                                    //item.setDurability((short)-1);
                                }
                            }, 1L);
                        } else if (Main.plugin.isBoots(item.getType())) {
                            Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                                public void run() {
                                    //item.setDurability((short)-1);
                                }
                            }, 1L);
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void blockBreakDurability(BlockBreakEvent event) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {

            Player player = event.getPlayer();

            if ((Main.plugin.isHoe(player.getInventory().getItemInMainHand())) || (player.getInventory().getItemInMainHand().getType().equals(Material.FLINT_AND_STEEL)) || (player.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD))) {
                return;
            }
            ItemStack itemInMain = player.getInventory().getItemInMainHand();

            if ((itemInMain != null) && (Main.plugin.isTool(itemInMain.getType()))) {
                if (itemInMain.getItemMeta().hasLore()) {
                    String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");
                    String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");

                    List<String> getItemLore = itemInMain.getItemMeta().getLore();

                    for (String getDurability : getItemLore) {
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            String durabilityRebuilder = "";
                            String durabilityAmountColour = "";
                            String prefixColourOnly = "";

                            int index = getItemLore.indexOf(getDurability);
                            String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;

                            if (currentValueMinusOne + 1 < 2) {
                                player.getWorld().playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                                player.getInventory().remove(player.getInventory().getItemInMainHand());
                                return;
                            }

                            double value = Double.valueOf(currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                            //itemInMain.setDurability((short)(int)Math.abs(value * itemInMain.getType().getMaxDurability() - itemInMain.getType().getMaxDurability()));

                            if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&")) {
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

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                durabilityAmountColour = prefixColourOnly;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreakingMain(itemInMain)) {
                                currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(itemInMain.getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                            }

                            int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);

                            if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 2.6D) {
                                if ((remainingDurabilityPercentage == 25) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 5.1D) {
                                if ((remainingDurabilityPercentage == 50) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 7.6D) {
                                if ((remainingDurabilityPercentage == 75) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else {
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            }

                            getItemLore.set(index, durabilityRebuilder);
                        }
                    }

                    ItemStack setItemInHand = new ItemStack(itemInMain);
                    ItemMeta setItemInHandMeta = setItemInHand.getItemMeta();

                    setItemInHandMeta.setLore(getItemLore);
                    setItemInHand.setItemMeta(setItemInHandMeta);

                    player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void fishingDurability(org.bukkit.event.player.PlayerFishEvent event) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {

            Player player = event.getPlayer();

            ItemStack itemInMain = player.getInventory().getItemInMainHand();

            if ((itemInMain != null) && (Main.plugin.isTool(itemInMain.getType()))) {
                if (itemInMain.getItemMeta().hasLore()) {
                    String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");
                    String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");

                    List<String> getItemLore = itemInMain.getItemMeta().getLore();

                    for (String getDurability : getItemLore) {
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            String durabilityRebuilder = "";
                            String durabilityAmountColour = "";
                            String prefixColourOnly = "";

                            int index = getItemLore.indexOf(getDurability);
                            String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;

                            if (currentValueMinusOne + 1 < 2) {
                                player.getWorld().playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                                player.getInventory().remove(player.getInventory().getItemInMainHand());
                                return;
                            }

                            double value = Double.valueOf(currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                            //itemInMain.setDurability((short)(int)Math.abs(value * itemInMain.getType().getMaxDurability() - itemInMain.getType().getMaxDurability()));

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
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

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                durabilityAmountColour = prefixColourOnly;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreakingMain(itemInMain)) {
                                currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(itemInMain.getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                            }

                            int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);

                            if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 2.6D) {
                                if ((remainingDurabilityPercentage == 25) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 5.1D) {
                                if ((remainingDurabilityPercentage == 50) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 7.6D) {
                                if ((remainingDurabilityPercentage == 75) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else {
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            }

                            getItemLore.set(index, durabilityRebuilder);
                        }
                    }

                    ItemStack setItemInHand = new ItemStack(itemInMain);
                    ItemMeta setItemInHandMeta = setItemInHand.getItemMeta();

                    setItemInHandMeta.setLore(getItemLore);
                    setItemInHand.setItemMeta(setItemInHandMeta);

                    player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void carrotStickDurability(PlayerInteractEvent event) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {

            Player player = event.getPlayer();

            ItemStack itemInMain = player.getInventory().getItemInMainHand();

            if ((itemInMain.getType().equals(Material.CARROT_STICK)) && ((event.getAction().equals(Action.RIGHT_CLICK_AIR)) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
                    && (itemInMain != null) && (Main.plugin.isTool(player.getInventory().getItemInMainHand().getType()))) {
                if (itemInMain.getItemMeta().hasLore()) {
                    String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");
                    String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");

                    List<String> getItemLore = itemInMain.getItemMeta().getLore();

                    for (String getDurability : getItemLore) {
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            String durabilityRebuilder = "";
                            String durabilityAmountColour = "";
                            String prefixColourOnly = "";

                            int index = getItemLore.indexOf(getDurability);
                            String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;

                            if (currentValueMinusOne + 1 < 2) {
                                player.getWorld().playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                                player.getInventory().remove(player.getInventory().getItemInMainHand());
                                return;
                            }

                            double value = Double.valueOf(currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                            //itemInMain.setDurability((short)(int)Math.abs(value * itemInMain.getType().getMaxDurability() - itemInMain.getType().getMaxDurability()));

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
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

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                durabilityAmountColour = prefixColourOnly;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreakingMain(itemInMain)) {
                                currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(itemInMain.getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                            }

                            int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);

                            if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 2.6D) {
                                if ((remainingDurabilityPercentage == 25) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 5.1D) {
                                if ((remainingDurabilityPercentage == 50) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 7.6D) {
                                if ((remainingDurabilityPercentage == 75) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else {
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            }

                            getItemLore.set(index, durabilityRebuilder);
                        }
                    }

                    ItemStack setItemInHand = new ItemStack(itemInMain);
                    ItemMeta setItemInHandMeta = setItemInHand.getItemMeta();

                    setItemInHandMeta.setLore(getItemLore);
                    setItemInHand.setItemMeta(setItemInHandMeta);

                    player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void flintAndSteelDurability(PlayerInteractEvent event) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {

            Player player = event.getPlayer();

            ItemStack itemInMain = player.getInventory().getItemInMainHand();

            if ((player.getInventory().getItemInMainHand().getType().equals(Material.FLINT_AND_STEEL)) && ((event.getAction().equals(Action.RIGHT_CLICK_AIR)) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
                    && (player.getInventory().getItemInMainHand() != null) && (Main.plugin.isTool(player.getInventory().getItemInMainHand().getType()))) {
                if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                    String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");
                    String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");

                    List<String> getItemLore = player.getInventory().getItemInMainHand().getItemMeta().getLore();

                    for (String getDurability : getItemLore) {
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            String durabilityRebuilder = "";
                            String durabilityAmountColour = "";
                            String prefixColourOnly = "";

                            int index = getItemLore.indexOf(getDurability);
                            String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;

                            if (currentValueMinusOne + 1 < 2) {
                                player.getWorld().playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                                player.getInventory().remove(player.getInventory().getItemInMainHand());
                                return;
                            }

                            double value = Double.valueOf(currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                            //player.getInventory().getItemInMainHand().setDurability((short)(int)Math.abs(value * player.getInventory().getItemInMainHand().getType().getMaxDurability() - player.getInventory().getItemInMainHand().getType().getMaxDurability()));

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
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

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                durabilityAmountColour = prefixColourOnly;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreakingMain(itemInMain)) {
                                currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(itemInMain.getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                            }

                            int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);

                            if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 2.6D) {
                                if ((remainingDurabilityPercentage == 25) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning"))) {
                                    player.sendMessage(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 5.1D) {
                                if ((remainingDurabilityPercentage == 50) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning"))) {
                                    player.sendMessage(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 7.6D) {
                                if ((remainingDurabilityPercentage == 75) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning"))) {
                                    player.sendMessage(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else {
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            }

                            getItemLore.set(index, durabilityRebuilder);
                        }
                    }

                    ItemStack setItemInHand = new ItemStack(player.getInventory().getItemInMainHand());
                    ItemMeta setItemInHandMeta = setItemInHand.getItemMeta();

                    setItemInHandMeta.setLore(getItemLore);
                    setItemInHand.setItemMeta(setItemInHandMeta);

                    player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void hoeDurability(PlayerInteractEvent event) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {

            Player player = event.getPlayer();

            ItemStack itemInMain = player.getInventory().getItemInMainHand();

            if (((itemInMain.getType().equals(Material.WOOD_HOE))
                    || (itemInMain.getType().equals(Material.STONE_HOE))
                    || (itemInMain.getType().equals(Material.IRON_HOE))
                    || (itemInMain.getType().equals(Material.DIAMOND_HOE)))
                    && (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                    && ((event.getClickedBlock().getType().equals(Material.GRASS)) || (event.getClickedBlock().getType().equals(Material.DIRT)))
                    && (itemInMain != null) && (Main.plugin.isTool(itemInMain.getType()))) {
                if (itemInMain.getItemMeta().hasLore()) {
                    String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");
                    String durabilitySplitter = Main.plugin.getConfig().getString("bonusStats.durability.splitter");

                    List<String> getItemLore = itemInMain.getItemMeta().getLore();

                    for (String getDurability : getItemLore) {
                        if (ChatColor.stripColor(getDurability).startsWith(durabilityName)) {
                            String durabilityRebuilder = "";
                            String durabilityAmountColour = "";
                            String prefixColourOnly = "";

                            int index = getItemLore.indexOf(getDurability);
                            String maximumValue = ChatColor.stripColor(getDurability).trim().replace("[", "").substring(durabilityName.length()).split(durabilitySplitter)[1].replace("]", "").trim();
                            int currentValueMinusOne = Integer.parseInt(ChatColor.stripColor(getDurability).trim().replace("[", "").replace(": ", "").trim().substring(durabilityName.length()).split(durabilitySplitter)[0].replace("§", "").replace("]", "").trim()) - 1;

                            if (currentValueMinusOne + 1 < 2) {
                                player.getWorld().playEffect(player.getLocation(), Effect.ZOMBIE_DESTROY_DOOR, 1);
                                player.getInventory().remove(player.getInventory().getItemInMainHand());
                                return;
                            }

                            double value = Double.valueOf(currentValueMinusOne).doubleValue() / Double.valueOf(maximumValue).doubleValue();
                            //itemInMain.setDurability((short)(int)Math.abs(value * itemInMain.getType().getMaxDurability() - itemInMain.getType().getMaxDurability()));

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.substring(0, 2)).contains("&"))) {
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

                            if ((getDurability.split(durabilitySplitter)[1].trim().length() > 1) && (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(0, 2)).contains("&"))) {
                                if (getDurability.split(durabilitySplitter)[1].trim().length() > 4) {
                                    if (this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim().substring(2, 4)).contains("&")) {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(2, 4);
                                    } else {
                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                    }
                                } else {
                                    durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getDurability.split(durabilitySplitter)[1].trim()).substring(0, 2);
                                }
                            } else {
                                durabilityAmountColour = prefixColourOnly;
                            }

                            if (this.vanilla_Unbreaking.hasUnbreakingMain(itemInMain)) {
                                currentValueMinusOne += this.vanilla_Unbreaking.calculateNewDurabilityLoss(itemInMain.getEnchantmentLevel(Enchantment.DURABILITY), "damage");
                            }

                            int remainingDurabilityPercentage = currentValueMinusOne * 100 / Integer.parseInt(maximumValue);

                            if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 2.6D) {
                                if ((remainingDurabilityPercentage == 25) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable25%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.RED + "25%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.RED + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 5.1D) {
                                if ((remainingDurabilityPercentage == 50) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable50%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.YELLOW + "50%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.YELLOW + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else if (currentValueMinusOne < Integer.parseInt(maximumValue) / 10 * 7.6D) {
                                if ((remainingDurabilityPercentage == 75) && (Main.plugin.getConfig().getBoolean("displayDurabilityWarnings.enable75%DurabilityWarning"))) {
                                    player.sendMessage(itemInMain.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + " is at " + ChatColor.GREEN + "75%" + ChatColor.LIGHT_PURPLE + " durability.");
                                }
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + ChatColor.GREEN + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            } else {
                                durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentValueMinusOne + this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilitySplitter + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maximumValue;
                            }

                            getItemLore.set(index, durabilityRebuilder);
                        }
                    }

                    ItemStack setItemInHand = new ItemStack(itemInMain);
                    ItemMeta setItemInHandMeta = setItemInHand.getItemMeta();

                    setItemInHandMeta.setLore(getItemLore);
                    setItemInHand.setItemMeta(setItemInHandMeta);

                    player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
                }
            }
        }
    }
}
