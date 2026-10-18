package com.prehistorica.fabric;

import com.prehistorica.platform.Platform;

public final class FabricPlatform implements Platform {
    @Override
    public String name() {
        return "fabric";
    }

    @Override
    public void log(String message) {
        System.out.println("[Prehistorica/fabric] " + message);
    }
}
