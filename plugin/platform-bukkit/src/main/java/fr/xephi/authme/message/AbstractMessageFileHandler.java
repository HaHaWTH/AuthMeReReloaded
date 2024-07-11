package fr.xephi.authme.message;

import com.google.common.annotations.VisibleForTesting;
import fr.xephi.authme.logger.ConsoleLogger;
import fr.xephi.authme.configruation.Configuration;
import fr.xephi.authme.initialization.DataFolder;
import fr.xephi.authme.initialization.Reloadable;
import fr.xephi.authme.logger.ConsoleLoggerFactory;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.CommonSettings;
import fr.xephi.authme.settings.properties.PluginSettings;
import fr.xephi.authme.util.FileUtils;
import fr.xephi.authme.util.message.I18NUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static fr.xephi.authme.message.MessagePathHelper.DEFAULT_LANGUAGE;

/**
 * Handles a YAML message file with a default file fallback.
 */
public abstract class AbstractMessageFileHandler implements Reloadable {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(AbstractMessageFileHandler.class);

    @DataFolder
    @Inject
    private File dataFolder;

    @Inject
    private Settings settings;

    private String filename;
    private Configuration configuration;
    private Map<String, Configuration> i18nConfiguration;
    private final String defaultFile;

    protected AbstractMessageFileHandler() {
        this.defaultFile = createFilePath(DEFAULT_LANGUAGE);
    }

    @Override
    @PostConstruct
    public void reload() {
        String language = getLanguage();
        filename = createFilePath(language);
        File messagesFile = initializeFile(filename);
        configuration = Configuration.loadFromFile(messagesFile);
        i18nConfiguration = null;
    }

    protected String getLanguage() {
        return settings.getProperty(CommonSettings.MESSAGES_LANGUAGE);
    }

    protected File getUserLanguageFile() {
        return new File(dataFolder, filename);
    }

    protected String getFilename() {
        return filename;
    }

    /**
     * Returns whether the message file configuration has an entry at the given path.
     *
     * @param path the path to verify
     * @return true if an entry exists for the path in the messages file, false otherwise
     */
    public boolean hasSection(String path) {
        return configuration.get(path) != null;
    }

    /**
     * Returns the message for the given key.
     *
     * @param key the key to retrieve the message for
     * @return the message
     */
    public String getMessage(String key) {
        String message = configuration.getString(key);
        return message == null
                ? "Error retrieving message '" + key + "'"
                : message;
    }

    /**
     * Returns the i18n message for the given key and given locale.
     *
     * @param key    the key to retrieve the message for
     * @param locale the locale that player client setting uses
     * @return the message
     */
    public String getMessageByLocale(String key, String locale) {
        if (locale == null || !settings.getProperty(PluginSettings.I18N_MESSAGES)) {
            return getMessage(key);
        }

        String message = getI18nConfiguration(locale).getString(key);
        return message == null
                ? "Error retrieving message '" + key + "'"
                : message;
    }

    /**
     * Returns the message for the given key only if it exists,
     * i.e. without falling back to the default file.
     *
     * @param key the key to retrieve the message for
     * @return the message, or {@code null} if not available
     */
    public String getMessageIfExists(String key) {
        return configuration.getString(key);
    }

    public Configuration getI18nConfiguration(String locale) {
        if (i18nConfiguration == null) {
            i18nConfiguration = new ConcurrentHashMap<>();
        }

        locale = I18NUtils.localeToCode(locale, settings);

        if (i18nConfiguration.containsKey(locale)) {
            return i18nConfiguration.get(locale);
        } else {
            // Sync with reload();
            String i18nFilename = createFilePath(locale);
            File i18nMessagesFile = initializeFile(i18nFilename);
            Configuration config = Configuration.loadFromFile(i18nMessagesFile);

            i18nConfiguration.put(locale, config);

            return config;
        }
    }

    /**
     * Creates the path to the messages file for the given language code.
     *
     * @param language the language code
     * @return path to the message file for the given language
     */
    protected abstract String createFilePath(String language);

    /**
     * Copies the messages file from the JAR to the local messages/ folder if it doesn't exist.
     *
     * @param filePath path to the messages file to use
     * @return the messages file to use
     */
    @VisibleForTesting
    File initializeFile(String filePath) {
        File file = new File(dataFolder, filePath);
        // Check that JAR file exists to avoid logging an error
        if (FileUtils.getResourceFromJar(filePath) != null && FileUtils.copyFileFromResource(file, filePath)) {
            return file;
        }

        if (FileUtils.copyFileFromResource(file, defaultFile)) {
            return file;
        } else {
            logger.warning("Wanted to copy default messages file '" + defaultFile + "' from JAR but it didn't exist");
            return null;
        }
    }
}
