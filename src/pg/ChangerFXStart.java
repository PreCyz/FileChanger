package pg;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import pg.view.ViewHandler;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:27:57
 */
public class ChangerFXStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new ViewHandler(stage).launchView();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
