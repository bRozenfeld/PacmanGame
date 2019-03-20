package model;

import java.util.ArrayList;
import java.util.Stack;

public class PathFinding {


    public Stack<Cell> getWay(ArrayList<Cell> graph, Cell beginCell, Cell endCell) {
        Stack<Cell> res = new Stack<>();
        return res;
    }


    /**
     * Heuristic function using the manhattan distance
     *
     * @param c1
     * @param c2
     * @return
     */
    private int getDistance(Cell c1, Cell c2) {
        int dx = Math.abs(c1.getX() - c2.getX());
        int dy = Math.abs(c1.getY() - c2.getY());
        return dx + dy;
    }
}
