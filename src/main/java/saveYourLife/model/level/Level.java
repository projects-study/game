package saveYourLife.model.level;

import saveYourLife.loader.EnemyLoader;
import saveYourLife.loader.JsonLevel;
import saveYourLife.loader.JsonSquad;
import saveYourLife.loader.JsonWave;
import saveYourLife.model.enemy.Enemy;
import saveYourLife.model.enemy.Squad;
import saveYourLife.model.enemy.Wave;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Level {

    public final static int WIDTH = 16;
    public final static int HEIGHT = 11;
    private Area[][] grid;
    private int life;
    private int cash;
    private List<Rule> rules;
    private List<Wave> waves;
    private List<Enemy> runningEnemies;

    private long waveStartTime;
    private Map<Wave, Long> squadStartTimes;

    private Predicate<List<Wave>> canStartNewWave = w -> !w.isEmpty() && ((System.nanoTime() - waveStartTime) / 1000000) >= waves.get(0).getDelay();
    private BiPredicate<List<Squad>, Wave> canStartNextSquad = (s, w) -> !s.isEmpty() && ((System.nanoTime() - waveStartTime) / 1000000) >= s.get(0).getDelay();

    public Level() {
    }

    public Level(JsonLevel jsonLevel) {
        this.life = jsonLevel.getLife();
        this.cash = jsonLevel.getCash();
        this.rules = jsonLevel.getRules();
        this.grid = new Area[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                grid[i][j] = new Area(jsonLevel.getGrid()[i][j]);
        this.waves = new ArrayList<>();
        this.runningEnemies = new ArrayList<>();
        for (JsonWave jsonWave : jsonLevel.getWaves()) {
            Wave wave = new Wave();
            wave.setDelay(jsonWave.getDelay());
            wave.setSquads(new ArrayList<>());
            for (JsonSquad jsonSquad : jsonWave.getSquads()) {
                Squad squad = new Squad();
                squad.setDelay(jsonSquad.getDelay());
                squad.setEnemies(new ArrayList<>());
                for (Integer enemyId : jsonSquad.getEnemies()) {
                    squad.getEnemies().add(EnemyLoader.loadEnemy(enemyId));
                }
                wave.getSquads().add(squad);
            }
            waves.add(wave);
        }
        this.waveStartTime = System.nanoTime();
        this.squadStartTimes = new HashMap<>();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 800, 50);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j].draw(g, j * (800 / WIDTH), i * (600 / (HEIGHT + 1)) + 50);
            }
        }
    }

    public Area[][] getGrid() {
        return grid;
    }

    public void setGrid(Area[][] grid) {
        this.grid = grid;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Wave> getWaves() {
        return waves;
    }

    public void setWaves(List<Wave> waves) {
        this.waves = waves;
    }

    public List<Enemy> getRunningEnemies() {
        return runningEnemies;
    }

    public void setRunningEnemies(List<Enemy> runningEnemies) {
        this.runningEnemies = runningEnemies;
    }

    public void update() {
        startNewWave();
        startNextSquad();
    }

    private void startNewWave() {
        if (canStartNewWave.test(waves)) {
            waveStartTime = System.nanoTime();
            squadStartTimes.put(waves.get(0), System.nanoTime());
            waves.remove(0);
        }
    }

    private void startNextSquad() {
        List<Wave> toRun = squadStartTimes.entrySet()
                .stream()
                .filter(e -> canStartNextSquad.test(e.getKey().getSquads(), e.getKey()))
                .map(e -> e.getKey())
                .collect(Collectors.toList());
        toRun.forEach(w -> {
            runningEnemies.addAll(w.getSquads().get(0).getEnemies());
            w.getSquads().remove(0);
            squadStartTimes.replace(w, System.nanoTime());
        });

        List<Wave> toRemove = squadStartTimes.entrySet()
                .stream()
                .filter(e -> e.getKey().getSquads().isEmpty())
                .map(e -> e.getKey()).collect(Collectors.toList());
        toRemove.forEach(w -> squadStartTimes.remove(w));
    }
}
