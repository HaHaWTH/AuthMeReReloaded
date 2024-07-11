package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import fr.xephi.authme.security.HashAlgorithm;
import fr.xephi.authme.settings.EnumSetProperty;

import java.util.Set;

import static ch.jalu.configme.properties.PropertyInitializer.newLowercaseStringSetProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public final class SecuritySettings implements SettingsHolder {

    @Comment({"Stop the server if we can't contact the sql database",
            "Take care with this, if you set this to false,",
            "AuthMe will automatically disable and the server won't be protected!"})
    public static final Property<Boolean> STOP_SERVER_ON_PROBLEM =
            newProperty("Security.SQLProblem.stopServer", false);

    @Comment({"Should we let Bedrock players login automatically?",
            "(Requires hookFloodgate to be true & floodgate loaded)",
            "(**THIS IS SAFE DO NOT WORRY**)"})
    public static final Property<Boolean> FORCE_LOGIN_BEDROCK =
            newProperty("3rdPartyFeature.features.bedrockAutoLogin", false);

    @Comment("Should we purge data on non-registered players quit?")
    public static final Property<Boolean> PURGE_DATA_ON_QUIT =
            newProperty("3rdPartyFeature.features.purgeData.purgeOnQuit", false);

    @Comment("Which world's player data should be deleted?(Enter the world *FOLDER* name where your players first logged in)")
    public static final Property<String> DELETE_PLAYER_DATA_WORLD =
            newProperty("3rdPartyFeature.features.purgeData.purgeWorldFolderName", "world");
    @Comment("Enable the new feature to prevent ghost players?")
    public static final Property<Boolean> ANTI_GHOST_PLAYERS =
            newProperty("3rdPartyFeature.fixes.antiGhostPlayer", false);

    @Comment({"(MC1.13- only)",
            "Should we fix the shulker crash bug with advanced method?"})
    public static final Property<Boolean> ADVANCED_SHULKER_FIX =
            newProperty("3rdPartyFeature.fixes.advancedShulkerFix", false);

    @Comment("Should we fix the location when players logged in the portal?")
    public static final Property<Boolean> LOGIN_LOC_FIX_SUB_PORTAL =
            newProperty("3rdPartyFeature.fixes.loginLocationFix.fixPortalStuck", false);

    @Comment("Should we fix the location when players logged underground?")
    public static final Property<Boolean> LOGIN_LOC_FIX_SUB_UNDERGROUND =
            newProperty("3rdPartyFeature.fixes.loginLocationFix.fixGroundStuck", false);

    @Comment("Copy AuthMe log output in a separate file as well?")
    public static final Property<Boolean> USE_LOGGING =
            newProperty("Security.console.logConsole", true);

    @Comment({"Query haveibeenpwned.com with a hashed version of the password.",
            "This is used to check whether it is safe."})
    public static final Property<Boolean> HAVE_I_BEEN_PWNED_CHECK =
            newProperty("Security.account.haveIBeenPwned.check", false);

    @Comment({"If the password is used more than this number of times, it is considered unsafe."})
    public static final Property<Integer> HAVE_I_BEEN_PWNED_LIMIT =
            newProperty("Security.account.haveIBeenPwned.limit", 0);

    @Comment("Enable captcha when a player uses wrong password too many times")
    public static final Property<Boolean> ENABLE_LOGIN_FAILURE_CAPTCHA =
            newProperty("Security.captcha.useCaptcha", false);

    @Comment("Check for updates on enabled from GitHub?")
    public static final Property<Boolean> CHECK_FOR_UPDATES =
            newProperty("Plugin.updates.checkForUpdates", true);

    @Comment("Should we show the AuthMe banner on startup?")
    public static final Property<Boolean> SHOW_STARTUP_BANNER =
            newProperty("Plugin.banners.showBanners", true);

    @Comment("Max allowed tries before a captcha is required")
    public static final Property<Integer> MAX_LOGIN_TRIES_BEFORE_CAPTCHA =
            newProperty("Security.captcha.maxLoginTry", 8);

    @Comment("Captcha length")
    public static final Property<Integer> CAPTCHA_LENGTH =
            newProperty("Security.captcha.captchaLength", 6);

    @Comment("Minutes after which login attempts count is reset for a player")
    public static final Property<Integer> CAPTCHA_COUNT_MINUTES_BEFORE_RESET =
            newProperty("Security.captcha.captchaCountReset", 120);

    @Comment("Require captcha before a player may register?")
    public static final Property<Boolean> ENABLE_CAPTCHA_FOR_REGISTRATION =
            newProperty("Security.captcha.requireForRegistration", false);

    @Comment("Minimum length of password")
    public static final Property<Integer> MIN_PASSWORD_LENGTH =
            newProperty("settings.security.minPasswordLength", 8);

    @Comment("Maximum length of password")
    public static final Property<Integer> MAX_PASSWORD_LENGTH =
            newProperty("settings.security.passwordMaxLength", 26);

    @Comment({
            "Possible values: SHA256, BCRYPT, BCRYPT2Y, PBKDF2, SALTEDSHA512,",
            "MYBB, IPB3, PHPBB, PHPFUSION, SMF, XENFORO, XAUTH, JOOMLA, WBB3, WBB4, MD5VB,",
            "PBKDF2DJANGO, WORDPRESS, ROYALAUTH, ARGON2, NOCRYPT, CUSTOM (for developers only). See full list at",
            "https://github.com/AuthMe/AuthMeReloaded/blob/master/docs/hash_algorithms.md",
            "If you use ARGON2, check that you have the argon2 c library on your system"
    })
    public static final Property<HashAlgorithm> PASSWORD_HASH =
            newProperty(HashAlgorithm.class, "settings.security.passwordHash", HashAlgorithm.SHA256);

    @Comment({
            "If a password check fails, AuthMe will also try to check with the following hash methods.",
            "Use this setting when you change from one hash method to another.",
            "AuthMe will update the password to the new hash. Example:",
            "legacyHashes:",
            "- 'SHA1'"
    })
    public static final Property<Set<HashAlgorithm>> LEGACY_HASHES =
            new EnumSetProperty<>(HashAlgorithm.class, "settings.security.legacyHashes");

    @Comment("Salt length for the SALTED2MD5 MD5(MD5(password)+salt)")
    public static final Property<Integer> DOUBLE_MD5_SALT_LENGTH =
            newProperty("settings.security.doubleMD5SaltLength", 8);

    @Comment("Number of rounds to use if passwordHash is set to PBKDF2. Default is 10000")
    public static final Property<Integer> PBKDF2_NUMBER_OF_ROUNDS =
            newProperty("settings.security.pbkdf2Rounds", 10000);

    @Comment({"Prevent unsafe passwords from being used; put them in lowercase!",
            "You should always set 'help' as unsafePassword due to possible conflicts.",
            "unsafePasswords:",
            "- '123456'",
            "- 'password'",
            "- 'help'"})
    public static final Property<Set<String>> UNSAFE_PASSWORDS =
            newLowercaseStringSetProperty("settings.security.unsafePasswords",
                    "12345678", "password", "qwertyui", "123456789", "87654321", "1234567890", "asdfghjkl", "zxcvbnm,", "asdfghjk", "12312312", "123123123", "32132132", "321321321");

    @Comment("Tempban a user's IP address if they enter the wrong password too many times")
    public static final Property<Boolean> TEMPBAN_ON_MAX_LOGINS =
            newProperty("Security.tempban.enableTempban", false);

    @Comment("How many times a user can attempt to login before their IP being tempbanned")
    public static final Property<Integer> MAX_LOGIN_TEMPBAN =
            newProperty("Security.tempban.maxLoginTries", 8);

    @Comment({"The length of time a IP address will be tempbanned in minutes",
            "Default: 480 minutes, or 8 hours"})
    public static final Property<Integer> TEMPBAN_LENGTH =
            newProperty("Security.tempban.tempbanLength", 480);

    @Comment({"How many minutes before resetting the count for failed logins by IP and username",
            "Default: 480 minutes (8 hours)"})
    public static final Property<Integer> TEMPBAN_MINUTES_BEFORE_RESET =
            newProperty("Security.tempban.minutesBeforeCounterReset", 480);

    @Comment({"The command to execute instead of using the internal ban system, empty if disabled.",
            "Available placeholders: %player%, %ip%"})
    public static final Property<String> TEMPBAN_CUSTOM_COMMAND =
            newProperty("Security.tempban.customCommand", "");

    @Comment("Number of characters a recovery code should have (0 to disable)")
    public static final Property<Integer> RECOVERY_CODE_LENGTH =
            newProperty("Security.recoveryCode.length", 8);

    @Comment("How many hours is a recovery code valid for?")
    public static final Property<Integer> RECOVERY_CODE_HOURS_VALID =
            newProperty("Security.recoveryCode.validForHours", 6);

    @Comment("Max number of tries to enter recovery code")
    public static final Property<Integer> RECOVERY_CODE_MAX_TRIES =
            newProperty("Security.recoveryCode.maxTries", 4);

    @Comment({"How long a player has after password recovery to change their password",
            "without logging in. This is in minutes.",
            "Default: 2 minutes"})
    public static final Property<Integer> PASSWORD_CHANGE_TIMEOUT =
            newProperty("Security.recoveryCode.passwordChangeTimeout", 5);

    @Comment({
            "Seconds a user has to wait for before a password recovery mail may be sent again",
            "This prevents an attacker from abusing AuthMe's email feature."
    })
    public static final Property<Integer> EMAIL_RECOVERY_COOLDOWN_SECONDS =
            newProperty("Security.emailRecovery.cooldown", 60);

    @Comment({
            "The mail shown using /email show will be partially hidden",
            "E.g. (if enabled)",
            " original email: my.email@example.com",
            " hidden email: my.***@***mple.com"
    })
    public static final Property<Boolean> USE_EMAIL_MASKING =
            newProperty("Security.privacy.enableEmailMasking", false);

    @Comment("Minutes after which a verification code will expire")
    public static final Property<Integer> VERIFICATION_CODE_EXPIRATION_MINUTES =
            newProperty("Security.privacy.verificationCodeExpiration", 10);

    private SecuritySettings() {
    }

}
