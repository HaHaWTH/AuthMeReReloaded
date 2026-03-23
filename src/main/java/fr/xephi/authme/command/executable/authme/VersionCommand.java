package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.DatabaseSettings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class VersionCommand implements ExecutableCommand {

    @Inject
    private BukkitService bukkitService;
    @Inject
    private Settings settings;
    @Inject
    private Messages messages;

    @Override
    public void executeCommand(CommandSender sender, List<String> arguments) {
        String year = new SimpleDateFormat("yyyy").format(new Date());
        messages.send(sender, MessageKey.ABOUT_HEADER, AuthMe.getPluginName());
        messages.send(sender, MessageKey.ABOUT_VERSION, AuthMe.getPluginName(), AuthMe.getPluginVersion(),
            AuthMe.getPluginBuildNumber());
        messages.send(sender, MessageKey.ABOUT_DATABASE, settings.getProperty(DatabaseSettings.BACKEND).toString());
        messages.send(sender, MessageKey.ABOUT_AUTHORS_HEADER);
        Collection<Player> onlinePlayers = bukkitService.getOnlinePlayers();
        printDeveloper(sender, "Gabriele C.", "sgdc3", "Project manager, Contributor", onlinePlayers);
        printDeveloper(sender, "Lucas J.", "ljacqu", "Main Developer", onlinePlayers);
        printDeveloper(sender, "games647", "games647", "Developer", onlinePlayers);
        printDeveloper(sender, "Hex3l", "Hex3l", "Developer", onlinePlayers);
        printDeveloper(sender, "krusic22", "krusic22", "Support", onlinePlayers);
        messages.send(sender, MessageKey.ABOUT_RETIRED_HEADER);
        printDeveloper(sender, "Alexandre Vanhecke", "xephi59", "Original Author", onlinePlayers);
        printDeveloper(sender, "Gnat008", "gnat008", "Developer, Retired", onlinePlayers);
        printDeveloper(sender, "DNx5", "DNx5", "Developer, Retired", onlinePlayers);
        printDeveloper(sender, "Tim Visee", "timvisee", "Developer, Retired", onlinePlayers);
        messages.send(sender, MessageKey.ABOUT_WEBSITE, "https://github.com/AuthMe/AuthMeReloaded");
        messages.send(sender, MessageKey.ABOUT_LICENSE);
        messages.send(sender, MessageKey.ABOUT_COPYRIGHT, year);
    }

    /**
     * Print a developer with proper styling.
     *
     * @param sender        The command sender
     * @param name          The display name of the developer
     * @param minecraftName The Minecraft username of the developer, if available
     * @param function      The function of the developer
     * @param onlinePlayers The list of online players
     */
    private void printDeveloper(CommandSender sender, String name, String minecraftName, String function,
                                Collection<Player> onlinePlayers) {
        String line = messages.retrieveSingle(sender, MessageKey.ABOUT_DEVELOPER_LINE, name, minecraftName, function);
        if (isPlayerOnline(minecraftName, onlinePlayers)) {
            line += messages.retrieveSingle(sender, MessageKey.ABOUT_DEVELOPER_INGAME);
        }
        sender.sendMessage(line);
    }

    /**
     * Check whether a player is online.
     *
     * @param minecraftName The Minecraft player name
     * @param onlinePlayers List of online players
     *
     * @return True if the player is online, false otherwise
     */
    private static boolean isPlayerOnline(String minecraftName, Collection<Player> onlinePlayers) {
        for (Player player : onlinePlayers) {
            if (player.getName().equalsIgnoreCase(minecraftName)) {
                return true;
            }
        }
        return false;
    }
}
