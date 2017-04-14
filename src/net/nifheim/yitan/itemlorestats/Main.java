package net.nifheim.yitan.itemlorestats;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import net.nifheim.yitan.loncoloreitems.DamageFix;
import net.nifheim.yitan.loncoloreitems.EventListener;
import net.nifheim.yitan.loncoloreitems.MVdWPlaceholderAPIHook;

import net.nifheim.yitan.itemlorestats.Commands.CreateLore_Com;
import net.nifheim.yitan.itemlorestats.Commands.CustomMaterial_Com;
import net.nifheim.yitan.itemlorestats.Commands.Export_Com;
import net.nifheim.yitan.itemlorestats.Commands.Give_Com;
import net.nifheim.yitan.itemlorestats.Commands.Lore_Com;
import net.nifheim.yitan.itemlorestats.Commands.Name_Com;
import net.nifheim.yitan.itemlorestats.Commands.Repair_Com;

import net.nifheim.yitan.itemlorestats.Damage.DamageSystem;
import net.nifheim.yitan.itemlorestats.Damage.EnvironmentalDamage;
import net.nifheim.yitan.itemlorestats.Damage.PotionListener;

import net.nifheim.yitan.itemlorestats.Durability.Durability;

import net.nifheim.yitan.itemlorestats.Interact.InteractEvents;

import net.nifheim.yitan.itemlorestats.ItemUpgrading.ItemUpgrade;
import net.nifheim.yitan.itemlorestats.ItemUpgrading.PlayerLevelEvents;

import net.nifheim.yitan.itemlorestats.Misc.SpigotStatCapWarning;
import net.nifheim.yitan.itemlorestats.Misc.WriteDefaultFiles;

import net.nifheim.yitan.itemlorestats.Util.InvSlot.GetSlots;
import net.nifheim.yitan.itemlorestats.Util.Util_ArmourWeight;
import net.nifheim.yitan.itemlorestats.Util.Util_Citizens;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_Random;
import net.nifheim.yitan.itemlorestats.Util.Util_Vault;
import net.nifheim.yitan.itemlorestats.Util.Util_WorldGuard;

import net.citizensnpcs.Citizens;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.zettelnet.armorweight.ArmorWeightPlugin;
import net.milkbowl.vault.Vault;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.nifheim.yitan.itemlorestats.listeners.CreatureSpawnListener;
import net.nifheim.yitan.itemlorestats.listeners.EnchantItemListener;
import net.nifheim.yitan.itemlorestats.listeners.EntityRegainHealthListener;
import net.nifheim.yitan.itemlorestats.listeners.GamemodeChangeListener;
import net.nifheim.yitan.itemlorestats.listeners.InventoryClickListener;
import net.nifheim.yitan.itemlorestats.listeners.InventoryDragListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerChangeWorldListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerDeathListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerDropItemListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerExpChangeListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerItemHeldListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerJoinListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerPickupItemListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerQuitListener;
import net.nifheim.yitan.itemlorestats.listeners.PlayerRespawnListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends org.bukkit.plugin.java.JavaPlugin {

    public static Main plugin;

    private FileConfiguration config;
    public final ConsoleCommandSender console = Bukkit.getConsoleSender();
    public String rep;

    public FileConfiguration PlayerDataConfig;

    public Util_Citizens util_Citizens;
    public Util_Vault util_Vault;
    public Util_WorldGuard util_WorldGuard;
    public HashMap<String, Long> spellCooldowns = new HashMap();
    public HashMap<String, Long> internalCooldowns = new HashMap();
    public HashMap<String, Boolean> combatLogVisible = new HashMap();
    public HashMap<String, Double> setBonusesModifiers = new HashMap();

    public DamageFix damagefix;
    public EventListener eventlistener;

    public Classes classes = new Classes();
    DamageSystem damageSystem = new DamageSystem(this);
    public Durability durability = new Durability();
    EnvironmentalDamage environmentalDamage = new EnvironmentalDamage();
    public GearStats gearStats = new GearStats();
    public GetSlots getSlots = new GetSlots();
    ItemUpgrade itemUpgrade = new ItemUpgrade();
    public SetBonuses setBonuses = new SetBonuses();
    public Soulbound soulbound = new Soulbound();
    WriteDefaultFiles writeDefaultFiles = new WriteDefaultFiles();
    public XpLevel xpLevel = new XpLevel();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    public Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();
    Util_ArmourWeight util_ArmourWeight = new Util_ArmourWeight(plugin);

    SpigotStatCapWarning spigotStatCapWarning = new SpigotStatCapWarning();

    CreateLore_Com createLore_Com = new CreateLore_Com();
    CustomMaterial_Com customMaterial_Com = new CustomMaterial_Com();
    Export_Com export_Com = new Export_Com();
    Give_Com give_Com = new Give_Com();
    Lore_Com lore_Com = new Lore_Com();
    Name_Com name_Com = new Name_Com();
    Repair_Com repair_Com = new Repair_Com();

    @Override
    public void onEnable() {
        checkDependencies();

        Locale.setDefault(Locale.ROOT);

        PluginManager plma = getServer().getPluginManager();
        /*
        New events clases
         */
        plma.registerEvents(new CreatureSpawnListener(), this);
        plma.registerEvents(new EnchantItemListener(), this);
        plma.registerEvents(new EntityRegainHealthListener(), this);
        plma.registerEvents(new GamemodeChangeListener(), this);
        plma.registerEvents(new InventoryClickListener(), this);
        plma.registerEvents(new InventoryDragListener(), this);
        plma.registerEvents(new PlayerChangeWorldListener(), this);
        plma.registerEvents(new PlayerDeathListener(), this);
        plma.registerEvents(new PlayerDropItemListener(), this);
        plma.registerEvents(new PlayerExpChangeListener(), this);
        plma.registerEvents(new PlayerItemHeldListener(), this);
        plma.registerEvents(new PlayerJoinListener(), this);
        plma.registerEvents(new PlayerPickupItemListener(), this);
        plma.registerEvents(new PlayerQuitListener(), this);
        plma.registerEvents(new PlayerRespawnListener(), this);
        /*
        End new event clases
         */
        plma.registerEvents(new net.nifheim.yitan.itemlorestats.Crafting.AddedStats(), this);
        plma.registerEvents(new DamageSystem(this), this);
        plma.registerEvents(new net.nifheim.yitan.itemlorestats.Durability.DurabilityEvents(), this);
        plma.registerEvents(new EnvironmentalDamage(), this);
        plma.registerEvents(new EntityDrops(), this);
        plma.registerEvents(new PotionListener(), this);
        plma.registerEvents(new InteractEvents(), this);
        plma.registerEvents(new PlayerLevelEvents(), this);
        plma.registerEvents(new net.nifheim.yitan.itemlorestats.Repair.RepairEvents(), this);

        plugin = this;

        writeDefaultFiles.checkExistence();

        getConfig().options().copyDefaults(true);
        getConfig().set("fileVersion", Integer.parseInt(getDescription().getVersion().replace(".", "")));
        saveConfig();

        eventlistener = new EventListener(this);
        damagefix = new DamageFix(this);
        plma.registerEvents(eventlistener, this);

        this.spigotStatCapWarning.updateSpigotValues();

    }

    @Override
    public void onDisable() {
        console.sendMessage(String.format("[%s] Disabled Version %s", new Object[]{
            getDescription().getName(), getDescription().getVersion()
        }));
    }

    public static Main getPlugin() {
        return plugin;
    }

    public void checkDependencies() {
        if (Bukkit.getServer().getPluginManager().getPlugin("ActionBarAPI") != null) {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Succesfully found and hooked into ActionBarAPI."));
        } else {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Unable to find ActionBarAPI, you need this API to run this plugin ..."));
            console.sendMessage(rep("                 &7You can download this in &chttps://www.spigotmc.org/resources/1315/"));
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
        if (getWorldGuard() != null) {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Successfully found and hooked into WorldGuard."));
        } else {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Unable to find WorldGuard!"));
        }
        if (getVault() != null) {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Successfully found and hooked into Vault."));
        } else {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Unable to find Vault!"));
        }
        if (getCitizens() != null) {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Successfully found and hooked into Citizens."));
        } else {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Unable to find Citizens!"));
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI") != null) {
            MVdWPlaceholderAPIHook.hook(this);
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Succesfully found and hooked into MVdWPlaceholderAPI."));
        } else {
            console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Unable to find MVdWPlaceholderAPI."));
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    public ArmorWeightPlugin getArmourWeight() {
        Plugin ArmorWeightPlugin = Bukkit.getServer().getPluginManager().getPlugin("ArmorWeight");

        if ((ArmorWeightPlugin == null) || (!(ArmorWeightPlugin instanceof ArmorWeightPlugin))) {
            return null;
        }

        this.util_ArmourWeight = new Util_ArmourWeight(plugin);
        return (ArmorWeightPlugin) ArmorWeightPlugin;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin WorldGuard = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if ((WorldGuard == null) || (!(WorldGuard instanceof WorldGuardPlugin))) {
            return null;
        }

        this.util_WorldGuard = new Util_WorldGuard(plugin);
        return (WorldGuardPlugin) WorldGuard;
    }

    public Vault getVault() {
        Plugin Vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");

        if ((Vault == null) || (!(Vault instanceof Vault))) {
            return null;
        }

        this.util_Vault = new Util_Vault(plugin);
        return (Vault) Vault;
    }

    public Citizens getCitizens() {
        Plugin Citizens = Bukkit.getServer().getPluginManager().getPlugin("Citizens");

        if ((Citizens == null) || (!(Citizens instanceof Citizens))) {
            return null;
        }

        this.util_Citizens = new Util_Citizens(plugin);
        return (Citizens) Citizens;
    }

    public ItemStack itemInMainHand(LivingEntity entity) {
        ItemStack item = null;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getItemInMainHand() != null) {
                    item = entity.getEquipment().getItemInMainHand();
                }
            }
        }

        return item;
    }

    public ItemStack itemInOffHand(LivingEntity entity) {
        ItemStack item = null;
        if (entity != null) {
            if (entity.getEquipment() != null) {
                if (entity.getEquipment().getItemInOffHand() != null) {
                    item = entity.getEquipment().getItemInOffHand();
                }
            }
        }

        return item;
    }

    public boolean isTool(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.tools").size(); i++) {
            if (plugin.getConfig().getList("materials.tools").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPotion(int potionID) {
        for (int i = 0; i < plugin.getConfig().getList("materials.potions").size(); i++) {
            if (Integer.parseInt(plugin.getConfig().getList("materials.potions").get(i).toString().split(":")[0]) == potionID) {
                return true;
            }
        }
        return false;
    }

    public boolean isArmour(Material material) {
        List<String> helmetList = (List<String>) plugin.getConfig().getList("materials.armour.helmet");
        List<String> chestList = (List<String>) plugin.getConfig().getList("materials.armour.chest");
        List<String> legsList = (List<String>) plugin.getConfig().getList("materials.armour.legs");
        List<String> bootsList = (List<String>) plugin.getConfig().getList("materials.armour.boots");
        List<String> armourList = new java.util.ArrayList();

        armourList.addAll(helmetList);
        armourList.addAll(chestList);
        armourList.addAll(legsList);
        armourList.addAll(bootsList);

        for (int i = 0; i < armourList.size(); i++) {
            if (((String) armourList.get(i)).split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isHelmet(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.helmet").size(); i++) {
            if (plugin.getConfig().getList("materials.armour.helmet").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isChestplate(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.chest").size(); i++) {
            if (plugin.getConfig().getList("materials.armour.chest").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isLeggings(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.legs").size(); i++) {
            if (plugin.getConfig().getList("materials.armour.legs").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoots(Material material) {
        for (int i = 0; i < plugin.getConfig().getList("materials.armour.boots").size(); i++) {
            if (plugin.getConfig().getList("materials.armour.boots").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isSword(ItemStack item) {
        if ((item.equals(Material.WOOD_SWORD)) || (item.equals(Material.STONE_SWORD)) || (item.equals(Material.IRON_SWORD)) || (item.equals(Material.GOLD_SWORD)) || (item.equals(Material.DIAMOND_SWORD))) {
            return true;
        }

        return false;
    }

    public boolean isHoe(ItemStack item) {
        return (item.equals(Material.WOOD_HOE)) || (item.equals(Material.STONE_HOE)) || (item.equals(Material.IRON_HOE)) || (item.equals(Material.GOLD_HOE)) || (item.equals(Material.DIAMOND_HOE));
    }

    public boolean isAxe(ItemStack item) {
        return (item.equals(Material.WOOD_AXE)) || (item.equals(Material.STONE_AXE)) || (item.equals(Material.IRON_AXE)) || (item.equals(Material.GOLD_AXE)) || (item.equals(Material.DIAMOND_AXE));
    }

    public boolean isPickAxe(ItemStack item) {
        return (item.equals(Material.WOOD_PICKAXE)) || (item.equals(Material.STONE_PICKAXE)) || (item.equals(Material.IRON_PICKAXE)) || (item.equals(Material.GOLD_PICKAXE)) || (item.equals(Material.DIAMOND_PICKAXE));
    }

    public boolean isSpade(ItemStack item) {
        return (item.equals(Material.WOOD_SPADE)) || (item.equals(Material.STONE_SPADE)) || (item.equals(Material.IRON_SPADE)) || (item.equals(Material.GOLD_SPADE)) || (item.equals(Material.DIAMOND_SPADE));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
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
                        sender.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                    }
                } else {
                    console.sendMessage("Item Lore Stats commands:");
                    console.sendMessage("   /ils");
                    console.sendMessage("   /ils reload");
                    console.sendMessage("   /ils stats");
                    console.sendMessage("   /ils version");
                    console.sendMessage("   /ils name <text>");
                    console.sendMessage("   /ils lore <player_name> <line#> <text>");
                    console.sendMessage("   /ils give <player_name> <item_name>");
                    console.sendMessage("   /ils give <player_name> <item_name>, <new_item_name>");
                    console.sendMessage("   /ils custom tool <Item Type Name>");
                    console.sendMessage("   /ils custom armour helmet/chest/legs/boots <Item Type Name>");
                    console.sendMessage("   /ils repair");
                    console.sendMessage("   /ils upgrade hand");
                    console.sendMessage("   /ils upgrade armour");
                    console.sendMessage("   /ils upgrade all");
                    console.sendMessage("   /ils combatLog");
                    console.sendMessage("   /ils sell");
                    console.sendMessage("   /ils export <item_name>");
                    console.sendMessage("   /ils setMultiplier");
                    console.sendMessage("   /ils remake <lvl>");
                }
            }

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("name")) {
                    this.name_Com.onNameCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("give")) {
                    this.give_Com.onGiveCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("export")) {
                    this.export_Com.onExportCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("lore")) {
                    this.lore_Com.onLoreCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("repair")) {
                    this.repair_Com.onRepairCommand(sender, args);
                }

                if (args[0].equalsIgnoreCase("custom")) {
                    this.customMaterial_Com.onCustomMaterialCommand(sender, args);
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
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        } else {
                            reloadConfig();
                            player.sendMessage(rep("&8[&cLoncoLoreItems&8] &7" + ChatColor.GREEN + " Configuration Reloaded!"));
                        }
                        return true;
                    }
                    reloadConfig();
                    console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7Configuration Reloaded!"));
                }

                if (args[0].equalsIgnoreCase("createlore")) {
                    this.createLore_Com.onCreateLoreCommand(sender, args);
                } else if (args[0].equalsIgnoreCase("setMultiplier")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".healthMultiplier", 0.045D);
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".damageMultiplier", 0.004D);
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".expMultiplier", 0.004D);
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".location.x", player.getLocation().getBlockX());
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".location.y", player.getLocation().getBlockY());
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".location.z", player.getLocation().getBlockZ());
                            saveConfig();
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set the NPC multiplier to multiply health and damage from " + ChatColor.GOLD + player.getLocation().getBlockX() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player.getLocation().getBlockY() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player.getLocation().getBlockZ() + ChatColor.LIGHT_PURPLE + ".");
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        }
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("stats")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        CharacterSheet characterSheet = new CharacterSheet();

                        characterSheet.returnStats(player, getHealthValue(player));
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("combatlog")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;

                        if ((getConfig().getBoolean("combatMessages.outgoing.damageDone")) || (getConfig().getBoolean("combatMessages.incoming.damageTaken"))) {
                            try {
                                this.PlayerDataConfig = new YamlConfiguration();
                                this.PlayerDataConfig.load(new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                                if (this.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", false);
                                    this.combatLogVisible.put(player.getName(), false);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (!this.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", true);
                                    this.combatLogVisible.put(player.getName(), true);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.GREEN + "enabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (this.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", false);
                                    this.combatLogVisible.put(player.getName(), false);
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                }

                                this.PlayerDataConfig.save(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                            } catch (IOException | InvalidConfigurationException e) {
                                console.sendMessage("*********** Failed to load player data for " + player.getName() + " when toggling combat log! ***********");
                            }
                        }
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("sell")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        this.util_Vault.removeMoneyForSale(player, itemInMainHand(player).getAmount());
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("health")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.RED + "[DEBUGGER] " + ChatColor.WHITE + player.getHealth() + " out of " + player.getMaxHealth());
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("speed")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.GOLD + "Your movement speed is " + ChatColor.WHITE + player.getWalkSpeed());
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("upgrade")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.command.upgrade"))) {
                            if (args.length >= 2) {
                                if ((args[1].equalsIgnoreCase("armour")) || (args[1].equalsIgnoreCase("armor"))) {
                                    this.itemUpgrade.increaseItemStatOnHelmet(player);
                                    this.itemUpgrade.increaseItemStatOnChestplate(player);
                                    this.itemUpgrade.increaseItemStatOnLeggings(player);
                                    this.itemUpgrade.increaseItemStatOnBoots(player);
                                } else if (args[1].equalsIgnoreCase("hand")) {
                                    if (player.getInventory().getItemInMainHand() != null) {
                                        this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInMainHand(), "Main");
                                    }

                                    if (player.getInventory().getItemInOffHand() != null) {
                                        this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInOffHand(), "Off");
                                    }
                                } else if (args[1].equalsIgnoreCase("all")) {
                                    this.itemUpgrade.increaseItemStatOnHelmet(player);
                                    this.itemUpgrade.increaseItemStatOnChestplate(player);
                                    this.itemUpgrade.increaseItemStatOnLeggings(player);
                                    this.itemUpgrade.increaseItemStatOnBoots(player);

                                    if (player.getInventory().getItemInMainHand() != null) {
                                        this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInMainHand(), "Main");
                                    }

                                    if (player.getInventory().getItemInOffHand() != null) {
                                        this.itemUpgrade.increaseItemStatOnItemInHand(player, player.getInventory().getItemInOffHand(), "Off");
                                    }
                                } else {
                                    player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.EnterTypeError", null, null, "", ""));
                                }
                            } else {
                                player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.EnterTypeError", null, null, "", ""));
                            }
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        }
                    } else {
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("zombie")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                            mob.setCustomName("ItemLoreStats Test Dummy");
                            mob.setMaxHealth(2048.0D);
                            mob.setHealth(2048.0D);
                            if (itemInMainHand(player) != null) {
                                mob.getEquipment().setItemInMainHand(itemInMainHand(player).clone());
                            }
                            if (itemInOffHand(player) != null) {
                                mob.getEquipment().setItemInOffHand(itemInOffHand(player).clone());
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
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("skeleton")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
                            mob.setCustomName("ItemLoreStats Test Dummy");
                            mob.setMaxHealth(2048.0D);
                            mob.setHealth(2048.0D);
                            if (itemInMainHand(player) != null) {
                                mob.getEquipment().setItemInMainHand(itemInMainHand(player).clone());
                            }
                            if (itemInOffHand(player) != null) {
                                mob.getEquipment().setItemInOffHand(itemInOffHand(player).clone());
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
                        console.sendMessage("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("test")) {
                    Player player = (Player) sender;

                    console.sendMessage("" + player.getInventory().getItemInMainHand().getData());
                    console.sendMessage("" + player.getInventory().getItemInMainHand().getDurability());
                }
            }
        }
        eventlistener.onCommand(sender, cmd, commandLabel, args);
        return false;
    }

    public void swapItems(int slotA, int slotB, Inventory inv) {
        ItemStack itemStackA = inv.getItem(slotA);
        ItemStack itemStackB = inv.getItem(slotB);
        inv.setItem(slotA, itemStackB);
        inv.setItem(slotB, itemStackA);
    }

    public double getHealthValue(Player player) {
        if (plugin.getConfig().getInt("baseHealth") == 0) {
            return 0.0D;
        }

        double healthBoost = 0.0D;
        double newHP = 0.0D;

        if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                    healthBoost = potionEffect.getAmplifier() * 4.0D;
                }
            }
        }

        if (!getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {

            double maxHealth = getConfig().getDouble("baseHealth") + getConfig().getDouble("additionalStatsPerLevel.health") * player.getLevel() + healthBoost;
            newHP = maxHealth;

            if (isTool(itemInMainHand(player).getType())) {
                newHP = (int) newHP + Double.valueOf(this.gearStats.getHealthItemInHand(itemInMainHand(player))).intValue();
            }

            if (isTool(itemInOffHand(player).getType())) {
                newHP = (int) newHP + Double.valueOf(this.gearStats.getHealthItemInHand(itemInOffHand(player))).intValue();
            }

            newHP = (int) newHP + Double.valueOf(this.gearStats.getHealthGear(player)).intValue();

        }

        return newHP;
    }

    public void updateHealth(Player player) {
        if ((!getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()))
                && (!player.hasMetadata("NPC"))) {
            if (plugin.getConfig().getInt("baseHealth") == 0) {
                return;
            }
            double healthBoost = 0.0D;
            double newHP;

            if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                    if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                        healthBoost = potionEffect.getAmplifier() * 4.0D;
                    }
                }
            }

            double maxHealth = getConfig().getDouble("baseHealth") + getConfig().getDouble("additionalStatsPerLevel.health") * player.getLevel() + healthBoost;
            newHP = maxHealth;

            if (isTool(itemInMainHand(player).getType())) {
                newHP = (int) newHP + Double.valueOf(this.gearStats.getHealthItemInHand(itemInMainHand(player))).intValue();
            }

            if (isTool(itemInOffHand(player).getType())) {
                newHP = (int) newHP + Double.valueOf(this.gearStats.getHealthItemInHand(itemInOffHand(player))).intValue();
            }

            newHP = (int) newHP + Double.valueOf(this.gearStats.getHealthGear(player)).intValue();

            player.setMaxHealth(newHP);

            if (getConfig().getInt("healthScale") > 0) {
                player.setHealthScale(getConfig().getDouble("healthScale"));
            }
        }
    }

    public void updatePlayerSpeed(Player player) {
        if (!getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            final Player playerFinal = player;

            getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                float maxSpeed = 0.4F;
                float speed = 0.2f;
                float basespeed = 0.2f;

                speed = (float) (Main.plugin.getConfig().getDouble("baseMovementSpeed"));
                speed = (float) (speed + (basespeed * Double.valueOf(playerFinal.getLevel()) * Main.this.getConfig().getDouble("additionalStatsPerLevel.speed")));

                speed = (float) (speed + (basespeed * Main.this.gearStats.getMovementSpeedGear(playerFinal) / 100));

                if (speed > maxSpeed) {

                    playerFinal.setWalkSpeed(maxSpeed);
                } else {
                    playerFinal.setWalkSpeed(speed);
                }
            }, 2L);
        } else {
            player.setWalkSpeed((float) plugin.getConfig().getDouble("baseMovementSpeed"));
        }
    }

    public String rep(String str) {
        return str.replaceAll("&", "§");
    }
}
