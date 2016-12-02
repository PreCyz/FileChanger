package pg.exception;

public class ProgramException extends Exception {

    private ErrorCode errorCode;
    private String argument;
    private boolean nullErrorCode;

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
    
    private ProgramException(ErrorCode errorCode, boolean nullErrorCode) {
        this(errorCode);
        this.nullErrorCode = nullErrorCode;
    }

    public ErrorCode getErrorCode() throws ProgramException {
        if (errorCode == null) {
            nullErrorCode = true;
            throw new ProgramException(ErrorCode.NULL_ERROR_CODE, true);
        }
        return errorCode;
    }

    public boolean isNullErrorCode() {
        return nullErrorCode;
    }
    
    public String getArgument() {
        return argument;
    }
    
}
