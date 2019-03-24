import graphics.*;
import model.*;

import javax.swing.*;
import java.awt.*;

public class TestGraphics {

    public static void main(String args[]) {
        TestGraphics tg = new TestGraphics();

        Frame frame = new Frame("test");
        //GraphicPacman gp = new GraphicPacman();
        Cell c = new Cell(1,1,false);
        Cell bc = new Cell(2,1,false);
        Ghost g = new Ghost(c, bc);
        GraphicGhost gg = new GraphicGhost(g);

        Gomme gomme=new Gomme(50,false);
        Gomme supgomme=new Gomme(100,true);
        //GraphicGhost gg = new GraphicGhost(g);
        GraphicGomme ggo = new GraphicGomme(gomme);
        GraphicGomme gsgo = new GraphicGomme(supgomme);

        JPanel jp1 = new JPanel();
        //jp1.setBackground(Color.RED);
        jp1.add(gg);
        //JPanel jp2 = new JPanel();
        //jp2.add(gp);

        //JPanel jp3= new JPanel();
      //  jp3.add(ggo);
     //  JPanel jp4= new JPanel();
      //  jp4.add(gsgo);

        //Panel jp5 = new JPanel();

        //jp5.setBackground(Color.BLACK);
        //jp1.add(ggo);
        //frame.add(ggo);
        //frame.add(jp1);
        //frame.add(gg);
        //frame.add(jp2);
        //  frame.add(jp3);
        //frame.add(jp4);

        Bonus b = new Bonus(100, TypeBonus.Galaxian);
        GBonus bo = new GBonus(b);
        //JPanel p6=new JPanel();
        //p6.add(bo);
        frame.add(bo);
        //ImageIcon im = new ImageIcon("res/images/chery.jpg");
        //System.out.println(im.getImage());
        //GraphicBonus gb = new GraphicBonus("res/images/cherry.jpg",b);
        //jp1.add(gb);
        //frame.add(gb);

        frame.setBounds(200,200,400,400);
        frame.setVisible(true);
    }
}
