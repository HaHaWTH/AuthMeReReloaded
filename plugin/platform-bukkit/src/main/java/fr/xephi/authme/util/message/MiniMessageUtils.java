package fr.xephi.authme.util.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MiniMessageUtils {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

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
    private MiniMessageUtils() {
    }
}
