package com.fwloopins.modernsuicide.command.handler;

import com.fwloopins.modernsuicide.ModernSuicide;
import com.fwloopins.modernsuicide.api.ModernSuicideMessaging;
import com.fwloopins.modernsuicide.object.MethodHandler;
import com.fwloopins.modernsuicide.object.PlayerData;
import com.fwloopins.modernsuicide.util.CommandUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SuicideTopMethodHandler extends MethodHandler {
    private final CommandSender sender;

    public SuicideTopMethodHandler(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void handleMethod() {
        if (!CommandUtil.hasPermissionOrFalse(sender, "modernsuicide.command.suicide.top")) return;

        List<PlayerData> playerDataList = ModernSuicide.dataManager.getDescendingSortedSuicides();

        if (playerDataList.isEmpty()) {
            ModernSuicideMessaging.sendErrorMessage(sender, "Nobody has suicided on this server");
            return;
        }

        Component component = Component.text("---", NamedTextColor.DARK_GRAY)
                .append(Component.text(" Suicide Leaderboard ", TextColor.color(0xAD3E72)))
                .append(Component.text("---", NamedTextColor.DARK_GRAY));

        for (int i = 0; i < Math.min(playerDataList.size(), 9); i++) {
            PlayerData playerData = playerDataList.get(i);
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerData.getUUID());

            component = component.appendNewline()
                    .append(Component.text(i + 1 + ". ", NamedTextColor.GRAY))
                    .append(Component.text(player.getName() + ": ", TextColor.color(0xAD3E72)))
                    .append(Component.text(playerData.getTotalSuicides(), NamedTextColor.GRAY));
        }

        sender.sendMessage(component);
    }
}
