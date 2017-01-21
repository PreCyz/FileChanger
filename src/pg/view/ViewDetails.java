package pg.view;

import pg.constant.ProgramConstants;
import pg.view.controller.AbstractController;
import pg.view.controller.LoggerController;
import pg.view.controller.MainController;

import java.net.URL;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:31:16
 */
public enum ViewDetails {
    START_VIEW("main.fxml", new MainController(), "mainWindowIcon.png", "window.main.title"),
    LOGGER_VIEW("logger.fxml", new LoggerController(), "loggerWindowIcon.png", "window.logger.title");

    private AbstractController controller;
    private URL url;
    private String windowImgFileName;
    private String windowTitleBundle;

    ViewDetails(String fxmlFileName, AbstractController controller, String windowImgFileName, String windowTitleBundle) {
        this.controller = controller;
        this.url = getClass().getClassLoader().getResource(ProgramConstants.FXML_RESOURCE_PATH + fxmlFileName);
        this.windowImgFileName = windowImgFileName;
        this.windowTitleBundle = windowTitleBundle;
    }

    public URL url() {
        return url;
    }

    public AbstractController controller(ViewHandler viewHandler) {
        controller.setViewHandler(viewHandler);
        return controller;
    }

    public String windowImgFileName() {
        return windowImgFileName;
    }

    public String windowTitleBundle() {
        return windowTitleBundle;
    }
}
