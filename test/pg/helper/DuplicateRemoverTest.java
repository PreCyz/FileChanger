package pg.helper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.runner.RunWith;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.runners.MockitoJUnitRunner;

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
	private static final String TEST_FILES = String.format("test%sresources%stestFiles%s",
			File.separator, File.separator, File.separator);

	private DuplicateRemover remover;
	private String fileOnePath;
	private String duplicates;

    @Before
    public void setUp() {
    	remover = new DuplicateRemover(getTestFileFullPath());
    	fileOnePath = getTestFileFullPath() + ONE_JPG;
    	duplicates = String.format("%sduplicates%s", getTestFileFullPath(), File.separator);
    }

	private String getTestFileFullPath() {
		String path = new File(".").toURI().getPath();
		return String.format("%s%s", path, TEST_FILES);
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
				try (OutputStream fis = new FileOutputStream(getTestFileFullPath() + path.getFileName())) {
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

	@Test
	public void givenFileWhenGetSHAHashForFileThenReturnNotEmptyHash() throws Exception {
		String fileHash = remover.getSHAHashForFile(new File(fileOnePath));
		assertThat(fileHash, is( not( isEmptyOrNullString() )));
	}
    
    @Test
    public void givenTwoDifferentFilesWhenGetSHAHashForFileThenReturnDifferentHashes() throws Exception {
        String firstHash = remover.getSHAHashForFile(new File(fileOnePath));
        String secondFileHash = remover.getSHAHashForFile(new File(getTestFileFullPath() + TWO_JPG));
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
    public void givenFilesWhenProcessDuplicatesThenSuccess() throws Exception{
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