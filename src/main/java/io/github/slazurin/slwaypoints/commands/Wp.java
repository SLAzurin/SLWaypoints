package io.github.slazurin.slwaypoints.commands;

import io.github.slazurin.slwaypoints.SLWaypoints;
import io.github.slazurin.slwaypoints.beans.Waypoint;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Wp implements TabExecutor {
    private final SLWaypoints plugin;
    public Wp(SLWaypoints plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command");
            return false;
        }
        if (strings.length != 1) {
            p.sendMessage(ChatColor.RED + "Expected a waypoint name.");
            return false;
        }
        wp(p,strings[0]);

        return true;
    }

    private void wp(Player p, String wpName) {
        if (!(this.plugin.getApi().wpExists(wpName))) {
            p.sendMessage(ChatColor.RED + wpName + " waypoint not set.");
            return;
        }
        p.teleport(this.plugin.getApi().getWaypoint(wpName));
        p.playNote(p.getLocation(), Instrument.BELL, Note.sharp(2, Note.Tone.F));
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
