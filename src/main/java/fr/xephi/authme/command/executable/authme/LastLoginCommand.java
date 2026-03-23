package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.service.CommonService;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Returns the last login date of the given user.
 */
public class LastLoginCommand implements ExecutableCommand {

    @Inject
    private DataSource dataSource;

    @Inject
    private CommonService commonService;

    @Override
    public void executeCommand(CommandSender sender, List<String> arguments) {
        // Get the player
        String playerName = arguments.isEmpty() ? sender.getName() : arguments.get(0);

        PlayerAuth auth = dataSource.getAuth(playerName);
        if (auth == null) {
            commonService.send(sender, MessageKey.UNKNOWN_USER);
            return;
        }

        // Get the last login date
        final Long lastLogin = auth.getLastLogin();
        final String lastLoginDate = lastLogin == null
            ? commonService.retrieveSingleMessage(sender, MessageKey.ADMIN_LAST_LOGIN_NEVER)
            : new Date(lastLogin).toString();

        // Show the player status
        commonService.send(sender, MessageKey.ADMIN_LAST_LOGIN, playerName, lastLoginDate);
        if (lastLogin != null) {
            String interval = createLastLoginIntervalMessage(sender, lastLogin);
            commonService.send(sender, MessageKey.ADMIN_LAST_LOGIN_INTERVAL, playerName, interval);
        }
        commonService.send(sender, MessageKey.ADMIN_LAST_LOGIN_IP, String.valueOf(auth.getLastIp()));
    }

    private String createLastLoginIntervalMessage(CommandSender sender, long lastLogin) {
        final long diff = System.currentTimeMillis() - lastLogin;
        int days = (int) (diff / 86400000);
        int hours = (int) (diff / 3600000 % 24);
        int mins = (int) (diff / 60000 % 60);
        int secs = (int) (diff / 1000 % 60);
        return commonService.retrieveSingleMessage(sender, MessageKey.ADMIN_LAST_LOGIN_INTERVAL_PARTS,
            String.valueOf(days), String.valueOf(hours), String.valueOf(mins), String.valueOf(secs));
    }
}
