package saveYourLife.model.level;

import saveYourLife.enums.TowerType;

public class Tower {

    private TowerType towerType;
    private Long fireTimestamp;
    private int lvl;


    public Tower(TowerType towerType) {
        this.towerType = towerType;
        this.fireTimestamp = System.nanoTime()-100000000000L;
        this.lvl = 1;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }

    public boolean canShoot() {
        return System.nanoTime() - fireTimestamp >= towerType.getFireRate();
    }

    public void shoot() {
        this.fireTimestamp = System.nanoTime();
    }

    public Long getFireTimestamp() {
        return fireTimestamp;
    }

    public void setFireTimestamp(Long fireTimestamp) {
        this.fireTimestamp = fireTimestamp;
    }

    public int getLvl() {
        return lvl;
    }

    public void lvlUp(){
        lvl++;
    }
}
