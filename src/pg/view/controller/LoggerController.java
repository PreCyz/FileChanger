package pg.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pg.logger.AbstractLogger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:34:16
 */
public class LoggerController extends AbstractController {

    @FXML private ListView<String> loggerListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        loggerListView.setItems(FXCollections.observableList(AbstractLogger.getLogs()));
    }

    public void calculateWindowWidth() {}

}
