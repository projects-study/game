package saveYourLife.model.enemy;

import saveYourLife.enums.MoveDir;
import saveYourLife.image.ImageFactory;
import saveYourLife.image.Sprite;
import saveYourLife.model.level.Area;

import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class Enemy {

    private int id;
    private int hp;
    private int speed;
    private int[] areaPosition;
    private int[] direction;
    private int x;
    private int y;
    private List<Area> path;
    private boolean readyToRemove = false;
    private static ImageFactory imageFactory;
    private double frame = 0;
    private MoveDir moveDir;

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
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

    public void update() {
        if (!path.isEmpty() && isAtArea.test(path.get(0))) {
            path.remove(0);
            calculateNewDirection();
        }
        x += direction[0] * speed;
        y += direction[1] * speed;
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
        int tester = (int) (frame + .5);
        if (tester < 3)
            frame = (float) (frame + .1);
        else
            frame = 0;

        Sprite sprite = imageFactory.getEnemy(id);
        int xFrom = sprite.getFrames().get(moveDir).get((int)frame).getxFrom();
        int xTo = sprite.getFrames().get(moveDir).get((int)frame).getxTo();
        int yFrom = sprite.getFrames().get(moveDir).get((int)frame).getyFrom();
        int yTo = sprite.getFrames().get(moveDir).get((int)frame).getyTo();
        g.drawImage(sprite.getImage(), x-8, y-8, x+8, y+8, xFrom, yFrom, xTo, yTo, null);

//        g.setColor(Color.BLACK);
//        g.fillRect(x-5, y-5, 10, 10);
    }
}
