package com.cesarsp.plugins.saddleremover.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShiftRightClickListener implements Listener {
    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent playerEntityInteraction) {
        Entity entity = playerEntityInteraction.getRightClicked();
        Player player = playerEntityInteraction.getPlayer();
        if ((entity instanceof Pig || entity instanceof Strider)
                && player.isSneaking()
                && player.getInventory().getItemInMainHand().getType() == Material.AIR
                && ((Steerable) entity).hasSaddle()) {
            ((Steerable) entity).setSaddle(false);
            player.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
        }
    }
}
