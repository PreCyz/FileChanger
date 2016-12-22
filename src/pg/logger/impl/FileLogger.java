package pg.logger.impl;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.logger.AbstractLogger;
import pg.logger.AppLogger;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-14 21:07:43
 */
public class FileLogger extends AbstractLogger {

    private final ListView<String> listView;

    public FileLogger(MessageHelper messageHelper, Class whoLogs, ListView<String> listView) {
        super(messageHelper, whoLogs);
        this.listView = listView;
        logMessages.add("!!!FileLog initialized. " + whoLogs.getSimpleName());
        this.listView.setItems(FXCollections.observableArrayList(logMessages));
    }

    @Override
    public void log(ProgramException ex) {
        try {
            logMessages.add(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
        } catch (ProgramException ex1) {
            if (ex1.isNullErrorCode()) {
                logMessages.add(messageHelper.getErrorMsg(ErrorCode.NULL_ERROR_CODE));
            } else {
                log(ex1);
            }
        } finally {
            listView.setItems(FXCollections.observableArrayList(logMessages));
            listView.scrollTo(listView.getItems().size() - 1);
        }
    }

    @Override
    public void log(String message) {
        logMessages.add(message);
        listView.setItems(FXCollections.observableArrayList(logMessages));
        listView.scrollTo(listView.getItems().size() - 1);
    }

}
