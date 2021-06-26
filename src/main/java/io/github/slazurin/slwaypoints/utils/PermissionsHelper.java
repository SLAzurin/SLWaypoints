package io.github.slazurin.slwaypoints.utils;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PermissionsHelper {
    public static boolean checkPlayer(@NotNull CommandSender commandSender, String permission) {
        List<String> permHierachy = new ArrayList<>();
        permHierachy.add(permission);
        System.out.println(permission);
        String cuttingPerm = permission;
        do {
            cuttingPerm = cuttingPerm.substring(0, cuttingPerm.lastIndexOf("."));
            permHierachy.add(cuttingPerm + ".*");
            System.out.println(cuttingPerm + ".*");
        } while(cuttingPerm.lastIndexOf(".") != -1);

        for (String perm : permHierachy) {
            if (commandSender.hasPermission(perm)) {
                return true;
            }
        }
        return false;
    }
}
