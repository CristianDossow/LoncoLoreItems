package net.nifheim.yitan.lorestats.commands;

import java.util.List;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CustomMaterial_Com {

    Util_GetResponse util_GetResponse = new Util_GetResponse();

    public void onCustomMaterialCommand(org.bukkit.command.CommandSender sender, String[] args) {
        if ((sender instanceof Player)) {
            Player player = (Player) sender;

            if (!Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                    if (player.getInventory().getItemInMainHand() != null) {
                        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                            if (args.length >= 3) {
                                String itemName = player.getInventory().getItemInMainHand().getType().toString();
                                String rebuiltName = "";

                                if (args[0].equalsIgnoreCase("custom")) {
                                    if (args[1].equalsIgnoreCase("tool")) {
                                        if (args[2] != null) {
                                            if (itemName != null) {
                                                List toolsList = Main.getInstance().getConfig().getList("materials.tools");

                                                for (int i = 0; i < args.length; i++) {
                                                    if (i >= 3) {
                                                        rebuiltName = rebuiltName + " " + args[i];
                                                    } else {
                                                        rebuiltName = args[i];
                                                    }
                                                }

                                                toolsList.add(itemName + ":" + rebuiltName.toUpperCase().replaceAll(" ", "_"));

                                                Main.getInstance().getConfig().set("materials.tools", toolsList);
                                                Main.getInstance().saveConfig();

                                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.AddedToConfig", player, player, rebuiltName, rebuiltName));
                                            } else {
                                                player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.HoldCustomItem", player, player, rebuiltName, rebuiltName));
                                            }
                                        } else {
                                            player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                        }
                                    } else if (args[1].equalsIgnoreCase("armour")) {
                                        if (args[2] != null) {
                                            if (args.length > 3) {
                                                if (args[3] != null) {
                                                    if (itemName != null) {
                                                        String armourType = args[2].toLowerCase();
                                                        List toolsList = Main.getInstance().getConfig().getList("materials.armour." + armourType);

                                                        for (int i = 0; i < args.length; i++) {
                                                            if (i >= 4) {
                                                                rebuiltName = rebuiltName + " " + args[i];
                                                            } else {
                                                                rebuiltName = args[i];
                                                            }
                                                        }

                                                        toolsList.add(itemName + ":" + rebuiltName.toUpperCase().replaceAll(" ", "_"));

                                                        Main.getInstance().getConfig().set("materials.armour." + armourType, toolsList);
                                                        Main.getInstance().saveConfig();

                                                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.AddedToConfig", player, player, rebuiltName, rebuiltName));
                                                    } else {
                                                        player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.HoldCustomItem", player, player, rebuiltName, rebuiltName));
                                                    }
                                                } else {
                                                    player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                                }
                                            } else {
                                                player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                            }
                                        } else {
                                            player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomArmourType", player, player, "", ""));
                                        }
                                    } else {
                                        player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomEquipmentType", player, player, "", ""));
                                    }
                                }
                            } else if (args.length == 2) {
                                if (args[1].equalsIgnoreCase("tool")) {
                                    player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomItemType", player, player, "", ""));
                                } else if (args[1].equalsIgnoreCase("armour")) {
                                    player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomArmourType", player, player, "", ""));
                                }
                            } else if (args.length == 1) {
                                player.sendMessage(this.util_GetResponse.getResponse("CustomItemMessages.CustomEquipmentType", player, player, "", ""));
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
        }
    }
}
