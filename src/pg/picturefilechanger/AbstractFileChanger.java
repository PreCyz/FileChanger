package pg.picturefilechanger;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import pg.exception.ProgramException;
import pg.helper.MessageHelper;

import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;

/**
 *
 * @author Gawa
 */
public abstract class AbstractFileChanger {
    protected final String[] params;
    protected final ResourceBundle bundle;
    protected final MessageHelper messageHelper;
    
    public enum Extensions {
        jpg, jpeg, mp4, gif;

        public static boolean isFileExtensionProcessable(String fileExt) {
            for (Extensions ext : Extensions.values()) {
                if (fileExt.equalsIgnoreCase(ext.name())) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public enum Params {
        source("param.source"),
        destination("param.destination"),
        extensions("param.extensions"),
        filePrefix("param.filePrefix"),
        nameConnector("param.nameConnector");

        private String msg;
        private MessageHelper messageHelper;
        Params(String msg) {
            this.msg = msg;
            messageHelper = MessageHelper.getInstance(
                    ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault()));
        }
        public String getMsg() {
            return messageHelper.getFullMessage(msg);
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
