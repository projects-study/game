package saveYourLife.model.level;

import saveYourLife.image.ImageFactory;
import saveYourLife.model.enemy.Enemy;

import java.awt.*;
import java.util.function.Predicate;

public class Bullet {

    private ImageFactory imageFactory = ImageFactory.getInstance();

    private double x;
    private double y;
    private int dmg;
    private int imageNr;
    private Enemy target;
    private double[] v;
    private boolean rdyToRemove;

    private Predicate<Enemy> isTargetHit = enemy -> Math.abs(enemy.getX() - x) < 10 && Math.abs(enemy.getY() - y) < 10;

    public Bullet(double x, double y, int dmg, int imageNr, Enemy target) {
        this.x = x;
        this.y = y;
        this.dmg = dmg;
        this.imageNr = imageNr;
        this.target = target;
        this.v = new double[2];
        calculateNewV();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getImageNr() {
        return imageNr;
    }

    public void setImageNr(int imageNr) {
        this.imageNr = imageNr;
    }

    private void calculateNewV() {
        v[0] = target.getX() - x;
        v[1] = target.getY() - y;
        double l = Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2));
        v[0] = v[0] / l;
        v[1] = v[1] / l;
    }

    public void update() {
        calculateNewV();
        x += v[0] * 6;
        y += v[1] * 6;
        if (isTargetHit.test(target)) {
            target.setHp(target.getHp() - dmg);
            rdyToRemove = true;
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(imageFactory.getBullet(imageNr), (int)x, (int)y,null);
    }

    public Enemy getTarget() {
        return target;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public double[] getV() {
        return v;
    }

    public void setV(double[] v) {
        this.v = v;
    }

    public boolean isRdyToRemove() {
        return rdyToRemove;
    }

    public void setRdyToRemove(boolean rdyToRemove) {
        this.rdyToRemove = rdyToRemove;
    }

    public Predicate<Enemy> getIsTargetHit() {
        return isTargetHit;
    }

    public void setIsTargetHit(Predicate<Enemy> isTargetHit) {
        this.isTargetHit = isTargetHit;
    }
}
