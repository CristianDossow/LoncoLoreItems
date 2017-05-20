package net.nifheim.yitan.skills;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SkillListener implements Listener{
    
    static SkillList sl = new SkillList();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ItemStack mainhand = event.getPlayer().getInventory().getItemInMainHand();
            if (event.getPlayer().isSneaking()&&mainhand != null) {
                if (mainhand.hasItemMeta() && !mainhand.getType().equals(Material.PAPER)) {
                    if (mainhand.getItemMeta().hasLore()) {
                    	sl.RunSkills(event.getPlayer(), mainhand);
                    }
                }
            }
        }
    }
}
