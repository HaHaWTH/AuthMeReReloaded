package fr.xephi.authme.process.login;

import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Tracks trusted force-login requests before the async login process updates PlayerCache.
 */
public class ForceLoginRequestService {

    private static final int MAX_ATTEMPTS = 20;
    private static final long RETRY_DELAY_TICKS = 5L;

    private final Set<String> pendingNames = ConcurrentHashMap.newKeySet();
    private final Set<String> runningNames = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<String, Integer> attemptCounts = new ConcurrentHashMap<>();

    ForceLoginRequestService() {
    }

    public void markPending(Player player) {
        if (player == null) {
            return;
        }
        markPending(player.getName());
    }

    public void markPending(String name) {
        normalize(name).ifPresent(normalizedName -> {
            pendingNames.add(normalizedName);
            attemptCounts.putIfAbsent(normalizedName, 0);
        });
    }

    public boolean beginAttempt(Player player) {
        if (player == null) {
            return false;
        }
        return normalize(player.getName())
            .filter(pendingNames::contains)
            .filter(runningNames::add)
            .isPresent();
    }

    public int incrementAttempt(Player player) {
        if (player == null) {
            return 0;
        }
        return normalize(player.getName())
            .map(name -> attemptCounts.merge(name, 1, Integer::sum))
            .orElse(0);
    }

    public boolean canRetry(Player player) {
        if (player == null) {
            return false;
        }
        return normalize(player.getName())
            .map(name -> attemptCounts.getOrDefault(name, 0) < MAX_ATTEMPTS)
            .orElse(false);
    }

    public void finishAttempt(Player player) {
        if (player == null) {
            return;
        }
        normalize(player.getName()).ifPresent(runningNames::remove);
    }

    public void clear(Player player) {
        if (player == null) {
            return;
        }
        clear(player.getName());
    }

    public void clear(String name) {
        normalize(name).ifPresent(normalizedName -> {
            pendingNames.remove(normalizedName);
            runningNames.remove(normalizedName);
            attemptCounts.remove(normalizedName);
        });
    }

    public boolean isPending(String name) {
        return normalize(name).filter(pendingNames::contains).isPresent();
    }

    public long retryDelayTicks() {
        return RETRY_DELAY_TICKS;
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
