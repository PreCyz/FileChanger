package pg.helper;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.picturefilechanger.ChangeDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by gawa on 25.12.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppConfigHelperTest {

    private AppConfigHelper appConfigHelper;
    @Mock private AppConfigHelper mockAppConfigHelper;

    @After
    public void tearDown() throws Exception {
        appConfigHelper = null;
        mockAppConfigHelper = null;
    }

    @Test
    public void whenGetInstanceThenReturnNotNullAppConfigHelper() throws ProgramException {
        assertNotNull(AppConfigHelper.getInstance());
    }

    @Test
    public void givenMockHelperWhenUpdateAppConfigurationThrowProgramException() throws Exception {
        ProgramException pe = new ProgramException(ErrorCode.SAVE_PROPERTIES);
        doThrow(pe).when(mockAppConfigHelper).updateAppConfiguration(new ChangeDetails());

        try {
            mockAppConfigHelper.updateAppConfiguration(new ChangeDetails());
        } catch (ProgramException ex) {
            assertEquals("ErrorCodes should be equal.", pe.getErrorCode(), ex.getErrorCode());
        }
    }

    @Test
    public void givenMockHelperWhenGetCoreNameLastUsedThenReturnString() {
        when(mockAppConfigHelper.getCoreNameLastUsed()).thenReturn("coreNameLastUsed");
        assertTrue("Return type should be String.", mockAppConfigHelper.getCoreNameLastUsed() instanceof String);
    }

    @Test
    public void givenMockHelperWhenGetFileConnectorsThenReturnListOfStrings() {
        when(mockAppConfigHelper.getFileConnectors()).thenReturn(Arrays.asList("", ""));
        assertTrue("Return type should be List.", mockAppConfigHelper.getFileConnectors() instanceof List);
    }

    @Test
    public void givenMockHelperWhenGetFileConnectorLastUsedThenReturnString() {
        when(mockAppConfigHelper.getFileConnectorLastUsed()).thenReturn("fileConnectorLastUsed");
        assertTrue("Return type should be String.", mockAppConfigHelper.getFileConnectorLastUsed() instanceof String);
    }

    @Test
    public void givenMockHelperWhenGetExtensionsThenReturnString() throws Exception {
        when(mockAppConfigHelper.getExtensions()).thenReturn("extensions");
        assertTrue("Return type should be String.", mockAppConfigHelper.getExtensions() instanceof String);
    }

}