package pg.view;

import javafx.fxml.FXMLLoader;
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
import pg.view.controller.AbstractController;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-10-30 14:14:25
 */
public class AppViewHandler extends AbstractViewHandler {

    private Stage primaryStage;
    private ResourceBundle bundle;
    private ResourceHelper resourceHelper;
    private AppLogger logger;

    public AppViewHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
        resourceHelper = new ResourceHelper();
        logger = new ConsoleLogger(MessageHelper.getInstance(bundle));
        window = primaryStage;
    }

    public void launchStartView() {
        buildScene(primaryStage, ViewDetails.START_VIEW);
    }

    public void launchLoggerView() {
        Stage loggerStage = new Stage();
        loggerStage.initModality(Modality.WINDOW_MODAL);
        buildScene(loggerStage, ViewDetails.LOGGER_VIEW);
    }

    private void buildScene(Stage stage, ViewDetails viewDetails) {
        try {
            Image icon = resourceHelper.readImage(viewDetails.windowImgFilePath());
            stage.getIcons().add(icon);
        } catch (ProgramException ex) {
            logger.log(ex);
            String errorMsg = MessageHelper.getInstance(bundle).getErrorMsg(ex.getErrorCode(), ex.getArgument());
            AbstractLogger.addMessage(errorMsg);
        }
        try {
            stage.setTitle(bundle.getString(viewDetails.windowTitleBundle()));
            stage.setResizable(false);
            //String css = getClass().getClassLoader().getResource("pg/resource/css/start.css").toExternalForm();
            FXMLLoader loader = new FXMLLoader(viewDetails.url(), bundle);
            AbstractController controller = viewDetails.controller(this);
            loader.setController(controller);
            stage.setScene(new Scene(loader.load()));
            stage.show();
            controller.calculateWindowWidth();
        } catch (IOException ex) {
            handleException(new ProgramException(ErrorCode.LAUNCH_PROGRAM, ex));
        }
    }

    public void changeWindowWidth(double width) {
        window.setWidth(window.getWidth() - width);
    }
}
