package fr.xephi.authme.command.executable.authme.debug;

import fr.xephi.authme.data.limbo.LimboPlayer;
import fr.xephi.authme.data.limbo.LimboService;
import fr.xephi.authme.data.limbo.persistence.LimboPersistence;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.permission.PermissionsManager;
import fr.xephi.authme.service.BukkitService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static fr.xephi.authme.command.executable.authme.debug.DebugSectionUtils.formatLocation;

/**
 * Shows the data stored in LimboPlayers and the equivalent properties on online players.
 */
class LimboPlayerViewer implements DebugSection {

    @Inject
    private LimboService limboService;

    @Inject
    private LimboPersistence limboPersistence;

    @Inject
    private BukkitService bukkitService;

    @Inject
    private PermissionsManager permissionsManager;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "limbo";
    }

    @Override
    public String getDescription() {
        return "View LimboPlayers and player's \"limbo stats\"";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        if (arguments.isEmpty()) {
            messages.send(sender, MessageKey.DEBUG_LIMBO_TITLE);
            messages.send(sender, MessageKey.DEBUG_LIMBO_USAGE);
            String limboKeys = DebugSectionUtils.<String>applyToLimboPlayersMap(limboService,
                map -> map == null ? "" : map.keySet().toString());
            messages.send(sender, MessageKey.DEBUG_LIMBO_AVAILABLE, limboKeys != null ? limboKeys : "");
            return;
        }

        LimboPlayer memoryLimbo = limboService.getLimboPlayer(arguments.get(0));
        Player player = bukkitService.getPlayerExact(arguments.get(0));
        LimboPlayer diskLimbo = player != null ? limboPersistence.getLimboPlayer(player) : null;
        if (memoryLimbo == null && player == null) {
            messages.send(sender, MessageKey.DEBUG_LIMBO_NO_DATA_TITLE);
            messages.send(sender, MessageKey.DEBUG_LIMBO_NO_DATA_DETAIL, arguments.get(0));
            return;
        }

        messages.send(sender, MessageKey.DEBUG_LIMBO_INFO_HEADER, arguments.get(0));
        InfoDisplayer displayer = new InfoDisplayer(sender, player, memoryLimbo, diskLimbo, messages);
        displayer.sendEntry(MessageKey.DEBUG_LIMBO_FIELD_IS_OP, Player::isOp, LimboPlayer::isOperator)
            .sendEntry(MessageKey.DEBUG_LIMBO_FIELD_WALK_SPEED, Player::getWalkSpeed, LimboPlayer::getWalkSpeed)
            .sendEntry(MessageKey.DEBUG_LIMBO_FIELD_CAN_FLY, Player::getAllowFlight, LimboPlayer::isCanFly)
            .sendEntry(MessageKey.DEBUG_LIMBO_FIELD_FLY_SPEED, Player::getFlySpeed, LimboPlayer::getFlySpeed)
            .sendEntry(MessageKey.DEBUG_LIMBO_FIELD_LOCATION,
                p -> formatLocation(p.getLocation()), l -> formatLocation(l.getLocation()))
            .sendEntry(MessageKey.DEBUG_LIMBO_FIELD_PRIMARY_GROUP,
                p -> permissionsManager.hasGroupSupport() ? permissionsManager.getPrimaryGroup(p) : "N/A",
                LimboPlayer::getGroups);
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.LIMBO_PLAYER_VIEWER;
    }

    /**
     * Displays the info for the given LimboPlayer and Player to the provided CommandSender.
     */
    private static final class InfoDisplayer {
        private final CommandSender sender;
        private final Optional<Player> player;
        private final Optional<LimboPlayer> memoryLimbo;
        private final Optional<LimboPlayer> diskLimbo;
        private final Messages messages;

        /**
         * Constructor.
         *
         * @param sender command sender to send the information to
         * @param player the player to get data from
         * @param memoryLimbo the limbo player to get data from
         */
        InfoDisplayer(CommandSender sender, Player player, LimboPlayer memoryLimbo, LimboPlayer diskLimbo,
                      Messages messages) {
            this.sender = sender;
            this.player = Optional.ofNullable(player);
            this.memoryLimbo = Optional.ofNullable(memoryLimbo);
            this.diskLimbo = Optional.ofNullable(diskLimbo);
            this.messages = messages;

            if (memoryLimbo == null) {
                messages.send(sender, MessageKey.DEBUG_LIMBO_NOTE_MEMORY);
            }
            if (player == null) {
                messages.send(sender, MessageKey.DEBUG_LIMBO_NOTE_OFFLINE);
            } else if (diskLimbo == null) {
                messages.send(sender, MessageKey.DEBUG_LIMBO_NOTE_DISK);
            }
        }

        /**
         * Displays a piece of information to the command sender.
         *
         * @param titleKey the designation of the piece of information
         * @param playerGetter getter for data retrieval on Player
         * @param limboGetter getter for data retrieval on the LimboPlayer
         * @param <T> the data type
         * @return this instance (for chaining)
         */
        <T> InfoDisplayer sendEntry(MessageKey titleKey,
                                    Function<Player, T> playerGetter,
                                    Function<LimboPlayer, T> limboGetter) {
            String title = messages.retrieveSingle(sender, titleKey);
            sender.sendMessage(messages.retrieveSingle(sender, MessageKey.DEBUG_LIMBO_ROW, title,
                getData(player, playerGetter),
                getData(memoryLimbo, limboGetter),
                getData(diskLimbo, limboGetter)));
            return this;
        }

        static <E, T> String getData(Optional<E> entity, Function<E, T> getter) {
            return entity.map(getter).map(String::valueOf).orElse(" -- ");
        }
    }
}
