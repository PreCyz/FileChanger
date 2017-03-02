package pg;

import java.util.ResourceBundle;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;
import pg.logger.AppLogger;
import pg.picturefilechanger.impl.FileChangerImpl;
import pg.picturefilechanger.validator.Validator;
import pg.picturefilechanger.validator.impl.ArgsValidator;

/**
 *
 * @author Paweł Gawędzki
 */
public class PictureFileChanger {
    
    private static ResourceBundle bundle;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runProgramWithExceptionHandling(args);
    }

    public static void runProgramWithExceptionHandling(String[] args) {
        try {
            runProgram(args);
        } catch (ProgramException ex) {
            MessageHelper messageHelper = MessageHelper.getInstance(bundle);
            AppLogger logger = new ConsoleLogger(messageHelper);
            logger.log(ex);
            System.err.println(ex.getMessage());
        }
    }

    protected static void runProgram(String[] args) throws ProgramException {
        bundle = PropertiesHelper.readBundles();
        new ArgsValidator(args, bundle).validate();
        new FileChangerImpl(args, bundle).run();
    }
    
}
