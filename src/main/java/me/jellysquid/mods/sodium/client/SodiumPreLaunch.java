package me.jellysquid.mods.sodium.client;

import me.jellysquid.mods.sodium.client.util.workarounds.PreLaunchChecks;
import me.jellysquid.mods.sodium.client.util.workarounds.Workarounds;
import me.jellysquid.mods.sodium.client.util.workarounds.probe.GraphicsAdapterProbe;

public class SodiumPreLaunch {

    public static void onPreLaunch() {
        GraphicsAdapterProbe.findAdapters();
        PreLaunchChecks.checkDrivers();
        Workarounds.init();
    }
}
