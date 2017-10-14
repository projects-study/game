package someGame.model.map;

import java.awt.*;

public class Area {

    public void draw(Graphics2D g, int x, int y){
        g.setColor(Color.BLACK);
        g.fillRect(x, y, 50, 50);
        g.setColor(Color.white);
        g.fillRect(x+1, y+1, 48, 48);
    }

}
