package fr.xephi.authme.task;

import com.github.Anon8281.universalScheduler.UniversalRunnable;
import org.bukkit.entity.Player;

/**
 * Message shown to a player in a regular interval as long as he is not logged in.
 */
public class MessageTask extends UniversalRunnable {

    private final Player player;
    private final String[] message;
    private boolean isMuted;

    /*
     * Constructor.
     */
    public MessageTask(Player player, String[] lines) {
        this.player = player;
        this.message = lines;
        isMuted = false;
    }

    public void setMuted(boolean isMuted) {
        this.isMuted = isMuted;
    }

    @Override
    public void run() {
        if (!isMuted) {
            player.sendMessage(message);
        }
    }
}
