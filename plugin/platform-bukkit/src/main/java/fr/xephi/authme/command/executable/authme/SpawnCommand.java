package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.command.PlayerCommand;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.SpawnLoader;
import fr.xephi.authme.util.TeleportUtils;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;

public class SpawnCommand extends PlayerCommand {

    @Inject
    private SpawnLoader spawnLoader;
    @Inject
    private BukkitService bukkitService;

    @Override
    public void runCommand(Player player, List<String> arguments) {
        if (spawnLoader.getSpawn() == null) {
            player.sendMessage("[AuthMe] Spawn has failed, please try to define the spawn");
        } else {
            bukkitService.runTaskIfFolia(player, () -> TeleportUtils.teleport(player, spawnLoader.getSpawn()));
        }
    }
}
