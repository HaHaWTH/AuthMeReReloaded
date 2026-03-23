package fr.xephi.authme.message;

/**
 * Keys for translatable messages managed by {@link Messages}.
 */
public enum MessageKey {
    /**
     * You have been disconnected due to doubled login.
     */
    DOUBLE_LOGIN_FIX("double_login_fix.fix_message"),

    /**
     * You are stuck in portal during Login.
     */
    LOCATION_FIX_PORTAL("login_location_fix.fix_portal"),

    /**
     * You are stuck underground during Login.
     */
    LOCATION_FIX_UNDERGROUND("login_location_fix.fix_underground"),

    /**
     * You are stuck underground during Login, but we cant fix it.
     */
    LOCATION_FIX_UNDERGROUND_CANT_FIX("login_location_fix.cannot_fix_underground"),

    /**
     * Bedrock auto login success!
     */
    BEDROCK_AUTO_LOGGED_IN("bedrock_auto_login.success"),

    /**
     * In order to use this command you must be authenticated!
     */
    DENIED_COMMAND("error.denied_command"),

    /**
     * A player with the same IP is already in game!
     */
    SAME_IP_ONLINE("on_join_validation.same_ip_online"),

    /**
     * In order to chat you must be authenticated!
     */
    DENIED_CHAT("error.denied_chat"),

    /** AntiBot protection mode is enabled! You have to wait some minutes before joining the server. */
    KICK_ANTIBOT("antibot.kick_antibot"),

    /** This user isn't registered! */
    UNKNOWN_USER("error.unregistered_user"),

    /** You're not logged in! */
    NOT_LOGGED_IN("error.not_logged_in"),

    /** Usage: /login &lt;password&gt; */
    USAGE_LOGIN("login.command_usage"),

    /** Wrong password! */
    WRONG_PASSWORD("login.wrong_password"),

    /** Successfully unregistered! */
    UNREGISTERED_SUCCESS("unregister.success"),

    /** In-game registration is disabled! */
    REGISTRATION_DISABLED("registration.disabled"),

    /** Logged-in due to Session Reconnection. */
    SESSION_RECONNECTION("session.valid_session"),

    /** Successful login! */
    LOGIN_SUCCESS("login.success"),

    /** Your account isn't activated yet, please check your emails! */
    ACCOUNT_NOT_ACTIVATED("misc.account_not_activated"),

    /** You already have registered this username! */
    NAME_ALREADY_REGISTERED("registration.name_taken"),

    /** You don't have the permission to perform this action! */
    NO_PERMISSION("error.no_permission"),

    /** An unexpected error occurred, please contact an administrator! */
    ERROR("error.unexpected_error"),

    /** Please, login with the command: /login &lt;password&gt; */
    LOGIN_MESSAGE("login.login_request"),

    /** Please, register to the server with the command: /register &lt;password&gt; &lt;ConfirmPassword&gt; */
    REGISTER_MESSAGE("registration.register_request"),

    /** You have exceeded the maximum number of registrations (%reg_count/%max_acc %reg_names) for your connection! */
    MAX_REGISTER_EXCEEDED("error.max_registration", "%max_acc", "%reg_count", "%reg_names"),

    /** Usage: /register &lt;password&gt; &lt;ConfirmPassword&gt; */
    USAGE_REGISTER("registration.command_usage"),

    /** Usage: /unregister &lt;password&gt; */
    USAGE_UNREGISTER("unregister.command_usage"),

    /** Password changed successfully! */
    PASSWORD_CHANGED_SUCCESS("misc.password_changed"),

    /** Passwords didn't match, check them again! */
    PASSWORD_MATCH_ERROR("password.match_error"),

    /** You can't use your name as password, please choose another one... */
    PASSWORD_IS_USERNAME_ERROR("password.name_in_password"),

    /** The chosen password isn't safe, please choose another one... */
    PASSWORD_UNSAFE_ERROR("password.unsafe_password"),

    /** Your chosen password is not secure. It was used %pwned_count times already! Please use a stronger password... */
    PASSWORD_PWNED_ERROR("password.pwned_password", "%pwned_count"),

    /** Your password contains illegal characters. Allowed chars: %valid_chars */
    PASSWORD_CHARACTERS_ERROR("password.forbidden_characters", "%valid_chars"),

    /** Your IP has been changed and your session data has expired! */
    SESSION_EXPIRED("session.invalid_session"),

    /** Only registered users can join the server! Please visit http://example.com to register yourself! */
    MUST_REGISTER_MESSAGE("registration.reg_only"),

    /** You're already logged in! */
    ALREADY_LOGGED_IN_ERROR("error.logged_in"),

    /** Logged out successfully! */
    LOGOUT_SUCCESS("misc.logout"),

    /** The same username is already playing on the server! */
    USERNAME_ALREADY_ONLINE_ERROR("on_join_validation.same_nick_online"),

    /** Successfully registered! */
    REGISTER_SUCCESS("registration.success"),

    /** Your password is too short or too long! Please try with another one! */
    INVALID_PASSWORD_LENGTH("password.wrong_length"),

    /** Configuration and database have been reloaded correctly! */
    CONFIG_RELOAD_SUCCESS("misc.reload"),

    /** Login timeout exceeded, you have been kicked from the server, please try again! */
    LOGIN_TIMEOUT_ERROR("login.timeout_error"),

    /** Usage: /changepassword &lt;oldPassword&gt; &lt;newPassword&gt; */
    USAGE_CHANGE_PASSWORD("misc.usage_change_password"),

    /** Your username is either too short or too long! */
    INVALID_NAME_LENGTH("on_join_validation.name_length"),

    /** Your username contains illegal characters. Allowed chars: %valid_chars */
    INVALID_NAME_CHARACTERS("on_join_validation.characters_in_name", "%valid_chars"),

    /** Please add your email to your account with the command: /email add &lt;yourEmail&gt; &lt;confirmEmail&gt; */
    ADD_EMAIL_MESSAGE("email.add_email_request"),

    /** Forgot your password? Please use the command: /email recovery &lt;yourEmail&gt; */
    FORGOT_PASSWORD_MESSAGE("recovery.forgot_password_hint"),

    /** To log in you have to solve a captcha code, please use the command: /captcha %captcha_code */
    USAGE_CAPTCHA("captcha.usage_captcha", "%captcha_code"),

    /** Wrong captcha, please type "/captcha %captcha_code" into the chat! */
    CAPTCHA_WRONG_ERROR("captcha.wrong_captcha", "%captcha_code"),

    /** Captcha code solved correctly! */
    CAPTCHA_SUCCESS("captcha.valid_captcha"),

    /** To register you have to solve a captcha first, please use the command: /captcha %captcha_code */
    CAPTCHA_FOR_REGISTRATION_REQUIRED("captcha.captcha_for_registration", "%captcha_code"),

    /** Valid captcha! You may now register with /register */
    REGISTER_CAPTCHA_SUCCESS("captcha.register_captcha_valid"),

    /** A VIP player has joined the server when it was full! */
    KICK_FOR_VIP("error.kick_for_vip"),

    /** The server is full, try again later! */
    KICK_FULL_SERVER("on_join_validation.kick_full_server"),

    /** An error occurred: unresolved player hostname! **/
    KICK_UNRESOLVED_HOSTNAME("error.kick_unresolved_hostname"),

    /** Usage: /email add &lt;email&gt; &lt;confirmEmail&gt; */
    USAGE_ADD_EMAIL("email.usage_email_add"),

    /** Usage: /email change &lt;oldEmail&gt; &lt;newEmail&gt; */
    USAGE_CHANGE_EMAIL("email.usage_email_change"),

    /** Usage: /email recovery &lt;Email&gt; */
    USAGE_RECOVER_EMAIL("recovery.command_usage"),

    /** Invalid new email, try again! */
    INVALID_NEW_EMAIL("email.new_email_invalid"),

    /** Invalid old email, try again! */
    INVALID_OLD_EMAIL("email.old_email_invalid"),

    /** Invalid email address, try again! */
    INVALID_EMAIL("email.invalid"),

    /** Email address successfully added to your account! */
    EMAIL_ADDED_SUCCESS("email.added"),

    /** Adding email was not allowed */
    EMAIL_ADD_NOT_ALLOWED("email.add_not_allowed"),

    /** Please confirm your email address! */
    CONFIRM_EMAIL_MESSAGE("email.request_confirmation"),

    /** Email address changed correctly! */
    EMAIL_CHANGED_SUCCESS("email.changed"),

    /** Changing email was not allowed */
    EMAIL_CHANGE_NOT_ALLOWED("email.change_not_allowed"),

    /** Your current email address is: %email */
    EMAIL_SHOW("email.email_show", "%email"),

    /** You currently don't have email address associated with this account. */
    SHOW_NO_EMAIL("email.no_email_for_account"),

    /** Recovery email sent successfully! Please check your email inbox! */
    RECOVERY_EMAIL_SENT_MESSAGE("recovery.email_sent"),

    /** Your country is banned from this server! */
    COUNTRY_BANNED_ERROR("on_join_validation.country_banned"),

    /** [AntiBotService] AntiBot enabled due to the huge number of connections! */
    ANTIBOT_AUTO_ENABLED_MESSAGE("antibot.auto_enabled"),

    /** [AntiBotService] AntiBot disabled after %m minutes! */
    ANTIBOT_AUTO_DISABLED_MESSAGE("antibot.auto_disabled", "%m"),

    /** The email address is already being used */
    EMAIL_ALREADY_USED_ERROR("email.already_used"),

    /** Your secret code is %code. You can scan it from here %url */
    TWO_FACTOR_CREATE("two_factor.code_created", "%code", "%url"),

    /** Please confirm your code with /2fa confirm &lt;code&gt; */
    TWO_FACTOR_CREATE_CONFIRMATION_REQUIRED("two_factor.confirmation_required"),

    /** Please submit your two-factor authentication code with /2fa code &lt;code&gt; */
    TWO_FACTOR_CODE_REQUIRED("two_factor.code_required"),

    /** Two-factor authentication is already enabled for your account! */
    TWO_FACTOR_ALREADY_ENABLED("two_factor.already_enabled"),

    /** No 2fa key has been generated for you or it has expired. Please run /2fa add */
    TWO_FACTOR_ENABLE_ERROR_NO_CODE("two_factor.enable_error_no_code"),

    /** Successfully enabled two-factor authentication for your account */
    TWO_FACTOR_ENABLE_SUCCESS("two_factor.enable_success"),

    /** Wrong code or code has expired. Please run /2fa add */
    TWO_FACTOR_ENABLE_ERROR_WRONG_CODE("two_factor.enable_error_wrong_code"),

    /** Two-factor authentication is not enabled for your account. Run /2fa add */
    TWO_FACTOR_NOT_ENABLED_ERROR("two_factor.not_enabled_error"),

    /** Successfully removed two-factor auth from your account */
    TWO_FACTOR_REMOVED_SUCCESS("two_factor.removed_success"),

    /** Invalid code! */
    TWO_FACTOR_INVALID_CODE("two_factor.invalid_code"),

    /** You are not the owner of this account. Please choose another name! */
    NOT_OWNER_ERROR("on_join_validation.not_owner_error"),

    /** You should join using username %valid, not %invalid. */
    INVALID_NAME_CASE("on_join_validation.invalid_name_case", "%valid", "%invalid"),

    /** You have been temporarily banned for failing to log in too many times. */
    TEMPBAN_MAX_LOGINS("error.tempban_max_logins"),

    /** You own %count accounts: */
    ACCOUNTS_OWNED_SELF("misc.accounts_owned_self", "%count"),

    /** The player %name has %count accounts: */
    ACCOUNTS_OWNED_OTHER("misc.accounts_owned_other", "%name", "%count"),

    /** An admin just registered you; please log in again */
    KICK_FOR_ADMIN_REGISTER("registration.kicked_admin_registered"),

    /** Error: not all required settings are set for sending emails. Please contact an admin. */
    INCOMPLETE_EMAIL_SETTINGS("email.incomplete_settings"),

    /** The email could not be sent. Please contact an administrator. */
    EMAIL_SEND_FAILURE("email.send_failure"),

    /** A recovery code to reset your password has been sent to your email. */
    RECOVERY_CODE_SENT("recovery.code.code_sent"),

    /** The recovery code is not correct! You have %count tries remaining. */
    INCORRECT_RECOVERY_CODE("recovery.code.incorrect", "%count"),

    /**
     * You have exceeded the maximum number of attempts to enter the recovery code.
     * Use "/email recovery [email]" to generate a new one.
     */
    RECOVERY_TRIES_EXCEEDED("recovery.code.tries_exceeded"),

    /** Recovery code entered correctly! */
    RECOVERY_CODE_CORRECT("recovery.code.correct"),

    /** Please use the command /email setpassword to change your password immediately. */
    RECOVERY_CHANGE_PASSWORD("recovery.code.change_password"),

    /** You cannot change your password using this command anymore. */
    CHANGE_PASSWORD_EXPIRED("email.change_password_expired"),

    /** An email was already sent recently. You must wait %time before you can send a new one. */
    EMAIL_COOLDOWN_ERROR("email.email_cooldown_error", "%time"),

    /**
     * This command is sensitive and requires an email verification!
     * Check your inbox and follow the email's instructions.
     */
    VERIFICATION_CODE_REQUIRED("verification.code_required"),

    /** Usage: /verification &lt;code&gt; */
    USAGE_VERIFICATION_CODE("verification.command_usage"),

    /** Incorrect code, please type "/verification &lt;code&gt;" into the chat, using the code you received by email */
    INCORRECT_VERIFICATION_CODE("verification.incorrect_code"),

    /** Your identity has been verified! You can now execute all commands within the current session! */
    VERIFICATION_CODE_VERIFIED("verification.success"),

    /** You can already execute every sensitive command within the current session! */
    VERIFICATION_CODE_ALREADY_VERIFIED("verification.already_verified"),

    /** Your code has expired! Execute another sensitive command to get a new code! */
    VERIFICATION_CODE_EXPIRED("verification.code_expired"),

    /** To verify your identity you need to link an email address with your account! */
    VERIFICATION_CODE_EMAIL_NEEDED("verification.email_needed"),

    /** You used a command too fast! Please, join the server again and wait more before using any command. */
    QUICK_COMMAND_PROTECTION_KICK("on_join_validation.quick_command"),

    /** Failed to parse %plugin% command! */
    COMMAND_PARSE_FAILED("error.command_parse_failed", "%plugin%"),

    /** Unknown command! (AuthMe command router) */
    UNKNOWN_AUTHME_COMMAND("error.unknown_authme_command"),

    /** Did you mean %command%? */
    COMMAND_SUGGEST_SIMILAR("error.command_suggest_similar", "%command%"),

    /** Use the command /%label% help to view help. */
    COMMAND_USE_HELP("error.command_use_help", "%label%"),

    /** Incorrect command arguments! */
    INCORRECT_COMMAND_ARGUMENTS_GENERIC("error.incorrect_command_arguments"),

    /** Detailed help: /%label% help %child% */
    COMMAND_DETAILED_HELP_SYNTAX("error.command_detailed_help_syntax", "%label%", "%child%"),

    /** second */
    SECOND("time.second"),

    /** seconds */
    SECONDS("time.seconds"),

    /** minute */
    MINUTE("time.minute"),

    /** minutes */
    MINUTES("time.minutes"),

    /** hour */
    HOUR("time.hour"),

    /** hours */
    HOURS("time.hours"),

    /** day */
    DAY("time.day"),

    /** days */
    DAYS("time.days"),

    // Staff command feedback (/authme and related)
    /** Error occurred during reload of AuthMe: aborting */
    RELOAD_ABORT_ERROR("admin.reload_abort_error"),

    /** Note: cannot change database type during /authme reload */
    RELOAD_DATABASE_TYPE_NOTE("admin.reload_database_type_note"),

    /** [AuthMe] Spawn has failed, please try to define the spawn */
    ADMIN_SPAWN_FAILED("admin.spawn_failed"),

    /** [AuthMe] First spawn has failed, please try to define the first spawn */
    ADMIN_FIRST_SPAWN_FAILED("admin.first_spawn_failed"),

    /** [AuthMe] Correctly defined new spawn point */
    ADMIN_SET_SPAWN_SUCCESS("admin.set_spawn_success"),

    /** [AuthMe] SetSpawn has failed, please retry */
    ADMIN_SET_SPAWN_FAILED("admin.set_spawn_failed"),

    /** [AuthMe] Correctly defined new first spawn point */
    ADMIN_SET_FIRST_SPAWN_SUCCESS("admin.set_first_spawn_success"),

    /** [AuthMe] SetFirstSpawn has failed, please retry */
    ADMIN_SET_FIRST_SPAWN_FAILED("admin.set_first_spawn_failed"),

    /** [AuthMe] AntiBot status: %status% */
    ADMIN_ANTIBOT_STATUS("admin.antibot_status", "%status%"),

    /** [AuthMe] AntiBot Manual Override: enabled! */
    ADMIN_ANTIBOT_MANUAL_ENABLED("admin.antibot_manual_enabled"),

    /** [AuthMe] AntiBot Manual Override: disabled! */
    ADMIN_ANTIBOT_MANUAL_DISABLED("admin.antibot_manual_disabled"),

    /** Invalid AntiBot mode! */
    ADMIN_ANTIBOT_INVALID_MODE("admin.antibot_invalid_mode"),

    /** Detailed help: %command% */
    ADMIN_ANTIBOT_DETAILED_HELP("admin.antibot_detailed_help", "%command%"),

    /** Purged data for player %player% */
    ADMIN_PURGE_PLAYER_SUCCESS("admin.purge_player_success", "%player%"),

    /**
     * This player is still registered! Are you sure you want to proceed?
     * Use '/authme purgeplayer %player% force' to run the command anyway
     */
    ADMIN_PURGE_PLAYER_CONFIRM("admin.purge_player_confirm", "%player%"),

    /** All players last position locations are now reset */
    ADMIN_PURGE_LASTPOS_ALL("admin.purge_lastpos_all"),

    /** %player%'s last position location is now reset */
    ADMIN_PURGE_LASTPOS_ONE("admin.purge_lastpos_one", "%player%"),

    /** Player needs to be online! */
    ADMIN_FORCE_LOGIN_OFFLINE("admin.force_login_offline"),

    /** You cannot force login the player %player%! */
    ADMIN_FORCE_LOGIN_DENIED("admin.force_login_denied", "%player%"),

    /** Force login for %player% performed! */
    ADMIN_FORCE_LOGIN_SUCCESS("admin.force_login_success", "%player%"),

    /** [AuthMe] This IP does not exist in the database. */
    ADMIN_ACCOUNTS_IP_UNKNOWN("admin.accounts_ip_unknown"),

    /** [AuthMe] %player% is a single account player */
    ADMIN_ACCOUNTS_SINGLE("admin.accounts_single", "%player%"),

    /** No known last IP address for player */
    ADMIN_ACCOUNTS_NO_LAST_IP("admin.accounts_no_last_ip"),

    /** [AuthMe] %player% has %count% accounts. */
    ADMIN_ACCOUNTS_MULTIPLE("admin.accounts_multiple", "%player%", "%count%"),

    /** [AuthMe] %list%. */
    ADMIN_ACCOUNTS_LIST("admin.accounts_list", "%list%"),

    /** Current IP of %name% is %ip%:%port% */
    ADMIN_GET_IP_CURRENT("admin.get_ip_current", "%name%", "%ip%", "%port%"),

    /** %name% is not registered in the database */
    ADMIN_GET_IP_NOT_REGISTERED("admin.get_ip_not_registered", "%name%"),

    /** Database: last IP: %lastIp%, registration IP: %regIp% */
    ADMIN_GET_IP_DATABASE("admin.get_ip_database", "%lastIp%", "%regIp%"),

    /** [AuthMe] %player%'s email: %email% */
    ADMIN_GET_EMAIL("admin.get_email", "%player%", "%email%"),

    /** [AuthMe] %player% last login: %date% */
    ADMIN_LAST_LOGIN("admin.last_login", "%player%", "%date%"),

    /** [AuthMe] The player %player% last logged in %interval% ago */
    ADMIN_LAST_LOGIN_INTERVAL("admin.last_login_interval", "%player%", "%interval%"),

    /** [AuthMe] Last player's IP: %ip% */
    ADMIN_LAST_LOGIN_IP("admin.last_login_ip", "%ip%"),

    /** never */
    ADMIN_LAST_LOGIN_NEVER("admin.last_login_never"),

    /** %days% days %hours% hours %mins% mins %secs% secs */
    ADMIN_LAST_LOGIN_INTERVAL_PARTS("admin.last_login_interval_parts", "%days%", "%hours%", "%mins%", "%secs%"),

    /** Converters: %list% */
    ADMIN_CONVERTER_LIST("admin.converter_list", "%list%"),

    /** [AuthMe] Successfully started %name% */
    ADMIN_CONVERTER_STARTED("admin.converter_started", "%name%"),

    /** Player '%player%' does not have two-factor auth enabled */
    ADMIN_TOTP_NOT_ENABLED("admin.totp_not_enabled", "%player%"),

    /** Disabled two-factor authentication successfully for '%player%' */
    ADMIN_TOTP_DISABLED_SUCCESS("admin.totp_disabled_success", "%player%"),

    /** Successfully updated the help file '%file%' */
    ADMIN_HELP_FILE_UPDATED("admin.help_file_updated", "%file%"),

    /** Could not update help file: %error% */
    ADMIN_HELP_FILE_UPDATE_FAILED("admin.help_file_update_failed", "%error%"),

    /** Player '%player%' does NOT have two-factor auth enabled */
    ADMIN_TOTP_VIEW_DISABLED("admin.totp_view_disabled", "%player%"),

    /** Player '%player%' has enabled two-factor authentication */
    ADMIN_TOTP_VIEW_ENABLED("admin.totp_view_enabled", "%player%"),

    /** This server is running %plugin% v%version% b%build%! <3 */
    AUTHME_INFO_RUNNING("admin.authme_info_running", "%plugin%", "%version%", "%build%"),

    /** Use the command /authme help to view help. */
    AUTHME_INFO_HELP("admin.authme_info_help"),

    /** Use the command /authme about to view about. */
    AUTHME_INFO_ABOUT("admin.authme_info_about"),

    /** The value you've entered is invalid! */
    PURGE_INVALID_VALUE("admin.purge_invalid_value"),

    /** You can only purge data older than %days% days */
    PURGE_MINIMUM_DAYS("admin.purge_minimum_days", "%days%"),

    /** No players to purge */
    PURGE_NO_PLAYERS("admin.purge_no_players"),

    /** Purge is already in progress! Aborting purge request */
    PURGE_ALREADY_RUNNING("admin.purge_already_running"),

    /** [AuthMe] Purge progress %current%/%total% */
    PURGE_PROGRESS("admin.purge_progress", "%current%", "%total%"),

    /** [AuthMe] Database has been purged successfully */
    PURGE_DATABASE_SUCCESS("admin.purge_database_success"),

    /** [AuthMe] Recently logged in players */
    RECENT_PLAYERS_HEADER("admin.recent_players_header"),

    /** - %player% (%lastlogin% with IP %ip%) */
    RECENT_PLAYER_LINE("admin.recent_player_line", "%player%", "%lastlogin%", "%ip%"),

    /** Can't perform a backup: disabled in configuration. Cause of the backup: %cause% */
    BACKUP_DISABLED("admin.backup_disabled", "%cause%"),

    /** A backup has been performed successfully. Cause of the backup: %cause% */
    BACKUP_SUCCESS("admin.backup_success", "%cause%"),

    /** Error while performing a backup! Cause of the backup: %cause% */
    BACKUP_FAILED("admin.backup_failed", "%cause%"),

    /** Please configure your connection to %type% and re-run this command */
    CONVERTER_CONFIGURE_DESTINATION("admin.converter_configure_destination", "%type%"),

    /** The data source to convert from could not be initialized */
    CONVERTER_SOURCE_INIT_FAILED("admin.converter_source_init_failed"),

    /** Skipped conversion for players which were already in %type%: %players% */
    CONVERTER_SKIPPED_EXISTING("admin.converter_skipped_existing", "%type%", "%players%"),

    /** Database successfully converted from %source% to %target% */
    CONVERTER_FINISHED("admin.converter_finished", "%source%", "%target%"),

    /** xAuth has not been found, please put xAuth.jar in your plugin folder and restart! */
    CONVERTER_XAUTH_CLASS_NOT_FOUND("admin.converter_xauth_class_not_found"),

    /** [AuthMe] xAuth plugin not found */
    CONVERTER_XAUTH_PLUGIN_NOT_FOUND("admin.converter_xauth_plugin_not_found"),

    /** [AuthMe] xAuth H2 database not found, checking for MySQL or SQLite data... */
    CONVERTER_XAUTH_H2_MISSING("admin.converter_xauth_h2_missing"),

    /** [AuthMe] Error while importing xAuthPlayers: did not find any players */
    CONVERTER_XAUTH_NO_PLAYERS("admin.converter_xauth_no_players"),

    /** [AuthMe] Starting import... */
    CONVERTER_XAUTH_STARTING("admin.converter_xauth_starting"),

    /** [AuthMe] Successfully converted from xAuth database */
    CONVERTER_XAUTH_SUCCESS("admin.converter_xauth_success"),

    /** CrazyLogin file not found, please put %file% in AuthMe folder! */
    CONVERTER_CRAZYLOGIN_FILE_NOT_FOUND("admin.converter_crazylogin_file_not_found", "%file%"),

    /** Failed to convert from SQLite. Please see the log for more info */
    CONVERTER_LOGINSECURITY_FAILED("admin.converter_loginsecurity_failed"),

    /** Migrated %count% accounts successfully from LoginSecurity */
    CONVERTER_LOGINSECURITY_MIGRATED("admin.converter_loginsecurity_migrated", "%count%"),

    /** Skipped conversion for players which were already in AuthMe: %players% */
    CONVERTER_LOGINSECURITY_SKIPPED("admin.converter_loginsecurity_skipped", "%players%"),

    /** The file '%path%' does not exist */
    CONVERTER_LOGINSECURITY_FILE_MISSING("admin.converter_loginsecurity_file_missing", "%path%"),

    /** The LoginSecurity database or username is not configured in AuthMe's config.yml */
    CONVERTER_LOGINSECURITY_MYSQL_UNCONFIGURED("admin.converter_loginsecurity_mysql_unconfigured"),

    /** Could not connect to LoginSecurity using Sqlite = %sqlite%, see log for more info */
    CONVERTER_LOGINSECURITY_CONNECT_FAILED("admin.converter_loginsecurity_connect_failed", "%sqlite%"),

    /** ==========[ %plugin% ABOUT ]========== */
    ABOUT_HEADER("about.header", "%plugin%"),

    /** Version: %plugin% v%version% (build: %build%) */
    ABOUT_VERSION("about.version", "%plugin%", "%version%", "%build%"),

    /** Database Implementation: %backend% */
    ABOUT_DATABASE("about.database", "%backend%"),

    /** Authors: */
    ABOUT_AUTHORS_HEADER("about.authors_header"),

    /** Retired authors: */
    ABOUT_RETIRED_HEADER("about.retired_header"),

    /** Developer line (name // mcname (role)) */
    ABOUT_DEVELOPER_LINE("about.developer_line", "%name%", "%mcname%", "%function%"),

    /** (In-Game) suffix when developer is online */
    ABOUT_DEVELOPER_INGAME("about.developer_ingame"),

    /** Website: %url% */
    ABOUT_WEBSITE("about.website", "%url%"),

    /** License: GNU GPL v3.0 */
    ABOUT_LICENSE("about.license"),

    /** Copyright line */
    ABOUT_COPYRIGHT("about.copyright", "%year%"),

    /** AuthMe debug utils */
    DEBUG_COMMAND_TITLE("debug.command_title"),

    /** Sections available to you: */
    DEBUG_COMMAND_SECTIONS_AVAILABLE("debug.command_sections_available"),

    /** - %name%: %description% */
    DEBUG_COMMAND_SECTION_LINE("debug.command_section_line", "%name%", "%description%"),

    /** You don't have permission to view any debug section */
    DEBUG_COMMAND_NO_PERM_ANY("debug.command_no_perm_any"),

    /** You don't have permission for this section. See /authme debug */
    DEBUG_COMMAND_NO_PERM_SECTION("debug.command_no_perm_section"),

    /** AuthMe spawn location viewer */
    DEBUG_SPAWN_TITLE("debug.spawn_title"),

    /** Spawn priority: %list% */
    DEBUG_SPAWN_PRIORITY("debug.spawn_priority", "%list%"),

    /** AuthMe spawn location: %location% */
    DEBUG_SPAWN_LOCATION("debug.spawn_location", "%location%"),

    /** AuthMe first spawn location: %location% */
    DEBUG_SPAWN_FIRST_LOCATION("debug.spawn_first_location", "%location%"),

    /** AuthMe (first)spawn are only used depending on the configured priority! */
    DEBUG_SPAWN_PRIORITY_NOTE("debug.spawn_priority_note"),

    /** Use '/authme debug spawn ?' for further help */
    DEBUG_SPAWN_HELP_HINT("debug.spawn_help_hint"),

    /** Use /authme spawn and /authme firstspawn to teleport to the spawns. */
    DEBUG_SPAWN_HELP_TELEPORT("debug.spawn_help_teleport"),

    /** /authme set(first)spawn sets the (first) spawn to your current location. */
    DEBUG_SPAWN_HELP_SET("debug.spawn_help_set"),

    /** Use /authme debug spawn <player> to view where a player would be teleported to. */
    DEBUG_SPAWN_HELP_PLAYER("debug.spawn_help_player"),

    /** Read more at wiki URL */
    DEBUG_SPAWN_HELP_WIKI("debug.spawn_help_wiki"),

    /** Player '%player%' is not online! */
    DEBUG_SPAWN_PLAYER_OFFLINE("debug.spawn_player_offline", "%player%"),

    /** Player '%player%' has spawn location: %location% */
    DEBUG_SPAWN_PLAYER_LOCATION("debug.spawn_player_location", "%player%", "%location%"),

    /** Note: this check excludes the AuthMe firstspawn. */
    DEBUG_SPAWN_PLAYER_NOTE("debug.spawn_player_note"),

    /** AuthMe test email sender */
    DEBUG_MAIL_TITLE("debug.mail_title"),

    /** Email config incomplete */
    DEBUG_MAIL_CONFIG_INCOMPLETE("debug.mail_config_incomplete"),

    /** Test email sent to %email% with success */
    DEBUG_MAIL_SENT("debug.mail_sent", "%email%"),

    /** Failed to send test mail to %email% */
    DEBUG_MAIL_FAILED("debug.mail_failed", "%email%"),

    /** Please provide an email address */
    DEBUG_MAIL_PROVIDE_ADDRESS("debug.mail_provide_address"),

    /** No email set for your account */
    DEBUG_MAIL_NO_ACCOUNT_EMAIL("debug.mail_no_account_email"),

    /** Invalid email usage */
    DEBUG_MAIL_INVALID("debug.mail_invalid"),

    /** AuthMe test email (subject) */
    DEBUG_MAIL_SUBJECT("debug.mail_subject"),

    /** Sample HTML email body */
    DEBUG_MAIL_BODY("debug.mail_body", "%server%"),

    /** AuthMe database viewer */
    DEBUG_DB_TITLE("debug.db_title"),

    /** Enter player name to view his data in the database. */
    DEBUG_DB_ENTER_NAME("debug.db_enter_name"),

    /** Example: /authme debug db Bobby */
    DEBUG_DB_EXAMPLE("debug.db_example"),

    /** No record exists for '%name%' */
    DEBUG_DB_NO_RECORD("debug.db_no_record", "%name%"),

    /** [AuthMe] Player %nick% / %realname% */
    DEBUG_DB_PLAYER_HEADER("debug.db_player_header", "%nick%", "%realname%"),

    /** Email / IP / Group summary line */
    DEBUG_DB_SUMMARY("debug.db_summary", "%email%", "%ip%", "%group%"),

    /** Quit location: %location% */
    DEBUG_DB_QUIT_LOC("debug.db_quit_location", "%location%"),

    /** Last login: %date% */
    DEBUG_DB_LAST_LOGIN("debug.db_last_login", "%date%"),

    /** Registration: %date% with IP %regip% */
    DEBUG_DB_REGISTRATION("debug.db_registration", "%date%", "%regip%"),

    /** Hash / salt (partial) */
    DEBUG_DB_HASH("debug.db_hash", "%hash%", "%salt%"),

    /** TOTP code (partial) */
    DEBUG_DB_TOTP("debug.db_totp", "%totp%"),

    /** Not available (null) */
    DEBUG_DB_DATE_NULL("debug.db_date_null"),

    /** Not available (0) */
    DEBUG_DB_DATE_ZERO("debug.db_date_zero"),

    /** AuthMe permission groups */
    DEBUG_GROUPS_TITLE("debug.groups_title"),

    /** Player %name% could not be found */
    DEBUG_GROUPS_PLAYER_NOT_FOUND("debug.groups_player_not_found", "%name%"),

    /** Player %name% has permission groups: %groups% */
    DEBUG_GROUPS_LIST("debug.groups_list", "%name%", "%groups%"),

    /** Primary group is: %primary% */
    DEBUG_GROUPS_PRIMARY("debug.groups_primary", "%primary%"),

    /** Defaults can be changed for the MySQL data source only. */
    DEBUG_MYSQL_NON_MYSQL("debug.mysql_non_mysql"),

    /** [AuthMe] MySQL change '%column%' */
    DEBUG_MYSQL_CHANGE_HEADER("debug.mysql_change_header", "%column%"),

    /** Replaced NULLs with default value */
    DEBUG_MYSQL_REPLACED_NULLS("debug.mysql_replaced_nulls", "%value%", "%rows%"),

    /** Changed column NOT NULL */
    DEBUG_MYSQL_NOT_NULL_SET("debug.mysql_not_null_set", "%column%"),

    /** Changed column to allow nulls */
    DEBUG_MYSQL_ALLOW_NULLS("debug.mysql_allow_nulls", "%column%"),

    /** Replaced default value to NULL */
    DEBUG_MYSQL_REPLACED_DEFAULT_NULL("debug.mysql_replaced_default_null", "%value%", "%rows%"),

    /** MySQL column details */
    DEBUG_MYSQL_DETAILS_TITLE("debug.mysql_details_title"),

    /** Failed while showing column details */
    DEBUG_MYSQL_DETAILS_FAILED("debug.mysql_details_failed"),

    /** MySQL column changer title */
    DEBUG_MYSQL_USAGE_TITLE("debug.mysql_usage_title"),

    /** Adds or removes a NOT NULL constraint for a column. */
    DEBUG_MYSQL_USAGE_INTRO("debug.mysql_usage_intro"),

    /** Example add line */
    DEBUG_MYSQL_USAGE_EXAMPLE_ADD("debug.mysql_usage_example_add"),

    /** Example add command */
    DEBUG_MYSQL_USAGE_EXAMPLE_ADD_CMD("debug.mysql_usage_example_add_cmd"),

    /** Example remove line */
    DEBUG_MYSQL_USAGE_EXAMPLE_REMOVE("debug.mysql_usage_example_remove"),

    /** Available columns: %columns% */
    DEBUG_MYSQL_AVAILABLE_COLS("debug.mysql_available_columns", "%columns%"),

    /** Legend for @ and # suffixes */
    DEBUG_MYSQL_LEGEND("debug.mysql_legend"),

    /** Column list error */
    DEBUG_MYSQL_COL_LIST_ERROR("debug.mysql_column_list_error"),

    /** NOT NULL state text */
    DEBUG_MYSQL_COL_STATE_NOT_NULL("debug.mysql_col_state_not_null"),

    /** nullable state text */
    DEBUG_MYSQL_COL_STATE_NULLABLE("debug.mysql_col_state_nullable"),

    /** no default */
    DEBUG_MYSQL_COL_NO_DEFAULT("debug.mysql_col_no_default"),

    /** default: '%value%' */
    DEBUG_MYSQL_COL_DEFAULT("debug.mysql_col_default", "%value%"),

    /** Full detail row: %prefix% (%columnName%): %nullState%, %defaultClause% */
    DEBUG_MYSQL_COL_ROW("debug.mysql_col_row", "%prefix%", "%columnName%", "%nullState%", "%defaultClause%"),

    /** AuthMe limbo viewer */
    DEBUG_LIMBO_TITLE("debug.limbo_title"),

    /** /authme debug limbo <player> usage */
    DEBUG_LIMBO_USAGE("debug.limbo_usage"),

    /** Available limbo records: %records% */
    DEBUG_LIMBO_AVAILABLE("debug.limbo_available", "%records%"),

    /** No AuthMe limbo data */
    DEBUG_LIMBO_NO_DATA_TITLE("debug.limbo_no_data_title"),

    /** No limbo data and no player online */
    DEBUG_LIMBO_NO_DATA_DETAIL("debug.limbo_no_data_detail", "%name%"),

    /** Player / limbo / disk header */
    DEBUG_LIMBO_INFO_HEADER("debug.limbo_info_header", "%name%"),

    /** Note: no Limbo information available */
    DEBUG_LIMBO_NOTE_MEMORY("debug.limbo_note_memory"),

    /** Note: player is not online */
    DEBUG_LIMBO_NOTE_OFFLINE("debug.limbo_note_offline"),

    /** Note: no Limbo on disk available */
    DEBUG_LIMBO_NOTE_DISK("debug.limbo_note_disk"),

    /** %title%: %player% / %memory% / %disk% */
    DEBUG_LIMBO_ROW("debug.limbo_row", "%title%", "%player%", "%memory%", "%disk%"),

    /** Is op */
    DEBUG_LIMBO_FIELD_IS_OP("debug.limbo_field_is_op"),

    /** Walk speed */
    DEBUG_LIMBO_FIELD_WALK_SPEED("debug.limbo_field_walk_speed"),

    /** Can fly */
    DEBUG_LIMBO_FIELD_CAN_FLY("debug.limbo_field_can_fly"),

    /** Fly speed */
    DEBUG_LIMBO_FIELD_FLY_SPEED("debug.limbo_field_fly_speed"),

    /** Location */
    DEBUG_LIMBO_FIELD_LOCATION("debug.limbo_field_location"),

    /** Prim. group */
    DEBUG_LIMBO_FIELD_PRIMARY_GROUP("debug.limbo_field_primary_group"),

    /** Validation tests */
    DEBUG_VALID_TITLE("debug.valid_title"),

    /** Intro for validation command */
    DEBUG_VALID_INTRO("debug.valid_intro"),

    /** Use ... pass */
    DEBUG_VALID_PASS_USAGE("debug.valid_pass_usage"),

    /** Use ... mail */
    DEBUG_VALID_MAIL_USAGE("debug.valid_mail_usage"),

    /** Use ... name */
    DEBUG_VALID_NAME_USAGE("debug.valid_name_usage"),

    /** Validation of password '%password%' */
    DEBUG_VALID_VALIDATE_PASSWORD("debug.valid_validate_password", "%password%"),

    /** Valid password! */
    DEBUG_VALID_VALID_PASSWORD("debug.valid_valid_password"),

    /** Validation of email '%email%' */
    DEBUG_VALID_VALIDATE_EMAIL("debug.valid_validate_email", "%email%"),

    /** Valid email! */
    DEBUG_VALID_VALID_EMAIL("debug.valid_valid_email"),

    /** Email is not valid! */
    DEBUG_VALID_INVALID_EMAIL("debug.valid_invalid_email"),

    /** Validation of username '%name%' */
    DEBUG_VALID_VALIDATE_NAME("debug.valid_validate_name", "%name%"),

    /** Valid username! */
    DEBUG_VALID_VALID_NAME("debug.valid_valid_name"),

    /** AuthMe permission check */
    DEBUG_PERM_TITLE("debug.perm_title"),

    /** Check if a player has permission: */
    DEBUG_PERM_USAGE_LINE1("debug.perm_usage_line1"),

    /** Example: /authme debug perm bobby my.perm.node */
    DEBUG_PERM_USAGE_EXAMPLE("debug.perm_usage_example"),

    /** Permission system type used: %system% */
    DEBUG_PERM_SYSTEM("debug.perm_system", "%system%"),

    /** Player does not exist */
    DEBUG_PERM_PLAYER_NOT_EXIST("debug.perm_player_not_exist", "%player%"),

    /** Player not online; checking offline */
    DEBUG_PERM_OFFLINE_CHECK("debug.perm_offline_check", "%player%"),

    /** Success: player has permission */
    DEBUG_PERM_SUCCESS("debug.perm_success", "%player%", "%node%"),

    /** Check failed: player does NOT have permission */
    DEBUG_PERM_FAIL("debug.perm_fail", "%player%", "%node%"),

    /** Did not detect AuthMe permission; using default permission = DENIED */
    DEBUG_PERM_DEFAULT_DENIED("debug.perm_default_denied"),

    /** AuthMe country lookup */
    DEBUG_CTY_TITLE("debug.cty_title"),

    /** Check player usage */
    DEBUG_CTY_USAGE_PLAYER("debug.cty_usage_player"),

    /** Check IP usage */
    DEBUG_CTY_USAGE_IP("debug.cty_usage_ip"),

    /** IP maps to country */
    DEBUG_CTY_IP_INFO("debug.cty_ip_info", "%ip%", "%code%", "%country%"),

    /** Country is not blocked */
    DEBUG_CTY_NOT_BLOCKED("debug.cty_not_blocked"),

    /** Country is blocked */
    DEBUG_CTY_BLOCKED("debug.cty_blocked"),

    /** Note about enableProtection */
    DEBUG_CTY_NOTE("debug.cty_note", "%setting%"),

    /** No player with name */
    DEBUG_CTY_NO_PLAYER("debug.cty_no_player", "%name%"),

    /** No last IP known */
    DEBUG_CTY_NO_IP("debug.cty_no_ip", "%name%"),

    /** Player has IP address */
    DEBUG_CTY_PLAYER_IP("debug.cty_player_ip", "%name%", "%ip%"),

    /** AuthMe statistics */
    DEBUG_STATS_TITLE("debug.stats_title"),

    /** LimboPlayers in memory */
    DEBUG_STATS_LIMBO("debug.stats_limbo", "%count%"),

    /** PlayerCache size */
    DEBUG_STATS_CACHE("debug.stats_cache", "%count%"),

    /** Total players in DB */
    DEBUG_STATS_DB_TOTAL("debug.stats_db_total", "%count%"),

    /** Cached PlayerAuth objects */
    DEBUG_STATS_CACHE_OBJS("debug.stats_cache_objs", "%count%"),

    /** Total logger instances */
    DEBUG_STATS_LOGGERS("debug.stats_loggers", "%count%"),

    /** Singleton Java classes */
    DEBUG_STATS_SINGLETONS("debug.stats_singletons", "%count%"),

    /** Reloadable / SettingsDependent / HasCleanup */
    DEBUG_STATS_INJECTOR_BREAKDOWN("debug.stats_injector_breakdown", "%reloadable%", "%settingsDep%", "%cleanup%"),

    /** Player only! Please use %command% instead. */
    PLAYER_ONLY_ALTERNATIVE("admin.player_only_alternative", "%command%"),

    /** This command is only for players. */
    PLAYER_ONLY("admin.player_only");


    private String key;
    private String[] tags;

    MessageKey(String key, String... tags) {
        this.key = key;
        this.tags = tags;
    }

    /**
     * Return the key used in the messages file.
     *
     * @return The key
     */
    public String getKey() {
        return key;
    }

    /**
     * Return a list of tags (texts) that are replaced with actual content in AuthMe.
     *
     * @return List of tags
     */
    public String[] getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return key;
    }
}
