package model;


import java.io.*;
import java.util.ArrayList;
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
     * represent the bonus objects that appear during a game
     */
    private Bonus bonus;

    /**
     * list of ghost containing the four ghost in the game
     */
    private ArrayList<Ghost> ghostList;

    /**
     * int representing the gomme remaining in the game
     */
    int numberGommes;

    /**
     * int corresponding of the ghost eaten by pacman when they are vulnerable
     */
    private int ghostEaten;

    private boolean isOver;

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
    private int[][] board = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,4,1,1,1,1,0,0,0,1,1,1,1,4,0},
            {0,1,0,1,0,1,1,0,1,1,0,1,0,1,0},
            {0,1,0,2,0,0,1,0,1,0,0,2,0,1,0},
            {0,1,0,1,1,0,1,0,1,0,1,1,0,1,0},
            {0,1,0,0,1,1,1,1,1,1,1,0,0,1,0},
            {0,1,1,1,1,0,0,1,0,0,1,1,1,1,0},
            {0,1,0,0,1,0,1,1,1,0,1,0,0,1,0},
            {0,1,0,1,1,0,0,0,0,0,1,1,0,1,0},
            {0,1,1,1,0,0,0,5,0,0,0,1,1,1,0},
            {0,1,0,1,0,1,1,1,1,1,0,1,0,1,0},
            {0,1,0,2,1,1,0,1,0,1,1,2,0,1,0},
            {0,1,0,0,1,0,0,1,0,0,1,0,0,1,0},
            {0,4,1,1,1,1,1,3,1,1,1,1,1,4,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}

    };

    public Game(int[][] board) {
        this.cellList = new ArrayList<>();
        this.ghostList = new ArrayList<>();
        this.initGame(board);
        this.level = 1;
        this.lives = 3;
        this.score = 0;
        this.ghostEaten = 0;
        this.bestScore = this.readBestScore();
        this.board = board;
        this.isOver = false;
    }

    /**
     * Initialize a new Game without passing a board
     * @param pacman : the pacman in the game
     */
    public Game(Pacman pacman,  ArrayList<Cell> cellList, int numberGommes, ArrayList<Ghost> ghostList) {
        this.cellList=cellList;
        this.ghostList=ghostList;
        this.pacman=pacman;
        this.numberGommes = numberGommes;
        this.bestScore=0;
        this.level=1;
        this.lives=3;
        this.score=0;
        this.ghostEaten = 0;
    }


    /**
     * Initialize a new game passing a board as an argument
     * @param pacman
     * @param cellList
     * @param numberGommes
     * @param ghostList
     * @param board
     */
    public Game(Pacman pacman,  ArrayList<Cell> cellList, int numberGommes, ArrayList<Ghost> ghostList,
                int[][] board) {
        this.cellList=cellList;
        this.ghostList=ghostList;
        this.pacman=pacman;
        this.numberGommes = numberGommes;
        this.bestScore=0;
        this.level=1;
        this.lives=3;
        this.score=0;
        this.ghostEaten = 0;
        this.board = board;
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

    /**
     * Move any MovableElement in the given direction
     * @param me : MovableElement to move
     * @param dx : int corresponding movement in the axe x
     * @param dy : int corresponding the movement in the axe y
     * @return boolean true if the move is legit, false otherwise
     */
    public boolean move(MovableElement me, int dx, int dy){
        Cell actualCell = me.getCell();
        Cell futureCell = new Cell(actualCell.getX()+dx, actualCell.getY()+dy, false);

        int ind = this.cellList.indexOf(futureCell);

        // The futur cell is either a wall or invalide x and y
        // It's not in the cell list of the game
        if (ind == -1)
            return false;

        actualCell.removeMovableElement(me);
        futureCell.addMovableElement(me);
        me.setCell(futureCell);

        return true;
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


    /**
     * Move an element by following the cell contained in the stack
     * @param me : MovableElement to move
     * @param stack : Stack<Cell> containing the cells to go
     */
    public void move(MovableElement me, Stack<Cell> stack) {
        while(!stack.empty()){
            Cell c = stack.pop();
            this.moveTo(me, c);
        }
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
     * Initialize a list of move for the ghost
     */
    public void setGhostsMoves() {
        PathFinding pf = new PathFinding();
        for(Ghost g : this.ghostList) {
            g.setCellStack(pf.getWay(cellList, g.getCell(), pacman.getCell()));
        }
    }

    /**
     * Move Pacman into the given direction until he meets a wall
     * @param direction
     */
    public void setPacmanMoves(Direction direction) {
        Stack<Cell> stack = new Stack<>();
        Cell c = getNextCell(pacman.getCell(), direction);
        if (c.getIsWall()==false && c != null) {
            stack.push(c);
        }
        pacman.setCellStack(stack);
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
                    if(c.getX() == cell.getX()+1 && c.getY() == cell.getY())
                        res = cell;
                }
                break;
            case Right:
                for(Cell cell : this.cellList) {
                    if(c.getX() == cell.getX()-1 && c.getY() == cell.getY())
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
     * Restore the initiale state of the game when player pass a level
     */
    public void nextLevel() {
        // send each ghost to its initial position
        for(Ghost g : this.ghostList) {
            g.getCell().removeMovableElement(g);
            g.getBeginCell().addMovableElement(g);
            g.setCell(g.getBeginCell());
        }

        // for each cell, restore its StaticElement at the beginning
        for(Cell c : this.cellList) {
            c.addStaticElement(c.getStaticElementAtStart());
        }

        // restore pacman to its initial position
        this.pacman.getCell().removeMovableElement(this.pacman);
        this.pacman.getBeginCell().addMovableElement(this.pacman);
        this.pacman.setCell(this.pacman.getBeginCell());

        this.level++;
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
        }

        // restore pacman to its initial position
        this.pacman.getCell().removeMovableElement(this.pacman);
        this.pacman.getBeginCell().addMovableElement(this.pacman);
        this.pacman.setCell(this.pacman.getBeginCell());

    }


    /**
     * 0 : wall
     * 1 : gomme
     * 2 : super gomme
     * 3 : pacman
     * 4 : ghost
     * 5 : bonus
     */
    private void initGame(int [][] board) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 0) {
                    Cell c = new Cell(j, i, true);
                    this.cellList.add(c);
                }
                else if (board[i][j] == 1) {
                    Gomme g = new Gomme(10, false);
                    Cell c = new Cell(j,i,false,g,g);
                    this.cellList.add(c);
                    this.numberGommes++;
                }
                else if (board[i][j] == 2) {
                    Gomme g = new Gomme(50, true);
                    Cell c = new Cell(j,i,false,g,g);
                    this.cellList.add(c);
                    this.numberGommes++;
                }
                else if(board[i][j] == 3) {
                    Cell c = new Cell(j,i,false);
                    this.pacman = new Pacman(c,c);
                    c.addMovableElement(pacman);
                    this.cellList.add(c);
                }
                else if(board[i][j] == 4) {
                    Cell c = new Cell(j,i,false);
                    Ghost g = new Ghost(c,GhostColor.Orange,c);
                    c.addMovableElement(g);
                    this.cellList.add(c);
                    this.ghostList.add(g);
                }
                else if(board[i][j] == 5) {
                    Cell c = new Cell(i,j,false);
                    Bonus bonus = new Bonus(100, TypeBonus.Cherry, c);
                    this.cellList.add(c);
                }
            }
        }
    }

    /**
     * Initialize the bonus for the corresponding level
     * @param c : Cell where the Bonus should appear
     */
    private void setBonus(Cell c) {
        if(this.level == 2) {
            this.bonus = new Bonus(300, TypeBonus.Strawberry, c);
        }
        else if (this.level == 3 || this.level == 4) {
            this.bonus = new Bonus(500, TypeBonus.Orange, c);
        }
        else if (this.level == 5 || this.level == 6) {
            this.bonus = new Bonus(700, TypeBonus.Orange, c);
        }
        else if (this.level == 7 || this.level == 8) {
            this.bonus = new Bonus(1000, TypeBonus.Melon, c);
        }
        else if(this.level == 9 || this.level == 10) {
            this.bonus = new Bonus(2000, TypeBonus.Galaxian, c);
        }
        else if(this.level == 11 || this.level == 12) {
            this.bonus = new Bonus(3000, TypeBonus.Bell, c);
        }
        else if(this.level >= 13) {
            this.bonus = new Bonus(5000, TypeBonus.Key, c);
        }
    }

    private void pacmanMeetGhost(Ghost g) {
        // pacman eat the ghost
        if (g.getIsVulnerable() == true) {
            this.ghostEaten++;
            this.score += 100 * (int)Math.pow(2,this.ghostEaten);
            g.setIsRegenerating(true);
            this.moveTo(g, g.getBeginCell());
        }
        // pacman is eaten by the ghost
        else {
            this.lives--;
            pacman.setIsEaten(true);
            if(this.lives == 0) {
                this.gameOver();
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
                    ghost.setIsVulnerable(true);
                }
            }
        }
        else if(se instanceof Bonus){
            Bonus b = (Bonus) se;
            b.getCell().removeStaticElement(se);
        }
        this.score += se.getValue();

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
     * Display the position of all the Element in the game
     */
    public void displayElements() {
        for(Ghost g : ghostList) {
            System.out.println("Ghost: " + g.getCell());
        }

        System.out.println("Pacman: " + pacman.getCell());

        for(Cell c : cellList) {
            if(c.getStaticElement() != null)
                System.out.println("Gum : " + c);
        }
    }

}
