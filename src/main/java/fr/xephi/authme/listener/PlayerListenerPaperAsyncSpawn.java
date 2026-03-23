package fr.xephi.authme.listener;

import fr.xephi.authme.service.TeleportationService;
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import javax.inject.Inject;

/**
 * Paper 1.21.9+: handles join spawn before the player entity is fully available.
 * <p>
 * Replaces {@link org.spigotmc.event.player.PlayerSpawnLocationEvent} for Paper servers — that event is
 * deprecated for removal; using {@link org.bukkit.event.player.PlayerEvent#getPlayer()} for entity-related
 * logic is unsafe during the configuration phase (see Paper Javadoc).
 *
 * @see <a href="https://jd.papermc.io/paper/io/papermc/paper/event/player/AsyncPlayerSpawnLocationEvent.html">AsyncPlayerSpawnLocationEvent</a>
 */
public class PlayerListenerPaperAsyncSpawn implements Listener {

    @Inject
    private TeleportationService teleportationService;

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerSpawn(AsyncPlayerSpawnLocationEvent event) {
        PlayerListener19Spigot.setJoinSpawnLocationHookRan();
        String name = event.getConnection().getProfile().getName();
        if (name == null) {
            return;
        }
        Location customSpawn = teleportationService.prepareOnJoinSpawnLocation(name, event.getSpawnLocation());
        if (customSpawn != null) {
            event.setSpawnLocation(customSpawn);
        }
    }
}
