package model;


import java.util.ArrayList;

/**
 * Class representing the game
 * @inv {@code bestScore >=0}
 * @inv {@code lives>=0 && lives<=3}
 * @inv {@code score>=0}
 * @inv {@code level>0}
 **/
public class Game {
    private int bestScore;
    private int lives;
    private int level;
    private int score;
    private ArrayList<Cell> cellList;
    private Pacman pacman;
    private Bonus bonus;
    private ArrayList<Ghost> ghostList;
    private ArrayList<Gomme> gommeList;


    public Game(Pacman pacman) {
        this.cellList=new ArrayList<Cell>();
        this.ghostList=new ArrayList<Ghost>();
        this.gommeList=new ArrayList<Gomme>();
        this.pacman=pacman;
        this.bestScore=0;
        this.level=1;
        this.lives=3;
        this.score=0;
    }

    public Game(int level,int lives,int score,Pacman pacman) {
        this.cellList=new ArrayList<Cell>();
        this.ghostList=new ArrayList<Ghost>();
        this.gommeList=new ArrayList<Gomme>();
        this.pacman=pacman;
        this.bestScore = 0;
        this.level=level;
        this.score=score;
        this.lives=lives;
    }

    public boolean movePacman(int direct){
        return true;
    }

    public boolean checkGomme(){
        if (gommeList.size() !=0)
            return true;
        else
            return false;
    }

    public void loseLife (){
        this.lives=this.lives-1;
        if (lives==0) {
            gameOver();
        }
    }

    public void gameOver(){

    }
}
