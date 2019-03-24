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
        this.setLayout(new BorderLayout());
    }

    public Cell getCell() {
        return this.cell;
    }



    public void redrawCell() {

        //System.out.println(this.getComponentCount());
        /*
        for(Component comp : this.getComponents()) {
            if(comp instanceof GraphicGomme || comp instanceof GraphicGhost ||
                    comp instanceof GraphicBonus || comp instanceof GraphicPacman) {
                this.remove(comp);
            }

        }
*/

        if(cell.getIsWall() == false) {
            this.removeAll();
            StaticElement se = cell.getStaticElement();
            if (se == null) {
                this.setBackground(Color.BLACK);
            } else if (se instanceof Bonus) { // check the bonus
                Bonus b = (Bonus) se;
                GraphicBonus gb = new GraphicBonus("res/images/cherry.jpg", b);
                this.add(gb);
            } else if(se instanceof Gomme) {
                Gomme g = (Gomme) se;
                GraphicGomme gc = new GraphicGomme(g);
                this.add(gc);
            }

            ArrayList<MovableElement> meList = cell.getMovableElementList();
            for (MovableElement me : meList) {
                if (me instanceof Pacman) {
                    GraphicPacman gp = new GraphicPacman((Pacman) me);
                    this.add(gp);
                } else {
                    GraphicGhost gg = new GraphicGhost((Ghost) me);
                    this.add(gg);
                }
            }

        }
        this.repaint();
        this.revalidate();
    }

}
