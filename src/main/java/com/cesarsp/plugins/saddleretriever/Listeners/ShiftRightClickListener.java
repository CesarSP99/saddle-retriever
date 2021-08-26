package com.cesarsp.plugins.saddleretriever.Listeners;

import com.cesarsp.plugins.saddleretriever.SaddleRetriever;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ShiftRightClickListener implements Listener {

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent playerEntityInteraction) {
        Entity entity = playerEntityInteraction.getRightClicked();
        Player player = playerEntityInteraction.getPlayer();
        if ((entity instanceof Pig || entity instanceof Strider)
                && playerEntityInteraction.getHand() == EquipmentSlot.OFF_HAND
                && player.isSneaking()
                && player.getInventory().getItemInMainHand().getType() == Material.AIR
                && ((Steerable) entity).hasSaddle()) {
            tryToRemoveSaddle(player, (Steerable) entity);
            //player.sendMessage("Fired");
        }
    }

    private void tryToRemoveSaddle(Player player, Steerable steerableEntity) {
        FileConfiguration config = SaddleRetriever.getInstance().getConfig();
        ConfigurationSection messages = config.getConfigurationSection("messages");
        String pigNoPermission = messages != null ? messages.getString("no-permission-pig") : "No permission";
        String striderNoPermission = messages != null ? messages.getString("no-permission-strider") : "No permission";
        if (config.getBoolean("use-permissions")) {
            if (steerableEntity instanceof Pig) {
                if (player.hasPermission("saddleretriever.pig")) {
                    steerableEntity.setSaddle(false);
                    player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
                } else {
                    TextComponent message = new TextComponent(pigNoPermission != null ? pigNoPermission : "No permission");
                    message.setColor(ChatColor.RED);
                    player.spigot().sendMessage(message);
                }
            } else {
                if (player.hasPermission("saddleretriever.strider")) {
                    steerableEntity.setSaddle(false);
                    player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
                } else {
                    TextComponent message = new TextComponent(striderNoPermission != null ? striderNoPermission : "No permission");
                    message.setColor(ChatColor.RED);
                    player.spigot().sendMessage(message);
                }
            }
        } else {
            steerableEntity.setSaddle(false);
            player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
        }
    }
}
