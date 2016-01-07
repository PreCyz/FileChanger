package pg.picturefilechanger.impl;

import java.util.Map;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pg.picturefilechanger.ChangeDetails;

/**
 *
 * @author Gawa
 */
public class FileChangerImplTest {
    private String[] args;
    private FileChanger changer;
    private Properties properties;
        
    @Before
    public void setUp() {
        args = new String[]{"source=d:\\testy\\src\\","destination=d:\\testy\\dst\\","extentions=jpg,jpeg,gif,mp4","filePrefix=xperiaM2","nameConnector=_"};
        changer = new FileChanger(args);
        properties = new Properties();
        properties.put("source", "d:\\testy\\src\\");
        properties.put("destination", "d:\\testy\\dst\\");
        properties.put("extentions", "jpg,jpeg,gif,mp4");
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
        assertNull(changer.transformArgumentsToProperties(null));
    }
    
    @Test 
    public void transformArgumentsToProperties() {
        assertEquals(changer.transformArgumentsToProperties(args), properties);
    }
    
    @Test 
    public void transformArgumentsToPropertiesNotSame() {
        assertNotSame(changer.transformArgumentsToProperties(args), properties);
    }
    
    @Test 
    public void transformArgumentsToPropertiesDifferentValue() {
        assertNotSame(changer.transformArgumentsToProperties(args), properties);
    }

    @Test
    public void testCreateMaxIndexMap() {
        Map<String, Integer> map = changer.createMaxIndexMap(properties);
        assertNotNull(map);
        assertTrue(!map.isEmpty());
    }
    
    @Test
    public void testCreateMaxIndexMap_throwsException() {
        try{
            changer.createMaxIndexMap(null);
            fail("Should be NullPointerException");
        } catch(NullPointerException ex){
            assertNotNull(ex);
        }
        
        properties = new Properties();
        try{
            changer.createMaxIndexMap(properties);
            fail("Should be NullPointerException");
        } catch(NullPointerException ex){
            assertNotNull(ex);
        }
        
        properties.put("destination", "");
        properties.put("nameConnector", "_");
        try{
            changer.createMaxIndexMap(properties);
            fail("Should be NullPointerException");
        } catch(NullPointerException ex){
            assertNotNull(ex);
        }
        
        properties.put("destination", "d:\\testy\\dst\\");
        properties.put("nameConnector", "");
        try{
            changer.createMaxIndexMap(properties);
            fail("Should be NumberFormatException");
        } catch(NumberFormatException ex){
            assertNotNull(ex);
        }
        
    }

    @Test
    public void testCreateChangeDetails() {
        String actualSrc = "d:\\testy\\src\\";
        String actualDestination = "d:\\testy\\dst\\";
        String actualFilePrefix = "xperiaM2";
        String actualNameConnector = "_";
        ChangeDetails details = changer.createChangeDetails(properties);
        
        assertEquals(actualSrc, details.getSourceDir());
        assertEquals(actualDestination, details.getDestinationDir());
        assertNull(details.getFileExtension());
        assertEquals(actualFilePrefix, details.getFileNamePrefix());
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
    
    private class FileChanger extends FileChangerImpl{

        public FileChanger(String[] params) {
            super(params);
        }

        @Override
        public Properties transformArgumentsToProperties(String[] args) {
            return super.transformArgumentsToProperties(args); //To change body of generated methods, choose Tools | Templates.
        }
    
    }
}
