package net.nifheim.yitan.loncoloremagics;

import org.bukkit.Location;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

import net.nifheim.yitan.itemlorestats.Main;
import net.nifheim.yitan.loncoloreitems.SwordActionBar;

public class SpellCast {

    public static void spellBuilder(Spell spell, Player player) {
        Projectile projectile = getProjectile(spell, player);
        projectile.setShooter(player);
        projectile.setVelocity(player.getLocation().getDirection().multiply(spell.speed));
        setProjectileProperties(projectile, spell);
        BukkitTask task = new SpellParticles(Main.getPlugin(),spell,projectile).runTaskTimer(Main.getPlugin(), 0, 2);
    }

    public static Projectile getProjectile(Spell spell, Player player) {
        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());

        LlamaSpit projectile = (LlamaSpit) player.getWorld().spawn(loc, LlamaSpit.class);
        projectile.setGravity(false);
        //projectile.setGlowing(true);
        //projectile.setMetadata("ILS_DragonFireball", new FixedMetadataValue(Main.getPlugin(), player.getName()));
        return projectile;

    }

    public static void setProjectileProperties(Projectile projectile, Spell spell) {
        double DHA = spell.directHeal;
        double AHA = spell.aoeHealAmount;
        double DDA = spell.directDamageAmount;
        double ADA = spell.aoeDamageAmount;

        projectile.setMetadata("SPELLNAME=", new FixedMetadataValue(Main.getPlugin(), spell.name));
        projectile.setMetadata("DHA=", new FixedMetadataValue(Main.getPlugin(), DHA));
        projectile.setMetadata("AHA=", new FixedMetadataValue(Main.getPlugin(), AHA));
        projectile.setMetadata("AHR=", new FixedMetadataValue(Main.getPlugin(), spell.aoeHealRange));
        projectile.setMetadata("DDA=", new FixedMetadataValue(Main.getPlugin(), DDA));
        projectile.setMetadata("ADA=", new FixedMetadataValue(Main.getPlugin(), ADA));
        projectile.setMetadata("ADR=", new FixedMetadataValue(Main.getPlugin(), spell.aoeDamageRange));

        if ((DHA > 0.0D) || (AHA > 0.0D)) {
            projectile.setMetadata("Heal=", new FixedMetadataValue(Main.getPlugin(), true));
        }

        if ((DDA > 0.0D) || (ADA > 0.0D)) {
            projectile.setMetadata("Damage=", new FixedMetadataValue(Main.getPlugin(), true));
        }
    }
}
