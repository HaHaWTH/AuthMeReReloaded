package fr.xephi.authme.logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates and keeps track of {@link ConsoleLogger} instances.
 */
public final class ConsoleLoggerFactory {

    private static final Map<String, ConsoleLogger> consoleLoggers = new ConcurrentHashMap<>();

    private ConsoleLoggerFactory() {
    }

    /**
     * Creates or returns the already existing logger associated with the given class.
     *
     * @param owningClass the class whose logger should be retrieved
     * @return logger for the given class
     */
    public static ConsoleLogger get(Class<?> owningClass) {
        String name = owningClass.getCanonicalName();
        return consoleLoggers.computeIfAbsent(name, ConsoleLoggerFactory::createLogger);
    }

    public static int getTotalLoggers() {
        return consoleLoggers.size();
    }

    private static ConsoleLogger createLogger(String name) {
        return new ConsoleLogger(name);
    }

}
