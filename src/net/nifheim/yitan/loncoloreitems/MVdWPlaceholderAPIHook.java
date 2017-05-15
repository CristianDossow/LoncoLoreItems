package net.nifheim.yitan.loncoloreitems;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.nifheim.yitan.itemlorestats.Main;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;

public class MVdWPlaceholderAPIHook {
	static DecimalFormat df = new DecimalFormat("#.#");
    public static void hook(Main plugin) {
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceHolderAPI")) {
        	
            PlaceholderAPI.registerPlaceholder(plugin, "ilsdefense",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                        	
                            return df.format(Main.getInstance().getPlayerStats(e.getPlayer()).percentArmor*100)+"%";
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsdamage",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            String damageMin = df.format(Main.getInstance().getPlayerStats(e.getPlayer()).minDamage);
                            String damageMax = df.format(Main.getInstance().getPlayerStats(e.getPlayer()).maxDamage);
                            return damageMin +"-"+ damageMax;
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsmagicpower",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            return Main.getInstance().getPlayerStats(e.getPlayer()).magicPower+"";
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsdodge",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            
                            		
                            return df.format(Main.getInstance().getPlayerStats(e.getPlayer()).dodge*100)+"%";
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsmovementspeed",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            
                            		
                            return df.format(Main.getInstance().getPlayerStats(e.getPlayer()).movementSpeed*100)+"%";
                        }
            	}
            );
            
        }
    }
}
