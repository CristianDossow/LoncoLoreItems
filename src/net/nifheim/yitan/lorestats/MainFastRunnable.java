package net.nifheim.yitan.lorestats;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.modifiers.StatModifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MainFastRunnable extends BukkitRunnable {

    private final Main plugin;

    public MainFastRunnable(Main main) {
        plugin = main;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStats ps = plugin.getPlayerStats(player);
            ps.ManaRegen(0.25);
            List<StatModifier> tr = new ArrayList<>();
            for (StatModifier sm : ps.Buffs) {
                if (sm.getEndTime() < System.currentTimeMillis()) {
                    tr.add(sm);
                    sm.getBuffOnEnd().forEach((boe) -> {
                        ps.addBuff(boe);
                    });
                    sm.getDeBuffOnEnd().forEach((boe) -> {
                        ps.addDeBuff(boe);
                    });
                }
            }
            if (ps.Buffs.removeAll(tr)) {
                ps.UpdateAll();
            }
            tr = new ArrayList<>();
            for (StatModifier sm : ps.DeBuffs) {
                if (sm.getEndTime() < System.currentTimeMillis()) {
                    tr.add(sm);
                    sm.getBuffOnEnd().forEach((boe) -> {
                        ps.addBuff(boe);
                    });
                    sm.getDeBuffOnEnd().forEach((boe) -> {
                        ps.addDeBuff(boe);
                    });
                }
            }
            if (ps.DeBuffs.removeAll(tr)) {
                ps.UpdateAll();
            }
            plugin.setPlayerStats(ps.getUuid(), ps);
        }
    }
}
