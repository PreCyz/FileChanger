package pg.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.helper.ResourceHelper;
import pg.logger.AbstractLogger;
import pg.logger.AppLogger;
import pg.logger.impl.ConsoleLogger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static pg.constant.ProgramConstants.IMG_RESOURCE_PATH;
import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-10-30 14:14:25
 */
public class AppViewHandler implements ViewHandler {
    
    private static Window window;

    private Stage primaryStage;
    private ResourceBundle bundle;
    private ResourceHelper resourceHelper;
    private AppLogger logger;

    public AppViewHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
        resourceHelper = new ResourceHelper();
        logger = new ConsoleLogger(MessageHelper.getInstance(bundle));
        AppViewHandler.window = primaryStage;
    }
    
    public void launchStartView() {
        try {
            Image icon = resourceHelper.readImage(IMG_RESOURCE_PATH + "title_icon.png");
            primaryStage.getIcons().add(icon);
        } catch (ProgramException ex) {
            logger.log(ex);
            String errorMsg = MessageHelper.getInstance(bundle).getErrorMsg(ex.getErrorCode(), ex.getArgument());
            AbstractLogger.addMessage(errorMsg);
        }
        try {
            primaryStage.setTitle(bundle.getString("window.title"));
            primaryStage.setResizable(false);
            primaryStage.setScene(createStartScene(ViewDetails.START_VIEW));
            primaryStage.show();
        } catch (ProgramException ex) {
            ViewHandler.handleException(ex);
        }
    }

    private Scene createStartScene(ViewDetails viewDetails) throws ProgramException {
        try {
            //String css = getClass().getClassLoader().getResource("pg/resource/css/start.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(viewDetails.url(), bundle);
            loader.setController(viewDetails.controller(this));
            return new Scene(loader.load());
            //scene.getStylesheets().add(css);
            //return scene;
        } catch (IOException ex) {
            throw new ProgramException(ErrorCode.LAUNCH_PROGRAM, ex);
        }
    }

    public void launchLoggerView() {
        Stage loggerStage = new Stage();
        try {
            Image icon = resourceHelper.readImage(IMG_RESOURCE_PATH + "title_icon.png");
            loggerStage.getIcons().add(icon);
        } catch (ProgramException ex) {
            logger.log(ex);
            String errorMsg = MessageHelper.getInstance(bundle).getErrorMsg(ex.getErrorCode(), ex.getArgument());
            AbstractLogger.addMessage(errorMsg);
        }
        try {
            loggerStage.initModality(Modality.WINDOW_MODAL);
            loggerStage.setTitle(bundle.getString("window.title"));
            loggerStage.setResizable(false);
            loggerStage.setScene(createStartScene(ViewDetails.LOGGER_VIEW));
            loggerStage.show();
        } catch (ProgramException ex) {
            ViewHandler.handleException(ex);
        }
    }

    public static Window window() {
        return window;
    }
}
