package pg.view.factory;

import pg.view.ViewHandler;
import pg.view.controller.LoggerController;
import pg.view.controller.MainController;
import pg.view.window.AbstractWindow;
import pg.view.window.impl.LoggerWindow;
import pg.view.window.impl.MainWindow;

import java.util.ResourceBundle;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:31:16
 */
public enum WindowFactory {

    START {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle) {
            return new MainWindow(new MainController(viewHandler), bundle);
        }
    },
    LOGGER {
        @Override
        public AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle) {
            return new LoggerWindow(new LoggerController(viewHandler), bundle);
        }
    };

    public abstract AbstractWindow createWindow(ViewHandler viewHandler, ResourceBundle bundle);
}
