package fr.xephi.authme.process.login;

import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks trusted force-login requests before the async login process updates PlayerCache.
 */
public class ForceLoginRequestService {

    private final Set<String> pendingNames = ConcurrentHashMap.newKeySet();

    ForceLoginRequestService() {
    }

    public void markPending(Player player) {
        if (player == null) {
            return;
        }
        markPending(player.getName());
    }

    public void markPending(String name) {
        normalize(name).ifPresent(pendingNames::add);
    }

    public void clear(Player player) {
        if (player == null) {
            return;
        }
        clear(player.getName());
    }

    public void clear(String name) {
        normalize(name).ifPresent(pendingNames::remove);
    }

    public boolean isPending(String name) {
        return normalize(name).filter(pendingNames::contains).isPresent();
    }

    private Optional<String> normalize(String name) {
        if (name == null) {
            return Optional.empty();
        }
        String normalized = name.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(normalized);
    }
}
