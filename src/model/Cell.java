package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class representing a cell of the game
 * @inv {@code x>=0}
 * @inv {@code y>=0}
 * @inv
 */
public class Cell {
    
    private int x;
    private int y;
    private final boolean isWall;
    private final StaticElement staticElementAtStart;
    private StaticElement staticElement;
    private ArrayList<MovableElement> movableElementList;


    public Cell(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.staticElementAtStart = null;
        this.movableElementList = new ArrayList<>();
        this.staticElement = null;
    }

    public Cell(int x, int y, boolean isWall, StaticElement staticElementAtStart, StaticElement se,
                ArrayList<MovableElement> mel) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.staticElement = se;
        this.movableElementList = mel;
        this.staticElementAtStart = staticElementAtStart;
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

    public StaticElement getStaticElementAtStart () {
        return this.staticElementAtStart;
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

    public void addStaticElement(StaticElement se){

    }

    public void removeStaticElement (StaticElement se){

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
}
