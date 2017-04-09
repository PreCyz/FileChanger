package pg;

import java.util.ResourceBundle;
import pg.exception.ProgramException;
import pg.filechanger.service.impl.ConsoleFileService;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;
import pg.logger.AppLogger;

/**
 *
 * @author Paweł Gawędzki
 */
public class ConsoleFileChangerMain {
    
    private static ResourceBundle bundle;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runProgramWithExceptionHandling(args);
    }

    private static void runProgramWithExceptionHandling(String[] args) {
        AppLogger logger = new ConsoleLogger();
        MessageHelper messageHelper = null;
        try {
	        bundle = PropertiesHelper.readBundles();
	        messageHelper = MessageHelper.getInstance(bundle);
	        logger.log(messageHelper.getFullMessage("bundle.loaded"));
        } catch (ProgramException ex) {
	        System.err.println(ex.getMessage());
	        System.exit(-1);
        }
	    ConsoleFileService fileChanger = new ConsoleFileService(args, bundle, logger);
	    try {
	        fileChanger.run();
        } catch (ProgramException ex) {
            logger.log(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
            System.err.println(ex.getMessage());
        }
    }
    
}
