package pg.helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pg.PictureFileChanger;

/**
 * @author Gawa [Paweł Gawędzki]
 * @date 2016-11-23 20:28:44
 */
public class MessageHelperTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void testPictureFileChanger_isArgumentEmpty() throws Exception {
        String arg = null;
        assertTrue(MessageHelper.empty(arg));
    }

    @Test
    public void testGetFullMessage_String() {
    }

    @Test
    public void testGetFullMessage_String_ObjectArr() {
    }

    @Test
    public void testGetErrorMsg() {
    }

}