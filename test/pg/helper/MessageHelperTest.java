package pg.helper;

import org.junit.*;
import pg.exception.ErrorCode;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static pg.constant.AppConstants.RESOURCE_BUNDLE;

/**
 * @author Gawa [Paweł Gawędzki]
 * @date 2016-11-23 20:28:44
 */
public class MessageHelperTest {

	private static final String WRONG_KEY = "wrong.key";
	private static final String TEST_KEY = "param.source";
	private static final String TEST_KEY_PARAM = "file.name.changed";
	private static final String TEST_VALUE = "Folder źródłowy";
	private static final String TEST_VALUE_PARAM = "Zmieniono nazwe pliku 1 na 2.";

	private static MessageHelper messageHelper;

	@BeforeClass
	public static void beforeClassSetup() {
		messageHelper = MessageHelper.getInstance(ResourceBundle.getBundle(RESOURCE_BUNDLE));
	}

    @Test
    public void givenNullStringWhenEmptyThenTrue() throws Exception {
        assertTrue(MessageHelper.empty(null));
    }

    @Test
    public void givenEmptyStringWhenEmptyThenTrue() throws Exception {
        assertTrue(MessageHelper.empty(""));
    }

    @Test
    public void givenSpaceStringWhenEmptyThenTrue() throws Exception {
        assertTrue(MessageHelper.empty(" "));
    }

    @Test
    public void givenNotEmptyStringWhenEmptyThenFalse() throws Exception {
        assertThat(MessageHelper.empty("qq"), is( equalTo(false)));
    }

    @Test
    public void givenProperKeyWhenGetFullMessageThenReturnProperValue() {
		assertThat(messageHelper.getFullMessage(TEST_KEY), is( equalTo( TEST_VALUE)));
    }

	@Test
	public void givenWrongKeyWhenGetFullMessageThenReturnQuestionMarks() {
		assertThat(messageHelper.getFullMessage(WRONG_KEY), is( equalTo( String.format("???%s???", WRONG_KEY))));
	}

	@Test
	public void givenWrongKeyWithParamsWhenGetFullMessageThenReturnQuestionMarks() {
		assertThat(messageHelper.getFullMessage(WRONG_KEY, 1, 2),
				is( equalTo( String.format("???%s???", WRONG_KEY))));
	}

	@Test
	public void givenProperKeyWithParamsWhenGetFullMessageThenReturnProperValue() {
		assertThat(messageHelper.getFullMessage(TEST_KEY_PARAM, 1, 2), is( equalTo( TEST_VALUE_PARAM)));
	}

	@Test(expected = NullPointerException.class)
	public void givenNullErrorCodeWhenGetErrorMsgThenThrowNullPointerException() {
		messageHelper.getErrorMsg(null);
	}

	@Test
	public void givenErrorCodeWhenGetErrorMsgThenReturnProperMessage() {
		String errorMsg = "Plik bundle nie został wczytany.";
		assertThat(messageHelper.getErrorMsg(ErrorCode.LOAD_BUNDLE), is( equalTo(errorMsg)));
	}

	@Test
	public void givenErrorCodeAndParamsWhenGetErrorMsgThenReturnProperMessage() {
		String errorMsg = "Parametr [1] ma wartość null.";
		assertThat(messageHelper.getErrorMsg(ErrorCode.NULL_ARGUMENT, 1), is( equalTo(errorMsg)));
	}

}