package pg.helper;

import pg.constant.AppConstants;
import pg.exception.ProgramException;
import pg.filechanger.dto.ChangeDetails;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by gawa on 25.12.16.
 */
public class AppConfigHelper {

    private static AppConfigHelper instance = null;

    private Properties appConfig;

    private AppConfigHelper() throws ProgramException {
        URL url = getClass().getClassLoader().getResource(AppConstants.APP_CONFIG);
        appConfig = PropertiesHelper.loadProgramProperties(url.getPath());
    }

    public static AppConfigHelper getInstance() throws ProgramException {
        if (instance == null) {
            instance = new AppConfigHelper();
        }
        return instance;
    }

    public void updateAppConfiguration(ChangeDetails changeDetails) throws ProgramException {
        appConfig.setProperty("core.name.lastUsed", changeDetails.getFileCoreName());
        appConfig.setProperty("file.connector.lastUsed", changeDetails.getFileNameIndexConnector());
        appConfig.setProperty("file.extensions", changeDetails.getFileExtension());

        PropertiesHelper.saveProgramProperties(appConfig, AppConstants.APP_CONFIG);
    }

    public String getCoreNameLastUsed() {
        return appConfig.getProperty("core.name.lastUsed");
    }

    public List<String> getFileConnectors() {
        return Arrays.asList(appConfig.getProperty("file.connectors").split(","));
    }

    public String getFileConnectorLastUsed() {
        List<String> fileConnectors = getFileConnectors();
        int idx = fileConnectors.indexOf(appConfig.getProperty("file.connector.lastUsed"));
        if (idx > -1) {
            return fileConnectors.get(idx);
        }
        return fileConnectors.get(0);
    }

    public String getExtensions() {
        return appConfig.getProperty("file.extensions");
    }

    public boolean getHideLogs() {
        String value = appConfig.getProperty("hide.logs").toLowerCase();
        return "true".equals(value);
    }
}
