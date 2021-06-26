package io.github.slazurin.slwaypoints.commands;

import io.github.slazurin.slwaypoints.SLWaypoints;
import io.github.slazurin.slwaypoints.beans.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DelWp implements TabExecutor {
    private final SLWaypoints plugin;
    public DelWp(SLWaypoints plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 1 || strings[0].length() == 0) {
            commandSender.sendMessage(ChatColor.RED + "Expected waypoint name.");
            return false;
        }
        delWp(commandSender, strings[0]);

        return true;
    }

    private void delWp(@NotNull CommandSender commandSender, String name) {
        if (this.plugin.getApi().wpExists(name)) {
            this.plugin.getApi().delWp(name);
            commandSender.sendMessage(ChatColor.RED + name + " deleted");

        } else {
            commandSender.sendMessage(ChatColor.RED + name + " doesn't exist");
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> l = new ArrayList<>();
        if (strings.length == 1) {
            List<Waypoint> wps = this.plugin.getApi().getWaypoints();
            for (Waypoint wp : wps) {
                if (wp.getName().startsWith(strings[0])) {
                    l.add(wp.getName());
                }
            }
        }

        return l;
    }
}
