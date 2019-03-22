package graphics;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class representing the graphic part of a Cell
 */
public class GraphicCell extends JPanel {

    private Cell cell;

    public GraphicCell(Cell cell) {
        super();
        this.cell = cell;
        //this.initComponent(cell);
    }

    public Cell getCell() {
        return this.cell;
    }

    private void initComponent(Cell cell) {
        StaticElement se = cell.getStaticElement();
        ArrayList<MovableElement> meList = cell.getMovableElementList();

        for(MovableElement me : meList) {
            if (me instanceof Pacman) {
                GraphicPacman gp = new GraphicPacman((Pacman)me);
                this.add(gp);
            }
            else {
                GraphicGhost gg = new GraphicGhost((Ghost)me);
                this.add(gg);
            }
        }
    }

}
