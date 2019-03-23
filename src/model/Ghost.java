package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/**
 * Class representing the ghost in the game
 */
public class Ghost extends MovableElement {

    private PathFinding pf;

    private boolean isVulnerable;
    private boolean isRegenerating;
    private GhostName name;

    private Stack<Cell> cellStack;

    public Ghost(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        this.isRegenerating = false;
        this.isVulnerable = false;
        this.name = null;
        this.pf = new PathFinding();
        this.cellStack = new Stack<>();
    }

    public Ghost(Cell cell, GhostName name, Cell beginCell) {
        super(cell, beginCell);
        this.name = name;
        this.isVulnerable = false;
        this.isRegenerating = false;
        this.pf = new PathFinding();
        this.cellStack = new Stack<>();
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

    /**
     * Define stack of cell where to ghost will have to go using the pathfinding
     * @param endCell : Cell to go
     * @param graph : ArrayList of Cell containing all the Cell in the game
     */
    public void setMoves(ArrayList<Cell> graph, Cell endCell) {
        cellStack = pf.getWay(graph, this.getCell(), endCell);
    }

    public Stack<Cell> getCellStack() {
        return cellStack;
    }

    public void setCellStack(Stack<Cell> cellStack) {
        this.cellStack = cellStack;
    }

    public void move() {
        System.out.println("One move");
        Cell futureCell = null;
        if (!cellStack.empty()) {
            futureCell = cellStack.pop();
            this.getCell().removeMovableElement(this);
            futureCell.addMovableElement(this);
            this.setCell(futureCell);
        }
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
