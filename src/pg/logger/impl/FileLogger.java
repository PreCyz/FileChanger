package pg.logger.impl;

import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-14 21:07:43
 */
public class FileLogger implements AppLogger {
    
    private MessageHelper messageHelper;
    private Class whoLoggs;
    
    public FileLogger(MessageHelper messageHelper, Class whoLoggs) {
        this.messageHelper = messageHelper;
        this.whoLoggs = whoLoggs;
    }

    @Override
    public void log(ProgramException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void log(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
