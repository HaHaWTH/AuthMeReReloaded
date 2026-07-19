package fr.xephi.authme.service.premium;

import fr.xephi.authme.data.auth.PlayerAuth;

import java.util.UUID;

/**
 * Evaluates whether a login should proceed through the regular AuthMe flow or
 * premium-specific handling.
 */
public class PremiumLoginEvaluator {

    public PremiumLoginDecision evaluate(PlayerAuth auth, UUID verifiedUuid, boolean premiumEnabled,
            boolean verificationSucceeded, boolean autoMigrate) {
        if (!premiumEnabled || !verificationSucceeded) {
            return PremiumLoginDecision.NORMAL_AUTH;
        }
        if (auth == null) {
            return PremiumLoginDecision.NORMAL_AUTH;
        }
        if (auth.getUuid() == null || !auth.getUuid().equals(verifiedUuid)) {
            return PremiumLoginDecision.NORMAL_AUTH;
        }
        if (autoMigrate) {
            return PremiumLoginDecision.AUTO_AUTHENTICATE;
        }
        return PremiumLoginDecision.REQUIRE_FINAL_PASSWORD;
    }
}
