package fr.xephi.authme.service.premium;

import java.util.Objects;

/**
 * Immutable premium migration configuration.
 */
public final class PremiumMigrationConfiguration {
    private final boolean enabled;
    private final boolean autoMigrate;
    private final boolean deletePasswordAfterMigration;
    private final boolean protectPremiumUsernames;
    private final boolean autoUpdateUsername;
    private final boolean createBackups;

    public PremiumMigrationConfiguration(boolean enabled, boolean autoMigrate,
            boolean deletePasswordAfterMigration,
            boolean protectPremiumUsernames,
            boolean autoUpdateUsername,
            boolean createBackups) {
        this.enabled = enabled;
        this.autoMigrate = autoMigrate;
        this.deletePasswordAfterMigration = deletePasswordAfterMigration;
        this.protectPremiumUsernames = protectPremiumUsernames;
        this.autoUpdateUsername = autoUpdateUsername;
        this.createBackups = createBackups;
    }

    public static PremiumMigrationConfiguration defaults() {
        return new PremiumMigrationConfiguration(false, true, true, true, true, true);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAutoMigrate() {
        return autoMigrate;
    }

    public boolean shouldDeletePasswordAfterMigration() {
        return deletePasswordAfterMigration;
    }

    public boolean shouldProtectPremiumUsernames() {
        return protectPremiumUsernames;
    }

    public boolean shouldAutoUpdateUsername() {
        return autoUpdateUsername;
    }

    public boolean shouldCreateBackups() {
        return createBackups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PremiumMigrationConfiguration)) {
            return false;
        }
        PremiumMigrationConfiguration that = (PremiumMigrationConfiguration) o;
        return enabled == that.enabled
                && autoMigrate == that.autoMigrate
                && deletePasswordAfterMigration == that.deletePasswordAfterMigration
                && protectPremiumUsernames == that.protectPremiumUsernames
                && autoUpdateUsername == that.autoUpdateUsername
                && createBackups == that.createBackups;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, autoMigrate, deletePasswordAfterMigration,
                protectPremiumUsernames, autoUpdateUsername, createBackups);
    }
}
