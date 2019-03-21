package graphics;

import model.Cell;
import model.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the main frame of the game
 */
public class GraphicGame extends JFrame {

    private Game game;

    private JPanel pBoard; // panel of the game
    private JPanel pInfo;
    private JPanel pStartStop;
    private JPanel pBestScore;
    private JButton bSTART;
    private JLabel lBestScore;
    private JLabel  lLives;
    private JLabel  lScore;
    private JLabel lLevel;

    public GraphicGame(String title, int x, int y, int w, int h, Game game) {
        super(title);
        this.setBounds(x,y,w,h);
        this.initComponents(game);
        this.initBoard(game);
        this.setVisible(true);

    }

    private void initComponents(Game g) {
        this.initBoard(g);
        this.add(this.pBoard, BorderLayout.CENTER);
    }

    /**
     * Initialize the board of the game
     * @param g
     */
    private void initBoard(Game g) {
        this.pBoard = new JPanel();
        this.pBoard.setLayout(new GridLayout(g.getBoard().length, g.getBoard()[0].length));
        // ne pas oublier les border


        for(Cell c : g.getCellList()) {
            GraphicCell gc = new GraphicCell(c);

            if(c.getIsWall() == true) {
                gc.setBackground(Color.BLUE);
            }
            else {
                gc.setBackground(Color.BLACK);
            }
            pBoard.add(gc);
        }
    }

    /**
     * Initialise
     */
    private void initPanelStopStart()  {
        this.pStartStop=new JPanel();
        this.pStartStop.setLayout(new BoxLayout(pStartStop,BoxLayout.X_AXIS));

        this.bSTART = new JButton("START");
        pStartStop.add(bSTART);
}

    private void initPanelBestScore(Game g) {
        this.pBestScore=new JPanel();
        this.pBestScore.setLayout(new BoxLayout(pBestScore,BoxLayout.X_AXIS));
        this.lBestScore=new JLabel("Best Score: ",g.getBestScore());
        this.pBestScore.add(lBestScore);
    }

    private void initPanelInfo(Game g) {
        this.pInfo=new JPanel();
        this.pInfo.setLayout((new BoxLayout(pInfo,BoxLayout.X_AXIS)));
        this.lLevel=new JLabel("Level: ",g.getLevel());
        this.pInfo.add(lLevel);
        this.lLives=new JLabel("Lives: ",g.getLives());
        this.pInfo.add(lLives);
        this.lScore=new JLabel("Score: ",g.getScore());
        this.pInfo.add(lScore);
    }




}
