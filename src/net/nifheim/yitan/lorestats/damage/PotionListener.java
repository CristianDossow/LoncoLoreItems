package net.nifheim.yitan.lorestats.damage;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class PotionListener implements org.bukkit.event.Listener {

    @EventHandler
    public void drinkPotion(PlayerItemConsumeEvent event) {
        if ((event.getItem().getItemMeta().toString().contains("healing")) || (event.getItem().getItemMeta().toString().contains("harming"))) {
            Player player = event.getPlayer();

            if (event.getItem().getItemMeta().toString().contains("healing")) {
                double potion;

                if (event.getItem().getItemMeta().toString().contains("strong")) {
                    potion = Math.round(player.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.heal.instantHealthII"));
                } else {
                    potion = Math.round(player.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.heal.instantHealthI"));
                }

                if (player.getHealth() + potion > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                } else {
                    player.setHealth(player.getHealth() + potion);
                }
            } else if (event.getItem().getItemMeta().toString().contains("harming")) {
                double potion;

                if (event.getItem().getItemMeta().toString().contains("strong")) {
                    potion = Math.round(player.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.damage.instantHarmingII"));
                } else {
                    potion = Math.round(player.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.damage.instantHarmingI"));
                }

                if (player.getHealth() - potion > player.getHealth()) {
                    player.setHealth(0.0D);
                } else {
                    player.setHealth(player.getHealth() - potion);
                }
            }
        }
    }

    @EventHandler
    public void useSplashPotion(PotionSplashEvent event) {
        event.getPotion().getEffects().stream().filter((e) -> (e.getType().equals(org.bukkit.potion.PotionEffectType.HEAL))).forEachOrdered((_item) -> {
            event.getAffectedEntities().stream().filter((entity) -> ((entity instanceof Player))).forEachOrdered((entity) -> {
                ItemStack thrownPotion = event.getPotion().getItem();
                if (thrownPotion.getItemMeta().toString().contains("healing")) {
                    double potion;

                    if (thrownPotion.toString().contains("strong")) {
                        potion = Math.round(entity.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.heal.splashHealthII"));
                    } else {
                        potion = Math.round(entity.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.heal.splashHealthI"));
                    }

                    if (entity.getHealth() + potion > entity.getMaxHealth()) {
                        entity.setHealth(entity.getMaxHealth());
                    } else {
                        entity.setHealth(entity.getHealth() + potion);
                    }
                }
            });
        });
    }

    @EventHandler
    public void onEnterAreaEffectCloud(AreaEffectCloudApplyEvent event) {
        AreaEffectCloud areaCloud = event.getEntity();

        if (areaCloud.getBasePotionData().getType().equals(PotionType.INSTANT_DAMAGE)) {
            event.getAffectedEntities().stream().map((entity) -> {
                double potion;
                if (areaCloud.getBasePotionData().isUpgraded()) {
                    potion = Math.round(entity.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.damage.lingerHarmingII"));
                } else {
                    potion = Math.round(entity.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.damage.lingerHarmingI"));
                }
                entity.damage(potion, areaCloud);
                return entity;
            }).filter((entity) -> ((entity instanceof Player))).forEachOrdered((entity) -> {
                Main.getInstance().updateHealth((Player) entity);
            });
        } else if (areaCloud.getBasePotionData().getType().equals(PotionType.INSTANT_HEAL)) {
            event.getAffectedEntities().stream().filter((entity) -> ((entity instanceof Player))).map((entity) -> {
                double potion;
                if (areaCloud.getBasePotionData().isUpgraded()) {
                    potion = Math.round(entity.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.heal.lingerHealthII"));
                } else {
                    potion = Math.round(entity.getMaxHealth() / 100.0D * Main.getInstance().getConfig().getInt("potions.heal.lingerHealthI"));
                }
                if (entity.getHealth() + potion > entity.getMaxHealth()) {
                    entity.setHealth(entity.getMaxHealth());
                } else {
                    entity.setHealth(entity.getHealth() + potion);
                }
                return entity;
            }).forEachOrdered((entity) -> {
                Main.getInstance().updateHealth((Player) entity);
            });
        }
    }
}
