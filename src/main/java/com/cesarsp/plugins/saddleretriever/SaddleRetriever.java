package com.cesarsp.plugins.saddleretriever;

import com.cesarsp.plugins.saddleretriever.CommandExecutors.SaddleRetrieverCommand;
import com.cesarsp.plugins.saddleretriever.Listeners.ShiftRightClickListener;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class SaddleRetriever extends JavaPlugin {

    private static SaddleRetriever instance;
    private FileConfiguration config;
    private final List<String> aliases = Collections.singletonList("sr");

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = this.getLogger();
        logger.info("Trying to initialize SaddleRetriever...");
        try {
            instance = this;
            reloadConfig();
            registerCommands();
            registerEvents();
            logger.info("SaddleRetriever was initialized successfully! :D");
        } catch (Exception e) {
            logger.severe("An error has occurred while trying to initialize SaddleRetriever");
            logger.severe(e.toString());
            getPluginLoader().disablePlugin(this);
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ShiftRightClickListener(), this);
    }

    private void registerCommands() {
        TabCompleter tabCompleter = new SaddleRetrieverCommand();
        CommandExecutor executor = (CommandExecutor) tabCompleter;
        PluginCommand command = this.getCommand("saddleretriever");
        if(command != null){
            command.setAliases(aliases);
            command.setTabCompleter(tabCompleter);
            command.setExecutor(executor);
        }
        else{
            this.getLogger().severe("Unable to register commands");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals("reload")) return super.onCommand(sender, command, label, args);
        reloadConfig(); // Load config
        sender.sendMessage("SaddleRetriever's configuration has been reloaded");
        return true;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger logger = this.getLogger();
        logger.info("Disabling SaddleRetriever");
    }

    public static SaddleRetriever getInstance() {
        return instance;
    }
}
