package graphics;

import model.Ghost;

import java.awt.*;

/**
 * Class representing the graphic part of a ghost
 */
public class GraphicGhost extends Component {

    private Ghost ghost;

    public GraphicGhost(Ghost ghost) {
        this.ghost = ghost;
    }
}
