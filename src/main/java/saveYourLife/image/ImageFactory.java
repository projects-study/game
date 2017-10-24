package saveYourLife.image;

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

    private ImageFactory() {
        areas = new HashMap<>();
        loadGraphics();
    }

    private void loadGraphics() {
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

    public static synchronized ImageFactory getInstance() {
        return instance == null ? instance = new ImageFactory() : instance;
    }

    public BufferedImage getImage(int id){
        return areas.get(id);
    }

}
