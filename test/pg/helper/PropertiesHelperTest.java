package pg.helper;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pg.constant.AppConstants;

/**
 * @author Gawa [Paweł Gawędzki]
 * @date 2016-11-23 20:29:01
 */
public class PropertiesHelperTest {

    private PropertiesHelper helper;
    private ClassLoader classLoader;

    @Before
    public void setUp() throws Exception {
        helper = new PropertiesHelper();
        classLoader = this.getClass().getClassLoader();
    }

    @After
    public void tearDown() throws Exception {
        helper = null;
        classLoader = null;
    }

    @Test
    public void givenPropertyFileWhenLoadProgramPropertiesThenFileLoaded() throws Exception {
        URL url = classLoader.getResource(AppConstants.APP_CONFIG_PATH);
        Properties properties = helper.loadProgramProperties(url.getPath());
        assertEquals("Props should contains value.", properties.getProperty("core.name.lastUsed"), "wakacje");
        String fileConnectors = properties.getProperty("file.connectors");
        assertEquals("Props should contains value.", fileConnectors, "-,+,_");
    }
    
    @Test
    public void givenBundleFileWhenReadBundleThenSuccess() throws Exception {
        ResourceBundle bundle = PropertiesHelper.readBundles();
        assertNotNull("Bundle should be created.", bundle);
        assertTrue("Bundle should not be empty.", !bundle.keySet().isEmpty());
    }

    @Test
    public void testLoadProgramProperties() throws Exception {
    }

    @Test
    public void testSaveProgramProperties() throws Exception {
    }

    @Test
    public void testChangeProperty() {
    }

    @Test
    public void testOverwriteProperty() {
    }

    @Test
    public void testIsAnyPropertyChanged() {
    }

}