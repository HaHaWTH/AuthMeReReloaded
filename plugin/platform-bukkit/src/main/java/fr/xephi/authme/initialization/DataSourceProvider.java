package fr.xephi.authme.initialization;

import com.alessiodp.libby.Library;
import fr.xephi.authme.data.auth.PlayerCache;
import fr.xephi.authme.datasource.CacheDataSource;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.datasource.DataSourceType;
import fr.xephi.authme.datasource.H2;
import fr.xephi.authme.datasource.MariaDB;
import fr.xephi.authme.datasource.MySQL;
import fr.xephi.authme.datasource.PostgreSqlDataSource;
import fr.xephi.authme.datasource.SQLite;
import fr.xephi.authme.datasource.mysqlextensions.MySqlExtensionsFactory;
import fr.xephi.authme.logger.ConsoleLogger;
import fr.xephi.authme.logger.ConsoleLoggerFactory;
import fr.xephi.authme.service.BukkitService;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.DatabaseSettings;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.File;
import java.sql.SQLException;

import static fr.xephi.authme.AuthMe.libraryManager;

/**
 * Creates the AuthMe data source.
 */
public class DataSourceProvider implements Provider<DataSource> {

    private static final int SQLITE_MAX_SIZE = 4000;

    private final ConsoleLogger logger = ConsoleLoggerFactory.get(DataSourceProvider.class);

    @Inject
    @DataFolder
    private File dataFolder;
    @Inject
    private Settings settings;
    @Inject
    private BukkitService bukkitService;
    @Inject
    private PlayerCache playerCache;
    @Inject
    private MySqlExtensionsFactory mySqlExtensionsFactory;

    DataSourceProvider() {
    }

    @Override
    public DataSource get() {
        try {
            return createDataSource();
        } catch (Exception e) {
            logger.logException("Could not create data source:", e);
            throw new IllegalStateException("Error during initialization of data source", e);
        }
    }

    /**
     * Sets up the data source.
     *
     * @return the constructed data source
     * @throws SQLException when initialization of a SQL data source failed
     */
    private DataSource createDataSource() throws SQLException {
        DataSourceType dataSourceType = settings.getProperty(DatabaseSettings.BACKEND);
        DataSource dataSource;
        switch (dataSourceType) {
            case MYSQL:
                dataSource = new MySQL(settings, mySqlExtensionsFactory);
                break;
            case MARIADB:
                dataSource = new MariaDB(settings, mySqlExtensionsFactory);
                break;
            case POSTGRESQL:
                dataSource = new PostgreSqlDataSource(settings, mySqlExtensionsFactory);
                break;
            case SQLITE:
                dataSource = new SQLite(settings, dataFolder);
                break;
            case H2:
                Library h2 = Library.builder()
                        .groupId("com.h2database")
                        .artifactId("h2")
                        .version("2.2.224")
                        .build();
                libraryManager.addMavenCentral();
                libraryManager.loadLibrary(h2);
                dataSource = new H2(settings, dataFolder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown data source type '" + dataSourceType + "'");
        }

        if (settings.getProperty(DatabaseSettings.USE_CACHING)) {
            dataSource = new CacheDataSource(dataSource, playerCache);
        }
        if (DataSourceType.SQLITE.equals(dataSourceType)) {
            checkDataSourceSize(dataSource);
        }
        return dataSource;
    }

    private void checkDataSourceSize(DataSource dataSource) {
        bukkitService.runTaskAsynchronously(() -> {
            int accounts = dataSource.getAccountsRegistered();
            if (accounts >= SQLITE_MAX_SIZE) {
                logger.warning("YOU'RE USING THE SQLITE DATABASE WITH "
                        + accounts + "+ ACCOUNTS; FOR BETTER PERFORMANCE, PLEASE UPGRADE TO MYSQL!!");
            }
        });
    }
}
