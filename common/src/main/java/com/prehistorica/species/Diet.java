package com.prehistorica.species;

public enum Diet {
    PLANTS,
    MEAT,
    FISH,
    MIXED;

    public boolean eatsMeat() {
        return this == MEAT || this == MIXED || this == FISH;
    }

    public boolean eatsPlants() {
        return this == PLANTS || this == MIXED;
    }
}
