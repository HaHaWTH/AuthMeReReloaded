package fr.xephi.authme.datasource.converter;

import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.datasource.DataSourceType;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static fr.xephi.authme.util.Utils.logAndSendMessage;

/**
 * Converts from one AuthMe data source type to another.
 *
 * @param <S> the source type to convert from
 */
public abstract class AbstractDataSourceConverter<S extends DataSource> implements Converter {

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(MySqlToSqlite.class);

    private final DataSource destination;
    private final DataSourceType destinationType;
    private final Messages messages;

    /**
     * Constructor.
     *
     * @param destination the data source to convert to
     * @param destinationType the data source type of the destination. The given data source is checked that its
     *                        type corresponds to this type before the conversion is started, enabling us to just pass
     *                        the current data source and letting this class check that the types correspond.
     * @param messages message service for user-facing output
     */
    public AbstractDataSourceConverter(DataSource destination, DataSourceType destinationType, Messages messages) {
        this.destination = destination;
        this.destinationType = destinationType;
        this.messages = messages;
    }

    // Implementation note: Because of ForceFlatToSqlite it is possible that the CommandSender is null,
    // which is never the case when a converter is launched from the /authme converter command.
    @Override
    public void execute(CommandSender sender) {
        CommandSender audience = sender != null ? sender : Bukkit.getConsoleSender();
        if (destinationType != destination.getType()) {
            if (sender != null) {
                messages.send(sender, MessageKey.CONVERTER_CONFIGURE_DESTINATION, destinationType.name());
            }
            return;
        }

        S source;
        try {
            source = getSource();
        } catch (Exception e) {
            logAndSendMessage(sender,
                messages.retrieveSingle(audience, MessageKey.CONVERTER_SOURCE_INIT_FAILED));
            logger.logException("Could not initialize source:", e);
            return;
        }

        List<String> skippedPlayers = new ArrayList<>();
        for (PlayerAuth auth : source.getAllAuths()) {
            if (destination.isAuthAvailable(auth.getNickname())) {
                skippedPlayers.add(auth.getNickname());
            } else {
                destination.saveAuth(auth);
                destination.updateSession(auth);
                destination.updateQuitLoc(auth);
            }
        }

        if (!skippedPlayers.isEmpty()) {
            logAndSendMessage(sender, messages.retrieveSingle(audience, MessageKey.CONVERTER_SKIPPED_EXISTING,
                destinationType.name(), String.join(", ", skippedPlayers)));
        }
        logAndSendMessage(sender, messages.retrieveSingle(audience, MessageKey.CONVERTER_FINISHED,
            source.getType().name(), destinationType.name()));
    }

    /**
     * @return the data source to convert from
     * @throws Exception during initialization of source
     */
    protected abstract S getSource() throws Exception;
}
