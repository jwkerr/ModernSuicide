package com.fwloopins.modernsuicide.hook;

import com.fwloopins.modernsuicide.ModernSuicide;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class ModernSuicidePlaceholderExpansion extends PlaceholderExpansion {
    private final ModernSuicide plugin;

    public ModernSuicidePlaceholderExpansion(ModernSuicide plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "modernsuicide";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        if (identifier.equalsIgnoreCase("total_suicides")) {
            if (player == null) {
                return String.valueOf(ModernSuicide.dataManager.getTotalSuicides());
            } else {
                return String.valueOf(ModernSuicide.dataManager.getTotalSuicides(player));
            }
        }

        return null;
    }
}
