package net.nifheim.yitan.lorestats.commands;

import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Name_Com {

    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Colours util_Colours = new Util_Colours();

    public void onNameCommand(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("name")) {
            if ((sender instanceof Player)) {
                Player player = (Player) sender;

                if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                    if (player.getInventory().getItemInMainHand() != null) {
                        if (player.getInventory().getItemInMainHand().getType() != org.bukkit.Material.AIR) {
                            if (args.length > 1) {
                                String storeName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                                String newName = "";

                                ItemStack getItemInHand = new ItemStack(player.getInventory().getItemInMainHand());
                                ItemMeta getItemInHandMeta = getItemInHand.getItemMeta();

                                for (int i = 1; i < args.length; i++) {
                                    if (i >= 2) {
                                        newName = newName + " " + args[i];
                                    } else {
                                        newName = args[i];
                                    }
                                }

                                getItemInHandMeta.setDisplayName(this.util_Colours.replaceTooltipColour(newName));
                                getItemInHand.setItemMeta(getItemInHandMeta);
                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Changed the name of " + ChatColor.RESET + storeName + ChatColor.LIGHT_PURPLE + " to " + ChatColor.RESET + this.util_Colours.replaceTooltipColour(newName));
                                player.getInventory().setItemInMainHand(new ItemStack(getItemInHand));
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", null, null, "", ""));
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
            } else {
                System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
            }
        }
    }
}
