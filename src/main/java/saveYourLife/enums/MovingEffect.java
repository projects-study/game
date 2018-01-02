package saveYourLife.enums;

public enum MovingEffect {
    SLOWDOWN(0.5F, 1000000000L);

    private float effectStrength;
    private Long effectDuration;

    MovingEffect(float effectStrength, Long effectDuration){
        this.effectStrength = effectStrength;
        this.effectDuration = effectDuration;
    }

    public float getEffectStrength() {
        return effectStrength;
    }

    public Long getEffectDuration() {
        return effectDuration;
    }
}
