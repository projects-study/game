package saveYourLife.loader;

import java.util.List;

public class JsonWave {

    private List<JsonSquad> squads;
    private int delay;

    public List<JsonSquad> getSquads() {
        return squads;
    }

    public void setSquads(List<JsonSquad> squads) {
        this.squads = squads;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

}
