package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/**
 * Class representing the ghost in the game
 */
public class Ghost extends MovableElement {

    private PathFinding pf;

    private boolean isRegenerating;
    private final GhostName name;

    private int vulnerabilityTime;

    private Stack<Cell> cellStack;

    public Ghost(Cell cell, Cell beginCell) {
        super(cell, beginCell);
        this.isRegenerating = false;
        this.name = null;
        this.pf = new PathFinding();
        this.cellStack = new Stack<>();
        this.vulnerabilityTime = 0;
    }

    public Ghost(Cell cell, GhostName name, Cell beginCell) {
        super(cell, beginCell);
        this.name = name;
        this.isRegenerating = false;
        this.pf = new PathFinding();
        this.cellStack = new Stack<>();
        this.vulnerabilityTime = 0;
    }

    public int getVulnerabilityTime() {
        return vulnerabilityTime;
    }

    public void setVulnerabilityTime(int vulnerabilityTime) {
        this.vulnerabilityTime = vulnerabilityTime;
    }


    public GhostName getName() {
        return name;
    }

    public boolean getIsRegenerating() {
        return isRegenerating;
    }

    public void setIsRegenerating(boolean regenerating) {
        isRegenerating = regenerating;
    }

    /**
     * Define stack of cell where to ghost will have to go using the pathfinding
     * If the ghost is regenerating or is vulnerable, move toward his begin cell
     * Else move toward endCell
     * @param endCell : Cell to go
     * @param graph : ArrayList of Cell containing all the Cell in the game
     */
    public void setMoves(ArrayList<Cell> graph, Cell endCell, int[][] board) {
            cellStack = pf.getWay(graph, this.getCell(), endCell, board);
    }

    /**
     * Define a way to come back to its initial position
     * @param graph
     */
    public void setRegeneratingMoves(ArrayList<Cell> graph, int[][] board) {
        cellStack = pf.getWay(graph, this.getCell(), this.getBeginCell(), board);
    }

    /**
     * Verify the statut isregenerating of a ghost
     * If isregenrating = true and he is on his begincell
     * Then switch isregenerating to false
     */
    public void checkIsRegenerating() {
        if(isRegenerating && this.getCell().equals(this.getBeginCell()))
            isRegenerating = false;
    }

    public Stack<Cell> getCellStack() {
        return cellStack;
    }

    public void setCellStack(Stack<Cell> cellStack) {
        this.cellStack = cellStack;
    }

    public void move() {
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
        return isRegenerating == ghost.isRegenerating &&
                vulnerabilityTime == ghost.vulnerabilityTime &&
                Objects.equals(pf, ghost.pf) &&
                name == ghost.name &&
                Objects.equals(cellStack, ghost.cellStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pf, isRegenerating, name, vulnerabilityTime, cellStack);
    }
}
