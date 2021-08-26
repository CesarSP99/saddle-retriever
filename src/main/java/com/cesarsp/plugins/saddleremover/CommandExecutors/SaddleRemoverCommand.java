package com.cesarsp.plugins.saddleremover.CommandExecutors;

import com.cesarsp.plugins.saddleremover.SaddleRemover;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaddleRemoverCommand implements CommandExecutor, TabCompleter {

    private final List<String> completions = Collections.singletonList("reload");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0 && args[0].equals("reload") && sender.hasPermission("saddleremover.reload")) {
            SaddleRemover.getInstance().reloadConfig();
            sender.sendMessage("Plugin settings reloaded successfully :)");
        } else {
            sender.sendMessage("You don't have permissions to perform this action");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length == 1 ? completions : new ArrayList<>();
    }
}