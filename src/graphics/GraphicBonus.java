package graphics;

import model.Bonus;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the graphic part of a bonus
 */
public class GraphicBonus extends JLabel {

    private Bonus bonus;
    private Image image;

    public GraphicBonus(String url, Bonus bonus) {
        super();
        this.bonus = bonus;

        ImageIcon icon = new ImageIcon(url);
        image = icon.getImage();
        this.setIcon(icon);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image newImg = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(newImg));

        g.drawImage(image,getWidth(),getHeight(), this);
    }

}
