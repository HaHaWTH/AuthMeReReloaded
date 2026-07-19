package fr.xephi.authme.service.premium;

import fr.xephi.authme.data.auth.PlayerAuth;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Default implementation of the premium migration API.
 */
public class PremiumMigrationApiImpl implements PremiumMigrationApi {

    private final PremiumMigrationService service;

    public PremiumMigrationApiImpl(PremiumMigrationService service) {
        this.service = service;
    }

    @Override
    public CompletableFuture<PremiumVerificationStatus> verifyPremiumIdentity(String username) {
        return service.verifyPremiumIdentity(username);
    }

    @Override
    public CompletableFuture<Boolean> migrateAccountAsync(PlayerAuth auth, UUID verifiedUuid,
            boolean deletePasswordAfterMigration) {
        return service.migrateAccountAsync(auth, verifiedUuid, deletePasswordAfterMigration);
    }

    @Override
    public boolean shouldAutoAuthenticate(PlayerAuth auth, UUID verifiedUuid, boolean enabled, boolean verified) {
        return service.shouldAutoAuthenticate(auth, verifiedUuid, enabled, verified);
    }
}
