package fr.xephi.authme.initialization;

import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import fr.xephi.authme.AuthMe;
import fr.xephi.authme.datasource.DataSource;

/**
 * Waits for asynchronous tasks to complete before closing the data source
 * so the plugin can shut down properly.
 */
public class TaskCloser implements Runnable {

    private final TaskScheduler scheduler;
    private final DataSource dataSource;

    /**
     * Constructor.
     *
     * @param dataSource the data source (nullable)
     */
    public TaskCloser(DataSource dataSource) {
        this.scheduler = AuthMe.getScheduler();
        this.dataSource = dataSource;
    }

    @Override
    public void run() {
        scheduler.cancelTasks();
        if (dataSource != null) {
            dataSource.closeConnection();
        }
    }
}
