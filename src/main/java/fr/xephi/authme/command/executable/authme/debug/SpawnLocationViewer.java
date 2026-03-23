package fr.xephi.authme.command.executable.authme.debug;

import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.SpawnLoader;
import fr.xephi.authme.settings.properties.RestrictionSettings;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;

import static fr.xephi.authme.command.executable.authme.debug.DebugSectionUtils.formatLocation;

/**
 * Shows the spawn location that AuthMe is configured to use.
 */
class SpawnLocationViewer implements DebugSection {

    @Inject
    private SpawnLoader spawnLoader;

    @Inject
    private Settings settings;

    @Inject
    private BukkitService bukkitService;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Shows the spawn location that AuthMe will use";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.DEBUG_SPAWN_TITLE);
        if (arguments.isEmpty()) {
            showGeneralInfo(sender);
        } else if ("?".equals(arguments.get(0))) {
            showHelp(sender);
        } else {
            showPlayerSpawn(sender, arguments.get(0));
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.SPAWN_LOCATION;
    }

    private void showGeneralInfo(CommandSender sender) {
        messages.send(sender, MessageKey.DEBUG_SPAWN_PRIORITY,
            String.join(", ", settings.getProperty(RestrictionSettings.SPAWN_PRIORITY)));
        messages.send(sender, MessageKey.DEBUG_SPAWN_LOCATION, formatLocation(spawnLoader.getSpawn()));
        messages.send(sender, MessageKey.DEBUG_SPAWN_FIRST_LOCATION, formatLocation(spawnLoader.getFirstSpawn()));
        messages.send(sender, MessageKey.DEBUG_SPAWN_PRIORITY_NOTE);
        messages.send(sender, MessageKey.DEBUG_SPAWN_HELP_HINT);
    }

    private void showHelp(CommandSender sender) {
        messages.send(sender, MessageKey.DEBUG_SPAWN_HELP_TELEPORT);
        messages.send(sender, MessageKey.DEBUG_SPAWN_HELP_SET);
        messages.send(sender, MessageKey.DEBUG_SPAWN_HELP_PLAYER);
        messages.send(sender, MessageKey.DEBUG_SPAWN_HELP_WIKI);
    }

    private void showPlayerSpawn(CommandSender sender, String playerName) {
        Player player = bukkitService.getPlayerExact(playerName);
        if (player == null) {
            messages.send(sender, MessageKey.DEBUG_SPAWN_PLAYER_OFFLINE, playerName);
        } else {
            Location spawn = spawnLoader.getSpawnLocation(player);
            messages.send(sender, MessageKey.DEBUG_SPAWN_PLAYER_LOCATION, playerName, formatLocation(spawn));
            messages.send(sender, MessageKey.DEBUG_SPAWN_PLAYER_NOTE);
        }
    }
}
