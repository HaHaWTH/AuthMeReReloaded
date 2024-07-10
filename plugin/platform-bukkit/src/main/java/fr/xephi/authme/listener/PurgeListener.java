package fr.xephi.authme.listener;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.SecuritySettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.io.File;
import java.util.UUID;

public class PurgeListener implements Listener {
    private final AuthMeApi authmeApi = AuthMeApi.getInstance();

    @Inject
    private Settings settings;
    @Inject
    private BukkitService bukkitService;
    @Inject
    private AuthMe plugin;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        UUID playerUUID = event.getPlayer().getUniqueId();
        if (!authmeApi.isRegistered(name)) {
            if (settings.getProperty(SecuritySettings.PURGE_DATA_ON_QUIT)) {
                bukkitService.runTaskLater(() -> {
                    if (!player.isOnline()) {
                        deletePlayerData(playerUUID);
                        deletePlayerStats(playerUUID);
                        deleteAuthMePlayerData(playerUUID);
                    }
                }, 100L);
            }
        }
    }

    private void deletePlayerData(UUID playerUUID) {
        // 获取服务器的存储文件夹路径
        File serverFolder = Bukkit.getServer().getWorldContainer();
        String worldFolderName = settings.getProperty(SecuritySettings.DELETE_PLAYER_DATA_WORLD);
        // 构建playerdata文件夹路径
        File playerDataFolder = new File(serverFolder, File.separator + worldFolderName + File.separator + "playerdata");

        // 构建玩家数据文件路径
        File playerDataFile = new File(playerDataFolder, File.separator + playerUUID + ".dat");
        File playerDataOldFile = new File(playerDataFolder, File.separator + playerUUID + ".dat_old");

        // 删除玩家数据文件
        if (playerDataFile.exists()) {
            playerDataFile.delete();
        }
        if (playerDataOldFile.exists()) {
            playerDataOldFile.delete();
        }
    }

    private void deleteAuthMePlayerData(UUID playerUUID) {
        File pluginFolder = plugin.getDataFolder();
        File path = new File(pluginFolder, File.separator + "playerdata" + File.separator + playerUUID);
        File dataFile = new File(path, File.separator + "data.json");
        if (dataFile.exists()) {
            dataFile.delete();
            path.delete();
        }
    }

    private void deletePlayerStats(UUID playerUUID) {
        // 获取服务器的存储文件夹路径
        File serverFolder = Bukkit.getServer().getWorldContainer();
        String worldFolderName = settings.getProperty(SecuritySettings.DELETE_PLAYER_DATA_WORLD);
        // 构建stats文件夹路径
        File statsFolder = new File(serverFolder, File.separator + worldFolderName + File.separator + "stats");
        // 构建玩家统计数据文件路径
        File statsFile = new File(statsFolder, File.separator + playerUUID + ".json");
        // 删除玩家统计数据文件
        if (statsFile.exists()) {
            statsFile.delete();
        }
    }
}
