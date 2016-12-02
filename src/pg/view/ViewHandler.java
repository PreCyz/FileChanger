package pg.view;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static pg.constant.ProgramConstants.IMG_PATH;
import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;
import pg.exception.ProgramException;
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
    
    private Stage primaryStage;
    private ResourceBundle bundle;
    private ResourceHelper resourceHelper;
    private AppLogger logger;
    private MessageHelper messageHelper;
    
    private ViewHandler() {}

    public ViewHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault());
        messageHelper = MessageHelper.getInstance(bundle);
        resourceHelper = new ResourceHelper();
        logger = new ConsoleLogger(messageHelper);
    }
    
    public void launchView() throws IOException {
        Parent root = FXMLLoader.load(FXML.START_VIEW.url(), bundle);
        Scene scene = new Scene(root);
        try {
            Image icon = resourceHelper.readImage(IMG_PATH + "title_icon.png");
            primaryStage.getIcons().add(icon);
        } catch (ProgramException ex) {
            logger.log(ex);
        }
        primaryStage.setTitle(bundle.getString("window.title"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
