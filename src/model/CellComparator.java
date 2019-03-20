package model;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {

    @Override
    public int compare(Cell c1, Cell c2) {
        return ((c1.getHeuristic() + c1.getCost()) - (c2.getHeuristic() +c2.getCost()));
    }
}
