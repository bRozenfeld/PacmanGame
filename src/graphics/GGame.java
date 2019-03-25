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
    private JPanel pLives;

    private GPacman lifeOne;
    private GPacman lifeTwo;
    private GPacman lifeThree;


    private JLabel lBestScore;
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

        initPanelLives();

        pSouth = this.initPanelSouth();
        this.add(pSouth, BorderLayout.SOUTH);

        this.initPanelBestScore();
        this.add(this.pBestScore, BorderLayout.NORTH);

        this.addKeyListener(new BoardListener());
    }
    
    private void initPanelBestScore() {
        pBestScore=new JPanel(new FlowLayout(FlowLayout.CENTER));
        lBestScore=new JLabel("Best Score: "+ game.getBestScore());
        lBestScore.setForeground(Color.YELLOW);
        pBestScore.add(lBestScore);
        pBestScore.setBackground(Color.BLACK);
        lBestScore.setFont(new Font("serif", Font.PLAIN,40));
    }

    private void initPanelInfo() {
        this.pInfo=new JPanel();
        pInfo.setBackground(Color.BLACK);
        this.pInfo.setLayout(new GridLayout(1,3));

        this.lLevel=new JLabel("" + game.getLevel());
        this.pInfo.add(lLevel);
        lLevel.setHorizontalAlignment(JLabel.CENTER);
        lLevel.setForeground(Color.YELLOW);

        this.lScore=new JLabel("Score: " + game.getScore());
        lScore.setFont(new Font("test",Font.ROMAN_BASELINE, 30));
        this.pInfo.add(lScore);
        lScore.setHorizontalAlignment(JLabel.CENTER);
        lScore.setForeground(Color.YELLOW);

        pInfo.add(pLives);

    }

    private void initPanelProducers(){
        this.pProducers=new JPanel();
        this.pProducers.setLayout(new BoxLayout(pProducers,BoxLayout.X_AXIS));
        JLabel lProducers=new JLabel("By @ROZENFELD_Benjamin && @SBAITY_Haitam");
        lProducers.setForeground(Color.BLUE);
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

    private void initPanelLives() {
        pLives = new JPanel(new GridLayout(1,3));
        pLives.setBackground(Color.BLACK);

        lifeOne = new GPacman();
        pLives.add(lifeOne);

        lifeTwo = new GPacman();
        pLives.add(lifeTwo);

        lifeThree = new GPacman();
        pLives.add(lifeThree);
    }

    private void gameOver() {

        this.remove(pSouth);
        this.remove(pBestScore);

        pBoard = new JPanel(new BorderLayout());
        pBoard.setBackground(Color.BLACK);

        lBestScore.setHorizontalAlignment(JLabel.CENTER);
        pBoard.add(lBestScore, BorderLayout.NORTH);

        JPanel pgo = new JPanel(new GridLayout(2,1));
        pgo.setBackground(Color.BLACK);

        JLabel jlgo = new JLabel("GAME OVER !");
        jlgo.setFont(new Font("lol", Font.PLAIN, 50));
        jlgo.setHorizontalAlignment(JLabel.CENTER);
        jlgo.setForeground(Color.YELLOW);

        pgo.add(jlgo);

        lScore.setForeground(Color.YELLOW);
        lScore.setFont(new Font("serif", Font.PLAIN,30));
        lScore.setHorizontalAlignment(JLabel.CENTER);

        pgo.add(lScore);

        pBoard.add(pgo, BorderLayout.CENTER);

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
        lLevel.setText("Level " + game.getLevel());
        lScore.setText("Score " + game.getScore());

        switch(game.getLives()) {
            case 0:
                lifeThree.setNothing(true);
                break;
            case 1:
                lifeTwo.setNothing(true);
                break;
            case 2:
                lifeThree.setNothing(true);
                break;
        }
        pLives.repaint();
        pLives.revalidate();
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
