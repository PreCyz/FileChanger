package pg;

import java.util.ResourceBundle;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;
import pg.logger.AppLogger;
import pg.picturefilechanger.AbstractFileChanger;
import pg.picturefilechanger.impl.FileChangerImpl;

/**
 *
 * @author Paweł Gawędzki
 */
public class PictureFileChanger {
    
    private static AbstractFileChanger fileChanger;
    private static ResourceBundle bundle;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runProgrmaWithExceptionHandling(args);
    }

    public static void runProgrmaWithExceptionHandling(String[] args) {
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
        argumentsValidation(args);
        bundle = PropertiesHelper.readBundles();
        createFileChanger(args, bundle);
        fileChanger.run();
    }

    protected static void argumentsValidation(String[] args) throws ProgramException {
        if(args == null || args.length == 0){
            throw new ProgramException(ErrorCode.NO_ARGUMENTS);
        }
        
        for(String arg : args){
            if(MessageHelper.empty(arg)){
                throw new ProgramException(ErrorCode.NULL_ARGUMENT, arg);
            }
        }
    }
    
    protected static void createFileChanger(String[] args, ResourceBundle bundle) {
        fileChanger = new FileChangerImpl(args, bundle);
    }

}
