package net.nifheim.yitan.lorestats;

import net.nifheim.yitan.lorestats.utils.Util_Colours;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GenerateFromFile implements org.bukkit.event.Listener {

    Util_Colours util_Colours = new Util_Colours();
    private File PlayerDataFile;
    private FileConfiguration PlayerDataConfig;

    public void exportWeapon(Player player, String WeaponName) {
        try {
            this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
            this.PlayerDataFile = new File(Main.getInstance().getDataFolder() + File.separator + "SavedItems" + File.separator + WeaponName + ".yml");

            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (itemInHand != null) {
                if (itemInHand.getItemMeta().getLore() != null) {
                    this.PlayerDataConfig.set("Item", itemInHand);

                    this.PlayerDataConfig.save(this.PlayerDataFile);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully exported " + ChatColor.GOLD + WeaponName + ChatColor.LIGHT_PURPLE + "!");
                } else {
                    player.sendMessage(ChatColor.RED + "Failed to export: Item in hand doesn't contain any lore!");
                }
            }
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Failed to export: Check console!");
            e.printStackTrace();
            System.out.println("*********** Item config failed to save! ***********");
        }
    }

    public ItemStack importWeapon(String weaponName, String newWeaponName, String playerName) {
        try {
            this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
            this.PlayerDataConfig.load(new File(Main.getInstance().getDataFolder() + File.separator + "SavedItems" + File.separator + weaponName + ".yml"));

            ItemStack newItemInHand = this.PlayerDataConfig.getItemStack("Item");

            if (newWeaponName != "noChange") {
                ItemMeta newItemInHandMeta = newItemInHand.getItemMeta();
                newItemInHandMeta.setDisplayName(this.util_Colours.replaceTooltipColour(newWeaponName));
                newItemInHand.setItemMeta(newItemInHandMeta);
            }

            if (Main.getInstance().getConfig().getBoolean("messages.itemReceived")) {
                Bukkit.getServer().getOfflinePlayer(playerName).getPlayer().sendMessage(Bukkit.getServer().getOfflinePlayer(playerName).getPlayer().getName() + ChatColor.LIGHT_PURPLE + " successfully received " + ChatColor.RESET + newItemInHand.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + ".");
            }

            return newItemInHand;
        } catch (Exception e) {
            Bukkit.getServer().getOfflinePlayer(playerName).getPlayer().sendMessage(ChatColor.RED + "Failed to load: Check console!");
            e.printStackTrace();
            System.out.println("*********** Item config failed to load! ***********");
        }

        return null;
    }
}
