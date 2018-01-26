package net.nifheim.beelzebu.characters.branch;

import net.nifheim.yitan.lorestats.Main;
import org.bukkit.entity.Player;

/**
 *
 * @author Beelzebu
 */
public abstract class Branch {

    private final Main plugin = Main.getInstance();
    private final BranchType type;
    private final String name;
    private final String id;

    public Branch(BranchType type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public Branch getBranchFromString(String branch) {
        return this;
    }

    public BranchType getBranchType() {
        return type;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setBranchForPlayer(Player player, BranchType type) {
        plugin.getAccount(player).getData().getDataFile().set("Branch", type.toString());
    }

    public BranchType getBranchForPlayer(Player player) {
        return plugin.getAccount(player).getData().getBranch();
    }
}
