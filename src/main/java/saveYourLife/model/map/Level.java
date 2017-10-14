package saveYourLife.model.map;

import saveYourLife.loader.LevelFactory;

import java.awt.*;

public class Level {

    public final static int WIDTH = 16;
    public final static int HEIGHT = 11;
    private static Level instance;
    private Area[][] grid;

    public Level() {
        grid = LevelFactory.loadLevel();
    }

    public static synchronized Level getInstance() {
        return instance == null ? instance = new Level() : instance;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 800, 50);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].draw(g, j * (800 / WIDTH), i * (600 / (HEIGHT + 1)) + 50);
            }
        }
    }
}
