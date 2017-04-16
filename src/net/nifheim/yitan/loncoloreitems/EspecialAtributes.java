package net.nifheim.yitan.loncoloreitems;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.nifheim.yitan.itemlorestats.Main;

public class EspecialAtributes {
    public static Main plugin;

    static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
    //public static String destroyname = Main.getInstance().getMessages().getString("Especial Atributes.Desroy");
    public static String destroyname = "Destrucción";
    //public static String weaponspeed = Main.getInstance().getMessages().getString("Especial Atributes.Weapon Speed");
    public static String weaponspeed ="vataque";
    //public static String voidbound = Main.getInstance().getMessages().getString("Especial Atributes.Void Bound");
    public static String voidbound ="Retorno del End";
    //public static String unknownItem = Main.getInstance().getMessages().getString("Especial Atributes.Unknow Item");
    public static String unknownItem ="Artículo no identificado";
    public static String enchantgiver = "Poder almacenado:";
    //public static String repairer = Main.getInstance().getMessages().getString("Especial Atributes.Repairer");
    public static String repairer ="Reparación";
    public static String durability = Main.plugin.getConfig().getString("bonusStats.durability.name");

    static public boolean HasDestroy(Player player) {

        if (player.getInventory().getItemInMainHand().hasItemMeta()) {
            if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                List<String> lore = player.getInventory().getItemInMainHand().getItemMeta().getLore();
                for (String loreline : lore) {
                    loreline = ChatColor.stripColor(loreline);
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(destroyname.toLowerCase().replaceAll(languageRegex, ""))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static public boolean HasEffect(ItemStack item, String effect) {
    	
        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                for (String loreline : lore) {
                    loreline = ChatColor.stripColor(loreline);
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(effect.toLowerCase().replaceAll(languageRegex, ""))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static public boolean Hasdurability(ItemStack item) {

        if (item.hasItemMeta()) {
            if (item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                for (String loreline : lore) {
                    loreline = ChatColor.stripColor(loreline);
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(durability.toLowerCase().replaceAll(languageRegex, ""))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static public boolean HasVoidBound(ItemStack item) {

        if (item != null) {
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getLore() != null) {
                    for (String loreline : item.getItemMeta().getLore()) {
                        loreline = ChatColor.stripColor(loreline);
                        loreline = loreline.toLowerCase();
                        if (loreline.replaceAll(languageRegex, "").matches(voidbound.toLowerCase().replaceAll(languageRegex, ""))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static public boolean IsUnknownItem(ItemStack item) {

        if (item != null) {
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getLore() != null) {
                    for (String loreline : item.getItemMeta().getLore()) {
                        loreline = ChatColor.stripColor(loreline);
                        loreline = loreline.toLowerCase();
                        if (loreline.replaceAll(languageRegex, "").matches(unknownItem.toLowerCase().replaceAll(languageRegex, ""))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static public boolean IsEnchantGiver(ItemStack item) {
        if (item != null) {
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() > 1) {
                    String loreline = item.getItemMeta().getLore().get(1);
                    loreline = ChatColor.stripColor(loreline);
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(enchantgiver.toLowerCase().replaceAll(languageRegex, ""))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static public boolean IsRepairerItem(ItemStack item) {
        if (item != null) {
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() > 0) {
                    String loreline = item.getItemMeta().getLore().get(0);
                    loreline = ChatColor.stripColor(loreline);
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(repairer.toLowerCase().replaceAll(languageRegex, ""))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static public int getRepairerPower(ItemStack item) {
        int repairPower = 0;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            if (item.getItemMeta().getLore() != null && item.getItemMeta().getLore().size() > 0) {
                String loreline = item.getItemMeta().getLore().get(0);
                loreline = ChatColor.stripColor(loreline);
                loreline = loreline.toLowerCase();
                if (loreline.replaceAll(languageRegex, "").matches(repairer.toLowerCase().replaceAll(languageRegex, ""))) {
                    repairPower = Integer.parseInt(loreline.replaceAll("[^0-9]", ""));
                }
            }
        }
        return repairPower;
    }

    static public String getEnchantGiverPower(ItemStack item) {
        String power = "";
        if (item.getItemMeta().getLore().size() > 2) {
            power = item.getItemMeta().getLore().get(2);
        }
        return power;
    }

    static public double getWeaponSpeed(ItemStack item) {
        double thisweaponspeed = 1;
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String loreline : lore) {
                loreline = ChatColor.stripColor(loreline);
                loreline = loreline.toLowerCase();
                if (loreline.replaceAll(languageRegex, "").matches(weaponspeed.toLowerCase().replaceAll(languageRegex, ""))) {
                    thisweaponspeed = Double.parseDouble(loreline.replaceAll("[^0-9.+-]", ""));
                }

            }
        }

        return thisweaponspeed;
    }

    static public boolean HaveEnchant(ItemStack item, String enchant) {

        if (item != null) {
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().getLore() != null) {
                    for (String loreline : item.getItemMeta().getLore()) {
                        loreline = ChatColor.stripColor(loreline);
                        loreline = loreline.toLowerCase();
                        if (loreline.replaceAll(languageRegex, "").matches(enchant.toLowerCase().replaceAll(languageRegex, ""))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
