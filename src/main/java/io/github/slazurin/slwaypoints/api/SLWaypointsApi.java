package io.github.slazurin.slwaypoints.api;

import io.github.slazurin.slwaypoints.SLWaypoints;
import io.github.slazurin.slwaypoints.beans.Waypoint;
import io.github.slazurin.slwaypoints.ymlstore.WaypointsStore;
import org.bukkit.Location;
import org.bukkit.World;
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
        ConfigurationSection section = cache.getRoot();
        if (section == null) {
            return waypoints;
        }
        for (String waypointName : cache.getRoot().getKeys(false)) {
            Waypoint w = new Waypoint();
            w.setName(waypointName);
            w.setDesc(cache.getString(waypointName + "." + "desc", ""));
            w.setWorld(cache.getString(waypointName + "." + "world", ""));
            w.setX(cache.getDouble(waypointName + "." + "x", 0));
            w.setY(cache.getDouble(waypointName + "." + "y", 0));
            w.setZ(cache.getDouble(waypointName + "." + "z", 0));
            w.setPitch(Float.parseFloat(cache.getString(waypointName + ".pitch", "0")));
            w.setYaw(Float.parseFloat(cache.getString(waypointName + ".yaw", "0")));
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

    public boolean wpExists(String wpName) {
        return this.waypointsStore.getCache().isSet(wpName);
    }

    public Location getWaypoint(String wpName) {
        FileConfiguration cache = this.waypointsStore.getCache();
        World world = this.plugin.getServer().getWorld(cache.getString(wpName + ".world", "world"));
        double x = cache.getDouble(wpName + ".x");
        double y = cache.getDouble(wpName + ".y");
        double z = cache.getDouble(wpName + ".z");
        float pitch = Float.parseFloat(cache.getString(wpName + ".pitch", "0"));
        float yaw = Float.parseFloat(cache.getString(wpName + ".yaw", "0"));

        return new Location(world, x, y, z, yaw, pitch);
    }

    public void delWp(String name) {
        this.waypointsStore.getCache().set(name, null);
        this.waypointsStore.saveStore();
    }
}
