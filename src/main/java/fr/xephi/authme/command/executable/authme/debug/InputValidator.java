package fr.xephi.authme.command.executable.authme.debug;

import fr.xephi.authme.listener.FailedVerificationException;
import fr.xephi.authme.listener.OnJoinVerifier;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.service.ValidationService;
import fr.xephi.authme.service.ValidationService.ValidationResult;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static fr.xephi.authme.command.executable.authme.debug.InputValidator.ValidationObject.MAIL;
import static fr.xephi.authme.command.executable.authme.debug.InputValidator.ValidationObject.NAME;
import static fr.xephi.authme.command.executable.authme.debug.InputValidator.ValidationObject.PASS;

/**
 * Checks if a sample username, email or password is valid according to the AuthMe settings.
 */
class InputValidator implements DebugSection {

    @Inject
    private ValidationService validationService;

    @Inject
    private Messages messages;

    @Inject
    private OnJoinVerifier onJoinVerifier;


    @Override
    public String getName() {
        return "valid";
    }

    @Override
    public String getDescription() {
        return "Checks if your config.yml allows a password / email";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        if (arguments.size() < 2 || !ValidationObject.matchesAny(arguments.get(0))) {
            displayUsageHint(sender);

        } else if (PASS.matches(arguments.get(0))) {
            validatePassword(sender, arguments.get(1));

        } else if (MAIL.matches(arguments.get(0))) {
            validateEmail(sender, arguments.get(1));

        } else if (NAME.matches(arguments.get(0))) {
            validateUsername(sender, arguments.get(1));

        } else {
            throw new IllegalStateException("Unexpected validation object with arg[0] = '" + arguments.get(0) + "'");
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.INPUT_VALIDATOR;
    }

    private void displayUsageHint(CommandSender sender) {
        messages.send(sender, MessageKey.DEBUG_VALID_TITLE);
        messages.send(sender, MessageKey.DEBUG_VALID_INTRO);
        messages.send(sender, MessageKey.DEBUG_VALID_PASS_USAGE);
        messages.send(sender, MessageKey.DEBUG_VALID_MAIL_USAGE);
        messages.send(sender, MessageKey.DEBUG_VALID_NAME_USAGE);
    }

    private void validatePassword(CommandSender sender, String password) {
        ValidationResult validationResult = validationService.validatePassword(password, "");
        messages.send(sender, MessageKey.DEBUG_VALID_VALIDATE_PASSWORD, password);
        if (validationResult.hasError()) {
            messages.send(sender, validationResult.getMessageKey(), validationResult.getArgs());
        } else {
            messages.send(sender, MessageKey.DEBUG_VALID_VALID_PASSWORD);
        }
    }

    private void validateEmail(CommandSender sender, String email) {
        boolean isValidEmail = validationService.validateEmail(email);
        messages.send(sender, MessageKey.DEBUG_VALID_VALIDATE_EMAIL, email);
        if (isValidEmail) {
            messages.send(sender, MessageKey.DEBUG_VALID_VALID_EMAIL);
        } else {
            messages.send(sender, MessageKey.DEBUG_VALID_INVALID_EMAIL);
        }
    }

    private void validateUsername(CommandSender sender, String username) {
        messages.send(sender, MessageKey.DEBUG_VALID_VALIDATE_NAME, username);
        try {
            onJoinVerifier.checkIsValidName(username);
            messages.send(sender, MessageKey.DEBUG_VALID_VALID_NAME);
        } catch (FailedVerificationException failedVerificationEx) {
            messages.send(sender, failedVerificationEx.getReason(), failedVerificationEx.getArgs());
        }
    }


    enum ValidationObject {

        PASS, MAIL, NAME;

        static boolean matchesAny(String arg) {
            return Arrays.stream(values()).anyMatch(vo -> vo.matches(arg));
        }

        boolean matches(String arg) {
            return name().equalsIgnoreCase(arg);
        }
    }

}
