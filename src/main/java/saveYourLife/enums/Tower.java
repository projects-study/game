package saveYourLife.enums;

public enum Tower {
    MAGIC(202),
    SLOWING(203),
    ARROW(205),
    AOE(204);

    private final int imageNo;
    private boolean enabled;

    Tower(int imageNo) {
        this.imageNo = imageNo;
        this.enabled = true;
    }

    public int getImageNo(){
        return imageNo;
    }

    public boolean isEnabled(){ return enabled; }

}
