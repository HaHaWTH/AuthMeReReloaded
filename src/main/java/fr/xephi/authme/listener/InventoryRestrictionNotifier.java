package fr.xephi.authme.listener;

import fr.xephi.authme.data.auth.PlayerCache;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.util.expiring.ExpiringSet;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class InventoryRestrictionNotifier {

    private static final long MESSAGE_COOLDOWN_SECONDS = 2L;

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(InventoryRestrictionNotifier.class);
    private final Messages messages;
    private final PlayerCache playerCache;
    private final ExpiringSet<UUID> notifiedPlayers = new ExpiringSet<>(MESSAGE_COOLDOWN_SECONDS, TimeUnit.SECONDS);

    @Inject
    InventoryRestrictionNotifier(Messages messages, PlayerCache playerCache) {
        this.messages = messages;
        this.playerCache = playerCache;
    }

    public void notifyDenied(Player player, String action) {
        Objects.requireNonNull(player, "player must not be null");
        Objects.requireNonNull(action, "action must not be null");

        UUID uuid = player.getUniqueId();
        if (notifiedPlayers.contains(uuid)) {
            return;
        }
        notifiedPlayers.add(uuid);

        messages.send(player, MessageKey.DENIED_INVENTORY);

        PlayerCache.AuthenticationState state = playerCache.getAuthenticationState(player);
        if (state == PlayerCache.AuthenticationState.CONNECTION_MISMATCH) {
            logger.warning("INVENTORY_ACTION_DENIED action='" + action
                + "' player='" + player.getName()
                + "' uuid=" + uuid
                + " state=" + state);
        } else {
            logger.fine("INVENTORY_ACTION_DENIED action='" + action
                + "' player='" + player.getName()
                + "' uuid=" + uuid
                + " state=" + state);
        }
    }

    public void clear(Player player) {
        if (player != null) {
            notifiedPlayers.remove(player.getUniqueId());
        }
    }
}
