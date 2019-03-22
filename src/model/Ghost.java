package model;

import java.util.Objects;

/**
 * Class representing the ghost in the game
 */
public class Ghost extends MovableElement {

    private boolean isVulnerable;
    private boolean isRegenerating;
    private GhostName name;

    public Ghost(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        this.isRegenerating = false;
        this.isVulnerable = false;
        this.name = null;
    }

    public Ghost(Cell cell, GhostName name, Cell beginCell) {
        super(cell, beginCell);
        this.name = name;
        this.isVulnerable = false;
        this.isRegenerating = false;
    }

    public GhostName getName() {
        return name;
    }

    public void setName(GhostName name) {
        this.name = name;
    }

    public boolean getIsRegenerating() {
        return isRegenerating;
    }

    public boolean getIsVulnerable() {
        return isVulnerable;
    }

    public void setIsRegenerating(boolean regenerating) {
        isRegenerating = regenerating;
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
                name == ghost.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isVulnerable, name);
    }
}
