package net.nifheim.beelzebu.rpgcore.commands;

import net.nifheim.yitan.itemlorestats.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        return true;
    }
}
