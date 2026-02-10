package fr.xephi.authme.service;

import com.google.common.annotations.VisibleForTesting;
import com.maxmind.db.CHMCache;
import com.maxmind.db.Reader.FileMode;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.AbstractCountryResponse;
import com.maxmind.geoip2.record.Country;
import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.initialization.DataFolder;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import fr.xephi.authme.util.InternetProtocolUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class GeoIpService {

    //private static final String LICENSE =
            //"[LICENSE] This product includes GeoLite2 data created by MaxMind, available at https://www.maxmind.com";

    private static final String DATABASE_NAME = "GeoLite2-Country";
    private static final String DATABASE_FILE = DATABASE_NAME + ".mmdb";


    private final ConsoleLogger logger = ConsoleLoggerFactory.get(GeoIpService.class);
    private final Path dataFile;

    private DatabaseReader databaseReader;
    private volatile boolean downloading;

    @Inject
    public GeoIpService(@DataFolder File dataFolder) {
        this.dataFile = dataFolder.toPath().resolve(DATABASE_FILE);

        // Fires download of recent data or the initialization of the look-up service
        isDataAvailable();
    }

    @VisibleForTesting
    GeoIpService(@DataFolder File dataFolder, DatabaseReader reader) {
        this.dataFile = dataFolder.toPath().resolve(DATABASE_FILE);

        this.databaseReader = reader;
    }

    /**
     * Download (if absent or old) the GeoIpLite data file and then try to load it.
     *
     * @return True if the data is available, false otherwise.
     */
    private synchronized boolean isDataAvailable() {
        if (downloading) {
            // we are currently downloading the database
            return false;
        }

        if (databaseReader != null) {
            // everything is initialized
            return true;
        }

        if (Files.exists(dataFile)) {
            try {
                startReading();
                return true;
            } catch (IOException ioEx) {
                logger.logException("Failed to load GeoLiteAPI database", ioEx);
                return false;
            }
        }

        // File is outdated or doesn't exist - let's try to download the data file!
        // use bukkit's cached threads
        return false;
    }

    /**
     *
     */

    private void startReading() throws IOException {
        databaseReader = new DatabaseReader.Builder(dataFile.toFile())
            .withCache(new CHMCache())
            .fileMode(FileMode.MEMORY)
            .build();

        // clear downloading flag, because we now have working reader instance
        downloading = false;
    }

    /**
     * Downloads the archive to the destination file if it's newer than the local version.
     *
     * @param lastModified modification timestamp of the already present file
     * @param destination save file
     * @return null if no updates were found, the MD5 hash of the downloaded archive if successful
     * @throws IOException if failed during downloading and writing to destination file
     */

    /**
     * Downloads the archive to the destination file if it's newer than the local version.
     *
     * @param destination save file
     * @return null if no updates were found, the MD5 hash of the downloaded archive if successful
     * @throws IOException if failed during downloading and writing to destination file
     */

    /**
     * Verify if the expected checksum is equal to the checksum of the given file.
     *
     * @param function the checksum function like MD5, SHA256 used to generate the checksum from the file
     * @param file the file we want to calculate the checksum from
     * @param expectedChecksum the expected checksum
     * @throws IOException on I/O error reading the file or the checksum verification failed
     */

    /**
     * Extract the database from gzipped data. Existing outputFile will be replaced if it already exists.
     *
     * @param inputFile gzipped database input file
     * @param outputFile destination file for the database
     * @throws IOException on I/O error reading the archive, or writing the output
     */

    /**
     * Get the country code of the given IP address.
     *
     * @param ip textual IP address to lookup.
     * @return two-character ISO 3166-1 alpha code for the country, "LOCALHOST" for local addresses
     *         or "--" if it cannot be fetched.
     */
    public String getCountryCode(String ip) {
        if (InternetProtocolUtils.isLocalAddress(ip)) {
            return "LOCALHOST";
        }
        return getCountry(ip).map(Country::getIsoCode).orElse("--");
    }

    /**
     * Get the country name of the given IP address.
     *
     * @param ip textual IP address to lookup.
     * @return The name of the country, "LocalHost" for local addresses, or "N/A" if it cannot be fetched.
     */
    public String getCountryName(String ip) {
        if (InternetProtocolUtils.isLocalAddress(ip)) {
            return "LocalHost";
        }
        return getCountry(ip).map(Country::getName).orElse("N/A");
    }

    /**
     * Get the country of the given IP address
     *
     * @param ip textual IP address to lookup
     * @return the wrapped Country model or {@link Optional#empty()} if
     *   <ul>
     *     <li>Database reader isn't initialized</li>
     *     <li>MaxMind has no record about this IP address</li>
     *     <li>IP address is local</li>
     *     <li>Textual representation is not a valid IP address</li>
     *   </ul>
     */
    private Optional<Country> getCountry(String ip) {
        if (ip == null || ip.isEmpty() || !isDataAvailable()) {
            return Optional.empty();
        }

        try {
            InetAddress address = InetAddress.getByName(ip);

            // Reader.getCountry() can be null for unknown addresses
            return Optional.ofNullable(databaseReader.country(address)).map(AbstractCountryResponse::getCountry);
        } catch (UnknownHostException e) {
            // Ignore invalid ip addresses
            // Legacy GEO IP Database returned a unknown country object with Country-Code: '--' and Country-Name: 'N/A'
        } catch (GeoIp2Exception | IOException ioEx) {
            logger.logException("Cannot lookup country for " + ip + " at GEO IP database", ioEx);
        }

        return Optional.empty();
    }
}
