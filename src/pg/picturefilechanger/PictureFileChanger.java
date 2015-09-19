package pg.picturefilechanger;

import pg.picturefilechanger.exceptions.ProgramException;

/**
 *
 * @author Paweł Gawędzki
 */
public class PictureFileChanger {
    
    private static FileChanger fileChanger;

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
        prepareObjects(args);
    }

    protected static void prepareObjects(String[] args) {
        fileChanger = new FileChanger(args);
    }

    protected static void argumentsValidation(String[] args) throws ProgramException {
        if(args == null || args.length == 0){
            throw new ProgramException(ProgramException.ErrorCode.NO_ARGUMENTS, null);
        }
        
        for(String arg : args){
            if(isArgumentEmpty(arg)){
                throw new ProgramException(ProgramException.ErrorCode.NULL_ARGUMENT, arg);
            }
        }
    }

    protected static boolean isArgumentEmpty(String arg) {
        return arg == null || "".equals(arg);
    }
}