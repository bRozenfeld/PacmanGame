package model;

import java.util.ArrayDeque;

public class Pacman extends MovableElement{

    private Direction direction; //the direction of pacman mouth
    private boolean mouthOpen;

    private ArrayDeque cellQueue;

    public Pacman(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        direction = Direction.Left;
        cellQueue = new ArrayDeque();
        this.mouthOpen = true;
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
        mouthOpen = !mouthOpen;

    }

    public boolean isMouthOpen() {
        return mouthOpen;
    }

}
