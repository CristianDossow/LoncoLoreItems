package net.nifheim.beelzebu.rpgcore.enchants;

/**
 *
 * @author Beelzebu
 */
public enum EnchantActions {
    INTERACT(0),
    EQUIP(1),
    UNEQUIP(2),
    ARROW_HIT_ENTITY(3),
    ARROW_HIT_GROUND(4),
    INTERACT_ENTITY(5),
    DAMAGE_OTHER_ENTITY(6),
    BLOCK_BREAK(7);

    public int id;
    public String name;

    EnchantActions(int id, String name) {
        this.id = id;
        this.name = name;
    }

    EnchantActions(int id) {
        this.id = id;
        this.name = this.name().toLowerCase();
    }

    public int getId() {
        return id;
    }
}
