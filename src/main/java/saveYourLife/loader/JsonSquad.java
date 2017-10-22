package saveYourLife.loader;

import java.util.List;

public class JsonSquad {

    private List<Integer> enemies;
    private int delay;

    public List<Integer> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Integer> enemies) {
        this.enemies = enemies;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
