package graphics;

import model.Ghost;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of a ghost
 */
public class GraphicGhost extends JPanel {

    private Ghost ghost;

    public GraphicGhost() {

    }

    public GraphicGhost(Ghost ghost) {
        super();
        this.ghost = ghost;
        this.setBackground(Color.BLACK);
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D)g;
        g2d.setBackground(Color.BLACK);

        int x = 0;
        int y = 0;
        int w = this.getParent().getSize().width -1;
        int h = this.getParent().getSize().height -1;

        switch(this.ghost.getName()) {
            case Inky:
                g2d.setColor(Color.CYAN);
                break;
            case Clyde:
                g2d.setColor(Color.ORANGE);
                break;
            case Blinky:
                g2d.setColor(Color.RED);
                break;
            case Pinky:
                g2d.setColor(Color.PINK);
                break;
        }

        g2d.fillArc(0,0,w, h,360, 180);
        g2d.fillRect(0,h/2,w,h/2);

        g2d.setColor(Color.WHITE);
        g2d.fillOval(w/4,h/6, w/7, w/7);
        g2d.fillOval(w/2,h/6, w/7, w/7);

    }
}
