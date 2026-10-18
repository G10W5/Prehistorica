package com.prehistorica.fabric;

import com.prehistorica.Prehistorica;
import com.prehistorica.platform.Platform;
import net.fabricmc.api.ModInitializer;

public final class PrehistoricaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Prehistorica.init(new FabricPlatform());
    }
}
