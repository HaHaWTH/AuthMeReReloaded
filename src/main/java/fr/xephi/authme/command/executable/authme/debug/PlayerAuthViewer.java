package fr.xephi.authme.command.executable.authme.debug;

import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.security.crypts.HashedPassword;
import fr.xephi.authme.util.StringUtils;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static fr.xephi.authme.command.executable.authme.debug.DebugSectionUtils.formatLocation;

/**
 * Allows to view the data of a PlayerAuth in the database.
 */
class PlayerAuthViewer implements DebugSection {

    @Inject
    private DataSource dataSource;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "db";
    }

    @Override
    public String getDescription() {
        return "View player's data in the database";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        if (arguments.isEmpty()) {
            messages.send(sender, MessageKey.DEBUG_DB_TITLE);
            messages.send(sender, MessageKey.DEBUG_DB_ENTER_NAME);
            messages.send(sender, MessageKey.DEBUG_DB_EXAMPLE);
            return;
        }

        PlayerAuth auth = dataSource.getAuth(arguments.get(0));
        if (auth == null) {
            messages.send(sender, MessageKey.DEBUG_DB_TITLE);
            messages.send(sender, MessageKey.DEBUG_DB_NO_RECORD, arguments.get(0));
        } else {
            displayAuthToSender(auth, sender);
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.PLAYER_AUTH_VIEWER;
    }

    /**
     * Outputs the PlayerAuth information to the given sender.
     *
     * @param auth the PlayerAuth to display
     * @param sender the sender to send the messages to
     */
    private void displayAuthToSender(PlayerAuth auth, CommandSender sender) {
        messages.send(sender, MessageKey.DEBUG_DB_PLAYER_HEADER, auth.getNickname(), auth.getRealName());
        messages.send(sender, MessageKey.DEBUG_DB_SUMMARY, auth.getEmail(), auth.getLastIp(),
            String.valueOf(auth.getGroupId()));
        messages.send(sender, MessageKey.DEBUG_DB_QUIT_LOC,
            formatLocation(auth.getQuitLocX(), auth.getQuitLocY(), auth.getQuitLocZ(), auth.getWorld()));
        messages.send(sender, MessageKey.DEBUG_DB_LAST_LOGIN, formatDate(sender, auth.getLastLogin()));
        messages.send(sender, MessageKey.DEBUG_DB_REGISTRATION,
            formatDate(sender, auth.getRegistrationDate()), auth.getRegistrationIp());

        HashedPassword hashedPass = auth.getPassword();
        messages.send(sender, MessageKey.DEBUG_DB_HASH,
            safeSubstring(hashedPass.getHash(), 6), safeSubstring(hashedPass.getSalt(), 4));
        messages.send(sender, MessageKey.DEBUG_DB_TOTP, safeSubstring(auth.getTotpKey(), 3));
    }

    /**
     * Fail-safe substring method. Guarantees not to show the entire String.
     *
     * @param str the string to transform
     * @param length number of characters to show from the start of the String
     * @return the first <code>length</code> characters of the string, or half of the string if it is shorter,
     *         or empty string if the string is null or empty
     */
    private static String safeSubstring(String str, int length) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else if (str.length() < length) {
            return str.substring(0, str.length() / 2) + "...";
        } else {
            return str.substring(0, length) + "...";
        }
    }

    /**
     * Formats the given timestamp to a human readable date.
     *
     * @param timestamp the timestamp to format (nullable)
     * @return the formatted timestamp
     */
    private String formatDate(CommandSender sender, Long timestamp) {
        if (timestamp == null) {
            return messages.retrieveSingle(sender, MessageKey.DEBUG_DB_DATE_NULL);
        } else if (timestamp == 0) {
            return messages.retrieveSingle(sender, MessageKey.DEBUG_DB_DATE_ZERO);
        } else {
            LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(date);
        }
    }
}
