package net.nifheim.beelzebu.rpgcore.commands;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public class CharacterCommand implements CommandExecutor {

    private final Main plugin;

    public CharacterCommand(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("use")) {
                return _use(p, args);
            }
        }
        return true;
    }

    private boolean _use(Player sender, String[] args) {
        if (args.length == 2) {
            int id;
            try {
                id = Integer.parseInt(args[1]);
                plugin.getAccount(sender).loadCharacter(id);
            } catch (NumberFormatException ex) {
                sender.sendMessage("The character id must be a number.");
            }
        } else {
            sender.sendMessage("Command usage: /character use <id>");
        }
        return true;
    }
}
