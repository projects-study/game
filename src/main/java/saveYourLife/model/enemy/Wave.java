package saveYourLife.model.enemy;

import java.util.List;

public class Wave {

    private List<Squad> squads;
    private int delay;

    public List<Squad> getSquads() {
        return squads;
    }

    public void setSquads(List<Squad> squads) {
        this.squads = squads;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
