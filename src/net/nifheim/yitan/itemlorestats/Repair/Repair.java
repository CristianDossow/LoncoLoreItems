package net.nifheim.yitan.itemlorestats.Repair;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_Vault;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Repair {

    Util_Colours util_Colours = new Util_Colours();
    Util_GetResponse util_GetResponse = new Util_GetResponse();

    public void repair(Player player, String type, String material) {
        int repairCost = Main.plugin.getConfig().getInt("durabilityAddedOnEachRepair." + type + "." + material);

        if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
            java.util.List<String> splitItemLore = player.getInventory().getItemInMainHand().getItemMeta().getLore();

            for (String getItemStat : splitItemLore) {
                String durabilityAmountColour = "";
                String prefixColourOnly = "";
                String durabilityRebuilder = "";
                String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");

                if (org.bukkit.ChatColor.stripColor(getItemStat).startsWith(durabilityName)) {
                    int currentAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(org.bukkit.ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[0]).replaceAll("&([0-9a-f])", ""));
                    int maxAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(org.bukkit.ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[1]).replaceAll("&([0-9a-f])", ""));
                    int index = splitItemLore.indexOf(getItemStat);

                    if (currentAmount + repairCost > maxAmount) {
                        currentAmount = maxAmount;
                    } else {
                        currentAmount += repairCost;
                    }

                    double value = Double.valueOf(currentAmount).doubleValue() / Double.valueOf(maxAmount).doubleValue();
                    player.getInventory().getItemInMainHand().setDurability((short) (int) Math.abs(value * player.getInventory().getItemInMainHand().getType().getMaxDurability() - player.getInventory().getItemInMainHand().getType().getMaxDurability()));

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

                    durabilityRebuilder = this.util_Colours.replaceTooltipColour(prefixColourOnly) + durabilityName + ": " + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + currentAmount + this.util_Colours.replaceTooltipColour(prefixColourOnly) + "/" + this.util_Colours.replaceTooltipColour(durabilityAmountColour) + maxAmount;

                    splitItemLore.set(index, durabilityRebuilder);

                    ItemStack repairedItem = new ItemStack(player.getInventory().getItemInMainHand());
                    ItemMeta repairedItemMeta = repairedItem.getItemMeta();

                    repairedItemMeta.setLore(splitItemLore);
                    repairedItem.setItemMeta(repairedItemMeta);

                    player.getInventory().setItemInMainHand(repairedItem);
                }
            }
        }
    }

    public boolean isFullDurability(Player player) {
        if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
            java.util.List<String> splitItemLore = player.getInventory().getItemInMainHand().getItemMeta().getLore();

            for (String getItemStat : splitItemLore) {
                String durabilityName = Main.plugin.getConfig().getString("bonusStats.durability.name");

                if (org.bukkit.ChatColor.stripColor(getItemStat).startsWith(durabilityName)) {
                    int currentAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(org.bukkit.ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[0]).replaceAll("&([0-9a-f])", ""));
                    int maxAmount = Integer.parseInt(this.util_Colours.extractAndReplaceTooltipColour(org.bukkit.ChatColor.stripColor(getItemStat).split(": ")[1].split("/")[1]).replaceAll("&([0-9a-f])", ""));

                    if (currentAmount == maxAmount) {
                        return true;
                    }
                    return false;
                }
            }
        }

        return false;
    }

    public String getItemInHandName(ItemStack itemStack) {
        if (itemStack.getItemMeta().getDisplayName() != null) {
            return itemStack.getItemMeta().getDisplayName();
        }
        return itemStack.getType().toString().substring(0, 1) + itemStack.getType().toString().substring(1).toLowerCase().replace("_", " ");
    }

    public void payAndRepair(Player player, Material type) {
        if (!isFullDurability(player)) {
            if (Main.plugin.getConfig().getString("durabilityAddedOnEachRepair.repairCostType").equalsIgnoreCase("Currency")) {
                if (type.equals(Material.WOOD_AXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
                } else if (type.equals(Material.STONE_AXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
                } else if (type.equals(Material.IRON_AXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
                } else if (type.equals(Material.GOLD_AXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
                } else if (type.equals(Material.DIAMOND_AXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
                } else if (type.equals(Material.WOOD_HOE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
                } else if (type.equals(Material.STONE_HOE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
                } else if (type.equals(Material.IRON_HOE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
                } else if (type.equals(Material.GOLD_HOE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
                } else if (type.equals(Material.DIAMOND_HOE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
                } else if (type.equals(Material.WOOD_SPADE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
                } else if (type.equals(Material.STONE_SPADE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
                } else if (type.equals(Material.IRON_SPADE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
                } else if (type.equals(Material.GOLD_SPADE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
                } else if (type.equals(Material.DIAMOND_SPADE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
                } else if (type.equals(Material.WOOD_PICKAXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
                } else if (type.equals(Material.STONE_PICKAXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
                } else if (type.equals(Material.IRON_PICKAXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
                } else if (type.equals(Material.GOLD_PICKAXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
                } else if (type.equals(Material.DIAMOND_PICKAXE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
                } else if (type.equals(Material.WOOD_SWORD)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "wood");
                } else if (type.equals(Material.STONE_SWORD)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stone");
                } else if (type.equals(Material.IRON_SWORD)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "iron");
                } else if (type.equals(Material.GOLD_SWORD)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "gold");
                } else if (type.equals(Material.DIAMOND_SWORD)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "diamond");
                } else if (type.equals(Material.SHEARS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "shears");
                } else if (type.equals(Material.BOW)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "bow");
                } else if (type.equals(Material.STICK)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "stick");
                } else if (type.equals(Material.FLINT_AND_STEEL)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "flintAndSteel");
                } else if (type.equals(Material.FISHING_ROD)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "fishingRod");
                } else if (type.equals(Material.CARROT_STICK)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "carrotStick");
                } else if (type.equals(Material.SHEARS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "tools", "shears");
                } else if (type.equals(Material.LEATHER_HELMET)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
                } else if (type.equals(Material.CHAINMAIL_HELMET)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
                } else if (type.equals(Material.IRON_HELMET)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
                } else if (type.equals(Material.GOLD_HELMET)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
                } else if (type.equals(Material.DIAMOND_HELMET)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
                } else if (type.equals(Material.LEATHER_CHESTPLATE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
                } else if (type.equals(Material.CHAINMAIL_CHESTPLATE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
                } else if (type.equals(Material.IRON_CHESTPLATE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
                } else if (type.equals(Material.GOLD_CHESTPLATE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
                } else if (type.equals(Material.DIAMOND_CHESTPLATE)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
                } else if (type.equals(Material.LEATHER_LEGGINGS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
                } else if (type.equals(Material.CHAINMAIL_LEGGINGS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
                } else if (type.equals(Material.IRON_LEGGINGS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
                } else if (type.equals(Material.GOLD_LEGGINGS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
                } else if (type.equals(Material.DIAMOND_LEGGINGS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
                } else if (type.equals(Material.LEATHER_BOOTS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "leather");
                } else if (type.equals(Material.CHAINMAIL_BOOTS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "chainmail");
                } else if (type.equals(Material.IRON_BOOTS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "iron");
                } else if (type.equals(Material.GOLD_BOOTS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "gold");
                } else if (type.equals(Material.DIAMOND_BOOTS)) {
                    Main.plugin.util_Vault.removeMoneyForRepair(player, "armour", "diamond");
                }
            } else if (type.equals(Material.WOOD_AXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 3)});
                        repair(player, "tools", "wood");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.STONE_AXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 3)});
                        repair(player, "tools", "stone");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_AXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 3)});
                        repair(player, "tools", "iron");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_AXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 3)});
                        repair(player, "tools", "gold");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_AXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 3)});
                        repair(player, "tools", "diamond");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.WOOD_HOE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 2)});
                        repair(player, "tools", "wood");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.STONE_HOE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 2)});
                        repair(player, "tools", "stone");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_HOE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 2)});
                        repair(player, "tools", "iron");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_HOE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 2)});
                        repair(player, "tools", "gold");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_HOE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 2)});
                        repair(player, "tools", "diamond");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.WOOD_SPADE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 1)});
                        repair(player, "tools", "wood");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.STONE_SPADE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 1)});
                        repair(player, "tools", "stone");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_SPADE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1)});
                        repair(player, "tools", "iron");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_SPADE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 1)});
                        repair(player, "tools", "gold");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_SPADE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 1)});
                        repair(player, "tools", "diamond");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.WOOD_PICKAXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 3)});
                        repair(player, "tools", "wood");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.STONE_PICKAXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 3)});
                        repair(player, "tools", "stone");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_PICKAXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 3)});
                        repair(player, "tools", "iron");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_PICKAXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 3)});
                        repair(player, "tools", "gold");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_PICKAXE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 2)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 3)});
                        repair(player, "tools", "diamond");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.WOOD_SWORD)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 2)});
                        repair(player, "tools", "wood");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.STONE_SWORD)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.COBBLESTONE, 2)});
                        repair(player, "tools", "stone");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCobblestone", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_SWORD)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 2)});
                        repair(player, "tools", "iron");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_SWORD)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 2)});
                        repair(player, "tools", "gold");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_SWORD)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 2)});
                        repair(player, "tools", "diamond");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.SHEARS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 2)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 2)});
                    repair(player, "tools", "shears");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.BOW)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 3)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.STRING), 3)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 3)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STRING, 3)});
                        repair(player, "tools", "bow");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughString", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.STICK)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.WOOD), 1)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.WOOD, 1)});
                    repair(player, "tools", "stick");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughWood", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.FLINT_AND_STEEL)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.FLINT), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.FLINT, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 1)});
                        repair(player, "tools", "flintAndSteel");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughFlint", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.FISHING_ROD)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.STICK), 3)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.STRING), 2)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STICK, 3)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.STRING, 2)});
                        repair(player, "tools", "fishingRod");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughString", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughSticks", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.CARROT_STICK)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.CARROT_ITEM), 1)) {
                    if (player.getInventory().containsAtLeast(new ItemStack(Material.FISHING_ROD), 1)) {
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.CARROT_ITEM, 1)});
                        player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.FISHING_ROD, 1)});
                        repair(player, "tools", "carrotStick");
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughFishingRod", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                    }
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughCarrots", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.LEATHER_HELMET)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 5)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 5)});
                    repair(player, "armour", "leather");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.CHAINMAIL_HELMET)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 4)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 4)});
                    repair(player, "armour", "chainmail");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_HELMET)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 5)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 5)});
                    repair(player, "armour", "iron");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_HELMET)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 5)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 5)});
                    repair(player, "armour", "gold");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_HELMET)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 5)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 5)});
                    repair(player, "armour", "diamond");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.LEATHER_CHESTPLATE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 8)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 8)});
                    repair(player, "armour", "leather");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.CHAINMAIL_CHESTPLATE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 7)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 7)});
                    repair(player, "armour", "chainmail");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_CHESTPLATE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 8)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 8)});
                    repair(player, "armour", "iron");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_CHESTPLATE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 8)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 8)});
                    repair(player, "armour", "gold");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_CHESTPLATE)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 8)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 8)});
                    repair(player, "armour", "diamond");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.LEATHER_LEGGINGS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 7)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 7)});
                    repair(player, "armour", "leather");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.CHAINMAIL_LEGGINGS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 6)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 6)});
                    repair(player, "armour", "chainmail");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_LEGGINGS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 7)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 7)});
                    repair(player, "armour", "iron");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_LEGGINGS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 7)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 7)});
                    repair(player, "armour", "gold");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_LEGGINGS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 7)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 7)});
                    repair(player, "armour", "diamond");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.LEATHER_BOOTS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.LEATHER), 4)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.LEATHER, 4)});
                    repair(player, "armour", "leather");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughLeather", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.CHAINMAIL_BOOTS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 3)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 3)});
                    repair(player, "armour", "chainmail");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.IRON_BOOTS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 4)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.IRON_INGOT, 4)});
                    repair(player, "armour", "iron");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughIron", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.GOLD_BOOTS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 4)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.GOLD_INGOT, 4)});
                    repair(player, "armour", "gold");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughGold", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            } else if (type.equals(Material.DIAMOND_BOOTS)) {
                if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 4)) {
                    player.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.DIAMOND, 4)});
                    repair(player, "armour", "diamond");
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.RepairSuccessfulMaterial", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                } else {
                    player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.NotEnoughDiamond", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
                }
            }
        } else {
            player.sendMessage(this.util_GetResponse.getResponse("RepairMessages.DoesntNeedRepair", player, player, getItemInHandName(player.getInventory().getItemInMainHand()), getItemInHandName(player.getInventory().getItemInMainHand())));
        }
    }
}
