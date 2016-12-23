package pg.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static pg.constant.ProgramConstants.IMG_RESOURCE_PATH;
import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;

import javafx.stage.Window;
import pg.exception.ProgramException;
import pg.logger.AbstractLogger;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.helper.ResourceHelper;
import pg.logger.AppLogger;
import pg.view.fxml.FXML;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-10-30 14:14:25
 */
public class ViewHandler {
    
    private static Window window;

    private Stage primaryStage;
    private ResourceBundle bundle;
    private ResourceHelper resourceHelper;
    private AppLogger logger;

    public ViewHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
        resourceHelper = new ResourceHelper();
        logger = new ConsoleLogger(MessageHelper.getInstance(bundle));
        ViewHandler.window = primaryStage;
    }
    
    public void launchView() throws IOException {
        try {
            Image icon = resourceHelper.readImage(IMG_RESOURCE_PATH + "title_icon.png");
            primaryStage.getIcons().add(icon);
        } catch (ProgramException ex) {
            logger.log(ex);
            String errorMsg = MessageHelper.getInstance(bundle).getErrorMsg(ex.getErrorCode(), ex.getArgument());
            AbstractLogger.addMessage(errorMsg);
        }
        primaryStage.setTitle(bundle.getString("window.title"));
        primaryStage.setResizable(false);

        Parent root = FXMLLoader.load(FXML.START_VIEW.url(), bundle);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static Window window() {
        return window;
    }
}
