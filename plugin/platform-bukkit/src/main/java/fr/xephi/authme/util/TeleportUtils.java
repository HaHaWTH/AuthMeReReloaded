package fr.xephi.authme.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.CompletableFuture;

/**
 * This class is a utility class for handling async teleportation of players in game.
 */
public class TeleportUtils {
    private static MethodHandle teleportAsyncMethodHandle;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            teleportAsyncMethodHandle = lookup.findVirtual(Player.class, "teleportAsync", MethodType.methodType(CompletableFuture.class, Location.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            teleportAsyncMethodHandle = null;
            // if not, set method handle to null
        }
    }

    /**
     * Teleport a player to a specified location.
     *
     * @param player   The player to be teleported
     * @param location Where should the player be teleported
     */
    public static void teleport(Player player, Location location) {
        if (teleportAsyncMethodHandle != null) {
            try {
                teleportAsyncMethodHandle.invoke(player, location);
            } catch (Throwable throwable) {
                player.teleport(location);
            }
        } else {
            player.teleport(location);
        }
    }
}
