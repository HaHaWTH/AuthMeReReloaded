package fr.xephi.authme.command.help;

/**
 * Common, non-generic keys for messages used when showing command help.
 * All keys are prefixed with {@code common}.
 */
public enum HelpMessage {

    HEADER("header"),

    OPTIONAL("optional"),

    HAS_PERMISSION("hasPermission"),

    NO_PERMISSION("noPermission"),

    DEFAULT("default"),

    RESULT("result"),

    /** Shown when help output cannot be built because the command description is missing */
    HELP_FAILED_RETRIEVE_INFO("helpFailedRetrieveInfo"),

    /** Shown when the help command cannot resolve the base command */
    HELP_MISSING_BASE_COMMAND("helpMissingBaseCommand"),

    /** Shown when the user requests help for an unknown command label */
    HELP_UNKNOWN_COMMAND("helpUnknownCommand"),

    /** Shown when the command mapper corrects a typo; %command% is the resolved command path */
    HELP_ASSUMING_COMMAND("helpAssumingCommand");

    private static final String PREFIX = "common.";
    private final String key;

    /**
     * Constructor.
     *
     * @param key the message key
     */
    HelpMessage(String key) {
        this.key = PREFIX + key;
    }

    /** @return the message key */
    public String getKey() {
        return key;
    }

    /** @return the key without the common prefix */
    public String getEntryKey() {
        // Note ljacqu 20171008: #getKey is called more often than this method, so we optimize for the former method
        return key.substring(PREFIX.length());
    }
}
