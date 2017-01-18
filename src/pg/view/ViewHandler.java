package pg.view;

import javafx.stage.Window;

/**
 * Created by gawa on 14.01.17.
 */
public interface ViewHandler {

    void launchStartView();
    void launchLoggerView();
    void changeWindowWidth(double width);
    Window window();

}
