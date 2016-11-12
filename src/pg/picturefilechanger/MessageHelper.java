package pg.picturefilechanger;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-12 22:03:27
 */
public class MessageHelper {
    
    private final Properties bundle;

    public MessageHelper(Properties bundle) {
        this.bundle = bundle;
    }
    
    public String msg(String key, Object ... params) {
        String bundleMsg = bundle.getProperty(key);
        if (bundleMsg == null) {
            return String.format("??? %s ???", key);
        }
        if (params.length == 0) {
            return bundleMsg;
        }
        MessageFormat mf = new MessageFormat(bundleMsg);
        return mf.format(params);
    }

}
