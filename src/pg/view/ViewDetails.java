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
    START_VIEW("main.fxml", new MainController()),
    LOGGER_VIEW("logger.fxml", new LoggerController());

    private AbstractController controller;
    private URL url;

    ViewDetails(String fileName, AbstractController controller) {
        this.controller = controller;
        this.url = getClass().getClassLoader().getResource(ProgramConstants.FXML_RESOURCE_PATH + fileName);
    }

    public URL url() {
        return url;
    }

    public AbstractController controller(ViewHandler viewHandler) {
        controller.setViewHandler(viewHandler);
        return controller;
    }

}
