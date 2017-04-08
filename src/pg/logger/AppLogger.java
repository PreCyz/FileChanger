package pg.logger;

import pg.exception.ProgramException;

/**
 * @author Gawa [Paweł Gawędzki] 
 * 2016-12-14 20:51:21
 */
public interface AppLogger {
    
    void log(ProgramException ex);
    void log(String message);
    void logBundle(String bundleKey);

}
