package com.fwloopins.modernsuicide.command.handler;

import com.fwloopins.modernsuicide.api.ModernSuicideMessaging;
import com.fwloopins.modernsuicide.object.MethodHandler;
import com.fwloopins.modernsuicide.manager.DataManager;
import com.fwloopins.modernsuicide.object.PlayerData;
import com.fwloopins.modernsuicide.util.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideStatsMethodHandler extends MethodHandler {
    private final CommandSender sender;
    private final String[] args;

    public SuicideStatsMethodHandler(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    @Override
    public void handleMethod() {
        if (!CommandUtil.hasPermissionOrFalse(sender, "modernsuicide.command.suicide.stats")) return;

        Player player;
        if (args.length <= 1) {
            if (!(sender instanceof Player senderPlayer)) {
                ModernSuicideMessaging.sendErrorMessage(sender, "Console has no suicide stats");
                return;
            }

            player = senderPlayer;
        } else {
            player = Bukkit.getPlayerExact(args[1]);
        }

        if (player == null) {
            ModernSuicideMessaging.sendErrorMessage(sender, "Specified player does not exist");
            return;
        }

        sendStatsMessage(sender, player);
    }

    private void sendStatsMessage(CommandSender sender, Player player) {
        for (PlayerData playerData : DataManager.playerDataList) {
            if (playerData.getUUID().equals(player.getUniqueId())) {
                ModernSuicideMessaging.sendSuccessMessage(sender, player.getName() + " has suicided " + playerData.getTotalSuicides() + " times");
                return;
            }
        }

        ModernSuicideMessaging.sendSuccessMessage(sender, player.getName() + " has never suicided");
    }
}
