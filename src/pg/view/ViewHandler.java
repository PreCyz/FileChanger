package pg.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pg.view.fxml.FXML;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-10-30 14:14:25
 */
public class ViewHandler {
    
    private Stage primaryStage;
    private ResourceBundle bundle;
    
    private ViewHandler() {}

    public ViewHandler(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.bundle = ResourceBundle.getBundle("pg.resource.bundle", Locale.getDefault());
    }
    
    public void launchView() throws IOException {
        //Image icon = new Image(getClass().getClassLoader().getResourceAsStream("pg/resource/img/title_icon.png"));
        Parent root = FXMLLoader.load(FXML.START_VIEW.url(), bundle);
        Scene scene = new Scene(root);
        
        //primaryStage.getIcons().add(icon);
        primaryStage.setTitle("???toBeFixedByBundle");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
