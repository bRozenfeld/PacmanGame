package graphics;

import model.Cell;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of a Cell
 */
public class GraphicCell extends JPanel {

    private Cell cell;

    public GraphicCell(Cell cell) {
        super();
        this.cell = cell;
        this.initComponent();
    }

    private void initComponent() {

    }
}
