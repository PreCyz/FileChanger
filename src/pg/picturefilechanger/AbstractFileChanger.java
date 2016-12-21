package pg.picturefilechanger;

import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import pg.exception.ProgramException;
import pg.helper.MessageHelper;

/**
 *
 * @author Gawa
 */
public abstract class AbstractFileChanger {
    protected final String[] params;
    protected final ResourceBundle bundle;
    protected final MessageHelper messageHelper;
    
    public static enum Extentions {
        jpg, jpeg, mp4, gif;

        public static boolean isFileExtensionProcessable(String fileExt) {
            for (Extentions ext : Extentions.values()) {
                if (fileExt.equalsIgnoreCase(ext.name())) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public static enum Params {
        source("Folder źródłowy"),
        destination("Folder docelowy"),
        extensions("Rozszerzenia plików"),
        filePrefix("Prefix nazwy nowego pliku"),
        nameConnector("Łącznik nazwy pliku z jego indexem");

        private String msg;
        public String getMsg() {
            return msg;
        }
        Params(String msg) {
            this.msg = msg;
        }
    }
    
    
    public AbstractFileChanger(String[] params, ResourceBundle bundle){
        this.params = params;
        this.bundle = bundle;
        messageHelper = MessageHelper.getInstance(bundle);
    }
    
    public void run() throws ProgramException {
        Properties properties = transformArgumentsToProperties(params);
        exitOnEmptyProperties(properties);
        displayPropertiesDetails(properties);
        exitOnPropertiesValidationError(properties);
        displaySourceInfo(properties);
        ChangeDetails changeDetails = createChangeDetails(properties);
        createDestinationIfNotExists(changeDetails);
        Map<String, Integer> maxExtIdxMap = createMaxIndexMap(properties);
        processChange(maxExtIdxMap, changeDetails);
    }

    
    protected abstract Properties transformArgumentsToProperties(String[] params);
    protected abstract void exitOnEmptyProperties(Properties properties);
    protected abstract void displayPropertiesDetails(Properties properties);
    protected abstract void exitOnPropertiesValidationError(Properties properties);
    protected abstract void displaySourceInfo(Properties properties);
    protected abstract ChangeDetails createChangeDetails(Properties properties);
    protected abstract void createDestinationIfNotExists(ChangeDetails changeDetails) throws ProgramException;
    protected abstract Map<String, Integer> createMaxIndexMap(Properties properties);
    protected abstract void processChange(Map<String, Integer> maxExtIdxMap, ChangeDetails changeDetails);
}
