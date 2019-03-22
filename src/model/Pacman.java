package model;

public class Pacman extends MovableElement{

    private boolean isEaten;
    private Direction direction; //the direction of pacman mouth

    public Pacman(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        isEaten = false;
        direction = Direction.Left;
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

    public String toString() {
        return "isEaten: " + isEaten + " direction: " + direction;
    }
}
