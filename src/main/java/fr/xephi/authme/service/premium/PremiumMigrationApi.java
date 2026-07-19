package fr.xephi.authme.service.premium;

import fr.xephi.authme.data.auth.PlayerAuth;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Public API for premium migration integrations.
 */
public interface PremiumMigrationApi {

    CompletableFuture<PremiumVerificationStatus> verifyPremiumIdentity(String username);

    CompletableFuture<Boolean> migrateAccountAsync(PlayerAuth auth, UUID verifiedUuid,
            boolean deletePasswordAfterMigration);

    boolean shouldAutoAuthenticate(PlayerAuth auth, UUID verifiedUuid, boolean enabled, boolean verified);
}
