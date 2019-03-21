import graphics.GraphicPacman;

import javax.swing.*;
import java.awt.*;

public class TestGraphics {

    public static void main(String args[]) {
        TestGraphics tg = new TestGraphics();

        Frame frame = new Frame("test");
        GraphicPacman gp = new GraphicPacman();

        frame.add(gp);

        frame.setBounds(200,200,400,400);
        frame.setVisible(true);
    }
}
