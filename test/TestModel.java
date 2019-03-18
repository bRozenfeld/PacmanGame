import model.*;

import java.util.ArrayList;

// 0:wall
// 1:gomme
// 2:pacman
// 3:ghost
// 4:supergomme
// 5: bonus
// 6: void cell
public class TestModel {

    public TestModel(){}

    private void displayBoard(int[][] board) {

        System.out.println("Board: ");
        System.out.println("0->wall ; 1->gomme ; 2->pacman ; 3->ghost");
        System.out.println("4->supergomme ; 5->bonus ; 6->void");

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    private Game initGame(int[][] board) {
        ArrayList<Cell> cellList = new ArrayList<>();
        ArrayList<Ghost> ghostList = new ArrayList<>();
        ArrayList<Gomme> gommeList = new ArrayList<>();
        Pacman pacman = null;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(board[i][j] == 0) {
                    Cell c = new Cell(i, j, true);
                    cellList.add(c);
                }
                else if (board[i][j] == 1) {
                    Gomme g = new Gomme(10, false);
                    Cell c = new Cell(i,j,false,g,g);
                    cellList.add(c);
                    gommeList.add(g);
                    c.addStaticElement(g);
                }
                else if(board[i][j] == 2) {
                    Cell c = new Cell(i,j,false);
                    pacman = new Pacman(c,c);
                    c.addMovableElement(pacman);
                    cellList.add(c);
                }
                else if(board[i][j] == 3) {
                    Cell c = new Cell(i,j,false);
                    Ghost g = new Ghost(c, GhostColor.Blue,c);
                    c.addMovableElement(g);
                    cellList.add(c);
                    ghostList.add(g);
                }
                else if (board[i][j] == 4) {
                    Gomme g = new Gomme(50, true);
                    Cell c = new Cell(i,j,false,g,g);
                    cellList.add(c);
                    gommeList.add(g);
                    c.addStaticElement(g);
                }
                else if(board[i][j] == 5) {
                    Cell c = new Cell(i,j,false);
                    Bonus b = new Bonus(100, TypeBonus.Apple,c);
                    c.addStaticElement(b);
                    cellList.add(c);
                }
                else if(board[i][j] == 6) {
                    Cell c = new Cell(i,j,false);
                    cellList.add(c);
                }
            }
        }
        Game g = new Game(pacman, cellList,gommeList.size(),ghostList);
        return g;
    }

    private void testMove() {
        int [][] board = {
                {0,0,0,0,0,0},
                {0,2,6,6,6,0},
                {0,6,0,0,6,0},
                {0,6,0,0,6,0},
                {0,6,6,6,6,0},
                {0,0,0,0,0,0}
        };
        this.displayBoard(board);

        Game g = initGame(board);
        Pacman p = g.getPacman();

        System.out.println("Position Pacman before move: ");
        p.displayPosition(); // (1,1)

        g.move(p,1,0);
        p.displayPosition(); // (2,1)

        g.move(p,0,-1);
        p.displayPosition(); // (2,1)

        Cell c = new Cell(3,4,false);
        g.moveTo(p, c);
        p.displayPosition(); // (3,4)
    }


    public static void main(String args[]) {
        TestModel tm = new TestModel();

        tm.testMove();
    }
}
