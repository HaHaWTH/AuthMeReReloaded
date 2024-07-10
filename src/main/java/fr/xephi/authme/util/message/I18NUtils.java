package fr.xephi.authme.util.message;

import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.PluginSettings;
import fr.xephi.authme.util.Utils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class I18NUtils {

    private static Map<UUID, String> PLAYER_LOCALE = new ConcurrentHashMap<>();
    private static final Map<String, String> LOCALE_MAP = new HashMap<>();
    private static final List<String> LOCALE_LIST = Arrays.asList(
        "en", "bg", "de", "eo", "es", "et", "eu", "fi", "fr", "gl", "hu", "id", "it", "ja", "ko", "lt", "nl", "pl",
        "pt", "ro", "ru", "sk", "sr", "tr", "uk"
    );

    static {
        LOCALE_MAP.put("pt_br", "br");
        LOCALE_MAP.put("cs_cz", "cz");
        LOCALE_MAP.put("nds_de", "de");
        LOCALE_MAP.put("sxu", "de");
        LOCALE_MAP.put("swg", "de");
        LOCALE_MAP.put("rpr", "ru");
        LOCALE_MAP.put("sl_si", "si");
        LOCALE_MAP.put("vi_vn", "vn");
        LOCALE_MAP.put("lzh", "zhcn");
        LOCALE_MAP.put("zh_cn", "zhcn");
        LOCALE_MAP.put("zh_hk", "zhhk");
        LOCALE_MAP.put("zh_tw", "zhtw");
        //LOCALE_MAP.put("zhmc", "zhmc");
    }

    /**
     * Returns the locale that player uses.
     *
     * @param player The player
     */
    public static String getLocale(Player player) {
        if (Utils.MAJOR_VERSION > 15) {
            return player.getLocale().toLowerCase();
        } else if (PLAYER_LOCALE.containsKey(player.getUniqueId())) {
            return PLAYER_LOCALE.get(player.getUniqueId());
        }

        return null;
    }

    public static void addLocale(UUID uuid, String locale) {
        if (PLAYER_LOCALE == null) {
            PLAYER_LOCALE = new ConcurrentHashMap<>();
        }

        PLAYER_LOCALE.put(uuid, locale);
    }

    public static void removeLocale(UUID uuid) {
        PLAYER_LOCALE.remove(uuid);
    }

    /**
     * Returns the AuthMe messages file language code, by given locale and settings.
     * Dreeam - Hard mapping, based on mc1.20.6, 5/29/2024
     *
     * @param locale   The locale that player client setting uses.
     * @param settings The AuthMe settings, for default/fallback language usage.
     */
    public static String localeToCode(String locale, Settings settings) {
        // Certain locale code to AuthMe language code redirect
        if (!settings.getProperty(PluginSettings.I18N_CODE_REDIRECT).isEmpty()) {
            for (String raw : settings.getProperty(PluginSettings.I18N_CODE_REDIRECT)) {
                String[] split = raw.split(":");

                if (split.length == 2 && locale.equalsIgnoreCase(split[0])) {
                    return split[1];
                }
            }
        }

        // Match certain locale code
        if (LOCALE_MAP.containsKey(locale)) {
            return LOCALE_MAP.get(locale);
        }

        if (locale.contains("_")) {
            locale = locale.substring(0, locale.indexOf("_"));
        }

        // Match locale code with "_"
        if (LOCALE_LIST.contains(locale)) {
            return locale;
        }

        return settings.getProperty(PluginSettings.MESSAGES_LANGUAGE);
    }
}
