package net.nifheim.beelzebu.characters.classes.fighter;

import net.nifheim.beelzebu.characters.branch.BranchType;
import net.nifheim.beelzebu.characters.classes.Fighter;

/**
 *
 * @author Beelzebu
 */
public class BaseFighter extends Fighter {

    public BaseFighter(BranchType type, String id, String name) {
        super(type, id, name, 0);
    }

    @Override
    public String getID() {
        return "Fighter";
    }

    @Override
    public String getName() {
        return "Luchador";
    }

    @Override
    public int getMinLevel() {
        return 0;
    }

    @Override
    public int getBaseHP() {
        return 10;
    }

    @Override
    public int getBaseHPRegen() {
        return 2;
    }

    @Override
    public int getBaseMana() {
        return 20;
    }

    @Override
    public int getBaseManaRegen() {
        return 1;
    }
}
