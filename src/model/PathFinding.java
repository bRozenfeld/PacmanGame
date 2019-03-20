package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class PathFinding {

    public Stack<Cell> getWay(ArrayList<Cell> graph, Cell beginCell, Cell endCell) {

        Stack<Cell> wrongRes = new Stack<>();
        wrongRes.add(new Cell(9,9,true));

        Stack<Cell> res = new Stack<>(); //stack containing the way
        CellComparator cp = new CellComparator();

        PriorityQueue<Cell> openList = new PriorityQueue<>(1,cp);
        ArrayList<Cell> closedList = new ArrayList<Cell>();

        beginCell.setCost(0);
        openList.add(beginCell);

        int i = 1;// just to debug
        while(!openList.isEmpty()) {


            System.out.println("tour: " + i);



            Cell c = openList.remove();
            System.out.println("Cell : " + c);
            System.out.println("Voisins: ");

            for(Cell v : this.getAdjacentCells(graph,c, endCell, closedList)) {

                System.out.println(v);
                System.out.println("V parent : ");
                System.out.println(c.getPreviousCell());

                if(!openList.contains(v) && !(closedList.contains(v))) {
                    openList.add(v);
                }
            }

            if(c.getX()==endCell.getX() && c.getY()==endCell.getY()) {
                res = this.buildWay(c, beginCell);
                return res; // end of the algo, we find a way
            }

            closedList.add(c);

            System.out.println("Closed List: ");
            System.out.println(closedList);
            System.out.println();
            System.out.println("Open list");
            System.out.println(openList);

            System.out.println("Fin tour");
            System.out.println();
            i++;
        }

        return wrongRes;
    }

    /**
     * build the way from the cell until the begin cell
     * @param c
     * @return
     */
    private Stack<Cell> buildWay(Cell c, Cell beginCell) {
        System.out.println("build way");
        Stack<Cell> res = new Stack<>();
        Cell tmp = c.getPreviousCell();
        System.out.println(tmp);
        res.push(tmp);
        while(!tmp.equals(beginCell)){
            res.push(tmp);
            tmp = tmp.getPreviousCell();
            System.out.println(tmp);
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
