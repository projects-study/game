package saveYourLife.image;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageFactory {

    private static ImageFactory instance;
    private Map<Integer, Image> areas;

    private ImageFactory() {
    areas = new HashMap<>();
    }

    public static synchronized ImageFactory getInstance() {
        return instance == null ? instance = new ImageFactory() : instance;
    }

}
