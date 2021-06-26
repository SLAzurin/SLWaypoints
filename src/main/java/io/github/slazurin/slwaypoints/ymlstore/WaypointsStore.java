package io.github.slazurin.slwaypoints.ymlstore;

import io.github.slazurin.slwaypoints.SLWaypoints;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class WaypointsStore {
    private final SLWaypoints plugin;
    private FileConfiguration cache;
    private final File waypointsStore;

    public WaypointsStore(SLWaypoints plugin) {
        this.plugin = plugin;
        this.waypointsStore = new File(this.plugin.getDataFolder(), "waypoints.yml");
        this.createStoreIfNotExists();
        this.cache = YamlConfiguration.loadConfiguration(this.waypointsStore);

    }

    public void reloadStore() {
        this.cache = YamlConfiguration.loadConfiguration(this.waypointsStore);
    }

    public FileConfiguration getCache() {
        return cache;
    }

    public void saveStore() {
        try {
            getCache().save(waypointsStore);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "{0}Couldn't open waypointsStore file when saving!", ChatColor.RED);
        }
    }

    private void createStoreIfNotExists() {
        if (!this.waypointsStore.exists()) {
            this.waypointsStore.getParentFile().mkdirs();
            this.plugin.saveResource("waypoints.yml", false);
        }
    }
}
