package pg.picturefilechanger.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import pg.exception.ProgramException;
import pg.helper.PropertiesHelper;

import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pg.picturefilechanger.AbstractFileChanger;
import pg.picturefilechanger.ChangeDetails;

/**
 * @author Gawa
 * Unit test for FileChangerImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class FileChangerImplTest {

    @Mock private FileChangerImpl mockChanger;

    private String[] args;
    private FileChanger changer;
    private Properties properties;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
        
    @Before
    public void setUp() throws Exception {
        System.out.println(FILE_SEPARATOR);
        args = new String[] {
                String.format("source=d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR),
                String.format("destination=d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR),
                "extensions=jpg,jpeg,gif,mp4", "filePrefix=xperiaM2", "nameConnector=_"};
        changer = new FileChanger(args);
        properties = new Properties();
        properties.put("source",
                String.format("d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
        properties.put("destination",
                String.format("d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
        properties.put("extensions", "jpg,jpeg,gif,mp4");
        properties.put("filePrefix", "xperiaM2");
        properties.put("nameConnector", "_");
    }
    
    @After
    public void tearDown() {
        args = null;
        changer = null;
        properties = null;
    }
    
    @Test(expected = NullPointerException.class) 
    public void transformNullToProperties() {
        assertNull(AbstractFileChanger.transformArgumentsToProperties(null));
    }
    
    @Test 
    public void transformArgumentsToProperties() {
        assertEquals(AbstractFileChanger.transformArgumentsToProperties(args), properties);
    }
    
    @Test 
    public void transformArgumentsToPropertiesNotSame() {
        assertNotSame(AbstractFileChanger.transformArgumentsToProperties(args), properties);
    }
    
    @Test 
    public void transformArgumentsToPropertiesDifferentValue() {
        assertNotSame(AbstractFileChanger.transformArgumentsToProperties(args), properties);
    }

    @Test
    public void testCreateMaxIndexMap() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("someKey", 1);
        when(mockChanger.createMaxIndexMap()).thenReturn(expected);
        Map<String, Integer> actual = mockChanger.createMaxIndexMap();
        assertNotNull(actual);
        assertTrue(!actual.isEmpty());
    }
    
    @Test
    public void givenNullParamsOrChangeDetailsWhenCreateMaxIndexMapThenThrowNullPointerException() {
        try {
            String[] params = null;
            changer = new FileChanger(params);
            changer.createMaxIndexMap();
            fail("Should throw NullPointerException.");
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }

        try {
            ChangeDetails changeDetails = null;
            changer = new FileChanger(changeDetails);
            changer.createMaxIndexMap();
            fail("Should throw NullPointerException.");
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }
    
    @Test(expected = ProgramException.class)
    public void givenNotExistingDestinationWhenCreateChangeDetailsThenReturnZeros() throws ProgramException {
        ChangeDetails changeDetails = changer.createChangeDetails(args);
        changeDetails.setDestinationDir(
                String.format("d:%stesty%snotExists%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR));
        changer.createDestinationIfNotExists();

        Map<String, Integer> indexMap = changer.createMaxIndexMap();

        assertEquals("Map should not be empty.", 4, indexMap.size());
        indexMap.entrySet().stream().forEach((entry) ->
            assertEquals("Index of "+entry.getKey()+" should be eq 1.", Integer.valueOf(1), entry.getValue()));
    }

    @Test
    public void testCreateChangeDetails() {
        String actualSrc = String.format("d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
        String actualDestination = String.format("d:%stesty%sdst%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
        String actualFilePrefix = "xperiaM2";
        String actualNameConnector = "_";
        ChangeDetails details = changer.createChangeDetails(args);
        
        assertEquals(actualSrc, details.getSourceDir());
        assertEquals(actualDestination, details.getDestinationDir());
        assertNull(details.getFileExtension());
        assertEquals(actualFilePrefix, details.getCoreName());
        assertEquals(actualNameConnector, details.getFileNameIndexConnector());
    }
    
    @Test
    public void testCreateChangeDetails_throwsException() {
        try{
            changer.createChangeDetails(null);
            fail("Should throw NullPointerException.");
        } catch(NullPointerException ex){
            assertNotNull(ex);
        }
    }
    
    @Test(expected = ProgramException.class)
    public void givenNoDestinationWhenCreateDestinationIfNotExistsThenCreateDestinationDirectory()
            throws Exception {
        String src = String.format("d:%stesty%ssrc%s", FILE_SEPARATOR, FILE_SEPARATOR, FILE_SEPARATOR);
        String dest = String.format("%s", String.format("d:%snotExists%s", FILE_SEPARATOR, FILE_SEPARATOR));
        String coreName = "xperiaM2";
        String nameConnector = "_";
        ChangeDetails changeDetails = new ChangeDetails(src, dest, coreName, nameConnector);
        changer = new FileChanger(changeDetails);
        changer.createDestinationIfNotExists();
        File destination = new File(changeDetails.getDestinationDir());
        assertTrue("Destination should exists.", destination.exists());
        destination.deleteOnExit();
    }
    
    private class FileChanger extends FileChangerImpl {

        FileChanger(String[] params) throws Exception {
            super(params, PropertiesHelper.readBundles());
        }

        FileChanger(ChangeDetails changeDetails) throws Exception {
            super(changeDetails, PropertiesHelper.readBundles());
        }
    
    }
}
