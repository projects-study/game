package saveYourLife.model.level;

import saveYourLife.enums.TowerType;

public class Tower {

    private TowerType towerType;
    private Long fireTimestamp;


    public Tower(TowerType towerType) {
        this.towerType = towerType;
        this.fireTimestamp = System.nanoTime();
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

}
