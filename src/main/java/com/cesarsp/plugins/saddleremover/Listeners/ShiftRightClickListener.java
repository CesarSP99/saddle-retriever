package com.cesarsp.plugins.saddleremover.Listeners;

import com.cesarsp.plugins.saddleremover.SaddleRemover;
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
        FileConfiguration config = SaddleRemover.getInstance().getConfig();
        ConfigurationSection messages = config.getConfigurationSection("messages");
        String pigNoPermission = messages != null ? messages.getString("no-permission-pig") : "No permission";
        String striderNoPermission = messages != null ? messages.getString("no-permission-strider") : "No permission";
        if (config.getBoolean("use-permissions")) {
            if (steerableEntity instanceof Pig) {
                if (player.hasPermission("saddleremover.pig")) {
                    steerableEntity.setSaddle(false);
                    player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
                } else {
                    player.sendMessage(pigNoPermission != null ? pigNoPermission : "No permission");
                }
            } else {
                if (player.hasPermission("saddleremover.strider")) {
                    steerableEntity.setSaddle(false);
                    player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
                } else {
                    player.sendMessage(striderNoPermission != null ? striderNoPermission : "No permission");
                }
            }
        } else {
            steerableEntity.setSaddle(false);
            player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
        }
    }
}
