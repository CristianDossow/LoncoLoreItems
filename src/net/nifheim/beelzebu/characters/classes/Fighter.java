package net.nifheim.beelzebu.characters.classes;

import net.nifheim.beelzebu.characters.branch.Branch;
import net.nifheim.beelzebu.characters.branch.BranchType;

/**
 *
 * @author Beelzebu
 */
public abstract class Fighter extends Branch {

    private int level;
    private int hp;
    private int hpRegen;
    private int mana;
    private int manaRegen;

    public Fighter(BranchType type, String id, String name, Integer level) {
        super(BranchType.Fighter, id, name);
    }

    @Override
    public BranchType getBranchType() {
        return BranchType.Fighter;
    }

    public int getMinLevel() {
        return level;
    }

    public int getBaseHP() {
        return hp;
    }

    public int getBaseHPRegen() {
        return hpRegen;
    }

    public int getBaseMana() {
        return mana;
    }

    public int getBaseManaRegen() {
        return manaRegen;
    }
}
