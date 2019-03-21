package graphics;

import model.Direction;
import model.Pacman;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of Pacman
 */
public class GraphicPacman extends Component {

    private Pacman pacman;
    private boolean mouthOpen;
    private Direction direction;

    public GraphicPacman() {
        this.mouthOpen = true;
        this.direction = Direction.Right;
    }

    public GraphicPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int x = 0;
        int y = 0;
        int w = getSize().width -1;
        int h = getSize().height -1;

        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x,y,w,h);

        if(this.mouthOpen == true) {
            Polygon polygon = new Polygon();
            g2d.setColor(Color.WHITE);
            switch(this.direction) {
                case Up:
                    polygon.addPoint(0,0);
                    polygon.addPoint(w / 2, h /2);
                    polygon.addPoint(w,0);
                    break;
                case Down:
                    polygon.addPoint(w / 2, h /2);
                    polygon.addPoint(0,h);
                    polygon.addPoint(w,h);
                    break;
                case Left:
                    polygon.addPoint(w / 2, h /2);
                    polygon.addPoint(0,0);
                    polygon.addPoint(0,h);
                    break;
                case Right:
                    polygon.addPoint(w / 2, h /2);
                    polygon.addPoint(w,h);
                    polygon.addPoint(w,0);
                    break;
            }
            g2d.fillPolygon(polygon);
        }
    }
}
