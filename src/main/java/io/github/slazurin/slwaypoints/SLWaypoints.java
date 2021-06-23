package io.github.slazurin.slwaypoints;

import io.github.slazurin.slwaypoints.api.SLWaypointsApi;
import io.github.slazurin.slwaypoints.commands.DebugInfo;
import io.github.slazurin.slwaypoints.commands.SetWp;
import io.github.slazurin.slwaypoints.commands.Wp;
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
        this.getCommand("setwp").setExecutor(new SetWp(this));
        this.getCommand("debuginfo").setExecutor(new DebugInfo(this));
    }

    public SLWaypointsApi getApi() {
        return api;
    }
}
