package net.nifheim.yitan.lorestats.utils;

import org.bukkit.ChatColor;

public class Util_Colours {

    public String extractTooltipColour(String input) {
        String toolTipColour = "";

        if (input.contains("&r")) {
            toolTipColour = toolTipColour + "&r";
        }

        if (input.contains("&o")) {
            toolTipColour = toolTipColour + "&o";
        }

        if (input.contains("&n")) {
            toolTipColour = toolTipColour + "&n";
        }

        if (input.contains("&m")) {
            toolTipColour = toolTipColour + "&m";
        }

        if (input.contains("&l")) {
            toolTipColour = toolTipColour + "&l";
        }

        if (input.contains("&k")) {
            toolTipColour = toolTipColour + "&k";
        }

        if (input.contains("&f")) {
            toolTipColour = toolTipColour + "&f";
        }

        if (input.contains("&e")) {
            toolTipColour = toolTipColour + "&e";
        }

        if (input.contains("&d")) {
            toolTipColour = toolTipColour + "&d";
        }

        if (input.contains("&c")) {
            toolTipColour = toolTipColour + "&c";
        }

        if (input.contains("&b")) {
            toolTipColour = toolTipColour + "&b";
        }

        if (input.contains("&a")) {
            toolTipColour = toolTipColour + "&a";
        }

        if (input.contains("&9")) {
            toolTipColour = toolTipColour + "&9";
        }

        if (input.contains("&8")) {
            toolTipColour = toolTipColour + "&8";
        }

        if (input.contains("&7")) {
            toolTipColour = toolTipColour + "&7";
        }

        if (input.contains("&6")) {
            toolTipColour = toolTipColour + "&6";
        }

        if (input.contains("&5")) {
            toolTipColour = toolTipColour + "&5";
        }

        if (input.contains("&4")) {
            toolTipColour = toolTipColour + "&4";
        }

        if (input.contains("&3")) {
            toolTipColour = toolTipColour + "&3";
        }

        if (input.contains("&2")) {
            toolTipColour = toolTipColour + "&2";
        }

        if (input.contains("&1")) {
            toolTipColour = toolTipColour + "&1";
        }

        if (input.contains("&0")) {
            toolTipColour = toolTipColour + "&0";
        }

        return toolTipColour;
    }

    public String extractRawTooltipColour(String input) {
        String toolTipColour = "";

        if (input.contains("§r")) {
            toolTipColour = toolTipColour + "&r";
        }

        if (input.contains("§o")) {
            toolTipColour = toolTipColour + "&o";
        }

        if (input.contains("§n")) {
            toolTipColour = toolTipColour + "&n";
        }

        if (input.contains("§m")) {
            toolTipColour = toolTipColour + "&m";
        }

        if (input.contains("§l")) {
            toolTipColour = toolTipColour + "&l";
        }

        if (input.contains("§k")) {
            toolTipColour = toolTipColour + "&k";
        }

        if (input.contains("§f")) {
            toolTipColour = toolTipColour + "&f";
        }

        if (input.contains("§e")) {
            toolTipColour = toolTipColour + "&e";
        }

        if (input.contains("§d")) {
            toolTipColour = toolTipColour + "&d";
        }

        if (input.contains("§c")) {
            toolTipColour = toolTipColour + "&c";
        }

        if (input.contains("§b")) {
            toolTipColour = toolTipColour + "&b";
        }

        if (input.contains("§a")) {
            toolTipColour = toolTipColour + "&a";
        }

        if (input.contains("§9")) {
            toolTipColour = toolTipColour + "&9";
        }

        if (input.contains("§8")) {
            toolTipColour = toolTipColour + "&8";
        }

        if (input.contains("§7")) {
            toolTipColour = toolTipColour + "&7";
        }

        if (input.contains("§6")) {
            toolTipColour = toolTipColour + "&6";
        }

        if (input.contains("§5")) {
            toolTipColour = toolTipColour + "&5";
        }

        if (input.contains("§4")) {
            toolTipColour = toolTipColour + "&4";
        }

        if (input.contains("§3")) {
            toolTipColour = toolTipColour + "&3";
        }

        if (input.contains("§2")) {
            toolTipColour = toolTipColour + "&2";
        }

        if (input.contains("§1")) {
            toolTipColour = toolTipColour + "&1";
        }

        if (input.contains("§0")) {
            toolTipColour = toolTipColour + "&0";
        }

        return toolTipColour;
    }

    public String extractAndReplaceTooltipColour(String input) {
        String toolTipColour = input;

        toolTipColour = toolTipColour.replaceAll(ChatColor.RESET.toString(), "&r");
        toolTipColour = toolTipColour.replaceAll(ChatColor.ITALIC.toString(), "&o");
        toolTipColour = toolTipColour.replaceAll(ChatColor.UNDERLINE.toString(), "&n");
        toolTipColour = toolTipColour.replaceAll(ChatColor.STRIKETHROUGH.toString(), "&m");
        toolTipColour = toolTipColour.replaceAll(ChatColor.BOLD.toString(), "&l");
        toolTipColour = toolTipColour.replaceAll(ChatColor.MAGIC.toString(), "&k");
        toolTipColour = toolTipColour.replaceAll(ChatColor.WHITE.toString(), "&f");
        toolTipColour = toolTipColour.replaceAll(ChatColor.YELLOW.toString(), "&e");
        toolTipColour = toolTipColour.replaceAll(ChatColor.LIGHT_PURPLE.toString(), "&d");
        toolTipColour = toolTipColour.replaceAll(ChatColor.RED.toString(), "&c");
        toolTipColour = toolTipColour.replaceAll(ChatColor.AQUA.toString(), "&b");
        toolTipColour = toolTipColour.replaceAll(ChatColor.GREEN.toString(), "&a");
        toolTipColour = toolTipColour.replaceAll(ChatColor.BLUE.toString(), "&9");
        toolTipColour = toolTipColour.replaceAll(ChatColor.DARK_GRAY.toString(), "&8");
        toolTipColour = toolTipColour.replaceAll(ChatColor.GRAY.toString(), "&7");
        toolTipColour = toolTipColour.replaceAll(ChatColor.GOLD.toString(), "&6");
        toolTipColour = toolTipColour.replaceAll(ChatColor.DARK_PURPLE.toString(), "&5");
        toolTipColour = toolTipColour.replaceAll(ChatColor.DARK_RED.toString(), "&4");
        toolTipColour = toolTipColour.replaceAll(ChatColor.DARK_AQUA.toString(), "&3");
        toolTipColour = toolTipColour.replaceAll(ChatColor.DARK_GREEN.toString(), "&2");
        toolTipColour = toolTipColour.replaceAll(ChatColor.DARK_BLUE.toString(), "&1");
        toolTipColour = toolTipColour.replaceAll(ChatColor.BLACK.toString(), "&0");

        return toolTipColour;
    }

    public String replaceTooltipColour(String input) {
        String toolTipColour = input;

        toolTipColour = toolTipColour.replaceAll("%n", "        ");
        toolTipColour = toolTipColour.replaceAll("&r", ChatColor.RESET.toString());
        toolTipColour = toolTipColour.replaceAll("&o", ChatColor.ITALIC.toString());
        toolTipColour = toolTipColour.replaceAll("&n", ChatColor.UNDERLINE.toString());
        toolTipColour = toolTipColour.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        toolTipColour = toolTipColour.replaceAll("&l", ChatColor.BOLD.toString());
        toolTipColour = toolTipColour.replaceAll("&k", ChatColor.MAGIC.toString());
        toolTipColour = toolTipColour.replaceAll("&f", ChatColor.WHITE.toString());
        toolTipColour = toolTipColour.replaceAll("&e", ChatColor.YELLOW.toString());
        toolTipColour = toolTipColour.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString());
        toolTipColour = toolTipColour.replaceAll("&c", ChatColor.RED.toString());
        toolTipColour = toolTipColour.replaceAll("&b", ChatColor.AQUA.toString());
        toolTipColour = toolTipColour.replaceAll("&a", ChatColor.GREEN.toString());
        toolTipColour = toolTipColour.replaceAll("&9", ChatColor.BLUE.toString());
        toolTipColour = toolTipColour.replaceAll("&8", ChatColor.DARK_GRAY.toString());
        toolTipColour = toolTipColour.replaceAll("&7", ChatColor.GRAY.toString());
        toolTipColour = toolTipColour.replaceAll("&6", ChatColor.GOLD.toString());
        toolTipColour = toolTipColour.replaceAll("&5", ChatColor.DARK_PURPLE.toString());
        toolTipColour = toolTipColour.replaceAll("&4", ChatColor.DARK_RED.toString());
        toolTipColour = toolTipColour.replaceAll("&3", ChatColor.DARK_AQUA.toString());
        toolTipColour = toolTipColour.replaceAll("&2", ChatColor.DARK_GREEN.toString());
        toolTipColour = toolTipColour.replaceAll("&1", ChatColor.DARK_BLUE.toString());
        toolTipColour = toolTipColour.replaceAll("&0", ChatColor.BLACK.toString());

        return toolTipColour;
    }

    public String removeTooltipColour(String input) {
        String toolTipColour = input;

        toolTipColour = toolTipColour.replaceAll("&r", "");
        toolTipColour = toolTipColour.replaceAll("&o", "");
        toolTipColour = toolTipColour.replaceAll("&n", "");
        toolTipColour = toolTipColour.replaceAll("&m", "");
        toolTipColour = toolTipColour.replaceAll("&l", "");
        toolTipColour = toolTipColour.replaceAll("&k", "");
        toolTipColour = toolTipColour.replaceAll("&f", "");
        toolTipColour = toolTipColour.replaceAll("&e", "");
        toolTipColour = toolTipColour.replaceAll("&d", "");
        toolTipColour = toolTipColour.replaceAll("&c", "");
        toolTipColour = toolTipColour.replaceAll("&b", "");
        toolTipColour = toolTipColour.replaceAll("&a", "");
        toolTipColour = toolTipColour.replaceAll("&9", "");
        toolTipColour = toolTipColour.replaceAll("&8", "");
        toolTipColour = toolTipColour.replaceAll("&7", "");
        toolTipColour = toolTipColour.replaceAll("&6", "");
        toolTipColour = toolTipColour.replaceAll("&5", "");
        toolTipColour = toolTipColour.replaceAll("&4", "");
        toolTipColour = toolTipColour.replaceAll("&3", "");
        toolTipColour = toolTipColour.replaceAll("&2", "");
        toolTipColour = toolTipColour.replaceAll("&1", "");
        toolTipColour = toolTipColour.replaceAll("&0", "");

        return toolTipColour;
    }

    public String removeRawTooltipColour(String input) {
        String toolTipColour = input;

        toolTipColour = toolTipColour.replaceAll("§r", "");
        toolTipColour = toolTipColour.replaceAll("§o", "");
        toolTipColour = toolTipColour.replaceAll("§n", "");
        toolTipColour = toolTipColour.replaceAll("§m", "");
        toolTipColour = toolTipColour.replaceAll("§l", "");
        toolTipColour = toolTipColour.replaceAll("§k", "");
        toolTipColour = toolTipColour.replaceAll("§f", "");
        toolTipColour = toolTipColour.replaceAll("§e", "");
        toolTipColour = toolTipColour.replaceAll("§d", "");
        toolTipColour = toolTipColour.replaceAll("§c", "");
        toolTipColour = toolTipColour.replaceAll("§b", "");
        toolTipColour = toolTipColour.replaceAll("§a", "");
        toolTipColour = toolTipColour.replaceAll("§9", "");
        toolTipColour = toolTipColour.replaceAll("§8", "");
        toolTipColour = toolTipColour.replaceAll("§7", "");
        toolTipColour = toolTipColour.replaceAll("§6", "");
        toolTipColour = toolTipColour.replaceAll("§5", "");
        toolTipColour = toolTipColour.replaceAll("§4", "");
        toolTipColour = toolTipColour.replaceAll("§3", "");
        toolTipColour = toolTipColour.replaceAll("§2", "");
        toolTipColour = toolTipColour.replaceAll("§1", "");
        toolTipColour = toolTipColour.replaceAll("§0", "");

        return toolTipColour;
    }
}
