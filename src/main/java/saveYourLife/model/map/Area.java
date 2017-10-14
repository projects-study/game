package saveYourLife.model.map;

import java.awt.*;

public class Area {

    private int type;

    public Area(int type) {
        this.type = type;
    }

    public void draw(Graphics2D g, int x, int y){
        g.setColor(Color.BLACK);
        g.fillRect(x, y, 50, 50);
        g.setColor(Color.white);
        g.fillRect(x+1, y+1, 48, 48);
        g.setColor(Color.BLACK);
        g.drawString("" + type, x + 10, y + 20);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
