package pg.picturefilechanger.validator;

import pg.exception.ProgramException;

/**
 * Created by gawa on 02.03.17.
 */
public interface Validator {

    void validate() throws ProgramException;

}
