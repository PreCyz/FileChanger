package pg.filechanger;

import java.util.ResourceBundle;
import pg.exception.ProgramException;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;
import pg.logger.AppLogger;
import pg.filechanger.core.FileChangerImpl;
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
        try {
            bundle = PropertiesHelper.readBundles();
            new FileChangerValidator(args, bundle).validate();
            new FileChangerImpl(args, bundle).run();
        } catch (ProgramException ex) {
            MessageHelper messageHelper = MessageHelper.getInstance(bundle);
            AppLogger logger = new ConsoleLogger(messageHelper);
            logger.log(ex);
            System.err.println(ex.getMessage());
        }
    }
    
}
