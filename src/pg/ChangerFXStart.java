package pg;

import javafx.application.Application;
import javafx.stage.Stage;
import pg.view.ViewHandler;
import pg.view.AppViewHandler;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:27:57
 */
public class ChangerFXStart extends Application {

    @Override
    public void start(Stage stage) {
        ViewHandler viewHandler = new AppViewHandler(stage);
        viewHandler.launchStartView();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
