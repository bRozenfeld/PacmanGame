package model;

/**
 * Class representing the bonus that appear in each level
 */
public class Bonus extends StaticElement {

    private final TypeBonus typeBonus;

    public Bonus(int value, TypeBonus typeBonus) {
        super(value);
        this.typeBonus = typeBonus;
    }

    public TypeBonus getTypeBonus() {
        return typeBonus;
    }

    public String toString() {
        return ""+ this.typeBonus;
    }
}
