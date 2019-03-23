package model;

import java.util.ArrayDeque;

public class Pacman extends MovableElement{

    private boolean isEaten;
    private Direction direction; //the direction of pacman mouth

    private ArrayDeque cellQueue;

    public Pacman(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        isEaten = false;
        direction = Direction.Left;
        cellQueue = new ArrayDeque();
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setIsEaten(boolean eaten) {
        isEaten = eaten;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ArrayDeque getCellQueue() {
        return cellQueue;
    }

    public void setCellQueue(ArrayDeque cellQueue) {
        this.cellQueue = cellQueue;
    }

    public void move() {
        Cell futureCell = (Cell) cellQueue.pollFirst();
        if(futureCell != null) {
            this.getCell().removeMovableElement(this);
            futureCell.addMovableElement(this);
            this.setCell(futureCell);
        }

    }

    public String toString() {
        return "isEaten: " + isEaten + " direction: " + direction;
    }
}
