package net.nifheim.yitan.loncoloreitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import net.nifheim.yitan.itemlorestats.Durability.Durability;
import net.nifheim.yitan.itemlorestats.Main;

public class EventListener implements Listener {

    Durability durability = new Durability();

    public HashMap<UUID, Long> bowCooldowns;
    public HashMap<UUID, Boolean> bowActionControl;
    public HashMap<UUID, Long> shootpower;
    public LoreCraftingStats getlorestrings;
    public String unknownItem = "Artículo no identificado";
    public String languageRegex = "[^A-Za-z������������_]";
    Main instance;
    Durability durabilityclass = new Durability();

    public EventListener(Main instance) {
        super();
        this.bowCooldowns = new HashMap<UUID, Long>();
        this.bowActionControl = new HashMap<UUID, Boolean>();
        this.shootpower = new HashMap<UUID, Long>();
        getlorestrings = new LoreCraftingStats();
        this.instance = instance;
    }
    
    @EventHandler
    public void onBlockExpEvent(BlockExpEvent event) {
    	event.setExpToDrop(0);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item != null) {
            if (item.getType().equals(Material.BOW)) {
                bowCooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
                bowActionControl.put(event.getPlayer().getUniqueId(), true);
                BukkitTask task = new BowActionBar(this.instance, event.getPlayer()).runTaskTimerAsynchronously(this.instance, 0, 2);

            }
            if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                //if(LoreUtils.IsWeapon(event.getPlayer().getInventory().getItemInMainHand()) ){
                instance.damagefix.attackCooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
                double weaponspeed = LoreUtils.getWeaponSpeed(event.getPlayer().getInventory().getItemInMainHand());
                instance.damagefix.attackCooldownsEnd.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + (long) (weaponspeed * 1000));
                BukkitTask task = new SwordActionBar(this.instance, event.getPlayer()).runTaskTimer(this.instance, 0, 2);
            }
            if (EspecialAtributes.IsUnknownItem(item)) {
                item = LoreItemMaker.ClearAndAddItemLore(item, event.getPlayer());
            }
            if (item.getItemMeta() != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
                    meta.removeEnchant(Enchantment.ARROW_DAMAGE);
                }
                if (meta.hasEnchant(Enchantment.DAMAGE_ALL)) {
                    meta.removeEnchant(Enchantment.DAMAGE_ALL);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    meta.removeEnchant(Enchantment.PROTECTION_FIRE);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    meta.removeEnchant(Enchantment.PROTECTION_EXPLOSIONS);
                }
                if (meta.hasEnchant(Enchantment.MENDING)) {
                    meta.removeEnchant(Enchantment.MENDING);
                }

                item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {

        if (event.getEntity() instanceof Player) {

            UUID uuid = event.getEntity().getUniqueId();
            bowActionControl.put(uuid, false);
            Long power = System.currentTimeMillis() - bowCooldowns.get(uuid);
            shootpower.put(uuid, power);

            Player player = (Player) event.getEntity();
            this.durability.durabilityCalcForItemInHand(player, 1, "damage", player.getInventory().getItemInMainHand(), "Main");
            if (player.getInventory().getItemInOffHand().getType().equals(Material.BOW)) {
                this.durability.durabilityCalcForItemInHand(player, 1, "damage", player.getInventory().getItemInOffHand(), "Off");
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        item = ClearAndAddItemLore(item, player);
    }

    @EventHandler
    public void addStatsToCraftedItem(PrepareItemCraftEvent event) {
        ItemStack item = event.getInventory().getResult();
        if (Main.plugin.isTool(item.getType())) {
            if (!(item.getType().equals(Material.DIAMOND_HOE) && item.getDurability() != 0)) {
                List<String> temlore = new ArrayList<String>();
                ItemMeta meta = item.getItemMeta();
                temlore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + unknownItem);
                item.getItemMeta().setLore(temlore);
                meta.setLore(temlore);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                meta.spigot().setUnbreakable(true);
                item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void PickupItem(PlayerPickupItemEvent e) {
        boolean NeedLore = true;
        ItemStack item = e.getItem().getItemStack();
        if (Main.plugin.isTool(item.getType()) || Main.plugin.isArmour(item.getType())) {
            String lvl = Main.plugin.getConfig().getString("bonusStats.xpLevel.name");
            lvl = ChatColor.stripColor(lvl);
            String durability = Main.plugin.getConfig().getString("bonusStats.durability.name");
            lvl = ChatColor.stripColor(durability);
            if (item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                for (String loreline : lore) {
                    loreline = ChatColor.stripColor(loreline.toString());
                    loreline = loreline.toLowerCase();
                    if (loreline.replaceAll(languageRegex, "").matches(unknownItem.replaceAll(languageRegex, "").toLowerCase())) {
                        item = ClearAndAddItemLore(item, e.getPlayer());
                        NeedLore = false;
                        return;
                    }
                    if (loreline.replaceAll(languageRegex, "").matches(lvl.replaceAll(languageRegex, "").toLowerCase())) {
                        NeedLore = false;
                    }
                    if (loreline.replaceAll(languageRegex, "").matches(durability.replaceAll(languageRegex, "").toLowerCase())) {
                        NeedLore = false;

                    }
                }
            } else {
                item = AddItemLore(item, e.getPlayer());
                NeedLore = false;
            }
        }
        if (NeedLore) {
            item = AddItemLore(item, e.getPlayer());
        }
    }

    @EventHandler
    public void noEnchants(PrepareItemEnchantEvent e) {
        e.getEnchanter().sendMessage(ChatColor.RED + "Mesa de encantamientos desactivada en este servidor");
        e.setCancelled(true);
    }

    @EventHandler
    public void OnPrepareAnvilEvent(PrepareAnvilEvent e) {
        AnvilInventory anvil = (AnvilInventory) e.getInventory();
        anvil.setRepairCost(1);

        ItemStack item = anvil.getItem(1);

        if (item != null && item.getType().equals(Material.ENCHANTED_BOOK)) {
            if (item.getItemMeta() != null && item.getItemMeta() instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) item.getItemMeta();
                Map<Enchantment, Integer> enchants = bookmeta.getStoredEnchants();
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    if (entry.getKey().equals(Enchantment.ARROW_DAMAGE)) {
                        bookmeta.removeStoredEnchant(Enchantment.ARROW_DAMAGE);
                    }
                    if (entry.getKey().equals(Enchantment.DAMAGE_ALL)) {
                        bookmeta.removeStoredEnchant(Enchantment.DAMAGE_ALL);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_FIRE)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_FIRE);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_PROJECTILE)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_PROJECTILE);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_EXPLOSIONS)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS);
                    }
                    if (entry.getKey().equals(Enchantment.MENDING)) {
                        bookmeta.removeStoredEnchant(Enchantment.MENDING);
                    }
                }
                item.setItemMeta(bookmeta);
            }
        }

    }

    private ItemStack AddItemLore(ItemStack item, Player player) {

        return LoreItemMaker.AddItemLore(item, player);
    }

    private ItemStack ClearAndAddItemLore(ItemStack item, Player player) {

        return LoreItemMaker.ClearAndAddItemLore(item, player);
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ArrayList<ItemStack> dropstoremove = new ArrayList<ItemStack>();
        if (player.getEnderChest().firstEmpty() != -1) {
            for (ItemStack item : event.getDrops()) {
                if (player.getEnderChest().firstEmpty() != -1) {
                    if (EspecialAtributes.HasVoidBound(item)) {
                        player.getEnderChest().addItem(item);
                        dropstoremove.add(item);
                    }
                }
            }
            for (ItemStack item : dropstoremove) {
                event.getDrops().remove(item);
            }
        }
    }

    @EventHandler
    public void OnInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType().equals(Material.ENCHANTED_BOOK)) {
            if (item.getItemMeta() != null && item.getItemMeta() instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) item.getItemMeta();
                Map<Enchantment, Integer> enchants = bookmeta.getStoredEnchants();
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    if (entry.getKey().equals(Enchantment.ARROW_DAMAGE)) {
                        bookmeta.removeStoredEnchant(Enchantment.ARROW_DAMAGE);
                    }
                    if (entry.getKey().equals(Enchantment.DAMAGE_ALL)) {
                        bookmeta.removeStoredEnchant(Enchantment.DAMAGE_ALL);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_FIRE)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_FIRE);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_PROJECTILE)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_PROJECTILE);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_EXPLOSIONS)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS);
                    }
                    if (entry.getKey().equals(Enchantment.MENDING)) {
                        bookmeta.removeStoredEnchant(Enchantment.MENDING);
                    }
                }
                item.setItemMeta(bookmeta);
            }
        }
        if (item != null) {
            if (item.getItemMeta() != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
                    meta.removeEnchant(Enchantment.ARROW_DAMAGE);
                }
                if (meta.hasEnchant(Enchantment.DAMAGE_ALL)) {
                    meta.removeEnchant(Enchantment.DAMAGE_ALL);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    meta.removeEnchant(Enchantment.PROTECTION_FIRE);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    meta.removeEnchant(Enchantment.PROTECTION_EXPLOSIONS);
                }
                if (meta.hasEnchant(Enchantment.MENDING)) {
                    meta.removeEnchant(Enchantment.MENDING);
                }

                item.setItemMeta(meta);
            }
        }

    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCursor();
        ItemStack itemcliked = event.getCurrentItem();
        if (EspecialAtributes.IsUnknownItem(item)) {
            item = LoreItemMaker.ClearAndAddItemLore(item, player);
        }

        if (EspecialAtributes.IsEnchantGiver(item)) {
            if (ItemCategory.isValidToEnchantGiver(item, itemcliked)) {
                String enchant = EspecialAtributes.getEnchantGiverPower(item);
                if (EspecialAtributes.HaveEnchant(itemcliked, enchant)) {
                    player.sendMessage("Este objeto ya posee el encantamiento");
                } else if (item.getAmount() > 1) {
                    player.sendMessage("Este objeto solo se puede usar de uno a la vez");
                } else {
                    player.setItemOnCursor(null);
                    LoreItemMaker.AddCustomEnchant(itemcliked, enchant);
                    player.sendMessage("Se ha encantado el objeto con " + enchant);
                    event.setCancelled(true);
                }
            }
        }

        if (itemcliked != null && !itemcliked.getType().equals(Material.AIR) && EspecialAtributes.IsRepairerItem(item)) {
            if (!EspecialAtributes.Hasdurability(itemcliked)) {
                player.sendMessage("Este objeto no es reparable");
            } else if (item.getAmount() > 1) {
                player.sendMessage("Este objeto solo se puede usar de uno a la vez");
            } else {
                int repairpower = EspecialAtributes.getRepairerPower(item);
                ItemStack repaireditem = durabilityclass.repairbyamount(player, repairpower, itemcliked);
                if (repaireditem != null) {
                    player.setItemOnCursor(null);
                    player.sendMessage("El objeto ha sido reparado en " + repairpower + " puntos");
                    itemcliked = repaireditem;
                    event.setCancelled(true);
                } else {
                    player.sendMessage("El objeto no necesita ser reparado");
                }
            }
        }

        if (itemcliked != null && itemcliked.getType().equals(Material.ENCHANTED_BOOK)) {
            if (itemcliked.getItemMeta() != null && itemcliked.getItemMeta() instanceof EnchantmentStorageMeta) {
                EnchantmentStorageMeta bookmeta = (EnchantmentStorageMeta) itemcliked.getItemMeta();
                Map<Enchantment, Integer> enchants = bookmeta.getStoredEnchants();
                for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                    if (entry.getKey().equals(Enchantment.ARROW_DAMAGE)) {
                        bookmeta.removeStoredEnchant(Enchantment.ARROW_DAMAGE);
                    }
                    if (entry.getKey().equals(Enchantment.DAMAGE_ALL)) {
                        bookmeta.removeStoredEnchant(Enchantment.DAMAGE_ALL);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_FIRE)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_FIRE);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_PROJECTILE)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_PROJECTILE);
                    }
                    if (entry.getKey().equals(Enchantment.PROTECTION_EXPLOSIONS)) {
                        bookmeta.removeStoredEnchant(Enchantment.PROTECTION_EXPLOSIONS);
                    }
                    if (entry.getKey().equals(Enchantment.MENDING)) {
                        bookmeta.removeStoredEnchant(Enchantment.MENDING);
                    }
                }
                itemcliked.setItemMeta(bookmeta);
            }
        }

        if (item != null) {
            if (item.getItemMeta() != null) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
                    meta.removeEnchant(Enchantment.ARROW_DAMAGE);
                }
                if (meta.hasEnchant(Enchantment.DAMAGE_ALL)) {
                    meta.removeEnchant(Enchantment.DAMAGE_ALL);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    meta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_FIRE)) {
                    meta.removeEnchant(Enchantment.PROTECTION_FIRE);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_PROJECTILE)) {
                    meta.removeEnchant(Enchantment.PROTECTION_PROJECTILE);
                }
                if (meta.hasEnchant(Enchantment.PROTECTION_EXPLOSIONS)) {
                    meta.removeEnchant(Enchantment.PROTECTION_EXPLOSIONS);
                }
                if (meta.hasEnchant(Enchantment.MENDING)) {
                    meta.removeEnchant(Enchantment.MENDING);
                }

                item.setItemMeta(meta);
            }
        }

    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        if (player != null && !player.hasPermission("ils.admin") && player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
            PotionEffect pe = player.getPotionEffect(PotionEffectType.SLOW_DIGGING);
            if (pe.getAmplifier() > 2) {
                player.sendMessage("Estás demasiado debilitado para construir");
                event.setCancelled(true);
            }

        }

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ils")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("remake")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        Player player = (Player) sender;
                        int lvl = 0;
                        try {
                            lvl = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.DARK_RED + "El nivel debe ser numerico");
                            return false;
                        }
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if (item != null) {
                            item = LoreItemMaker.ClearAndAddItemLore(item, player, lvl);
                        }
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("voidbound")) {
                                Player player = (Player) sender;
                                ItemStack item = player.getInventory().getItemInMainHand();
                                LoreItemMaker.AddVoidbound(item);
                                return true;
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }
                if (args[0].equalsIgnoreCase("pergamino")) {
                    if (sender instanceof Player && sender.hasPermission("ils.admin")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("voidbound")) {
                                Player player = (Player) sender;
                                ItemStack item = ItemMaker.EnchantScroll(EspecialAtributes.voidbound, ItemCategory.anytype);
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
                            if (args[1].equalsIgnoreCase("repairstone")) {
                                int power = 1;
                                try {
                                    power = Integer.parseInt(args[2]);
                                } catch (NumberFormatException e) {
                                    sender.sendMessage(ChatColor.DARK_RED + "El nivel debe ser numerico");
                                    return false;
                                }
                                Player player = (Player) sender;
                                ItemStack item = ItemMaker.RepairerStone(power);
                                player.getInventory().addItem(item);
                                return true;
                            }
                            return false;
                        }

                        return false;
                    }
                    return false;
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("clearhand")) {

        }

        return false;
    }

}
