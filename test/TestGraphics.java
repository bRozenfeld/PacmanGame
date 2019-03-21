import graphics.GraphicGhost;
import graphics.GraphicPacman;
import model.Cell;
import model.Ghost;
import model.GhostColor;

import javax.swing.*;
import java.awt.*;

public class TestGraphics {

    public static void main(String args[]) {
        TestGraphics tg = new TestGraphics();

        Frame frame = new Frame("test");
        GraphicPacman gp = new GraphicPacman();
        Cell c = new Cell(1,1,false);
        Cell bc = new Cell(2,1,false);
        Ghost g = new Ghost(c, GhostColor.Blue, bc);

        GraphicGhost gg = new GraphicGhost(g);

        JPanel jp1 = new JPanel();
        jp1.add(gg);
        JPanel jp2 = new JPanel();
        jp2.add(gp);

        frame.add(jp1);
        frame.add(jp2);

        frame.setBounds(200,200,400,400);
        frame.setVisible(true);
    }
}
