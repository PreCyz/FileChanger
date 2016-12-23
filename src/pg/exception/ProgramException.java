package pg.exception;

public class ProgramException extends Exception {

    private ErrorCode errorCode;
    private String argument;

    public ProgramException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
    
    public ProgramException(ErrorCode errorCode, String argument) {
        super();
        this.errorCode = errorCode;
        this.argument = argument;
    }

    public ProgramException(ErrorCode errorCode, Exception details, String argument) {
        super(details);
        this.errorCode = errorCode;
        this.argument = argument;
    }
    
    public ProgramException(ErrorCode errorCode, Exception details) {
        super(details);
        this.errorCode = errorCode;
    }
    
    public ErrorCode getErrorCode() {
        if (errorCode == null) {
            return ErrorCode.NULL_ERROR_CODE;
        }
        return errorCode;
    }

    public String getArgument() {
        return argument;
    }
    
}
