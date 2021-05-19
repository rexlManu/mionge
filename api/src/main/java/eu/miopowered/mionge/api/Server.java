package eu.miopowered.mionge.api;

import org.apache.logging.log4j.Logger;

/**
 * The server interface that contains every method
 */
public interface Server {

    void startup();

    void shutdown();

    /**
     * Gives the current logger instance
     *
     * @return {@link Logger}
     */
    Logger logger();

}
