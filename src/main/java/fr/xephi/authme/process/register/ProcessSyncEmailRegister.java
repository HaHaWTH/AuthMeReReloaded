package fr.xephi.authme.process.register;

import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.data.limbo.LimboService;
import fr.xephi.authme.events.RegisterEvent;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.process.SynchronousProcess;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.service.CommonService;
import fr.xephi.authme.service.velocity.VMessageType;
import fr.xephi.authme.service.velocity.VelocitySender;
import fr.xephi.authme.util.PlayerUtils;
import org.bukkit.entity.Player;

import javax.inject.Inject;

/**
 * Performs synchronous tasks after a successful {@link RegistrationType#EMAIL email registration}.
 */
public class ProcessSyncEmailRegister implements SynchronousProcess {
    
    private final ConsoleLogger logger = ConsoleLoggerFactory.get(ProcessSyncEmailRegister.class);

    @Inject
    private BukkitService bukkitService;

    @Inject
    private CommonService service;

    @Inject
    private LimboService limboService;
    @Inject
    private VelocitySender velocitySender;

    ProcessSyncEmailRegister() {
    }

    /**
     * Performs sync tasks for a player which has just registered by email.
     *
     * @param player the recently registered player
     */
    public void processEmailRegister(Player player) {
        service.send(player, MessageKey.ACCOUNT_NOT_ACTIVATED);
        limboService.replaceTasksAfterRegistration(player);
        velocitySender.sendAuthMeVelocityMessage(player, VMessageType.REGISTER);
        bukkitService.callEvent(new RegisterEvent(player));
        logger.fine(player.getName() + " registered " + PlayerUtils.getPlayerIp(player));
    }

}
