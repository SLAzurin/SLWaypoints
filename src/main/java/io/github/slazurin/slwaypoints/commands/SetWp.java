package io.github.slazurin.slwaypoints.commands;

import io.github.slazurin.slwaypoints.SLWaypoints;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetWp implements TabExecutor {
    private final SLWaypoints plugin;
    public SetWp(SLWaypoints plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to set a new waypoint");
            return false;
        }

        if (strings.length < 1 || strings[0].length() == 0) {
            p.sendMessage(ChatColor.RED + "Don't forget the waypoint name!");
            return false;
        }
        if (strings[0].contains(".")) {
            p.sendMessage(ChatColor.RED + "Cannot have period in name.");
            return false;
        }
        if (this.plugin.getApi().wpExists(strings[0])) {
            p.sendMessage(ChatColor.RED + strings[0] + " already exists.");
            return false;
        }
        this.setWp(p, strings);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }

    private void setWp(Player p, String[] args) {
        if (this.plugin.getApi().hasWaypoint(args[0])) {
            p.sendMessage(ChatColor.RED + args[0] + " is already set.");
            return;
        }
        String desc = null;
        if (args.length > 1) {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                strBuilder.append(args[i]).append(" ");
            }
            desc = strBuilder.toString().trim();
        }
        this.plugin.getApi().setWaypoint(args[0], desc, p.getLocation());
        p.sendMessage(ChatColor.AQUA + args[0] + " is now set " + (desc != null ? "with description: \"" + desc + "\"" : ""));
    }
}
