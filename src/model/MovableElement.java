package model;


import java.util.Stack;

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
    private final Cell beginCell;

    /**
     * Contains the cells where to MovableElement has to go
     */
    private Stack<Cell> cellStack;

    public MovableElement(Cell cell, Cell beginCell) {
        this.cell = cell;
        this.beginCell = beginCell;
        this.cellStack = new Stack<>();
    }

    public Cell getCell() {
        return cell;
    }

    public Cell getBeginCell() {
        return beginCell;
    }

    public Stack<Cell> getCellStack() {
        return cellStack;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setCellStack(Stack<Cell> stack) {
        this.cellStack = stack;
    }


    public void move() {
        Cell futureCell = null;
        if (!cellStack.empty()) {
            futureCell = cellStack.pop();
            cell.removeMovableElement(this);
            futureCell.addMovableElement(this);
            this.cell = futureCell;
        }
    }

    /**
     * Display the position of the element in the terminal
     */
    public void displayPosition() {
        System.out.println("(" + this.cell.getX() + "," + this.cell.getY() + ")");
    }


}
