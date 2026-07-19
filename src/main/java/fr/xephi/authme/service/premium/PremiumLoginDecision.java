package fr.xephi.authme.service.premium;

/**
 * Decision outcome for premium-aware login evaluation.
 */
public enum PremiumLoginDecision {
    NORMAL_AUTH,
    REQUIRE_FINAL_PASSWORD,
    AUTO_AUTHENTICATE
}
