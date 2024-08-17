package fr.xephi.authme.util.message;

import fr.xephi.authme.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniMessageUtils {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final char SECTION_CHAR = '§';
    private static final char AMPERSAND_CHAR = '&';
    private static final boolean HEX_SUPPORTED = Utils.MAJOR_VERSION >= 16;
    private static Method methodDisallow;
    private static Method methodKick;

    static {
        try {
            methodDisallow = AsyncPlayerPreLoginEvent.class.getMethod("disallow", AsyncPlayerPreLoginEvent.Result.class, Component.class);
        } catch (Exception e) {
            methodDisallow = null;
        }
        try {
            methodKick = Player.class.getMethod("kick", Component.class);
        } catch (Exception e) {
            methodKick = null;
        }
    }
    /**
     * Parse a MiniMessage string into a legacy string.
     *
     * @param message The message to parse.
     * @return The parsed message.
     */
    public static String parseMiniMessageToLegacy(String message) {
        Component component = miniMessage.deserialize(message);
        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    /**
     * Parse a MiniMessage string into a component.
     *
     * @param message The message to parse.
     * @return The parsed message.
     */
    public static Component parseMiniMessage(String message) {
        return miniMessage.deserialize(convertLegacyToMiniMessage(message, false, SECTION_CHAR, HEX_SUPPORTED));
    }

    /**
     * Kicks a player with the given message.
     *
     * @param player the player to kick
     * @param message the message to send
     */
    public static void kickPlayer(Player player, Component message) {
        if (methodKick != null) {
            try {
                methodKick.invoke(player, message);
            } catch (Exception e) {
                player.kickPlayer(LegacyComponentSerializer.legacySection().serialize(message));
            }
        } else {
            player.kickPlayer(LegacyComponentSerializer.legacySection().serialize(message));
        }
    }

    /**
     * Disallows the login event with the given result and reason.
     *
     * @param event the event
     * @param result the event result to set
     * @param message the denial message
     */
    public static void disallowPreLoginEvent(AsyncPlayerPreLoginEvent event,
                                                              AsyncPlayerPreLoginEvent.Result result, Component message) {
        if (methodDisallow != null) {
            try {
                methodDisallow.invoke(event, result, message);
            } catch (Exception e) {
                event.disallow(result, LegacyComponentSerializer.legacySection().serialize(message));
            }
        } else {
            event.disallow(result, LegacyComponentSerializer.legacySection().serialize(message));
        }
    }

    @SuppressWarnings("all")
    private static String convertLegacyToMiniMessage(String legacy, boolean concise, char charCode, boolean rgb) {
        String miniMessage = legacy.replaceAll(Pattern.quote(String.valueOf(charCode)) + "0", "<black>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "1", "<dark_blue>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "2", "<dark_green>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "3", "<dark_aqua>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "4", "<dark_red>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "5", "<dark_purple>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "6", "<gold>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "7", "<gray>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "8", "<dark_gray>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "9", "<blue>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "a", "<green>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "b", "<aqua>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "c", "<red>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "d", "<light_purple>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "e", "<yellow>")
            .replaceAll(Pattern.quote(String.valueOf(charCode)) + "f", "<white>");

        if (concise) {
            miniMessage = miniMessage.replaceAll(Pattern.quote(String.valueOf(charCode)) + "n", "<u>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "m", "<st>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "k", "<obf>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "o", "<i>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "l", "<b>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "r", "<r>");
        } else {
            miniMessage = miniMessage.replaceAll(Pattern.quote(String.valueOf(charCode)) + "n", "<underlined>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "m", "<strikethrough>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "k", "<obfuscated>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "o", "<italic>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "l", "<bold>")
                .replaceAll(Pattern.quote(String.valueOf(charCode)) + "r", "<reset>");
        }

        if (rgb) {
            Pattern pattern = Pattern.compile(Pattern.quote(String.valueOf(charCode)) + "#([0-9a-fA-F]{6})");
            Matcher matcher = pattern.matcher(miniMessage);
            miniMessage = matcher.replaceAll("<#$1>");
        }

        return miniMessage;
    }
    private MiniMessageUtils() {
    }
}
