package pg.logger.impl;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-14 21:07:43
 */
public class FileLogger implements AppLogger {
    
    private final MessageHelper messageHelper;
    private final Class whoLogs;
    private final ListView<String> listView;
    private List<String> logMessages;

    public FileLogger(MessageHelper messageHelper, Class whoLogs, ListView<String> listView) {
        this.messageHelper = messageHelper;
        this.whoLogs = whoLogs;
        this.listView = listView;
        logMessages = new LinkedList<>();
        logMessages.add("!!!FileLog initialized. " + whoLogs.getSimpleName());
        this.listView.setItems(FXCollections.observableArrayList(logMessages));
    }

    @Override
    public void log(ProgramException ex) {
        try {
            logMessages.add(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
            listView.setItems(FXCollections.observableArrayList(logMessages));
            listView.scrollTo(listView.getItems().size() - 1);
        } catch (ProgramException ex1) {
            if (ex1.isNullErrorCode()) {
                logMessages.add(messageHelper.getErrorMsg(ErrorCode.NULL_ERROR_CODE));
                listView.setItems(FXCollections.observableArrayList(logMessages));
                listView.scrollTo(listView.getItems().size() - 1);
            } else {
                log(ex1);
            }
        }
    }

    @Override
    public void log(String message) {
        logMessages.add(message);
        listView.setItems(FXCollections.observableArrayList(logMessages));
    }

}
