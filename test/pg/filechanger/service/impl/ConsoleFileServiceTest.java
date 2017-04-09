package pg.filechanger.service.impl;

import java.util.*;
import org.junit.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static pg.constant.AppConstants.FILE_SEPARATOR;

import pg.filechanger.dto.ChangeDetails;
import pg.helper.PropertiesHelper;
import pg.exception.ProgramException;
import pg.logger.impl.ConsoleLogger;

/**
 * @author Gawa
 * Unit test for ConsoleFileService
 */
public class ConsoleFileServiceTest {

    private String[] args;
    private FileService changer;
    private Properties properties;

    @Before
    public void setUp() throws Exception {
        System.out.println(FILE_SEPARATOR);
        args = new String[] {
                String.format("source=d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR),
                String.format("destination=d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR),
                "extensions=jpg,jpeg,gif,mp4", "coreName=xperiaM2", "nameConnector=_"};
        changer = new FileService(args);
        properties = new Properties();
        properties.put("source",
                String.format("d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
        properties.put("destination",
                String.format("d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
        properties.put("extensions", "jpg,jpeg,gif,mp4");
        properties.put("coreName", "xperiaM2");
        properties.put("nameConnector", "_");
    }
    
    @After
    public void tearDown() {
        args = null;
        changer = null;
        properties = null;
    }

    @Test
    public void whenCreateChangeDetailsThenReturnChangeDetails() {
        String actualSrc = String.format("d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
        String actualDestination = String.format("d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
        String actualFilePrefix = "xperiaM2";
        String actualNameConnector = "_";
        ChangeDetails details = changer.createChangeDetails(args);

        assertEquals(actualSrc, details.getSourceDir());
        assertEquals(actualDestination, details.getDestinationDir());
        assertNull(details.getFileExtension());
        assertEquals(actualFilePrefix, details.getFileCoreName());
        assertEquals(actualNameConnector, details.getFileNameIndexConnector());
    }

    @Test
    public void givenNullParametersWhenCreateChangeDetailsThenThrowNullPointerException() {
        try {
            changer.createChangeDetails(null);
            fail("Should throw NullPointerException.");
        } catch(NullPointerException ex) {
            assertNotNull(ex);
        }
    }

	@Test
	public void givenArgumentsWhenCreateChangeDetailsThenReturnObjectChangeDetails() {
		assertThat(changer.createChangeDetails(args), notNullValue());
	}
    
    private class FileService extends ConsoleFileService {

        FileService(String[] params) throws Exception {
            super(params, PropertiesHelper.readBundles(), new ConsoleLogger());
        }

    }
}
