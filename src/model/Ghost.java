package model;

import java.util.Objects;

/**
 * Class representing the ghost in the game
 */
public class Ghost extends MovableElement {

    private boolean isVulnerable;
    private GhostColor color;

    public Ghost(Cell cell, GhostColor color) {
        super(cell);
        this.color = color;
    }

    public GhostColor getColor() {
        return color;
    }

    public boolean getIsVulnerable() {
        return isVulnerable;
    }

    public void setIsVulnerable(boolean isVulnerable) {
        this.isVulnerable = isVulnerable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ghost ghost = (Ghost) o;
        return isVulnerable == ghost.isVulnerable &&
                color == ghost.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isVulnerable, color);
    }
}
