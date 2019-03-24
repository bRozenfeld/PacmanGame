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

    private ArrayList<GraphicCell> listCell; // all the graphic cells
    private ArrayList<GraphicCell> ghostList; // the ghost list

    private JPanel pBoard; // panel of the game
    private JPanel pInfo;
    private JPanel pStartStop;
    private JPanel pBestScore;
    private JPanel  pProducers;
    private JPanel pOver; // panel showing when the player lose

    private JButton bSTART;

    private JLabel lBestScore;
    private JLabel  lLives;
    private JLabel  lScore;
    private JLabel lLevel;


    public GraphicGame(String title, int x, int y, int w, int h, Game game) {
        super(title);
        this.setBounds(x,y,w,h);

        this.game = game;

        this.initComponents(game);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        this.isRunning = true;


        this.setFocusable(true);
    }

    /************* Initialisation part *********************/
    private void initComponents(Game g) {
        //this.initBoard(g);
        //this.add(this.pBoard, BorderLayout.CENTER);
        this.updateBoard();

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
        this.ghostList = new ArrayList<>();
        this.listCell = new ArrayList<>();
        this.pBoard = new JPanel(new GridLayout(g.getBoard().length, g.getBoard()[0].length));

        for(Cell c : g.getCellList()) {
            GraphicCell gc = new GraphicCell(c);
            if(c.getIsWall() == true) {
                gc.setBackground(Color.BLUE);
            }
            else {
                gc.setBackground(Color.BLACK);
                if (gc.getCell().getStaticElement() instanceof Gomme) {
                    Gomme gum = (Gomme) gc.getCell().getStaticElement();
                    GraphicGomme gg = new GraphicGomme(gum);
                    gc.add(gg);
                }
                if (!gc.getCell().getMovableElementList().isEmpty()) {
                    MovableElement me = gc.getCell().getMovableElementList().get(0);
                    if (me instanceof Pacman) {
                        GraphicPacman gp = new GraphicPacman(g.getPacman());
                        gc.add(gp);
                    } else if(me instanceof  Ghost){
                        Ghost ghost = (Ghost) me;
                        GraphicGhost gg = new GraphicGhost(ghost);
                        gc.add(gg);
                        ghostList.add(gc);
                    }
                }
                gc.setBackground(Color.BLACK);
            }
            pBoard.add(gc);
            this.listCell.add(gc);
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
        return pSouth;
    }

    private void initPanelOver() {

        this.remove(pBoard);
        this.remove(pInfo);
        this.remove(pStartStop);

        pOver = new JPanel();
        pOver.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel jlose = new JLabel("You lose !");
        pOver.add(jlose);

        JLabel lScore = new JLabel("Score : " + game.getScore());
        pOver.add(lScore);


        this.add(pOver);
        this.repaint();
        this.revalidate();
    }

    /************ Update Part *******************************/
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
        if(game.isOver()) initPanelOver();
        else {
            this.updateBoard();
            this.updateInfo();
        }
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
                updateGame();
                render();
            }
        };
        new Timer(delay, taskPerformer).start();
    }

    private void doNothing(){}

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



}
