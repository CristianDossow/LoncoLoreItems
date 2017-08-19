package net.nifheim.yitan.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import io.lumine.xikage.mythicmobs.MythicMobs;
import net.nifheim.yitan.lorestats.durability.Durability;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import net.nifheim.yitan.lorestats.PlayerStatsFormules;

public class EventListener implements Listener {

    Durability durability = new Durability();

    public HashMap<UUID, Long> bowCooldowns;
    public HashMap<UUID, Boolean> bowActionControl;
    //public HashMap<UUID, Long> shootpower;
    public LoreCraftingStats getlorestrings;
    public static String unknownItem = "Artículo no identificado";
    public static String enchantSlot = Main.getInstance().getMessages().getString("Lores.Enchants.Empty");
    public static String languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
    Main instance;
    Durability durabilityclass = new Durability();

    public EventListener(Main instance) {
        super();
        this.bowCooldowns = new HashMap<UUID, Long>();
        this.bowActionControl = new HashMap<UUID, Boolean>();
        //this.shootpower = new HashMap<UUID, Long>();
        getlorestrings = new LoreCraftingStats();
        this.instance = instance;
    }

    @EventHandler
    public void onBlockExpEvent(BlockExpEvent event) {
        event.setExpToDrop(0);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        ItemStack weapon = p.getInventory().getItemInMainHand();
        if (ItemCategory.isAnyWeapon(weapon) || ItemCategory.isTool(weapon)) {
            if (!weapon.hasItemMeta() || !weapon.getItemMeta().hasLore()) {
                p.getInventory().setItemInMainHand(LoreItemMaker.CheckItemLore(weapon, p));
            }
        }
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
            if (!item.getType().equals(Material.FISHING_ROD) && (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
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
                if (meta.hasEnchant(Enchantment.ARROW_FIRE)) {
                    meta.removeEnchant(Enchantment.ARROW_FIRE);
                }
                if (meta.hasEnchant(Enchantment.FIRE_ASPECT)) {
                    meta.removeEnchant(Enchantment.FIRE_ASPECT);
                }
                item.setItemMeta(meta);
            }
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID uuid = player.getUniqueId();
            bowActionControl.put(uuid, false);
            Long time = System.currentTimeMillis() - bowCooldowns.get(uuid);
            //shootpower.put(uuid, power);
            PlayerStats damagerStats = Main.getInstance().getPlayerStats(player);
            damagerStats.UpdateAll();
            double bowDamage = 1;
            if (bowCooldowns.containsKey(uuid)) {
                bowDamage = damagerStats.minDamage + Math.random() * (damagerStats.maxDamage - damagerStats.minDamage);
                if (time < damagerStats.weaponSpeed * 1000) {
                    if (damagerStats.weaponSpeed != 0) {
                        double damagereduction = bowDamage - (bowDamage * (time / (damagerStats.weaponSpeed * 1000)));
                        bowDamage = bowDamage - damagereduction;
                    }
                }
            }
            event.getProjectile().setMetadata("Damage=", new FixedMetadataValue(Main.getInstance(), bowDamage));
            this.durability.durabilityCalcForItemInHand(player, 1, "damage", player.getInventory().getItemInMainHand(), "Main");
            if (player.getInventory().getItemInOffHand().getType().equals(Material.BOW)) {
                this.durability.durabilityCalcForItemInHand(player, 1, "damage", player.getInventory().getItemInOffHand(), "Off");
            }
        } else if (event.getEntity() != null) {
            if (event.getEntity() instanceof LivingEntity) {
                double damage = MythicMobs.inst().getMobManager().getMythicMobInstance((Entity) event.getEntity()).getDamage();
                event.getProjectile().setMetadata("Damage=", new FixedMetadataValue(Main.getInstance(), damage));
            }
        }
    }

    @EventHandler
    public void onShootProjectile(ProjectileLaunchEvent event) {/*
    	if(event.getEntity() != null &&event.getEntityType() != EntityType.ARROW &&event.getEntity().getShooter()!=null ){
    		if(event.getEntity().getShooter() instanceof LivingEntity){
        		double damage = MythicMobs.inst().getMobManager().getMythicMobInstance((Entity) event.getEntity().getShooter()).getDamage();
        		event.getEntity().setMetadata("Damage=", new FixedMetadataValue(Main.getInstance(), damage));
    		}
    		if(event.getEntity().getShooter() instanceof Attributable){
            	Attributable entity = (Attributable)event.getEntity().getShooter();
            	AttributeInstance ai = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            	if(ai !=null){
            		event.getEntity().setMetadata("Damage=", new FixedMetadataValue(Main.getInstance(), ai.getValue()));
            	}
    		}

    	}*/

    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        item = CheckItemLore(item, player);
    }

    @EventHandler
    public void addStatsToCraftedItem(PrepareItemCraftEvent event) {
        ItemStack item = event.getInventory().getResult();
        if (item != null && Main.getInstance().isTool(item.getType())) {
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
        if (Main.getInstance().isTool(item.getType()) || Main.getInstance().isArmour(item.getType())) {
            String lvl = Main.getInstance().getConfig().getString("bonusStats.xpLevel.name");
            lvl = ChatColor.stripColor(lvl);
            String durability = Main.getInstance().getConfig().getString("bonusStats.durability.name");
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

    public static ItemStack AddItemLore(ItemStack item, Player player) {

        return LoreItemMaker.AddItemLore(item, player);
    }

    public static ItemStack ClearAndAddItemLore(ItemStack item, Player player) {

        return LoreItemMaker.ClearAndAddItemLore(item, player);
    }

    public static ItemStack CheckItemLore(ItemStack item, Player player) {

        return LoreItemMaker.CheckItemLore(item, player);
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
                } else if (item.getAmount() > 1 && itemcliked.getAmount() > 1) {
                    player.sendMessage("Este objeto solo se puede usar/encantar de uno a la vez");
                } else {
                    boolean enchanted = false;
                    ItemMeta meta = itemcliked.getItemMeta();
                    List<String> lores = meta.getLore();
                    for (String lore : lores) {
                        if (!enchanted && lore.equals(Main.getInstance().rep(enchantSlot))) {
                            enchanted = true;
                            player.setItemOnCursor(null);
                            lores.set(lores.indexOf(lore), ChatColor.GRAY + "- " + enchant);
                            player.sendMessage("Se ha encantado el objeto con " + enchant);
                            event.setCancelled(true);
                        }
                    }
                    if (enchanted) {
                        meta.setLore(lores);
                        itemcliked.setItemMeta(meta);
                        event.setCurrentItem(itemcliked);
                    } else {
                        player.sendMessage("El objeto no tiene ranuras para encantar");
                    }
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
        if (itemcliked != null && !itemcliked.getType().equals(Material.AIR) && EspecialAtributes.IsItemBooter(item)) {
            if (!EspecialAtributes.HasLevel(itemcliked)) {
                player.sendMessage("Este objeto no es reformable");
            } else if (item.getAmount() > 1) {
                player.sendMessage("Este objeto solo se puede usar de uno a la vez");
            } else {
                int bootpower = EspecialAtributes.getItemBooterPower(item);
                int itemlvl = PlayerStatsFormules.getItemLvl(itemcliked);
                int newlvl = itemlvl + bootpower;
                if (newlvl > 100) {
                    newlvl = 100;
                }
                ItemStack reformeditem = LoreItemMaker.ClearAndAddItemLore(itemcliked, player, newlvl);
                if (reformeditem != null) {
                    player.setItemOnCursor(null);
                    player.sendMessage("El objeto ha sido reformado al nivel " + newlvl);
                    itemcliked = reformeditem;
                    event.setCancelled(true);
                } else {
                    player.sendMessage("Este objeto no es reformable");
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
}
