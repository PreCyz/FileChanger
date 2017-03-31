package pg.duplicat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.*;

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
    public void cleanup(){
        dfr = null;
    }

    @Test
    public void givenDuplicateFileRemoverWhenGetDuplicateRemoverThenReturnRemover() {
        assertThat(dfr.getDuplicateRemover(), notNullValue());
    }

    @Test
    public void givenNullArgumentArrayWhenValidateArgsThrowUnsupportedOperationException() {
	    try {
		    DuplicateFileRemover.validateArgs(null);
		    fail("Should throw UnsupportedOperationException.");
	    } catch(UnsupportedOperationException ex) {
		    assertThat(ex.getMessage(), equalTo("Nie podano scieżki do katalogu."));
	    }
    }

	@Test
	public void givenEmptyArgumentArrayWhenValidateArgsThrowUnsupportedOperationException() {
		try {
			DuplicateFileRemover.validateArgs(new String[]{});
			fail("Should throw UnsupportedOperationException.");
		} catch(UnsupportedOperationException ex) {
			assertThat(ex.getMessage(), equalTo("Nie podano scieżki do katalogu."));
		}
	}

	@Test
	public void givenArgumentArrayWithEmptyElementWhenValidateArgsThrowUnsupportedOperationException() {
		try {
			DuplicateFileRemover.validateArgs(new String[]{""});
			fail("Should throw UnsupportedOperationException.");
		} catch(UnsupportedOperationException ex) {
			assertThat(ex.getMessage(), equalTo("Niewłaściwy parametr."));
		}
	}

	@Test
	public void givenArgumentArrayWithNullElementWhenValidateArgsThrowUnsupportedOperationException() {
		try {
			DuplicateFileRemover.validateArgs(new String[]{null});
			fail("Should throw UnsupportedOperationException.");
		} catch(UnsupportedOperationException ex) {
			assertThat(ex.getMessage(), equalTo("Niewłaściwy parametr."));
		}
	}

	@Test
	public void givenArgumentArrayWithSpaceElementWhenValidateArgsThrowUnsupportedOperationException() {
		try {
			DuplicateFileRemover.validateArgs(new String[]{" "});
			fail("Should throw UnsupportedOperationException.");
		} catch(UnsupportedOperationException ex) {
			assertThat(ex.getMessage(), equalTo("Niewłaściwy parametr."));
		}
	}
}
