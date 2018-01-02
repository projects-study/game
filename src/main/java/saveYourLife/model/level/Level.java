package saveYourLife.model.level;

import saveYourLife.enums.RuleType;
import saveYourLife.enums.TowerType;
import saveYourLife.loader.EnemyLoader;
import saveYourLife.loader.JsonLevel;
import saveYourLife.loader.JsonSquad;
import saveYourLife.loader.JsonWave;
import saveYourLife.model.enemy.Enemy;
import saveYourLife.model.enemy.Squad;
import saveYourLife.model.enemy.Wave;

import java.awt.*;
import java.util.*;
import java.util.List;
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
    private Map<Integer, List<Area>> paths;
    private List<Bullet> bullets;
    private boolean ended;

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
        this.grid = new Area[HEIGHT + 2][WIDTH + 2];
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                grid[i + 1][j + 1] = new Area(jsonLevel.getGrid()[i][j], j * (800 / WIDTH) + 25, i * (600 / (HEIGHT + 1)) + 75);
        paths = new HashMap<>();
        generatePaths();
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
                    float hpMultiplier = 1;
                    if(getRule(RuleType.MONSTER_HP)!=null)
                        hpMultiplier = getRule(RuleType.MONSTER_HP).getSingleValue();
                    squad.getEnemies().add(EnemyLoader.loadEnemy(enemyId, hpMultiplier));
                }
                wave.getSquads().add(squad);
            }
            waves.add(wave);
        }
        this.waveStartTime = System.nanoTime();
        this.squadStartTimes = new HashMap<>();
        this.bullets = new ArrayList<>();
        Rule rule = getRule(RuleType.BAN_TOWER);
        rule.getListValue().forEach(i -> {
            for(TowerType tower: TowerType.values())
                if(tower.getImageNo() == i)
                    tower.setEnabled(false);
        });
    }

    public Rule getRule(RuleType ruleType){
        return rules.stream().filter(r -> r.getType().equals(ruleType)).findFirst().get();
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

    private void generatePaths() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                if (grid[i + 1][j + 1].isStartArea.getAsBoolean()) {
                    findPathFromStart(i + 1, j + 1);
                }
    }

    private void findPathFromStart(int i, int j) {
        findPaths(i, j, new ArrayList<Area>());
    }

    private void findPaths(int indX, int indY, List<Area> path) {
        path.add(grid[indX][indY]);
        boolean foundedOne = false;
        List<Area> cPath = null;
        int countRoads = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == -1) || (i == -1 && j == 0) || (i == 1 && j == 0) || (i == 0 && j == 1)) {
                    Area nArea = grid[indX + i][indY + j];
                    if (nArea != null && nArea.isRoadArea.getAsBoolean() && !path.contains(nArea))
                        countRoads++;
                }
            }
        }
        if (countRoads > 1)
            cPath = new ArrayList<>(path);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == -1) || (i == -1 && j == 0) || (i == 1 && j == 0) || (i == 0 && j == 1)) {
                    Area nArea = grid[indX + i][indY + j];
                    if (nArea != null && nArea.isFinishArea.getAsBoolean()) {
                        List<Area> fPath = new ArrayList<>() {{
                            addAll(path);
                            add(nArea);
                        }};
                        addStartAndEnd(fPath);
                        paths.put(paths.size() + 1, fPath);
                    } else if (nArea != null && nArea.isRoadArea.getAsBoolean() && !path.contains(nArea)) {
                        if (foundedOne) {
                            findPaths(indX + i, indY + j, cPath);
                        } else {
                            foundedOne = true;
                            findPaths(indX + i, indY + j, path);
                        }
                    }
                }
            }
        }
    }

    private void addStartAndEnd(List<Area> path) {
        Area firstArea = path.get(0);
        if (firstArea.getType() % 2 == 0) {
            if (firstArea.getCenter()[0] < 100) {
                path.add(0, new Area(-1, -25, firstArea.getCenter()[1]));
            } else {
                path.add(0, new Area(-1, firstArea.getCenter()[0] + 25, firstArea.getCenter()[1]));
            }
        } else {
            if (firstArea.getCenter()[1] < 100) {
                path.add(0, new Area(-1, firstArea.getCenter()[0], -25));
            } else {
                path.add(0, new Area(-1, firstArea.getCenter()[0], firstArea.getCenter()[1] + 25));
            }
        }

        Area lastArea = path.get(path.size() - 1);
        if (lastArea.getType() % 2 == 0) {
            if (lastArea.getCenter()[0] < 100) {
                path.add(new Area(-1, -25, lastArea.getCenter()[1]));
            } else {
                path.add(new Area(-1, lastArea.getCenter()[0] + 25, lastArea.getCenter()[1]));
            }
        } else {
            if (lastArea.getCenter()[1] < 100) {
                path.add(new Area(-1, lastArea.getCenter()[0], -25));
            } else {
                path.add(new Area(-1, lastArea.getCenter()[0], lastArea.getCenter()[1] + 25));
            }
        }

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 800, 50);
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                grid[i + 1][j + 1].draw(g, j * (800 / WIDTH), i * (600 / (HEIGHT + 1)) + 50);
        runningEnemies.forEach(enemy -> enemy.draw(g));
        bullets.forEach(b -> b.draw(g));
    }

    public void update() {
        startNewWave();
        startNextSquad();
        updateTowers();
        updateBullets();
        updateEnemies();
        if(waves.isEmpty() && runningEnemies.isEmpty())
            ended = true;
    }

    public boolean isEnded(){
        return ended;
    }

    private void updateEnemies() {
        runningEnemies.forEach(Enemy::update);
        float cashMultiplier = 1;
        if(getRule(RuleType.CASH_FOR_KILL)!=null)
            cashMultiplier = getRule(RuleType.CASH_FOR_KILL).getSingleValue();
        for(Enemy e:runningEnemies)
            if(e.isReadyToRemove())
                cash+=(int)(e.getCash()*cashMultiplier);
        runningEnemies.removeIf(Enemy::isReadyToRemove);
    }

    private void updateBullets() {
        bullets.forEach(Bullet::update);
        bullets.removeIf(Bullet::isRdyToRemove);
    }

    private void updateTowers() {
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++)
                if (grid[i + 1][j + 1].getTower()!=null)
                    updateTower(grid[i + 1][j + 1]);
    }

    private void updateTower(Area area) {
        runningEnemies.forEach(e -> {
            Tower tower = area.getTower();
            if(isEnemyInTowerRange(area, e) && tower.canShoot()){
                area.getTower().shoot();
                bullets.add(new Bullet(area.getCenter()[0], area.getCenter()[1], tower.getTowerType().getFirePower(), tower.getTowerType().getImageNo(), e));
                System.out.println("SHOOT");
            }
        });
    }

    private boolean isEnemyInTowerRange(Area area, Enemy enemy) {
        return Math.sqrt(Math.pow((area.getCenter()[0] - enemy.getX()), 2) + Math.pow((area.getCenter()[1] - enemy.getY()), 2)) <= area.getTower().getTowerType().getRange();
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
            List<Area> path = randomPath();
            w.getSquads().get(0).getEnemies().forEach(enemy -> enemy.setPath(new ArrayList<>(path)));
            placeEnemiesOnStart(w.getSquads().get(0).getEnemies(), path);
            runningEnemies.addAll(w.getSquads().get(0).getEnemies());
            w.getSquads().remove(0);
            squadStartTimes.replace(w, System.nanoTime());
        });

        List<Wave> toRemove = squadStartTimes.entrySet()
                .stream()
                .filter(e -> e.getKey().getSquads().isEmpty())
                .map(e -> e.getKey())
                .collect(Collectors.toList());
        toRemove.forEach(w -> squadStartTimes.remove(w));
    }

    private void placeEnemiesOnStart(List<Enemy> enemies, List<Area> path) {
        Area start = path.get(0);
        Random random = new Random();
        enemies.forEach(e -> {
            int[] areaPos = {random.nextInt(25) - 10, random.nextInt(25) - 10};
            e.setX(start.getCenter()[0] - areaPos[0]);
            e.setY(start.getCenter()[1] - areaPos[1]);
            e.setAreaPosition(areaPos);
        });
    }

    private List<Area> randomPath() {
        int rand = new Random().nextInt(paths.size()) + 1;
        return paths.get(rand);
    }
}
