package pg.logger.impl;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import pg.logger.AbstractLogger;

/**
 * @author Gawa 2016-12-14 21:07:43
 */
public class FileLogger extends AbstractLogger {

    private final ListView<String> listView;

    public FileLogger(ListView<String> listView) {
        super();
        this.listView = listView;
        this.listView.setItems(FXCollections.observableArrayList(logMessages));
    }

    private void showOnListView() {
        listView.setItems(FXCollections.observableArrayList(logMessages));
        listView.scrollTo(listView.getItems().size() - 1);
    }

    @Override
    public void log(String message) {
        logMessages.add(message);
        showOnListView();
        logger.log(logger.getLevel(), message);
    }

}
