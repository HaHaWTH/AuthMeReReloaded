package fr.xephi.authme.security.crypts;

import at.favre.lib.crypto.bcrypt.BCrypt.Version;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.ExternalSettings;

import javax.inject.Inject;

/**
 * BCrypt hash algorithm with configurable cost factor.
 */
public class BCrypt extends BCryptBasedHash {

    @Inject
    public BCrypt(Settings settings) {
        super(createHasher(settings));
    }

    private static BCryptHasher createHasher(Settings settings) {
        int bCryptLog2Rounds = settings.getProperty(ExternalSettings.BCRYPT_LOG2_ROUND);
        return new BCryptHasher(Version.VERSION_2A, bCryptLog2Rounds);
    }
}
