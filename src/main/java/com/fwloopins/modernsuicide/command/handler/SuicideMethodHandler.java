package com.fwloopins.modernsuicide.command.handler;

import com.fwloopins.modernsuicide.ModernSuicide;
import com.fwloopins.modernsuicide.api.ModernSuicideMessaging;
import com.fwloopins.modernsuicide.command.MethodHandler;
import com.fwloopins.modernsuicide.event.SuicideEvent;
import com.fwloopins.modernsuicide.util.CommandUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideMethodHandler extends MethodHandler {
    private final CommandSender sender;

    public SuicideMethodHandler(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void handleMethod() {
        if (!(sender instanceof Player player)) {
            ModernSuicideMessaging.sendErrorMessage(sender, "Only players can use this command method");
            return;
        }

        if (!CommandUtil.hasPermissionOrFalse(sender, "modernsuicide.command.suicide.suicide")) return;

        SuicideEvent suicideEvent = new SuicideEvent(player);
        if (!suicideEvent.callEvent()) {
            ModernSuicideMessaging.sendErrorMessage(player, suicideEvent.getCancelledMessage());
            return;
        }

        player.setHealth(0);
        ModernSuicide.dataManager.incrementSuicides(player);
    }
}
