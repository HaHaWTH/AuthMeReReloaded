package fr.xephi.authme.command.executable.authme.debug;

import com.google.common.collect.ImmutableList;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.AdminPermission;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.DefaultPermission;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.permission.PermissionsManager;
import fr.xephi.authme.permission.PlayerPermission;
import fr.xephi.authme.permission.PlayerStatePermission;
import fr.xephi.authme.service.BukkitService;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Checks if a player has a given permission, as checked by AuthMe.
 */
class HasPermissionChecker implements DebugSection {

    static final List<Class<? extends PermissionNode>> PERMISSION_NODE_CLASSES = ImmutableList.of(
        AdminPermission.class, PlayerPermission.class, PlayerStatePermission.class, DebugSectionPermissions.class);

    @Inject
    private PermissionsManager permissionsManager;

    @Inject
    private BukkitService bukkitService;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "perm";
    }

    @Override
    public String getDescription() {
        return "Checks if a player has a given permission";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.DEBUG_PERM_TITLE);
        if (arguments.size() < 2) {
            messages.send(sender, MessageKey.DEBUG_PERM_USAGE_LINE1);
            messages.send(sender, MessageKey.DEBUG_PERM_USAGE_EXAMPLE);
            messages.send(sender, MessageKey.DEBUG_PERM_SYSTEM,
                String.valueOf(permissionsManager.getPermissionSystem()));
            return;
        }

        final String playerName = arguments.get(0);
        final String permissionNode = arguments.get(1);

        Player player = bukkitService.getPlayerExact(playerName);
        if (player == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            if (offlinePlayer == null) {
                messages.send(sender, MessageKey.DEBUG_PERM_PLAYER_NOT_EXIST, playerName);
            } else {
                messages.send(sender, MessageKey.DEBUG_PERM_OFFLINE_CHECK, playerName);
                performPermissionCheck(offlinePlayer, permissionNode, permissionsManager::hasPermissionOffline, sender);
            }
        } else {
            performPermissionCheck(player, permissionNode, permissionsManager::hasPermission, sender);
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.HAS_PERMISSION_CHECK;
    }

    /**
     * Performs a permission check and informs the given sender of the result. {@code permissionChecker} is the
     * permission check to perform with the given {@code node} and the {@code player}.
     *
     * @param player the player to check a permission for
     * @param node the node of the permission to check
     * @param permissionChecker permission checking function
     * @param sender the sender to inform of the result
     * @param <P> the player type
     */
    private <P extends OfflinePlayer> void performPermissionCheck(
        P player, String node, BiFunction<P, PermissionNode, Boolean> permissionChecker, CommandSender sender) {

        PermissionNode permNode = getPermissionNode(sender, node);
        if (permissionChecker.apply(player, permNode)) {
            messages.send(sender, MessageKey.DEBUG_PERM_SUCCESS, player.getName(), node);
        } else {
            messages.send(sender, MessageKey.DEBUG_PERM_FAIL, player.getName(), node);
        }
    }

    /**
     * Based on the given permission node (String), tries to find the according AuthMe {@link PermissionNode}
     * instance, or creates a new one if not available.
     *
     * @param sender the sender (used to inform him if no AuthMe PermissionNode can be matched)
     * @param node the node to search for
     * @return the node as {@link PermissionNode} object
     */
    private PermissionNode getPermissionNode(CommandSender sender, String node) {
        Optional<? extends PermissionNode> permNode = PERMISSION_NODE_CLASSES.stream()
            .map(Class::getEnumConstants)
            .flatMap(Arrays::stream)
            .filter(perm -> perm.getNode().equals(node))
            .findFirst();
        if (permNode.isPresent()) {
            return permNode.get();
        } else {
            messages.send(sender, MessageKey.DEBUG_PERM_DEFAULT_DENIED);
            return createPermNode(node);
        }
    }

    private static PermissionNode createPermNode(String node) {
        return new PermissionNode() {
            @Override
            public String getNode() {
                return node;
            }

            @Override
            public DefaultPermission getDefaultPermission() {
                return DefaultPermission.NOT_ALLOWED;
            }
        };
    }
}
