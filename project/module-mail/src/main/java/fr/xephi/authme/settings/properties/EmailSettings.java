package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public final class EmailSettings implements SettingsHolder {

    @Comment("Email SMTP server host")
    public static final Property<String> SMTP_HOST =
            PropertyInitializer.newProperty("Email.mailSMTP", "smtp.163.com");

    @Comment("Email SMTP server port")
    public static final Property<Integer> SMTP_PORT =
            PropertyInitializer.newProperty("Email.mailPort", 465);

    @Comment("Only affects port 25: enable TLS/STARTTLS?")
    public static final Property<Boolean> PORT25_USE_TLS =
            PropertyInitializer.newProperty("Email.useTls", true);

    @Comment("Email account which sends the mails")
    public static final Property<String> MAIL_ACCOUNT =
            PropertyInitializer.newProperty("Email.mailAccount", "");

    @Comment("Email account password")
    public static final Property<String> MAIL_PASSWORD =
            PropertyInitializer.newProperty("Email.mailPassword", "");

    @Comment("Email address, fill when mailAccount is not the email address of the account")
    public static final Property<String> MAIL_ADDRESS =
            PropertyInitializer.newProperty("Email.mailAddress", "");

    @Comment("Custom sender name, replacing the mailAccount name in the email")
    public static final Property<String> MAIL_SENDER_NAME =
            PropertyInitializer.newProperty("Email.mailSenderName", "");

    @Comment("Recovery password length")
    public static final Property<Integer> RECOVERY_PASSWORD_LENGTH =
            PropertyInitializer.newProperty("Email.RecoveryPasswordLength", 12);

    @Comment("Mail Subject")
    public static final Property<String> RECOVERY_MAIL_SUBJECT =
            PropertyInitializer.newProperty("Email.mailSubject", "Your new AuthMe password");

    @Comment("Like maxRegPerIP but with email")
    public static final Property<Integer> MAX_REG_PER_EMAIL =
            PropertyInitializer.newProperty("Email.maxRegPerEmail", 1);

    @Comment("Recall players to add an email?")
    public static final Property<Boolean> RECALL_PLAYERS =
            PropertyInitializer.newProperty("Email.recallPlayers", false);

    @Comment("Delay in minute for the recall scheduler")
    public static final Property<Integer> DELAY_RECALL =
            PropertyInitializer.newProperty("Email.delayRecall", 5);

    @Comment("Send the new password drawn in an image?")
    public static final Property<Boolean> PASSWORD_AS_IMAGE =
            PropertyInitializer.newProperty("Email.generateImage", false);

    @Comment("The OAuth2 token")
    public static final Property<String> OAUTH2_TOKEN =
            PropertyInitializer.newProperty("Email.emailOauth2Token", "");
    @Comment("Email notifications when the server shuts down")
    public static final Property<Boolean> SHUTDOWN_MAIL =
            PropertyInitializer.newProperty("Email.shutDownEmail", false);
    @Comment("Email notification address when the server is shut down")
    public static final Property<String> SHUTDOWN_MAIL_ADDRESS =
            PropertyInitializer.newProperty("Email.shutDownEmailAddress", "your@mail.com");

    private EmailSettings() {
    }

}
