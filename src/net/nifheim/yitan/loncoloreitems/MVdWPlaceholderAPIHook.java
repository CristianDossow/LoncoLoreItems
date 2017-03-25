package net.nifheim.yitan.loncoloreitems;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.nifheim.yitan.itemlorestats.Main;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;

public class MVdWPlaceholderAPIHook {
    public static void hook(Main plugin) {
        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceHolderAPI")) {
        	
            PlaceholderAPI.registerPlaceholder(plugin, "ilsdefense",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            return plugin.gearStats.getArmourGear(e.getPlayer())+"%";
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsdamage",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            
                            		
                            return plugin.gearStats.getPlayerDamage(e.getPlayer());
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsdodge",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            
                            		
                            return plugin.gearStats.getDodgeGear(e.getPlayer())+"%";
                        }
            	}
            );
            PlaceholderAPI.registerPlaceholder(plugin, "ilsmovementspeed",
                    new PlaceholderReplacer() {
                        @Override
                        public String onPlaceholderReplace(PlaceholderReplaceEvent e) {
                            
                            		
                            return plugin.gearStats.getTotalMovementSpeed(e.getPlayer())+"%";
                        }
            	}
            );
            
        }
    }
}
