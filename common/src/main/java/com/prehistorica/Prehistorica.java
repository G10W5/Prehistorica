package com.prehistorica;

import com.prehistorica.platform.Platform;
import com.prehistorica.species.SpeciesDefinition;
import com.prehistorica.species.SpeciesRegistry;

public final class Prehistorica {
    public static final String MOD_ID = "prehistorica";
    public static final String MOD_NAME = "Prehistorica";

    private Prehistorica() {
    }

    public static void init(Platform platform) {
        SpeciesRegistry.load();
        platform.log(SpeciesRegistry.count() + " species definitions loaded");
        for (SpeciesDefinition species : SpeciesRegistry.all()) {
            platform.log("  species: " + species.id() + " (" + species.displayName() + ", " + species.diet() + ")");
        }
    }
}
