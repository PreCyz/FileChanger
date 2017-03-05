package pg.picturefilechanger;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import pg.exception.ProgramException;
import pg.helper.MessageHelper;

import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;

/**
 * @author Gawa
 */
public abstract class AbstractFileChanger {

    private String[] params;
    protected final ResourceBundle bundle;
    protected final MessageHelper messageHelper;
    protected ChangeDetails changeDetails;

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
        public String message() {
            return messageHelper.getFullMessage(msg);
        }
    }

    public AbstractFileChanger(String[] params, ResourceBundle bundle){
        this.params = params;
        this.bundle = bundle;
        messageHelper = MessageHelper.getInstance(bundle);
    }

    public AbstractFileChanger(ChangeDetails changeDetails, ResourceBundle bundle){
        this.changeDetails = changeDetails;
        this.bundle = bundle;
        messageHelper = MessageHelper.getInstance(bundle);
    }
    
    public void run() throws ProgramException {
        if (changeDetails == null) {
            createChangeDetails(params);
        }
        createDestinationIfNotExists();
        Map<String, Integer> maxExtIdxMap = createMaxIndexMap();
        processChange(maxExtIdxMap, changeDetails);
    }

    //example source=sciezka destination=sciezka extensions=ext1,ext2,ext3,ext4...
    public static Properties transformArgumentsToProperties(String[] params) {
        Properties properties = new Properties();
        for (String s : params) {
            properties.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1));
        }
        return properties;
    }

    protected abstract ChangeDetails createChangeDetails(String[] parameters);
    protected abstract void createDestinationIfNotExists() throws ProgramException;
    protected abstract Map<String, Integer> createMaxIndexMap();
    protected abstract void processChange(Map<String, Integer> maxExtIdxMap, ChangeDetails changeDetails);
}
