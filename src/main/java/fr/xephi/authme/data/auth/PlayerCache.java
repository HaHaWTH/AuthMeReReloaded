package fr.xephi.authme.data.auth;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Connection-bound authentication state machine.
 * <p>
 * Live authentication is owned by one exact {@link Player} object representing one server connection.
 * Only that connection may transition itself {@code CONNECTED_UNAUTHENTICATED -> AUTHENTICATED}.
 * Only that connection may transition itself {@code AUTHENTICATED -> CONNECTED_UNAUTHENTICATED} for logout.
 * Only that connection may transition itself to disconnected on quit.
 * <p>
 * Name-based APIs are preserved for compatibility/offline/admin paths only.
 * They must not authorize a live {@link Player}.
 */
public class PlayerCache {

    private final ConcurrentMap<String, ConnectionState> connections = new ConcurrentHashMap<>();

    PlayerCache() {
    }

    public void registerConnection(Player player) {
        Objects.requireNonNull(player, "player must not be null");
        String name = normalizeName(player.getName());
        UUID uuid = player.getUniqueId();
        connections.compute(name, (key, oldState) ->
            ConnectionState.unauthenticated(player, uuid));
    }

    public boolean authenticate(Player player, PlayerAuth auth) {
        Objects.requireNonNull(player, "player must not be null");
        Objects.requireNonNull(auth, "auth must not be null");
        String name = normalizeName(player.getName());
        UUID uuid = player.getUniqueId();

        for (;;) {
            ConnectionState current = connections.get(name);
            if (current == null) {
                return false;
            }
            if (!current.belongsTo(player)) {
                return false;
            }
            if (current.isAuthenticated()) {
                return true;
            }
            ConnectionState updated = current.withAuth(auth);
            if (connections.replace(name, current, updated)) {
                return true;
            }
        }
    }

    public Optional<PlayerAuth> deauthenticate(Player player) {
        Objects.requireNonNull(player, "player must not be null");
        String name = normalizeName(player.getName());

        for (;;) {
            ConnectionState current = connections.get(name);
            if (current == null) {
                return Optional.empty();
            }
            if (!current.belongsTo(player)) {
                return Optional.empty();
            }
            if (!current.isAuthenticated()) {
                return Optional.empty();
            }
            ConnectionState updated = current.withoutAuth();
            if (connections.replace(name, current, updated)) {
                return Optional.ofNullable(current.getAuth().orElse(null));
            }
        }
    }

    public Optional<ConnectionSnapshot> disconnect(Player player) {
        Objects.requireNonNull(player, "player must not be null");
        String name = normalizeName(player.getName());

        for (;;) {
            ConnectionState current = connections.get(name);
            if (current == null) {
                return Optional.empty();
            }
            if (!current.belongsTo(player)) {
                return Optional.empty();
            }
            if (connections.remove(name, current)) {
                return Optional.of(current.snapshot(name));
            }
        }
    }

    public AuthenticationState getAuthenticationState(Player player) {
        Objects.requireNonNull(player, "player must not be null");
        String name = normalizeName(player.getName());
        ConnectionState state = connections.get(name);
        if (state == null) {
            return AuthenticationState.NO_CONNECTION;
        }
        if (!state.belongsTo(player)) {
            return AuthenticationState.CONNECTION_MISMATCH;
        }
        return state.isAuthenticated()
            ? AuthenticationState.AUTHENTICATED
            : AuthenticationState.CONNECTED_UNAUTHENTICATED;
    }

    public boolean isAuthenticated(Player player) {
        return getAuthenticationState(player) == AuthenticationState.AUTHENTICATED;
    }

    public Optional<PlayerAuth> getAuth(Player player) {
        Objects.requireNonNull(player, "player must not be null");
        String name = normalizeName(player.getName());
        ConnectionState state = connections.get(name);
        if (state == null || !state.belongsTo(player)) {
            return Optional.empty();
        }
        return state.getAuth();
    }

    public void updatePlayer(PlayerAuth auth) {
        if (auth == null) {
            return;
        }
        String name = normalizeName(auth.getNickname());
        connections.computeIfPresent(name, (key, state) -> {
            if (state.isAuthenticated()) {
                return state.withAuth(auth);
            }
            return state;
        });
    }

    public void removePlayer(String user) {
        connections.remove(normalizeName(user));
    }

    public boolean isAuthenticated(String user) {
        String name = normalizeName(user);
        ConnectionState state = connections.get(name);
        return state != null && state.isAuthenticated();
    }

    public PlayerAuth getAuth(String user) {
        String name = normalizeName(user);
        ConnectionState state = connections.get(name);
        if (state == null) {
            return null;
        }
        return state.getAuth().orElse(null);
    }

    public int getLogged() {
        return (int) connections.values().stream().filter(ConnectionState::isAuthenticated).count();
    }

    public Map<String, PlayerAuth> getCache() {
        Map<String, PlayerAuth> snapshot = new HashMap<>();
        connections.forEach((name, state) -> {
            state.getAuth().ifPresent(auth -> snapshot.put(name, auth));
        });
        return Collections.unmodifiableMap(snapshot);
    }

    private static String normalizeName(String name) {
        return name.toLowerCase(Locale.ROOT);
    }

    public enum AuthenticationState {
        NO_CONNECTION,
        CONNECTED_UNAUTHENTICATED,
        AUTHENTICATED,
        CONNECTION_MISMATCH
    }

    public static final class ConnectionSnapshot {
        private final String normalizedName;
        private final UUID uniqueId;
        private final PlayerAuth auth;

        ConnectionSnapshot(String normalizedName, UUID uniqueId, PlayerAuth auth) {
            this.normalizedName = normalizedName;
            this.uniqueId = uniqueId;
            this.auth = auth;
        }

        public String getNormalizedName() {
            return normalizedName;
        }

        public UUID getUniqueId() {
            return uniqueId;
        }

        public Optional<PlayerAuth> getAuth() {
            return Optional.ofNullable(auth);
        }

        public boolean wasAuthenticated() {
            return auth != null;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ConnectionSnapshot)) {
                return false;
            }
            ConnectionSnapshot that = (ConnectionSnapshot) o;
            return Objects.equals(normalizedName, that.normalizedName)
                && Objects.equals(uniqueId, that.uniqueId)
                && Objects.equals(auth, that.auth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(normalizedName, uniqueId, auth);
        }

        @Override
        public String toString() {
            return "ConnectionSnapshot{name=" + normalizedName + ",uuid=" + uniqueId + ",auth=" + (auth != null) + "}";
        }
    }

    private static final class ConnectionState {
        private final Player owner;
        private final UUID uniqueId;
        private final PlayerAuth auth;

        ConnectionState(Player owner, UUID uniqueId, PlayerAuth auth) {
            this.owner = owner;
            this.uniqueId = uniqueId;
            this.auth = auth;
        }

        static ConnectionState unauthenticated(Player owner, UUID uniqueId) {
            return new ConnectionState(owner, uniqueId, null);
        }

        ConnectionState withAuth(PlayerAuth auth) {
            return new ConnectionState(this.owner, this.uniqueId, auth);
        }

        ConnectionState withoutAuth() {
            return new ConnectionState(this.owner, this.uniqueId, null);
        }

        boolean belongsTo(Player candidate) {
            return this.owner == candidate;
        }

        boolean isAuthenticated() {
            return auth != null;
        }

        Optional<PlayerAuth> getAuth() {
            return Optional.ofNullable(auth);
        }

        ConnectionSnapshot snapshot(String normalizedName) {
            return new ConnectionSnapshot(normalizedName, uniqueId, auth);
        }
    }
}
