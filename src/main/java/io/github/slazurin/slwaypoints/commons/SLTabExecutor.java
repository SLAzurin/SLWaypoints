package io.github.slazurin.slwaypoints.commons;

import io.github.slazurin.slwaypoints.SLWaypoints;
import io.github.slazurin.slwaypoints.utils.PermissionsHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

public abstract class SLTabExecutor implements TabExecutor {
    protected final SLWaypoints plugin;
    protected final String permission;

    protected SLTabExecutor(SLWaypoints plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (PermissionsHelper.checkPlayer(commandSender, this.permission)) {
            return onCommandExt(commandSender, command, s, strings);
        }
        return false;
    }

    public abstract boolean onCommandExt(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings);
}
