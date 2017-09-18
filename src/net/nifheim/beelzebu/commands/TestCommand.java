package net.nifheim.beelzebu.commands;

import net.nifheim.beelzebu.utils.ItemSerializer;
import net.nifheim.yitan.lorestats.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class TestCommand implements CommandExecutor {

    private final Main plugin;

    public TestCommand(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("items")) {
                plugin.getConfig().set("Test.ItemSerialized", ItemSerializer.serialize(p.getInventory().getItemInMainHand()));
                plugin.saveConfig();
                plugin.reloadConfig();
                p.sendMessage("serialized");
            } else if (args[0].equalsIgnoreCase("itemd")) {
                p.getInventory().setItemInMainHand(ItemSerializer.deserialize(plugin.getConfig().getString("Test.ItemSerialized")));
            } else {
                p.sendMessage("");
            }
        }
        return true;
    }

}
