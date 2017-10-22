package saveYourLife.loader;

import saveYourLife.model.level.Rule;

import java.util.List;

public class JsonLevel {

    private int[][] grid;
    private int life;
    private int cash;
    private List<Rule> rules;
    private List<JsonWave> waves;

    public List<Rule> getRules() { return rules; }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int[][] getGrid() { return grid; }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public List<JsonWave> getWaves() {
        return waves;
    }

    public void setWaves(List<JsonWave> waves) {
        this.waves = waves;
    }
}
