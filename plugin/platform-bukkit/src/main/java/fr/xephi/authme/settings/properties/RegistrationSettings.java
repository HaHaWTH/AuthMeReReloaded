package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import fr.xephi.authme.process.register.RegisterSecondaryArgument;
import fr.xephi.authme.process.register.RegistrationType;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public final class RegistrationSettings implements SettingsHolder {

    @Comment("Enable registration on the server?")
    public static final Property<Boolean> IS_ENABLED =
        newProperty("settings.registration.enabled", true);

    @Comment({
        "Send every X seconds a message to a player to",
        "remind him that he has to login/register"})
    public static final Property<Integer> MESSAGE_INTERVAL =
        newProperty("settings.registration.messageInterval", 5);

    @Comment({
        "Only registered and logged in players can play.",
        "See restrictions for exceptions"})
    public static final Property<Boolean> FORCE =
        newProperty("settings.registration.force", true);

    @Comment({
        "Type of registration: PASSWORD or EMAIL",
        "PASSWORD = account is registered with a password supplied by the user;",
        "EMAIL = password is generated and sent to the email provided by the user.",
        "More info at https://github.com/AuthMe/AuthMeReloaded/wiki/Registration"
    })
    public static final Property<RegistrationType> REGISTRATION_TYPE =
        newProperty(RegistrationType.class, "settings.registration.type", RegistrationType.PASSWORD);

    @Comment({
        "Second argument the /register command should take: ",
        "NONE = no 2nd argument",
        "CONFIRMATION = must repeat first argument (pass or email)",
        "EMAIL_OPTIONAL = for password register: 2nd argument can be empty or have email address",
        "EMAIL_MANDATORY = for password register: 2nd argument MUST be an email address"
    })
    public static final Property<RegisterSecondaryArgument> REGISTER_SECOND_ARGUMENT =
        newProperty(RegisterSecondaryArgument.class, "settings.registration.secondArg",
            RegisterSecondaryArgument.CONFIRMATION);

    @Comment({
        "Should we unregister the player when he didn't verify the email?",
        "This only works if you enabled email registration."})
    public static final Property<Boolean> UNREGISTER_ON_EMAIL_VERIFICATION_FAILURE =
        newProperty("settings.registration.email.unregisterOnEmailVerificationFailure", false);

    @Comment({"How many minutes should we wait before unregister the player",
        "when he didn't verify the email?"})
    public static final Property<Long> UNREGISTER_AFTER_MINUTES =
        newProperty("settings.registration.email.unregisterAfterMinutes", 10L);
    @Comment({
        "Do we force kick a player after a successful registration?",
        "Do not use with login feature below"})
    public static final Property<Boolean> FORCE_KICK_AFTER_REGISTER =
        newProperty("settings.registration.forceKickAfterRegister", false);

    @Comment("Does AuthMe need to enforce a /login after a successful registration?")
    public static final Property<Boolean> FORCE_LOGIN_AFTER_REGISTER =
        newProperty("settings.registration.forceLoginAfterRegister", false);
    @Comment("Should we delay the join message and display it once the player has logged in?")
    public static final Property<Boolean> DELAY_JOIN_MESSAGE =
        newProperty("settings.delayJoinMessage", true);

    @Comment({
        "The custom join message that will be sent after a successful login,",
        "keep empty to use the original one.",
        "Available variables:",
        "{PLAYERNAME}: the player name (no colors)",
        "{DISPLAYNAME}: the player display name (with colors)",
        "{DISPLAYNAMENOCOLOR}: the player display name (without colors)"})
    public static final Property<String> CUSTOM_JOIN_MESSAGE =
        newProperty("settings.customJoinMessage", "");

    @Comment("Should we remove the leave messages of unlogged users?")
    public static final Property<Boolean> REMOVE_UNLOGGED_LEAVE_MESSAGE =
        newProperty("settings.removeUnloggedLeaveMessage", true);

    @Comment("Should we remove join messages altogether?")
    public static final Property<Boolean> REMOVE_JOIN_MESSAGE =
        newProperty("settings.removeJoinMessage", true);

    @Comment("Should we remove leave messages altogether?")
    public static final Property<Boolean> REMOVE_LEAVE_MESSAGE =
        newProperty("settings.removeLeaveMessage", true);

    @Comment("Do we need to add potion effect Blinding before login/register?")
    public static final Property<Boolean> APPLY_BLIND_EFFECT =
        newProperty("settings.applyBlindEffect", false);

    @Comment({
        "Do we need to prevent people to login with another case?",
        "If Xephi is registered, then Xephi can login, but not XEPHI/xephi/XePhI"})
    public static final Property<Boolean> PREVENT_OTHER_CASE =
        newProperty("settings.preventOtherCase", true);


    private RegistrationSettings() {
    }

}
