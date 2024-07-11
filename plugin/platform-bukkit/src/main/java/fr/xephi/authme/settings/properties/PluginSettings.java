package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import fr.xephi.authme.logger.LogLevel;

import java.util.Set;

import static ch.jalu.configme.properties.PropertyInitializer.newLowercaseStringSetProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public final class PluginSettings implements SettingsHolder {

    @Comment({
            "Should we execute /help command when unregistered players press Shift+F?",
            "This keeps compatibility with some menu plugins",
            "If you are using TrMenu, don't enable this because TrMenu already implemented this."
    })
    public static final Property<Boolean> MENU_UNREGISTER_COMPATIBILITY =
            newProperty("3rdPartyFeature.compatibility.menuPlugins", false);

    @Comment({
            "Send i18n messages to player based on their client settings, this option will override `settings.messagesLanguage`",
            "(Requires Protocollib or Packetevents)",
            "This will not affect language of AuthMe help command."
    })
    public static final Property<Boolean> I18N_MESSAGES =
            newProperty("3rdPartyFeature.features.i18nMessages.enabled", false);

    @Comment({"Redirect locale code to certain AuthMe language code as you want",
            "Minecraft locale list: https://minecraft.wiki/w/Language",
            "AuthMe language code: https://github.com/HaHaWTH/AuthMeReReloaded/blob/master/docs/translations.md",
            "For example, if you want to show Russian messages to player using language Tatar(tt_ru),",
            "and show Chinese Simplified messages to player using language Classical Chinese(lzh), then:",
            "locale-code-redirect:",
            "- 'tt_ru:ru'",
            "- 'lzh:zhcn'"})
    public static final Property<Set<String>> I18N_CODE_REDIRECT =
            newLowercaseStringSetProperty("3rdPartyFeature.features.i18nMessages.locale-code-redirect",
                    "tt_ru:ru", "lzh:zhcn");

    @Comment({
            "Enables switching a player to defined permission groups before they log in.",
            "See below for a detailed explanation."
    })
    public static final Property<Boolean> ENABLE_PERMISSION_CHECK =
            newProperty("GroupOptions.enablePermissionCheck", false);

    @Comment({
            "This is a very important option: if a registered player joins the server",
            "AuthMe will switch him to unLoggedInGroup. This should prevent all major exploits.",
            "You can set up your permission plugin with this special group to have no permissions,",
            "or only permission to chat (or permission to send private messages etc.).",
            "The better way is to set up this group with few permissions, so if a player",
            "tries to exploit an account they can do only what you've defined for the group.",
            "After login, the player will be moved to his correct permissions group!",
            "Please note that the group name is case-sensitive, so 'admin' is different from 'Admin'",
            "Otherwise your group will be wiped and the player will join in the default group []!",
            "Example: registeredPlayerGroup: 'NotLogged'"
    })
    public static final Property<String> REGISTERED_GROUP =
            newProperty("GroupOptions.registeredPlayerGroup", "");

    @Comment({
            "Similar to above, unregistered players can be set to the following",
            "permissions group"
    })
    public static final Property<String> UNREGISTERED_GROUP =
            newProperty("GroupOptions.unregisteredPlayerGroup", "");

    @Comment("Forces authme to hook into Vault instead of a specific permission handler system.")
    public static final Property<Boolean> FORCE_VAULT_HOOK =
            newProperty("settings.forceVaultHook", false);

    @Comment("The name of the server, used in some placeholders.")
    public static final Property<String> SERVER_NAME = newProperty("settings.serverName", "Your Minecraft Server");

    private PluginSettings() {
    }

}
