package fr.xephi.authme.service.premium;

import fr.xephi.authme.data.auth.PlayerAuth;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PremiumMigrationServiceTest {

    @Test
    public void shouldAutoAuthenticateMigratedPremiumAccounts() {
        PremiumMigrationService service = new PremiumMigrationService();
        UUID premiumUuid = UUID.fromString("11111111-2222-3333-4444-555555555555");
        PlayerAuth auth = PlayerAuth.builder()
                .name("player")
                .uuid(premiumUuid)
                .build();

        assertTrue(service.shouldAutoAuthenticate(auth, premiumUuid, true, true));
        assertFalse(service.shouldAutoAuthenticate(auth, premiumUuid, false, true));
        assertFalse(service.shouldAutoAuthenticate(auth, UUID.randomUUID(), true, true));
        assertFalse(
                service.shouldAutoAuthenticate(PlayerAuth.builder().name("player").build(), premiumUuid, true, false));
    }
}
