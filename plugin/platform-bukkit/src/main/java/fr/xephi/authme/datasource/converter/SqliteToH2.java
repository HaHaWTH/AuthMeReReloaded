package fr.xephi.authme.datasource.converter;

import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.datasource.DataSourceType;
import fr.xephi.authme.datasource.SQLite;
import fr.xephi.authme.initialization.DataFolder;
import fr.xephi.authme.settings.Settings;

import javax.inject.Inject;
import java.io.File;
import java.sql.SQLException;

/**
 * Converts SQLite to H2
 *
 */
public class SqliteToH2 extends AbstractDataSourceConverter<SQLite>{

    private final Settings settings;
    private final File dataFolder;

    @Inject
    SqliteToH2(Settings settings, DataSource dataSource, @DataFolder File dataFolder) {
        super(dataSource, DataSourceType.H2);
        this.settings = settings;
        this.dataFolder = dataFolder;
    }

    @Override
    protected SQLite getSource() throws SQLException {
        return new SQLite(settings, dataFolder);
    }
}
