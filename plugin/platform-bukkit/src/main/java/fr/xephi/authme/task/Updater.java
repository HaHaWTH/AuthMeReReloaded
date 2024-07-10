package fr.xephi.authme.task;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class Updater {
    private final String currentVersion;
    private String latestVersion;
    private static boolean isUpdateAvailable = false;
    private static final String owner = "HaHaWTH";
    private static final String repo = "AuthMeReReloaded";
    private static final String UPDATE_URL = "https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest";

    public Updater(String currentVersion) {
        this.currentVersion = currentVersion;
    }


    /**
     * Check if there is an update available
     * Note: This method will perform a network request!
     *
     * @return true if there is an update available, false otherwise
     */
    public boolean isUpdateAvailable() {
        URI uri = URI.create(UPDATE_URL);
        try {
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\Z").next();
            scanner.close();
            String latestVersion = response.substring(response.indexOf("tag_name") + 11);
            latestVersion = latestVersion.substring(0, latestVersion.indexOf("\""));
            this.latestVersion = latestVersion;
            isUpdateAvailable = !currentVersion.equals(latestVersion);
            return isUpdateAvailable;
        } catch (IOException ignored) {
            this.latestVersion = null;
            isUpdateAvailable = false;
            return false;
        }
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Returns true if there is an update available, false otherwise
     * Must be called after {@link Updater#isUpdateAvailable()}
     *
     * @return A boolean indicating whether there is an update available
     */
    public static boolean hasUpdate() {
        return isUpdateAvailable;
    }
}
