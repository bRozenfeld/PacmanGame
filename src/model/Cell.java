package model;

import java.util.ArrayList;

/**
 * Class representing a cell of the game
 * @inv {@code x>=0}
 * @inv {@code y>=0}
 * @inv
 */
public class Cell {
    
    private int x;
    private int y;
    private boolean isWall;
    private StaticElement staticElement;
    private ArrayList<MovableElement> movableElementList;

    public Cell(int x, int y, boolean isWall, StaticElement staticElement) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.staticElement = staticElement;
        this.movableElementList = new ArrayList<MovableElement>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addMovableElement(MovableElement me){

    }

    public void removeMovableElement(MovableElement me){

    }

    public void addStaticElement(StaticElement se){

    }

    public void removeStaticElement (StaticElement se){

    }
}
