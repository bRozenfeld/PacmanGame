package graphics;

import model.Ghost;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of a ghost
 */
public class GraphicGhost extends JPanel {

    /**
     * The ghost to draw
     */
    private Ghost ghost;

    public GraphicGhost(Ghost ghost) {
        super();
        this.ghost = ghost;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        int x = 0;
        int y = 0;
        int w = getSize().width -1;
        int h = getSize().height -1;

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


        if (ghost.getIsRegenerating() == false) {
            if (ghost.getVulnerabilityTime() > 0) {
                if(ghost.isFlashing())
                    g2d.setColor(Color.WHITE);
                else
                    g2d.setColor(Color.BLUE);
            }
            g2d.fillArc(0, 0, w, h, 360, 180);
            g2d.fillRect(0, h / 2, w, h / 2);
        }

        // if regenerating, then only show his eyes
        g2d.setColor(Color.WHITE);
        g2d.fillOval(w / 4, h / 6, w / 7, w / 7);
        g2d.fillOval(w / 2, h / 6, w / 7, w / 7);


    }
}
