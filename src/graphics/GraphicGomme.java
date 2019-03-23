package graphics;

import model.Gomme;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of a gomme
 */
public class GraphicGomme extends JPanel {

    private Gomme gomme;

    public GraphicGomme(Gomme gomme) {
        super();
        this.gomme = gomme;
        this.setBackground(Color.BLACK);
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        int x = 0;
        int y = 0;
        int w = this.getParent().getSize().width/2 ;
        int h = this.getParent().getSize().width/2 ;
        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.YELLOW);
        if(this.gomme.getIsSuper()==true){
            g2d.fillOval(x,y,w,h);
        }
        else {
            g2d.fillOval(x,y,w*2/3,h*2/3);
        }

    }


}
