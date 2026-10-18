package com.prehistorica.species;

public final class SpeciesDefinition {
    private final String id;
    private final String displayName;
    private final Diet diet;
    private final Temperament temperament;
    private final float maxHealth;
    private final float damage;
    private final float speed;
    private final float scale;
    private final float hitboxWidth;
    private final float hitboxHeight;
    private final int hatchlingTicks;
    private final int juvenileTicks;
    private final int gestationTicks;

    public SpeciesDefinition(
            String id,
            String displayName,
            Diet diet,
            Temperament temperament,
            float maxHealth,
            float damage,
            float speed,
            float scale,
            float hitboxWidth,
            float hitboxHeight,
            int hatchlingTicks,
            int juvenileTicks,
            int gestationTicks
    ) {
        this.id = id;
        this.displayName = displayName;
        this.diet = diet;
        this.temperament = temperament;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.speed = speed;
        this.scale = scale;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.hatchlingTicks = hatchlingTicks;
        this.juvenileTicks = juvenileTicks;
        this.gestationTicks = gestationTicks;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public Diet diet() {
        return diet;
    }

    public Temperament temperament() {
        return temperament;
    }

    public float maxHealth() {
        return maxHealth;
    }

    public float damage() {
        return damage;
    }

    public float speed() {
        return speed;
    }

    public float scale() {
        return scale;
    }

    public float hitboxWidth() {
        return hitboxWidth;
    }

    public float hitboxHeight() {
        return hitboxHeight;
    }

    public int hatchlingTicks() {
        return hatchlingTicks;
    }

    public int juvenileTicks() {
        return juvenileTicks;
    }

    public int gestationTicks() {
        return gestationTicks;
    }
}
