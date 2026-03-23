package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.command.help.HelpMessagesService;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.service.CommonService;
import fr.xephi.authme.service.HelpTranslationGenerator;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Messages command, updates the user's help messages file with any missing files
 * from the provided file in the JAR.
 */
public class UpdateHelpMessagesCommand implements ExecutableCommand {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(UpdateHelpMessagesCommand.class);

    @Inject
    private HelpTranslationGenerator helpTranslationGenerator;
    @Inject
    private HelpMessagesService helpMessagesService;

    @Inject
    private CommonService commonService;

    @Override
    public void executeCommand(CommandSender sender, List<String> arguments) {
        try {
            File updatedFile = helpTranslationGenerator.updateHelpFile();
            commonService.send(sender, MessageKey.ADMIN_HELP_FILE_UPDATED, updatedFile.getName());
            helpMessagesService.reloadMessagesFile();
        } catch (IOException e) {
            commonService.send(sender, MessageKey.ADMIN_HELP_FILE_UPDATE_FAILED, e.getMessage());
            logger.logException("Could not update help file:", e);
        }
    }
}
