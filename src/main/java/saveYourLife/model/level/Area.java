package saveYourLife.model.level;

import saveYourLife.image.ImageFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Area {

    private ImageFactory imageFactory = ImageFactory.getInstance();

    private int type;

    public Area(int type) {
        this.type = type;
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
}
