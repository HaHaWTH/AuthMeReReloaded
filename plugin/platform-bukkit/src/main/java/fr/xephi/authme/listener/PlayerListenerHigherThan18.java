package fr.xephi.authme.listener;

import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.PluginSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import javax.inject.Inject;

public class PlayerListenerHigherThan18 implements Listener {
    @Inject
    private ListenerService listenerService;

    @Inject
    private Settings settings;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (listenerService.shouldCancelEvent(event)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSwitchHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && player.hasPermission("keybindings.use") && settings.getProperty(PluginSettings.MENU_UNREGISTER_COMPATIBILITY)) {
            event.setCancelled(true);
            Bukkit.dispatchCommand(event.getPlayer(), "help");
        }
    }
}
