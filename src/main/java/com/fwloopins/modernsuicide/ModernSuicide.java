package com.fwloopins.modernsuicide;

import com.fwloopins.modernsuicide.command.SuicideCommand;
import com.fwloopins.modernsuicide.hook.ModernSuicidePlaceholderExpansion;
import com.fwloopins.modernsuicide.manager.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModernSuicide extends JavaPlugin {
    private static ModernSuicide instance;
    public static DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        loadConfig();
        registerCommands();
        initialiseManagers();

        dataManager.loadData();

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new ModernSuicidePlaceholderExpansion(this).register();
        }
    }

    @Override
    public void onDisable() {
        dataManager.saveData();
    }

    private void loadConfig() {
        saveDefaultConfig();
        reloadConfig();
    }

    private void registerCommands() {
        getCommand("suicide").setExecutor(new SuicideCommand());
    }

    private void initialiseManagers() {
        dataManager = new DataManager();
    }

    public static ModernSuicide getInstance() {
        return instance;
    }
}
