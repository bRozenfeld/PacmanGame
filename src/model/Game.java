package model;


import java.io.*;
import java.util.ArrayList;

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

    /**
     * Initialize a new Game
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

    public Game(int level,int lives,int score,Pacman pacman, ArrayList<Cell> cellList, int numberGommes,
                ArrayList<Ghost> ghostList) {
        this.cellList=cellList;
        this.ghostList=ghostList;
        this.numberGommes = numberGommes;
        this.pacman=pacman;
        this.bestScore = this.getBestScore();
        this.level=level;
        this.score=score;
        this.lives=lives;
        this.ghostEaten = 0;
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
                Ghost g = (Ghost) me;
                // Pacman eat the ghost
                if (g.getIsVulnerable() == true) {
                    this.ghostEaten++;
                    this.score += 100 * (int)Math.pow(2,this.ghostEaten);
                    g.setIsRegenerating(true);
                }
                // pacman is eaten
                else {
                    this.lives--;
                    this.isOver();
                }
            }
        }
    }

    /**
     * Move the movableElement into the given cell
     * @param me : MovableElement to move
     * @param c : Cell to go
     */
    public void moveTo(MovableElement me, Cell c) {
        Cell actualCell = me.getCell();
        int x = actualCell.getX();
        int y = actualCell.getY();
        int finalX = c.getX();
        int finalY = c.getY();

        while (x != finalX && y != finalY) {
            if (y > finalY) { // Has to go top
                while(this.move(me, 0, -1)) {
                    y--;
                }
            } else if (y < finalY) { // Has to go bot
                while(this.move(me, 0, 1)) {
                    y++;
                }
            }

            if (x > finalX) { // Has to go left
                while(this.move(me, -1, 0)) {
                    x--;
                }
            } else if (x > finalX){ // Has to go right
                while(this.move(me, 1, 0)) {
                    x++;
                }
            }
        }
    }

    public void loseLife (){
        this.lives=this.lives-1;
        if (lives==0) {
            gameOver();
        }
    }

    public void gameOver(){

    }

    /**
     * Check if the game is over
     * @return true if remaining life equal 0, false otherwise
     */
    public boolean isOver() {
        if(this.lives == 0)
            return true;
        return false;
    }




    /**
     * Increase the actual score when pacman eat a gomme by the value of the static element
     * Remove the eaten element from the game
     * If this is a super gomme, then make ghost vulnerable
     * @param se : the element being eaten
     */
    private void eatStaticElement(StaticElement se) {
        if(se instanceof Gomme) {
            this.numberGommes--;
            this.pacman.getCell().removeStaticElement(se);
            this.checkGomme();
            Gomme g = (Gomme) se;
            if (g.getIsSuper() == true) {
                for(Ghost ghost : this.ghostList) {
                    ghost.setIsVulnerable(true);
                }
            }
        }
        else { // se is a bonus
            this.bonus = null;
        }
        this.score += se.getValue();

    }

    /**
     * Check if there is remaining gomme in the game
     * @return boolean true if there s still gomme, false otherwise
     */
    private boolean checkGomme(){
        if (this.numberGommes==0) {
            return false;
        }
        return true;
    }

    /**
     * Get the best score written in the file bestScore.txt in the res folder
     * @return int : best score
     */
     private int getBestScore() {
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
    private void setBestScore() {
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
}
