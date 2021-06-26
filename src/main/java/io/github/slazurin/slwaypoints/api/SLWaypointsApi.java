package io.github.slazurin.slwaypoints.api;

import io.github.slazurin.slwaypoints.SLWaypoints;
import io.github.slazurin.slwaypoints.beans.Waypoint;
import io.github.slazurin.slwaypoints.ymlstore.WaypointsStore;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SLWaypointsApi {
    private final SLWaypoints plugin;
    private final WaypointsStore waypointsStore;
    public SLWaypointsApi(SLWaypoints plugin) {
        this.plugin = plugin;
        this.waypointsStore = new WaypointsStore(this.plugin);
    }

    public List<Waypoint> getWaypoints() {
        FileConfiguration cache = this.waypointsStore.getCache();
        List<Waypoint> waypoints = new ArrayList<>();
        ConfigurationSection section = cache.getDefaultSection();
        if (section == null) {
            return waypoints;
        }
        for (String waypointName : cache.getDefaultSection().getKeys(false)) {
            Waypoint w = new Waypoint();
            w.setName(waypointName);
            w.setDesc(cache.getString(waypointName + "." + "desc", ""));
            waypoints.add(w);
        }
        waypoints.sort((Waypoint w1, Waypoint w2) -> w1.getName().compareToIgnoreCase(w2.getName()));
        return waypoints;
    }

    public boolean hasWaypoint(String waypointName) {
        return this.waypointsStore.getCache().isSet(waypointName);
    }

    public void setWaypoint(String name, String desc, Location l) {
        FileConfiguration cache = this.waypointsStore.getCache();
        cache.set(name + ".world", l.getWorld().getName());
        cache.set(name + ".x", l.getX());
        cache.set(name + ".y", l.getY());
        cache.set(name + ".z", l.getZ());
        cache.set(name + ".pitch", l.getPitch());
        cache.set(name + ".yaw", l.getYaw());
        if (!(desc == null || desc.equals(""))) {
            cache.set(name + ".desc", desc);
        }
        this.waypointsStore.saveStore();
    }
}
