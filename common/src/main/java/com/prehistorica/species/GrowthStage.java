package com.prehistorica.species;

public enum GrowthStage {
    HATCHLING(0.35f),
    JUVENILE(0.65f),
    ADULT(1.0f);

    private final float scaleMultiplier;

    GrowthStage(float scaleMultiplier) {
        this.scaleMultiplier = scaleMultiplier;
    }

    public float scaleMultiplier() {
        return scaleMultiplier;
    }

    public GrowthStage next() {
        GrowthStage[] stages = values();
        if (ordinal() + 1 >= stages.length) {
            return ADULT;
        }
        return stages[ordinal() + 1];
    }
}
