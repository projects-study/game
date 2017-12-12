package saveYourLife.enums;

public enum Tower {
    MAGIC(202),
    SLOWING(203),
    //ARROW(205),
    AOE(204);

    private final int imageNo;

    Tower(int imageNo) {
        this.imageNo = imageNo;
    }

    public int getImageNo(){
        return imageNo;
    }

}
