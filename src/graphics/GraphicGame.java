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
    private JPanel pStartStop;
    private JPanel pBestScore;
    private JPanel pProducers;
    private JPanel pOver; // panel showing when the player lose

    private JButton bStart;

    private JLabel lBestScore;
    private JLabel  lLives;
    private JLabel  lScore;
    private JLabel lLevel;


    public GraphicGame(String title, int x, int y, int w, int h, Game game) {
        super(title);
        this.setBounds(x,y,w,h);

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

        JPanel pSouth = this.initiPanelSouth(game);
        this.add(pSouth, BorderLayout.SOUTH);

        this.initPanelBestScore(game);
        this.add(this.pBestScore, BorderLayout.NORTH);

        this.addKeyListener(new BoardListener());
    }

    /**
     * Initialise
     */
    private void initPanelStopStart()  {
        pStartStop=new JPanel();
        pStartStop.setLayout(new BoxLayout(pStartStop,BoxLayout.X_AXIS));

        bStart = new JButton("START");
        bStart.addMouseListener(new RunListener());
        pStartStop.add(bStart);
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
        pSouth.setBackground(Color.BLACK);
        return pSouth;
    }

    private void initPanelOver() {

        System.out.println(this.getComponentCount());
        pBoard.removeAll();
        this.remove(pBoard);
        this.remove(pStartStop);
        this.remove(pInfo);
        this.remove(pBestScore);
        System.out.println(this.getComponentCount());
        this.repaint();
        this.revalidate();

        pOver = new JPanel();
        pOver.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel jlose = new JLabel("You lose !");
        pOver.add(jlose);

        JLabel lScore = new JLabel("Score : " + game.getScore());
        pOver.add(lScore);

        this.add(pOver, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();

    }

    /************ Update Part *******************************/
    private void updatebStart(){
        if(isRunning) {
            bStart.setText("Play");
            isRunning = false;
            timer.stop();
        }
        else {
            bStart.setText("Stop");
            isRunning = true;
            timer.restart();
        }

        System.out.println(this.isFocused());


    }

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

    private void render() {
        this.updateBoard();
        this.updateInfo();
    }

    private void updateGhost() {
        for (Ghost g : this.game.getGhostList()) {
            g.move();
            if(g.getIsRegenerating() == true)
                g.move(); // he will go 2 times faster
            else if (g.getCellStack().size()==0) {
                switch (g.getName()) {
                    case Inky:
                        game.setInkyMoves(g);
                        break;
                    case Pinky:
                        game.setPinkyMoves(g);
                        break;
                    case Blinky:
                        game.setBlinkyMoves(g);
                        break;
                    case Clyde:
                        game.setClydeMoves(g);
                        break;
                }
            }
        }
        //this.displayGhostPosition();
    }


    private void updateGame() {
        game.checkGhost();

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

                if(isRunning) {
                    updateGame();
                    render();
                }

                if(game.isOver()) {
                    initPanelOver();
                    timer.stop();
                }
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();


    }



    /************ Inner class Part ***************************/
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

    private void initKeyBindings() {
       // pBoard.getInputMap().put(KeyStroke.getKeyStroke("UP"), );
    }
}
