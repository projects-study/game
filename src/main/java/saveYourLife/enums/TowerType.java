package saveYourLife.enums;

public enum TowerType {
    MAGIC(202, 100D, 1),
    SLOWING(203, 100D, 1),
    ARROW(205, 100D, 1),
    AOE(204, 100D, 1);

    private final int imageNo;
    private boolean enabled;
    private Double range;
    private int firePower;

    TowerType(int imageNo, Double range, int firePower) {
        this.imageNo = imageNo;
        this.enabled = true;
        this.range = range;
        this.firePower = firePower;
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
}
