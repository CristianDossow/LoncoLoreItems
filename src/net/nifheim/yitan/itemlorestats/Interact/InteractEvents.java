package net.nifheim.yitan.itemlorestats.Interact;

import net.nifheim.yitan.itemlorestats.Classes;
import net.nifheim.yitan.itemlorestats.GearStats;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.Soulbound;
import net.nifheim.yitan.itemlorestats.Spells.SpellsEvents;
import net.nifheim.yitan.itemlorestats.XpLevel;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvents implements org.bukkit.event.Listener {

    public Main instance;
    Classes classes = new Classes();
    GearStats gearStats = new GearStats();
    Soulbound soulbound = new Soulbound();
    XpLevel xpLevel = new XpLevel();
    SpellsEvents spellsEvents = new SpellsEvents();

    @org.bukkit.event.EventHandler
    public void mainInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (Main.getInstance().getConfig().getBoolean("usingMcMMO")) {
            return;
        }
        if (Main.getInstance().getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            return;
        }
        if ((player.getGameMode().equals(GameMode.CREATIVE)) && (player.getInventory().getItemInMainHand().getType().equals(Material.SKULL_ITEM))) {
            return;
        }
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (Main.getInstance().isTool(player.getInventory().getItemInMainHand().getType())) {
                this.spellsEvents.spellSelection(player);
            } else if (Main.getInstance().isArmour(player.getInventory().getItemInMainHand().getType())) {
                if (!this.xpLevel.checkXPLevel(player, player.getInventory().getItemInMainHand())) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }

                if (!this.soulbound.checkSoulbound(player, player.getInventory().getItemInMainHand())) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }

                if (!this.classes.checkClasses(player, player.getInventory().getItemInMainHand())) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
                /*
         final Player playerFinal = player;
         Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
           public void run() {
             Main.getInstance().updateHealth(playerFinal);
             Main.getInstance().updatePlayerSpeed(playerFinal);
             Main.getInstance().setBonuses.updateSetBonus(playerFinal);
           }
           
         }, 3L);*/
            } else if ((event.getItem() != null) && (Main.getInstance().isPotion(event.getItem().getDurability()))) {
                if (!this.xpLevel.checkXPLevel(player, player.getInventory().getItemInMainHand())) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }

                if (!this.soulbound.checkSoulbound(player, player.getInventory().getItemInMainHand())) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }

                if (!this.classes.checkClasses(player, player.getInventory().getItemInMainHand())) {
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
            }
        }

        if ((event.getAction() == Action.LEFT_CLICK_BLOCK)
                && (Main.getInstance().isTool(player.getInventory().getItemInMainHand().getType()))) {
            if (!this.xpLevel.checkXPLevel(player, player.getInventory().getItemInMainHand())) {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }

            if (!this.soulbound.checkSoulbound(player, player.getInventory().getItemInMainHand())) {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }

            if (!this.classes.checkClasses(player, player.getInventory().getItemInMainHand())) {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
        }
    }
}
