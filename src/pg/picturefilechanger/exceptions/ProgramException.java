package pg.picturefilechanger.exceptions;

/**
 *
 * @author Paweł Gawędzki
 */
public class ProgramException extends Exception{
    private static final long serialVersionUID = -6941136934880817501L;
    
    private String message;
    
    public static enum ErrorCode{
        NULL_ARGUMENT, NO_ARGUMENTS, PREPARATION
    }
    
    public ProgramException(ErrorCode errorCode, String param){
        switch(errorCode){
            case NULL_ARGUMENT :
                this.message = String.format("Parametr [%s] ma wartość null.", param);
                break;
            case NO_ARGUMENTS :
                this.message = String.format("Program uruchomiony bez wymaganych parametrów.");
                break;
            case PREPARATION :
                this.message = String.format("Błąd podczas przygotowania obiektów.");
                break;
        }
    }
    
    @Override
    public String getMessage(){
        return message;
    }

}
