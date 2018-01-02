package saveYourLife.image;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageFactory {

    private static ImageFactory instance;
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        instance = new ImageFactory();
    }

    private Map<Integer, BufferedImage> areas;
    private Map<Integer, Sprite> enemies;
    private BufferedImage menu;
    private BufferedImage bar;
    private Map<Integer, BufferedImage> minis;
    private Map<Integer, BufferedImage> bullets;
    private BufferedImage boomBullet;

    private ImageFactory() {
        areas = new HashMap<>();
        enemies = new HashMap<>();
        minis = new HashMap<>();
        bullets = new HashMap<>();
        loadGraphics();
    }

    public static synchronized ImageFactory getInstance() {
        return instance;
    }

    private void loadGraphics() {
        loadAreas();
        loadEnemies();
        loadMenuAndMinis();
        loadBullets();
    }

    private void loadAreas() {
        File folder = new File("./src/images/areas");
        for (File file : folder.listFiles()) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                areas.put(Integer.parseInt(file.getName().split("\\.")[0]), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadEnemies() {
        File folder = new File("./src/images/enemies");
        for (File file : folder.listFiles()) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                String id = file.getName().split("\\.")[0];
                Sprite sprite = objectMapper.readValue(new File("./src/main/resources/enemies/sprite/" + id + ".json"), Sprite.class);
                sprite.setImage(image);
                enemies.put(Integer.parseInt(id), sprite);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBullets(){
        File folder = new File("./src/images/bullets");
        for (File file : folder.listFiles()) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                if(!file.getName().split("\\.")[0].matches(".*[a-zA-Z]+.*"))
                    bullets.put(Integer.parseInt(file.getName().split("\\.")[0]), image);
                else
                    boomBullet = image;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedImage getBoomBullet() {
        return boomBullet;
    }

    private void loadMenuAndMinis(){
        loadMenu();
        loadMinis();
    }

    private void loadMenu(){
        File folder = new File("./src/images/menu");
        for (File file : folder.listFiles()) {
            try {
                if(file.getName().split("\\.")[0].equals("kolo"))
                    menu = ImageIO.read(file);
                else
                    bar = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedImage getBar() {
        return bar;
    }

    private void loadMinis(){
        File folder = new File("./src/images/min");
        for (File file : folder.listFiles()) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                String id = file.getName().split("\\.")[0].split("min")[1];
                minis.put(Integer.parseInt(id), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public BufferedImage getImage(int id) {
        return areas.get(id);
    }

    public Sprite getEnemy(int id) {
        return enemies.get(id);
    }

    public BufferedImage getMenu() {
        return menu;
    }

    public void setMenu(BufferedImage menu) {
        this.menu = menu;
    }

    public Map<Integer, BufferedImage> getMinis() {
        return minis;
    }

    public void setMinis(Map<Integer, BufferedImage> minis) {
        this.minis = minis;
    }

    public BufferedImage getBullet(int id){ return bullets.get(id); }
}
