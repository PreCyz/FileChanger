package pg.filechanger.validator;

import pg.exception.ProgramException;

/**
 * Created by gawa on 02.03.17.
 */
public interface ArgsValidator {

    void validate() throws ProgramException;

}
