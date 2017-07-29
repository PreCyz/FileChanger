package pg.remover;


import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;

/**
 *
 * @author premik
 */
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DuplicateRemoverTest {

	private static final int NUMBER_OF_TEST_FILES = 5;
	private static final String ONE_JPG = "one.jpg";
	private static final String TWO_JPG = "two.jpg";
	private static final String TEST_FILES = DuplicateRemoverTest.class.getClassLoader()
			.getResource(String.format("testFiles%s", File.separator)).getPath();

	private DuplicateRemover remover;
	private String fileOnePath;
	private String duplicates;

    @Before
    public void setUp() {
    	remover = new DuplicateRemover(TEST_FILES);
    	fileOnePath = String.format("%s%s", TEST_FILES, ONE_JPG);
    	duplicates = String.format("%sduplicates%s", TEST_FILES, File.separator);
    }

    @After
    public void cleanup() {
    	remover = null;
        fileOnePath = null;
	    cleanupDuplicatesDir();
	    duplicates = null;
    }

	private void cleanupDuplicatesDir() {
		Path duplicatesPath = Paths.get(duplicates);
		if (!Files.exists(duplicatesPath)) {
			return;
		}
		try {
			Files.list(duplicatesPath).filter(path -> Files.exists(path)).forEach(path -> {
				try (OutputStream fis = new FileOutputStream(String.format("%s%s", TEST_FILES, path.getFileName()))) {
					Files.copy(path, fis);
					Files.delete(path);
				} catch (IOException e) {
					fail("Should have never happened - deleting duplicates.");
				}
			});
			Files.deleteIfExists(duplicatesPath);
		} catch (IOException e) {
			fail("Should have never happened - deleting dir.");
		}
	}

    @Test
    public void givenRemoverWhenGetFileOnlyListThenReturnListOfFiles() {
	    List<File> fileOnlyList = remover.getFileOnlyList();
	    assertThat(fileOnlyList, notNullValue());
        assertThat(fileOnlyList, hasSize(NUMBER_OF_TEST_FILES));
    }

    @Test(expected = IOException.class)
    public void givenNotExistingFileWhenGetSHAHashForFileThenThrowFileNotFoundException() throws Exception {
	    remover.getSHAHashForFile(new File(""));
    }

	@Test(expected = NullPointerException.class)
	public void givenNullWhenGetSHAHashForFileThenThrowFileNotFoundException() throws Exception {
		remover.getSHAHashForFile(null);
	}

	@Test
	public void givenFileWhenGetSHAHashForFileThenReturnNotEmptyHash() throws Exception {
		String fileHash = remover.getSHAHashForFile(new File(fileOnePath));
		assertThat(fileHash, is( notNullValue()));
	}
    
    @Test
    public void givenTwoDifferentFilesWhenGetSHAHashForFileThenReturnDifferentHashes() throws Exception {
        String firstHash = remover.getSHAHashForFile(new File(fileOnePath));
        String secondFileHash = remover.getSHAHashForFile(new File(String.format("%s%s", TEST_FILES, TWO_JPG)));
        assertThat(firstHash, is( not( equalTo( secondFileHash))));
    }

	@Test
	public void givenTheSameFilesWhenGetSHAHashForFileThenReturnTheSameHash() throws Exception {
		String firstHash = remover.getSHAHashForFile(new File(fileOnePath));
		String secondFileHash = remover.getSHAHashForFile(new File(fileOnePath));
		assertThat(firstHash, is( equalTo( secondFileHash)));
	}

	@Test
	public void givenFilesWhenCreatePossibleDuplicateFileListThenReturnNotEmptyList() throws Exception {
		remover.createPossibleDuplicateFileList();
		List<File> possibleDuplicates = remover.getPossibleDuplicates();
		assertThat(possibleDuplicates, notNullValue());
		assertThat(possibleDuplicates, hasSize(1));
	}

    @Test
    public void givenFilesWhenCreateDuplicateListThenReturnNotEmptyList() throws Exception {
	    remover.createPossibleDuplicateFileList();
	    remover.createDuplicatesList();
	    List<File> duplicatesList = remover.getDuplicatesList();
	    assertThat(duplicatesList, notNullValue());
	    assertThat(duplicatesList, hasSize(1));
    }

	@Test
	public void whenCreateDuplicateDirIfNotExistsThenDirIsCreated() throws Exception {
		Path dir = Paths.get(duplicates);
		assertThat(Files.exists(dir), is( equalTo(false)));
		remover.createDuplicateDirIfNotExists();
		assertThat(Files.isDirectory(dir), is( equalTo(true)));
		assertTrue(Files.deleteIfExists(dir));
	}

    @Test
    public void whenMoveDuplicatesThenDuplicatesAreMoved() throws Exception {
        remover.createPossibleDuplicateFileList();
        remover.createDuplicatesList();
        remover.createDuplicateDirIfNotExists();
        remover.moveDuplicates();
        assertThat(new File(duplicates).listFiles(), arrayWithSize(1));
        assertThat(remover.getDuplicatesList(), hasSize(0));
    }

    @Test
    public void givenFilesWhenProcessDuplicatesThenSuccess() throws Exception {
    	DuplicateRemover spyRemover = spy(remover);

        spyRemover.processDuplicates();

        verify(spyRemover, times(1)).createPossibleDuplicateFileList();
        verify(spyRemover, times(1)).createDuplicatesList();
        verify(spyRemover, times(1)).createDuplicateDirIfNotExists();
        verify(spyRemover, times(1)).moveDuplicates();
	    assertThat(new File(duplicates).listFiles(), arrayWithSize(1));
	    assertThat(spyRemover.getDuplicatesList(), hasSize(0));
    }
}