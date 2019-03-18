package model;


/**
 * Class representing an Element of the game that can move
 */
public abstract class MovableElement {

    /**
     * The cell containing this element
     */
    private Cell cell;

    public MovableElement(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }
}
