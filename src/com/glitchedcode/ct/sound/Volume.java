package com.glitchedcode.ct.sound;

public enum Volume {

    MUTE(-1000F),
    LOW(-3.0103F),
    REGULAR(0F),
    HIGH(3.0103F),
    MAX(6.0206F);

    private final float f;

    Volume(float f) {
        this.f = f;
    }

    public float getValue() {
        return f;
    }
}