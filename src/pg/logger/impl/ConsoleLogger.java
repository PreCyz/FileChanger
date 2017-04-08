package pg.logger.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pg.logger.AppLogger;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-02 23:16:18
 */
public class ConsoleLogger implements AppLogger {

    private static Logger logger = LogManager.getLogger(ConsoleLogger.class.getSimpleName());

    @Override
    public void log(String message) {
        logger.log(logger.getLevel(), message);
    }

}
