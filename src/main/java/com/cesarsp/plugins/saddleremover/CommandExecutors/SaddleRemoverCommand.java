package com.cesarsp.plugins.saddleremover.CommandExecutors;

import com.cesarsp.plugins.saddleremover.SaddleRemover;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
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
            TextComponent message = new TextComponent("Plugin settings reloaded successfully :)");
            message.setColor(ChatColor.AQUA);
            sender.spigot().sendMessage(message);
        } else {
            TextComponent message = new TextComponent("You don't have permissions to perform this action");
            message.setColor(ChatColor.RED);
            sender.spigot().sendMessage(message);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length == 1 ? completions : new ArrayList<>();
    }
}