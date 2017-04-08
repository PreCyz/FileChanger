package pg.filechanger;

import java.util.ResourceBundle;
import pg.exception.ProgramException;
import pg.filechanger.core.*;
import pg.filechanger.validator.ArgsValidator;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;
import pg.logger.AppLogger;
import pg.filechanger.validator.impl.FileChangerValidator;

/**
 *
 * @author Paweł Gawędzki
 */
public class FileChangerMain {
    
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
	    ArgsValidator argsValidator;
	    AbstractFileChanger fileChanger;
        try {
	        bundle = PropertiesHelper.readBundles();
	        messageHelper = MessageHelper.getInstance(bundle);
	        logger.log(messageHelper.getFullMessage("bundle.loaded"));
        } catch (ProgramException ex) {
	        System.err.println(ex.getMessage());
	        System.exit(-1);
        }
	    argsValidator = new FileChangerValidator(args, bundle);
        try {
	        argsValidator.validate();
	        fileChanger = new FileChangerImpl(args, bundle, logger);
	        fileChanger.run();
        } catch (ProgramException ex) {
            logger.log(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
            System.err.println(ex.getMessage());
        }
    }
    
}
