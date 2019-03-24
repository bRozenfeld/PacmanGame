package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/**
 * Class representing a ghost in the game
 *
 * @inv {vulnerabilityTime >= 0}
 */
public class Ghost extends MovableElement {

    /**
     * path finding used to move in the good position
     */
    private PathFinding pf;

    /**
     * Define if the ghost is regenerating or not
     * A ghost is regenerating when he pacman eats him
     * Until he s come back to his initial position
     */
    private boolean isRegenerating;

    /**
     * Define the name of the ghost
     */
    private final GhostName name;

    /**
     * Define is a ghost is flashing or not
     * A ghost is flashing when he s vulnerable and
     * his vulnerability is about to leave
     */
    private boolean isFlashing;

    /**
     * Int representing the remaining time of a ghost
     * to be vulnerable. A ghost become vulnerable when
     * pacman eats a super gum
     */
    private int vulnerabilityTime;

    /**
     * Stack containing the futures cell where the ghost
     * will move
     */
    private Stack<Cell> cellStack;

    /**
     * Create a new Ghost
     * @param cell : cell containing the ghost
     * @param name : the ghost name
     * @param beginCell : cell containing the ghost at the beginning
     */
    public Ghost(Cell cell, GhostName name, Cell beginCell) {
        super(cell, beginCell);
        this.name = name;
        this.isRegenerating = false;
        this.pf = new PathFinding();
        this.cellStack = new Stack<>();
        this.vulnerabilityTime = 0;
        this.isFlashing = false;
    }

    public int getVulnerabilityTime() {
        return vulnerabilityTime;
    }

    public GhostName getName() {
        return name;
    }

    public boolean isFlashing() {
        return isFlashing;
    }

    public boolean getIsRegenerating() {
        return isRegenerating;
    }

    public Stack<Cell> getCellStack() {
        return cellStack;
    }

    public void setVulnerabilityTime(int vulnerabilityTime) {
        this.vulnerabilityTime = vulnerabilityTime;
        invariant();
    }

    public void setIsRegenerating(boolean regenerating) {
        isRegenerating = regenerating;
    }

    public void setIsFlashing(boolean isFlashing) {
        this.isFlashing = isFlashing;
    }

    public void setCellStack(Stack<Cell> cellStack) {
        this.cellStack = cellStack;
    }

    /**
     * Each time this method is called
     * Change the state of flashing
     */
    public void setFlashing() {
        isFlashing = !isFlashing;
    }

    /**
     * Define stack of cell where to ghost will have to go using the path finding
     * starting from its position
     * @param endCell : Cell to go
     * @param graph : ArrayList of Cell containing all the Cell in the game
     * @param board : 2D int array representing the map
     *
     * @pre {@code endCell != null}
     * @pre {@code graph != null}
     * @pre {@code board != null}
     *
     * @post {@code cellStack != null}
     */
    public void setMoves(ArrayList<Cell> graph, Cell endCell, int[][] board) {
        //precondition
        assert graph != null : "Precondition violated : graph is null";
        assert endCell != null : "Precondition violated : endCell is null";
        assert board != null : "Precondition violated : board is null";

        cellStack = pf.getWay(graph, this.getCell(), endCell, board);

        //post condition
        assert cellStack != null : "Post condition violated : cellStack is null";
    }

    /**
     * Define a way to come back to its initial position
     * @param graph : list of cell containing all the cells of the game
     * @param : 2D int array representing the map
     *
     * @pre {@code graph != null}
     * @pre {@code board != null}
     *
     * @post {@code cellStack != null}
     */
    public void setRegeneratingMoves(ArrayList<Cell> graph, int[][] board) {
        //precondition
        assert graph != null : "Precondition violated : graph is null";
        assert board != null : "Precondition violated : board is null";

        cellStack = pf.getWay(graph, this.getCell(), this.getBeginCell(), board);

        //post condition
        assert cellStack != null : "Post condition violated : cellStack is null";
    }

    /**
     * Verify the state of regenerating of a ghost
     * If is regenrating = true and he is on his begin cell
     * Then switch is regenerating to false
     */
    public void checkIsRegenerating() {
        if(isRegenerating && this.getCell().equals(this.getBeginCell())) {
            isRegenerating = false;
        }
    }

    /**
     * Move the ghost into the cell
     * positioning at the head of his stack
     */
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

    private void invariant() {
        assert vulnerabilityTime >= 0 : "Invariant violated : vulnerabilityTime < 0";
    }

}
