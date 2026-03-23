package fr.xephi.authme.command.executable.authme.debug;

import ch.jalu.injector.factory.SingletonStore;
import fr.xephi.authme.data.auth.PlayerCache;
import fr.xephi.authme.data.limbo.LimboService;
import fr.xephi.authme.datasource.CacheDataSource;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.initialization.HasCleanup;
import fr.xephi.authme.initialization.Reloadable;
import fr.xephi.authme.initialization.SettingsDependent;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.List;

/**
 * Fetches various statistics, particularly regarding in-memory data that is stored.
 */
class DataStatistics implements DebugSection {

    @Inject
    private PlayerCache playerCache;

    @Inject
    private LimboService limboService;

    @Inject
    private DataSource dataSource;

    @Inject
    private SingletonStore<Object> singletonStore;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Outputs general data statistics";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.DEBUG_STATS_TITLE);
        Integer limboCount = DebugSectionUtils.<Integer>applyToLimboPlayersMap(limboService,
            map -> map == null ? 0 : map.size());
        messages.send(sender, MessageKey.DEBUG_STATS_LIMBO, String.valueOf(limboCount != null ? limboCount : 0));
        messages.send(sender, MessageKey.DEBUG_STATS_CACHE, String.valueOf(playerCache.getLogged()));

        outputDatabaseStats(sender);
        outputInjectorStats(sender);
        messages.send(sender, MessageKey.DEBUG_STATS_LOGGERS,
            String.valueOf(ConsoleLoggerFactory.getTotalLoggers()));
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.DATA_STATISTICS;
    }

    private void outputDatabaseStats(CommandSender sender) {
        messages.send(sender, MessageKey.DEBUG_STATS_DB_TOTAL, String.valueOf(dataSource.getAccountsRegistered()));
        if (dataSource instanceof CacheDataSource) {
            CacheDataSource cacheDataSource = (CacheDataSource) this.dataSource;
            messages.send(sender, MessageKey.DEBUG_STATS_CACHE_OBJS,
                String.valueOf(cacheDataSource.getCachedAuths().size()));
        }
    }

    private void outputInjectorStats(CommandSender sender) {
        messages.send(sender, MessageKey.DEBUG_STATS_SINGLETONS,
            String.valueOf(singletonStore.retrieveAllOfType().size()));
        messages.send(sender, MessageKey.DEBUG_STATS_INJECTOR_BREAKDOWN,
            String.valueOf(singletonStore.retrieveAllOfType(Reloadable.class).size()),
            String.valueOf(singletonStore.retrieveAllOfType(SettingsDependent.class).size()),
            String.valueOf(singletonStore.retrieveAllOfType(HasCleanup.class).size()));
    }
}
