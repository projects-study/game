package saveYourLife.model.enemy;

import saveYourLife.enums.MoveDir;
import saveYourLife.enums.MovingEffect;
import saveYourLife.image.ImageFactory;
import saveYourLife.image.Sprite;
import saveYourLife.model.level.Area;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class Enemy {

    private static Color darkGreen = new Color(60,179,113);

    private int id;
    private int hp;
    private double speed;
    private int[] areaPosition;
    private int[] direction;
    private double x;
    private double y;
    private List<Area> path;
    private boolean readyToRemove;
    private static ImageFactory imageFactory;
    private double frame = 0;
    private MoveDir moveDir;
    private int cash;
    private MovingEffect movingEffect;
    private Long movingEffectTimestamp;

    static{
        imageFactory = ImageFactory.getInstance();
    }

    private Predicate<Area> isAtArea = area -> Math.abs((area.getCenter()[0] - areaPosition[0]) - x) < 5 &&
            Math.abs((area.getCenter()[1] - areaPosition[1]) - y) < 5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public List<Area> getPath() {
        return path;
    }

    public void setPath(List<Area> path) {
        this.path = path;
        areaPosition = new int[2];
        areaPosition[0] = 0;
        areaPosition[1] = 0;
        direction = new int[2];
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isReadyToRemove() {
        return readyToRemove;
    }

    public void setReadyToRemove(boolean readyToRemove) {
        this.readyToRemove = readyToRemove;
    }

    public int[] getAreaPosition() {
        return areaPosition;
    }

    public void setAreaPosition(int[] areaPosition) {
        this.areaPosition = areaPosition;
    }

    public int getCash(){ return cash; }

    public void update() {
        if (!path.isEmpty() && isAtArea.test(path.get(0))) {
            path.remove(0);
            calculateNewDirection();
        }
        if(hp<=0)
            readyToRemove=true;
        if(movingEffect != null && System.nanoTime()-movingEffectTimestamp>=movingEffect.getEffectDuration())
            movingEffect = null;
        double newSpeed = speed;
        if(movingEffect!=null)
            newSpeed *=movingEffect.getEffectStrength();
        x += direction[0] * newSpeed;
        y += direction[1] * newSpeed;
    }

    public void setMovingEffect(MovingEffect movingEffect){
        this.movingEffect = movingEffect;
        this.movingEffectTimestamp = System.nanoTime();
    }

    private void calculateNewDirection() {
        if (!path.isEmpty()) {
            Area area = path.get(0);
            if ((area.getCenter()[0] - areaPosition[0]) - x > 10)
                direction[0] = 1;
            else if ((area.getCenter()[0] - areaPosition[0]) - x < -10)
                direction[0] = -1;
            else
                direction[0] = 0;
            if ((area.getCenter()[1] - areaPosition[1]) - y > 10)
                direction[1] = 1;
            else if ((area.getCenter()[1] - areaPosition[1]) - y < -10)
                direction[1] = -1;
            else
                direction[1] = 0;

            setNewMoveDir();
        } else {
            readyToRemove = true;
        }

    }

    private void setNewMoveDir(){
        if(direction[0]>0)
            moveDir = MoveDir.RIGHT;
        else if(direction[0]<0)
            moveDir = MoveDir.LEFT;
        else if(direction[1]>0)
            moveDir = MoveDir.DOWN;
        else if(direction[1]<0)
            moveDir = MoveDir.UP;
    }

    public void draw(Graphics2D g) {
        Sprite sprite = imageFactory.getEnemy(id);
        int tester = (int) (frame + .5);
        if (tester < sprite.getFrames().get(moveDir).size())
            frame = (float) (frame + .1);
        else
            frame = 0;


        int xFrom = sprite.getFrames().get(moveDir).get((int)frame).getxFrom();
        int xTo = sprite.getFrames().get(moveDir).get((int)frame).getxTo();
        int yFrom = sprite.getFrames().get(moveDir).get((int)frame).getyFrom();
        int yTo = sprite.getFrames().get(moveDir).get((int)frame).getyTo();
        int halfWidth = (xTo-xFrom)/2;
        int halfHeight = (yTo-yFrom)/2;
        g.drawImage(sprite.getImage(), (int)(x-halfWidth), (int)(y-halfHeight), (int)(x+halfWidth), (int)(y+halfHeight), xFrom, yFrom, xTo, yTo, null);
        if(movingEffect!=null) {
            g.setColor(darkGreen);
            g.setFont(new Font("default", Font.BOLD, 10));
            g.drawString("S", (int)(x+halfWidth), (int)(y+halfHeight));
        }
    }
}
