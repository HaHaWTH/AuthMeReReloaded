package fr.xephi.authme.listener;
//Prevent Ghost Players

import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.service.CommonService;
import fr.xephi.authme.util.message.MiniMessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;

public class DoubleLoginFixListener implements Listener {
    @Inject
    private CommonService service;

    public DoubleLoginFixListener() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Collection<? extends Player> PlayerList = Bukkit.getServer().getOnlinePlayers();
        HashSet<String> PlayerSet = new HashSet<String>();
        for (Player ep : PlayerList) {
            if (PlayerSet.contains(ep.getName().toLowerCase())) {
                MiniMessageUtils.kickPlayer(ep, MiniMessageUtils.parseMiniMessage(service.retrieveSingleMessage(ep.getPlayer(), MessageKey.DOUBLE_LOGIN_FIX)));
                break;
            }
            PlayerSet.add(ep.getName().toLowerCase());
        }
    }
}
