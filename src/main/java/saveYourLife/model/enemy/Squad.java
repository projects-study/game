package saveYourLife.model.enemy;

import java.util.List;

public class Squad {

    private List<Enemy> enemies;
    private int delay;

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
