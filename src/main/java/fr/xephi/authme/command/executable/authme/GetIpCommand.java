package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.command.ExecutableCommand;
import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.service.CommonService;
import fr.xephi.authme.util.PlayerUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;

public class GetIpCommand implements ExecutableCommand {

    @Inject
    private BukkitService bukkitService;

    @Inject
    private DataSource dataSource;

    @Inject
    private CommonService commonService;

    @Override
    public void executeCommand(CommandSender sender, List<String> arguments) {
        String playerName = arguments.get(0);
        Player player = bukkitService.getPlayerExact(playerName);
        PlayerAuth auth = dataSource.getAuth(playerName);

        if (player != null) {
            commonService.send(sender, MessageKey.ADMIN_GET_IP_CURRENT, player.getName(),
                PlayerUtils.getPlayerIp(player), String.valueOf(player.getAddress().getPort()));
        }

        if (auth == null) {
            String displayName = player == null ? playerName : player.getName();
            commonService.send(sender, MessageKey.ADMIN_GET_IP_NOT_REGISTERED, displayName);
        } else {
            commonService.send(sender, MessageKey.ADMIN_GET_IP_DATABASE,
                String.valueOf(auth.getLastIp()), String.valueOf(auth.getRegistrationIp()));
        }
    }
}
