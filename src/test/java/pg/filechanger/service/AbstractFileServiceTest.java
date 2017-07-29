package pg.filechanger.service;

import java.util.*;

import pg.filechanger.service.AbstractFileService;
import org.junit.*;
import pg.exception.*;
import pg.helper.*;
import pg.logger.AppLogger;
import pg.logger.impl.ConsoleLogger;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 *
 * @author Gawa
 */
public class AbstractFileServiceTest {

	private AbstractFileService mockFileChanger;

	private class MockConsoleFileService extends AbstractFileService {

		MockConsoleFileService(AppLogger logger, ResourceBundle bundle) {
			super(logger, bundle);
		}

		@Override
		public void run() throws ProgramException {}

	}

	@Before
	public void setup() throws Exception {
		mockFileChanger = new MockConsoleFileService(new ConsoleLogger(), PropertiesHelper.readBundles());
	}

    @Test
    public void WhenCreateAbstractFileChangerThenMessageHelperNotNull() {
        assertThat(mockFileChanger.messageHelper, notNullValue());
    }
    //TODO: adding test to all public methods from AbstractFileService



    
}
