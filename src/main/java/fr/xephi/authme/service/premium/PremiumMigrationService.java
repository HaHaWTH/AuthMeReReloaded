package fr.xephi.authme.service.premium;

import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.output.ConsoleLoggerFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Lightweight built-in premium migration support for offline-mode accounts.
 *
 * <p>
 * This implementation intentionally avoids unsupported protocol assumptions. It
 * only provides a
 * secure, opt-in service layer that can gate account migration based on
 * verified Mojang identity
 * and preserve existing AuthMe behaviour when verification is unavailable or
 * disabled.
 * </p>
 */
public class PremiumMigrationService {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(PremiumMigrationService.class);

    /**
     * Verify a player identity using the official Mojang session model when
     * available.
     *
     * <p>
     * The current Minecraft protocol does not allow a plugin to reliably prove a
     * player's
     * premium status from the server side without an authenticated session flow.
     * This service therefore
     * returns an explicit unsupported status unless a future authenticated
     * integration is added.
     * </p>
     *
     * @param username the username to verify
     * @return the verification result
     */
    public CompletableFuture<PremiumVerificationStatus> verifyPremiumIdentity(String username) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Premium verification requested for " + username
                    + " but the built-in implementation is protocol-limited and remains disabled by default.");
            return PremiumVerificationStatus.UNSUPPORTED;
        });
    }

    /**
     * Validate a premium verification outcome before migration can proceed.
     *
     * @param auth         the existing AuthMe account
     * @param verifiedUuid the verified Mojang UUID
     * @param enabled      whether premium migration is enabled
     * @param verified     whether verification succeeded
     * @return true when the account can be auto-authenticated as premium
     */
    public boolean shouldAutoAuthenticate(PlayerAuth auth, UUID verifiedUuid, boolean enabled, boolean verified) {
        return enabled
                && verified
                && auth != null
                && auth.getUuid() != null
                && auth.getUuid().equals(verifiedUuid);
    }

    /**
     * Perform the migration workflow asynchronously.
     *
     * @param auth                         the existing AuthMe account
     * @param verifiedUuid                 the verified Mojang UUID
     * @param deletePasswordAfterMigration whether the password hash should be
     *                                     removed
     * @return a future that completes once the migration workflow has finished
     */
    public CompletableFuture<Boolean> migrateAccountAsync(PlayerAuth auth, UUID verifiedUuid,
            boolean deletePasswordAfterMigration) {
        return CompletableFuture.supplyAsync(() -> {
            if (auth == null || verifiedUuid == null) {
                return false;
            }
            if (auth.getUuid() != null && !auth.getUuid().equals(verifiedUuid)) {
                logger.warning("Skipping premium migration for " + auth.getNickname()
                        + " because the stored UUID does not match the verified UUID.");
                return false;
            }
            auth.setUuid(verifiedUuid);
            if (deletePasswordAfterMigration) {
                auth.setPassword(new fr.xephi.authme.security.crypts.HashedPassword(""));
                logger.info("Password hash cleared for premium migration of " + auth.getNickname());
            }
            return true;
        }).orTimeout(5, TimeUnit.SECONDS);
    }
}
