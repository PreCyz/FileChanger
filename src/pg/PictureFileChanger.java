package pg;

import pg.picturefilechanger.AbstractFileChanger;
import pg.picturefilechanger.impl.FileChangerImpl;
import static pg.picturefilechanger.exceptions.ProgramException.ErrorCode.NO_ARGUMENTS;
import static pg.picturefilechanger.exceptions.ProgramException.ErrorCode.NULL_ARGUMENT;
import pg.picturefilechanger.exceptions.ProgramException;

/**
 *
 * @author Paweł Gawędzki
 */
public class PictureFileChanger {
    
    private static AbstractFileChanger fileChanger;

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
            System.err.println(ex.getMessage());
        }
    }

    protected static void runProgram(String[] args) throws ProgramException {
        argumentsValidation(args);
        createFileChanger(args);
        fileChanger.run();
    }

    protected static void argumentsValidation(String[] args) throws ProgramException {
        if(args == null || args.length == 0){
            throw new ProgramException(NO_ARGUMENTS, null);
        }
        
        for(String arg : args){
            if(empty(arg)){
                throw new ProgramException(NULL_ARGUMENT, arg);
            }
        }
    }

    protected static boolean empty(String value) {
        return value == null || value.length() == 0;
    }
    
    protected static void createFileChanger(String[] args) {
        fileChanger = new FileChangerImpl(args);
    }

}
