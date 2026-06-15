package fr.xephi.authme.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import javax.inject.Inject;

/**
 * Listener of player events for events introduced in Minecraft 1.9.
 */
public class PlayerListener19 implements Listener {

    @Inject
    private ListenerService listenerService;

    @Inject
    private InventoryRestrictionNotifier inventoryRestrictionNotifier;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (listenerService.shouldCancelEvent(event)) {
            event.setCancelled(true);
            inventoryRestrictionNotifier.notifyDenied(event.getPlayer(), "swap_hands");
        }
    }
}
