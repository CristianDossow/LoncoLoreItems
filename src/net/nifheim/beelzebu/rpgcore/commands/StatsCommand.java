package net.nifheim.beelzebu.rpgcore.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.itemlorestats.CharacterSheet;
import net.nifheim.yitan.itemlorestats.Commands.CreateLore_Com;
import net.nifheim.yitan.itemlorestats.Commands.CustomMaterial_Com;
import net.nifheim.yitan.itemlorestats.Commands.Export_Com;
import net.nifheim.yitan.itemlorestats.Commands.Give_Com;
import net.nifheim.yitan.itemlorestats.Commands.Lore_Com;
import net.nifheim.yitan.itemlorestats.Commands.Name_Com;
import net.nifheim.yitan.itemlorestats.Commands.Repair_Com;
import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.itemlorestats.PlayerStats;
import net.nifheim.yitan.loncoloreitems.EspecialAtributes;
import net.nifheim.yitan.loncoloreitems.ItemCategory;
import net.nifheim.yitan.loncoloreitems.ItemMaker;
import net.nifheim.yitan.loncoloreitems.LoreItemMaker;
import net.nifheim.yitan.loncoloremagics.Spell;
import net.nifheim.yitan.loncoloremagics.SpellsList;
import net.nifheim.yitan.skills.List.WindStep;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Yitan
 */
public class StatsCommand implements CommandExecutor {

    private final Main plugin;
    private final FileConfiguration config;
    private final CreateLore_Com createLore_Com;
    private final CustomMaterial_Com customMaterial_Com;
    private final Export_Com export_Com;
    private final Give_Com give_Com;
    private final Lore_Com lore_Com;
    private final Name_Com name_Com;
    private final Repair_Com repair_Com;

    public StatsCommand(Main main) {
        plugin = main;
        config = plugin.getConfig();
        createLore_Com = new CreateLore_Com();
        customMaterial_Com = new CustomMaterial_Com();
        export_Com = new Export_Com();
        give_Com = new Give_Com();
        lore_Com = new Lore_Com();
        name_Com = new Name_Com();
        repair_Com = new Repair_Com();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ils")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("remake")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        Player player = (Player) sender;
                        int lvl;
                        try {
                            lvl = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.DARK_RED + "El nivel debe ser numerico");
                            return false;
                        }
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item != null) {
                            LoreItemMaker.ClearAndAddItemLore(item, player, lvl);
                            sender.sendMessage("Item Reconstruido");
                        }
                        return true;

                    }
                }
                if (args[0].equalsIgnoreCase("stats2")) {
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        PlayerStats ps = Main.getInstance().getPlayerStats(p);
                        ps.UpdateAll();
                        ps.ShowStats();
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("pergamino")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("voidbound")) {
                                Player player = (Player) sender;
                                ItemStack item = ItemMaker.EnchantScroll(EspecialAtributes.voidbound, ItemCategory.anytype, null);
                                player.getInventory().addItem(item);
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("windstep")) {
                                Player player = (Player) sender;
                                List<String> desc = new ArrayList<>();
                                desc.add("Al usar aumenta la velocidad temporalmente");
                                ItemStack item = ItemMaker.EnchantScroll(new WindStep().getName(), ItemCategory.swordtype, desc);
                                player.getInventory().addItem(item);
                                return true;
                            }
                            return false;
                        }

                        return false;
                    }
                    return false;
                }
                if (args[0].equalsIgnoreCase("giveitem")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        if (args.length > 2) {
                            int power;
                            try {
                                power = Integer.parseInt(args[2]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.DARK_RED + "El nivel debe ser numerico");
                                return false;
                            }
                            Player player = (Player) sender;
                            ItemStack item;
                            if (args[1].equalsIgnoreCase("repairstone")) {
                                item = ItemMaker.RepairerStone(power);
                                player.getInventory().addItem(item);
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("itembooststone")) {
                                item = ItemMaker.itemLevelBoost(power);
                                player.getInventory().addItem(item);
                                return true;
                            }
                            return false;
                        }

                        return false;
                    }
                    return false;
                }
                if (args[0].equalsIgnoreCase("spellbook")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        if (args.length > 1) {
                            Spell spell = SpellsList.getSpell(args[1]);

                            if (spell == null) {
                                sender.sendMessage("El Echiso no existe");
                            } else {
                                Player player = (Player) sender;
                                ItemStack item = ItemMaker.SpellBook(spell);
                                player.getInventory().addItem(item);
                                return true;
                            }
                            return false;
                        }

                        return false;
                    }
                    return false;
                }
                if (args[0].equalsIgnoreCase("loreclear") && sender.hasPermission("ils.admin")) {
                    Player player = (Player) sender;
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                        ItemMeta meta = item.getItemMeta();
                        meta.setLore(new ArrayList<>());
                        item.setItemMeta(meta);
                        sender.sendMessage("Lore Borrado");
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("mana") && sender.hasPermission("ils.admin")) {
                    if (args.length == 3) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p == null) {
                            sender.sendMessage(ChatColor.RED + "Can't found the player");
                            return true;
                        }
                        try {
                            int amount = Integer.parseInt(args[2]);
                            PlayerStats ps = Main.getInstance().getPlayerStats(p);
                            if (ps.manaCurrent + amount > ps.manaMax) {
                                ps.manaCurrent = ps.manaMax;
                            }
                            if (ps.manaCurrent + amount < 0) {
                                ps.manaCurrent = 0;
                            } else {
                                ps.manaCurrent = ps.manaCurrent + amount;
                            }

                            p.sendMessage("has obtenido " + amount + " puntos de maná");
                            sender.sendMessage(p.getDisplayName() + " ha obtenido " + amount + " puntos de maná");
                            return true;
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "invalid given amount");
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
                    }
                }
                if (args[0].equalsIgnoreCase("vida") && sender.hasPermission("ils.admin")) {
                    if (args.length == 3) {
                        Player p = Bukkit.getPlayer(args[1]);
                        if (p == null) {
                            sender.sendMessage(ChatColor.RED + "Can't found the player");
                            return true;
                        }
                        try {
                            int amount = Integer.parseInt(args[2]);
                            if (p.getHealth() + amount > p.getMaxHealth()) {
                                p.setHealth(p.getMaxHealth());
                            }
                            if (p.getHealth() + amount < 0) {
                                p.setHealth(0);
                            } else {
                                p.setHealth(p.getHealth() + amount);
                            }
                            p.sendMessage("has sanado por " + amount + " puntos de vida");
                            sender.sendMessage(p.getDisplayName() + " ha obtenido " + amount + " puntos de vida");
                            return true;
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "invalid given amount");
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
                    }
                }
                if (args[0].equalsIgnoreCase("weapons") && sender.hasPermission("ils.admin")) {
                    if (args.length == 2) {
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            try {
                                int data = Integer.parseInt(args[1]);
                                for (int i = data * 35 + 1; i < data * 35 + 36; i++) {
                                    ItemStack item = ItemMaker.Weapon(i);
                                    p.getInventory().addItem(item);
                                }
                                return true;
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.RED + "invalid given amount");
                                return true;
                            }

                        } else {
                            sender.sendMessage(ChatColor.RED + "only a player can use the command");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
                    }
                }
                if (args[0].equalsIgnoreCase("bows") && sender.hasPermission("ils.admin")) {
                    if (args.length == 2) {
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            try {
                                int data = Integer.parseInt(args[1]);
                                for (int i = data * 35 + 1; i < data * 35 + 36; i++) {
                                    ItemStack item = ItemMaker.Bow(i);
                                    p.getInventory().addItem(item);
                                }
                                return true;
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.RED + "invalid given amount");
                                return true;
                            }

                        } else {
                            sender.sendMessage(ChatColor.RED + "only a player can use the command");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
                    }
                }
                if (args[0].equalsIgnoreCase("tools") && sender.hasPermission("ils.admin")) {
                    if (args.length == 2) {
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            try {
                                int data = Integer.parseInt(args[1]);
                                for (int i = data * 35 + 1; i < data * 35 + 36; i++) {
                                    ItemStack item = ItemMaker.Tool(i);
                                    p.getInventory().addItem(item);
                                }
                                return true;
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.RED + "invalid given amount");
                                return true;
                            }

                        } else {
                            sender.sendMessage(ChatColor.RED + "only a player can use the command");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
                    }
                }
                if (args[0].equalsIgnoreCase("shields") && sender.hasPermission("ils.admin")) {
                    if (args.length == 2) {
                        if (sender instanceof Player) {
                            Player p = (Player) sender;
                            try {
                                int data = Integer.parseInt(args[1]);
                                for (int i = data * 35 + 1; i < data * 35 + 36; i++) {
                                    ItemStack item = ItemMaker.Shield(i);
                                    p.getInventory().addItem(item);
                                }
                                return true;
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.RED + "invalid given amount");
                                return true;
                            }

                        } else {
                            sender.sendMessage(ChatColor.RED + "only a player can use the command");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("ils")) {
            if (args.length == 0) {
                if ((sender instanceof Player)) {
                    if ((sender.isOp()) || (sender.hasPermission("ils.commandlist"))) {
                        sender.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------------------");
                        sender.sendMessage(ChatColor.GOLD + "                             Item Lore Stats              ");
                        sender.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------------------");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils reload");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils stats");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils version");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils name <text>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils lore <player_name> <line#> <text>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils give <player_name> <item_name>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils give <player_name> <item_name> <new_item_name>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils custom tool <item_type>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils custom armour helmet/chest/legs/boots <item_type>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils repair");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils upgrade hand/armour/all");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils combatLog");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils sell");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils export <item_name>");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils setMultiplier");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "   /ils remake <lvl>");
                    } else {
                        sender.sendMessage(plugin.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                    }
                } else {
                    plugin.log("Item Lore Stats commands:");
                    plugin.log("   /ils");
                    plugin.log("   /ils reload");
                    plugin.log("   /ils stats");
                    plugin.log("   /ils version");
                    plugin.log("   /ils name <text>");
                    plugin.log("   /ils lore <player_name> <line#> <text>");
                    plugin.log("   /ils give <player_name> <item_name>");
                    plugin.log("   /ils give <player_name> <item_name>, <new_item_name>");
                    plugin.log("   /ils custom tool <Item Type Name>");
                    plugin.log("   /ils custom armour helmet/chest/legs/boots <Item Type Name>");
                    plugin.log("   /ils repair");
                    plugin.log("   /ils upgrade hand");
                    plugin.log("   /ils upgrade armour");
                    plugin.log("   /ils upgrade all");
                    plugin.log("   /ils combatLog");
                    plugin.log("   /ils sell");
                    plugin.log("   /ils export <item_name>");
                    plugin.log("   /ils setMultiplier");
                    plugin.log("   /ils remake <lvl>");
                }
            }

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("name")) {
                    name_Com.onNameCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("give")) {
                    give_Com.onGiveCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("export")) {
                    export_Com.onExportCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("lore")) {
                    lore_Com.onLoreCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("repair")) {
                    repair_Com.onRepairCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("custom")) {
                    customMaterial_Com.onCustomMaterialCommand(sender, args);
                }

                if ((args[0].equalsIgnoreCase("test"))
                        && ((sender instanceof Player))) {
                    Player player = (Player) sender;

                    player.hasPermission("ils.admin");
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;

                        if (!player.hasPermission("ils.admin")) {
                            player.sendMessage(plugin.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else {
                            plugin.reloadConfig();
                            player.sendMessage(plugin.rep("&8[&cLoncoLoreItems&8] &7" + ChatColor.GREEN + " Configuration Reloaded!"));
                        }
                        return true;
                    }
                    plugin.reloadConfig();
                    plugin.log(plugin.rep("&8[&cLoncoLoreItems&8] &7Configuration Reloaded!"));
                }

                if (args[0].equalsIgnoreCase("createlore")) {
                    createLore_Com.onCreateLoreCommand(sender, args);
                } else if (args[0].equalsIgnoreCase("setMultiplier")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            config.set("npcModifier." + player.getWorld().getName() + ".healthMultiplier", 0.045D);
                            config.set("npcModifier." + player.getWorld().getName() + ".damageMultiplier", 0.004D);
                            config.set("npcModifier." + player.getWorld().getName() + ".expMultiplier", 0.004D);
                            config.set("npcModifier." + player.getWorld().getName() + ".location.x", player.getLocation().getBlockX());
                            config.set("npcModifier." + player.getWorld().getName() + ".location.y", player.getLocation().getBlockY());
                            config.set("npcModifier." + player.getWorld().getName() + ".location.z", player.getLocation().getBlockZ());
                            plugin.saveConfig();
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set the NPC multiplier to multiply health and damage from " + ChatColor.GOLD + player.getLocation().getBlockX() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player.getLocation().getBlockY() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player.getLocation().getBlockZ() + ChatColor.LIGHT_PURPLE + ".");
                        } else {
                            player.sendMessage(plugin.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        }
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("stats")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        CharacterSheet characterSheet = new CharacterSheet();

                        characterSheet.returnStats(player, plugin.getHealthValue(player));
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("combatlog")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;

                        if ((config.getBoolean("combatMessages.outgoing.damageDone")) || (config.getBoolean("combatMessages.incoming.damageTaken"))) {
                            try {
                                plugin.PlayerDataConfig = new YamlConfiguration();
                                plugin.PlayerDataConfig.load(new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                                if (plugin.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    plugin.PlayerDataConfig.set("extra.combatLogVisible", false);
                                    plugin.combatLogVisible.put(player.getName(), false);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (!plugin.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    plugin.PlayerDataConfig.set("extra.combatLogVisible", true);
                                    plugin.combatLogVisible.put(player.getName(), true);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.GREEN + "enabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (plugin.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                                    plugin.PlayerDataConfig.set("extra.combatLogVisible", false);
                                    plugin.combatLogVisible.put(player.getName(), false);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                }

                                plugin.PlayerDataConfig.save(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                            } catch (IOException | InvalidConfigurationException e) {
                                plugin.log("*********** Failed to load player data for " + player.getName() + " when toggling combat log! ***********");
                            }
                        }
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("sell")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        plugin.util_Vault.removeMoneyForSale(player, plugin.itemInMainHand(player).getAmount());
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("health")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.RED + "[DEBUGGER] " + ChatColor.WHITE + player.getHealth() + " out of " + player.getMaxHealth());
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("speed")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.GOLD + "Your movement speed is " + ChatColor.WHITE + player.getWalkSpeed());
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.command.upgrade"))) {
                            if (args.length >= 2) {
                                if ((args[1].equalsIgnoreCase("armour")) || (args[1].equalsIgnoreCase("armor"))) {
                                    plugin.itemUpgrade.increaseItemStatOnHelmet(player);
                                    plugin.itemUpgrade.increaseItemStatOnChestplate(player);
                                    plugin.itemUpgrade.increaseItemStatOnLeggings(player);
                                    plugin.itemUpgrade.increaseItemStatOnBoots(player);
                                } else if (args[1].equalsIgnoreCase("hand")) {
                                    if (player.getInventory().getItemInMainHand() != null) {
                                        plugin.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInMainHand(), "Main");
                                    }

                                    if (player.getInventory().getItemInOffHand() != null) {
                                        plugin.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInOffHand(), "Off");
                                    }
                                } else if (args[1].equalsIgnoreCase("all")) {
                                    plugin.itemUpgrade.increaseItemStatOnHelmet(player);
                                    plugin.itemUpgrade.increaseItemStatOnChestplate(player);
                                    plugin.itemUpgrade.increaseItemStatOnLeggings(player);
                                    plugin.itemUpgrade.increaseItemStatOnBoots(player);

                                    if (player.getInventory().getItemInMainHand() != null) {
                                        plugin.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInMainHand(), "Main");
                                    }

                                    if (player.getInventory().getItemInOffHand() != null) {
                                        plugin.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInOffHand(), "Off");
                                    }
                                } else {
                                    player.sendMessage(plugin.util_GetResponse.getResponse("ErrorMessages.EnterTypeError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(plugin.util_GetResponse.getResponse("ErrorMessages.EnterTypeError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(plugin.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        }
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("zombie")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                            mob.setCustomName("ItemLoreStats Test Dummy");
                            mob.setMaxHealth(2048.0D);
                            mob.setHealth(2048.0D);
                            if (plugin.itemInMainHand(player) != null) {
                                mob.getEquipment().setItemInMainHand(plugin.itemInMainHand(player).clone());
                            }
                            if (plugin.itemInOffHand(player) != null) {
                                mob.getEquipment().setItemInOffHand(plugin.itemInOffHand(player).clone());
                            }
                            if (player.getInventory().getHelmet() != null) {
                                mob.getEquipment().setHelmet(player.getInventory().getHelmet().clone());
                            }
                            if (player.getInventory().getChestplate() != null) {
                                mob.getEquipment().setChestplate(player.getInventory().getChestplate().clone());
                            }
                            if (player.getInventory().getLeggings() != null) {
                                mob.getEquipment().setLeggings(player.getInventory().getLeggings().clone());
                            }
                            if (player.getInventory().getBoots() != null) {
                                mob.getEquipment().setBoots(player.getInventory().getBoots().clone());
                            }
                            mob.setCustomNameVisible(true);
                        }
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("skeleton")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
                            mob.setCustomName("ItemLoreStats Test Dummy");
                            mob.setMaxHealth(2048.0D);
                            mob.setHealth(2048.0D);
                            if (plugin.itemInMainHand(player) != null) {
                                mob.getEquipment().setItemInMainHand(plugin.itemInMainHand(player).clone());
                            }
                            if (plugin.itemInOffHand(player) != null) {
                                mob.getEquipment().setItemInOffHand(plugin.itemInOffHand(player).clone());
                            }
                            if (player.getInventory().getHelmet() != null) {
                                mob.getEquipment().setHelmet(player.getInventory().getHelmet().clone());
                            }
                            if (player.getInventory().getChestplate() != null) {
                                mob.getEquipment().setChestplate(player.getInventory().getChestplate().clone());
                            }
                            if (player.getInventory().getLeggings() != null) {
                                mob.getEquipment().setLeggings(player.getInventory().getLeggings().clone());
                            }
                            if (player.getInventory().getBoots() != null) {
                                mob.getEquipment().setBoots(player.getInventory().getBoots().clone());
                            }
                            mob.setCustomNameVisible(true);
                        }
                    } else {
                        plugin.log("[ILS]" + plugin.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("test")) {
                    Player player = (Player) sender;

                    plugin.log("" + player.getInventory().getItemInMainHand().getData());
                    plugin.log("" + player.getInventory().getItemInMainHand().getDurability());
                }
            }
        }
        return true;
    }
}
