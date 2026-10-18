package com.prehistorica.neoforge;

import com.prehistorica.Prehistorica;
import com.prehistorica.platform.Platform;

public final class NeoForgePlatform implements Platform {
    @Override
    public String name() {
        return "neoforge";
    }

    @Override
    public void log(String message) {
        System.out.println("[Prehistorica/neoforge] " + message);
    }
}
