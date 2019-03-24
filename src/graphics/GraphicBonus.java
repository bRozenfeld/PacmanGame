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

    public GraphicBonus(Bonus bonus) {
        super();
        this.bonus = bonus;

        ImageIcon icon = new ImageIcon(setUrl());
        image = icon.getImage();
        this.setIcon(icon);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image newImg = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(newImg));

        g.drawImage(image,getWidth(),getHeight(), this);
    }

    /**
     * Set the url to the file corresponding to the bonus
     * @return String containing the path until the file
     */
    private String setUrl() {
        String s = new String();
        switch(bonus.getTypeBonus()) {
            case Cherry:
                s = "res/images/cherry.jpg";
                break;
            case Strawberry:
                s = "res/images/strawberry.jpg";
                break;
            case Orange:
                s = "res/images/orange.jpg";
                break;
            case Apple:
                s = "res/images/apple.jpg";
                break;
            case Melon:
                s = "res/images/melon.jpg";
                break;
            case Galaxian:
                s = "res/images/galaxian.jpg";
                break;
            case Bell:
                s = "res/images/bell.jpg";
                break;
            case Key:
                s = "res/images/key.jpg";
                break;
        }
        return s;
    }
}
