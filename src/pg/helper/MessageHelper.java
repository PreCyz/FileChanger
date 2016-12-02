package pg.helper;

import pg.exception.ErrorCode;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageHelper {
    private ResourceBundle bundle;

    public MessageHelper(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getFullMessage(String key) {
        return getFullMessage(key, new Object[]{});
    }

    public String getFullMessage(String key, Object ... params) {
        try {
            return MessageFormat.format(bundle.getString(key), params);
        } catch (MissingResourceException ex) {
            return String.format("???%s???", key);
        }
    }

    public String getErrorMsg(ErrorCode errorCode, Object ... params) {
        String errorMsgKey = String.format("errorCode.%s", errorCode.name());
        return getFullMessage(errorMsgKey, params);
    }
    
    public static boolean empty(String value) {
        return value == null || value.length() == 0;
    }
    
}
