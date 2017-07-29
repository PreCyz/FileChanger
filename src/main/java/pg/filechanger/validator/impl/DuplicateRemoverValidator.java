package pg.filechanger.validator.impl;

import pg.exception.*;
import pg.filechanger.validator.ArgsValidator;

/**
 * Created by gawa on 07.04.17.
 */
public class DuplicateRemoverValidator implements ArgsValidator {

	private final String[] arguments;

	public DuplicateRemoverValidator(String[] arguments) {
		this.arguments = arguments;
	}

	@Override
	public void validate() throws ProgramException {
		if (arguments == null || arguments.length == 0) {
			throw new ProgramException(ErrorCode.NO_ARGUMENTS);
		}
		if (arguments[0] == null || "".equals(arguments[0].trim())) {
			throw new ProgramException(ErrorCode.ARGUMENT_WRONG_VALUE, arguments[0]);
		}
	}

}
