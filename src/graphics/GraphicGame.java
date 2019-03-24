package graphics;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

/**
 * Class representing the main frame of the game
 */
public class GraphicGame extends JFrame {

    private Game game;
    private boolean isRunning;
    private Timer timer;

    private ArrayList<GraphicCell> listCell; // all the graphic cells
    private ArrayList<GraphicCell> ghostList; // the ghost list

    private JPanel pBoard; // panel of the game
    private JPanel pInfo;
    private JPanel pBestScore;
    private JPanel pProducers;
    private JPanel pSouth;


    private JLabel lBestScore;
    private JLabel lLives;
    private JLabel lScore;
    private JLabel lLevel;

    private int time;


    public GraphicGame(String title, int x, int y, int w, int h, Game game) {
        super(title);
        this.setBounds(x,y,w,h);

        this.time = 0;

        this.game = game;

        this.initComponents();


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        this.isRunning = true;


        this.setFocusable(true);
    }

    /************* Initialisation part *********************/
    private void initComponents() {

        this.updateBoard();

        pSouth = this.initiPanelSouth(game);
        this.add(pSouth, BorderLayout.SOUTH);

        this.initPanelBestScore(game);
        this.add(this.pBestScore, BorderLayout.NORTH);

        this.addKeyListener(new BoardListener());
    }


    private void initPanelBestScore(Game g) {
        pBestScore=new JPanel(new FlowLayout(FlowLayout.CENTER));
        lBestScore=new JLabel("Best Score: "+ g.getBestScore());
        pBestScore.add(lBestScore);
        pBestScore.setBackground(Color.BLACK);
        lBestScore.setForeground(Color.WHITE);
        lBestScore.setFont(new Font("serif", Font.PLAIN,20));
    }

    private void initPanelInfo(Game g) {
        this.pInfo=new JPanel();
        this.pInfo.setLayout(new BoxLayout(pInfo,BoxLayout.X_AXIS));
        this.lLevel=new JLabel("Level: " + g.getLevel());
        this.pInfo.add(lLevel);
        this.lLives=new JLabel("Lives: " + g.getLives());
        this.pInfo.add(lLives);
        this.lScore=new JLabel("Score: " + g.getScore());
        this.pInfo.add(lScore);
    }

    private void initPanelProducers(){
        this.pProducers=new JPanel();
        this.pProducers.setLayout(new BoxLayout(pProducers,BoxLayout.X_AXIS));
        JLabel lProducers=new JLabel("By @ROZENFELD_Benjamin && @SBAITY_Haitam");
        lProducers.setForeground(Color.MAGENTA);
        pProducers.setBackground(Color.GREEN);
        pProducers.add(lProducers);
    }

    private JPanel initiPanelSouth(Game g){
        JPanel pSouth=new JPanel();
        pSouth.setLayout(new BoxLayout(pSouth,BoxLayout.Y_AXIS));
        this.initPanelInfo(g);
        pSouth.add(this.pInfo);

        this.initPanelProducers();
        pSouth.add(this.pProducers);
        pSouth.setBackground(Color.BLACK);
        return pSouth;
    }

    private void gameOver() {

        this.remove(pSouth);
        this.remove(pBestScore);

        pBoard = new JPanel(new BorderLayout());
        pBoard.setBackground(Color.BLACK);

        pBoard.add(lBestScore);

        lScore.setText("GAME OVER!!!       your score:"+game.getScore());
        lScore.setForeground(Color.YELLOW);
        lScore.setFont(new Font("serif", Font.PLAIN,30));
        lScore.setHorizontalAlignment(JLabel.CENTER);

        pBoard.add(lScore);

        this.add(pBoard);
        pBoard.repaint();
        pBoard.revalidate();


    }

    /************ Update Part *******************************/
    private void updateBoard() {

        pBoard = new JPanel(new GridLayout(game.getBoard().length, game.getBoard()[0].length));
        pBoard.setBackground(Color.BLACK);

        for(Cell c : game.getCellList()) {
            GraphicCell gc = new GraphicCell(c);
            if(c.getIsWall() == true) {
                gc.setBackground(Color.BLUE);
            }
            else { // check first static element in the cell
                gc.setBackground(Color.BLACK);
                StaticElement se = c.getStaticElement();
                if(se instanceof Gomme) {
                    Gomme gum = (Gomme) se;
                    GraphicGomme gg = new GraphicGomme(gum);
                    gc.add(gg);
                }
                else if(se instanceof Bonus) {
                    Bonus b = (Bonus) se;
                    //GraphicBonus gb = new GraphicBonus(b);
                    GBonus gb = new GBonus(b);
                    gc.add(gb);
                }
                // check the movable elements
                if(!c.getMovableElementList().isEmpty()) {
                    gc.removeAll();
                    MovableElement me = c.getMovableElementList().get(0); // just need one to draw
                    if (me instanceof  Pacman) {
                        GraphicPacman gp = new GraphicPacman(game.getPacman());
                        gc.add(gp);
                    } else  if(me instanceof Ghost) {
                        Ghost g = (Ghost) me;
                        GraphicGhost gg = new GraphicGhost(g);
                        gc.add(gg);
                    }
                }
            }
            pBoard.add(gc);
        }

        this.add(pBoard);
        pBoard.repaint();
        pBoard.revalidate();

    }

    private void updateInfo() {
        lLevel.setText("Level: " + game.getLevel());
        lLives.setText("Lives: "+ game.getLives());
        lScore.setText("Score: " + game.getScore());
    }

    private void updateGhost() {
        for (Ghost g : this.game.getGhostList()) {
            g.move();
            if(g.getIsRegenerating() == true)
                g.move(); // he will go 2 times faster
            else  {
                switch (g.getName()) {
                    case Inky:
                        if(g.getCellStack().size() <= 4)
                            game.setInkyMoves(g);
                        break;
                    case Pinky:
                        if(g.getCellStack().size() <= 2)
                            game.setPinkyMoves(g);
                        break;
                    case Blinky:
                        if(g.getCellStack().size() <= 6) // refresh blinky the most often
                            game.setBlinkyMoves(g);
                        break;
                    case Clyde:
                        if(g.getCellStack().size() == 0)
                            game.setClydeMoves(g);
                        break;
                }
            }
        }
        //this.displayGhostPosition();
    }

    private void updateModel(int timer) {
        game.checkGhost(timer);

        game.getPacman().move();
        game.checkPacman();
        updateGhost();
        game.checkPacman();

    }

    /************* Loop Game Part **************************/

    public void play() {

        int delay = 200;
        game.setGhostsMoves();

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!game.isOver()) {
                    updateModel(time);
                    updateBoard();
                    updateInfo();
                    time++;
                    System.out.println(time);
                }
                else {
                    timer.stop();
                    updateInfo();
                    gameOver();
                }
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
    }



    /************ Inner class Part ***************************/
    class BoardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    GraphicGame.this.game.getPacman().setDirection(Direction.Left);
                    GraphicGame.this.game.setPacmanMoves();
                    break;
                case KeyEvent.VK_RIGHT:
                    GraphicGame.this.game.getPacman().setDirection(Direction.Right);
                    GraphicGame.this.game.setPacmanMoves();
                    break;
                case KeyEvent.VK_UP:
                    GraphicGame.this.game.getPacman().setDirection(Direction.Up);
                    GraphicGame.this.game.setPacmanMoves();
                    break;
                case KeyEvent.VK_DOWN:
                    GraphicGame.this.game.getPacman().setDirection(Direction.Down);
                    GraphicGame.this.game.setPacmanMoves();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
