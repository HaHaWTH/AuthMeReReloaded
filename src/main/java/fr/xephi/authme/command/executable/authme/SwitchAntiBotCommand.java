package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.command.CommandMapper;
import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.command.FoundCommandResult;
import fr.xephi.authme.command.help.HelpProvider;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.service.AntiBotService;
import fr.xephi.authme.service.CommonService;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Display or change the status of the antibot mod.
 */
public class SwitchAntiBotCommand implements ExecutableCommand {

    @Inject
    private AntiBotService antiBotService;

    @Inject
    private CommandMapper commandMapper;

    @Inject
    private HelpProvider helpProvider;

    @Inject
    private CommonService commonService;

    @Override
    public void executeCommand(final CommandSender sender, List<String> arguments) {
        if (arguments.isEmpty()) {
            commonService.send(sender, MessageKey.ADMIN_ANTIBOT_STATUS,
                antiBotService.getAntiBotStatus().name());
            return;
        }

        String newState = arguments.get(0);

        // Enable or disable the mod
        if ("ON".equalsIgnoreCase(newState)) {
            antiBotService.overrideAntiBotStatus(true);
            commonService.send(sender, MessageKey.ADMIN_ANTIBOT_MANUAL_ENABLED);
        } else if ("OFF".equalsIgnoreCase(newState)) {
            antiBotService.overrideAntiBotStatus(false);
            commonService.send(sender, MessageKey.ADMIN_ANTIBOT_MANUAL_DISABLED);
        } else {
            commonService.send(sender, MessageKey.ADMIN_ANTIBOT_INVALID_MODE);
            FoundCommandResult result = commandMapper.mapPartsToCommand(sender, Arrays.asList("authme", "antibot"));
            helpProvider.outputHelp(sender, result, HelpProvider.SHOW_ARGUMENTS);
            commonService.send(sender, MessageKey.ADMIN_ANTIBOT_DETAILED_HELP, "/authme help antibot");
        }
    }
}
