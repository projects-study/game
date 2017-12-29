package saveYourLife.model.level;

import saveYourLife.enums.TowerType;

public class Tower {

    private TowerType towerType;
    private Long fireTimestamp;
    private Long fireRate;

    public Tower(TowerType towerType) {
        this.towerType = towerType;
        this.fireTimestamp = System.nanoTime();
        this.fireRate = 1000000000L;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }

    public boolean canShoot() {
        return System.nanoTime() - fireTimestamp >= fireRate;
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

    public Long getFireRate() {
        return fireRate;
    }

    public void setFireRate(Long fireRate) {
        this.fireRate = fireRate;
    }
}
