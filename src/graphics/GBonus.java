package graphics;

import model.Bonus;

import javax.swing.*;
import java.awt.*;

public class GBonus extends JPanel {
    private Bonus bonus;

    public GBonus(Bonus bonus) {
        super();
        this.bonus = bonus;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x = 0;
        int y = 0;
        int w = getSize().width - 1;
        int h = getSize().width -1;

        g2d.setColor(Color.GREEN);
        g2d.fillOval(0,0,w,h);
    }

}
