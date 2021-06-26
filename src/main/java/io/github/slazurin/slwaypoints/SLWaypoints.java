package io.github.slazurin.slwaypoints;

import io.github.slazurin.slwaypoints.api.SLWaypointsApi;
import io.github.slazurin.slwaypoints.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class SLWaypoints extends JavaPlugin {
    private SLWaypointsApi api;

    @Override
    public void onEnable() {
        this.api = new SLWaypointsApi(this);
        this.registerCommands();
        getLogger().info("Enabled SLWaypoints");
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabled SLWaypoints");
    }

    private void registerCommands() {
        this.getCommand("wp").setExecutor(new Wp(this));
        this.getCommand("wps").setExecutor(new Wps(this));
        this.getCommand("setwp").setExecutor(new SetWp(this));
        this.getCommand("delwp").setExecutor(new DelWp(this));
        this.getCommand("debuginfo").setExecutor(new DebugInfo(this));
    }

    public SLWaypointsApi getApi() {
        return api;
    }
}
