package pg.picturefilechanger.validator.impl;

import org.junit.After;
import org.junit.Test;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.PropertiesHelper;
import pg.picturefilechanger.ChangeDetails;
import pg.picturefilechanger.validator.Validator;

import static org.junit.Assert.*;

public class ArgsValidatorTest {

    private Validator validator;

    @After
    public void tearDown() throws Exception {
        validator = null;
    }

    @Test
    public void givenNullArgumentsWhenValidateThenThrowProgramExceptionWithNoArguments() throws Exception {
        try {
            String[] args = null;
            validator = new ArgsValidator(args, PropertiesHelper.readBundles());
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
                    new String[]{"source=1", "destination=2", "extensions=1", "coreName=1", "nameConnector=1"},
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
                    new String[]{"1=1", "destination=2", "extensions=1", "coreName=1", "nameConnector=1"},
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
                    new String[] {"source=1", "2=2", "extensions=1", "coreName=1", "nameConnector=1"},
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
                    new String[] {"source=1", "destination=2", "1=1", "coreName=1", "nameConnector=1"},
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
                    new String[] {"source=1", "destination=2", "extensions=1", "coreName=1", "1=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException | IllegalArgumentException ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenNoNameConnectorWhenValidateThenThrowProgramException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "destination=2", "extensions=1", "coreName=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu nameConnector[Łącznik nazwy pliku z jego " +
                    "indexem]=[null].";
            assertEquals(ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test
    public void givenNoFilePrefixWhenValidateThenThrowProgramException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "destination=2", "extensions=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu coreName[Główna nazwa pliku]=[null].";
            assertEquals(ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test
    public void givenNoExtensionsWhenValidateThenThrowProgramException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1", "destination=2"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu extensions[Rozszerzenia plików]=[null].";
            assertEquals(ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test
    public void givenNoDestinationWhenValidateThenThrowProgramException() {
        try {
            validator = new ArgsValidator(
                    new String[] {"source=1"},
                    PropertiesHelper.readBundles()
            );
            validator.validate();
            fail("There should be no IllegalArgumentException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu destination[Folder docelowy]=[null].";
            assertEquals(ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test(expected = NullPointerException.class)
    public void givenNullChangeDetailsWhenValidateThenThrowProgramException() throws Exception {
        ChangeDetails changeDetails = null;
        validator = new ArgsValidator(changeDetails, PropertiesHelper.readBundles());
        validator.validate();
    }

    @Test
    public void givenEmptyObjectChangeDetailsWhenValidateThenThrowProgramException() {
        try {
            ChangeDetails changeDetails = new ChangeDetails();
            validator = new ArgsValidator(changeDetails, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should throw ProgramException.");
        } catch (ProgramException ex) {
            assertEquals("ErrorCode should be ...", ErrorCode.NO_ARGUMENTS, ex.getErrorCode());
        }
    }

    @Test
    public void givenChangeDetailsWithSourceWhenValidateThenThrowProgramException() {
        try {
            ChangeDetails changeDetails = new ChangeDetails("source", null, null, null);
            validator = new ArgsValidator(changeDetails, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should throw ProgramException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu destination[Folder docelowy]=[null].";
            assertEquals("ErrorCode should be ...", ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test
    public void givenChangeDetailsWithSourceAndDestinationWhenValidateThenThrowProgramException() {
        try {
            ChangeDetails changeDetails = new ChangeDetails("source", "destination", null, null);
            validator = new ArgsValidator(changeDetails, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should throw ProgramException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu extensions[Rozszerzenia plików]=[null].";
            assertEquals("ErrorCode should be ...", ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test
    public void givenChangeDetailsWithoutCoreNameWhenValidateThenThrowProgramException() {
        try {
            ChangeDetails changeDetails = new ChangeDetails("source", "destination", null, null);
            changeDetails.setFileExtension("extensions");
            validator = new ArgsValidator(changeDetails, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should throw ProgramException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu coreName[Główna nazwa pliku]=[null].";
            assertEquals("ErrorCode should be ...", ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

    @Test
    public void givenChangeDetailsWithoutConnectorWhenValidateThenThrowProgramException() {
        try {
            ChangeDetails changeDetails = new ChangeDetails("source", "destination", "fileCoreName", null);
            changeDetails.setFileExtension("extensions");
            validator = new ArgsValidator(changeDetails, PropertiesHelper.readBundles());
            validator.validate();
            fail("Should throw ProgramException.");
        } catch (ProgramException ex) {
            String expectedMsg = "Zła wartość wymaganego argumentu nameConnector[Łącznik nazwy pliku z jego " +
                    "indexem]=[null].";
            assertEquals("ErrorCode should be ...", ErrorCode.ARGUMENT_WRONG_VALUE, ex.getErrorCode());
            assertEquals(expectedMsg, ex.getArgument());
        }
    }

}