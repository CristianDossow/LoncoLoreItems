package net.nifheim.yitan.itemlorestats;

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
import net.nifheim.yitan.itemlorestats.Commands.Version_Com;

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
import net.nifheim.yitan.itemlorestats.Util.Util_Citizens;
import net.nifheim.yitan.itemlorestats.Util.Util_Colours;
import net.nifheim.yitan.itemlorestats.Util.Util_EntityManager;
import net.nifheim.yitan.itemlorestats.Util.Util_Format;
import net.nifheim.yitan.itemlorestats.Util.Util_GetResponse;
import net.nifheim.yitan.itemlorestats.Util.Util_Random;
import net.nifheim.yitan.itemlorestats.Util.Util_Vault;
import net.nifheim.yitan.itemlorestats.Util.Util_WorldGuard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.zettelnet.armorweight.ArmorWeightPlugin;
import net.milkbowl.vault.Vault;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.citizensnpcs.Citizens;
import net.nifheim.yitan.itemlorestats.Util.Util_ArmourWeight;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends org.bukkit.plugin.java.JavaPlugin {

    public static Main plugin;

    private FileConfiguration config;
    private ConsoleCommandSender console;

    private FileConfiguration PlayerDataConfig;

    public Util_Citizens util_Citizens;
    public Util_Vault util_Vault;
    public Util_WorldGuard util_WorldGuard;
    public HashMap<String, Long> spellCooldowns = new HashMap();
    public HashMap<String, Long> internalCooldowns = new HashMap();
    public HashMap<String, Boolean> combatLogVisible = new HashMap();
    public HashMap<String, Double> setBonusesModifiers = new HashMap();

    public DamageFix damagefix;
    public EventListener eventlistener;

    Classes classes = new Classes();
    DamageSystem damageSystem = new DamageSystem(this);
    Durability durability = new Durability();
    EnvironmentalDamage environmentalDamage = new EnvironmentalDamage();
    public GearStats gearStats = new GearStats();
    GetSlots getSlots = new GetSlots();
    ItemUpgrade itemUpgrade = new ItemUpgrade();
    public SetBonuses setBonuses = new SetBonuses();
    Soulbound soulbound = new Soulbound();
    WriteDefaultFiles writeDefaultFiles = new WriteDefaultFiles();
    XpLevel xpLevel = new XpLevel();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    Util_GetResponse util_GetResponse = new Util_GetResponse();
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
    Version_Com version_Com = new Version_Com();
    String rep;

    private int setMinecraftBuildNumber(String buildNum) {
        String version = buildNum;

        version = version.split("-")[0].replace(".", "");
        getConfig().set("serverVersion", Integer.valueOf(Integer.parseInt(version)));

        return Integer.parseInt(version);
    }

    public int getMinecraftBuildNumber(String buildNum) {
        String version = buildNum;

        version = version.split("-")[0].replace(".", "");

        return Integer.parseInt(version);
    }

    public int BukkitVersion() {
        return getConfig().getInt("serverVersion");
    }

    @Override
    public void onEnable() {
        checkDependencies();

        Locale.setDefault(Locale.ROOT);

        PluginManager plma = getServer().getPluginManager();
        /*
        Events
         */
        plma.registerEvents(new ItemLoreStatsListener(), this);
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
        setMinecraftBuildNumber(Bukkit.getBukkitVersion());
        getConfig().set("fileVersion", Integer.parseInt(getDescription().getVersion().replace(".", "")));
        saveConfig();

        eventlistener = new EventListener(this);
        damagefix = new DamageFix(this);
        plma.registerEvents(eventlistener, this);
        if (getServer().getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            MVdWPlaceholderAPIHook.hook(this);
            console.sendMessage(rep("&8[&cItemLoreStats&8] &7MVdWPlaceholderAPI found, hooking in this."));
        }

        this.spigotStatCapWarning.updateSpigotValues();

    }

    @Override
    public void onDisable() {
        console.sendMessage(String.format("[%s] Disabled Version %s", new Object[]{
            getDescription().getName(), getDescription().getVersion()
        }));
    }

    public static Main getPlugin() {
        return (Main) Bukkit.getPluginManager().getPlugin("ItemLoreStats");
    }

    public void checkDependencies() {
        if (getWorldGuard() != null) {
            console.sendMessage("[ItemLoreStats] Successfully found and hooked into WorldGuard.");
        } else {
            console.sendMessage("[ItemLoreStats] Unable to find WorldGuard!");
        }
        if (getVault() != null) {
            console.sendMessage("[ItemLoreStats] Successfully found and hooked into Vault.");
        } else {
            console.sendMessage("[ItemLoreStats] Unable to find Vault!");
        }
        if (getCitizens() != null) {
            console.sendMessage("[ItemLoreStats] Successfully found and hooked into Citizens.");
        } else {
            console.sendMessage("[ItemLoreStats] Unable to find Citizens!");
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
            if (((String) armourList.get(i)).toString().split(":")[0].equals(material.toString())) {
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
        if ((item.equals(Material.WOOD_HOE)) || (item.equals(Material.STONE_HOE)) || (item.equals(Material.IRON_HOE)) || (item.equals(Material.GOLD_HOE)) || (item.equals(Material.DIAMOND_HOE))) {
            return true;
        }

        return false;
    }

    public boolean isAxe(ItemStack item) {
        if ((item.equals(Material.WOOD_AXE)) || (item.equals(Material.STONE_AXE)) || (item.equals(Material.IRON_AXE)) || (item.equals(Material.GOLD_AXE)) || (item.equals(Material.DIAMOND_AXE))) {
            return true;
        }

        return false;
    }

    public boolean isPickAxe(ItemStack item) {
        if ((item.equals(Material.WOOD_PICKAXE)) || (item.equals(Material.STONE_PICKAXE)) || (item.equals(Material.IRON_PICKAXE)) || (item.equals(Material.GOLD_PICKAXE)) || (item.equals(Material.DIAMOND_PICKAXE))) {
            return true;
        }

        return false;
    }

    public boolean isSpade(ItemStack item) {
        if ((item.equals(Material.WOOD_SPADE)) || (item.equals(Material.STONE_SPADE)) || (item.equals(Material.IRON_SPADE)) || (item.equals(Material.GOLD_SPADE)) || (item.equals(Material.DIAMOND_SPADE))) {
            return true;
        }

        return false;
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
                    System.out.println("Item Lore Stats commands:");
                    System.out.println("   /ils");
                    System.out.println("   /ils reload");
                    System.out.println("   /ils stats");
                    System.out.println("   /ils version");
                    System.out.println("   /ils name <text>");
                    System.out.println("   /ils lore <player_name> <line#> <text>");
                    System.out.println("   /ils give <player_name> <item_name>");
                    System.out.println("   /ils give <player_name> <item_name>, <new_item_name>");
                    System.out.println("   /ils custom tool <Item Type Name>");
                    System.out.println("   /ils custom armour helmet/chest/legs/boots <Item Type Name>");
                    System.out.println("   /ils repair");
                    System.out.println("   /ils upgrade hand");
                    System.out.println("   /ils upgrade armour");
                    System.out.println("   /ils upgrade all");
                    System.out.println("   /ils combatLog");
                    System.out.println("   /ils sell");
                    System.out.println("   /ils export <item_name>");
                    System.out.println("   /ils setMultiplier");
                    System.out.println("   /ils remake <lvl>");
                }
            }

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("version")) {
                    this.version_Com.onVersionCommand(sender, args);
                }

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
                            player.sendMessage(ChatColor.GOLD + "[ItemLoreStats] " + ChatColor.GREEN + " Configuration Reloaded!");
                        }
                        return true;
                    }
                    reloadConfig();
                    System.out.println("[ItemLoreStats] Configuration Reloaded!");
                }

                if (args[0].equalsIgnoreCase("createlore")) {
                    this.createLore_Com.onCreateLoreCommand(sender, args);
                } else if (args[0].equalsIgnoreCase("setMultiplier")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        if ((player.isOp()) || (player.hasPermission("ils.admin"))) {
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".healthMultiplier", Double.valueOf(0.045D));
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".damageMultiplier", Double.valueOf(0.004D));
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".expMultiplier", Double.valueOf(0.004D));
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".location.x", Integer.valueOf(player.getLocation().getBlockX()));
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".location.y", Integer.valueOf(player.getLocation().getBlockY()));
                            getConfig().set("npcModifier." + player.getWorld().getName() + ".location.z", Integer.valueOf(player.getLocation().getBlockZ()));
                            saveConfig();
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Successfully set the NPC multiplier to multiply health and damage from " + ChatColor.GOLD + player.getLocation().getBlockX() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player.getLocation().getBlockY() + ChatColor.LIGHT_PURPLE + ", " + ChatColor.GOLD + player.getLocation().getBlockZ() + ChatColor.LIGHT_PURPLE + ".");
                        } else {
                            player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", null, null, "", ""));
                        }
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("stats")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        CharacterSheet characterSheet = new CharacterSheet();

                        characterSheet.returnStats(player, getHealthValue(player));
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("combatlog")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;

                        if ((getConfig().getBoolean("combatMessages.outgoing.damageDone")) || (getConfig().getBoolean("combatMessages.incoming.damageTaken"))) {
                            try {
                                this.PlayerDataConfig = new YamlConfiguration();
                                this.PlayerDataConfig.load(new File(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                                if (this.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", Boolean.valueOf(false));
                                    this.combatLogVisible.put(player.getName(), Boolean.valueOf(false));
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (!this.PlayerDataConfig.getBoolean("extra.combatLogVisible")) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", Boolean.valueOf(true));
                                    this.combatLogVisible.put(player.getName(), Boolean.valueOf(true));
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.GREEN + "enabled" + ChatColor.LIGHT_PURPLE + ".");
                                } else if (this.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                                    this.PlayerDataConfig.set("extra.combatLogVisible", Boolean.valueOf(false));
                                    this.combatLogVisible.put(player.getName(), Boolean.valueOf(false));
                                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Combat Log " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
                                }

                                this.PlayerDataConfig.save(plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("*********** Failed to load player data for " + player.getName() + " when toggling combat log! ***********");
                            }
                        }
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("sell")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        this.util_Vault.removeMoneyForSale(player, itemInMainHand(player).getAmount());
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("health")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.RED + "[DEBUGGER] " + ChatColor.WHITE + player.getHealth() + " out of " + player.getMaxHealth());
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("speed")) {
                    if ((sender instanceof Player)) {
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.GOLD + "Your movement speed is " + ChatColor.WHITE + player.getWalkSpeed());
                    } else {
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
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
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
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
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
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
                        System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", null, null, "", ""));
                    }
                } else if (args[0].equalsIgnoreCase("test")) {
                    Player player = (Player) sender;

                    System.out.println(player.getInventory().getItemInMainHand().getData());
                    System.out.println(player.getInventory().getItemInMainHand().getDurability());
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
            double newHP = 0.0D;

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

            getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    float maxSpeed = 0.4F;
                    float speed = 0.2f;
                    float basespeed = 0.2f;

                    speed = (float) (Main.plugin.getConfig().getDouble("baseMovementSpeed"));
                    speed = (float) (speed + (basespeed * Double.valueOf(playerFinal.getLevel()).doubleValue() * Main.this.getConfig().getDouble("additionalStatsPerLevel.speed")));

                    speed = (float) (speed + (basespeed * Main.this.gearStats.getMovementSpeedGear(playerFinal) / 100));

                    if (speed > maxSpeed) {

                        playerFinal.setWalkSpeed(maxSpeed);
                    } else {
                        playerFinal.setWalkSpeed(speed);
                    }

                }
            }, 2L);
        } else {
            player.setWalkSpeed((float) plugin.getConfig().getDouble("baseMovementSpeed"));
        }
    }

    public class ItemLoreStatsListener implements org.bukkit.event.Listener {

        public ItemLoreStatsListener() {
        }

        @EventHandler
        public void onRegenHealth(EntityRegainHealthEvent event) {
            if ((event.getEntity() instanceof Player)) {
                if (event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)) {
                    Player player = (Player) event.getEntity();
                    if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                        if (Main.plugin.getConfig().getDouble("baseHealthRegen") == 0.0D) {
                            return;
                        }
                        double gearRegen = 0.0D;
                        double modifier = 0.0D;

                        if (Main.this.isTool(Main.this.itemInMainHand(player).getType())) {
                            gearRegen += Main.this.gearStats.getHealthRegenItemInHand(Main.this.itemInMainHand(player));
                        }

                        if (Main.this.isTool(Main.this.itemInOffHand(player).getType())) {
                            gearRegen += Main.this.gearStats.getHealthRegenItemInHand(Main.this.itemInOffHand(player));
                        }

                        gearRegen += Main.this.gearStats.getHealthRegenGear(player);

                        double baseRegen = Main.this.getConfig().getDouble("baseHealthRegen");
                        double additionalLevelRegen = Main.this.getConfig().getDouble("additionalStatsPerLevel.healthRegen");
                        double modifiedHealthRegen = player.getMaxHealth() / 100.0D * (gearRegen + baseRegen + Double.valueOf(player.getLevel()).doubleValue() * additionalLevelRegen + modifier);

                        event.setAmount(modifiedHealthRegen);
                    }
                }

            }
        }

        @EventHandler
        public void expChangeEvent(PlayerExpChangeEvent event) {
            if (event.getPlayer().hasMetadata("NPC")) {
                return;
            }
            Player player = event.getPlayer();
            double bonusExp = 0.0D;
            double xpMultiplier = 0.0D;

            if ((Main.this.isTool(Main.this.itemInMainHand(player).getType()))
                    && (Main.this.gearStats.getXPMultiplierItemInHand(Main.this.itemInMainHand(player)) > 0.0D)) {
                xpMultiplier += Main.this.gearStats.getXPMultiplierItemInHand(Main.this.itemInMainHand(player));
            }

            if ((Main.this.isTool(Main.this.itemInOffHand(player).getType()))
                    && (Main.this.gearStats.getXPMultiplierItemInHand(Main.this.itemInOffHand(player)) > 0.0D)) {
                xpMultiplier += Main.this.gearStats.getXPMultiplierItemInHand(Main.this.itemInOffHand(player));
            }

            if (Main.this.gearStats.getXPMultiplierGear(player) > 0.0D) {
                xpMultiplier += Main.this.gearStats.getXPMultiplierGear(player);
            }

            bonusExp = event.getAmount() * xpMultiplier / 100.0D;
            player.giveExp((int) bonusExp);

        }

        @EventHandler
        public void enchantTableUse(EnchantItemEvent event) {
            if (Main.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                final Player playerFinal = event.getEnchanter();
                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Main.this.PlayerDataConfig = new YamlConfiguration();
                            Main.this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                            Main.this.PlayerDataConfig.set("extra.xp", Float.valueOf(playerFinal.getExp()));
                            Main.this.PlayerDataConfig.set("extra.level", Integer.valueOf(playerFinal.getLevel()));
                            Main.this.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when using the enchanting table! ***********");
                        }
                    }
                }, 4L);
            }
        }

        @EventHandler
        public void saveExpOnDeath(PlayerDeathEvent event) {
            if (event.getEntity().hasMetadata("NPC")) {
                return;
            }
            if (Main.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                Player player = event.getEntity();
                try {
                    Main.this.PlayerDataConfig = new YamlConfiguration();
                    Main.this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                    event.setDroppedExp(0);

                    int lvl = player.getLevel();
                    if (lvl == 0) {
                        player.setExp(0);
                    } else {
                        player.setExp(0);
                        player.setLevel(lvl - 1);
                    }

                    Main.this.PlayerDataConfig.set("extra.xp", Float.valueOf(player.getExp()));
                    Main.this.PlayerDataConfig.set("extra.level", Integer.valueOf(player.getLevel()));
                    Main.this.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("*********** Failed to save player data for " + player.getName() + " when dying! ***********");
                }
            }
        }

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player playerFinal = event.getPlayer();
            Main.this.getServer().getScheduler().scheduleAsyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (!new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                        try {
                            Main.this.PlayerDataConfig = new YamlConfiguration();

                            Main.this.updateHealth(playerFinal);

                            Main.this.PlayerDataConfig.set("extra.logoutHealth", Double.valueOf(20.0D));
                            Main.this.PlayerDataConfig.set("extra.maxHealth", Double.valueOf(20.0D));
                            Main.this.PlayerDataConfig.set("extra.hunger", Integer.valueOf(20));
                            Main.this.PlayerDataConfig.set("extra.xp", Float.valueOf(0.0F));
                            Main.this.PlayerDataConfig.set("extra.level", Integer.valueOf(0));
                            Main.this.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("*********** Failed to save player data for " + playerFinal.getName() + " when logging in! ***********");
                        }
                    } else if (new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml").exists()) {
                        try {
                            Main.this.PlayerDataConfig = new YamlConfiguration();
                            Main.this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                            playerFinal.setMaxHealth(Main.this.PlayerDataConfig.getDouble("extra.maxHealth"));
                            playerFinal.setHealth(Main.this.PlayerDataConfig.getDouble("extra.logoutHealth"));
                            playerFinal.setFoodLevel(Main.this.PlayerDataConfig.getInt("extra.hunger"));

                            if (Main.this.PlayerDataConfig.get("extra.combatLogVisible") == null) {
                                Main.this.combatLogVisible.put(playerFinal.getName(), Boolean.valueOf(true));
                            } else {
                                Main.this.combatLogVisible.put(playerFinal.getName(), Boolean.valueOf(Main.this.PlayerDataConfig.getBoolean("extra.combatLogVisible")));
                            }

                            if ((Main.plugin.getConfig().getBoolean("keepXPOnDeath"))) {
                                //playerFinal.setExp((float)ItemLoreStats.this.PlayerDataConfig.getDouble("extra.xp"));
                                //playerFinal.setLevel(ItemLoreStats.this.PlayerDataConfig.getInt("extra.level"));
                            }

                            Main.this.updateHealth(playerFinal);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when logging in! ***********");
                        }
                    }

                    Main.this.updateHealth(playerFinal);
                    Main.this.updatePlayerSpeed(playerFinal);

                }
            }, 5L);

            if (event.getPlayer().isOp()) {
                Main.this.getServer().getScheduler().runTaskLaterAsynchronously(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (Main.this.getMinecraftBuildNumber(Bukkit.getBukkitVersion()) >= 1100) {
                        }
                    }
                }, 60L);
            }
        }

        @EventHandler
        public void onPlayerRespawn(PlayerRespawnEvent event) {
            Player player = event.getPlayer();

            if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                final Player playerFinal = player;
                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    public void run() {
                        if (Main.plugin.getConfig().getBoolean("keepXPOnDeath")) {
                            try {
                                Main.this.PlayerDataConfig = new YamlConfiguration();
                                Main.this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + playerFinal.getName() + ".yml"));

                                playerFinal.setExp((float) Main.this.PlayerDataConfig.getDouble("extra.xp"));
                                playerFinal.setLevel(Main.this.PlayerDataConfig.getInt("extra.level"));
                                Main.this.combatLogVisible.put(playerFinal.getName(), Boolean.valueOf(Main.this.PlayerDataConfig.getBoolean("extra.combatLogVisible")));
                                Main.this.updateHealth(playerFinal);
                                Main.this.updatePlayerSpeed(playerFinal);
                                Main.this.setBonuses.updateSetBonus(playerFinal);
                                playerFinal.setHealth(playerFinal.getMaxHealth());
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("*********** Failed to load player data for " + playerFinal.getName() + " when respawning! ***********");
                            }
                        } else {
                            Main.this.updateHealth(playerFinal);
                            Main.this.updatePlayerSpeed(playerFinal);
                            Main.this.setBonuses.updateSetBonus(playerFinal);
                        }

                    }
                }, 3L);
            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            if ((event.getPlayer() instanceof Player)) {
                Player player = event.getPlayer();

                if (!new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                    if (!player.isDead()) {
                        player.setMaxHealth(20.0D);
                        if (player.getHealth() > 20.0D) {
                            player.setHealth(20.0D);
                        }
                    } else {
                        player.setMaxHealth(20.0D);
                    }
                } else if (new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists()) {
                    try {
                        Main.this.PlayerDataConfig = new YamlConfiguration();
                        Main.this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));

                        Main.this.PlayerDataConfig.set("extra.logoutHealth", Long.valueOf(Math.round(player.getHealth())));
                        Main.this.PlayerDataConfig.set("extra.maxHealth", Long.valueOf(Math.round(player.getMaxHealth())));
                        Main.this.PlayerDataConfig.set("extra.hunger", Integer.valueOf(player.getFoodLevel()));
                        Main.this.PlayerDataConfig.set("extra.xp", Float.valueOf(player.getExp()));
                        Main.this.PlayerDataConfig.set("extra.level", Integer.valueOf(player.getLevel()));
                        Main.this.PlayerDataConfig.set("extra.combatLogVisible", Main.this.combatLogVisible.get(player.getName()));
                        Main.this.PlayerDataConfig.save(Main.plugin.getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");

                        if (!player.isDead()) {
                            player.setMaxHealth(20.0D);
                            if (player.getHealth() > 20.0D) {
                                player.setHealth(20.0D);
                            }
                        } else {
                            player.setMaxHealth(20.0D);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("*********** Failed to save player data for " + player.getName() + " when logging out! ***********");
                    }
                }
            }
        }

        @EventHandler
        public void onDropItemEvent(PlayerDropItemEvent event) {
            Player player = event.getPlayer();
            if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
                final Player playerFinal = player;
                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    public void run() {
                        Main.this.updateHealth(playerFinal);
                        Main.this.updatePlayerSpeed(playerFinal);
                        Main.this.setBonuses.updateSetBonus(playerFinal);
                    }

                }, 2L);
            }
        }

        @EventHandler
        public void onPickupCustomItem(PlayerPickupItemEvent event) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem().getItemStack();

            if ((!Main.this.getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()))
                    && (Main.this.getConfig().getBoolean("messages.itemLooted"))
                    && (item != null)
                    && (item.getItemMeta() != null)
                    && (item.getItemMeta().hasDisplayName())
                    && (item.getItemMeta().getDisplayName().startsWith("ILS_"))) {
                ItemStack itemStack = item.clone();
                ItemMeta itemMeta = item.getItemMeta();

                itemMeta.setDisplayName(item.getItemMeta().getDisplayName().substring(4));
                item.setItemMeta(itemMeta);

                event.getItem().setItemStack(itemStack);
                player.sendMessage(Main.this.util_GetResponse.getResponse("Item.Looted", player, player, item.getItemMeta().getDisplayName(), item.getItemMeta().getDisplayName()));
            }
        }

        @EventHandler
        public void onGameModeChange(PlayerGameModeChangeEvent event) {
            final Player player = event.getPlayer();
            Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                public void run() {
                    Main.this.updateHealth(player);
                }
            }, 2L);
        }

        @EventHandler
        public void checkOnPickup(PlayerPickupItemEvent event) {
            if ((event.isCancelled()) || (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
                return;
            }
            if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
                Player player = event.getPlayer();
                ItemStack item = event.getItem().getItemStack().clone();

                if ((item != null)
                        && (item.getAmount() == 1)
                        && (Main.this.isTool(item.getType()))
                        && (item.hasItemMeta())
                        && (item.getItemMeta().getLore() != null)) {
                    if (player.getInventory().firstEmpty() == player.getInventory().getHeldItemSlot()) {
                        for (int slot = player.getInventory().getHeldItemSlot() + 1; slot < 35; slot++) {
                            if (player.getInventory().getItem(slot) == null) {
                                if ((Main.this.gearStats.getSoulboundName(player, item) != "")
                                        && (!Main.this.gearStats.getSoulboundName(player, item).equals(player.getName()))) {
                                    event.setCancelled(true);
                                    event.getItem().remove();
                                    event.getPlayer().getInventory().setItem(slot, item);

                                    break;
                                }

                                if ((Main.this.gearStats.getXPLevelRequirement(player, item) != 0)
                                        && (Main.this.gearStats.getXPLevelRequirement(player, item) > player.getLevel())) {
                                    event.setCancelled(true);
                                    event.getItem().remove();
                                    event.getPlayer().getInventory().setItem(slot, item);

                                    break;
                                }

                                if ((Main.this.gearStats.getClass(item) != null)
                                        && (!player.hasPermission("ils.use." + Main.this.gearStats.getClass(item)))) {
                                    event.setCancelled(true);
                                    event.getItem().remove();
                                    event.getPlayer().getInventory().setItem(slot, item);

                                    break;
                                }
                            }
                        }
                    }
                }

                Main.this.updateHealth(player);
                Main.this.updatePlayerSpeed(player);

                Main.this.setBonuses.updateSetBonus(player);
            }
        }

        @EventHandler
        public void onPlayerHeldItemChange(final PlayerItemHeldEvent event) {
            if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(event.getPlayer().getWorld().getName())) {
                final Player playerFinal = event.getPlayer();
                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    public void run() {
                        ItemStack checkItemHeld = playerFinal.getInventory().getItem(event.getNewSlot());

                        if ((checkItemHeld != null)
                                && (checkItemHeld.getType() != Material.AIR)
                                && (checkItemHeld.getItemMeta() != null)
                                && (checkItemHeld.getItemMeta().getLore() != null)
                                && (Main.this.isTool(checkItemHeld.getType()))) {
                            if ((Main.this.gearStats.phic_SoulboundNameItemInHand(checkItemHeld) != null)
                                    && (!Main.this.gearStats.phic_SoulboundNameItemInHand(checkItemHeld).equals(playerFinal.getName()))) {
                                Main.this.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                                playerFinal.sendMessage(Main.this.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, String.valueOf(Main.this.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(Main.this.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld))));

                                playerFinal.sendMessage(Main.this.util_GetResponse.getResponse("SoulboundMessages.BoundToSomeoneElseForItemInHand", playerFinal, playerFinal, Main.this.gearStats.phic_SoulboundNameItemInHand(checkItemHeld), Main.this.gearStats.phic_SoulboundNameItemInHand(checkItemHeld)));

                                return;
                            }

                            if ((Main.this.gearStats.phic_ClassItemInHand(checkItemHeld) != null)
                                    && (!playerFinal.hasPermission("ils.use." + Main.this.gearStats.phic_ClassItemInHand(checkItemHeld)))) {
                                Main.this.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                                playerFinal.sendMessage(Main.this.util_GetResponse.getResponse("ClassRequirementMessages.NotRequiredClassForItemInHand", playerFinal, playerFinal, String.valueOf(Main.this.gearStats.phic_ClassItemInHand(checkItemHeld)), String.valueOf(Main.this.gearStats.phic_ClassItemInHand(checkItemHeld))));

                                return;
                            }

                            if ((Main.this.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld) != 0)
                                    && (Main.this.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld) > playerFinal.getLevel())) {
                                Main.this.swapItems(event.getNewSlot(), event.getPreviousSlot(), playerFinal.getInventory());
                                playerFinal.sendMessage(Main.this.util_GetResponse.getResponse("LevelRequirementMessages.LevelTooLowForItemInHand", playerFinal, playerFinal, String.valueOf(Main.this.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld)), String.valueOf(Main.this.gearStats.phic_XPLevelRequirementItemInHand(checkItemHeld))));

                                return;
                            }
                        }

                        Main.this.updateHealth(playerFinal);
                        Main.this.updatePlayerSpeed(playerFinal);

                        Main.this.setBonuses.updateSetBonus(playerFinal);
                    }
                }, 2L);
            }
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {
            if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
                return;
            }
            if ((event.getInventory().getType().equals(InventoryType.CRAFTING))
                    || (event.getInventory().getType().equals(InventoryType.PLAYER))
                    || (event.getInventory().getType().equals(InventoryType.FURNACE))
                    || (event.getInventory().getType().equals(InventoryType.DROPPER))
                    || (event.getInventory().getType().equals(InventoryType.HOPPER))
                    || (event.getInventory().getType().equals(InventoryType.DISPENSER))
                    || (event.getInventory().getType().equals(InventoryType.CHEST))
                    || (event.getInventory().getType().equals(InventoryType.ENCHANTING))
                    || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) {
                Player player = (Player) event.getWhoClicked();

                if (event.getOldCursor() != null) {
                    ItemStack item = event.getOldCursor().clone();

                    if (!Main.this.getConfig().getBoolean("usingMcMMO")) {
                        Main.this.durability.syncArmourDurability(player);
                    }

                    if (Main.this.getSlots.isOffHandSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))
                            || ((Main.this.getSlots.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (Main.this.isArmour(item.getType()))) || ((Main.this.getSlots.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (player.getInventory().getHeldItemSlot() == Main.this.getSlots.getRawHeldItemSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) && (Main.this.isTool(item.getType())))) {
                        if (!Main.this.xpLevel.checkXPLevel(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();

                            return;
                        }

                        if (!Main.this.soulbound.checkSoulbound(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();

                            return;
                        }

                        if (!Main.this.classes.checkClasses(player, item)) {
                            event.setCancelled(true);
                            player.updateInventory();

                            return;
                        }
                    }
                }
            }
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
                return;
            }
            if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(event.getWhoClicked().getWorld().getName())) {

                if ((event.getInventory().getType().equals(InventoryType.CRAFTING))
                        || (event.getInventory().getType().equals(InventoryType.PLAYER))
                        || (event.getInventory().getType().equals(InventoryType.FURNACE))
                        || (event.getInventory().getType().equals(InventoryType.DROPPER))
                        || (event.getInventory().getType().equals(InventoryType.HOPPER))
                        || (event.getInventory().getType().equals(InventoryType.DISPENSER))
                        || (event.getInventory().getType().equals(InventoryType.CHEST))
                        || (event.getInventory().getType().equals(InventoryType.ENCHANTING))
                        || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) {
                    Player player = (Player) event.getWhoClicked();

                    if (event.getCurrentItem() != null) {
                        ItemStack item = event.getCursor().clone();

                        if (event.isShiftClick()) {
                            item = event.getCurrentItem().clone();
                        }
                        if (!Main.this.getConfig().getBoolean("usingMcMMO")) {
                            Main.this.durability.syncArmourDurability(player);
                        }

                        if ((event.getSlot() == 45) || (event.getRawSlot() == 45)
                                || ((event.getSlotType().equals(InventoryType.SlotType.ARMOR)) && (Main.this.isArmour(item.getType())))
                                || ((event.isShiftClick())) || ((event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) && (event.getSlot() == player.getInventory().getHeldItemSlot()) && (Main.this.isTool(item.getType())))) {
                            if (!Main.this.xpLevel.checkXPLevel(player, item)) {
                                event.setCancelled(true);
                                player.updateInventory();

                                return;
                            }

                            if (!Main.this.soulbound.checkSoulbound(player, item)) {
                                event.setCancelled(true);
                                player.updateInventory();

                                return;
                            }

                            if (!Main.this.classes.checkClasses(player, item)) {
                                event.setCancelled(true);
                                player.updateInventory();

                                return;
                            }
                        }
                    }
                }
            }
        }

        @EventHandler
        public void healthIncreaseOnDragEquip(InventoryDragEvent event) {
            if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
                return;
            }
            final Player player = (Player) event.getWhoClicked();

            if (((event.getInventory().getType().equals(InventoryType.CRAFTING))
                    || (event.getInventory().getType().equals(InventoryType.PLAYER))
                    || (event.getInventory().getType().equals(InventoryType.FURNACE))
                    || (event.getInventory().getType().equals(InventoryType.DROPPER))
                    || (event.getInventory().getType().equals(InventoryType.HOPPER))
                    || (event.getInventory().getType().equals(InventoryType.DISPENSER))
                    || (event.getInventory().getType().equals(InventoryType.CHEST))
                    || (event.getInventory().getType().equals(InventoryType.ENCHANTING))
                    || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) && ((Main.this.getSlots.isArmourSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))) || (Main.this.getSlots.isHotbarSlot(event.getRawSlots().toString().replaceAll("\\[|\\]", ""))))) {
                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    public void run() {
                        player.updateInventory();
                        Main.this.updateHealth(player);
                        Main.this.updatePlayerSpeed(player);
                    }

                }, 1L);
            }
        }

        @EventHandler
        public void healthIncreaseOnEquip(InventoryClickEvent event) {
            if ((event.isCancelled()) || (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
                return;
            }
            final Player player = (Player) event.getWhoClicked();

            if (((event.getInventory().getType().equals(InventoryType.CRAFTING))
                    || (event.getInventory().getType().equals(InventoryType.PLAYER))
                    || (event.getInventory().getType().equals(InventoryType.FURNACE))
                    || (event.getInventory().getType().equals(InventoryType.DROPPER))
                    || (event.getInventory().getType().equals(InventoryType.HOPPER))
                    || (event.getInventory().getType().equals(InventoryType.DISPENSER))
                    || (event.getInventory().getType().equals(InventoryType.CHEST))
                    || (event.getInventory().getType().equals(InventoryType.ENCHANTING))
                    || (event.getInventory().getType().equals(InventoryType.ENDER_CHEST))) && ((event.getSlotType().equals(InventoryType.SlotType.ARMOR)) || (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) || (event.isShiftClick()))) {
                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    public void run() {
                        player.updateInventory();
                        Main.this.updateHealth(player);
                        Main.this.updatePlayerSpeed(player);
                    }

                }, 1L);
            }
        }

        @EventHandler
        public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
            if ((event.getPlayer() instanceof Player)) {
                final Player playerFinal = event.getPlayer();

                Main.this.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Main.this.updateHealth(playerFinal);
                        Main.this.updatePlayerSpeed(playerFinal);
                        Main.this.setBonuses.updateSetBonus(playerFinal);

                    }
                }, 2L);

                if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(playerFinal.getWorld().getName())) {
                }
            }
        }

        @EventHandler
        public void modifyMobHealth(CreatureSpawnEvent event) {
            if (!Main.this.getConfig().getStringList("disabledInWorlds").contains(event.getEntity().getWorld().getName())) {
                LivingEntity entity = event.getEntity();
                Location entityLoc = entity.getLocation();

                if ((Main.plugin.getConfig().getBoolean("ILSLootFromNaturalSpawnsOnly"))
                        && (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL))) {
                    entity.setMetadata("naturalSpawn", new FixedMetadataValue(Main.plugin, Boolean.valueOf(true)));
                }

                /*if ((ItemLoreStats.this.util_WorldGuard != null) /*&& (ItemLoreStats.this.util_WorldGuard.entityInLevelRegion(entity))) {
                    String regionName = ItemLoreStats.this.util_WorldGuard.getRegionNameFromLocation(entityLoc);

                    int minLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[0]);
                    int maxLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[1]);
                    int mobLevel = ItemLoreStats.this.util_Random.randomRange(minLevelRange, maxLevelRange);

                    double additionalHealth = ItemLoreStats.this.gearStats.getHealthItemInHand(ItemLoreStats.this.itemInMainHand(entity)) + ItemLoreStats.this.gearStats.getHealthItemInHand(ItemLoreStats.this.itemInOffHand(entity)) + ItemLoreStats.this.gearStats.getHealthGear(entity);
                    double newHealth = entity.getMaxHealth() + mobLevel * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + regionName + ".healthMultiplier") + additionalHealth;

                    entity.setMetadata("level", new FixedMetadataValue(ItemLoreStats.plugin, Integer.valueOf(mobLevel)));
                    entity.setMetadata("regionSpawned", new FixedMetadataValue(ItemLoreStats.plugin, ItemLoreStats.this.util_WorldGuard.getRegionInsideName(entity)));

                    entity.setMaxHealth(Double.valueOf(newHealth).intValue());
                    entity.setHealth(Double.valueOf(newHealth).intValue());/*if ((ItemLoreStats.this.util_WorldGuard != null) /*&& (ItemLoreStats.this.util_WorldGuard.entityInLevelRegion(entity))) {
                    String regionName = ItemLoreStats.this.util_WorldGuard.getRegionNameFromLocation(entityLoc);

                    int minLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[0]);
                    int maxLevelRange = Integer.parseInt(regionName.split("_")[1].split("-")[1]);
                    int mobLevel = ItemLoreStats.this.util_Random.randomRange(minLevelRange, maxLevelRange);

                    double additionalHealth = ItemLoreStats.this.gearStats.getHealthItemInHand(ItemLoreStats.this.itemInMainHand(entity)) + ItemLoreStats.this.gearStats.getHealthItemInHand(ItemLoreStats.this.itemInOffHand(entity)) + ItemLoreStats.this.gearStats.getHealthGear(entity);
                    double newHealth = entity.getMaxHealth() + mobLevel * ItemLoreStats.plugin.getConfig().getDouble("npcModifier." + regionName + ".healthMultiplier") + additionalHealth;

                    entity.setMetadata("level", new FixedMetadataValue(ItemLoreStats.plugin, Integer.valueOf(mobLevel)));
                    entity.setMetadata("regionSpawned", new FixedMetadataValue(ItemLoreStats.plugin, ItemLoreStats.this.util_WorldGuard.getRegionInsideName(entity)));

                    entity.setMaxHealth(Double.valueOf(newHealth).intValue());
                    entity.setHealth(Double.valueOf(newHealth).intValue());
                } else*/ if (Main.this.getConfig().getString("npcModifier." + event.getEntity().getWorld().getName()) != null) {
                    String worldName = entity.getWorld().getName();

                    Location loc = new Location(entity.getWorld(), Main.this.getConfig().getInt("npcModifier." + worldName + ".location.x"), Main.this.getConfig().getInt("npcModifier." + worldName + ".location.y"), Main.this.getConfig().getInt("npcModifier." + worldName + ".location.z"));

                    int mobLevel = (int) Math.ceil(entity.getEyeLocation().distance(loc) / Main.this.getConfig().getDouble("npcModifier." + worldName + ".blocksPerLevel"));
                    double calcNewHealth = Math.ceil(mobLevel * Main.this.getConfig().getDouble("npcModifier." + worldName + ".healthMultiplier"));
                    double additionalHealth = Main.this.gearStats.getHealthItemInHand(Main.this.itemInMainHand(entity)) + Main.this.gearStats.getHealthItemInHand(Main.this.itemInOffHand(entity)) + Main.this.gearStats.getHealthGear(entity);
                    double newHealth = calcNewHealth + additionalHealth;

                    entity.setMetadata("level", new FixedMetadataValue(Main.plugin, Integer.valueOf(mobLevel)));

                    if (newHealth > 2000000.0D) {
                        newHealth = 2000000.0D;
                    }

                    entity.setMaxHealth(Double.valueOf(newHealth).intValue());
                    entity.setHealth(Double.valueOf(newHealth).intValue());
                }
            }
        }
    }

    public String rep(String str) {
        return str.replaceAll("&", "§");
    }
}
