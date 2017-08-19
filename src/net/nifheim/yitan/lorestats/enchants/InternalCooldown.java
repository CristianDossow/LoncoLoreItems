package net.nifheim.yitan.lorestats.enchants;

import net.nifheim.yitan.lorestats.Main;

public class InternalCooldown {

    public boolean hasCooldown(String playerName, int getSeconds) {
        if (getSeconds == 0) {
            return false;
        }
        if (Main.getInstance().internalCooldowns.get(playerName) != null) {
            return System.currentTimeMillis() <= (Main.getInstance().internalCooldowns.get(playerName)) + getSeconds * 1000;
        }
        return false;
    }
}
