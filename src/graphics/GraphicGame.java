package graphics;

import model.Cell;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Class representing the main frame of the game
 */
public class GraphicGame extends JFrame {

    private Game game;

    private boolean isRunning;

    private JPanel pBoard; // panel of the game
    private JPanel pInfo;
    private JPanel pStartStop;
    private JPanel pBestScore;
    private JPanel  pProducers;

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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.isRunning = false;

        this.setFocusable(true);
    }

    private void initComponents(Game g) {
        this.initBoard(g);
        this.add(this.pBoard, BorderLayout.CENTER);

        JPanel pSouth = this.initiPanelSouth(g);
        this.add(pSouth, BorderLayout.SOUTH);

        this.initPanelBestScore(g);
        this.add(this.pBestScore, BorderLayout.NORTH);

        this.addKeyListener(new BoardListener());
    }

    /**
     * Initialize the board of the game
     * @param g
     */
    private void initBoard(Game g) {
        this.pBoard = new JPanel();
        this.pBoard.setLayout(new GridLayout(g.getBoard().length, g.getBoard()[0].length));
        this.pBoard.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        for(Cell c : g.getCellList()) {
            GraphicCell gc = new GraphicCell(c);
            gc.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
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
        this.bSTART.addMouseListener(new RunListener());
        pStartStop.add(bSTART);
}

    private void initPanelBestScore(Game g) {
        this.pBestScore=new JPanel();
        this.pBestScore.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.lBestScore=new JLabel("Best Score: "+ g.getBestScore());
        this.pBestScore.add(lBestScore);
    }

    private void initPanelInfo(Game g) {
        this.pInfo=new JPanel();
        this.pInfo.setLayout(new BoxLayout(pInfo,BoxLayout.X_AXIS));
        this.lLevel=new JLabel("Level: " + g.getLevel());
        this.pInfo.add(lLevel);
        this.lLives=new JLabel("               Lives: " + g.getLives());
        this.pInfo.add(lLives);
        this.lScore=new JLabel("               Score: " + g.getScore());
        this.pInfo.add(lScore);
    }

    private void initPanelProducers(){
        this.pProducers=new JPanel();
        this.pProducers.setLayout(new BoxLayout(pProducers,BoxLayout.X_AXIS));
        JLabel lProducers=new JLabel("By @ROZENFELD Benjamin && @SBAITY Haitam");
        lProducers.setForeground(Color.MAGENTA);
        pProducers.setBackground(Color.GREEN);
        pProducers.add(lProducers);
    }

    private JPanel initiPanelSouth(Game g){
        JPanel pSouth=new JPanel();
        pSouth.setLayout(new BoxLayout(pSouth,BoxLayout.Y_AXIS));
        this.initPanelInfo(g);
        pSouth.add(this.pInfo);
        this.initPanelStopStart();
        pSouth.add(this.pStartStop);
        this.initPanelProducers();
        pSouth.add(this.pProducers);
        return pSouth;
    }

    private void updatebStart(){
        if (this.isRunning==false) {
            this.bSTART.setText("STOP");
            this.isRunning=true;
        }

        else {
            this.bSTART.setText("START");
            this.isRunning=false;
        }
    }

//Inner class

        class RunListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            GraphicGame.this.updatebStart();

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class BoardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            System.out.println(key);
            switch (key) {
                case KeyEvent.VK_LEFT:
                    break;
                case KeyEvent.VK_RIGHT:
                    break;
                case KeyEvent.VK_UP:
                    break;
                case KeyEvent.VK_DOWN:
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
