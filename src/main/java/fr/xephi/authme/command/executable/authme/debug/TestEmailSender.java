package fr.xephi.authme.command.executable.authme.debug;

import ch.jalu.datasourcecolumns.data.DataSourceValue;
import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.mail.SendMailSsl;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.util.StringUtils;
import fr.xephi.authme.util.Utils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.List;

/**
 * Sends out a test email.
 */
class TestEmailSender implements DebugSection {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(TestEmailSender.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private SendMailSsl sendMailSsl;

    @Inject
    private Server server;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "mail";
    }

    @Override
    public String getDescription() {
        return "Sends out a test email";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.DEBUG_MAIL_TITLE);
        if (!sendMailSsl.hasAllInformation()) {
            messages.send(sender, MessageKey.DEBUG_MAIL_CONFIG_INCOMPLETE);
            return;
        }

        String email = getEmail(sender, arguments);

        // getEmail() takes care of informing the sender of the error if email == null
        if (email != null) {
            boolean sendMail = sendTestEmail(sender, email);
            if (sendMail) {
                messages.send(sender, MessageKey.DEBUG_MAIL_SENT, email);
            } else {
                messages.send(sender, MessageKey.DEBUG_MAIL_FAILED, email);
            }
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.TEST_EMAIL;
    }

    /**
     * Gets the email address to use based on the sender and the arguments. If the arguments are empty,
     * we attempt to retrieve the email from the sender. If there is an argument, we verify that it is
     * an email address.
     * {@code null} is returned if no email address could be found. This method informs the sender of
     * the specific error in such cases.
     *
     * @param sender the command sender
     * @param arguments the provided arguments
     * @return the email to use, or null if none found
     */
    private String getEmail(CommandSender sender, List<String> arguments) {
        if (arguments.isEmpty()) {
            DataSourceValue<String> emailResult = dataSource.getEmail(sender.getName());
            if (!emailResult.rowExists()) {
                messages.send(sender, MessageKey.DEBUG_MAIL_PROVIDE_ADDRESS);
                return null;
            }
            final String email = emailResult.getValue();
            if (Utils.isEmailEmpty(email)) {
                messages.send(sender, MessageKey.DEBUG_MAIL_NO_ACCOUNT_EMAIL);
                return null;
            }
            return email;
        } else {
            String email = arguments.get(0);
            if (StringUtils.isInsideString('@', email)) {
                return email;
            }
            messages.send(sender, MessageKey.DEBUG_MAIL_INVALID);
            return null;
        }
    }

    private boolean sendTestEmail(CommandSender sender, String email) {
        HtmlEmail htmlEmail;
        try {
            htmlEmail = sendMailSsl.initializeMail(email);
        } catch (EmailException e) {
            logger.logException("Failed to create email for sample email:", e);
            return false;
        }

        htmlEmail.setSubject(messages.retrieveSingle(sender, MessageKey.DEBUG_MAIL_SUBJECT));
        String message = messages.retrieveSingle(sender, MessageKey.DEBUG_MAIL_BODY, server.getName());
        return sendMailSsl.sendEmail(message, htmlEmail);
    }
}
