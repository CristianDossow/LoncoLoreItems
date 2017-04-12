package net.nifheim.beelzebu.rpgcore.enchants;

/**
 *
 * @author Beelzebu
 */
public enum EnchantTypes {
    ABILITY(0),
    SWORD(1),
    SPELL(2),
    BOW(5),
    ANY(6),
    BLOCK(7);

    public int id;
    public String name;

    EnchantTypes(int id, String name) {
        this.id = id;
        this.name = name;
    }

    EnchantTypes(int id) {
        this.id = id;
        this.name = this.name().toLowerCase();
    }

    public int getId() {
        return id;
    }
}
