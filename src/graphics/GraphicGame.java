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
    private JPanel pOver; // panel showing when the player lose

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

    private void updateInfo() {
        lLevel.setText("Level: " + game.getLevel());
        lLives.setText("Lives: "+ game.getLives());
        lScore.setText("Score: " + game.getScore());
    }

    private void render() {
        if(game.isOver()) initPanelOver();
        else {
            this.updateBoardG();
            this.updateInfo();
        }
    }

    private void updateGhost(int timer) {
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


    private void updateGame(int i) {
        game.checkPacman();
        game.checkGhost();

        if(game.getPacman().isEaten() == false) {
            game.getPacman().move();
            updateGhost(i);

        }
        else { //Il faudrait rajouter du temps avant de repasser à true
            game.rebuildLevel();
            game.getPacman().setIsEaten(false);
        }
    }

    /************* Loop Game Part **************************/
    public void run() throws InterruptedException {
        int i = 0;
        game.setGhostsMoves();

        while(this.isRunning && !game.isOver()) {
            // Informations
            System.out.println("Tour :" + i);
            //this.displayPacmanPosition();

            this.updateGame(i);
            this.render();

            try {
                Thread.sleep(1000);
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


    /************ methodes de merde *****************/
    private void displayGhostPosition() {
        for(Ghost g : this.game.getGhostList()) {
            System.out.println("Ghost: " + g.getCell());
        }
    }

    private void displayPacmanPosition() {
        Cell c = game.getPacman().getCell();
        System.out.println("Pacman: " + c + game.getPacman());
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
                        gc.setBackground(Color.GREEN);
                    } else if (gum.getIsSuper() == true){
                        gc.setBackground(Color.GRAY);
                    }
                } else if(gc.getCell().getStaticElement() instanceof Bonus) {
                    gc.setBackground(Color.WHITE);
                }
                if (!gc.getCell().getMovableElementList().isEmpty()) {
                    MovableElement me = gc.getCell().getMovableElementList().get(0);
                    if (me instanceof Pacman) {
                        gc.setBackground(Color.YELLOW);
                    } else if(me instanceof  Ghost){
                        Ghost ghost = (Ghost) me;
                        switch(ghost.getName()) {
                            case Pinky:
                                gc.setBackground(Color.PINK);
                                break;
                            case Inky:
                                gc.setBackground(Color.CYAN);
                                break;
                            case Clyde:
                                gc.setBackground(Color.ORANGE);
                                break;
                            case Blinky:
                                gc.setBackground(Color.RED);
                                break;
                        }

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
                    if (gc.getCell().getStaticElement() instanceof Gomme) {
                        Gomme gum = (Gomme) gc.getCell().getStaticElement();
                        if (gum.getIsSuper() == false) {
                            gc.setBackground(Color.GREEN);
                        } else if (gum.getIsSuper() == true) {
                            gc.setBackground(Color.GRAY);
                        }
                    }
                    else if(gc.getCell().getStaticElement() instanceof Bonus) {
                        gc.setBackground(Color.WHITE);
                    }
                }
                if (!gc.getCell().getMovableElementList().isEmpty()) {
                    for (MovableElement me : gc.getCell().getMovableElementList())
                        if (me instanceof Pacman) {
                            gc.setBackground(Color.YELLOW);
                        } else if(me instanceof  Ghost){
                            Ghost ghost = (Ghost) me;
                            if(ghost.getVulnerabilityTime() > 0) {
                                gc.setBackground(Color.BLUE);
                            }
                            else if(ghost.getIsRegenerating() == true) {
                                gc.setBackground(Color.WHITE);
                            }
                            else {
                                switch (ghost.getName()) {
                                    case Pinky:
                                        gc.setBackground(Color.PINK);
                                        break;
                                    case Inky:
                                        gc.setBackground(Color.CYAN);
                                        break;
                                    case Clyde:
                                        gc.setBackground(Color.ORANGE);
                                        break;
                                    case Blinky:
                                        gc.setBackground(Color.RED);
                                        break;
                                }
                            }
                        }
                }
            }
            gc.repaint();
            gc.revalidate();
        }
    }
}
