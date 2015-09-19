package pg.picturefilechanger.exceptions;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paweł Gawędzki
 */
public class ProgramExceptionTest {

    @Test
    public void testProgramException_NO_ARGUMENTS() {
        ProgramException pe = new ProgramException(ProgramException.ErrorCode.NO_ARGUMENTS, null);
        
        assertNotNull(pe);
        assertEquals("Program uruchomiony bez wymaganych parametrów.", pe.getMessage());
    }
    
    @Test
    public void testProgramException_NULL_ARGUMENT() {
        String argument = "";
        ProgramException pe = new ProgramException(ProgramException.ErrorCode.NULL_ARGUMENT, argument);
        
        assertNotNull(pe);
        assertEquals(String.format("Parametr [%s] ma wartość null.", argument), pe.getMessage());
    }
    
    @Test
    public void testProgramException_PREPARATION() {
        String argument = "";
        ProgramException pe = new ProgramException(ProgramException.ErrorCode.PREPARATION, null);
        
        assertNotNull(pe);
        assertEquals(String.format("Błąd podczas przygotowania obiektów."), pe.getMessage());
    }
    
    @Test(expected = NullPointerException.class)
    public void testProgramException_nullParam() {
        assertNotNull(new ProgramException(null, null));
    }
}
