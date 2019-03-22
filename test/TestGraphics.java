import graphics.GraphicGhost;
import graphics.GraphicGomme;
import graphics.GraphicPacman;
import model.Cell;
import model.Ghost;
import model.Gomme;
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
        Gomme gomme=new Gomme(50,false);
        Gomme supgomme=new Gomme(100,true);
        GraphicGhost gg = new GraphicGhost(g);
        GraphicGomme ggo = new GraphicGomme(gomme);
        GraphicGomme gsgo = new GraphicGomme(supgomme);

        //JPanel jp1 = new JPanel();
        //jp1.add(gg);
        //JPanel jp2 = new JPanel();
        //jp2.add(gp);
        JPanel jp3= new JPanel();
        jp3.add(ggo);
        JPanel jp4= new JPanel();
        jp4.add(gsgo);

        //frame.add(jp1);
        //frame.add(jp2);
        frame.add(jp3);
        //frame.add(jp4);

        frame.setBounds(200,200,400,400);
        frame.setVisible(true);
    }
}
