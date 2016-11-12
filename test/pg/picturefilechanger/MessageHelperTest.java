package pg.picturefilechanger;

import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pg.PictureFileChanger;

/**
 * @author Gawa [Paweł Gawędzki]
 * @date 2016-11-12 22:08:41
 */
public class MessageHelperTest {

    private MessageHelper helper;

    @Before
    public void setUp() {
        helper = new MessageHelper(PictureFileChanger.readBundles());
    }

    @After
    public void tearDown() {
        helper = null;
    }

    @Test
    public void givenLoadedBundleWhenMsgThenReturnMessage() {
        Object[] params = new Object[]{"A", "B"}; 
        String expected = "Zmieniono nazwe pliku A na B.";
        
        String actual = helper.msg("file.name.changed", params);
        
        assertEquals("Message should be [Zmieniono nazwe pliku A na B.].", expected, actual);
    }
    
    @Test
    public void givenLoadedBundleWhenMsgWithoutParamsThenReturnMessage() {
        String expected = "Bundle loaded with success.";
        
        String actual = helper.msg("bundle.loaded");

        assertEquals("Message should be 'Bundle loaded'.", expected, actual);
    }
    
    @Test
    public void givenLoadedBundleWhenMsgForUknownBundleThenUknownMsg() {
        String actual = helper.msg("bundle");
        String expected = "??? bundle ???";

        assertEquals("Message should be '??? bundle ???'.", expected, actual);
    }
    
    @Test(expected = NullPointerException.class)
    public void givenLoadedBundleWhenMsgForNullKeyThen() {
        helper.msg(null);
    }

}