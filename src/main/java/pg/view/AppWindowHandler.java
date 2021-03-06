package pg.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.helper.ResourceHelper;
import pg.logger.AbstractLogger;
import pg.logger.AppLogger;
import pg.logger.impl.ConsoleLogger;
import pg.view.factory.WindowFactory;
import pg.view.window.AbstractWindow;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static pg.constant.AppConstants.RESOURCE_BUNDLE;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-10-30 14:14:25
 */
public class AppWindowHandler extends AbstractWindowHandler {

    private Stage primaryStage;
    private ResourceBundle bundle;
    private ResourceHelper resourceHelper;
    private AppLogger logger;
    private MessageHelper messageHelper;

    public AppWindowHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
        messageHelper = MessageHelper.getInstance(bundle);
        resourceHelper = new ResourceHelper();
        logger = new ConsoleLogger();
        window = primaryStage;
    }

    public void launchStartView() {
        buildScene(primaryStage, WindowFactory.START.createWindow(this, bundle));
    }

    public void launchLoggerView() {
        Stage loggerStage = new Stage();
        loggerStage.initModality(Modality.WINDOW_MODAL);
        buildScene(loggerStage, WindowFactory.LOGGER.createWindow(this, bundle));
    }

    private void buildScene(Stage stage, AbstractWindow window) {
        try {
            Image icon = resourceHelper.readImage(window.windowImgFilePath());
            stage.getIcons().add(icon);
        } catch (ProgramException ex) {
            logger.log(messageHelper.getErrorMsg(ex.getErrorCode()));
            String errorMsg = MessageHelper.getInstance(bundle).getErrorMsg(ex.getErrorCode(), ex.getArgument());
            AbstractLogger.addMessage(errorMsg);
        }
        try {
            stage.setTitle(bundle.getString(window.windowTitleBundle()));
            stage.setResizable(window.resizable());
	        Scene scene = new Scene(window.root());
	        scene.getStylesheets().add(window.css());
	        stage.setScene(scene);
            stage.show();
            window.refreshWindowSize();
        } catch (IOException ex) {
            handleException(new ProgramException(ErrorCode.LAUNCH_PROGRAM, ex));
        }
    }

    public void changeWindowWidth(double width) {
        window.setWidth(window.getWidth() - width);
    }
}
