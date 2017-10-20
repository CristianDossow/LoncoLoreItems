package net.nifheim.yitan.lorestats;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.citizensnpcs.Citizens;
import net.milkbowl.vault.Vault;
import net.nifheim.beelzebu.characters.Account;
import net.nifheim.beelzebu.commands.CharacterCommand;
import net.nifheim.beelzebu.commands.StatsCommand;
import net.nifheim.beelzebu.commands.TestCommand;
import net.nifheim.beelzebu.utils.ActionBarAPI;
import net.nifheim.beelzebu.utils.DataManager;
import net.nifheim.beelzebu.utils.PlaceholderAPI;
import net.nifheim.yitan.items.DamageFix;
import net.nifheim.yitan.items.EventListener;
import net.nifheim.yitan.lorestats.damage.DamageSystem;
import net.nifheim.yitan.lorestats.damage.EnvironmentalDamage;
import net.nifheim.yitan.lorestats.damage.PotionListener;
import net.nifheim.yitan.lorestats.durability.Durability;
import net.nifheim.yitan.lorestats.interact.InteractEvents;
import net.nifheim.yitan.lorestats.itemupgrading.ItemUpgrade;
import net.nifheim.yitan.lorestats.itemupgrading.PlayerLevelEvents;
import net.nifheim.yitan.lorestats.listeners.*;
import net.nifheim.yitan.lorestats.misc.SpigotStatCapWarning;
import net.nifheim.yitan.lorestats.misc.WriteDefaultFiles;
import net.nifheim.yitan.lorestats.repair.RepairEvents;
import net.nifheim.yitan.lorestats.utils.InvSlot.GetSlots;
import net.nifheim.yitan.lorestats.utils.Util_Citizens;
import net.nifheim.yitan.lorestats.utils.Util_Colours;
import net.nifheim.yitan.lorestats.utils.Util_EntityManager;
import net.nifheim.yitan.lorestats.utils.Util_Format;
import net.nifheim.yitan.lorestats.utils.Util_GetResponse;
import net.nifheim.yitan.lorestats.utils.Util_Random;
import net.nifheim.yitan.lorestats.utils.Util_Vault;
import net.nifheim.yitan.lorestats.utils.Util_WorldGuard;
import net.nifheim.yitan.magic.SpellListeners;
import net.nifheim.yitan.skills.SkillListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin {

    private static Main plugin;
    private DataManager dataManager;
    public Main instance;
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    public static String rep;
    private PlaceholderAPI placeholderAPI;
    private ActionBarAPI aba;
    // Files
    private final File messagesFile = new File(getDataFolder(), "messages.yml");
    public static FileConfiguration messages;
    public static int checkdb;

    public FileConfiguration PlayerDataConfig;

    public Util_Citizens util_Citizens;
    public Util_Vault util_Vault;
    public Util_WorldGuard util_WorldGuard;
    public HashMap<String, Long> spellCooldowns = new HashMap<>();
    public HashMap<String, Long> internalCooldowns = new HashMap<>();
    public HashMap<String, Boolean> combatLogVisible = new HashMap<>();
    public HashMap<String, Double> setBonusesModifiers = new HashMap<>();
    public HashMap<UUID, PlayerStats> playersStats = new HashMap<>();

    public DamageFix damagefix;
    public EventListener eventlistener;

    public Classes classes = new Classes();
    private final DamageSystem damageSystem = new DamageSystem(this);
    public Durability durability = new Durability();
    public EnvironmentalDamage environmentalDamage = new EnvironmentalDamage();
    public GearStats gearStats = new GearStats();
    public GetSlots getSlots = new GetSlots();
    public ItemUpgrade itemUpgrade = new ItemUpgrade();
    public SetBonuses setBonuses = new SetBonuses();
    public Soulbound soulbound = new Soulbound();
    WriteDefaultFiles writeDefaultFiles = new WriteDefaultFiles();
    public XpLevel xpLevel = new XpLevel();
    Util_Colours util_Colours = new Util_Colours();
    Util_EntityManager util_EntityManager = new Util_EntityManager();
    Util_Format util_Format = new Util_Format();
    public Util_GetResponse util_GetResponse = new Util_GetResponse();
    Util_Random util_Random = new Util_Random();

    SpigotStatCapWarning spigotStatCapWarning = new SpigotStatCapWarning();

    static public Scoreboard scoreboard;

    BukkitTask fastTasks;

    public static Main getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {

        loadManagers();
        Locale.setDefault(Locale.ROOT);

        plugin = this;
        instance = this;

        aba = new ActionBarAPI();
        aba.loadActionBar();

        PluginManager plma = getServer().getPluginManager();

        //New events clases
        plma.registerEvents(new SpellListeners(), this);
        plma.registerEvents(new SkillListener(), this);
        plma.registerEvents(new CreatureSpawnListener(), this);
        plma.registerEvents(new EnchantItemListener(), this);
        plma.registerEvents(new EntityRegainHealthListener(), this);
        plma.registerEvents(new EntityShotBowListener(), this);
        plma.registerEvents(new GamemodeChangeListener(), this);
        plma.registerEvents(new InventoryClickListener(), this);
        plma.registerEvents(new InventoryDragListener(), this);
        plma.registerEvents(new PlayerChangeWorldListener(), this);
        plma.registerEvents(new PlayerDeathListener(), this);
        plma.registerEvents(new PlayerDropItemListener(), this);
        plma.registerEvents(new PlayerExpChangeListener(), this);
        plma.registerEvents(new PlayerInteractEntityListener(), this);
        plma.registerEvents(new PlayerInteractListener(), this);
        plma.registerEvents(new PlayerItemHeldListener(), this);
        plma.registerEvents(new PlayerJoinListener(this), this);
        plma.registerEvents(new PlayerPickupItemListener(), this);
        plma.registerEvents(new PlayerQuitListener(this), this);
        plma.registerEvents(new PlayerRespawnListener(), this);
        //End new event clases

        plma.registerEvents(new net.nifheim.yitan.lorestats.crafting.AddedStats(), this);
        plma.registerEvents(new DamageSystem(this), this);
        plma.registerEvents(new net.nifheim.yitan.lorestats.durability.DurabilityEvents(), this);
        plma.registerEvents(new EnvironmentalDamage(), this);
        plma.registerEvents(new PotionListener(), this);
        plma.registerEvents(new InteractEvents(), this);
        plma.registerEvents(new PlayerLevelEvents(), this);
        plma.registerEvents(new RepairEvents(), this);
        plma.registerEvents(new MerchantClickListener(), this);
        plugin = this;

        writeDefaultFiles.checkExistence();

        getConfig().options().copyDefaults(true);
        getConfig().set("fileVersion", Integer.parseInt(getDescription().getVersion().replace(".", "")));
        saveConfig();

        eventlistener = new EventListener(this);
        damagefix = new DamageFix(this);
        plma.registerEvents(eventlistener, this);

        spigotStatCapWarning.updateSpigotValues();

        fastTasks = new MainFastRunnable(this).runTaskTimerAsynchronously(this, 20, 10);

        Bukkit.getOnlinePlayers().stream().filter((player) -> (new File(getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists())).forEachOrdered((player) -> {
            try {
                PlayerStats ps = getPlayerStats(player);
                PlayerDataConfig = new YamlConfiguration();
                PlayerDataConfig.load(new File(getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));
                ps.manaCurrent = PlayerDataConfig.getDouble("extra.mana");
            }
            catch (IOException | InvalidConfigurationException e) {
                System.out.println("*********** Failed to load player data for " + player.getName() + " when logging in! ***********");
            }
        });
        Bukkit.getOnlinePlayers().forEach((player) -> {
            getPlayerStats(player);
        });
        scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
        scoreboard.registerNewTeam("BlueCT");
        scoreboard.registerNewTeam("RedCT");
        scoreboard.registerNewTeam("YellowCT");
        scoreboard.getTeam("BlueCT").setPrefix(ChatColor.BLUE + "");
        scoreboard.getTeam("RedCT").setPrefix(ChatColor.RED + "");
        scoreboard.getTeam("YellowCT").setPrefix(ChatColor.YELLOW + "");
    }

    @Override
    public void onDisable() {
        if (scoreboard != null) {
            scoreboard.getTeam("BlueCT").unregister();
            scoreboard.getTeam("RedCT").unregister();
            scoreboard.getTeam("YellowCT").unregister();
        }
        Bukkit.getScheduler().cancelTasks(this);
        Bukkit.getOnlinePlayers().stream().filter((player) -> (new File(getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml").exists())).forEachOrdered((player) -> {
            try {
                PlayerStats ps = getPlayerStats(player);
                PlayerDataConfig = new YamlConfiguration();
                PlayerDataConfig.load(new File(getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml"));
                PlayerDataConfig.set("extra.logoutHealth", Math.round(player.getHealth()));
                PlayerDataConfig.set("extra.maxHealth", Math.round(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
                PlayerDataConfig.set("extra.hunger", player.getFoodLevel());
                PlayerDataConfig.set("extra.xp", player.getExp());
                PlayerDataConfig.set("extra.level", player.getLevel());
                PlayerDataConfig.set("extra.mana", ps.manaCurrent);
                PlayerDataConfig.set("extra.combatLogVisible", combatLogVisible.get(player.getName()));
                PlayerDataConfig.save(getDataFolder() + File.separator + "PlayerData" + File.separator + player.getName() + ".yml");
            }
            catch (IOException | InvalidConfigurationException e) {
                System.out.println("*********** Failed to save player data for " + player.getName() + " when logging out! ***********");
            }
        });
        log(String.format("Disabled Version %s", new Object[]{
            getDescription().getVersion()
        }));
        Bukkit.getOnlinePlayers().forEach((player) -> {
            getPlayerStats(player);
        });
    }

    public void loadManagers() {
        if (!messagesFile.exists()) {
            copy(getResource("messages.yml"), messagesFile);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        checkDependencies();
        getCommand("ils").setExecutor(new StatsCommand(this));
        getCommand("character").setExecutor(new CharacterCommand(this));
        getCommand("test").setExecutor(new TestCommand(this));
    }

    public void checkDependencies() {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderAPI = new PlaceholderAPI(this);
            placeholderAPI.hook();
            log("Successfully found and hooked into PlaceholderAPI.");
        } else {
            log("Unable to find PlaceholderAPI.");
        }
        if (getWorldGuard() != null) {
            log("Successfully found and hooked into WorldGuard.");
        } else {
            log("Unable to find WorldGuard!");
        }
        if (getVault() != null) {
            log("Successfully found and hooked into Vault.");
        } else {
            log("Unable to find Vault!");
        }
        if (getCitizens() != null) {
            log("Successfully found and hooked into Citizens.");
        } else {
            log("Unable to find Citizens!");
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException nfe) {
        }
        return false;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin WorldGuard = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if ((WorldGuard == null) || (!(WorldGuard instanceof WorldGuardPlugin))) {
            return null;
        }

        util_WorldGuard = new Util_WorldGuard(plugin);
        return (WorldGuardPlugin) WorldGuard;
    }

    public Vault getVault() {
        Plugin Vault = Bukkit.getServer().getPluginManager().getPlugin("Vault");

        if ((Vault == null) || (!(Vault instanceof Vault))) {
            return null;
        }

        util_Vault = new Util_Vault(plugin);
        return (Vault) Vault;
    }

    public Citizens getCitizens() {
        Plugin Citizens = Bukkit.getServer().getPluginManager().getPlugin("Citizens");

        if ((Citizens == null) || (!(Citizens instanceof Citizens))) {
            return null;
        }

        util_Citizens = new Util_Citizens(plugin);
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
        for (int i = 0; i < getConfig().getList("materials.tools").size(); i++) {
            if (getConfig().getList("materials.tools").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPotion(int potionID) {
        for (int i = 0; i < getConfig().getList("materials.potions").size(); i++) {
            if (Integer.parseInt(getConfig().getList("materials.potions").get(i).toString().split(":")[0]) == potionID) {
                return true;
            }
        }
        return false;
    }

    public boolean isArmour(Material material) {
        List<String> helmetList = getConfig().getStringList("materials.armour.helmet");
        List<String> chestList = getConfig().getStringList("materials.armour.chest");
        List<String> legsList = getConfig().getStringList("materials.armour.legs");
        List<String> bootsList = getConfig().getStringList("materials.armour.boots");
        List<String> armourList = new ArrayList<>();

        armourList.addAll(helmetList);
        armourList.addAll(chestList);
        armourList.addAll(legsList);
        armourList.addAll(bootsList);

        for (int i = 0; i < armourList.size(); i++) {
            if (armourList.get(i).split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isHelmet(Material material) {
        for (int i = 0; i < getConfig().getList("materials.armour.helmet").size(); i++) {
            if (getConfig().getList("materials.armour.helmet").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isChestplate(Material material) {
        for (int i = 0; i < getConfig().getList("materials.armour.chest").size(); i++) {
            if (getConfig().getList("materials.armour.chest").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isLeggings(Material material) {
        for (int i = 0; i < getConfig().getList("materials.armour.legs").size(); i++) {
            if (getConfig().getList("materials.armour.legs").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoots(Material material) {
        for (int i = 0; i < getConfig().getList("materials.armour.boots").size(); i++) {
            if (getConfig().getList("materials.armour.boots").get(i).toString().split(":")[0].equals(material.toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isSword(ItemStack item) {
        return (item.equals(new ItemStack(Material.WOOD_SWORD)) || (item.equals(new ItemStack(Material.STONE_SWORD))) || (item.equals(new ItemStack(Material.IRON_SWORD))) || (item.equals(new ItemStack(Material.GOLD_SWORD))) || (item.equals(new ItemStack(Material.DIAMOND_SWORD))));
    }

    public boolean isHoe(ItemStack item) {
        return (item.equals(new ItemStack(Material.WOOD_HOE)) || (item.equals(new ItemStack(Material.STONE_HOE))) || (item.equals(new ItemStack(Material.IRON_HOE))) || (item.equals(new ItemStack(Material.GOLD_HOE))) || (item.equals(new ItemStack(Material.DIAMOND_HOE))));
    }

    public boolean isAxe(ItemStack item) {
        return (item.equals(new ItemStack(Material.WOOD_AXE)) || (item.equals(new ItemStack(Material.STONE_AXE))) || (item.equals(new ItemStack(Material.IRON_AXE))) || (item.equals(new ItemStack(Material.GOLD_AXE))) || (item.equals(new ItemStack(Material.DIAMOND_AXE))));
    }

    public boolean isPickAxe(ItemStack item) {
        return (item.equals(new ItemStack(Material.WOOD_PICKAXE)) || (item.equals(new ItemStack(Material.STONE_PICKAXE))) || (item.equals(new ItemStack(Material.IRON_PICKAXE))) || (item.equals(new ItemStack(Material.GOLD_PICKAXE))) || (item.equals(new ItemStack(Material.DIAMOND_PICKAXE))));
    }

    public boolean isSpade(ItemStack item) {
        return (item.equals(new ItemStack(Material.WOOD_SPADE)) || (item.equals(new ItemStack(Material.STONE_SPADE))) || (item.equals(new ItemStack(Material.IRON_SPADE))) || (item.equals(new ItemStack(Material.GOLD_SPADE))) || (item.equals(new ItemStack(Material.DIAMOND_SPADE))));
    }

    public void swapItems(int slotA, int slotB, Inventory inv) {
        ItemStack itemStackA = inv.getItem(slotA);
        ItemStack itemStackB = inv.getItem(slotB);
        inv.setItem(slotA, itemStackB);
        inv.setItem(slotB, itemStackA);
    }

    public double getHealthValue(Player player) {
        if (getConfig().getInt("baseHealth") == 0) {
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
                newHP = (int) newHP + Double.valueOf(gearStats.getHealthItemInHand(itemInMainHand(player))).intValue();
            }

            if (isTool(itemInOffHand(player).getType())) {
                newHP = (int) newHP + Double.valueOf(gearStats.getHealthItemInHand(itemInOffHand(player))).intValue();
            }

            newHP = (int) newHP + Double.valueOf(gearStats.getHealthGear(player)).intValue();

        }

        return newHP;
    }

    public void updateHealth(Player player) {
        if ((!getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName()))
                && (!player.hasMetadata("NPC"))) {
            if (getConfig().getInt("baseHealth") == 0) {
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
                newHP = (int) newHP + Double.valueOf(gearStats.getHealthItemInHand(itemInMainHand(player))).intValue();
            }

            if (isTool(itemInOffHand(player).getType())) {
                newHP = (int) newHP + Double.valueOf(gearStats.getHealthItemInHand(itemInOffHand(player))).intValue();
            }

            newHP = (int) newHP + Double.valueOf(gearStats.getHealthGear(player)).intValue();

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newHP);

            if (getConfig().getInt("healthScale") > 0) {
                player.setHealthScale(getConfig().getDouble("healthScale"));
            }
        }
    }

    public void updatePlayerSpeed(Player player) {
        /*
        if (!getConfig().getStringList("disabledInWorlds").contains(player.getWorld().getName())) {
            final Player playerFinal = player;

            getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                float maxSpeed = 0.4F;
                float speed = 0.2f;
                float basespeed = 0.2f;

                speed = (float) (getConfig().getDouble("baseMovementSpeed"));
                speed = (float) (speed + (basespeed * Double.valueOf(playerFinal.getLevel()) * Main.getConfig().getDouble("additionalStatsPerLevel.speed")));

                speed = (float) (speed + (basespeed * Main.gearStats.getMovementSpeedGear(playerFinal) / 100));

                if (speed > maxSpeed) {

                    playerFinal.setWalkSpeed(maxSpeed);
                } else {
                    playerFinal.setWalkSpeed(speed);
                }
            }, 2L);
        } else {
            player.setWalkSpeed((float) getConfig().getDouble("baseMovementSpeed"));
        }
         */
    }

    public void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        }
        catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Can't copy the file " + file.getName() + " to the plugin data folder.", e.getCause());
        }
    }

    public String rep(String str) {
        if (str == null) {
            return "";
        }
        return str
                .replaceAll("%prefix%", getMessages().getString("Prefix"))
                .replaceAll("&", "§");
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public PlayerStats getPlayerStats(Player player) {
        if (playersStats.containsKey(player.getUniqueId())) {
            return playersStats.get(player.getUniqueId());
        } else {
            PlayerStats ps = new PlayerStats(player);
            ps.UpdateAll();
            playersStats.put(player.getUniqueId(), ps);
            return ps;
        }
    }

    public void setPlayerStats(PlayerStats ps) {
        playersStats.put(ps.player.getUniqueId(), ps);
    }

    public void log(String msg) {
        console.sendMessage(rep("&8[&cLoncoLoreItems&8] &7" + msg));
    }

    public DataManager getDataManager(Player p) {
        dataManager = new DataManager(p);
        return dataManager;
    }

    public Account getAccount(Player p) {
        return new Account(p);
    }
}
