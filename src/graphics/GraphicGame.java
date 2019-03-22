package graphics;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Class representing the main frame of the game
 */
public class GraphicGame extends JFrame {

    private Game game;
    private boolean isRunning;

    private ArrayList<GraphicCell> listCell;

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
        //this.initBoardBis(game);
        //this.initBoard(game);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        this.isRunning = true;
        this.game = game;

        this.setFocusable(true);
    }

    /************* Initialisation part *********************/
    private void initComponents(Game g) {
        //this.initBoard(g);
        this.initBoardBis(g);
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
        this.listCell = new ArrayList<>();
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

    private void render() {
        this.updateBoardG();

    }

    private void updateGame() {
        game.setGhostsMoves();
        game.checkPacman();
        if(!game.getPacman().getCellStack().empty()) game.getPacman().move();
        for(Ghost g : this.game.getGhostList()) {
            g.move();
        }

    }

    /************* Loop Game Part **************************/
    public void run() throws InterruptedException {
        int i = 0;

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0/60.0; // 60FPS

        long lastFpsTime = 0;
        long fps = 0;

        while(this.isRunning) {
            // Informations
            System.out.println("Tour :" + i);
            this.displayPacmanPosition();


            long now = System.nanoTime();
            long updateLength = now - lastTime;
            lastTime = now;
            delta = updateLength / ns;

            // Update the frame counter
            lastFpsTime += updateLength;
            fps++;

            //Update our FPS counter if a second has passed since
            //we last update
            if(lastFpsTime >= 1000000000) {
                System.out.println("FPS: " + fps);
                lastFpsTime = 0;
                fps = 0;
            }

            this.updateGame();
            this.render();

            try {
                Thread.sleep(1000);
                //Thread.sleep((long) ((lastTime-System.nanoTime() + ns) / 1000000));
            } catch(Exception e){}

            i++;
        }
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
            System.out.println(key);
            switch (key) {
                case KeyEvent.VK_LEFT:
                    //GraphicGame.this.game.setPacmanMoves(Direction.Left);
                    GraphicGame.this.game.movePacman(Direction.Left);
                    break;
                case KeyEvent.VK_RIGHT:
                    //GraphicGame.this.game.setPacmanMoves(Direction.Right);
                    GraphicGame.this.game.movePacman(Direction.Right);
                    break;
                case KeyEvent.VK_UP:
                    //GraphicGame.this.game.setPacmanMoves(Direction.Up);
                    GraphicGame.this.game.movePacman(Direction.Up);
                    break;
                case KeyEvent.VK_DOWN:
                    //GraphicGame.this.game.setPacmanMoves(Direction.Down);
                    GraphicGame.this.game.movePacman(Direction.Down);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


    /************ methodes de merde *****************/
    private void displayGhostPosition() {
        for(Ghost g : this.game.getGhostList()) {
            System.out.println("Ghost: " + g.getCell());
        }
    }

    private void displayPacmanPosition() {
        Cell c = game.getPacman().getCell();
        System.out.println("Pacman: " + c);
        if(c.getStaticElement() == null) {
            System.out.println("Void");
        }
        else {
            System.out.println(c.getStaticElement());
        }
    }

    private void initBoardBis(Game g) {
        this.listCell = new ArrayList<>();
        this.pBoard = new JPanel();
        this.pBoard.setLayout(new GridLayout(g.getBoard().length, g.getBoard()[0].length));
        this.pBoard.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        for (Cell c : g.getCellList()) {
            GraphicCell gc = new GraphicCell(c);
            gc.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            if (c.getIsWall() == true) {
                gc.setBackground(Color.BLUE);
            } else {
                if (gc.getCell().getStaticElement() instanceof Gomme) {
                    Gomme gum = (Gomme) gc.getCell().getStaticElement();
                    if (gum.getIsSuper() == false) {
                        gc.setBackground(Color.ORANGE);
                    } else {
                        gc.setBackground(Color.PINK);
                    }
                }
                if (!gc.getCell().getMovableElementList().isEmpty()) {
                    MovableElement me = gc.getCell().getMovableElementList().get(0);
                    if (me instanceof Pacman) {
                        gc.setBackground(Color.YELLOW);
                    } else {
                        gc.setBackground(Color.RED);
                    }
                }
            }
            pBoard.add(gc);
            this.listCell.add(gc);
        }
    }

    private void updateBoardG() {
        for(GraphicCell gc : listCell) {
            gc.removeAll();
            if(gc.getCell().getIsWall()==true) gc.setBackground(Color.BLUE);
            else {
                if (gc.getCell().getStaticElement() == null && gc.getCell().getMovableElementList().isEmpty()) {
                    gc.setBackground(Color.BLACK);
                }
                if(gc.getCell().getStaticElement() != null) {
                    gc.setBackground(Color.ORANGE);
                }
                if (!gc.getCell().getMovableElementList().isEmpty()) {
                    for (MovableElement me : gc.getCell().getMovableElementList())
                        if (me instanceof Pacman) {
                            gc.setBackground(Color.YELLOW);
                        } else {
                            gc.setBackground(Color.RED);
                        }
                }
            }
            gc.repaint();
            gc.revalidate();
        }
    }
}
