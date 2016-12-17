package pg;

import java.util.Properties;
import java.util.ResourceBundle;
import org.junit.Test;
import static org.junit.Assert.*;
import pg.exception.ProgramException;
import pg.helper.PropertiesHelper;

/**
 *
 * @author premik
 */
public class PictureFileChangerTest {
    
    @Test
    public void testPictureFileChanger() throws Exception{
        assertNotNull(new PictureFileChanger());
    }
    
    @Test(expected = ProgramException.class)
    public void testPictureFileChanger_validateNullArguments() throws Exception{
        PictureFileChanger.argumentsValidation(null);
        assertTrue(false);
    }
    
    @Test
    public void testPictureFileChanger_isArgumentsValid() throws Exception{
        String[] args = new String[]{"1","2"};
        PictureFileChanger.argumentsValidation(args);
        assertTrue(true);
    }
    
    @Test(expected = ProgramException.class)
    public void testPictureFileChanger_isArgumentsEmpty() throws Exception{
        String[] args = new String[]{"","1"};
        PictureFileChanger.argumentsValidation(args);
        assertTrue(false);
    }
}
