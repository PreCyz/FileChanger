package pg.picturefilechanger.validator.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pg.PictureFileChanger;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.PropertiesHelper;
import pg.picturefilechanger.validator.Validator;

import static org.junit.Assert.*;

/**
 * Created by gawa on 02.03.17.
 */
public class ArgsValidatorTest {

    private Validator validator;

    @After
    public void tearDown() throws Exception {
        validator = null;
    }

    @Test
    public void givenNullArgumentsWhenValidateThenThrowProgramExceptionWithNoArguments() throws Exception {
        try {
            validator = new ArgsValidator(null, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should be ProgramException");
        } catch (ProgramException ex) {
            assertEquals(ErrorCode.NO_ARGUMENTS, ex.getErrorCode());
        }
    }

    @Test
    public void givenProperArgumentsWhenValidateThenSuccess() {
        try {
            validator = new ArgsValidator(
                    new String[]{"source=1", "destination=2", "extensions=1", "filePrefix=1", "nameConnector=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
        } catch (ProgramException ex) {
            fail("There should be no exception.");
        }
    }

    @Test
    public void givenEmptyArgumentWhenValidateThenThrowProgramException() throws Exception {
        try {
            validator = new ArgsValidator(new String[]{"", "1"}, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should throw ProgramException");
        } catch (ProgramException ex) {
            assertEquals(ErrorCode.NULL_ARGUMENT, ex.getErrorCode());
        }
    }

    @Test
    public void givenWrongSourceWhenValidateThenThrowIllegalArgumentException() {
        try {
            validator = new ArgsValidator(
                    new String[]{"1=1", "destination=2", "extensions=1", "filePrefix=1", "nameConnector=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException | IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenWrongDestinationWhenValidateThenThrowIllegalArgumentException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "2=2", "extensions=1", "filePrefix=1", "nameConnector=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException | IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenWrongExtensionsWhenValidateThenThrowIllegalArgumentException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "destination=2", "1=1", "filePrefix=1", "nameConnector=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException | IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenWrongFilePrefixWhenValidateThenThrowIllegalArgumentException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "destination=2", "extensions=1", "1=1", "nameConnector=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException | IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenWrongNameConnectorWhenValidateThenThrowIllegalArgumentException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "destination=2", "extensions=1", "filePrefix=1", "1=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException | IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

}