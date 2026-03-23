package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.List;

/**
 * AuthMe base command; shows the version and some command pointers.
 */
public class AuthMeCommand implements ExecutableCommand {

    @Inject
    private Messages messages;

    @Override
    public void executeCommand(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.AUTHME_INFO_RUNNING,
            AuthMe.getPluginName(),
            AuthMe.getPluginVersion(),
            String.valueOf(AuthMe.getPluginBuildNumber()));
        messages.send(sender, MessageKey.AUTHME_INFO_HELP);
        messages.send(sender, MessageKey.AUTHME_INFO_ABOUT);
    }
}
