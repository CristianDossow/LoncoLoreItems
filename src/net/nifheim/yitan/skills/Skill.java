package net.nifheim.yitan.skills;

import net.nifheim.yitan.lorestats.PlayerStats;
import org.bukkit.entity.Player;

public class Skill {

    public String name;
    public long cd;
    public Player player;
    public PlayerStats ps;

    public Skill() {
        super();
        this.cd = 0;
        this.name = "";
    }

    public void Execute(PlayerStats ps) {
        this.ps = ps;
        this.player = ps.player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCd() {
        return cd;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerStats getPs() {
        return ps;
    }

    public void setPs(PlayerStats ps) {
        this.ps = ps;
    }

}
