package pg.logger;

import pg.helper.MessageHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gawa on 22.12.16.
 */
public abstract class AbstractLogger implements AppLogger {

    protected static List<String> logMessages = new LinkedList<>();

    protected final MessageHelper messageHelper;
    protected final Class whoLogs;

    public AbstractLogger(MessageHelper messageHelper, Class whoLogs) {
        this.messageHelper = messageHelper;
        this.whoLogs = whoLogs;
    }

    public static void addMessage(String message) {
        logMessages.add(message);
    }

}
