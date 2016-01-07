package pg.picturefilechanger;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Gawa
 */
public abstract class AbstractFileChanger {
    protected final String[] params;
    
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
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
        extentions("Rozszerzenia plików"),
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
    
    
    public AbstractFileChanger(String[] params){
        this.params = params;
    }
    
    public void run(){
        Properties properties = transformArgumentsToProperties(params);
        exitOnEmptyProperties(properties);
        displayPropertiesDetails(properties);
        exitOnPropertiesValidationError(properties);
        displaySourceInfo(properties);
        ChangeDetails changeDetails = createChangeDetails(properties);
        Map<String, Integer> maxExtIdxMap = createMaxIndexMap(properties);
        processChange(maxExtIdxMap, changeDetails);
    }

    
    protected abstract Properties transformArgumentsToProperties(String[] params);
    protected abstract void exitOnEmptyProperties(Properties properties);
    protected abstract void displayPropertiesDetails(Properties properties);
    protected abstract void exitOnPropertiesValidationError(Properties properties);
    protected abstract void displaySourceInfo(Properties properties);
    protected abstract ChangeDetails createChangeDetails(Properties properties);
    protected abstract Map<String, Integer> createMaxIndexMap(Properties properties);
    protected abstract void processChange(Map<String, Integer> maxExtIdxMap, ChangeDetails changeDetails);
}
