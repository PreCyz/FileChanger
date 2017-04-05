package pg.helper;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import pg.constant.AppConstants;
import pg.exception.*;

/**
 * @author Gawa [Paweł Gawędzki]
 * @date 2016-11-23 20:29:01
 */
public class PropertiesHelperTest {

    private static Properties backup = new Properties();
	private static URL propertiesUrl;

	@BeforeClass
	public static void backup() throws IOException {
		propertiesUrl = PropertiesHelperTest.class.getClassLoader().getResource(AppConstants.APP_CONFIG_PATH);
		try (Reader reader = new FileReader(propertiesUrl.getPath())) {
			backup.load(reader);
		}
	}

    @After
    public void cleanup() throws IOException {
	    try (Writer writer = new PrintWriter(propertiesUrl.getPath())) {
		    backup.store(writer, "");
		    writer.flush();
	    }
    }

    @Test
    public void givenPropertyFileWhenLoadProgramPropertiesThenFileLoaded() throws Exception {
        Properties properties = PropertiesHelper.loadProgramProperties(propertiesUrl.getPath());
        assertThat(properties.getProperty("core.name.lastUsed"), is( equalTo("wakacje")));
        String fileConnectors = properties.getProperty("file.connectors");
        assertThat(fileConnectors, is( equalTo("-,+,_")));
    }

	@Test
	public void givenNullFilePathWhenLoadProgramPropertiesThenFileLoaded() throws Exception {
		assertThat(PropertiesHelper.loadProgramProperties(null), nullValue());
	}

	@Test
	public void givenEmptyFilePathWhenLoadProgramPropertiesThenFileLoaded() throws Exception {
		assertThat(PropertiesHelper.loadProgramProperties(""), nullValue());
	}

	@Test
	public void givenFilePathAsSpaceWhenLoadProgramPropertiesThenThrowProgramExceptionWithLoadPropertiesErrorCode()
			throws Exception {
		try {
			PropertiesHelper.loadProgramProperties(" ");
			fail("Should be thrown ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.LOAD_PROPERTIES)));
		}
	}
    
    @Test
    public void givenBundleFileWhenReadBundleThenSuccess() throws Exception {
        ResourceBundle bundle = PropertiesHelper.readBundles();
        assertThat(bundle, notNullValue());
        assertThat(bundle.keySet().size(), greaterThan(0));
    }

    @Test
    public void givenNullPropertiesWhenSaveProgramPropertiesThenThrowProgramException() {
    	try {
    		PropertiesHelper.saveProgramProperties(null, propertiesUrl.getPath());
    		fail("Should be thrown NullPointerException.");
	    } catch (ProgramException ex) {
    		assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.SAVE_PROPERTIES)));
	    }
    }

	@Test
	public void givenNullPropertiesFilePathWhenSaveProgramPropertiesThenThrowProgramException() {
		try {
			PropertiesHelper.saveProgramProperties(new Properties(), null);
			fail("Should be thrown ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.SAVE_PROPERTIES)));
		}
	}

	@Test
	public void givenPropertiesFilePathWhenSaveProgramPropertiesThenSaveWithSuccess() throws Exception {
		Properties properties = PropertiesHelper.loadProgramProperties(propertiesUrl.getPath());
		String testKey = "test.key";
		String testValue = "test value";
		properties.setProperty(testKey, testValue);
		PropertiesHelper.saveProgramProperties(properties, propertiesUrl.getPath());
		properties = PropertiesHelper.loadProgramProperties(propertiesUrl.getPath());
		assertThat(properties.get(testKey), is( equalTo(testValue)));
		properties.remove(testKey);
		PropertiesHelper.saveProgramProperties(properties, propertiesUrl.getPath());
		properties = PropertiesHelper.loadProgramProperties(propertiesUrl.getPath());
		assertThat(properties.get(testKey), nullValue());
	}

}
