package pg.filechanger.exceptions;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;

/**
 * @author Paweł Gawędzki
 */
public class ProgramExceptionTest {

    private MessageHelper msgHelper;

    @Before
    public void setUp() throws Exception {
        msgHelper = MessageHelper.getInstance(PropertiesHelper.readBundles());
    }

    @Test
    public void testProgramException_NO_ARGUMENTS() throws Exception {
        ProgramException pe = new ProgramException(ErrorCode.NO_ARGUMENTS);

        assertNotNull(pe);
        assertEquals("Program został uruchomiony bez wymaganych parametrów.", msgHelper.getErrorMsg(pe.getErrorCode()));
    }

    @Test
    public void testProgramException_NULL_ARGUMENT() throws Exception {
        String argument = "";
        ProgramException pe = new ProgramException(ErrorCode.NULL_ARGUMENT, argument);

        assertNotNull(pe);
        assertEquals(String.format("Parametr [%s] ma wartość null.", argument),
                msgHelper.getErrorMsg(pe.getErrorCode(), argument));
    }

    @Test
    public void testProgramException_PREPARATION() throws Exception {
        String argument = "";
        ProgramException pe = new ProgramException(ErrorCode.PREPARATION, argument);

        assertNotNull(pe);
        assertEquals(String.format("Błąd podczas przygotowania obiektów."), msgHelper.getErrorMsg(pe.getErrorCode()));
    }

    @Test
    public void testProgramException_NULL_ERROR_CODE() {
        ProgramException programException = new ProgramException(null);
        assertNotNull(programException);
        assertEquals("Error code should be NULL_ERROR_CODE.",
                programException.getErrorCode(), ErrorCode.NULL_ERROR_CODE);
        assertEquals("Message should be taken for error code [NULL_ERROR_CODE]",
                "Kod błędu nie może być równy null.", msgHelper.getErrorMsg(programException.getErrorCode()));
    }
}
