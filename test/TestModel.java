import model.*;

import java.util.ArrayList;
import java.util.Stack;

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
        System.out.println("0->wall ; 1->gomme ; 2->pacman ; 3->ghost ; 4->supergomme ; 5->bonus ; 6->void");
        System.out.println();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }

    private Game initGame(int[][] board) {
        ArrayList<Cell> cellList = new ArrayList<>();
        ArrayList<Ghost> ghostList = new ArrayList<>();
        ArrayList<Gomme> gommeList = new ArrayList<>();
        Pacman pacman = null;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
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
        int[][] board = {
                {0,0,0,0,0,0,0,0},
                {0,2,1,1,1,1,1,0},
                {0,1,0,0,0,1,0,0},
                {0,1,1,1,1,1,1,0},
                {0,0,0,0,1,0,1,0},
                {0,1,1,1,1,0,1,0},
                {0,0,0,0,0,0,0,0},
        };
        this.displayBoard(board);

        Game g = initGame(board);
        Pacman p = g.getPacman();
        Cell endCell = new Cell(5,6,false);

        PathFinding pf = new PathFinding();
        Stack<Cell> s = pf.getWay(g.getCellList(), p.getCell(), endCell);
        System.out.println();
        while(!s.empty()){
            System.out.println(s.pop());
        }

    }


    public static void main(String args[]) {
        TestModel tm = new TestModel();

        tm.testMove();
    }
}
