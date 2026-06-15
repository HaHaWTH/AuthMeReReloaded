package fr.xephi.authme.task;

import com.github.Anon8281.universalScheduler.UniversalRunnable;
import fr.xephi.authme.data.auth.PlayerCache;
import fr.xephi.authme.process.login.ForceLoginRequestService;
import org.bukkit.entity.Player;

/**
 * Message shown to a player in a regular interval as long as he is not logged in.
 */
public class MessageTask extends UniversalRunnable {

    private final Player player;
    private final String[] message;
    private final PlayerCache playerCache;
    private final ForceLoginRequestService forceLoginRequestService;
    private boolean isMuted;

    /*
     * Constructor.
     */
    public MessageTask(Player player, String[] lines, PlayerCache playerCache,
                       ForceLoginRequestService forceLoginRequestService) {
        this.player = player;
        this.message = lines;
        this.playerCache = playerCache;
        this.forceLoginRequestService = forceLoginRequestService;
        isMuted = false;
    }

    public void setMuted(boolean isMuted) {
        this.isMuted = isMuted;
    }

    @Override
    public void run() {
        if (!player.isOnline()
            || isMuted
            || playerCache.isAuthenticated(player.getName())
            || forceLoginRequestService.isPending(player.getName())) {
            return;
        }
        player.sendMessage(message);
    }
}
