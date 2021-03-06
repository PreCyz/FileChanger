package pg.helper;

import pg.constant.AppConstants;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Gawa [Paweł Gawędzki]
 */
public final class PropertiesHelper {

	private PropertiesHelper() {}

	public static Properties loadProgramProperties(String filePath) throws ProgramException {
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

    public static void saveProgramProperties(Properties props, String filePath) throws ProgramException {
        try (Writer writer = new PrintWriter(filePath)) {
            props.store(writer, "");
            writer.flush();
        } catch (Exception ex) {
            throw new ProgramException(ErrorCode.SAVE_PROPERTIES, ex);
        }
    }

    public static ResourceBundle readBundles() throws ProgramException {
        ResourceBundle bundle = ResourceBundle.getBundle(AppConstants.BUNDLE_PATH);
        if (bundle == null) {
            throw new ProgramException(ErrorCode.LOAD_BUNDLE);
        }
        return bundle;
    }

    public static ResourceBundle readBundles(Locale locale) throws ProgramException {
        ResourceBundle bundle = ResourceBundle.getBundle(AppConstants.BUNDLE_PATH, locale);
        if (bundle == null) {
            throw new ProgramException(ErrorCode.LOAD_BUNDLE);
        }
        return bundle;
    }

    //example source=srcPath destination=destPath extensions=ext1,ext2,ext3,ext4...
    public static Properties transformArgumentsToProperties(String[] stringArray) {
        Properties properties = new Properties();
        for (String s : stringArray) {
            properties.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1));
        }
        return properties;
    }
}
