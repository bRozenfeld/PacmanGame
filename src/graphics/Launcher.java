package graphics;

import model.Game;

public class Launcher {

    public static void main(String args[]) {

        int[][] board1 = {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,4,1,1,1,1,0,0,0,1,1,1,1,8,0},
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
                {0,7,1,1,1,1,1,3,1,1,1,1,1,6,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}

        };

        // 0: wall
        // 1: cell
        // 2: supergomme
        // 3: porte fantome
        // 4: sans gomme
        // 5: démarrage pacman
        // 6: démarrage fantome
        int[][] board2 =
                {
                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                        {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
                        {0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
                        {0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
                        {0,2,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,2,0},
                        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                        {0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
                        {0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
                        {0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
                        {0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,0,0,0,4,0,0,4,0,0,0,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,4,4,4,4,4,6,4,4,4,4,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,4,0,0,0,3,3,0,0,0,4,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,4,0,4,4,4,4,4,4,0,4,0,0,1,0,0,0,0,0,0},
                        {4,4,4,4,4,4,1,4,4,4,0,4,4,4,4,4,4,0,4,4,4,1,4,4,4,4,4,4},
                        {0,0,0,0,0,0,1,0,0,4,0,6,4,6,4,6,4,0,4,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,4,0,0,0,0,0,0,0,0,4,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,4,4,4,4,4,4,4,4,4,4,0,0,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,1,0,0,4,0,0,0,0,0,0,0,0,4,0,0,1,0,0,0,0,0,0},
                        {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
                        {0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
                        {0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
                        {0,2,1,1,0,0,1,1,1,1,1,1,1,4,5,1,1,1,1,1,1,1,0,0,1,1,2,0},
                        {0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
                        {0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
                        {0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
                        {0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
                        {0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
                        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                };

        Game g = new Game(board1);

        GraphicGame gg = new GraphicGame("Pacman", 200, 200, 500, 600, g);


        try {
            gg.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
