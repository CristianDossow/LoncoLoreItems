package net.nifheim.yitan.lorestats.API;

import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.utils.Util_Format;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetStats {

    Util_Format util_Format = new Util_Format();

    private int getLineForStat(String searchFor, String itemLore) {
        int line = 0;

        String[] splitLore = itemLore.split(", ");
        String[] arrayOfString1;
        int j = (arrayOfString1 = splitLore).length;
        for (int i = 0; i < j; i++) {
            String getStat = arrayOfString1[i];

            if (ChatColor.stripColor(getStat).startsWith(searchFor + ": ")) {
                return line;
            }
            line++;
        }

        return 1337;
    }

    public void setArmour(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("primaryStats.armour.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("primaryStats.armour.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("primaryStats.armour.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setDodge(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.dodge.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.dodge.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.dodge.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setBlock(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.block.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.block.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.block.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setCritChance(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.critChance.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.critChance.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.critChance.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setCritDamage(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.critDamage.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.critDamage.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.critDamage.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setDamageMin(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("primaryStats.damage.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("primaryStats.damage.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("primaryStats.damage.name") + ": ").length()).trim().split("-")[0].trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setDamageMax(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("primaryStats.damage.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("primaryStats.damage.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("primaryStats.damage.name") + ": ").length()).trim().split("-")[1].trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setHealth(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("primaryStats.health.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("primaryStats.health.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("primaryStats.health.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setHealthRegen(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("primaryStats.healthRegen.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("primaryStats.healthRegen.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("primaryStats.healthRegen.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setLifeSteal(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.lifeSteal.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.lifeSteal.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.lifeSteal.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setReflect(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.reflect.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.reflect.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.reflect.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setIce(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.ice.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.ice.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.ice.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setFire(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.fire.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.fire.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.fire.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setPoison(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.poison.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.poison.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.poison.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setWither(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.wither.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.wither.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.wither.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setHarming(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.harming.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.harming.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.harming.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setBlind(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.blind.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.blind.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.blind.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setMovementSpeed(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("secondaryStats.movementSpeed.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("secondaryStats.movementSpeed.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("secondaryStats.movementSpeed.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setXPMultiplier(Player player, ItemStack itemStack, double value) {
        ItemStack modifyItem = itemStack;
        double storeVal = this.util_Format.format(value);

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            if (storeItemLore.contains(Main.getInstance().getConfig().getString("bonusStats.xpMultiplier.name"))) {
                int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("bonusStats.xpMultiplier.name"), modifyItem.getItemMeta().getLore().toString());
                String loreLineStat = (String) storeItemLore.get(loreLineNumb);
                String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("bonusStats.xpMultiplier.name") + ": ").length()).trim()).replaceAll("[^0-9.]", "");

                storeItemLore.set(loreLineNumb, loreLineStat.replaceAll(currentStatVal, String.valueOf(this.util_Format.format(storeVal))));

                modifyItemMeta.setLore(storeItemLore);
                modifyItem.setItemMeta(modifyItemMeta);

                player.updateInventory();
            }
        }
    }

    public void setDurability(Player player, ItemStack itemStack, int percentage) {
        ItemStack modifyItem = itemStack;
        double storeVal = percentage;

        if ((modifyItem != null)
                && (modifyItem.getType() != Material.AIR)
                && (modifyItem.getItemMeta() != null)
                && (modifyItem.getItemMeta().getLore() != null)) {
            List<String> storeItemLore = itemStack.getItemMeta().getLore();
            ItemMeta modifyItemMeta = itemStack.getItemMeta();

            int loreLineNumb = getLineForStat(Main.getInstance().getConfig().getString("bonusStats.durability.name"), modifyItem.getItemMeta().getLore().toString());
            String loreLineStat = (String) storeItemLore.get(loreLineNumb);
            String currentStatVal = String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("bonusStats.durability.name") + ": ").length()).trim().split("/")[0]).replaceAll("[^0-9.]", "");
            double maxDur = Integer.parseInt(String.valueOf(ChatColor.stripColor(loreLineStat).substring((Main.getInstance().getConfig().getString("bonusStats.durability.name") + ": ").length()).trim().split("/")[1]).replaceAll("[^0-9.]", ""));

            storeVal = maxDur / 100.0D * percentage;

            storeItemLore.set(loreLineNumb, loreLineStat.replaceFirst(currentStatVal, String.valueOf(Math.round(storeVal))));

            modifyItemMeta.setLore(storeItemLore);
            modifyItem.setItemMeta(modifyItemMeta);

            player.updateInventory();
        }
    }
}
