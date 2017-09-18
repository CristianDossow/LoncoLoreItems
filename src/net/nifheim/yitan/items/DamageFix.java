package net.nifheim.yitan.items;

import java.util.HashMap;
import java.util.UUID;
import net.nifheim.yitan.lorestats.Main;
import net.nifheim.yitan.lorestats.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class DamageFix {

    String weaponspeed;
    public HashMap<UUID, Long> attackCooldowns;
    public HashMap<UUID, Long> attackCooldownsEnd;
    private final String languageRegex;
    public Main instance;

    public DamageFix(Main instance) {
        super();
        //this.weaponspeed = "vataque";
        this.attackCooldowns = new HashMap<UUID, Long>();
        this.attackCooldownsEnd = new HashMap<UUID, Long>();
        this.languageRegex = "[^A-Za-zñÑáéíóúÁÉÍÓÚ_]";
        this.instance = instance;
    }

    public boolean IsAttackInCooldown(UUID uuid) {
        if (attackCooldowns.containsKey(uuid) && attackCooldownsEnd.containsKey(uuid)) {
            Long time = attackCooldownsEnd.get(uuid);
            if (time > System.currentTimeMillis()) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }

    public double getAttackpower(Player player) {
        UUID uuid = player.getUniqueId();
        if (IsAttackInCooldown(uuid)) {
            Long time = System.currentTimeMillis() - attackCooldowns.get(uuid);
            Long endtime = attackCooldownsEnd.get(uuid) - attackCooldowns.get(uuid);
            //Bukkit.broadcastMessage(time+"-"+endtime);
            double power = (double) time / (double) endtime;

            power = power * power;
            if (time < endtime) {
                return power;
            } else {
                return 1;
            }
        }
        return 1;
    }

    public double FixDamage(Entity entity, double damage) {
        if (entity instanceof Player) {
            Player player = ((Player) entity);

            if (getAttackpower(player) > 0.99 && getAttackpower(player) < 1) {
                damage = damage * 2;
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "¡Golpe Perfecto! (x2)");

            } else if (getAttackpower(player) > 0.8 && getAttackpower(player) < 1) {
                damage = damage * 1.25;
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "¡Golpe Óptimo! (x1,25)");
            } else {
                damage = damage * getAttackpower(player);
            }
            //if(!attackCooldowns.containsKey(player.getUniqueId())){
            instance.damagefix.attackCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
            PlayerStats ps = Main.getInstance().getPlayerStats(player);
            double weaponspeed = ps.weaponSpeed;
            instance.damagefix.attackCooldownsEnd.put(player.getUniqueId(), System.currentTimeMillis() + (long) (weaponspeed * 1000));
            BukkitTask task = new SwordActionBar(this.instance, player).runTaskTimer(this.instance, 0, 2);
            // }

            return damage;
        }
        /*
        if ((entity instanceof Projectile)) {
            Projectile projectile = (Projectile) entity;
            if (!(projectile.getShooter() instanceof Entity)) {
                return damage;
            }
            if (projectile.getShooter() != null) {
                if (projectile.getShooter() instanceof Player) {
                    Player shooter = (Player) projectile.getShooter();
                    if (shooter.getInventory().getItemInMainHand().getType().equals(Material.BOW)) {
                        HashMap<UUID, Long> bowCooldowns = instance.eventlistener.bowCooldowns;
                        UUID uuid = shooter.getUniqueId();
                        if (bowCooldowns.containsKey(uuid)) {
                            if (shooter.getInventory().getItemInMainHand().hasItemMeta()) {
                                List<String> lore = shooter.getInventory().getItemInMainHand().getItemMeta().getLore();
                                for (String loreline : lore) {
                                    loreline = ChatColor.stripColor(loreline.toString());
                                    loreline = loreline.toLowerCase();
                                    if (loreline.replaceAll(this.languageRegex, "").matches(weaponspeed)) {
                                        double thisweaponspeed = Double.parseDouble(loreline.replaceAll("[^0-9.+-]", ""));
                                        Long time = instance.eventlistener.shootpower.get(uuid);
                                        if (time < thisweaponspeed * 1000) {
                                            if (thisweaponspeed != 0) {
                                                double damagereduction = damage - (damage * (time / (thisweaponspeed * 1000)));
                                                damage = damage - damagereduction;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    } else {
                        return 1;
                    }
                }
            }
        }*/
        return damage;
    }
}
