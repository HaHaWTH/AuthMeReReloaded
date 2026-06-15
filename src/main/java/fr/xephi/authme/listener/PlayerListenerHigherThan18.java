package fr.xephi.authme.listener;

import fr.xephi.authme.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import javax.inject.Inject;

public class PlayerListenerHigherThan18 implements Listener {
    @Inject
    private ListenerService listenerService;

    @Inject
    private Settings settings;

    @Inject
    private InventoryRestrictionNotifier inventoryRestrictionNotifier;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (listenerService.shouldCancelEvent(event)) {
            event.setCancelled(true);
            if (event.getEntity() instanceof Player) {
                inventoryRestrictionNotifier.notifyDenied((Player) event.getEntity(), "pickup_item");
            }
        }
    }

}
