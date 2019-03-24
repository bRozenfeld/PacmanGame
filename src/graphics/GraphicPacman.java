package graphics;

import model.Direction;
import model.Pacman;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of Pacman
 */
public class GraphicPacman extends JPanel {

    private Pacman pacman;
    private boolean mouthOpen;
    private GraphicCell gCell;

    public GraphicPacman(Pacman pacman) {
        super();
        this.pacman = pacman;
        this.mouthOpen = true;
    }

    public GraphicCell getgCell() {
        return gCell;
    }

    public void setgCell(GraphicCell gCell) {
        this.gCell = gCell;
    }

    public void changeMouth() {
        mouthOpen = !mouthOpen;
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;
        int x = 0;
        int y = 0;
        int w = this.getParent().getSize().width -1;
        int h = this.getParent().getSize().height -1;

        g2d.setBackground(Color.BLACK);
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x,y,w,h);

        if(this.mouthOpen == true) {
            Polygon polygon = new Polygon();
            g2d.setColor(Color.BLACK);
            switch(pacman.getDirection()) {
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
