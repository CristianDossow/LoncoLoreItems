package net.nifheim.yitan.itemlorestats.Commands;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.Repair.Repair;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Repair_Com {

    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Colours util_Colours = new Util_Colours();
    Repair repair = new Repair();

    public void onRepairCommand(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("repair")) {
            if ((sender instanceof Player)) {
                Player player = (Player) sender;

                if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                    if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                        if (player.getInventory().getItemInMainHand() != null) {
                            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                                    List<String> splitItemLore = player.getInventory().getItemInMainHand().getItemMeta().getLore();

                                    for (String getItemStat : splitItemLore) {
                                        String durabilityAmountColour = "";
                                        String prefixColourOnly = "";
                                        String durabilityRebuilder = "";

                                        if (org.bukkit.ChatColor.stripColor(getItemStat).startsWith(Main.plugin.getConfig().getString("bonusStats.durability.name"))) {
                                            int maxAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(org.bukkit.ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[1]).replaceAll("&([0-9a-f])", ""));
                                            int index = splitItemLore.indexOf(getItemStat);

                                            if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)).contains("&")) {
                                                if (getItemStat.length() > 4) {
                                                    if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(2, 4)).contains("&")) {
                                                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2))) + this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(2, 4)));
                                                    } else {
                                                        prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)));
                                                    }
                                                } else {
                                                    prefixColourOnly = this.util_Colours.replaceTooltipColour(this.util_Colours.extractAndReplaceTooltipColour(getItemStat.substring(0, 2)));
                                                }
                                            } else {
                                                prefixColourOnly = this.util_Colours.replaceTooltipColour("&5&o");
                                            }

                                            if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim().substring(0, 2)).contains("&")) {
                                                if (getItemStat.split("/")[1].trim().length() > 4) {
                                                    if (this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim().substring(2, 4)).contains("&")) {
                                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(0, 2) + this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(2, 4);
                                                    } else {
                                                        durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(0, 2);
                                                    }
                                                } else {
                                                    durabilityAmountColour = this.util_Colours.extractAndReplaceTooltipColour(getItemStat.split("/")[1].trim()).substring(0, 2);
                                                }
                                            } else {
                                                durabilityAmountColour = prefixColourOnly;
                                            }

                                            durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + Main.plugin.getConfig().getString("bonusStats.durability.name") + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxAmount + this.util_Colours.replaceTooltipColour(prefixColourOnly) + "/" + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxAmount;

                                            splitItemLore.set(index, durabilityRebuilder);

                                            ItemStack repairedItem = new ItemStack(player.getInventory().getItemInMainHand());
                                            ItemMeta repairedItemMeta = repairedItem.getItemMeta();

                                            repairedItemMeta.setLore(splitItemLore);
                                            repairedItem.setItemMeta(repairedItemMeta);

                                            player.getInventory().setItemInMainHand(repairedItem);
                                            player.getInventory().getItemInMainHand().setDurability((short) 0);

                                            if (Main.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairCostType").equalsIgnoreCase("Currency")) {
                                                player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulCurrency", player, player, player.getInventory().getItemInMainHand().getItemMeta().getDisplayName(), player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
                                            } else {
                                                player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, player.getInventory().getItemInMainHand().getItemMeta().getDisplayName(), player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()));
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NoLoreError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        }
                    } else if (player.hasPermission("ils.repair")) {
                        if (player.getInventory().getItemInMainHand() != null) {
                            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                                    if ((Main.plugin.isTool(player.getInventory().getItemInMainHand().getType())) || (Main.plugin.isArmour(player.getInventory().getItemInMainHand().getType()))) {
                                        this.repair.payAndRepair(player, player.getInventory().getItemInMainHand().getType());
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NoLoreError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        }
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                    }
                }
            } else {
                System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
            }
        }
    }
}
