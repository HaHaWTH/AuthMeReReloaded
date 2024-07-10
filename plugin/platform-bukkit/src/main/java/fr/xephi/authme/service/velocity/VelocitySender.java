package fr.xephi.authme.service.velocity;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.xephi.authme.AuthMe;
import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.initialization.SettingsDependent;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.service.bungeecord.MessageType;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.HooksSettings;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

import javax.inject.Inject;

public class VelocitySender implements SettingsDependent {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(VelocitySender.class);
    private final AuthMe plugin;
    private final BukkitService bukkitService;

    private boolean isEnabled;

    /*
     * Constructor.
     */
    @Inject
    VelocitySender(AuthMe plugin, BukkitService bukkitService, Settings settings) {
        this.plugin = plugin;
        this.bukkitService = bukkitService;
        reload(settings);
    }

    @Override
    public void reload(Settings settings) {
        this.isEnabled = settings.getProperty(HooksSettings.VELOCITY);

        if (this.isEnabled) {
            Messenger messenger = plugin.getServer().getMessenger();
            if (!messenger.isOutgoingChannelRegistered(plugin, "authmevelocity:main")) {
                messenger.registerOutgoingPluginChannel(plugin, "authmevelocity:main");
            }
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    private void sendForwardedVelocityMessage(Player player, VMessageType type, String playerName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(type.toString());
        out.writeUTF(playerName);
        bukkitService.sendVelocityMessage(player, out.toByteArray());
    }

    /**
     * Sends a message to the AuthMe plugin messaging channel, if enabled.
     *
     * @param player     The player related to the message
     * @param type       The message type, See {@link MessageType}
     */
    public void sendAuthMeVelocityMessage(Player player, VMessageType type) {
        if (!isEnabled) {
            return;
        }
        if (!plugin.isEnabled()) {
            logger.debug("Tried to send a " + type + " velocity message but the plugin was disabled!");
            return;
        }
        sendForwardedVelocityMessage(player, type, player.getName());
    }

}
