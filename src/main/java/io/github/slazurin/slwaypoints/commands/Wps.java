package io.github.slazurin.slwaypoints.commands;

import io.github.slazurin.slwaypoints.SLWaypoints;
import io.github.slazurin.slwaypoints.beans.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Wps implements TabExecutor {
    private final SLWaypoints plugin;
    public Wps(SLWaypoints plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 1) {
            commandSender.sendMessage(ChatColor.RED + "Expected 0 or 1 argument");
            return false;
        }

        int page = 1;

        if (strings.length != 0) {
            try {
                page = Integer.parseInt(strings[0]);
            } catch (NumberFormatException e) {
                commandSender.sendMessage(ChatColor.RED + "Page number invalid.");
                return false;
            }
        }
        listWaypoints(commandSender, page);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> l = new ArrayList<>();
        if (strings.length == 1) {
            l.add(ChatColor.ITALIC + "<page>");
        }
        return l;
    }

    private void listWaypoints(CommandSender p, int page) {
        List<Waypoint> waypoints = this.plugin.getApi().getWaypoints();
        int waypointsPerPage = 15;
        int pageCount = waypoints.size() / waypointsPerPage;
        if (waypoints.size() % waypointsPerPage != 0) {
            pageCount += 1;
        }
        if (page == 1 && waypoints.size() == 0) {
            p.sendMessage(ChatColor.RED  + "There is no waypoint added yet.");
            return;
        }
        if (page > pageCount || page < 1) {
            p.sendMessage(ChatColor.RED  + "Page number does not exist.");
            return;
        }

        p.sendMessage(ChatColor.GOLD + "Available waypoints: (Page " + page + "/" + pageCount + ", total: " + waypoints.size() +")");
        int displayNum = waypointsPerPage;
        if (page == pageCount && waypoints.size() % waypointsPerPage != 0) {
            displayNum = waypoints.size() % waypointsPerPage;
        }

        int offset = page * waypointsPerPage - waypointsPerPage;
        for (int i = 0; i < displayNum; i++) {
            Waypoint h = waypoints.get(offset+i);
            String w = "World";
            if (h.getWorld().contains("_the_end")) {
                w = "End";
            } else if (h.getWorld().contains("_nether")) {
                w = "Nether";
            }
            p.sendMessage(ChatColor.LIGHT_PURPLE + h.getName() + " " + ChatColor.YELLOW + h.getDesc() + ChatColor.GRAY + " (" + w + ")");
        }
    }
}
