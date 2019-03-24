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
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        int x = 0;
        int y = 0;
        int w = getSize().width - 1;
        int h = getSize().width -1;

        if(this.gomme.getIsSuper()==true){
            g2d.setColor(Color.PINK);
            g2d.fillOval(w/3,h/4,w*1/2,h*1/2);
        }
        else {
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(w/3,h/4,w*1/3,h*1/3);
        }
    }
}
