package net.nifheim.beelzebu.rpgcore.commands;

import net.nifheim.yitan.itemlorestats.Main;
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
                plugin.getConfig().set("Test.ItemSerialized", p.getInventory().getItemInMainHand());
                plugin.saveConfig();
                plugin.reloadConfig();
                p.sendMessage("serialized");
            } else if (args[0].equalsIgnoreCase("itemd")) {
                p.getInventory().setItemInMainHand(plugin.getConfig().getItemStack("Test.ItemSerialized"));
            } else {
                p.sendMessage("");
            }
        }
        return true;
    }

}
