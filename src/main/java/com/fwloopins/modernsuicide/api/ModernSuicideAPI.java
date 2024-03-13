package com.fwloopins.modernsuicide.api;

import com.fwloopins.modernsuicide.ModernSuicide;
import com.fwloopins.modernsuicide.manager.DataManager;
import com.fwloopins.modernsuicide.object.PlayerData;
import org.bukkit.entity.Player;

import java.util.List;

public class ModernSuicideAPI {
    private static ModernSuicideAPI instance;

    public static ModernSuicideAPI getInstance() {
        if (instance == null)
            instance = new ModernSuicideAPI();

        return instance;
    }

    /**
     *
     * @return The total amount of suicides through ModernSuicide of all players
     */
    public int getTotalSuicides() {
        return ModernSuicide.dataManager.getTotalSuicides();
    }

    /**
     *
     * @param player The player you want to check
     * @return The total amount of times the specified player has suicided through ModernSuicide
     */
    public int getTotalSuicides(Player player) {
        return ModernSuicide.dataManager.getTotalSuicides(player);
    }

    /**
     *
     * @param player The player to change the suicide count of
     * @param value The value to set the player's suicides to, values lower than 0 will not work
     */
    public void setTotalSuicides(Player player, int value) {
        if (value < 0) return;

        List<PlayerData> playerDataList = DataManager.playerDataList;
        for (PlayerData playerData : playerDataList) {
            if (playerData.getUUID().equals(player.getUniqueId())) {
                playerData.setTotalSuicides(value);
                return;
            }
        }

        playerDataList.add(new PlayerData(player.getUniqueId(), value));
    }
}
