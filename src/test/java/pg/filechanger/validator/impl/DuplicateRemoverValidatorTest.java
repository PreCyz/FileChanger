package pg.filechanger.validator.impl;

import pg.filechanger.validator.impl.DuplicateRemoverValidator;
import org.junit.*;
import pg.exception.*;
import pg.filechanger.validator.ArgsValidator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by gawa on 07.04.17.
 */
public class DuplicateRemoverValidatorTest {

	private ArgsValidator duplicateRemoverValidator;

	@After
	public void cleanup() {
		duplicateRemoverValidator = null;
	}


	@Test
	public void givenNullArgsArrayWhenValidateThenThrowProgramExceptionWithErrorCodeNO_ARGUMENTS() {
		try {
			duplicateRemoverValidator = new DuplicateRemoverValidator(null);
			duplicateRemoverValidator.validate();
			fail("Should throw ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.NO_ARGUMENTS)));
		}
	}

	@Test
	public void givenEmptyArgsArrayWhenValidateThenThrowProgramExceptionWithErrorCodeNO_ARGUMENTS() {
		try {
			duplicateRemoverValidator = new DuplicateRemoverValidator(new String[]{});
			duplicateRemoverValidator.validate();
			fail("Should throw ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.NO_ARGUMENTS)));
		}
	}

	@Test
	public void givenArgsArrayWithNullWhenValidateThenThrowProgramExceptionWithErrorCodeARGUMENT_WRONG_VALUE() {
		try {
			duplicateRemoverValidator = new DuplicateRemoverValidator(new String[]{});
			duplicateRemoverValidator.validate();
			fail("Should throw ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.NO_ARGUMENTS)));
		}
	}

	@Test
	public void givenArgsArrayWithEmptyWhenValidateThenThrowProgramExceptionWithErrorCodeARGUMENT_WRONG_VALUE() {
		try {
			duplicateRemoverValidator = new DuplicateRemoverValidator(new String[]{""});
			duplicateRemoverValidator.validate();
			fail("Should throw ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.ARGUMENT_WRONG_VALUE)));
		}
	}

	@Test
	public void givenArgsArrayWithSpaceWhenValidateThenThrowProgramExceptionWithErrorCodeARGUMENT_WRONG_VALUE() {
		try {
			duplicateRemoverValidator = new DuplicateRemoverValidator(new String[]{" "});
			duplicateRemoverValidator.validate();
			fail("Should throw ProgramException.");
		} catch (ProgramException ex) {
			assertThat(ex.getErrorCode(), is( equalTo(ErrorCode.ARGUMENT_WRONG_VALUE)));
		}
	}

	@Test
	public void givenArgsArrayWithPathWhenValidateThenSuccess() {
		try {
			duplicateRemoverValidator = new DuplicateRemoverValidator(new String[]{"aa"});
			duplicateRemoverValidator.validate();
		} catch (Exception ex) {
			fail("Should not throw any error.");
		}
	}

}