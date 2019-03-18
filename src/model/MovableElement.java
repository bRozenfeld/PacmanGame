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

    /**
     * @return Cell containing this movable element
     */
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
