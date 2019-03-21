package graphics;

import model.Ghost;

import java.awt.*;

/**
 * Class representing the graphic part of a ghost
 */
public class GraphicGhost extends Component {

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

        switch(this.ghost.getColor()) {
            case Blue:
                g2d.setColor(Color.BLUE);
                break;
            case Orange:
                g2d.setColor(Color.ORANGE);
                break;
            case Red:
                g2d.setColor(Color.RED);
                break;
            case Pink:
                g2d.setColor(Color.PINK);
                break;
        }

        g2d.fillArc(w/2,0,w/2,h/3,90, 150);
        g2d.fillArc(w/2,0,w/2,h/3,90, -150);

        g2d.fillRect(0,h-h/3,w,2*h/3);

    }
}
