package net.nifheim.yitan.loncoloremagics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStatsFormules;

public class SpellListeners implements Listener {

    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack offhand = event.getPlayer().getInventory().getItemInOffHand();
            ItemStack mainhand = event.getPlayer().getInventory().getItemInMainHand();
            if (offhand != null && offhand.getType().equals(Material.DIAMOND_HOE) && offhand.getDurability()==98) {
                if (offhand.hasItemMeta()) {
                    if (offhand.getItemMeta().hasLore()) {
                        String spellname = offhand.getItemMeta().getLore().get(0);
                        spellname = ChatColor.stripColor(spellname);
                        Spell spell = SpellsList.getSpell(spellname);
                        if (spell != null) {
                        	double power = PlayerStatsFormules.getStat(PlayerStatsFormules.magicPower, mainhand);
                            if(mainhand.getType().equals(Material.DIAMOND_SWORD)&& power>0){
                                SpellCast.spellBuilder(spell, event.getPlayer());
                            }else {
                            	if(Main.plugin.getPlayerStats(event.getPlayer()).lastMessage+2000<System.currentTimeMillis()){
                            		event.getPlayer().sendMessage("Se requiere un arma mágica para lanzar hechizos");
                            		Main.plugin.getPlayerStats(event.getPlayer()).lastMessage=System.currentTimeMillis();
                            	}
                            }
                        } else {
                        	if(Main.plugin.getPlayerStats(event.getPlayer()).lastMessage+2000<System.currentTimeMillis()){
                        		event.getPlayer().sendMessage("no contiene echizos");
                        		Main.plugin.getPlayerStats(event.getPlayer()).lastMessage=System.currentTimeMillis();
                        	}
                        }
                    }
                }
            }
        }
    }
}
