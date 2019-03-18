package model;

/**
 * Class representing the ghost in the game
 */
public class Ghost extends MovableElement {
    private Boolean isVulnerable;
    private String color;


    public Ghost(Cell cell, Boolean isVulnerable, String color) {
        super(cell);
        this.isVulnerable = isVulnerable;
        this.color = color;
    }

    public void move(){

    }
}
