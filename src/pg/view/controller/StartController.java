package pg.view.controller;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import static pg.constant.ProgramConstants.IMG_RESOURCE_PATH;

import pg.exception.ProgramException;
import pg.view.ViewHandler;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:34:16
 */
public class StartController extends AbstractController {
    
    @FXML private TextField coreNameTextField;
    @FXML private ComboBox<String> fileConnectorComboBox;
    @FXML private TitledPane mandatoryTitledPane;
    @FXML private TitledPane optionalTitledPane;
    @FXML private Button runButton;
    @FXML private Button sourceButton;
    @FXML private Button destinationButton;
    @FXML private Button exitButton;
    @FXML private Label maxIndexesLabel;
    @FXML private Label sourceLabel;
    @FXML private Label destinationLabel;
    @FXML private ListView logListView;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        List<String> list = Arrays.asList(new String[]{"", "-", "_", "+"});
        coreNameTextField.setText("Test");
        fileConnectorComboBox.setItems(FXCollections.observableList(list));
        setUpButtons();
    }

    private void setUpButtons() {
        setImgForButton(sourceButton);
        setImgForButton(destinationButton);
        setImgForButton(runButton);
        setImgForButton(exitButton);
    }

    private void setImgForButton(Button button) {
        final String imgExtension = ".png";
        try {
            Image buttonImage = resourceHelper.readImage(IMG_RESOURCE_PATH + button.getId() + imgExtension);
            button.setGraphic(new ImageView(buttonImage));
        } catch (ProgramException ex) {
            logger.log(ex);
        }
    }

    public void exit() {
        logger.log("!!!Naciśnięto przycisk 'Exit'. Program zakończy działanie.");
        System.exit(0);
    }

    public void run() {
        logger.log("!!!Naciśnięto przycisk 'Run'.");
        maxIndexesLabel.setText("YOU PRESSED RUN MAN !!");
    }

    public void source() {
        logger.log("!!!Naciśnięto przycisk 'Src'.");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        configureDirectoryChooser(directoryChooser);
        File sourceDir = directoryChooser.showDialog(ViewHandler.window());
        sourceLabel.setText(sourceDir.getAbsolutePath());
    }

    private void configureDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("!!!Wybierz katalog");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public void destination() {
        logger.log("!!!Naciśnięto przycisk 'Dst'.");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        configureDirectoryChooser(directoryChooser);
        File destinationDir = directoryChooser.showDialog(ViewHandler.window());
        destinationLabel.setText(destinationDir.getAbsolutePath());
    }

}
