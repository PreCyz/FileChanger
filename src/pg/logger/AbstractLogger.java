package pg.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pg.helper.MessageHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gawa on 22.12.16.
 */
public abstract class AbstractLogger implements AppLogger {

    protected static Logger logger;
    protected static List<String> logMessages = new LinkedList<>();

    protected final MessageHelper messageHelper;

    public AbstractLogger(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
        logger = LogManager.getLogger(this.getClass().getSimpleName());
    }

    public static void addMessage(String message) {
        logMessages.add(message);
    }
    public static List<String> getLogs() {
        return new ArrayList<>(logMessages);
    }

}
