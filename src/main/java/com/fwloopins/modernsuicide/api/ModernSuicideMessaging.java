package com.fwloopins.modernsuicide.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

public class ModernSuicideMessaging {
    private static final Component PREFIX_COMPONENT = Component.text("ModernSuicide", TextColor.color(0xAD3E72)).append(Component.text(" » ", NamedTextColor.DARK_GRAY));

    public static void sendSuccessMessage(CommandSender sender, String message) {
        sendMessage(sender, message, NamedTextColor.GREEN);
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sendMessage(sender, message, NamedTextColor.RED);
    }

    private static void sendMessage(CommandSender sender, String message, TextColor colour) {
        sender.sendMessage(PREFIX_COMPONENT.append(Component.text(message, colour)));
    }
}
