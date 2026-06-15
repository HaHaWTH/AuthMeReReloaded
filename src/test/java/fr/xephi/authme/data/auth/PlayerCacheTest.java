package fr.xephi.authme.data.auth;

import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCacheTest {

    @Test
    void authenticatesOnlyRegisteredCurrentConnection() {
        PlayerCache cache = new PlayerCache();
        Player player = createPlayerProxy("TestPlayer", UUID.randomUUID());
        cache.registerConnection(player);

        PlayerAuth auth = PlayerAuth.builder()
            .name("TestPlayer")
            .realName("TestPlayer")
            .uuid(UUID.randomUUID())
            .build();

        assertTrue(cache.authenticate(player, auth));
        assertTrue(cache.isAuthenticated(player));
    }

    @Test
    void rejectsAuthenticationFromReplacedConnection() {
        PlayerCache cache = new PlayerCache();
        UUID uuid = UUID.randomUUID();
        Player oldPlayer = createPlayerProxy("TestPlayer", uuid);
        Player newPlayer = createPlayerProxy("TestPlayer", uuid);

        cache.registerConnection(oldPlayer);

        PlayerAuth auth = PlayerAuth.builder()
            .name("TestPlayer")
            .realName("TestPlayer")
            .uuid(uuid)
            .build();

        assertFalse(cache.authenticate(newPlayer, auth));
    }

    @Test
    void staleDisconnectCannotRemoveCurrentAuthenticatedConnection() {
        PlayerCache cache = new PlayerCache();
        UUID uuid = UUID.randomUUID();
        Player oldPlayer = createPlayerProxy("TestPlayer", uuid);
        Player newPlayer = createPlayerProxy("TestPlayer", uuid);

        cache.registerConnection(oldPlayer);
        cache.disconnect(oldPlayer);

        cache.registerConnection(newPlayer);

        PlayerAuth auth = PlayerAuth.builder()
            .name("TestPlayer")
            .realName("TestPlayer")
            .uuid(uuid)
            .build();

        assertTrue(cache.authenticate(newPlayer, auth));
    }

    @Test
    void duplicateDisconnectIsIdempotent() {
        PlayerCache cache = new PlayerCache();
        Player player = createPlayerProxy("TestPlayer", UUID.randomUUID());

        cache.registerConnection(player);

        var disconnect1 = cache.disconnect(player);
        var disconnect2 = cache.disconnect(player);

        assertTrue(disconnect1.isPresent());
        assertFalse(disconnect2.isPresent());
    }

    @Test
    void deauthenticateKeepsConnectionButRemovesAuthentication() {
        PlayerCache cache = new PlayerCache();
        Player player = createPlayerProxy("TestPlayer", UUID.randomUUID());

        cache.registerConnection(player);

        PlayerAuth auth = PlayerAuth.builder()
            .name("TestPlayer")
            .realName("TestPlayer")
            .uuid(UUID.randomUUID())
            .build();

        cache.authenticate(player, auth);

        var removed = cache.deauthenticate(player);

        assertTrue(removed.isPresent());
        assertEquals(
            PlayerCache.AuthenticationState.CONNECTED_UNAUTHENTICATED,
            cache.getAuthenticationState(player));
    }

    @Test
    void updatePlayerCannotCreateAuthentication() {
        PlayerCache cache = new PlayerCache();
        Player player = createPlayerProxy("TestPlayer", UUID.randomUUID());

        cache.registerConnection(player);

        PlayerAuth auth = PlayerAuth.builder()
            .name("TestPlayer")
            .realName("TestPlayer")
            .uuid(UUID.randomUUID())
            .build();

        cache.updatePlayer(auth);

        assertEquals(
            PlayerCache.AuthenticationState.CONNECTED_UNAUTHENTICATED,
            cache.getAuthenticationState(player));
    }

    @Test
    void cacheExposureIsImmutableSnapshot() {
        PlayerCache cache = new PlayerCache();
        Player player = createPlayerProxy("TestPlayer", UUID.randomUUID());

        cache.registerConnection(player);

        PlayerAuth auth = PlayerAuth.builder()
            .name("TestPlayer")
            .realName("TestPlayer")
            .uuid(UUID.randomUUID())
            .build();

        cache.authenticate(player, auth);

        var map = cache.getCache();
        assertThrows(UnsupportedOperationException.class, () -> map.put("key", auth));
    }

    private static Player createPlayerProxy(String name, UUID uuid) {
        return (Player) Proxy.newProxyInstance(
            Player.class.getClassLoader(),
            new Class<?>[]{Player.class},
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    switch (method.getName()) {
                        case "getName":
                            return name;
                        case "getUniqueId":
                            return uuid;
                        case "equals":
                            return proxy == args[0];
                        case "hashCode":
                            return System.identityHashCode(proxy);
                        case "toString":
                            return "PlayerProxy{" + name + "}";
                        default:
                            if (method.getReturnType() == boolean.class) {
                                return false;
                            }
                            if (method.getReturnType() == int.class) {
                                return 0;
                            }
                            if (method.getReturnType() == long.class) {
                                return 0L;
                            }
                            if (method.getReturnType() == float.class) {
                                return 0f;
                            }
                            if (method.getReturnType() == double.class) {
                                return 0d;
                            }
                            return null;
                    }
                }
            });
    }
}
