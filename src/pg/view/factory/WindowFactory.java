package pg.view.factory;

import pg.constant.AppConstants;
import pg.view.ViewHandler;
import pg.view.controller.AbstractController;
import pg.view.controller.LoggerController;
import pg.view.controller.MainController;
import pg.view.window.AbstractWindow;
import pg.view.window.impl.LoggerWindow;
import pg.view.window.impl.MainWindow;

import java.net.URL;
import java.util.ResourceBundle;

import static pg.constant.AppConstants.IMG_RESOURCE_PATH;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:31:16
 */
public enum WindowFactory {

    START {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle) {
            return new MainWindow(viewHandler, bundle);
        }
    },
    LOGGER {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle) {
            return new LoggerWindow(viewHandler, bundle);
        }
    };

    public abstract AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle);
}
