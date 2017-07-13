package net.nifheim.beelzebu.rpgcore.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.nifheim.beelzebu.rpgcore.utils.InventorySerializer;
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
                plugin.getConfig().set("Test.ItemSerialized", InventorySerializer.serialize(p.getInventory().getItemInMainHand()));
                p.sendMessage("serialized");
            } else if (args[0].equalsIgnoreCase("itemd")) {
                try {
                    InventorySerializer.deserialize(plugin.getConfig().get("Tests.ItemSerialized"));
                    p.sendMessage("deserialized");
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(TestCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                p.sendMessage("");
            }
        }
        return true;
    }

}
