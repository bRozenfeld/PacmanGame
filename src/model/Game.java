package model;


import java.io.*;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Class representing the game
 * @inv {@code bestScore >=0}
 * @inv {@code lives>=0 && lives<=3}
 * @inv {@code score>=0}
 * @inv {@code level>0}
 **/
public class Game {

    /**
     * 2D array representing the board
     * 0 : wall
     * 1 : cell with gomme
     * 2 : cell with super gomme
     * 3 : starting cell for pacman
     * 4 : starting cell for a ghost
     * 5 : cell where bonus will appear
     *
     * P e à supprimer
     */
    private static final int WALL = 0;
    private static final int GUM = 1;
    private static final int SUPER_GOMME = 2;
    private static final int PACMAN = 3;
    private static final int VOID_CELL = 4;
    private static final int BONUS = 5;
    private static final int PINKY = 6;
    private static final int INKY = 7;
    private static final int CLYDE = 8;
    private static final int BLINKY = 9;

    /**
     * int representing the best score
     */
    private int bestScore;

    /**
     * int representing the remaining life of the player
     */
    private int lives;

    /**
     * int representing the level of the game
     */
    private int level;

    /**
     * int representing the actual player's score
     */
    private int score;

    /**
     * list of cell containing all the cell in the game
     */
    private ArrayList<Cell> cellList;

    /**
     * represent the pacman in the game
     */
    private Pacman pacman;

    /**
     * represent the bonus objects that appear twice per level
     */
    private Bonus bonus;

    /**
     * Cell where the bonus will appear. It's always in the same cell
     */
    private Cell bonusCell;

    /**
     * list of ghost containing the four ghost in the game
     */
    private ArrayList<Ghost> ghostList;

    /**
     * int representing the gum remaining in the game
     */
    int numberGommes;

    /**
     * int corresponding of the number of ghost eaten by Pacman
     * when they are vulnerable.
     * Each time pacman eat a ghost, his score increase by 2^ghostEaten
     */
    private int ghostEaten;

    /**
     * Indicate if the game is over or not
     */
    private boolean isOver;

    /**
     * int representing the remaining time when ghost become blue
     * Its value depend on the level of the game
     */
    private int ghostBlueTime;

    /**
     * int representing the remaining time when their vulnerability is about to go
     */
    private int flashBeforeBlueTimeEnd;

    /**
     * int representing how many time a new bonus will appear
     * in this level
     * A bonus appear twice per level
     */
    private int bonusRemaining;


    private int gumsBeforeFirstBonus;
    private int gumsBeforeSecondBonus;

    private boolean isNextLevel;



    /**
     * 2D array representing the board
     * 0 : wall
     * 1 : cell with gomme
     * 2 : cell with super gomme
     * 3 : starting cell for pacman
     * 4 : starting cell for a ghost
     * 5 : cell where bonus will appear
     *
     * P e à supprimer
     */
    private int[][] board;

    public Game(int[][] board) {

        this.board = board;
        this.level = 1;
        this.lives = 3;
        this.score = 0;
        this.ghostEaten = 0;
        this.bestScore = this.readBestScore();
        this.board = board;
        this.isOver = false;
        this.isNextLevel = false;
        this.bonusRemaining = 2;

        this.initBoard(); // init the board
        this.setGhostTime(); // init the ghost parameters
        this.initBonus(); // init the bonus

        this.gumsBeforeFirstBonus = numberGommes / 3;
        this.gumsBeforeSecondBonus = numberGommes * 2 / 3;

    }

    public Bonus getBonus() {
        return bonus;
    }

    public int getBestScore() { return bestScore; }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public Pacman getPacman() {
        return this.pacman;
    }

    public ArrayList<Cell> getCellList() {
        return this.cellList;
    }

    public ArrayList<Ghost> getGhostList() {
        return this.ghostList;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public boolean isNextLevel() {
        return isNextLevel;
    }

    public void setNextLevel(boolean nextLevel) {
        isNextLevel = nextLevel;
    }

    /**
     * Check the state of the ghosts in the game
     * That means checking their vulnerability or
     * If they are regenerating or not
     */
    public void checkGhost() {
        for(Ghost g : ghostList) {
            if(g.getVulnerabilityTime() > 0) {
                g.setVulnerabilityTime(g.getVulnerabilityTime()-1);
            }
            g.checkIsRegenerating();
        }
    }

    /**
     * Check the cell containing pacman
     * If the cell contain other element then call the methods to handle it
     */
    public void checkPacman() {
        // Check if there is a static element (ie a gomme or a bonus) in the pacman cell
        StaticElement se = this.pacman.getCell().getStaticElement();
        if(se != null) {
            this.eatStaticElement(se);
        }

        // Check if there is a movable element (ie a ghost) in the pacman cell
        ArrayList<MovableElement> mel = this.pacman.getCell().getMovableElementList();
        if(!mel.isEmpty()) { // ghost in there
            for (MovableElement me : mel) {
                if(me instanceof Ghost) {
                    Ghost g = (Ghost) me;
                    this.pacmanMeetGhost(g);
                    break;
                }
            }
        }
    }

    /**
     * Move the movableElement into the given cell
     * @param me : MovableElement to move
     * @param futurCell : Cell to go
     */
    public void moveTo(MovableElement me, Cell futurCell) {
        Cell actualCell = me.getCell();
        actualCell.removeMovableElement(me);
        futurCell.addMovableElement(me);
        me.setCell(futurCell);
    }


    public void movePacman(Direction dir) {
        Cell c = getNextCell(pacman.getCell(), dir);
        if (c.getIsWall()==false) {
            c.addMovableElement(pacman);
            pacman.getCell().removeMovableElement(pacman);
            pacman.setCell(c);
        }
    }

    /**
     * Initialize a list of move for each ghost
     */
    public void setGhostsMoves() {
        for(Ghost g : this.ghostList) {
            switch(g.getName()) {
                case Blinky:
                    setBlinkyMoves(g);
                    break;
                case Inky:
                    setInkyMoves(g);
                    break;
                case Clyde:
                    setClydeMoves(g);
                    break;
                case Pinky:
                    setPinkyMoves(g);
                    break;
            }
        }
    }

    /**
     * Define list of movement to pinky
     * He aims for a position in front of pacman mouth
     * @param g : Ghost representing Pinky
     */
    public void setPinkyMoves(Ghost g) {
        Cell c = (Cell) pacman.getCellQueue().peekFirst();
        if(c != null) {
            g.setMoves(cellList, c, board);
        }
        else {
            g.setMoves(cellList, pacman.getCell(), board);
        }
    }

    /**
     * Define stack of cell where the ghost has to go
     * Clyde behavior is totally random on the map
     * @param g : Ghost g representing Clyde
     */
    public void setClydeMoves(Ghost g) {
        PathFinding pf = new PathFinding();
        Random r = new Random();
        boolean find = false;
        while(!find) {
            int rand = r.nextInt(cellList.size());
            Cell c = cellList.get(rand);
            if(!c.getIsWall()) {
                g.setMoves(cellList, c, board);
                find = true;
            }
        }
    }

    /**
     * Define the stack of cell where Inky has to move
     * Sometimes he runs toward pacman, other times he run away
     * @param g : Ghost Inky
     */
    public void setInkyMoves(Ghost g) {
        Random r = new Random();
        int rand = r.nextInt(2); // random int between 0 and 1
        if(rand == 0) { // run toward pacman
            g.setMoves(cellList, pacman.getCell(), board);
        }
        else { // run away from pacman
            boolean find = false;
            for(Cell c : cellList) {
                if(c.getIsWall() != false) {
                    int dx = Math.abs(c.getX() - pacman.getCell().getX());
                    int dy = Math.abs(c.getY() - pacman.getCell().getY());
                    if(dx >= 5 && dy >= 5) {
                        g.setMoves(cellList, c, board);
                        find = true;
                        break;
                    }
                }
            }
            while(!find) { // in case no cell is found, pick a random one
                rand = r.nextInt(cellList.size());
                Cell cell = cellList.get(rand);
                if(cell.getIsWall() != false) {
                    g.setMoves(cellList, cell, board);
                    find = true;
                }
            }
        }
    }

    /**
     * Define the stack list where blinky has to move
     * Blinky is just following pacman all time
     */
    public void setBlinkyMoves(Ghost g) {
        g.setMoves(cellList, pacman.getCell(), board);
    }

    /**
     * Move Pacman into the pacman direction until he meets a wall
     */
    public void setPacmanMoves() {
        ArrayDeque ad = new ArrayDeque();
        Cell c = getNextCell(pacman.getCell(), pacman.getDirection());
        while (c != null  && c.getIsWall() == false) {
            ad.offerLast(c);
            c = getNextCell(c, pacman.getDirection());
        }
        pacman.setCellQueue(ad);

    }

    private Cell getNextCell(Cell c, Direction dir) {
        Cell res = null;
        switch (dir) {
            case Up:
                for(Cell cell : this.cellList) {
                    if(c.getX() == cell.getX()&& c.getY() == cell.getY()+1)
                        res = cell;
                }
                break;
            case Down:
                for(Cell cell : this.cellList) {
                    if(c.getX() == cell.getX() && c.getY() == cell.getY()-1)
                        res = cell;
                }
                break;
            case Left:
                for(Cell cell : this.cellList) {
                    if((c.getX() == cell.getX()+1 ||  (Math.abs(cell.getX() - c.getX()) == board[0].length-1
                            && cell.getX() > c.getX())) && c.getY() == cell.getY())
                        res = cell;
                }
                break;
            case Right:
                for(Cell cell : this.cellList) {
                    if((c.getX() == cell.getX()-1 ||  (Math.abs(cell.getX() - c.getX()) == board[0].length-1
                            && cell.getX() < c.getX())) && c.getY() == cell.getY())
                        res = cell;
                }
                break;
        }
        return res;
    }

    /**
     * Compare the actual score with the best score when the game is over
     * Then call a method the write the best score in a file
     * @return int : the maximum between score and bestscore
     */
    public void gameOver(){
        if(this.score > this.bestScore) {
            this.bestScore = this.score;
        }
        this.writeBestScore();

        this.isOver = true;
    }

    /**
     * Increase the level of a player and
     * Restore the initiale state of the game
     * But adjust the parameters depending of the level
     */
    public void nextLevel() {
        level++;

        this.initBoard();
        this.initBonus();
        this.setGhostTime();
    }

    /**
     * Rebuild the game when pacman is eaten by a ghost
     */
    public void rebuildLevel() {
        // send each ghost to its initial position
        for(Ghost g : this.ghostList) {
            g.getCell().removeMovableElement(g);
            g.getBeginCell().addMovableElement(g);
            g.setCell(g.getBeginCell());
            g.setCellStack(new Stack<>());
            g.setIsRegenerating(false);
            g.setVulnerabilityTime(0);
        }

        // restore pacman to its initial position
        this.pacman.getCell().removeMovableElement(this.pacman);
        this.pacman.getBeginCell().addMovableElement(this.pacman);
        this.pacman.setCell(this.pacman.getBeginCell());
        this.pacman.setCellQueue(new ArrayDeque());

    }

    public Cell getBonusCell() {
        return bonusCell;
    }


    /**
     * Initialise the bonus corresponding to the level of the game
     */
    public void initBonus() {
        if(level == 1) {
            bonus = new Bonus(100, TypeBonus.Cherry);
        } else if (level == 2) {
            bonus = new Bonus(300, TypeBonus.Strawberry);
        } else if (level == 3 || level == 4) {
            bonus = new Bonus(500, TypeBonus.Orange);
        } else if (level == 5 || level == 6) {
            bonus = new Bonus(700, TypeBonus.Orange);
        } else if (level == 7 || level == 8) {
            bonus = new Bonus(1000, TypeBonus.Melon);
        } else if (level == 9 || level == 10) {
            bonus = new Bonus(2000, TypeBonus.Galaxian);
        } else if (level == 11 || level == 12) {
            bonus = new Bonus(3000, TypeBonus.Bell);
        } else if (level >= 13) {
            bonus = new Bonus(5000, TypeBonus.Key);
        }
    }

    /**
     *
     * @param g
     * @inv ghostEaten<5
     */
    private void pacmanMeetGhost(Ghost g) {
        // pacman eat the ghost
        if (g.getVulnerabilityTime() > 0) {
            this.ghostEaten++;
            this.score += 100 * (int)Math.pow(2,this.ghostEaten);
            g.setIsRegenerating(true);
            g.setVulnerabilityTime(0);
            if (this.ghostEaten==4) {
                this.ghostEaten=0;
            }
            g.setRegeneratingMoves(cellList, board);
        }
        // pacman is eaten by the ghost
        else {
            this.lives--;
            if(this.lives == 0) {
                this.gameOver();
            }
            else {
                rebuildLevel();
            }
        }
    }

    /**
     * Increase the actual score when pacman eat a gum by the value of the static element
     * Remove the eaten element from the game
     * If this is a super gomme, then make ghost vulnerable
     * @param se : the element being eaten
     */
    private void eatStaticElement(StaticElement se) {
        if(se instanceof Gomme) {
            this.numberGommes--;
            this.pacman.getCell().removeStaticElement(se);
            Gomme g = (Gomme) se;
            if (g.getIsSuper() == true) {
                for(Ghost ghost : this.ghostList) {
                    ghost.setVulnerabilityTime(ghostBlueTime + flashBeforeBlueTimeEnd);
                }
            }
        }
        else if(se instanceof Bonus){
            Bonus b = (Bonus) se;
            bonusCell.removeStaticElement(se);
        }
        this.score += se.getValue();

        if(this.numberGommes == gumsBeforeFirstBonus || numberGommes == gumsBeforeSecondBonus) {
            if(bonusCell.getStaticElement() == null)
                bonusCell.addStaticElement(bonus);
        }
        // check if there s still gomme left in the game
        if(this.numberGommes == 0)
            this.nextLevel();
    }

    /**
     * Get the best score written in the file bestScore.txt in the res folder
     * @return int : best score
     */
     private int readBestScore() {
         BufferedReader bIn = null;
         String s = null;
         int res = 0;
         try {
             File inputFile = new File("res/bestScore.txt");
             bIn = new BufferedReader((new FileReader(inputFile)));
             s = bIn.readLine();
             res = Integer.parseInt(s);
         } catch(IOException e) { }
         finally {
             if (bIn != null) {
                 try {
                     bIn.close();
                 } catch(IOException ec){}
             }
         }
         return res;
     }


    private void initBoard() {
        this.cellList = new ArrayList<>();
        this.ghostList = new ArrayList<>();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == WALL) {
                    Cell c = new Cell(j, i, true);
                    this.cellList.add(c);
                }
                else if (board[i][j] == GUM) {
                    Gomme g = new Gomme(10, false);
                    Cell c = new Cell(j,i,false,g,g);
                    this.cellList.add(c);
                    this.numberGommes++;
                }
                else if (board[i][j] == SUPER_GOMME) {
                    Gomme g = new Gomme(50, true);
                    Cell c = new Cell(j,i,false,g,g);
                    this.cellList.add(c);
                    this.numberGommes++;
                }
                else if(board[i][j] == PACMAN) {
                    Cell c = new Cell(j,i,false);
                    this.pacman = new Pacman(c,c);
                    c.addMovableElement(pacman);
                    this.cellList.add(c);
                }
                else if(board[i][j] == BLINKY) {
                    Cell c = new Cell(j,i,false);
                    Ghost g = new Ghost(c,GhostName.Blinky, c);
                    c.addMovableElement(g);
                    this.cellList.add(c);
                    this.ghostList.add(g);
                }
                else if(board[i][j] == BONUS) {
                    Cell c = new Cell(j,i,false);
                    this.cellList.add(c);
                    this.bonusCell = c;
                }
                else if(board[i][j] == PINKY) {
                    Cell c = new Cell(j,i,false);
                    Ghost g = new Ghost(c,GhostName.Pinky, c);
                    c.addMovableElement(g);
                    this.cellList.add(c);
                    this.ghostList.add(g);
                }
                else if(board[i][j] == INKY) {
                    Cell c = new Cell(j,i,false);
                    Ghost g = new Ghost(c,GhostName.Inky, c);
                    c.addMovableElement(g);
                    this.cellList.add(c);
                    this.ghostList.add(g);
                }
                else if(board[i][j] == CLYDE) {
                    Cell c = new Cell(j,i,false);
                    Ghost g = new Ghost(c,GhostName.Clyde, c);
                    c.addMovableElement(g);
                    this.cellList.add(c);
                    this.ghostList.add(g);
                }
                else if(board[i][j] == VOID_CELL) {
                    Cell c = new Cell(j,i, false);
                    this.cellList.add(c);
                }
            }
        }
     }

    /**
     * Write the attribute best score in a file bestScore.txt in the folder res
     * If the file doesn't exist, create a new file
     */
    private void writeBestScore() {
         BufferedWriter bOut = null;
         try {
             File inputFile = new File("res/bestScore.txt");
             inputFile.createNewFile();
             bOut = new BufferedWriter((new FileWriter(inputFile)));
             bOut.write("" +this.bestScore);
         } catch(IOException e) {}
         finally {
             if(bOut != null) {
                 try {
                     bOut.close();
                 } catch(IOException ec) {}
             }
         }
     }

    /**
     * Set the bluetime for ghost when they are vulnerable
     * And the time before they will stop being vulnerable
     */
    private void setGhostTime() {
        if (level == 1) {
            ghostBlueTime = 6;
            flashBeforeBlueTimeEnd = 5;
        }
        else if(level == 2) {
            ghostBlueTime = 5;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 3) {
            ghostBlueTime = 4;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 4) {
            ghostBlueTime = 3;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 5) {
            ghostBlueTime = 2;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 6) {
            ghostBlueTime = 5;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 7) {
            ghostBlueTime = 5;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 8) {
            ghostBlueTime = 2;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 9) {
            ghostBlueTime = 1;
            flashBeforeBlueTimeEnd = 3;
        }
        else if (level == 10) {
            ghostBlueTime = 5;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 11) {
            ghostBlueTime = 2;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 12) {
            ghostBlueTime = 1;
            flashBeforeBlueTimeEnd = 3;
        }
        else if (level == 13) {
            ghostBlueTime = 1;
            flashBeforeBlueTimeEnd = 3;
        }
        else if (level == 14) {
            ghostBlueTime = 3;
            flashBeforeBlueTimeEnd = 5;
        }
        else if (level == 15) {
            ghostBlueTime = 1;
            flashBeforeBlueTimeEnd = 3;
        }
        else if (level == 16) {
            ghostBlueTime = 1;
            flashBeforeBlueTimeEnd = 3;
        }
        else if (level == 17) {
            ghostBlueTime = 0;
            flashBeforeBlueTimeEnd = 0;
        }
        else if (level == 18) {
            ghostBlueTime = 1;
            flashBeforeBlueTimeEnd = 3;
        }
        else if (level >= 19) {
            ghostBlueTime = 0;
            flashBeforeBlueTimeEnd = 0;
        }

    }


}
