package saveYourLife.enums;

public enum TowerType {
    MAGIC(202, 100D, 10, 1000000000L, 10),
    SLOWING(203, 100D, 1, 1000000000L, 10),
    ARROW(205, 100D, 10, 1000000000L, 10),
    AOE(204, 100D, 5, 1000000000L, 10);

    private final int imageNo;
    private boolean enabled;
    private Double range;
    private int firePower;
    private int cost;
    private Long fireRate;

    TowerType(int imageNo, Double range, int firePower, Long fireRate, int cost) {
        this.imageNo = imageNo;
        this.enabled = true;
        this.range = range;
        this.firePower = firePower;
        this.cost = cost;
        this.fireRate = fireRate;
    }

    public int getImageNo() {
        return imageNo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Double getRange() {
        return range;
    }

    public void setRange(Double range) {
        this.range = range;
    }

    public int getFirePower() {
        return firePower;
    }

    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    public int getCost(){return cost;}

    public Long getFireRate() { return fireRate; }
}
