package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public final class ConverterSettings implements SettingsHolder {

    @Comment("CrazyLogin database file name")
    public static final Property<String> CRAZYLOGIN_FILE_NAME =
            newProperty("Converter.CrazyLogin.fileName", "accounts.db");

    @Comment("LoginSecurity: convert from SQLite; if false we use MySQL")
    public static final Property<Boolean> LOGINSECURITY_USE_SQLITE =
            newProperty("Converter.loginSecurity.useSqlite", true);

    @Comment("LoginSecurity MySQL: database host")
    public static final Property<String> LOGINSECURITY_MYSQL_HOST =
            newProperty("Converter.loginSecurity.mySql.host", "");

    @Comment("LoginSecurity MySQL: database name")
    public static final Property<String> LOGINSECURITY_MYSQL_DATABASE =
            newProperty("Converter.loginSecurity.mySql.database", "");

    @Comment("LoginSecurity MySQL: database user")
    public static final Property<String> LOGINSECURITY_MYSQL_USER =
            newProperty("Converter.loginSecurity.mySql.user", "");

    @Comment("LoginSecurity MySQL: password for database user")
    public static final Property<String> LOGINSECURITY_MYSQL_PASSWORD =
            newProperty("Converter.loginSecurity.mySql.password", "");

    private ConverterSettings() {
    }

    @Override
    public void registerComments(CommentsConfiguration conf) {
        conf.setComment("Converter",
                "Converter settings: see https://github.com/AuthMe/AuthMeReloaded/wiki/Converters");
    }
}
