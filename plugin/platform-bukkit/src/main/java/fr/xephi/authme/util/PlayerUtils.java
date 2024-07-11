package fr.xephi.authme.util;

import org.bukkit.entity.Player;

/**
 * Player utilities.
 */
public final class PlayerUtils {

    // Utility class
    private PlayerUtils() {
    }
    private static final boolean isLeavesServer = Utils.isClassLoaded("top.leavesmc.leaves.LeavesConfig") || Utils.isClassLoaded("org.leavesmc.leaves.LeavesConfig");

    /**
     * Returns the IP of the given player.
     *
     * @param player The player to return the IP address for
     * @return The player's IP address
     */
    public static String getPlayerIp(Player player) {
        return player.getAddress().getAddress().getHostAddress();
    }

    /**
     * Returns if the player is an NPC or not.
     *
     * @param player The player to check
     * @return True if the player is an NPC, false otherwise
     */
    public static boolean isNpc(Player player) {
        if (isLeavesServer) {
            return player.hasMetadata("NPC") || player.getAddress() == null;
        } else {
            return player.hasMetadata("NPC");
        }
    }

}
