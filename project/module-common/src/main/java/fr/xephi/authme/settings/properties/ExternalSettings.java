package fr.xephi.authme.settings.properties;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

/**
 * ExternalSettings
 *
 * @author TheFloodDragon
 * @since 2024/7/11 21:39
 */
public final class ExternalSettings implements SettingsHolder {

    @Comment("How much log2 rounds needed in BCrypt (do not change if you do not know what it does)")
    public static final Property<Integer> BCRYPT_LOG2_ROUND =
            newProperty("ExternalBoardOptions.bCryptLog2Round", 12);

}
