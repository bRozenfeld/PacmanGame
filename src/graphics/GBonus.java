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
        int x = 1;
        int y = 1;
        int w = getSize().width - 1;
        int h = getSize().width -1;

        System.out.println(w);
        System.out.println(h);
        //g2d.setColor(Color.GREEN);
        //g2d.fillOval(x,y,w,w);

        switch (bonus.getTypeBonus()){
            case Cherry:
                System.out.println("I draw cherry");
                g2d.setColor(Color.RED);
                g2d.fillOval(x,h/2,w/3,h/3);
                g2d.fillOval(h/2,h/3,w/3,h/3);
                g2d.setColor(Color.GREEN);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(w/4,h/2,w*3/4,y);
                g2d.drawLine(w/2,h/2,w*3/4,y);
                break;
            case Orange:
                g2d.setColor(Color.orange);
                g2d.fillOval(50,50,w*2/3,h*2/3);
                break;
            case Key:
                g2d.setColor(Color.yellow);
                g2d.fillArc(x,y,w/4,h/4,360,360);
                g2d.setStroke(new BasicStroke(15));
                g2d.drawLine(w/4,w/8,w*3/4,w/8);
                g2d.drawLine(w/2,w/8,w/2,w/5);
                g2d.drawLine(w*3/4,w/8,w*3/4,w/4);
                break;
            case Bell:
                g2d.setColor(Color.yellow);
                g2d.fillArc(0, 0, w*3/4, h, 360, 180);
                g2d.fillRect(0, h* 1/2, w*3/4, h* 1/3);
                break;
            case Melon:
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x,y,w*3/4,h*3/4);
                break;
            case Galaxian:
                g2d.setColor(Color.BLUE);
                int[] xpts = {x,w/8,w/4,w*3/8,w/4,w/8};
                int[] ypts = {h/4,y,y,h/4,h/2,h/2};
                g2d.fillPolygon(xpts,ypts,6);
                break;
            case Strawberry:
                g2d.setColor(Color.RED);
                g2d.fillArc(x,y,w*1/2,h*3/4,360,180);
                int[] xpt = {w*1/4,0,(w*1/2)};
                int[] ypt = {250,h-242,h-242};
                g2d.fillPolygon(xpt,ypt,3);
                break;
            case Apple:
                g2d.setColor(Color.RED);
                g2d.fillOval(x,y,w*3/4,h*3/4);
                break;
        }
    }

}
