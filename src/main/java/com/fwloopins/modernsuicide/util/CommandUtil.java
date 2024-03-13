package com.fwloopins.modernsuicide.util;

import com.fwloopins.modernsuicide.api.ModernSuicideMessaging;
import org.bukkit.command.CommandSender;

public class CommandUtil {

    public static boolean hasPermissionOrFalse(CommandSender sender, String permission) {
        if (!(sender.hasPermission(permission))) {
            ModernSuicideMessaging.sendErrorMessage(sender, "You do not have permission to send this command");
            return false;
        }

        return true;
    }
}
