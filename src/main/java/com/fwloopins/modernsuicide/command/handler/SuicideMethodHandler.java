package com.fwloopins.modernsuicide.command.handler;

import com.fwloopins.modernsuicide.ModernSuicide;
import com.fwloopins.modernsuicide.api.ModernSuicideMessaging;
import com.fwloopins.modernsuicide.object.MethodHandler;
import com.fwloopins.modernsuicide.event.SuicideEvent;
import com.fwloopins.modernsuicide.util.CommandUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SuicideMethodHandler extends MethodHandler {
    private final CommandSender sender;
    private static final Map<UUID, Long> cooldowns = new HashMap<>();

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

        UUID uuid = player.getUniqueId();
        if (hasCooldown(uuid)) {
            Long cooldownSeconds = getCooldownSeconds(uuid);
            if (cooldownSeconds == null) return;

            ModernSuicideMessaging.sendErrorMessage(sender, "You must wait " + cooldownSeconds + " seconds before suiciding again");
            return;
        }

        SuicideEvent suicideEvent = new SuicideEvent(player);
        if (!suicideEvent.callEvent()) {
            ModernSuicideMessaging.sendErrorMessage(player, suicideEvent.getCancelledMessage());
            return;
        }

        player.setHealth(0);
        ModernSuicide.dataManager.incrementSuicides(player);

        cooldowns.put(uuid, Instant.now().getEpochSecond());
    }

    private boolean hasCooldown(UUID uuid) {
        long cooldownTimeSeconds = ModernSuicide.getInstance().getConfig().getLong("cooldown_time_seconds");
        Long value = cooldowns.getOrDefault(uuid, null);

        return value != null && Instant.now().getEpochSecond() - value < cooldownTimeSeconds;
    }

    private Long getCooldownSeconds(UUID uuid) {
        Long value = cooldowns.getOrDefault(uuid, null);
        if (value == null) return null;

        long cooldownTimeSeconds = ModernSuicide.getInstance().getConfig().getLong("cooldown_time_seconds");
        long targetTime = value + cooldownTimeSeconds;
        return targetTime - Instant.now().getEpochSecond();
    }
}
