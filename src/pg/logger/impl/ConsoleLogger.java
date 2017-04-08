package pg.logger.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-02 23:16:18
 */
public class ConsoleLogger implements AppLogger {

    private static Logger logger = LogManager.getLogger(ConsoleLogger.class.getSimpleName());

    private final MessageHelper messageHelper;

    public ConsoleLogger(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }
    
    @Override
    public void log(ProgramException ex) {
        String errorMsg = messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument());
        logger.log(logger.getLevel(), errorMsg);
    }

    @Override
    public void log(String message) {
        logger.log(logger.getLevel(), message);
    }

    @Override
    public void logBundle(String bundleKey) {
        logger.log(logger.getLevel(), messageHelper.getFullMessage(bundleKey));
    }

}
