package pg.picturefilechanger;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paweł Gawędzki
 */
public class FileChangerTest {
    
    public FileChangerTest() {
    }

    @Test
    public void testCreateObject() {
        assertNotNull(new FileChanger(null));
    }
    
}
