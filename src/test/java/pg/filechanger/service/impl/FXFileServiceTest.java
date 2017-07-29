package pg.filechanger.service.impl;

import org.junit.Test;
import pg.helper.PropertiesHelper;
import pg.logger.impl.ConsoleLogger;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by gawa on 09.04.17.
 */
public class FXFileServiceTest {

	private FXFileService changer;

	@Test
	public void givenEmptyChangeDetailsWhenCreateMaxIndexMapThenThrowNullPointerException() {
		try {
			changer = new FileService();
			changer.createMaxIndexMap();
			fail("Should throw NullPointerException.");
		} catch (Exception ex) {
			assertThat(ex, instanceOf(NullPointerException.class));
		}
	}

	@Test
	public void getMaxExtIdxMap() throws Exception {
	}

	@Test
	public void addSourceDir() throws Exception {
	}

	@Test
	public void addDestinationDir() throws Exception {
	}

	@Test
	public void addFileCoreName() throws Exception {
	}

	@Test
	public void addFileNameIndexConnector() throws Exception {
	}

	@Test
	public void addFileExtension() throws Exception {
	}

	@Test
	public void createMaxIndexMap() throws Exception {
	}

	@Test
	public void run() throws Exception {
	}

	@Test
	public void isUpdateIndexesPossible() throws Exception {
	}

	@Test
	public void buildMaxIndexesLabelText() throws Exception {
	}

	@Test
	public void isChnagePossible() throws Exception {
	}

	@Test
	public void updateAppConfiguration() throws Exception {
	}

	private class FileService extends FXFileService {

		FileService() throws Exception {
			super(PropertiesHelper.readBundles(), new ConsoleLogger());
		}

	}

}