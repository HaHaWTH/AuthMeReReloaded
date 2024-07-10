package fr.xephi.authme.command.executable.authme;

import fr.xephi.authme.command.PlayerCommand;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.SpawnLoader;
import fr.xephi.authme.util.TeleportUtils;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;

/**
 * Teleports the player to the first spawn.
 */
public class FirstSpawnCommand extends PlayerCommand {
    @Inject
    private Settings settings;
    @Inject
    private SpawnLoader spawnLoader;
    @Inject
    private BukkitService bukkitService;
    @Override
    public void runCommand(Player player, List<String> arguments) {
        if (spawnLoader.getFirstSpawn() == null) {
            player.sendMessage("[AuthMe] First spawn has failed, please try to define the first spawn");
        } else {
            //String name= player.getName();
            bukkitService.runTaskIfFolia(player, () -> {
                TeleportUtils.teleport(player, spawnLoader.getFirstSpawn());
            });
            //player.teleport(spawnLoader.getFirstSpawn());
        }
    }
}
