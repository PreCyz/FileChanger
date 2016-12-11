package pg;

import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import pg.picturefilechanger.exceptions.ProgramException;

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
    
    @Test
    public void testPictureFileChanger_isArgumentEmpty() throws Exception{
        String arg = null;
        assertTrue(PictureFileChanger.empty(arg));
    }
    
    @Test
    public void givenBundleFileWhenReadBundleThenSuccess() {
        Properties bundle = PictureFileChanger.readBundles();

        assertNotNull("Bundle should be created.", bundle);
        assertTrue("Bundle should not be empty.", !bundle.isEmpty());
    }
}
