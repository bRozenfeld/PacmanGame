package model;

/**
 * Abstract class representing an Element of the game that can move
 */
public abstract class MovableElement {

    /**
     * The cell currently containing this element
     */
    private Cell cell;

    /**
     * Cell where the element is positioning at the beginning
     */
    private final Cell beginCell;

    /**
     * Initialise a new movable element
     * @param cell : cell containing this element
     * @param beginCell : cell containing this element at the beginning
     */
    public MovableElement(Cell cell, Cell beginCell) {
        this.cell = cell;
        this.beginCell = beginCell;
    }

    public Cell getCell() {
        return cell;
    }

    public Cell getBeginCell() {
        return beginCell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    /**
     * Abstract method move
     */
    public void move() {}

}
