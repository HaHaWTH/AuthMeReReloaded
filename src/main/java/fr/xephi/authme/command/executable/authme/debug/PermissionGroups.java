package fr.xephi.authme.command.executable.authme.debug;

import fr.xephi.authme.data.limbo.UserGroup;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.permission.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Outputs the permission groups of a player.
 */
class PermissionGroups implements DebugSection {

    @Inject
    private PermissionsManager permissionsManager;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "groups";
    }

    @Override
    public String getDescription() {
        return "Show permission groups a player belongs to";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.DEBUG_GROUPS_TITLE);
        String name = arguments.isEmpty() ? sender.getName() : arguments.get(0);
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            messages.send(sender, MessageKey.DEBUG_GROUPS_PLAYER_NOT_FOUND, name);
        } else {
            List<String> groupNames = permissionsManager.getGroups(player).stream()
                .map(UserGroup::getGroupName)
                .collect(toList());

            messages.send(sender, MessageKey.DEBUG_GROUPS_LIST, name, String.join(", ", groupNames));
            messages.send(sender, MessageKey.DEBUG_GROUPS_PRIMARY,
                String.valueOf(permissionsManager.getGroups(player)));
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.PERM_GROUPS;
    }
}
