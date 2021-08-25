package com.cesarsp.plugins.saddleremover;

import com.cesarsp.plugins.saddleremover.Listeners.ShiftRightClickListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SaddleRemover extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = this.getLogger();
        logger.info("Trying to initialize SaddleRemover...");
        try{
            getServer().getPluginManager().registerEvents(new ShiftRightClickListener(), this);
            logger.info("SaddleRemover was initialized successfully! :D");
        }
        catch (Exception e){
            logger.severe("An error has occurred");
            logger.severe(e.toString());
            getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger logger = this.getLogger();
        logger.info("Disabling Saddle Remover");
    }
}
