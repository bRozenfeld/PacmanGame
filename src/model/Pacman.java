package model;

public class Pacman extends MovableElement{

    boolean isEaten;

    public Pacman(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        isEaten = false;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setIsEaten(boolean eaten) {
        isEaten = eaten;
    }

    public String toString() {
        return "isEaten: " + isEaten;
    }
}
