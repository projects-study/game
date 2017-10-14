package someGame.model.map;

import someGame.loader.FileLoader;
import someGame.main.GameMain;

import java.awt.*;

public class Map {

    private final int WIDTH = 16;
    private final int HEIGHT = 11;
    private static Map instance;
    private Area[][] grid;

    private Map(){
        grid = new Area[WIDTH][HEIGHT];
        for(int i=0; i<WIDTH; i++){
            for(int j=0; j<HEIGHT; j++){
                grid[i][j] = new Area();
            }
        }
        FileLoader.loadLevel();
    }

    public static synchronized Map getInstance(){
        return instance == null ? instance = new Map() : instance;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 800, 50);
        for(int i=0; i<WIDTH; i++){
            for(int j=0; j<HEIGHT; j++){
                grid[i][j].draw(g, i*(800/WIDTH), j*(600/(HEIGHT+1))+50);
            }
        }
    }

}
