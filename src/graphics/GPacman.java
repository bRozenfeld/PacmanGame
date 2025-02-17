package graphics;

import model.Direction;
import model.Pacman;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of Pacman
 */
public class GPacman extends JPanel {

    private Pacman pacman;
    private boolean nothing;

    public GPacman () {
        pacman = null;
        nothing = false;
    }

    public GPacman(Pacman pacman) {
        super();
        this.pacman = pacman;
    }

    public void setNothing(boolean nothing) {
        this.nothing = nothing;
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;
        int x = 0;
        int y = 0;
        int w = getSize().width -1;
        int h = getSize().height -1;

        if (pacman != null ) {
            g2d.setBackground(Color.BLACK);
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(x, y, w, h);

            if (pacman.isMouthOpen() == true) {
                Polygon polygon = new Polygon();
                g2d.setColor(Color.BLACK);
                switch (pacman.getDirection()) {
                    case Up:
                        polygon.addPoint(0, 0);
                        polygon.addPoint(w / 2, h / 2);
                        polygon.addPoint(w, 0);
                        break;
                    case Down:
                        polygon.addPoint(w / 2, h / 2);
                        polygon.addPoint(0, h);
                        polygon.addPoint(w, h);
                        break;
                    case Left:
                        polygon.addPoint(w / 2, h / 2);
                        polygon.addPoint(0, 0);
                        polygon.addPoint(0, h);
                        break;
                    case Right:
                        polygon.addPoint(w / 2, h / 2);
                        polygon.addPoint(w, h);
                        polygon.addPoint(w, 0);
                        break;
                }
                g2d.fillPolygon(polygon);
            }
        }
        else {
            if(!nothing) {
                Polygon polygon = new Polygon();

                g2d.setColor(Color.YELLOW);
                g2d.fillOval(x, y, w, h);

                g2d.setColor(Color.BLACK);
                polygon.addPoint(w / 2, h / 2);
                polygon.addPoint(w, h);
                polygon.addPoint(w, 0);

                g2d.fillPolygon(polygon);
            }
            else {
                g2d.setBackground(Color.BLACK);
            }
        }
    }
}
