package com.fwloopins.modernsuicide.command;

import com.fwloopins.modernsuicide.api.ModernSuicideMessaging;
import com.fwloopins.modernsuicide.command.handler.SuicideStatsMethodHandler;
import com.fwloopins.modernsuicide.command.handler.SuicideTopMethodHandler;
import com.fwloopins.modernsuicide.command.handler.SuicideMethodHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SuicideCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            SuicideMethodHandler smh = new SuicideMethodHandler(sender);
            smh.handleMethod();
            return true;
        }

        switch (args[0]) {
            case "stats": {
                SuicideStatsMethodHandler ssmh = new SuicideStatsMethodHandler(sender, args);
                ssmh.handleMethod();
                return true;
            }
            case "top": {
                SuicideTopMethodHandler stmh = new SuicideTopMethodHandler(sender);
                stmh.handleMethod();
                return true;
            }
            default: {
                ModernSuicideMessaging.sendErrorMessage(sender, "Invalid command argument provided");
                return true;
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> availableArguments = List.of("stats", "top");

        switch (args.length) {
            case 1: {
                if (args[0].isEmpty()) return availableArguments;

                return availableArguments.stream()
                        .filter(string -> string.startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }
            case 2: {
                if (args[0].equalsIgnoreCase("stats")) {
                    return getPlayerList(args[1]);
                }
            }
        }

        return null;
    }

    private List<String> getPlayerList(String arg) {
        List<String> playerList = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            playerList.add(player.getName());
        }

        for (int i = 0; i < playerList.size(); i++) {
            String name = playerList.get(i);
            if (!name.toLowerCase().startsWith(arg.toLowerCase()))
                playerList.remove(name);
        }

        return playerList;
    }
}
