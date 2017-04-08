package pg.filechanger.core;

import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import pg.exception.ProgramException;
import pg.filechanger.dto.ChangeDetails;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

/**
 * @author Gawa
 */
public abstract class AbstractFileChanger {

    protected final AppLogger logger;
    protected final ResourceBundle bundle;
    protected final MessageHelper messageHelper;
    protected ChangeDetails changeDetails;
    private String[] params;

    public AbstractFileChanger(AppLogger logger, ResourceBundle bundle) {
	    this.logger = logger;
	    this.bundle = bundle;
	    messageHelper = MessageHelper.getInstance(bundle);
    }

    public AbstractFileChanger(String[] params, ResourceBundle bundle, AppLogger logger) {
    	this(logger, bundle);
        this.params = params;
    }

    public AbstractFileChanger(ChangeDetails changeDetails, ResourceBundle bundle, AppLogger logger) {
    	this(logger, bundle);
        this.changeDetails = changeDetails;
    }
    
    public void run() throws ProgramException {
        if (changeDetails == null) {
            createChangeDetails(params);
        }
        createDestinationIfNotExists();
        Map<String, Integer> maxExtIdxMap = createMaxIndexMap(changeDetails);
        processChange(maxExtIdxMap, changeDetails);
    }

    //example source=srcPath destination=destPath extensions=ext1,ext2,ext3,ext4...
    public static Properties transformArgumentsToProperties(String[] params) {
        Properties properties = new Properties();
        for (String s : params) {
            properties.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1));
        }
        return properties;
    }

	public abstract Map<String, Integer> createMaxIndexMap(ChangeDetails changeDetails);
	protected abstract ChangeDetails createChangeDetails(String[] parameters);
	protected abstract void createDestinationIfNotExists() throws ProgramException;
    protected abstract void processChange(Map<String, Integer> maxExtIdxMap, ChangeDetails changeDetails);

}
