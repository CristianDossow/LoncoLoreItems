package net.nifheim.yitan.loncoloremagics;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpellListeners implements Listener {

    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack offhand = event.getPlayer().getInventory().getItemInOffHand();
            if (offhand != null && offhand.getType().equals(Material.DIAMOND_HOE) && offhand.getDurability()==98) {
                if (offhand.hasItemMeta()) {
                    if (offhand.getItemMeta().hasLore()) {
                        String spellname = offhand.getItemMeta().getLore().get(0);
                        spellname = ChatColor.stripColor(spellname);
                        Spell spell = SpellsList.getSpell(spellname);
                        if (spell != null) {
                            event.getPlayer().sendMessage("lanzando " + spell.name);
                            SpellCast.spellBuilder(spell, event.getPlayer());
                        } else {
                            event.getPlayer().sendMessage("no contiene echizos");
                        }
                    }
                }
            }
        }
    }
}
