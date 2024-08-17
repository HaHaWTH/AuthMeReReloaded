package fr.xephi.authme.service;

import fr.xephi.authme.AuthMe;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdventureService {
    private final AuthMe plugin;
    public BukkitAudiences adventure;
    public AdventureService(AuthMe plugin) {
        this.plugin = plugin;
    }

    public void init() {
        adventure = BukkitAudiences.create(plugin);
    }

    public void close() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    public void send(CommandSender sender, Component component) {
        adventure.sender(sender).sendMessage(component);
    }

    public void send(Player player, Component component) {
        adventure.player(player).sendMessage(component);
    }

}
