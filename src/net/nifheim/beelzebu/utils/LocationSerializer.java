package net.nifheim.beelzebu.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer {

    public static String locationToString(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getPitch() + ";" + loc.getYaw();
    }

    public static Location locationFromString(String string) {
        String[] s = string.split(";");
        return new Location(Bukkit.getWorld(s[0]), Double.valueOf(s[1]), Double.valueOf(s[2]), Double.valueOf(s[3]), Float.valueOf(s[4]), Float.valueOf(s[5]));
    }
}
