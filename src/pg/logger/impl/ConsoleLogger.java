package pg.logger.impl;

import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-02 23:16:18
 */
public class ConsoleLogger implements AppLogger {
    private final MessageHelper messageHelper;

    public ConsoleLogger(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }
    
    @Override
    public void log(ProgramException ex) {
        System.out.println(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }

}
