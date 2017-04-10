package net.nifheim.yitan.itemlorestats.Spells;

import net.nifheim.yitan.itemlorestats.Main;
import java.io.File;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;

public class SpellCreator {

    private org.bukkit.configuration.file.FileConfiguration PlayerDataConfig;

    public void spellBuilder(String spellFileName, Player player) {
        Projectile projectile = getProjectile(spellFileName, player);
        projectile.setShooter(player);
        projectile.setVelocity(player.getLocation().getDirection().multiply(getProjectileVelocity(spellFileName)));
        setProjectileProperties(projectile, spellFileName);
    }

    public String getValue(String spellFileName, String val) {
        try {
            this.PlayerDataConfig = new org.bukkit.configuration.file.YamlConfiguration();
            this.PlayerDataConfig.load(new File(Main.plugin.getDataFolder() + File.separator + "SavedSpells" + File.separator + spellFileName + ".yml"));

            return this.PlayerDataConfig.getString(val);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("*********** Failed to load " + val + " from " + spellFileName + "! ***********");
        }

        return null;
    }

    public Projectile getProjectile(String spellFileName, Player player) {
        String projectileType = getValue(spellFileName, "projectile-type").toLowerCase();
        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());

        if (projectileType.equals("snowball")) {
            Snowball projectile = (Snowball) player.getWorld().spawn(loc, Snowball.class);
            projectile.setMetadata("ILS_Snowball", new FixedMetadataValue(Main.getInstance(), player.getName()));

            return projectile;
        }
        if (projectileType.equals("smallfireball")) {
            SmallFireball projectile = (SmallFireball) player.getWorld().spawn(loc, SmallFireball.class);
            projectile.setYield(0.0F);
            projectile.setIsIncendiary(false);
            projectile.setMetadata("ILS_SmallFireball", new FixedMetadataValue(Main.getInstance(), player.getName()));

            return projectile;
        }
        if (projectileType.equals("fireball")) {
            Fireball projectile = (Fireball) player.getWorld().spawn(loc, Fireball.class);
            projectile.setYield(0.0F);
            projectile.setIsIncendiary(false);
            projectile.setMetadata("ILS_Fireball", new FixedMetadataValue(Main.getInstance(), player.getName()));

            return projectile;
        }
        if (projectileType.equals("arrow")) {
            Arrow projectile = (Arrow) player.getWorld().spawn(loc, Arrow.class);
            projectile.setMetadata("ILS_Arrow", new FixedMetadataValue(Main.getInstance(), player.getName()));

            return projectile;
        }
        if (projectileType.equals("egg")) {
            Egg projectile = (Egg) player.getWorld().spawn(loc, Egg.class);
            projectile.setMetadata("ILS_Egg", new FixedMetadataValue(Main.getInstance(), player.getName()));

            return projectile;
        }

        return null;
    }

    public double getProjectileVelocity(String spellFileName) {
        double projectileVelocity = Double.parseDouble(getValue(spellFileName, "projectile-velocity").toLowerCase());

        return projectileVelocity;
    }

    public Effect getProjectileHitEffect(String spellFileName) {
        String projectileHitEffect = getValue(spellFileName, "projectile-hit-effect").toLowerCase();

        if (projectileHitEffect.equals("ender_signal")) {
            Effect hitEffect = Effect.ENDER_SIGNAL;

            return hitEffect;
        }
        if (projectileHitEffect.equals("mobspawner_flames")) {
            Effect hitEffect = Effect.MOBSPAWNER_FLAMES;

            return hitEffect;
        }
        if (projectileHitEffect.equals("potion_break")) {
            Effect hitEffect = Effect.POTION_BREAK;

            return hitEffect;
        }
        if (projectileHitEffect.equals("smoke")) {
            Effect hitEffect = Effect.SMOKE;

            return hitEffect;
        }

        return null;
    }

    public int getCooldown(String spellFileName) {
        int cooldown = Integer.parseInt(getValue(spellFileName, "cooldown"));

        return cooldown;
    }

    public double getDirectHealAmount(String spellFileName) {
        double directHealAmount = Double.parseDouble(getValue(spellFileName, "heal.direct-heal-amount").toLowerCase());

        return directHealAmount;
    }

    public double getAOEHealAmount(String spellFileName) {
        double aoeHealAmount = Double.parseDouble(getValue(spellFileName, "heal.aoe-heal-amount").toLowerCase());

        return aoeHealAmount;
    }

    public double getAOEHealRange(String spellFileName) {
        double aoeHealRange = Double.parseDouble(getValue(spellFileName, "heal.aoe-heal-range").toLowerCase());

        return aoeHealRange;
    }

    public double getDirectDamageAmount(String spellFileName) {
        double directDamageAmount = Double.parseDouble(getValue(spellFileName, "damage.direct-damage-amount").toLowerCase());

        return directDamageAmount;
    }

    public double getAOEDamageAmount(String spellFileName) {
        double aoeDamageAmount = Double.parseDouble(getValue(spellFileName, "damage.aoe-damage-amount").toLowerCase());

        return aoeDamageAmount;
    }

    public double getAOEDamageRange(String spellFileName) {
        double aoeDamageRange = Double.parseDouble(getValue(spellFileName, "damage.aoe-damage-range").toLowerCase());

        return aoeDamageRange;
    }

    public void setProjectileProperties(Projectile projectile, String spellFileName) {
        double DHA = getDirectHealAmount(spellFileName);
        double AHA = getAOEHealAmount(spellFileName);
        double DDA = getDirectDamageAmount(spellFileName);
        double ADA = getAOEDamageAmount(spellFileName);

        projectile.setMetadata("DHA=", new FixedMetadataValue(Main.getInstance(), Double.valueOf(DHA)));
        projectile.setMetadata("AHA=", new FixedMetadataValue(Main.getInstance(), Double.valueOf(AHA)));
        projectile.setMetadata("AHR=", new FixedMetadataValue(Main.getInstance(), Double.valueOf(getAOEHealRange(spellFileName))));
        projectile.setMetadata("DDA=", new FixedMetadataValue(Main.getInstance(), Double.valueOf(DDA)));
        projectile.setMetadata("ADA=", new FixedMetadataValue(Main.getInstance(), Double.valueOf(ADA)));
        projectile.setMetadata("ADR=", new FixedMetadataValue(Main.getInstance(), Double.valueOf(getAOEDamageRange(spellFileName))));

        if ((DHA > 0.0D) || (AHA > 0.0D)) {
            projectile.setMetadata("Heal=", new FixedMetadataValue(Main.getInstance(), Boolean.valueOf(true)));
        }

        if ((DDA > 0.0D) || (ADA > 0.0D)) {
            projectile.setMetadata("Damage=", new FixedMetadataValue(Main.getInstance(), Boolean.valueOf(true)));
        }
    }
}
