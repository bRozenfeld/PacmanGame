package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class PathFinding {

    public Stack<Cell> getWay(ArrayList<Cell> graph, Cell beginCell, Cell endCell) {
        Stack<Cell> res = new Stack<>(); //stack containing the way
        CellComparator cp = new CellComparator();

        PriorityQueue<Cell> openList = new PriorityQueue<>(1,cp);
        ArrayList<Cell> closedList = new ArrayList<>();

        beginCell.setCost(0);
        openList.add(beginCell);

        while(!openList.isEmpty()) {

            Cell c = openList.remove();

            /* To debug
            System.out.println("Cell : " + c);
            System.out.println("Voisins: ");
            */
            for(Cell v : this.getAdjacentCells(graph,c, endCell, closedList)) {

                /*
                System.out.println(v);
                System.out.println("V parent : ");
                System.out.println(c.getPreviousCell());
                */

                if(!openList.contains(v) && !(closedList.contains(v))) {
                    openList.add(v);
                }
            }

            if(c.getX()==endCell.getX() && c.getY()==endCell.getY()) {
                res = this.buildWay(c, beginCell);
                return res; // end of the algo, we find a way
            }

            closedList.add(c);

        }

        // maybe need modif to throw exception ause it means it never find the cell
        return res;
    }

    /**
     * build the way from the cell until the begin cell
     * @param c
     * @return
     */
    public Stack<Cell> buildWay(Cell c, Cell beginCell) {
        Stack<Cell> res = new Stack<>();
        res.push(c);
        Cell tmp = c.getPreviousCell();
        while (tmp != null && !tmp.equals(beginCell)) {
            res.push(tmp);
            tmp = tmp.getPreviousCell();
        }

        return res;
    }

    /**
     * get all the adjacent cells of a cell
     * @param g
     * @param c
     * @return
     */
    private ArrayList<Cell> getAdjacentCells(ArrayList<Cell> g, Cell c, Cell endCell, ArrayList<Cell> closedList) {
        ArrayList<Cell> listCell = new ArrayList<>();
        for(Cell cell : g) {
            if(((cell.getY()==c.getY() && (c.getX()==cell.getX()+1 || c.getX()==cell.getX()-1))
                    || (cell.getX() == c.getX() &&(c.getY()==cell.getY()+1 || c.getY()==cell.getY()-1))
                    ) && cell.getIsWall()==false) {
                if(closedList.contains(cell)) {
                    c.setPreviousCell(cell);
                }
                else {
                    cell.setHeuristic(this.getDistance(cell, endCell));
                    cell.setCost(c.getCost() + 1);
                    listCell.add(cell);
                }
            }
        }
        return listCell;
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
