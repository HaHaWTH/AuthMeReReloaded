package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import fr.xephi.authme.logger.LogLevel;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

/**
 * CommonSettings
 *
 * @author TheFloodDragon
 * @since 2024/7/11 20:19
 */
public final class CommonSettings implements SettingsHolder {

    @Comment({
            "Message language, available languages:",
            "https://github.com/AuthMe/AuthMeReloaded/blob/master/docs/translations.md",
            "Example: zhcn, en"
    })
    public static final Property<String> MESSAGES_LANGUAGE =
            newProperty("settings.messagesLanguage", "en");

    @Comment({
            "(Disabled since 2024.7.11) Log level: INFO, FINE, DEBUG. Use INFO for general messages,",
            "FINE for some additional detailed ones (like password failed),",
            "and DEBUG for debugging"
    })
    public static final Property<LogLevel> LOG_LEVEL =
            newProperty(LogLevel.class, "settings.logLevel", LogLevel.FINE);

    @Comment({
            "By default we schedule async tasks when talking to the database. If you want",
            "typical communication with the database to happen synchronously, set this to false"
    })
    public static final Property<Boolean> USE_ASYNC_TASKS =
            newProperty("settings.useAsyncTasks", true);

    @Comment({
            "Do you want to enable the session feature?",
            "If enabled, when a player authenticates successfully,",
            "his IP and his nickname is saved.",
            "The next time the player joins the server, if his IP",
            "is the same as last time and the timeout hasn't",
            "expired, he will not need to authenticate."
    })
    public static final Property<Boolean> SESSIONS_ENABLED =
            newProperty("settings.sessions.enabled", true);

    @Comment({
            "After how many minutes should a session expire?",
            "A player's session ends after the timeout or if his IP has changed"
    })
    public static final Property<Integer> SESSIONS_TIMEOUT =
            newProperty("settings.sessions.timeout", 43200);

    @Comment("The name of the server, used in some placeholders.")
    public static final Property<String> SERVER_NAME = newProperty("settings.serverName", "Your Minecraft Server");

}
