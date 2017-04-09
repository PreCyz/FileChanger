package pg;

import javafx.application.Application;
import javafx.stage.Stage;
import pg.view.WindowHandler;
import pg.view.AppWindowHandler;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:27:57
 */
public class FXFileChangerMain extends Application {

    @Override
    public void start(Stage stage) {
        WindowHandler windowHandler = new AppWindowHandler(stage);
        windowHandler.launchStartView();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
