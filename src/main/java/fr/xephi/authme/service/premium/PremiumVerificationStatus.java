package fr.xephi.authme.service.premium;

/**
 * Result status for a premium verification attempt.
 */
public enum PremiumVerificationStatus {
    VERIFIED,
    FAILED,
    UNSUPPORTED,
    SKIPPED
}
