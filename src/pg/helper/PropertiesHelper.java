package pg.helper;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.ResourceBundle;
import pg.constant.ProgramConstants;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;

/**
 * @author Gawa [Paweł Gawędzki]
 */
public class PropertiesHelper {

    private boolean anyPropertyChanged;

    public Properties loadProgramProperties(String filePath) throws ProgramException {
        if (filePath == null || filePath.length() == 0) {
            return null;
        }
        Properties props = new Properties();
        try (Reader reader = new FileReader(filePath)){
            props.load(reader);
        } catch (Exception ex) {
            throw new ProgramException(ErrorCode.LOAD_PROPERTIES, ex);
        }
        return props;
    }

    public void saveProgramProperties(Properties props, String filePath) throws ProgramException {
        try (Writer writer = new PrintWriter(filePath)){
            props.store(writer, "");
            writer.flush();
        } catch (Exception ex) {
            throw new ProgramException(ErrorCode.SAVE_PROPERTIES, ex);
        }
    }

    public void changeProperty(Properties properties, String key, String valueToBeAdded) {
        String newValue = String.format("%s%s", properties.getProperty(key), valueToBeAdded);
        properties.setProperty(key, newValue);
        anyPropertyChanged = true;
    }

    public void overwriteProperty(Properties properties, String key, String newValue) {
        properties.setProperty(key, newValue);
        anyPropertyChanged = true;
    }

    public boolean isAnyPropertyChanged() {
        return anyPropertyChanged;
    }
    
    public static ResourceBundle readBundles() throws ProgramException {
        ResourceBundle bundle = ResourceBundle.getBundle(ProgramConstants.BUNDLE_PATH);
        if (bundle == null) {
            throw new ProgramException(ErrorCode.LOAD_BUNDLE);
        }
        MessageHelper helper = MessageHelper.getInstance(bundle);
        System.out.println(helper.getFullMessage("bundle.loaded"));
        return bundle;
    }
}
