import graphics.*;
import model.*;

import javax.swing.*;
import java.awt.*;

public class TestGraphics {

    public static void main(String args[]) {
        TestGraphics tg = new TestGraphics();

        Frame frame = new Frame("test");


        Bonus b = new Bonus(100, TypeBonus.Strawberry);
        GBonus bo = new GBonus(b);

        frame.add(bo);


        frame.setBounds(200,200,400,400);
        frame.setVisible(true);
    }
}