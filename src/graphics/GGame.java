package graphics;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * Class representing the main frame of the game
 */
public class GGame extends JFrame {

    private Game game;
    private Timer timer;

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


    public GGame(String title, int x, int y, int w, int h, Game game) {
        super(title);
        this.setBounds(x,y,w,h);

        this.time = 0;
        this.game = game;

        this.initComponents();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
        this.setFocusable(true);
    }

    /************* Initialisation part *********************/
    private void initComponents() {

        this.updateBoard();

        pSouth = this.initPanelSouth();
        this.add(pSouth, BorderLayout.SOUTH);

        this.initPanelBestScore();
        this.add(this.pBestScore, BorderLayout.NORTH);

        this.addKeyListener(new BoardListener());
    }
    
    private void initPanelBestScore() {
        pBestScore=new JPanel(new FlowLayout(FlowLayout.CENTER));
        lBestScore=new JLabel("Best Score: "+ game.getBestScore());
        pBestScore.add(lBestScore);
        pBestScore.setBackground(Color.BLACK);
        lBestScore.setForeground(Color.WHITE);
        lBestScore.setFont(new Font("serif", Font.PLAIN,20));
    }

    private void initPanelInfo() {
        this.pInfo=new JPanel();
        this.pInfo.setLayout(new BoxLayout(pInfo,BoxLayout.X_AXIS));
        this.lLevel=new JLabel("Level: " + game.getLevel());
        this.pInfo.add(lLevel);
        this.lLives=new JLabel("Lives: " + game.getLives());
        this.pInfo.add(lLives);
        this.lScore=new JLabel("Score: " + game.getScore());
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

    private JPanel initPanelSouth(){
        JPanel pSouth=new JPanel();
        pSouth.setLayout(new BoxLayout(pSouth,BoxLayout.Y_AXIS));
        this.initPanelInfo();
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
            GCell gc = new GCell(c);
            if(c.getIsWall() == true) {
                gc.setBackground(Color.BLUE);
            }
            else { // check first static element in the cell
                gc.setBackground(Color.BLACK);
                StaticElement se = c.getStaticElement();
                if(se instanceof Gum) {
                    Gum gum = (Gum) se;
                    GGum gg = new GGum(gum);
                    gc.add(gg);
                }
                else if(se instanceof Bonus) {
                    Bonus b = (Bonus) se;
                    //GBonus gb = new GBonus(b);
                    GBonus gb = new GBonus(b);
                    gc.add(gb);
                }
                // check the movable elements
                if(!c.getMovableElementList().isEmpty()) {
                    gc.removeAll();
                    MovableElement me = c.getMovableElementList().get(0); // just need one to draw
                    if (me instanceof  Pacman) {
                        GPacman gp = new GPacman(game.getPacman());
                        gc.add(gp);
                    } else  if(me instanceof Ghost) {
                        Ghost g = (Ghost) me;
                        GGhost gg = new GGhost(g);
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
    }

    private void updateModel(int timer) {
        game.checkGhost(timer);
        game.getPacman().move();
        game.checkPacman(); // check pacman after he moves
        updateGhost();
        game.checkPacman(); // check pacman after the ghost moves
    }

    /************* Loop Game Part **************************/

    public void play() {

        int delay = 170;
        game.setGhostsMoves();

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!game.isOver()) {
                    updateModel(time);
                    updateBoard();
                    updateInfo();
                    time++;
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
                    GGame.this.game.getPacman().setDirection(Direction.Left);
                    GGame.this.game.setPacmanMoves();
                    break;
                case KeyEvent.VK_RIGHT:
                    GGame.this.game.getPacman().setDirection(Direction.Right);
                    GGame.this.game.setPacmanMoves();
                    break;
                case KeyEvent.VK_UP:
                    GGame.this.game.getPacman().setDirection(Direction.Up);
                    GGame.this.game.setPacmanMoves();
                    break;
                case KeyEvent.VK_DOWN:
                    GGame.this.game.getPacman().setDirection(Direction.Down);
                    GGame.this.game.setPacmanMoves();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

}
