package graphics;

import model.Gomme;

import java.awt.*;

/**
 * Class representing the graphic part of a gomme
 */
public class GraphicGomme extends Component {

    private Gomme gomme;

    public GraphicGomme(Gomme gomme) {
        this.gomme = gomme;
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        int x = 0;
        int y = 0;
        int w = getSize().width -1;
        int h = getSize().height -1;
        g2d.setColor(Color.yellow);
        g2d.fillOval(x,y,w,h);

    }
}
