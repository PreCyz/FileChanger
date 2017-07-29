package pg.helper;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import pg.constant.AppConstants;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Gawa [Paweł Gawędzki]
 * @date 2016-11-23 20:29:01
 */
public class PropertiesHelperTest {

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static Properties backup = new Properties();
	private static URL propertiesUrl;

	@BeforeClass
	public static void backup() throws IOException {
		propertiesUrl = PropertiesHelperTest.class.getClassLoader().getResource(AppConstants.APP_CONFIG);
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

	@Test(expected = NullPointerException.class)
	public void givenNullWhenTransformNullToPropertiesThenThrowNullPointerException() {
		assertNull(PropertiesHelper.transformArgumentsToProperties(null));
	}

	@Test
	public void givenStringArrayWithArgumentsWhenTransformArgumentsToPropertiesThenReturnProperties() {
		String[] args = new String[] {
				String.format("source=d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR),
				String.format("destination=d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR),
				"extensions=jpg,jpeg,gif,mp4", "coreName=xperiaM2", "nameConnector=_"};
		Properties properties = new Properties();
		properties.put("source",
				String.format("d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
		properties.put("destination",
				String.format("d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
		properties.put("extensions", "jpg,jpeg,gif,mp4");
		properties.put("coreName", "xperiaM2");
		properties.put("nameConnector", "_");
		Properties expected = PropertiesHelper.transformArgumentsToProperties(args);
		assertNotSame(expected, properties);
		assertEquals(expected, properties);
	}

}
