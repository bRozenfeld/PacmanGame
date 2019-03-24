package model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Class used by the ghosts to find a way in the map
 * The algorithm used is the algorithm A*
 */
public class PathFinding {

    /**
     * Find the fastest way in the map using the algorithm A*
     * To go from a startCell to an endCell
     * @param graph : list of all the cell in the map
     * @param beginCell : Cell where we start the research
     * @param endCell : The cell we can to go
     * @param board : 2D array of int representing the map
     * @return : Stack of cell containing the way between begin cell and the end cell
     *
     * @pre {@code graph != null}
     * @pre {@code beginCell != null}
     * @pre {@code endCell != null}
     * @pre {@code board != null}
     */
    public Stack<Cell> getWay(ArrayList<Cell> graph, Cell beginCell, Cell endCell, int[][] board) {

        //pre condition
        assert graph != null : "Precondition violated : graph is null";
        assert beginCell != null : "Precondition violated : beginCell is null";
        assert endCell != null : "Precondition violated : endCell is null";
        assert board != null : "Precondition violated : board is null";

        Stack<Cell> res = new Stack<>(); //stack containing the way
        CellComparator cp = new CellComparator();

        PriorityQueue<Cell> openList = new PriorityQueue<>(1,cp);
        ArrayList<Cell> closedList = new ArrayList<>();

        beginCell.setCost(0);
        openList.add(beginCell);

        while(!openList.isEmpty()) {

            Cell c = openList.remove();

            for(Cell v : this.getAdjacentCells(graph,c, endCell, closedList, board)) {

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

        // there s no way to get to the end cell, return an empty stack
        return res;
    }

    /**
     * build the way back from the ending cell until the begin cell
     * @param c : the ending cell
     * @param beginCell : the begin cell
     * @return : Stack of cell containing the way between begin cell and the end cell
     *
     * @pre {@code c != null}
     * @pre {@code beginCell != null}
     *
     * @post {@code res != null}
     */
    public Stack<Cell> buildWay(Cell c, Cell beginCell) {

        //precondition
        assert c != null : "Precondition violated : c is null";
        assert beginCell != null : "Precondition violated : beginCell is null";

        Stack<Cell> res = new Stack<>();
        res.push(c);
        Cell tmp = c.getPreviousCell();
        while (tmp != null && !tmp.equals(beginCell)) {
            res.push(tmp);
            tmp = tmp.getPreviousCell();
        }

        // Clean all the previous cell to avoid stack overhead
        for(Cell cell : res) {
            cell.setPreviousCell(null);
        }

        //post condition
        assert res != null : "Post condition violated : res is null";

        return res;
    }

    /**
     * get all the adjacent cells of a cell
     * @param g : list containing all the cell in the map
     * @param c : the cell that we want those adjacent cell
     * @param endCell : the destination cell
     * @param closedList : list of cell where the heuristic and the cost has already been calculated
     * @param board : 2D int array containing the map
     * @return ArrayList of cell containing all the cell adjacent to the cell
     *
     * @pre {@code g != null}
     * @pre {@code c != null}
     * @pre {@code endCell != null}
     * @pre {@code closedList != null}
     * @pre {@code board != null}
     *
     * @post {@code res != null}
     */
    private ArrayList<Cell> getAdjacentCells(ArrayList<Cell> g, Cell c, Cell endCell, ArrayList<Cell> closedList, int[][] board) {

        //precondition
        assert g != null : "Precondition violated : g is null";
        assert c != null : "Precondition violated : c is null";
        assert endCell != null : "Precondition violated : endCell is null";
        assert closedList != null : "Precondition violated : closedList is null";

        ArrayList<Cell> listCell = new ArrayList<>();
        for(Cell cell : g) {
            if(((cell.getY()==c.getY() && (Math.abs(c.getX()-cell.getX())==1
                    || Math.abs(c.getX()-cell.getX())==board[0].length-1))
                    || (cell.getX() == c.getX() && Math.abs(c.getY()-cell.getY())==1))
                     && cell.getIsWall()==false) {
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

        //post condition
        assert listCell != null : "Post condition violated : listCell is null";

        return listCell;
    }

    /**
     * Heuristic function using the manhattan distance
     *
     * @param c1 : cell
     * @param c2 : cell
     * @return int corresponding to the manhattan distance between c1 and c2
     *
     * @pre {@code c1 != null}
     * @pre {@code c2 != null}
     *
     * @post {@code res >= 0}
     */
    private int getDistance(Cell c1, Cell c2) {
        //precondition
        assert c1 != null : "Precondition violated : c1 is null";
        assert c2 != null : "Precondition violated : c2 is null";

        int dx = Math.abs(c1.getX() - c2.getX());
        int dy = Math.abs(c1.getY() - c2.getY());

        //post condition
        assert dx + dy >= 0 : "Post condition violated : res is < 0";

        return dx + dy;
    }
}
