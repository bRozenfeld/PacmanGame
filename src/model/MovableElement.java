package model;


/**
 * Class representing an Element of the game that can move
 */
public abstract class MovableElement {

    /**
     * The cell currently containing this element
     */
    private Cell cell;
    /**
     * Cell where the element is positioning at the beginning
     */
    private Cell beginCell;

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
     * Display the position of the element in the terminal
     */
    public void displayPosition() {
        System.out.println("(" + this.cell.getX() + "," + this.cell.getY() + ")");
    }

}
