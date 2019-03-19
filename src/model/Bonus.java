package model;

/**
 * Class representing the bonus that appear in each level
 */
public class Bonus extends StaticElement {
    private TypeBonus typeBonus;
    private final Cell cell;

    public Bonus(int value, TypeBonus typeBonus, Cell c) {
        super(value);
        this.typeBonus = typeBonus;
        this.cell = c;
    }

    public Cell getCell() {
        return cell;
    }

}
