package net.nifheim.yitan.itemlorestats.listeners;

import net.nifheim.yitan.itemlorestats.Main;

import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void ItemToStack(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();
        if(item.getType().equals(Material.DIAMOND_HOE)&&item.getDurability()!=0){
            for( Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(item.getType()).entrySet()){
            	if(item.getAmount()>0&&entry.getValue().isSimilar(item)){
            		if(entry.getValue().getAmount()+item.getAmount()>64){
            			int dif =64 - entry.getValue().getAmount() ;
            			entry.getValue().setAmount(entry.getValue().getAmount()+dif);
            			event.getItem().getItemStack().setAmount(item.getAmount()-dif);
            		}else{
            			entry.getValue().setAmount(entry.getValue().getAmount()+item.getAmount());
            			event.getItem().getItemStack().setAmount(0);
            			event.getItem().remove();
            			
            		}
            		event.setCancelled(true);
            	}
            }
        }
    }
	
    @EventHandler
    public void onPickupCustomItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        if ((!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()))
                && (Main.plugin.getConfig().getBoolean("messages.itemLooted"))
                && (item != null)
                && (item.getItemMeta() != null)
                && (item.getItemMeta().hasDisplayName())
                && (item.getItemMeta().getDisplayName().startsWith("ILS_"))) {
            ItemStack itemStack = item.clone();
            ItemMeta itemMeta = item.getItemMeta();

            itemMeta.setDisplayName(item.getItemMeta().getDisplayName().substring(4));
            item.setItemMeta(itemMeta);

            event.getItem().setItemStack(itemStack);
            player.sendMessage(Main.plugin.util_GetResponse.getResponse("Item.Looted", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
        }
    }

    @EventHandler
    public void checkOnPickup(PlayerPickupItemEvent event) {
        if ((event.isCancelled()) || (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
            return;
        }
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem().getItemStack().clone();

            if ((item != null)
                    && (item.getAmount() == 1)
                    && (Main.plugin.isTool(item.getType()))
                    && (item.hasItemMeta())
                    && (item.getItemMeta().getLore() != null)) {
                if (player.getInventory().firstEmpty() == player.getInventory().getHeldItemSlot()) {
                    for (int slot = player.getInventory().getHeldItemSlot() + 1; slot < 35; slot++) {
                        if (player.getInventory().getItem(slot) == null) {
                            if ((!"".equals(Main.plugin.gearStats.getSoulboundName(player, item)))
                                    && (!Main.plugin.gearStats.getSoulboundName(player, item).equals(player.getName()))) {
                                event.setCancelled(true);
                                event.getItem().remove();
                                event.getPlayer().getInventory().setItem(slot, item);

                                break;
                            }

                            if ((Main.plugin.gearStats.getXPLevelRequirement(player, item) != 0)
                                    && (Main.plugin.gearStats.getXPLevelRequirement(player, item) > player.getLevel())) {
                                event.setCancelled(true);
                                event.getItem().remove();
                                event.getPlayer().getInventory().setItem(slot, item);

                                break;
                            }

                            if ((Main.plugin.gearStats.getClass(item) != null)
                                    && (!player.hasPermission("ils.use." + Main.plugin.gearStats.getClass(item)))) {
                                event.setCancelled(true);
                                event.getItem().remove();
                                event.getPlayer().getInventory().setItem(slot, item);

                                break;
                            }
                        }
                    }
                }
            }

            Main.plugin.updateHealth(player);
            Main.plugin.updatePlayerSpeed(player);

            Main.plugin.setBonuses.updateSetBonus(player);
        }
    }
}
