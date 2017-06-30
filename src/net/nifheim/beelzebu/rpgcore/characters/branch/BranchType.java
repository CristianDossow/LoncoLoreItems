package net.nifheim.beelzebu.rpgcore.characters.branch;

/**
 *
 * @author Beelzebu
 */
public enum BranchType {
    Fighter(0),
    Rogue(1),
    Mage(2),
    Demon(3);

    public int id;
    public String name;

    BranchType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    BranchType(int id) {
        this.id = id;
        name = name();
    }

    public int getID() {
        return id;
    }
}
