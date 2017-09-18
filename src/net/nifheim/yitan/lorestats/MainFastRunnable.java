package net.nifheim.yitan.lorestats;

import java.util.ArrayList;
import java.util.List;
import net.nifheim.yitan.modifiers.StatModifier;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MainFastRunnable extends BukkitRunnable {

    private final Main instance;
    //HashMap<Player, BossBar> manaBar = new HashMap();

    public MainFastRunnable(Main plugin) {
        this.instance = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStats ps = instance.getPlayerStats(player);
            ps.ManaRegen(0.25);
            float progress = (float) (ps.manaCurrent / ps.manaMax >= 0 ? ps.manaCurrent / ps.manaMax : 0);
            String barText = instance.rep(
                    "&9&lMana&7: &b" + (int) ps.manaCurrent + "&7/&b" + (int) ps.manaMax
                    + " &4&lVida&7: &c" + (int) ps.player.getHealth() + "&7/&c" + (int) ps.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
                    + " &6&lNivel: &e" + ps.player.getLevel());
            if (instance.manaBar.containsKey(player)) {

                instance.manaBar.get(player).setProgress(progress);
                instance.manaBar.get(player).setTitle(barText);
                if (0.20 > progress) {
                    instance.manaBar.get(player).setColor(BarColor.RED);
                } else if (0.50 > progress) {
                    instance.manaBar.get(player).setColor(BarColor.YELLOW);
                } else {
                    instance.manaBar.get(player).setColor(BarColor.GREEN);
                }
            } else {
                BossBar bs = Bukkit.createBossBar(barText, BarColor.BLUE, BarStyle.SEGMENTED_10);
                progress = (float) (ps.manaCurrent / ps.manaMax >= 0 ? ps.manaCurrent / ps.manaMax : 0);
                bs.setProgress(progress);
                bs.addPlayer(player);
                instance.manaBar.put(player, bs);
            }
            List<StatModifier> tr = new ArrayList<>();
            for (StatModifier sm : ps.Buffs) {
                if (sm.getEndTime() < System.currentTimeMillis()) {
                    tr.add(sm);
                    for (StatModifier boe : sm.getBuffOnEnd()) {
                        ps.addBuff(boe);
                    }
                    for (StatModifier boe : sm.getDeBuffOnEnd()) {
                        ps.addDeBuff(boe);
                    }
                }
            }
            if (ps.Buffs.removeAll(tr)) {
                ps.UpdateAll();
            }
            tr = new ArrayList<>();
            for (StatModifier sm : ps.DeBuffs) {
                if (sm.getEndTime() < System.currentTimeMillis()) {
                    tr.add(sm);
                    for (StatModifier boe : sm.getBuffOnEnd()) {
                        ps.addBuff(boe);
                    }
                    for (StatModifier boe : sm.getDeBuffOnEnd()) {
                        ps.addDeBuff(boe);
                    }
                }
            }
            if (ps.DeBuffs.removeAll(tr)) {
                ps.UpdateAll();
            }
            instance.setPlayerStats(ps);
        }
    }
}
