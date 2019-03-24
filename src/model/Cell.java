package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class representing a cell of the game
 * @inv {@code x>=0}
 * @inv {@code y>=0}
 */
public class Cell {
    
    private int x;
    private int y;
    private final boolean isWall;
    private StaticElement staticElement;
    private ArrayList<MovableElement> movableElementList;

    // Part for path finding
    private Cell previousCell; //to rebuild the way
    private int cost; // cost to move from cell to cell
    private int heuristic; // distance between cell and the ending cell


    public Cell(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.movableElementList = new ArrayList<>();
        this.staticElement = null;
    }

    public Cell(int x, int y, boolean isWall, StaticElement se) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.staticElement = se;
        this.movableElementList = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getIsWall() {
        return isWall;
    }

    public StaticElement getStaticElement() {
        return staticElement;
    }

    public ArrayList<MovableElement> getMovableElementList() {
        return movableElementList;
    }

    /**
     * Add a new element to the cell
     * @param me : MovableElement to add
     */
    public void addMovableElement(MovableElement me){
        this.movableElementList.add(me);
    }

    /**
     * Remove a mobile element to the cell
     * @param me : MovableElement to remove
     */
    public void removeMovableElement(MovableElement me){
        this.movableElementList.remove(me);
    }

    /**
     * Add a static element to the cell
     * @param se : MovableElement to remove
     */
    public void addStaticElement(StaticElement se){ this.staticElement = se; }

    /**
     * Remove a static element to the cell
     * @param se : MovableElement to remove
     */
    public void removeStaticElement (StaticElement se){
        this.staticElement = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y &&
                isWall == cell.isWall &&
                Objects.equals(staticElement, cell.staticElement) &&
                Objects.equals(movableElementList, cell.movableElementList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, isWall, staticElement, movableElementList);
    }

    public String toString() {
        String s = new String("(" + this.x + "," + this.y + ")  Wall: " + this.isWall + "\n");
        s+= this.staticElement;
        return s;
    }

    // Part for the path finding

    public Cell getPreviousCell() {
        return previousCell;
    }

    public void setPreviousCell(Cell previousCell) {
        this.previousCell = previousCell;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
}
