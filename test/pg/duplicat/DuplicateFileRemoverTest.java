package pg.duplicat;

import static org.junit.Assert.*;

import java.time.*;
import org.junit.*;
import pg.helper.TimeHelper;

/**
 *
 * @author premik
 */
public class DuplicateFileRemoverTest {
    private DuplicateFileRemover dfr;
    
    @Before
    public void setUp() {
        dfr = new DuplicateFileRemover("aa");
    }
    
    @After
    public void tearDown(){
        dfr = null;
    }

    @Test
    public void testGetHelper() {
        assertNotNull(dfr.getHelper());
    }
    
    //@Test
    public void testGetDuration() throws Exception{
        LocalTime start = LocalTime.now();
        Duration hours = Duration.ofHours(1);
        LocalTime stop = start.plus(hours);
        
        //assertEquals(1, dfr.durationInfo(start, stop));
        
        Duration minutes = Duration.ofMinutes(3);
        stop = start.plus(minutes);
        assertEquals("Czas trwania: 3[m].", TimeHelper.durationInfo(start, stop));
        minutes = Duration.ofMinutes(60);
        stop = start.plus(minutes);
        assertEquals("Czas trwania: 1[h].", TimeHelper.durationInfo(start, stop));
        
        Duration seconds = Duration.ofSeconds(4);
        stop = start.plus(seconds);
        assertEquals("Czas trwania: 4[s].", TimeHelper.durationInfo(start, stop));
        seconds = Duration.ofSeconds(60);
        stop = start.plus(seconds);
        assertEquals("Czas trwania: 1[m].", TimeHelper.durationInfo(start, stop));
    }
    
    @Test
    public void testRunArgument() throws Exception{
        String[] args;
        try{
            args = null;
            DuplicateFileRemover.validateArgs(args);
            fail();
        } catch(UnsupportedOperationException ex){
            assertEquals("Nie podano scieżki do katalogu.", ex.getMessage());
        }
        
        try{
            args = new String[]{};
            DuplicateFileRemover.validateArgs(args);
            fail();
        } catch(UnsupportedOperationException ex){
            assertEquals("Nie podano scieżki do katalogu.", ex.getMessage());
        }
        
        try{
            args = new String[]{""};
            DuplicateFileRemover.validateArgs(args);
            fail();
        } catch(UnsupportedOperationException ex){
            assertEquals("Niewłaściwy parametr.", ex.getMessage());
        }
        
        try{
            args = new String[]{null};
            DuplicateFileRemover.validateArgs(args);
            fail();
        } catch(UnsupportedOperationException ex){
            assertEquals("Niewłaściwy parametr.", ex.getMessage());
        }
        
        try{
            args = new String[]{" "};
            DuplicateFileRemover.validateArgs(args);
            fail();
        } catch(UnsupportedOperationException ex){
            assertEquals("Niewłaściwy parametr.", ex.getMessage());
        }
    }
}
