package com.prehistorica.neoforge;

import com.prehistorica.Prehistorica;
import com.prehistorica.platform.Platform;
import net.neoforged.fml.common.Mod;

@Mod(Prehistorica.MOD_ID)
public final class PrehistoricaNeoForge {
    public PrehistoricaNeoForge() {
        Prehistorica.init(new NeoForgePlatform());
    }
}
