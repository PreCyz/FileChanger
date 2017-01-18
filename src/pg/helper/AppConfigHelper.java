package pg.helper;

import pg.constant.ProgramConstants;
import pg.exception.ProgramException;
import pg.picturefilechanger.ChangeDetails;

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
        URL url = getClass().getClassLoader().getResource(ProgramConstants.APP_CONFIG_PATH);
        appConfig = new PropertiesHelper().loadProgramProperties(url.getPath());
    }

    public static AppConfigHelper getInstance() throws ProgramException {
        if (instance == null) {
            instance = new AppConfigHelper();
        }
        return instance;
    }

    public void updateAppConfiguration(ChangeDetails changeDetails) throws ProgramException {
        appConfig.setProperty("core.name.lastUsed", changeDetails.getCoreName());
        appConfig.setProperty("file.connector.lastUsed", changeDetails.getFileNameIndexConnector());
        appConfig.setProperty("file.extensions", changeDetails.getFileExtension());

        new PropertiesHelper().saveProgramProperties(appConfig, ProgramConstants.APP_CONFIG_PATH);
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
