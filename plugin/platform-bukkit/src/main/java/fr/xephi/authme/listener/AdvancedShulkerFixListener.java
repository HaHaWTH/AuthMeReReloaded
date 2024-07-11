package fr.xephi.authme.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

//This fix is only for Minecraft 1.13-
public class AdvancedShulkerFixListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onDispenserActivate(BlockDispenseEvent event) {
        Block block = event.getBlock();

        if (block.getY() <= 0 || block.getY() >= block.getWorld().getMaxHeight() - 1) {
            event.setCancelled(true);
        }
    }
}
