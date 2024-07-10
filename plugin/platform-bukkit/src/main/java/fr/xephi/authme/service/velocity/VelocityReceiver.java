package fr.xephi.authme.service.velocity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.xephi.authme.AuthMe;
import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.data.ProxySessionManager;
import fr.xephi.authme.initialization.SettingsDependent;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.process.Management;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.HooksSettings;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import javax.inject.Inject;

public class VelocityReceiver implements PluginMessageListener, SettingsDependent {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(VelocityReceiver.class);

    private final AuthMe plugin;
    private final BukkitService bukkitService;
    private final ProxySessionManager proxySessionManager;
    private final Management management;

    private boolean isEnabled;

    @Inject
    VelocityReceiver(AuthMe plugin, BukkitService bukkitService, ProxySessionManager proxySessionManager,
                   Management management, Settings settings) {
        this.plugin = plugin;
        this.bukkitService = bukkitService;
        this.proxySessionManager = proxySessionManager;
        this.management = management;
        reload(settings);
    }

    @Override
    public void reload(Settings settings) {
        this.isEnabled = settings.getProperty(HooksSettings.VELOCITY);
        if (this.isEnabled) {
            final Messenger messenger = plugin.getServer().getMessenger();
            if (!messenger.isIncomingChannelRegistered(plugin, "authmevelocity:main")) {
                messenger.registerIncomingPluginChannel(plugin, "authmevelocity:main", this);
            }
        }
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!isEnabled) {
            return;
        }

        if (channel.equals("authmevelocity:main")) {
            final ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

            final String data = in.readUTF();
            final String username = in.readUTF();
            processData(username, data);
            logger.debug("PluginMessage | AuthMeVelocity identifier processed");
        }
    }

    private void processData(String username, String data) {
        if (VMessageType.LOGIN.toString().equals(data)) {
            performLogin(username);
        }
    }

    private void performLogin(String name) {
        Player player = bukkitService.getPlayerExact(name);
        if (player != null && player.isOnline()) {
            management.forceLogin(player, true);
            logger.info("The user " + player.getName() + " has been automatically logged in, "
                + "as requested via plugin messaging.");
        } else {
            proxySessionManager.processProxySessionMessage(name);
            logger.info("The user " + name + " should be automatically logged in, "
                + "as requested via plugin messaging but has not been detected, nickname has been"
                + " added to autologin queue.");
        }
    }

}
