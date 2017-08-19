package net.nifheim.yitan.lorestats.commands;

import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Lore_Com {

    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Colours util_Colours = new Util_Colours();

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    public void onLoreCommand(CommandSender sender, String[] args) {
        if ((sender instanceof Player)) {
            if (args.length > 1) {
                if (org.bukkit.Bukkit.getServer().getPlayer(args[1]) != null) {
                    Player player = org.bukkit.Bukkit.getServer().getPlayer(args[1]);
                    if (Main.getInstance().itemInMainHand(player) != null) {
                        if (Main.getInstance().itemInMainHand(player).getType() != Material.AIR) {
                            if (sender.hasPermission("ils.admin")) {
                                if (Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName() != null) {
                                    if (args.length > 2) {
                                        if (isInteger(args[2])) {
                                            if (args.length > 3) {
                                                if (args.length > 4) {
                                                    String newLineText = "";
                                                    List<String> getItemLore;
                                                    if (Main.getInstance().itemInMainHand(player).getItemMeta().hasLore()) {
                                                        getItemLore = Main.getInstance().itemInMainHand(player).getItemMeta().getLore();
                                                    } else {
                                                        getItemLore = new java.util.ArrayList();
                                                    }

                                                    List<String> storeItemLore = getItemLore;

                                                    if (Integer.parseInt(args[2]) - 1 >= getItemLore.size()) {
                                                        ItemStack getItemInHand = new ItemStack(Main.getInstance().itemInMainHand(player));
                                                        ItemMeta getItemInHandMeta = getItemInHand.getItemMeta();

                                                        for (int i = 0; i < args.length; i++) {
                                                            if (i >= 4) {
                                                                newLineText = newLineText + " " + args[i];
                                                            } else {
                                                                newLineText = args[i];
                                                            }
                                                        }

                                                        storeItemLore.add(this.util_Colours.replaceTooltipColour(newLineText));

                                                        getItemInHandMeta.setLore(storeItemLore);

                                                        getItemInHand.setItemMeta(getItemInHandMeta);

                                                        if (Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName() != null) {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName());
                                                            }
                                                        } else if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getType().name());
                                                        }

                                                        player.getInventory().setItemInMainHand(new ItemStack(getItemInHand));
                                                    } else {
                                                        int lineNumber = Integer.parseInt(args[2]);

                                                        ItemStack getItemInHand = new ItemStack(Main.getInstance().itemInMainHand(player));
                                                        ItemMeta getItemInHandMeta = getItemInHand.getItemMeta();

                                                        for (int i = 0; i < args.length; i++) {
                                                            if (i >= 4) {
                                                                newLineText = newLineText + " " + args[i];
                                                            } else {
                                                                newLineText = args[i];
                                                            }
                                                        }

                                                        storeItemLore.set(lineNumber - 1, this.util_Colours.replaceTooltipColour(newLineText));

                                                        getItemInHandMeta.setLore(storeItemLore);

                                                        getItemInHand.setItemMeta(getItemInHandMeta);

                                                        if (Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName() != null) {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName());
                                                            }
                                                            final Player playerFinal = player;
                                                            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                                                Main.getInstance().updateHealth(playerFinal);
                                                                Main.getInstance().updatePlayerSpeed(playerFinal);
                                                            }, 2L);
                                                        } else {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getType().name());
                                                            }
                                                            final Player playerFinal = player;
                                                            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                                                Main.getInstance().updateHealth(playerFinal);
                                                                Main.getInstance().updatePlayerSpeed(playerFinal);
                                                            }, 2L);
                                                        }

                                                        player.getInventory().setItemInMainHand(new ItemStack(getItemInHand));
                                                    }
                                                } else {
                                                    player.sendMessage(ChatColor.RED + "You need to give the stat a value. For example, /ils lore " + args[2] + " " + "Damage: " + ChatColor.DARK_RED + "+15");
                                                }
                                            } else {
                                                player.sendMessage(ChatColor.RED + "You need a stat to add. For example, /ils lore " + args[1] + " " + args[2] + " " + ChatColor.DARK_RED + "Damage: +15");
                                            }
                                        } else {
                                            player.sendMessage(ChatColor.RED + args[2] + " is not a line number. For example, /ils lore " + ChatColor.DARK_RED + "1");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You need a line number and a stat. For example, /ils lore " + ChatColor.DARK_RED + "1 " + "Damage: +15");
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        }
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                    }
                } else {
                    Player player = (Player) sender;
                    if (Main.getInstance().itemInMainHand(player) != null) {
                        if (Main.getInstance().itemInMainHand(player).getType() != Material.AIR) {
                            if (sender.hasPermission("ils.admin")) {
                                if (Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName() != null) {
                                    if (args.length > 1) {
                                        if (isInteger(args[1])) {
                                            if (args.length > 2) {
                                                if (args.length > 3) {
                                                    String newLineText = "";
                                                    List<String> getItemLore;
                                                    if (Main.getInstance().itemInMainHand(player).getItemMeta().hasLore()) {
                                                        getItemLore = Main.getInstance().itemInMainHand(player).getItemMeta().getLore();
                                                    } else {
                                                        getItemLore = new java.util.ArrayList();
                                                    }

                                                    List<String> storeItemLore = getItemLore;

                                                    if (Integer.parseInt(args[1]) - 1 >= getItemLore.size()) {
                                                        ItemStack getItemInHand = new ItemStack(Main.getInstance().itemInMainHand(player));
                                                        ItemMeta getItemInHandMeta = getItemInHand.getItemMeta();

                                                        for (int i = 0; i < args.length; i++) {
                                                            if (i >= 3) {
                                                                newLineText = newLineText + " " + args[i];
                                                            } else {
                                                                newLineText = args[i];
                                                            }
                                                        }

                                                        storeItemLore.add(this.util_Colours.replaceTooltipColour(newLineText));

                                                        getItemInHandMeta.setLore(storeItemLore);

                                                        getItemInHand.setItemMeta(getItemInHandMeta);

                                                        if (Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName() != null) {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName());
                                                            }
                                                            final Player playerFinal = player;
                                                            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                                                Main.getInstance().updateHealth(playerFinal);
                                                                Main.getInstance().updatePlayerSpeed(playerFinal);
                                                            }, 2L);
                                                        } else {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getType().name());
                                                            }
                                                            final Player playerFinal = player;
                                                            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                                                Main.getInstance().updateHealth(playerFinal);
                                                                Main.getInstance().updatePlayerSpeed(playerFinal);
                                                            }, 2L);
                                                        }

                                                        player.getInventory().setItemInMainHand(new ItemStack(getItemInHand));
                                                    } else {
                                                        int lineNumber = Integer.parseInt(args[1]);

                                                        ItemStack getItemInHand = new ItemStack(Main.getInstance().itemInMainHand(player));
                                                        ItemMeta getItemInHandMeta = getItemInHand.getItemMeta();

                                                        for (int i = 0; i < args.length; i++) {
                                                            if (i >= 3) {
                                                                newLineText = newLineText + " " + args[i];
                                                            } else {
                                                                newLineText = args[i];
                                                            }
                                                        }

                                                        storeItemLore.set(lineNumber - 1, this.util_Colours.replaceTooltipColour(newLineText));

                                                        getItemInHandMeta.setLore(storeItemLore);

                                                        getItemInHand.setItemMeta(getItemInHandMeta);

                                                        if (Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName() != null) {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getItemMeta().getDisplayName());
                                                            }
                                                            final Player playerFinal = player;
                                                            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                                                Main.getInstance().updateHealth(playerFinal);
                                                                Main.getInstance().updatePlayerSpeed(playerFinal);
                                                            }, 2L);
                                                        } else {
                                                            if (Main.getInstance().getConfig().getBoolean("messages.loreSuccessfullyAdded")) {
                                                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Lore added to " + ChatColor.RESET + Main.getInstance().itemInMainHand(player).getType().name());
                                                            }
                                                            final Player playerFinal = player;
                                                            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                                                                Main.getInstance().updateHealth(playerFinal);
                                                                Main.getInstance().updatePlayerSpeed(playerFinal);
                                                            }, 2L);
                                                        }

                                                        player.getInventory().setItemInMainHand(new ItemStack(getItemInHand));
                                                    }
                                                } else {
                                                    player.sendMessage(ChatColor.RED + "You need to give the stat a value. For example, /ils lore " + args[1] + " " + "Damage: " + ChatColor.DARK_RED + "+15");
                                                }
                                            } else {
                                                player.sendMessage(ChatColor.RED + "You need a stat to add. For example, /ils lore " + args[1] + " " + ChatColor.DARK_RED + "Damage: +15");
                                            }
                                        } else {
                                            player.sendMessage(ChatColor.RED + args[1] + " is not a line number. For example, /ils lore " + ChatColor.DARK_RED + "1 " + ChatColor.RED + "Damage: +15");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "You need a line number and a stat. For example, /ils lore " + ChatColor.DARK_RED + "1 " + "Damage: +15");
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                        }
                    } else {
                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", null, null, "", ""));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You need a line number or player name. For example, /ils lore " + ChatColor.DARK_RED + "1" + ChatColor.RED + " or /ils lore " + ChatColor.DARK_RED + sender.getName());
            }
        } else {
            System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
        }
    }
}
