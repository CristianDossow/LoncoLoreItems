package net.nifheim.yitan.lorestats.commands;

import java.io.File;
import net.nifheim.yitan.lorestats.GenerateFromFile;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Export_Com {

    Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Colours util_Colours = new Util_Colours();
    GenerateFromFile generateFromFile = new GenerateFromFile();

    public void onExportCommand(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("export")) {
            if ((sender instanceof Player)) {
                Player player = (Player) sender;

                if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                    if (player.getInventory().getItemInMainHand() != null) {
                        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                            if (args.length > 1) {
                                String newItemName = "";

                                for (int i = 0; i < args.length; i++) {
                                    if (i >= 2) {
                                        newItemName = newItemName + " " + args[i];
                                    } else {
                                        newItemName = args[i];
                                    }
                                }

                                if (new File(Main.getInstance().getDataFolder() + File.separator + "SavedItems" + File.separator + newItemName + ".yml").exists()) {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.ItemAlreadyExistsError", null, null, "", ""));
                                } else {
                                    this.generateFromFile.exportWeapon(player, newItemName);
                                }
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
