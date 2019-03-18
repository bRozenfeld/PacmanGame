package model;

/**
 * Class representing the bonus that appear in each level
 */
public class Bonus extends StaticElement {
    private TypeBonus typeBonus;

    public Bonus(int value, TypeBonus typeBonus) {
        super(value);
        this.typeBonus = typeBonus;
    }
}
