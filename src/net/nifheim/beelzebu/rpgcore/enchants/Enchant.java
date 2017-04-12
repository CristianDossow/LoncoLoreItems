package net.nifheim.beelzebu.rpgcore.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Beelzebu
 */
public class Enchant {

    private final String name;
    private final String description;
    private final int maxLevel;
    private final EnchantType type;

    public Enchant(EnchantType type, String name, String description, int maxLevel) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
    }

    public boolean onInteract(PlayerInteractEvent e, int level) {
        return false;
    }

    public boolean onInteractEntity(PlayerInteractEntityEvent e, int level) {
        return false;
    }

    public boolean onDamageOther(EntityDamageByEntityEvent e, Player attacker, int level) {
        return false;
    }
    
    public boolean onArrowHitEntity(EntityDamageByEntityEvent e, Player shooter, int level) {
        return this.onDamageOther(e, shooter, level);
    }

    public boolean onArrowHitGround(ProjectileHitEvent e, Player shooter, int level) {
        return false;
    }

    public void onEquip(Player p, int level) {
    }

    public void onUnequip(Player p, int level) {
    }

    public EnchantType getType() {
        return this.type;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}