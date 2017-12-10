package saveYourLife.model.level;

import saveYourLife.image.ImageFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class Area {

    private ImageFactory imageFactory = ImageFactory.getInstance();

    private int type;
    private int[] center;

    public BooleanSupplier isStartArea = () -> type >=0 && type <=99;

    public BooleanSupplier isFinishArea = () -> type >=100 && type <=199;

    public BooleanSupplier isTowerArea = () -> type >=200 && type <=299;

    public BooleanSupplier isRoadArea = () -> type >=300 && type <=399;

    public Area(int type, int x, int y) {
        this.type = type;
        center = new int[2];
        center[0] = x;
        center[1] = y;
    }

    public void draw(Graphics2D g, int x, int y) {
        BufferedImage img = imageFactory.getImage(type);
//        int angle = 90;
//        double rotationRequired = Math.toRadians (angle);
//        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 25, 25);
//        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//        g.drawImage(op.filter(img, null), x, y, null);
        g.drawImage(img, x, y, null);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int[] getCenter() {
        return center;
    }

    public void setCenter(int[] center) {
        this.center = center;
    }

}
