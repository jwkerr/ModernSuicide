package com.fwloopins.modernsuicide.manager;

import com.fwloopins.modernsuicide.ModernSuicide;
import com.fwloopins.modernsuicide.object.PlayerData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager {
    public static List<PlayerData> playerDataList = new ArrayList<>();

    public void loadData() {
        File dataFolder = ModernSuicide.getInstance().getDataFolder();
        File file = new File(dataFolder, "data.json");

        if (!file.exists()) return;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonArray playerArray = gson.fromJson(sb.toString(), JsonArray.class);

            if (playerArray == null) return;

            for (JsonElement playerElement : playerArray) {
                JsonObject playerObject = playerElement.getAsJsonObject();
                UUID uuid = UUID.fromString(playerObject.get("uuid").getAsString());
                int totalSuicides = playerObject.get("totalSuicides").getAsInt();

                playerDataList.add(new PlayerData(uuid, totalSuicides));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        if (playerDataList.isEmpty()) return;

        JsonArray jsonArray = new JsonArray();
        for (PlayerData playerData : playerDataList) {
            JsonObject playerObject = new JsonObject();
            playerObject.addProperty("uuid", playerData.getUUID().toString());
            playerObject.addProperty("totalSuicides", playerData.getTotalSuicides());
            jsonArray.add(playerObject);
        }

        File dataFolder = ModernSuicide.getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdirs()) return;
        }

        File file = new File(dataFolder, "data.json");

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalSuicides() {
        int totalSuicides = 0;

        for (PlayerData playerData : playerDataList) {
            totalSuicides += playerData.getTotalSuicides();
        }

        return totalSuicides;
    }

    public Integer getTotalSuicides(OfflinePlayer player) {
        if (player == null) return null;

        for (PlayerData playerData : playerDataList) {
            if (playerData.getUUID().equals(player.getUniqueId()))
                return playerData.getTotalSuicides();
        }

        return 0;
    }

    public void incrementSuicides(OfflinePlayer player) {
        for (PlayerData playerData : playerDataList) {
            if (playerData.getUUID().equals(player.getUniqueId())) {
                playerData.incrementSuicides();
                return;
            }
        }

        playerDataList.add(new PlayerData(player.getUniqueId(), 1));
    }

    public List<PlayerData> getDescendingSortedSuicides() {
        List<PlayerData> sortedSuicides = new ArrayList<>(playerDataList);
        sortedSuicides.sort((a, b) -> Integer.compare(b.getTotalSuicides(), a.getTotalSuicides()));

        return sortedSuicides;
    }
}
