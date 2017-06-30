package net.nifheim.yitan.itemlorestats.ItemUpgrading;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUpgrade {

    net.nifheim.yitan.itemlorestats.GearStats gearStats = new net.nifheim.yitan.itemlorestats.GearStats();
    net.nifheim.yitan.itemlorestats.Util.Util_Colours util_Colours = new net.nifheim.yitan.itemlorestats.Util.Util_Colours();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();

    static String languageRegex = "[^A-Za-z������������_]";

    public void increaseItemStatOnItemInHand(Player player, ItemStack itemInHand, String handType) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            String damageName = Main.plugin.getConfig().getString("primaryStats.damage.name");
            String healthName = Main.plugin.getConfig().getString("primaryStats.health.name");
            String heroesManaName = Main.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
            String levelName = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");

            if ((itemInHand != null)
                    && (itemInHand.hasItemMeta())
                    && (itemInHand.getItemMeta().hasLore())) {
                List<String> itemLore = itemInHand.getItemMeta().getLore();
                ItemMeta setItemInHandMeta = itemInHand.getItemMeta();
                int globalIndex = 0;

                if (ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
                    String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0];
                    int upgrades = 0;

                    if (levelStatIndex.contains("[+")) {
                        upgrades = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "")) + 1;
                    } else {
                        upgrades = 1;
                    }

                    if (Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
                        return;
                    }
                    if (upgrades > Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
                        if (Main.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                            player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, itemInHand.getItemMeta().getDisplayName(), itemInHand.getItemMeta().getDisplayName()));
                        }
                        return;
                    }

                    for (String line : itemLore) {
                        if (globalIndex < itemLore.size()) {
                            globalIndex++;

                            for (int i = 0; i < Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); i++) {
                                String key = Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "");
                                String statName = "";

                                if (Main.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("primaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("secondaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("bonusStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                                }

                                statName = statName.replaceAll(" ", "");

                                String lore = ChatColor.stripColor(line.toString());

                                if ((lore.replaceAll(this.languageRegex, "").matches(statName)) && (lore.contains("%"))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.startsWith(levelName)) {
                                    if ((lore.contains("[+")) && (lore.contains("]"))) {
                                        String getLine = line;
                                        int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                                        getLine = getLine.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                                    }
                                } else if ((lore.replaceAll(this.languageRegex, "").matches(healthName)) || (lore.replaceAll(this.languageRegex, "").matches(heroesManaName))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if ((lore.replaceAll(this.languageRegex, "").matches(damageName)) && (!lore.contains("%"))) {
                                    if (lore.contains("-")) {
                                        String getLine = line;
                                        double getMinNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                                        double getMaxNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());

                                        double upgradeMinNumbers = getMinNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats.damage");
                                        double upgradeMaxNumbers = getMaxNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats.damage");

                                        getLine = getLine.replaceAll(String.valueOf(getMinNumbers), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                                        getLine = getLine.replaceAll(String.valueOf(getMaxNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        String getLine = line;
                                        double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                        double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats.damage");

                                        getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return;
                }

                setItemInHandMeta.setLore(itemLore);
                itemInHand.setItemMeta(setItemInHandMeta);

                ItemStack setItemInHand = new ItemStack(itemInHand);

                if (handType.equals("Main")) {
                    player.getInventory().setItemInMainHand(new ItemStack(setItemInHand));
                } else if (handType.equals("Off")) {
                    player.getInventory().setItemInOffHand(new ItemStack(setItemInHand));
                }

                if (Main.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
                    player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, itemInHand.getItemMeta().getDisplayName(), itemInHand.getItemMeta().getDisplayName()));
                }
            }
        }
    }

    public void increaseItemStatOnHelmet(Player player) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            String damageName = Main.plugin.getConfig().getString("primaryStats.damage.name");
            String healthName = Main.plugin.getConfig().getString("primaryStats.health.name");
            String heroesManaName = Main.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
            String levelName = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");

            ItemStack item = player.getInventory().getHelmet();

            if ((item != null)
                    && (item.hasItemMeta())
                    && (item.getItemMeta().hasLore())) {
                List<String> itemLore = item.getItemMeta().getLore();
                ItemMeta itemMeta = item.getItemMeta();
                int globalIndex = 0;

                if (ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
                    String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
                    int upgrades = 0;

                    if (levelStatIndex.contains("[+")) {
                        upgrades = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
                    } else {
                        upgrades = 1;
                    }

                    if (Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
                        return;
                    }
                    if (upgrades > Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
                        if (Main.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                            player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, player.getInventory().getHelmet().getItemMeta().getDisplayName(), player.getInventory().getHelmet().getItemMeta().getDisplayName()));
                        }
                        return;
                    }

                    for (String line : itemLore) {
                        if (globalIndex < itemLore.size()) {
                            globalIndex++;

                            for (int i = 0; i < Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); i++) {
                                String key = Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "");
                                String statName = "";

                                if (Main.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("primaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("secondaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("bonusStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                                }

                                statName = statName.replaceAll(" ", "");

                                String lore = ChatColor.stripColor(line.toString());

                                if ((lore.replaceAll(this.languageRegex, "").matches(statName)) && (lore.contains("%"))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.startsWith(levelName)) {
                                    if ((lore.contains("[+")) && (lore.contains("]"))) {
                                        String getLine = line;
                                        int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                                        getLine = getLine.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                                    }
                                } else if ((lore.replaceAll(this.languageRegex, "").matches(healthName)) || (lore.replaceAll(this.languageRegex, "").matches(heroesManaName))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                                    if (lore.contains("-")) {
                                        String getLine = line;
                                        double getMinNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                                        double getMaxNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());

                                        double upgradeMinNumbers = getMinNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                                        double upgradeMaxNumbers = getMaxNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getMinNumbers), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                                        getLine = getLine.replaceAll(String.valueOf(getMaxNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        String getLine = line;
                                        double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                        double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return;
                }

                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);

                player.getInventory().setHelmet(new ItemStack(item));

                if (Main.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
                    player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                }
            }
        }
    }

    public void increaseItemStatOnChestplate(Player player) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            String damageName = Main.plugin.getConfig().getString("primaryStats.damage.name");
            String healthName = Main.plugin.getConfig().getString("primaryStats.health.name");
            String heroesManaName = Main.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
            String levelName = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");

            ItemStack item = player.getInventory().getChestplate();

            if ((item != null)
                    && (item.hasItemMeta())
                    && (item.getItemMeta().hasLore())) {
                List<String> itemLore = item.getItemMeta().getLore();
                ItemMeta itemMeta = item.getItemMeta();
                int globalIndex = 0;

                if (ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
                    String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
                    int upgrades = 0;

                    if (levelStatIndex.contains("[+")) {
                        upgrades = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
                    } else {
                        upgrades = 1;
                    }

                    if (Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
                        return;
                    }
                    if (upgrades > Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
                        if (Main.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                            player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                        }
                        return;
                    }

                    for (String line : itemLore) {
                        if (globalIndex < itemLore.size()) {
                            globalIndex++;

                            for (int i = 0; i < Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); i++) {
                                String key = Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
                                String statName = "";

                                if (Main.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("primaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("secondaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("bonusStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                                }

                                statName = statName.replaceAll(" ", "");

                                String lore = ChatColor.stripColor(line.toString());

                                if ((lore.replaceAll(this.languageRegex, "").matches(statName)) && (lore.contains("%"))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.startsWith(levelName)) {
                                    if ((lore.contains("[+")) && (lore.contains("]"))) {
                                        String getLine = line;
                                        int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                                        getLine = getLine.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                                    }
                                } else if ((lore.replaceAll(this.languageRegex, "").matches(healthName)) || (lore.replaceAll(this.languageRegex, "").matches(heroesManaName))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                                    if (lore.contains("-")) {
                                        String getLine = line;
                                        double getMinNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                                        double getMaxNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());

                                        double upgradeMinNumbers = getMinNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                                        double upgradeMaxNumbers = getMaxNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getMinNumbers), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                                        getLine = getLine.replaceAll(String.valueOf(getMaxNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        String getLine = line;
                                        double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                        double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return;
                }

                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);

                player.getInventory().setChestplate(new ItemStack(item));

                if (Main.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
                    player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                }
            }
        }
    }

    public void increaseItemStatOnLeggings(Player player) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            String damageName = Main.plugin.getConfig().getString("primaryStats.damage.name");
            String healthName = Main.plugin.getConfig().getString("primaryStats.health.name");
            String heroesManaName = Main.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
            String levelName = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");

            ItemStack item = player.getInventory().getLeggings();
            if ((item != null)
                    && (item.hasItemMeta())
                    && (item.getItemMeta().hasLore())) {
                List<String> itemLore = item.getItemMeta().getLore();
                ItemMeta itemMeta = item.getItemMeta();
                int globalIndex = 0;

                if (ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
                    String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
                    int upgrades = 0;

                    if (levelStatIndex.contains("[+")) {
                        upgrades = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
                    } else {
                        upgrades = 1;
                    }

                    if (Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
                        return;
                    }
                    if (upgrades > Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
                        if (Main.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                            player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                        }
                        return;
                    }

                    for (String line : itemLore) {
                        if (globalIndex < itemLore.size()) {
                            globalIndex++;

                            for (int i = 0; i < Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); i++) {
                                String key = Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
                                String statName = "";

                                if (Main.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("primaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("secondaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("bonusStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                                }

                                statName = statName.replaceAll(" ", "");

                                String lore = ChatColor.stripColor(line.toString());

                                if ((lore.replaceAll(this.languageRegex, "").matches(statName)) && (lore.contains("%"))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.startsWith(levelName)) {
                                    if ((lore.contains("[+")) && (lore.contains("]"))) {
                                        String getLine = line;
                                        int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                                        getLine = getLine.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                                    }
                                } else if ((lore.replaceAll(this.languageRegex, "").matches(healthName)) || (lore.replaceAll(this.languageRegex, "").matches(heroesManaName))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                                    if (lore.contains("-")) {
                                        String getLine = line;
                                        double getMinNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                                        double getMaxNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());

                                        double upgradeMinNumbers = getMinNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                                        double upgradeMaxNumbers = getMaxNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getMinNumbers), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                                        getLine = getLine.replaceAll(String.valueOf(getMaxNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        String getLine = line;
                                        double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                        double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return;
                }

                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);

                player.getInventory().setLeggings(new ItemStack(item));

                if (Main.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
                    player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                }
            }
        }
    }

    public void increaseItemStatOnBoots(Player player) {
        if (!Main.plugin.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            String damageName = Main.plugin.getConfig().getString("primaryStats.damage.name");
            String healthName = Main.plugin.getConfig().getString("primaryStats.health.name");
            String heroesManaName = Main.plugin.getConfig().getString("heroesOnlyStats.heroesMaxMana.name");
            String levelName = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");

            ItemStack item = player.getInventory().getBoots();

            if ((item != null)
                    && (item.hasItemMeta())
                    && (item.getItemMeta().hasLore())) {
                List<String> itemLore = item.getItemMeta().getLore();
                ItemMeta itemMeta = item.getItemMeta();
                int globalIndex = 0;

                if (ChatColor.stripColor(itemLore.toString()).contains(levelName)) {
                    String levelStatIndex = ChatColor.stripColor(itemLore.toString()).substring(ChatColor.stripColor(itemLore.toString()).indexOf(levelName)).split(",")[0].trim();
                    int upgrades = 0;

                    if (levelStatIndex.contains("[+")) {
                        upgrades = Integer.parseInt(levelStatIndex.split("\\+")[1].split("\\]")[0].replaceAll("\\[", "").replaceAll("\\]", "").trim()) + 1;
                    } else {
                        upgrades = 1;
                    }

                    if (Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap") == 0) {
                        return;
                    }
                    if (upgrades > Main.plugin.getConfig().getInt("upgradeStatsOnLevelChange.upgradeCap")) {
                        if (Main.plugin.getConfig().getBoolean("messages.upgradeCapReached")) {
                            player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeCapReached", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                        }
                        return;
                    }

                    for (String line : itemLore) {
                        if (globalIndex < itemLore.size()) {
                            globalIndex++;

                            for (int i = 0; i < Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).size(); i++) {
                                String key = Main.plugin.getConfig().getConfigurationSection("upgradeStatsOnLevelChange.stats").getKeys(false).toString().split(",")[i].trim().replaceAll("&([0-9a-f])", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
                                String statName = "";

                                if (Main.plugin.getConfig().getString("primaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("primaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("secondaryStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("secondaryStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("bonusStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("bonusStats." + key + ".name");
                                } else if (Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name") != null) {
                                    statName = Main.plugin.getConfig().getString("heroesOnlyStats." + key + ".name");
                                }

                                statName = statName.replaceAll(" ", "");

                                String lore = ChatColor.stripColor(line.toString());

                                if ((lore.replaceAll(this.languageRegex, "").matches(statName)) && (lore.contains("%"))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", ""));
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.startsWith(levelName)) {
                                    if ((lore.contains("[+")) && (lore.contains("]"))) {
                                        String getLine = line;
                                        int getNumbers = Integer.parseInt(ChatColor.stripColor(String.valueOf(line.split("\\+")[1].split("\\]")[0])).replaceAll("[^0-9.]", "").trim());
                                        getLine = getLine.replaceAll("\\+" + getNumbers, "\\+" + (getNumbers + 1));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        itemLore.set(globalIndex - 1, line + ChatColor.DARK_GREEN + " [" + ChatColor.RED + "+1" + ChatColor.DARK_GREEN + "]");
                                    }
                                } else if ((lore.replaceAll(this.languageRegex, "").matches(healthName)) || (lore.replaceAll(this.languageRegex, "").matches(heroesManaName))) {
                                    String getLine = line;
                                    double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                    double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                    getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                    itemLore.set(globalIndex - 1, getLine);
                                } else if (lore.replaceAll(this.languageRegex, "").matches(damageName)) {
                                    if (lore.contains("-")) {
                                        String getLine = line;
                                        double getMinNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[0].replaceAll("[^0-9.]", "").trim());
                                        double getMaxNumbers = Double.parseDouble(ChatColor.stripColor(line).split("\\-")[1].replaceAll("[^0-9.]", "").trim());

                                        double upgradeMinNumbers = getMinNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);
                                        double upgradeMaxNumbers = getMaxNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getMinNumbers), String.valueOf(this.util_Format.format(upgradeMinNumbers)));
                                        getLine = getLine.replaceAll(String.valueOf(getMaxNumbers), String.valueOf(this.util_Format.format(upgradeMaxNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    } else {
                                        String getLine = line;
                                        double getNumbers = Double.parseDouble(ChatColor.stripColor(line).replaceAll("[^0-9.]", "").trim());
                                        double upgradeNumbers = getNumbers + Main.plugin.getConfig().getDouble("upgradeStatsOnLevelChange.stats." + key);

                                        getLine = getLine.replaceAll(String.valueOf(getNumbers), String.valueOf(this.util_Format.format(upgradeNumbers)));

                                        itemLore.set(globalIndex - 1, getLine);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return;
                }

                itemMeta.setLore(itemLore);
                item.setItemMeta(itemMeta);

                player.getInventory().setBoots(new ItemStack(item));

                if (Main.plugin.getConfig().getBoolean("messages.upgradeSuccessful")) {
                    player.sendMessage(this.util_GetResponse.getResponse("UpgradeMessages.UpgradeSuccessful", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
                }
            }
        }
    }
}
