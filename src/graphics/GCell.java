package graphics;

import model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of a Cell
 */
public class GCell extends JPanel {

    private Cell cell;
    public GCell(Cell cell) {
        super();
        this.cell = cell;
        this.setLayout(new BorderLayout());
    }

    public Cell getCell() {
        return this.cell;
    }

}
