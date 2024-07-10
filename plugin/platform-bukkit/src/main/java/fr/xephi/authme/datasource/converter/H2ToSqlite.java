package fr.xephi.authme.datasource.converter;

import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.datasource.DataSourceType;
import fr.xephi.authme.datasource.H2;
import fr.xephi.authme.initialization.DataFolder;
import fr.xephi.authme.settings.Settings;

import javax.inject.Inject;
import java.io.File;
import java.sql.SQLException;

/**
 * Converts H2 to SQLite.
 *
 */
public class H2ToSqlite extends AbstractDataSourceConverter<H2>{

    private final Settings settings;
    private final File dataFolder;

    @Inject
    H2ToSqlite(Settings settings, DataSource dataSource, @DataFolder File dataFolder) {
        super(dataSource, DataSourceType.SQLITE);
        this.settings = settings;
        this.dataFolder = dataFolder;
    }

    @Override
    protected H2 getSource() throws SQLException {
        return new H2(settings, dataFolder);
    }
}
