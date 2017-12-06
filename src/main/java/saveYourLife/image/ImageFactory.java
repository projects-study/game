package saveYourLife.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import saveYourLife.enums.MoveDir;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageFactory {

    private static ImageFactory instance;
    private Map<Integer, BufferedImage> areas;
    private Map<Integer, Sprite> enemies;
    private static ObjectMapper objectMapper = new ObjectMapper();

    static{
        instance = new ImageFactory();
    }

    private ImageFactory() {
        areas = new HashMap<>();
        enemies = new HashMap<>();
        loadGraphics();
    }

    private void loadGraphics() {
        loadAreas();
        loadEnemies();
    }

    private void loadAreas(){
        File folder = new File("./src/images/areas");
        for(File file : folder.listFiles()){
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                areas.put(Integer.parseInt(file.getName().split("\\.")[0]), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadEnemies(){
        File folder = new File("./src/images/enemies");
        for(File file : folder.listFiles()){
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                String id = file.getName().split("\\.")[0];
                Sprite sprite = objectMapper.readValue(new File("./src/main/resources/enemies/sprite/"+ id +".json"), Sprite.class);
                sprite.setImage(image);
                enemies.put(Integer.parseInt(id), sprite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized ImageFactory getInstance() {
        return instance;
    }

    public BufferedImage getImage(int id){
        return areas.get(id);
    }

    public Sprite getEnemy(int id){
        return enemies.get(id);
    }

}
