package saveYourLife.model.level;

import saveYourLife.image.ImageFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.BooleanSupplier;

public class Area {

    private ImageFactory imageFactory = ImageFactory.getInstance();

    private int type;
    public BooleanSupplier isStartArea = () -> type >= 0 && type <= 99;
    public BooleanSupplier isFinishArea = () -> type >= 100 && type <= 199;
    public BooleanSupplier isTowerArea = () -> type >= 200 && type <= 299;
    public BooleanSupplier isRoadArea = () -> type >= 300 && type <= 399;
    private int[] center;
    private Tower tower;

    public Area(int type, int x, int y) {
        this.type = type;
        center = new int[2];
        center[0] = x;
        center[1] = y;
    }

    public void draw(Graphics2D g, int x, int y) {
        BufferedImage img = imageFactory.getImage(type);
        if(tower==null)
            g.drawImage(img, x, y, null);
        else
            g.drawImage(imageFactory.getImage(tower.getTowerType().getImageNo()),x,y,null);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[] getCenter() {
        return center;
    }

    public void setCenter(int[] center) {
        this.center = center;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }
}
