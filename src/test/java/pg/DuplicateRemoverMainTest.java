package pg;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.*;
import pg.remover.DuplicateRemoverMain;

/**
 *
 * @author premik
 */
public class DuplicateRemoverMainTest {

    private DuplicateRemoverMain dfr;
    
    @Before
    public void setUp() {
        dfr = new DuplicateRemoverMain("aa");
    }
    
    @After
    public void cleanup(){
        dfr = null;
    }

    @Test
    public void givenDuplicateFileRemoverWhenGetDuplicateRemoverThenReturnRemover() {
        assertThat(dfr.getDuplicateRemover(), notNullValue());
    }

}
