package fr.xephi.authme.listener;

import com.github.puregero.multilib.MultiLib;
import fr.xephi.authme.AuthMe;
import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.data.auth.PlayerCache;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.initialization.SettingsDependent;
import fr.xephi.authme.service.ValidationService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.RegistrationSettings;
import fr.xephi.authme.settings.properties.SecuritySettings;
import fr.xephi.authme.util.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

import javax.inject.Inject;

/**
 * Service class for the AuthMe listeners to determine whether an event should be canceled.
 */
class ListenerService implements SettingsDependent {
    private final AuthMeApi authmeApi = AuthMeApi.getInstance();
    private final DataSource dataSource;
    private final PlayerCache playerCache;
    private final ValidationService validationService;
    private boolean isRegistrationForced;


    @Inject
    ListenerService(Settings settings, DataSource dataSource, PlayerCache playerCache,
                    ValidationService validationService) {
        this.dataSource = dataSource;
        this.playerCache = playerCache;
        this.validationService = validationService;
        reload(settings);
    }

    /**
     * Returns whether an event should be canceled (for unauthenticated, non-NPC players).
     *
     * @param event the event to process
     * @return true if the event should be canceled, false otherwise
     */
    public boolean shouldCancelEvent(EntityEvent event) {
        Entity entity = event.getEntity();
        return shouldCancelEvent(entity);
    }

    /**
     * Returns, based on the entity associated with the event, whether or not the event should be canceled.
     *
     * @param entity the player entity to verify
     * @return true if the associated event should be canceled, false otherwise
     */
    public boolean shouldCancelEvent(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            return shouldCancelEvent(player);
        }
        return false;
    }

    /**
     * Returns whether an event should be canceled (for unauthenticated, non-NPC players).
     *
     * @param event the event to process
     * @return true if the event should be canceled, false otherwise
     */
    public boolean shouldCancelEvent(PlayerEvent event) {
        Player player = event.getPlayer();
        return shouldCancelEvent(player);
    }

    /**
     * Returns, based on the player associated with the event, whether or not the event should be canceled.
     *
     * @param player the player to verify
     * @return true if the associated event should be canceled, false otherwise
     */

    public boolean shouldCancelEvent(Player player) {
        if (MultiLib.isMultiPaper()) {
            if (MultiLib.isExternalPlayer(player)) {
                return false;
            }
        }
        return player != null && !checkAuth(player.getName()) && !PlayerUtils.isNpc(player);
    }
    public boolean shouldCancelInvEvent(Player player) {
        try {
            return !AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA) || authmeApi.isRegistered(player.getName()) || GuiCaptchaHandler.closeReasonMap.containsKey(player)/* || !player.getOpenInventory().getTitle().equals("请验证你是真人")*/;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void reload(Settings settings) {
        isRegistrationForced = settings.getProperty(RegistrationSettings.FORCE);
    }

    /**
     * Checks whether the player is allowed to perform actions (i.e. whether he is logged in
     * or if other settings permit playing).
     *
     * @param name the name of the player to verify
     * @return true if the player may play, false otherwise
     */
    private boolean checkAuth(String name) {
        if (validationService.isUnrestricted(name) || playerCache.isAuthenticated(name)){
            return true;
        }
        if (!isRegistrationForced && !dataSource.isAuthAvailable(name)) {
            return true;
        }
        return false;
    }
}
