package pg.view.factory;

import pg.view.WindowHandler;
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
        public AbstractWindow createWindow(WindowHandler windowHandler, ResourceBundle bundle) {
            return new MainWindow(new MainController(windowHandler), bundle);
        }
    },
    LOGGER {
        @Override
        public AbstractWindow createWindow(WindowHandler windowHandler, ResourceBundle bundle) {
            return new LoggerWindow(new LoggerController(windowHandler), bundle);
        }
    };

    public abstract AbstractWindow createWindow(WindowHandler windowHandler, ResourceBundle bundle);
}
