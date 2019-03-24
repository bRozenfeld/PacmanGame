package model;

import java.util.ArrayDeque;

/**
 * Class representing pacman in the game
 *
 * @inv {@code cellQueue != null}
 */
public class Pacman extends MovableElement{

    /**
     * The direction of pacman mouth
     */
    private Direction direction;

    /**
     * represent if pacman has his mouth open or not
     */
    private boolean mouthOpen;

    /**
     * Represent a queue of cell containing the cells
     * where pacman has to go
     */
    private ArrayDeque cellQueue;

    /**
     * Create a pacman
     * @param cell : the cell containing pacman
     * @param beginCell : the cell where pacman starts in the beginning
     */
    public Pacman(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        direction = Direction.Left;
        cellQueue = new ArrayDeque();
        this.mouthOpen = true;
    }

    public Direction getDirection() {
        return direction;
    }

    public ArrayDeque getCellQueue() {
        return cellQueue;
    }

    public boolean isMouthOpen() {
        return mouthOpen;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setCellQueue(ArrayDeque cellQueue) {
        this.cellQueue = cellQueue;
    }

    /**
     * Move pacman to the next cell at the front of his queue
     * At each move his mouth either open or close
     */
    public void move() {
        Cell futureCell = (Cell) cellQueue.pollFirst();
        if(futureCell != null) {
            this.getCell().removeMovableElement(this);
            futureCell.addMovableElement(this);
            this.setCell(futureCell);
        }
        mouthOpen = !mouthOpen;
    }

}
